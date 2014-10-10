package es.tid.rsvp.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.SenderDescriptor;
import es.tid.rsvp.constructs.te.SenderDescriptorTE;
import es.tid.rsvp.objects.Integrity;
import es.tid.rsvp.objects.RSVPHop;
import es.tid.rsvp.objects.RSVPHopIPv4;
import es.tid.rsvp.objects.RSVPHopIPv6;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.Session;
import es.tid.rsvp.objects.SessionIPv4;
import es.tid.rsvp.objects.SessionIPv6;
import es.tid.rsvp.objects.SessionLSPTunnelIPv4;

/*
RFC 2205: RSVP		Path Teardown Messages

         Receipt of a PathTear (path teardown) message deletes matching
         path state.  Matching state must have match the SESSION,
         SENDER_TEMPLATE, and PHOP objects.  In addition, a PathTear
         message for a multicast session can only match path state for
         the incoming interface on which the PathTear arrived.  If there
         is no matching path state, a PathTear message should be
         discarded and not forwarded.

         PathTear messages are initiated explicitly by senders or by
         path state timeout in any node, and they travel downstream
         towards all receivers.  A unicast PathTear must not be
         forwarded if there is path state for the same (session, sender)
         pair but a different PHOP.  Forwarding of multicast PathTear
         messages is governed by the rules of Section 3.9.

         A PathTear message must be routed exactly like the
         corresponding Path message.  Therefore, its IP destination
         address must be the session DestAddress, and its IP source
         address must be the sender address from the path state being
         torn down.

             <PathTear Message> ::= <Common Header> [ <INTEGRITY> ]

                                         <SESSION> <RSVP_HOP>

                                        [ <sender descriptor> ]

             <sender descriptor> ::= (see earlier definition)


         A PathTear message may include a SENDER_TSPEC or ADSPEC object
         in its sender descriptor, but these must be ignored.  The order
         requirements are as given earlier for a Path message, but the
         above order is recommended.

         Deletion of path state as the result of a PathTear message or a
         timeout must also adjust related reservation state as required
         to maintain consistency in the local node.  The adjustment
         depends upon the reservation style.  For example, suppose a
         PathTear deletes the path state for a sender S.  If the style
         specifies explicit sender selection (FF or SE), any reservation
         with a filter spec matching S should be deleted; if the style
         has wildcard sender selection (WF), the reservation should be
         deleted if S is the last sender to the session.  These
         reservation changes should not trigger an immediate Resv
         refresh message, since the PathTear message has already made
         the required changes upstream.  They should not trigger a
         ResvErr message, since the result could be to generate a shower
         of such messages.

*/


public class RSVPPathTearMessage extends RSVPMessage {

	/*
	 *   RSVP Common Header

                0             1              2             3
         +-------------+-------------+-------------+-------------+
         | Vers | Flags|  Msg Type   |       RSVP Checksum       |
         +-------------+-------------+-------------+-------------+
         |  Send_TTL   | (Reserved)  |        RSVP Length        |
         +-------------+-------------+-------------+-------------+
         
         The fields in the common header are as follows:

         Vers: 4 bits

              Protocol version number.  This is version 1.

         Flags: 4 bits

              0x01-0x08: Reserved

                   No flag bits are defined yet.

         Msg Type: 8 bits

              1 = Path

              2 = Resv

              3 = PathErr

              4 = ResvErr

              5 = PathTear

              6 = ResvTear

              7 = ResvConf

         RSVP Checksum: 16 bits

              The one's complement of the one's complement sum of the
              message, with the checksum field replaced by zero for the
              purpose of computing the checksum.  An all-zero value
              means that no checksum was transmitted.

         Send_TTL: 8 bits

              The IP TTL value with which the message was sent.  See
              Section 3.8.

         RSVP Length: 16 bits

              The total length of this RSVP message in bytes, including
              the common header and the variable-length objects that
              follow.
         
        <PathTear Message> ::= <Common Header> [ <INTEGRITY> ]

                               <SESSION> <RSVP_HOP>

                              [ <sender descriptor> ]

        <sender descriptor> ::= (see earlier definition)

         
         
	 */
	
	private Integrity integrity;
	private Session session;
	private RSVPHop rsvpHop;
	private LinkedList<SenderDescriptor> senderDescriptors;
	
	/**
	 * Log
	 */
	private Logger log;
	
	/**
	 * Constructor that has to be used in case of creating a new Path Teardown Message to
	 * be sent.
	 */
	
	public RSVPPathTearMessage(){
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_PATHTEAR;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;

		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log = Logger.getLogger("ROADM");

		log.finest("RSVP Path Teardown Message Created");
		
	}
	
	/**
	 * Constructor to be used in case of creating a new Path TearDown message to be decoded
	 * @param bytes
	 * @param length
	 */
	
	public RSVPPathTearMessage(byte[] bytes, int length){
		this.bytes = bytes;
		this.length = length;
		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log = Logger.getLogger("ROADM");

		log.finest("RSVP Path Message Created");
	}
	
	/**
	 * 
	 */
	
	public void encodeHeader() {
		bytes[0]= (byte)(((vers<<4) &0xF0) | (flags & 0x0F));
		bytes[1]= (byte) msgType;
		bytes[2]= (byte)((rsvpChecksum>>8) & 0xFF);
		bytes[3]= (byte)(rsvpChecksum & 0xFF);
		bytes[4]= (byte) sendTTL;
		bytes[5]= (byte) reserved;
		bytes[6]= (byte)((length>>8) & 0xFF);
		bytes[7]= (byte)(length & 0xFF);
	}

	/**
	 * 
	 */
	
	public void encode() throws RSVPProtocolViolationException {

		log.finest("Starting RSVP Path TearDown Message encode");
		
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		length=commonHeaderSize;
		// Obtencion del tama�o completo del mensaje
		if(integrity != null){
			length = length + integrity.getLength();
			log.finest("Integrity RSVP Object found");
		}
		if(session != null){
			length = length + session.getLength();
			log.finest("Session RSVP Object found");
			
		}else{
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Session RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
		}
		if(rsvpHop != null){
			length = length + rsvpHop.getLength();
			log.finest("Hop RSVP Object found");
		}else{
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Hop RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
		}
		int sdSize = senderDescriptors.size();

		for(int i = 0; i < sdSize; i++){
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			length = length + sd.getLength();
			log.finest("Sender Descriptor RSVP Construct found");
		}
		
		bytes = new byte[length];
		encodeHeader();
		int currentIndex = commonHeaderSize;
		
		if(integrity != null){
			//Campo Opcional
			integrity.encode();
			System.arraycopy(integrity.getBytes(), 0, bytes, currentIndex, integrity.getLength());
			currentIndex = currentIndex + integrity.getLength();
		}
		
		// Campo Obligatorio
		session.encode();
		System.arraycopy(session.getBytes(), 0, bytes, currentIndex, session.getLength());
		currentIndex = currentIndex + session.getLength();
		// Campo Obligatorio
		rsvpHop.encode();
		System.arraycopy(rsvpHop.getBytes(), 0, bytes, currentIndex, rsvpHop.getLength());
		currentIndex = currentIndex + rsvpHop.getLength();
	
		// Lista de Sender Descriptors
		for(int i = 0; i < sdSize; i++){
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			try{
				sd.encode();
				System.arraycopy(sd.getBytes(), 0, bytes, currentIndex, sd.getLength());
				currentIndex = currentIndex + sd.getLength();

			}catch(RSVPProtocolViolationException e){
				log.severe("Errors during Sender Descriptor number "+i+" encoding");
			}
		}
		log.finest("RSVP Path TearDown Message encoding accomplished");
	}

	/**
	 * 
	 */
	
	public void decode() throws RSVPProtocolViolationException {

		decodeHeader();
		
		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			int classNum = RSVPObject.getClassNum(bytes,offset);
			if(classNum == 1){
				// Session Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					// Session IPv4
					session = new SessionIPv4();
					session.decode(bytes, offset);
					offset = offset + session.getLength();
					
				}else if(cType == 2){
					// Session IPv6
					session = new SessionIPv6();
					session.decode(bytes, offset);
					offset = offset + session.getLength();
					
				}else if(cType == 7){
					// LSPTunnelSession IPv4
					session = new SessionLSPTunnelIPv4(bytes,offset);
					session.decode(bytes, offset);
					offset = offset + session.getLength();
					
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			} else if(classNum == 3){
				// RSVPHop Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					// RSVPHop IPv4
					rsvpHop = new RSVPHopIPv4();
					rsvpHop.decode(bytes, offset);
					offset = offset + rsvpHop.getLength();
				}else if(cType == 2){
					// RSVPHop IPv6
					rsvpHop = new RSVPHopIPv6();
					rsvpHop.decode(bytes, offset);
					offset = offset + rsvpHop.getLength();
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			}
			else if(classNum == 4){
				
				// Integrity Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					integrity = new Integrity();
					integrity.decode(bytes, offset);
					offset = offset + integrity.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
			}
			else if(classNum == 11){
				// Sender Descriptor Construct
				int cType = RSVPObject.getcType(bytes,offset);
				if((cType == 7)||(cType == 8)){
					
					SenderDescriptorTE sd = new SenderDescriptorTE();
					sd.decode(bytes, offset);
					offset = offset + sd.getLength();
					this.addSenderDescriptor(sd);
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}
				
			}
			else{
				// Fallo en classNum
				throw new RSVPProtocolViolationException();
			}
		}
	}

	/**
	 * 
	 * @param senderDescriptor
	 */
	
	public void addSenderDescriptor(SenderDescriptor senderDescriptor){
		
		senderDescriptors.add(senderDescriptor);

	}

	
	
	// Getters & Setters
		
	
	public Integrity getIntegrity() {
		return integrity;
	}

	public void setIntegrity(Integrity integrity) {
		this.integrity = integrity;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public RSVPHop getRsvpHop() {
		return rsvpHop;
	}

	public void setRsvpHop(RSVPHop rsvpHop) {
		this.rsvpHop = rsvpHop;
	}

	public LinkedList<SenderDescriptor> getSenderDescriptors() {
		return senderDescriptors;
	}

	public void setSenderDescriptors(LinkedList<SenderDescriptor> senderDescriptors) {
		this.senderDescriptors = senderDescriptors;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	


}