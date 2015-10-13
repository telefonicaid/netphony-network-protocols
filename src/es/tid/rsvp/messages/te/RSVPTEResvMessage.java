package es.tid.rsvp.messages.te;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.FFFlowDescriptor;
import es.tid.rsvp.constructs.FlowDescriptor;
import es.tid.rsvp.constructs.SEFlowDescriptor;
import es.tid.rsvp.constructs.te.FFFlowDescriptorTE;
import es.tid.rsvp.constructs.te.SEFlowDescriptorTE;
import es.tid.rsvp.messages.RSVPMessageTypes;
import es.tid.rsvp.messages.RSVPResvMessage;
import es.tid.rsvp.objects.*;
import org.slf4j.LoggerFactory;

/**

<p>RFC 3209  RSVP-TE	Reservation Message</p>

<p>The format of the Resv message is as follows:

      <Resv Message> ::=       <Common Header> [ <INTEGRITY> ]
                               <SESSION>  <RSVP_HOP>
                               <TIME_VALUES>
                               [ <RESV_CONFIRM> ]  [ <SCOPE> ]
                               [ <POLICY_DATA> ... ]
                               <STYLE> <flow descriptor list>

      <flow descriptor list> ::= <FF flow descriptor list>
                               | <SE flow descriptor>

      <FF flow descriptor list> ::= <FLOWSPEC> <FILTER_SPEC>
                               <LABEL> [ <RECORD_ROUTE> ]
                               | <FF flow descriptor list>
                               <FF flow descriptor>

      <FF flow descriptor> ::= [ <FLOWSPEC> ] <FILTER_SPEC> <LABEL>
                               [ <RECORD_ROUTE> ]

      <SE flow descriptor> ::= <FLOWSPEC> <SE filter spec list>

      <SE filter spec list> ::= <SE filter spec>
                               | <SE filter spec list> <SE filter spec>

      <SE filter spec> ::=     <FILTER_SPEC> <LABEL> [ <RECORD_ROUTE> ]

      Note:  LABEL and RECORD_ROUTE (if present), are bound to the
             preceding FILTER_SPEC.  No more than one LABEL and/or
             RECORD_ROUTE may follow each FILTER_SPEC.</p>


 */

public class RSVPTEResvMessage extends RSVPResvMessage {

	/**
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
         
         The Resv message format is as follows:

           <Resv Message> ::= <Common Header> [ <INTEGRITY> ]

                                   <SESSION>  <RSVP_HOP>

                                   <TIME_VALUES>

                                   [ <RESV_CONFIRM> ]  [ <SCOPE> ]

                                   [ <POLICY_DATA> ... ]

                                   <STYLE> <flow descriptor list>

           <flow descriptor list> ::=  <empty> |

                            <flow descriptor list> <flow descriptor>
         
	 */
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new RSVP-TE Resv Message wanted to be sent
	 */
	
	public RSVPTEResvMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_RESV;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		policyData = new LinkedList<PolicyData>();
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log.debug("RSVP-TE Resv Message Created");
	}
	
	/**
	 * Constructor to be used when an RSVP-TE Resv Message wanted to be decoded
	 * @param bytes
	 * @param length
	 */
	
	public RSVPTEResvMessage(byte[] bytes, int length){
		
		this.bytes = bytes;
		this.length = length;
		policyData = new LinkedList<PolicyData>();
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log.debug("RSVP-TE Resv Message Created");
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
	public void encode() throws RSVPProtocolViolationException {

		log.debug("Starting RSVP-TE Resv Message encode");
		
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
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
		if(resvConfirm != null){
			length = length + resvConfirm.getLength();
			log.debug("ResvConfirm RSVP Object found");
			
		}
		if(scope != null){
			length = length + scope.getLength();
			log.debug("Scope RSVP Object found");
		}
		
		int pdSize = policyData.size();
				
		for(int i = 0; i < pdSize; i++){
			PolicyData pd = (PolicyData) policyData.get(i);
			length = length + pd.getLength();
			log.debug("Policy Data RSVP Object found");
				
		}					
		
		if(style != null){
			length = length + style.getLength();
			log.debug("Style RSVP Object found");
			
		}else{
			// Campo Obligatorio, si no existe hay fallo
			log.error("Style RSVP Object NOT found");
			throw new RSVPProtocolViolationException();			
		}
		
		int fdSize = flowDescriptors.size();

		for(int i = 0; i < fdSize; i++){
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);
			length = length + fd.getLength();
			log.debug("Flow Descriptor RSVP Construct found");
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
		// Campo Obligatorio
		timeValues.encode();
		System.arraycopy(timeValues.getBytes(), 0, bytes, currentIndex, timeValues.getLength());
		currentIndex = currentIndex + timeValues.getLength();
		
		if(resvConfirm != null){
			
			//Campo Opcional
			resvConfirm.encode();
			System.arraycopy(resvConfirm.getBytes(), 0, bytes, currentIndex, resvConfirm.getLength());
			currentIndex = currentIndex + resvConfirm.getLength();
			
		}
		if(scope != null){
			
			//Campo Opcional
			scope.encode();
			System.arraycopy(scope.getBytes(), 0, bytes, currentIndex, scope.getLength());
			currentIndex = currentIndex + scope.getLength();
			
		}
		// Campos Opcionales
		for(int i = 0; i < pdSize; i++){
				
			PolicyData pd = (PolicyData) policyData.get(i);
			pd.encode();
			System.arraycopy(pd.getBytes(), 0, bytes, currentIndex, pd.getLength());
			currentIndex = currentIndex + pd.getLength();
							
		}
	
		// Campo Obligatorio
		style.encode();
		System.arraycopy(style.getBytes(), 0, bytes, currentIndex, style.getLength());
		currentIndex = currentIndex + style.getLength();
		
		// Lista de Flow Descriptors
		for(int i = 0; i < fdSize; i++){
			
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);
			try{
				fd.encode();
				System.arraycopy(fd.getBytes(), 0, bytes, currentIndex, fd.getLength());
				currentIndex = currentIndex + fd.getLength();

			}catch(RSVPProtocolViolationException e){
				log.error("Errors during Flow Descriptor number " + i + " encoding");
			}
		}
		log.debug("RSVP-TE Resv Message encoding accomplished");
	}

	@Override
	public void decode() throws RSVPProtocolViolationException {
		decodeHeader();
		log.info("Entra en el decode de RSVPTEResvMessage");
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
					log.error("Error Deconding Time Values");
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}else if(classNum == 15){
				
				// Resv Confirm Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// ResvConfirm IPv4
					resvConfirm = new ResvConfirmIPv4();
					resvConfirm.decode(bytes, offset);
					offset = offset + resvConfirm.getLength();
					
				}else if(cType == 2){
					
					// ResvConfirm IPv6
					resvConfirm = new ResvConfirmIPv6();
					resvConfirm.decode(bytes, offset);
					offset = offset + resvConfirm.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
			}else if(classNum == 7){
				
				// Scope Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// Scope IPv4
					scope = new ScopeIPv4();
					scope.decode(bytes, offset);
					offset = offset + scope.getLength();
					
				}else if(cType == 2){
					
					// Scope IPv6
					scope = new ScopeIPv6();
					scope.decode(bytes, offset);
					offset = offset + scope.getLength();
					
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
			}
			else if(classNum == 8){
				
				// Style Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					style = new Style();
					style.decode(bytes, offset);
					offset = offset + style.getLength();
					
				}else{
					// Fallo en cType
					throw new RSVPProtocolViolationException();
				}				
			}else if(classNum == 9){
				
				// Flow Descriptor Construct
				if(style != null){	// Style debe haberse decodificado porque en caso
									// contrario el mensaje no se habra construido bien
					if(style.getOptionVector()==RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_FF_STYLE){
						// Decodifico el primero que es distinto en su formacion.
						FFFlowDescriptor fffd = new FFFlowDescriptorTE(true);
						fffd.decode(bytes, offset);
						offset = offset + fffd.getLength();
						flowDescriptors.add(fffd);
						while(offset < length){		// Mientras quede mensaje
							// Decodifico los siguientes
							FFFlowDescriptor fffd2 = new FFFlowDescriptorTE(false);
							fffd2.decode(bytes, offset);
							offset = offset + fffd2.getLength();
							flowDescriptors.add(fffd2);
						}
						
					}else if(style.getOptionVector()==RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_SE_STYLE){
						// Los Flow Descriptor SE son todos iguales
						while(offset < length){		// Mientras quede mensaje
							SEFlowDescriptor sefd = new SEFlowDescriptorTE();
							sefd.decode(bytes, offset);
							offset = offset + sefd.getLength();
							flowDescriptors.add(sefd);
						}
					}
				}else{
					
					// Malformed Resv Message
					log.error("Malformed RSVP-TE Resv Message, Style Object not Found");
					throw new RSVPProtocolViolationException();
				}
			}	
			else{
				// Fallo en classNum
				log.error("Malformed RSVP-TE Resv Message, Object classNum incorrect");
				throw new RSVPProtocolViolationException();
			}
		}
	}
}