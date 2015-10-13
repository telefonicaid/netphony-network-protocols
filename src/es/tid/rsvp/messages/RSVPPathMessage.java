package es.tid.rsvp.messages;

import java.util.*;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.SenderDescriptor;
import es.tid.rsvp.objects.*;
import org.slf4j.LoggerFactory;


/**
     RFC 2205: RSVP		PATH Message

         Each sender host periodically sends a Path message for each
         data flow it originates.  It contains a SENDER_TEMPLATE object
         defining the format of the data packets and a SENDER_TSPEC
         object specifying the traffic characteristics of the flow.
         Optionally, it may contain may be an ADSPEC object carrying
         advertising (OPWA) data for the flow.

         A Path message travels from a sender to receiver(s) along the
         same path(s) used by the data packets.  The IP source address
         of a Path message must be an address of the sender it
         describes, while the destination address must be the
         DestAddress for the session.  These addresses assure that the
         message will be correctly routed through a non-RSVP cloud.

         The format of a Path message is as follows:

           <Path Message> ::= <Common Header> [ <INTEGRITY> ]

                                     <SESSION> <RSVP_HOP>

                                     <TIME_VALUES>

                                    [ <POLICY_DATA> ... ]

                                    [ <sender descriptor> ]

           <sender descriptor> ::= <SENDER_TEMPLATE> <SENDER_TSPEC>

                                    [ <ADSPEC> ]


         If the INTEGRITY object is present, it must immediately follow
         the common header.  There are no other requirements on
         transmission order, although the above order is recommended.
         Any number of POLICY_DATA objects may appear.

         The PHOP (i.e., RSVP_HOP) object of each Path message contains
         the previous hop address, i.e., the IP address of the interface
         through which the Path message was most recently sent.  It also
         carries a logical interface handle (LIH).

         Each RSVP-capable node along the path(s) captures a Path
         message and processes it to create path state for the sender
         defined by the SENDER_TEMPLATE and SESSION objects.  Any
         POLICY_DATA, SENDER_TSPEC, and ADSPEC objects are also saved in
         the path state.  If an error is encountered while processing a
         Path message, a PathErr message is sent to the originating
         sender of the Path message.  Path messages must satisfy the
         rules on SrcPort and DstPort in Section 3.2.

         Periodically, the RSVP process at a node scans the path state
         to create new Path messages to forward towards the receiver(s).
         Each message contains a sender descriptor defining one sender,
         and carries the original sender's IP address as its IP source
         address.  Path messages eventually reach the applications on
         all receivers; however, they are not looped back to a receiver
         running in the same application process as the sender.

         The RSVP process forwards Path messages and replicates them as
         required by multicast sessions, using routing information it
         obtains from the appropriate uni-/multicast routing process.
         The route depends upon the session DestAddress, and for some
         routing protocols also upon the source (sender's IP) address.
         The routing information generally includes the list of zero or
         more outgoing interfaces to which the Path message is to be
         forwarded.  Because each outgoing interface has a different IP
         address, the Path messages sent out different interfaces
         contain different PHOP addresses.  In addition, ADSPEC objects
         carried in Path messages will also generally differ for
         different outgoing interfaces.

         Path state for a given session and sender may not necessarily
         have a unique PHOP or unique incoming interface.  There are two
         cases, corresponding to multicast and unicast sessions.

         o    Multicast Sessions

              Multicast routing allows a stable distribution tree in
              which Path messages from the same sender arrive from more
              than one PHOP, and RSVP must be prepared to maintain all
              such path state.  The RSVP rules for handling this
              situation are contained in Section 3.9.  RSVP must not
              forward (according to the rules of Section 3.9) Path
              messages that arrive on an incoming interface different
              from that provided by routing.

         o    Unicast Sessions

              For a short period following a unicast route change
              upstream, a node may receive Path messages from multiple
              PHOPs for a given (session, sender) pair.  The node cannot
              reliably determine which is the right PHOP, although the
              node will receive data from only one of the PHOPs at a
              time.  One implementation choice for RSVP is to ignore
              PHOP in matching unicast past state, and allow the PHOP to
              flip among the candidates.  Another implementation choice
              is to maintain path state for each PHOP and to send Resv
              messages upstream towards all such PHOPs.  In either case,
              the situation is a transient; the unused path state will
              time out or be torn down (because upstream path state
              timed out).

*/

public class RSVPPathMessage extends RSVPMessage{

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
              
           
           <Path Message> ::= <Common Header> [ <INTEGRITY> ]

                                     <SESSION> <RSVP_HOP>

                                     <TIME_VALUES>

                                    [ <POLICY_DATA> ... ]

                                    [ <sender descriptor> ]

           <sender descriptor> ::= <SENDER_TEMPLATE> <SENDER_TSPEC>

                                    [ <ADSPEC> ]


         
	 */
	
	protected Integrity integrity;
	protected Session session;
	protected RSVPHop rsvpHop;
	protected TimeValues timeValues;
	protected LinkedList<PolicyData> policyData;
	protected LinkedList<SenderDescriptor> senderDescriptors;
    //***************RUBEN**************	
	protected LinkedList<IntservSenderTSpec> intservSenderTSpec;
	//**********************************
	
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	public RSVPPathMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_PATH;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		//***************RUBEN**************	
		intservSenderTSpec = new LinkedList<IntservSenderTSpec>();
		//**********************************
		
		log.debug("RSVP Path Message Created");
				
	}
	
	/**
	 * 
	 * @param bytes
	 * @param length
	 */
	
	public RSVPPathMessage(byte[] bytes, int length){
		
		this.bytes = bytes;
		this.length = length;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		//***************RUBEN**************
		intservSenderTSpec = new LinkedList<IntservSenderTSpec>();
		//**********************************
		
		log.debug("RSVP Path Message Created");
	}
	
	@Override
	public void encodeHeader() {
		bytes[0]= (byte)(((vers<<4) &0xF0) | (flags & 0x0F));
		bytes[1]= (byte) msgType;
		bytes[2]= (byte)((rsvpChecksum>>8) & 0xFF);
		bytes[3]= (byte)(rsvpChecksum & 0xFF);
		bytes[4]= (byte) sendTTL;
		bytes[5]= (byte) reserved;
		bytes[6]= (byte)((length>>8) & 0xFF);
		bytes[7]= (byte)(length & 0xFF);
		log.debug("RSVP Path Message Header encoded");
	}
	
	/**
	 * @throws RSVPProtocolViolationException
	 */
	
	public void encode() throws RSVPProtocolViolationException{

		log.debug("Starting RSVP Path Message encode");
		
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		// Obtencion del tama�o completo del mensaje
		
		if(integrity != null){
			
			length = length + integrity.getLength();
			log.debug("Integrity RSVP Object found");
		}
		if(session != null){
			
			length = length + session.getLength();
			log.debug("Session RSVP Object found");
		
		}else{
			// Campo Obligatorio, si no existe hay fallo
			log.error("Session RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
		}
		if(rsvpHop != null){
			
			length = length + rsvpHop.getLength();
			log.debug("Hop RSVP Object found");
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Hop RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		if(timeValues != null){
			
			length = length + timeValues.getLength();
			log.debug("Time Values RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Time Values RSVP Object NOT found");
			throw new RSVPProtocolViolationException();			
		}
		
		int pdSize = policyData.size();
		
			
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			length = length + pd.getLength();
			log.debug("Policy Data RSVP Object found");
				
		}
						
		
		int sdSize = senderDescriptors.size();

		for(int i = 0; i < sdSize; i++){
			
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			length = length + sd.getLength();
			log.debug("Sender Descriptor RSVP Construct found");
			
		}
		
		//*************** RUBEN *****************
		int iSenderTSpecSize = intservSenderTSpec.size();

		for(int i = 0; i < iSenderTSpecSize; i++){
			
			IntservSenderTSpec isTSpec = (IntservSenderTSpec) intservSenderTSpec.get(i);
			length = length + isTSpec.getLength();
			log.debug("IntServSenderTSpec Descriptor RSVP Construct found");
			System.out.println("rub IntServSenderTSpec Descriptor RSVP Construct found (RSVPPathMessage.java line 336)");
		}
		//****************************************
		
		
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
		// Campo Obligatorio
		timeValues.encode();
		System.arraycopy(timeValues.getBytes(), 0, bytes, currentIndex, timeValues.getLength());
		currentIndex = currentIndex + timeValues.getLength();
		
		// Campos Opcionales
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			pd.encode();
			System.arraycopy(pd.getBytes(), 0, bytes, currentIndex, pd.getLength());
			currentIndex = currentIndex + pd.getLength();
		}
	
		// Lista de Sender Descriptors
		for(int i = 0; i < sdSize; i++){
			
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			try{
				sd.encode();
				System.arraycopy(sd.getBytes(), 0, bytes, currentIndex, sd.getLength());
				currentIndex = currentIndex + sd.getLength();

			}catch(RSVPProtocolViolationException e){
				
				log.error("Errors during Sender Descriptor number " + i + " encoding");
				
			}
						
		}
		
		
		//*************** RUBEN *****************
		//
		for(int i = 0; i < iSenderTSpecSize; i++){
					
			IntservSenderTSpec isTSpec = (IntservSenderTSpec) intservSenderTSpec.get(i);
			//try{
				isTSpec.encode();
				System.arraycopy(isTSpec.getBytes(), 0, bytes, currentIndex, isTSpec.getLength());
				currentIndex = currentIndex + isTSpec.getLength();

		/*	}catch(RSVPProtocolViolationException a){
						
				log.error("Errors during Sender Descriptor number " + i + " encoding");
						
			}*/
								
		}
	
		//***************************************
				
				
		log.debug("RSVP Path Message encoding accomplished");
		
	}

	/**
	 * @throws RSVPProtocolViolationException
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
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
			}
			else if(classNum == 3){
				
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
			else if(classNum == 5){
				
				// TimeValues Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					timeValues = new TimeValues();
					timeValues.decode(bytes, offset);
					offset = offset + timeValues.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == 13){
				
				// Policy Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					PolicyData pd = new PolicyData();
					pd.decode(bytes, offset);
					offset = offset + pd.getLength();
					policyData.add(pd);
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == 11){
				log.info("XXXXX entramos en 11" );
				// Sender Descriptor Construct
				int cType = RSVPObject.getcType(bytes,offset);
				if((cType == 1)||(cType == 2)||(cType == 3)){
					
					SenderDescriptor sd = new SenderDescriptor();
					sd.decode(bytes, offset);
					offset = offset + sd.getLength();
					this.addSenderDescriptor(sd);
					
				}else{
					log.info("XXXXX fallo en cType" );
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}
//			else{
//				
//				//************  RUBEN ***************
//				if (classNum == 12)
//				System.out.println("rub Entra en el decode de RSVPPathMessaje.java line 551. ClassNum: "+ classNum);
//				// Fallo en classNum
//				else throw new RSVPProtocolViolationException();
//			}
		}
	}
	
	
	/**
	 * 
	 * @param senderDescriptor
	 */
	
	public void addSenderDescriptor(SenderDescriptor senderDescriptor){
		
		senderDescriptors.add(senderDescriptor);

	}

	// GETTERS AND SETTERS

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

	public TimeValues getTimeValues() {
		return timeValues;
	}

	public void setTimeValues(TimeValues timeValues) {
		this.timeValues = timeValues;
	}



	public LinkedList<PolicyData> getPolicyData() {
		return policyData;
	}

	public void setPolicyData(LinkedList<PolicyData> policyData) {
		this.policyData = policyData;
	}

	public LinkedList<SenderDescriptor> getSenderDescriptors() {
		return senderDescriptors;
	}

	public LinkedList<IntservSenderTSpec> getIntservSenderTSpec() {
		return intservSenderTSpec;
	}

}
