package es.tid.rsvp.messages;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.SenderDescriptor;
import es.tid.rsvp.objects.ErrorSpec;
import es.tid.rsvp.objects.ErrorSpecIPv4;
import es.tid.rsvp.objects.ErrorSpecIPv6;
import es.tid.rsvp.objects.Integrity;
import es.tid.rsvp.objects.PolicyData;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import es.tid.rsvp.objects.Session;
import es.tid.rsvp.objects.SessionIPv4;
import es.tid.rsvp.objects.SessionIPv6;
import org.slf4j.LoggerFactory;


/**
  Path Error Message.

         PathErr (path error) messages report errors in processing Path
         messages.  They are travel upstream towards senders and are
         routed hop-by-hop using the path state.  At each hop, the IP
         destination address is the unicast address of a previous hop.
         PathErr messages do not modify the state of any node through
         which they pass; they are only reported to the sender
         application.

{@code
           <PathErr message> ::= <Common Header> [ <INTEGRITY> ]

                                      <SESSION> <ERROR_SPEC>

                                      [ <POLICY_DATA> ...]

                                     [ <sender descriptor> ]

           <sender descriptor> ::= (see earlier definition)}

         The ERROR_SPEC object specifies the error and includes the IP
         address of the node that detected the error (Error Node
         Address).  One or more POLICY_DATA objects may be included
         message to provide relevant information.  The sender descriptor
         is copied from the message in error.  The object order
         requirements are as given earlier for a Path message, but the
         above order is recommended.

 * @author fmn
 *
 */

public class RSVPPathErrMessage extends RSVPMessage {

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
         
	 */
	
	private Integrity integrity;
	private Session session;
	private ErrorSpec errorSpec;
	private LinkedList<PolicyData> policyData;
	private LinkedList<SenderDescriptor> senderDescriptors;
	
	/**
	 * Log
	 */
  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor that has to be used in case of creating a new Path Error Message to
	 * be sent.
	 */
	
	public RSVPPathErrMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_PATHERR;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log.debug("RSVP Path Error Message Created");
		
		
	}
	
	/**
	 * Constructor to be used in case of creating a new Path Error message to be decoded
	 * @param bytes bytes
	 * @param length length 
	 */
	
	public RSVPPathErrMessage(byte[] bytes, int length){
		
		this.bytes = bytes;
		this.length = length;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log.debug("RSVP Path Error Message Created");
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
		
	}

	@Override
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting RSVP Path Error Message encode");
		//FIXME: COMPUTE CHECKSUM!!
		rsvpChecksum = 0xFF;
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		length=commonHeaderSize;
		// Obtencion del tama�o completo del mensaje
		
		if(integrity != null){
			integrity.encode();
			length = length + integrity.getLength();
			log.debug("Integrity RSVP Object found");
			
		}
		if(session != null){
			session.encode();
			length = length + session.getLength();
			log.debug("Session RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Session RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}if(errorSpec != null){
			errorSpec.encode();
			length = length + errorSpec.getLength();
			log.debug("ErrorSpec RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("ErrorSpec RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		int pdSize = policyData.size();
		
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			pd.encode();
			length = length + pd.getLength();
			log.debug("Policy Data RSVP Object found");
				
		}						
		
		int sdSize = senderDescriptors.size();

		for(int i = 0; i < sdSize; i++){
			
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			try{
			sd.encode();
			length = length + sd.getLength();
			log.debug("Sender Descriptor RSVP Construct found");
		}catch(RSVPProtocolViolationException e){
			
			log.error("Errors during Sender Descriptor number " + i + " encoding");
			
		}
			
		}
		
		bytes = new byte[length];
		encodeHeader();
		int currentIndex = commonHeaderSize;
		
		if(integrity != null){
			
			//Campo Opcional
			
			System.arraycopy(integrity.getBytes(), 0, bytes, currentIndex, integrity.getLength());
			currentIndex = currentIndex + integrity.getLength();
			
		}
		
		// Campo Obligatorio
		
		System.arraycopy(session.getBytes(), 0, bytes, currentIndex, session.getLength());
		currentIndex = currentIndex + session.getLength();

		// Campo Obligatorio
		
		System.arraycopy(errorSpec.getBytes(), 0, bytes, currentIndex, errorSpec.getLength());
		currentIndex = currentIndex + errorSpec.getLength();
		
		// Campos Opcionales
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			
			System.arraycopy(pd.getBytes(), 0, bytes, currentIndex, pd.getLength());
			currentIndex = currentIndex + pd.getLength();
							
		}
	
		// Lista de Sender Descriptors
		for(int i = 0; i < sdSize; i++){
			
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			System.arraycopy(sd.getBytes(), 0, bytes, currentIndex, sd.getLength());

				
 
				currentIndex = currentIndex + sd.getLength();

			
						
		}
	
		log.debug("RSVP Path Error Message encoding accomplished");
		
	}

	@Override
	public void decode() throws RSVPProtocolViolationException {

		decodeHeader();
		
		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			int classNum = RSVPObject.getClassNum(bytes,offset);
			//System.out.println(" classnum "+classNum+" offset "+offset +"length "+length);

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
			}else if(classNum == 6){
				
				// ErrorSpec Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// ErrorSpec IPv4
					errorSpec = new ErrorSpecIPv4();
					errorSpec.decode(bytes, offset);
					
					offset = offset + errorSpec.getLength();
					
				}else if(cType == 2){
					
					// ErrorSpec IPv6
					errorSpec = new ErrorSpecIPv6();
					errorSpec.decode(bytes, offset);
					offset = offset + errorSpec.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
				
			}else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_POLICY_DATA){
				
				// Policy Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					PolicyData pd = new PolicyData();
					pd.decode(bytes, offset);
					offset = offset + pd.getLength();
					policyData.add(pd);
					System.out.println(" LEEENGO "+pd.getLength()+" offset "+offset +"length "+length);
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TEMPLATE){
				
				// Sender Descriptor Construct
				int cType = RSVPObject.getcType(bytes,offset);
				if((cType == 1)||(cType == 2)||(cType == 3)){
					
					SenderDescriptor sd = new SenderDescriptor();
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
	 * @param senderDescriptor Sender Descriptor
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

	public ErrorSpec getErrorSpec() {
		return errorSpec;
	}

	public void setErrorSpec(ErrorSpec errorSpec) {
		this.errorSpec = errorSpec;
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

	public void setSenderDescriptors(LinkedList<SenderDescriptor> senderDescriptors) {
		this.senderDescriptors = senderDescriptors;
	}

}
