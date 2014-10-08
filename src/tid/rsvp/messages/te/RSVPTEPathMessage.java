package tid.rsvp.messages.te;

import java.util.*;
import java.util.logging.Logger;
import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.constructs.SenderDescriptor;
import tid.rsvp.constructs.te.SenderDescriptorTE;
import tid.rsvp.messages.RSVPMessageTypes;
import tid.rsvp.messages.RSVPPathMessage;
import tid.rsvp.objects.*;


/*
     RFC 3209: LSP Tunnel related Message Formats

   Five new objects are defined in this section:

      Object name          Applicable RSVP messages
      ---------------      ------------------------
      LABEL_REQUEST          Path
      LABEL                  Resv
      EXPLICIT_ROUTE         Path
      RECORD_ROUTE           Path, Resv
      SESSION_ATTRIBUTE      Path

   New C-Types are also assigned for the SESSION, SENDER_TEMPLATE, and
   FILTER_SPEC, objects.

   Detailed descriptions of the new objects are given in later sections.
   All new objects are OPTIONAL with respect to RSVP.  An implementation
   can choose to support a subset of objects.  However, the
   LABEL_REQUEST and LABEL objects are mandatory with respect to this
   specification.

   The LABEL and RECORD_ROUTE objects, are sender specific.  In Resv
   messages they MUST appear after the associated FILTER_SPEC and prior
   to any subsequent FILTER_SPEC.

   The relative placement of EXPLICIT_ROUTE, LABEL_REQUEST, and
   SESSION_ATTRIBUTE objects is simply a recommendation.  The ordering
   of these objects is not important, so an implementation MUST be
   prepared to accept objects in any order.

3.1. Path Message

   The format of the Path message is as follows:

      <Path Message> ::=       <Common Header> [ <INTEGRITY> ]
                               <SESSION> <RSVP_HOP>
                               <TIME_VALUES>
                               [ <EXPLICIT_ROUTE> ]
                               <LABEL_REQUEST>
                               [ <SESSION_ATTRIBUTE> ]

                               [ <POLICY_DATA> ... ]
                               <sender descriptor>

      <sender descriptor> ::=  <SENDER_TEMPLATE> <SENDER_TSPEC>
                               [ <ADSPEC> ]
                               [ <RECORD_ROUTE> ]


*/

public class RSVPTEPathMessage extends RSVPPathMessage{

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
              
     
	 */
	
	private ERO ero;
	private LabelRequest labelRequest;
	private SessionAttribute sessionAttribute;
	private SuggestedLabel suggestedLabel;
	private UpstreamLabel upstreamLabel;

	/**
	 * Log
	 */
		
	private Logger log;
	
	/**
	 * Constructor to be used when a new RSVP-TE Path Message wanted to be sent
	 */
	
	public RSVPTEPathMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_PATH;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log = Logger.getLogger("ROADM");
		
		log.finest("RSVP-TE Path Message Created");
				
	}
	
	/**
	 * Constructor to be used when an RSVP-TE Path Message wanted to be decoded
	 * @param bytes
	 * @param length
	 */
	
	public RSVPTEPathMessage(byte[] bytes, int length){
		
		this.bytes = bytes;
		this.length = length;
		
		policyData = new LinkedList<PolicyData>();
		senderDescriptors = new LinkedList<SenderDescriptor>();
		
		log = Logger.getLogger("ROADM");
	
		log.finest("RSVP-TE Path Message Created");
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
		log.finest("RSVP-TE Path Message Header encoded");
		
	}

	/**
	 * @throws RSVPProtocolViolationException
	 */
	
	public void encode() throws RSVPProtocolViolationException{

		log.finest("Starting RSVP-TE Path Message encode");
		
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
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
		if(timeValues != null){
			
			length = length + timeValues.getLength();
			log.finest("Time Values RSVP Object found");

			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Time Values RSVP Object NOT found");
			throw new RSVPProtocolViolationException();			
			
		}
		if(ero != null){
			
			// Campo Opcional
			ero.encode();
			length = length + ero.getLength();
			log.finest("ERO RSVP Object found");
			
		}
		if(labelRequest != null){
			
			length = length + labelRequest.getLength();
			log.finest("Label RSVP Object found");

		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.severe("Label RSVP Object NOT found");
			throw new RSVPProtocolViolationException();		
			
		}
		if(sessionAttribute != null){
			
			// Campo Opcional
			length = length + sessionAttribute.getLength();
			log.finest("Session Attribute RSVP Object found");

		}
		
		
		int pdSize = policyData.size();
		
			
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			length = length + pd.getLength();
			log.finest("Policy Data RSVP Object found");
				
		}
						
		
		int sdSize = senderDescriptors.size();

		for(int i = 0; i < sdSize; i++){
			
			SenderDescriptor sd = (SenderDescriptor) senderDescriptors.get(i);
			length = length + sd.getLength();
			log.finest("Sender Descriptor RSVP Construct found");

		}
		
		if(this.suggestedLabel != null){
			
			// Campo Opcional
			length = length + this.suggestedLabel.getLength();
			log.finest("Suggested Label RSVP Object found");

		}
		
		if(this.upstreamLabel != null){
			
			// Campo Opcional
			length = length + upstreamLabel.getLength();
			log.finest("Upstream Label RSVP Object found");

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
;

		// Campo Obligatorio
		timeValues.encode();
		System.arraycopy(timeValues.getBytes(), 0, bytes, currentIndex, timeValues.getLength());
		currentIndex = currentIndex + timeValues.getLength();

		
		if(ero != null){
			
			// Campo Opcional
			System.arraycopy(ero.getBytes(), 0, bytes, currentIndex, ero.getLength());
			currentIndex = currentIndex + ero.getLength();
			
		}
		
		// Campo Obligatorio
		labelRequest.encode();
		System.arraycopy(labelRequest.getBytes(), 0, bytes, currentIndex, labelRequest.getLength());
		currentIndex = currentIndex + labelRequest.getLength();
		
		if(sessionAttribute != null){
			
			// Campo Opcional
			sessionAttribute.encode();
			System.arraycopy(sessionAttribute.getBytes(), 0, bytes, currentIndex, sessionAttribute.getLength());
			currentIndex = currentIndex + sessionAttribute.getLength();
			

		}	

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
				
				log.severe("Errors during Sender Descriptor number "+i+" encoding");
				
			}
						
		}
	
		if(this.suggestedLabel != null){
			
			// Campo Opcional
			suggestedLabel.encode();
			System.arraycopy(suggestedLabel.getBytes(), 0, bytes, currentIndex, suggestedLabel.getLength());
			currentIndex = currentIndex + suggestedLabel.getLength();

		}
		
		if(this.upstreamLabel != null){
			
			// Campo Opcional
			upstreamLabel.encode();
			System.arraycopy(upstreamLabel.getBytes(), 0, bytes, currentIndex, upstreamLabel.getLength());
			currentIndex = currentIndex + upstreamLabel.getLength();

		}
		
		
		calculateChecksum();

		
		log.finest("RSVP-TE Path Message encoding accomplished");
		
	}

	/**
	 * @throws RSVPProtocolViolationException
	 */
	
	public void decode() throws RSVPProtocolViolationException {
		decodeHeader();
		log.finest("RSVP-TE Path Message Decode started");
		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		while(offset < length){		// Mientras quede mensaje
			
			int classNum = RSVPObject.getClassNum(bytes,offset);
			if(classNum == 1){
				
				// Session Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 7){
					
					// LSPTunnelSession IPv4
					session = new SessionLSPTunnelIPv4(bytes,offset);
					session.decode(bytes, offset);
					
					offset = offset + session.getLength();
					
				}else if(cType == 8){
					
					// LSPTunnelSession IPv6
					session = new SessionLSPTunnelIPv6(bytes,offset);
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
			}else if(classNum == 20){
				
				// ERO Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					ero = new ERO(bytes, offset);			
					offset = offset + ero.getLength();
										
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}				
			}
			else if(classNum == 19){
				// Label Request Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					// LabelRequestWOLabelRange
					labelRequest = new LabelRequestWOLabelRange(bytes,offset);
					labelRequest.decode(bytes, offset);
					offset = offset + labelRequest.getLength();
					
				}else if(cType == 2){
					// LabelRequestWATMLabelRange
					labelRequest = new LabelRequestWATMLabelRange(bytes,offset);
					labelRequest.decode(bytes, offset);
					offset = offset + labelRequest.getLength();
								
				}else if(cType == 2){
					// LabelRequestWFrameRelayLabelRange
					labelRequest = new LabelRequestWFrameRelayLabelRange(bytes,offset);
					labelRequest.decode(bytes, offset);
					offset = offset + labelRequest.getLength();
								
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == 207){
				
				// Session Attribute Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 7){
					// Session Atribute WO Resource Affinities
					sessionAttribute = new SessionAttributeWOResourceAffinities(bytes,offset);
					sessionAttribute.decode(bytes, offset);
					offset = offset + sessionAttribute.getLength();
					
				}else if(cType == 1){
					// Session Atribute With Resource Affinities
					sessionAttribute = new SessionAttributeWResourceAffinities(bytes,offset);
					sessionAttribute.decode(bytes, offset);
					offset = offset + sessionAttribute.getLength();
								
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
		log.finest("RSVP-TE Path Message decoding accomplished");
	}
	
	
	/**
	 * 
	 * @param senderDescriptor
	 */
	
	public void addSenderDescriptor(SenderDescriptor senderDescriptor){
		
		senderDescriptors.add(senderDescriptor);

	}

		
	
	// GETTERS AND SETTERS

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ERO getEro() {
		return ero;
	}

	public void setEro(ERO ero) {
		this.ero = ero;
	}

	public LabelRequest getLabelRequest() {
		return labelRequest;
	}

	public void setLabelRequest(LabelRequest labelRequest) {
		this.labelRequest = labelRequest;
	}

	public SessionAttribute getSessionAttribute() {
		return sessionAttribute;
	}

	public void setSessionAttribute(SessionAttribute sessionAttribute) {
		this.sessionAttribute = sessionAttribute;
	}

	public SuggestedLabel getSuggestedLabel() {
		return suggestedLabel;
	}

	public void setSuggestedLabel(SuggestedLabel suggestedLabel) {
		this.suggestedLabel = suggestedLabel;
	}

	public UpstreamLabel getUpstreamLabel() {
		return upstreamLabel;
	}

	public void setUpstreamLabel(UpstreamLabel upstreamLabel) {
		this.upstreamLabel = upstreamLabel;
	}
}
