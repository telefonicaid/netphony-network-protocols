package es.tid.rsvp.messages;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.FFFlowDescriptor;
import es.tid.rsvp.constructs.FlowDescriptor;
import es.tid.rsvp.constructs.SEFlowDescriptor;
import es.tid.rsvp.constructs.WFFlowDescriptor;
import es.tid.rsvp.objects.ErrorSpec;
import es.tid.rsvp.objects.ErrorSpecIPv4;
import es.tid.rsvp.objects.ErrorSpecIPv6;
import es.tid.rsvp.objects.Integrity;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.ResvConfirm;
import es.tid.rsvp.objects.ResvConfirmIPv4;
import es.tid.rsvp.objects.ResvConfirmIPv6;
import es.tid.rsvp.objects.Session;
import es.tid.rsvp.objects.SessionIPv4;
import es.tid.rsvp.objects.SessionIPv6;
import es.tid.rsvp.objects.Style;
import org.slf4j.LoggerFactory;


/**
 * Reservation Confirmation Message.

         ResvConf messages are sent to (probabilistically) acknowledge
         reservation requests.  A ResvConf message is sent as the result
         of the appearance of a RESV_CONFIRM object in a Resv message.




         A ResvConf message is sent to the unicast address of a receiver
         host; the address is obtained from the RESV_CONFIRM object.
         However, a ResvConf message is forwarded to the receiver hop-
         by-hop, to accommodate the hop-by-hop integrity check
         mechanism.
{@code
           <ResvConf message> ::= <Common Header> [ <INTEGRITY> ]

                                      <SESSION> <ERROR_SPEC>

                                      <RESV_CONFIRM>

                                      <STYLE> <flow descriptor list>

           <flow descriptor list> ::= (see earlier definition)
}

         The object order requirements are the same as those given
         earlier for a Resv message, but the above order is recommended.

         The RESV_CONFIRM object is a copy of that object in the Resv
         message that triggered the confirmation.  The ERROR_SPEC is
         used only to carry the IP address of the originating node, in
         the Error Node Address; the Error Code and Value are zero to
         indicate a confirmation.  The flow descriptor list specifies
         the particular reservations that are being confirmed; it may be
         a subset of flow descriptor list of the Resv that requested the
         confirmation.

 * @author fmn
 *
 */

public class RSVPResvConfMessage extends RSVPMessage {

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
         
	 */
	
	
	private Integrity integrity;
	private Session session;
	private ErrorSpec errorSpec;
	private ResvConfirm resvConfirm;
	private Style style;
	private LinkedList<FlowDescriptor> flowDescriptors;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor that has to be used in case of creating a new Resv Confirmation Message to
	 * be sent.
	 */
	
	public RSVPResvConfMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_RESVCONF;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log.debug("RSVP Resv Confirmation Message Created");
		
		
	}
	
	/**
	 * Constructor to be used in case of creating a new Resv Confirmation message to be decoded
	 * @param bytes bytes 
	 * @param length length 
	 * @throws RSVPProtocolViolationException Exception when decoding the message 
	 */
	
	public RSVPResvConfMessage(byte[] bytes, int length) throws RSVPProtocolViolationException{
		
		super(bytes);	
		decode();
		
		
		log.debug("RSVP Resv Confirmation Message Created");
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
		log.debug("Starting RSVP Resv Message encode");
		//FIXME: COMPUTE CHECKSUM!!
		rsvpChecksum = 0xFF;
		// Obtengo el tama�o de la cabecera comun
		int commonHeaderSize = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
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
			
		}
		if(errorSpec != null){
			errorSpec.encode();
			length = length + errorSpec.getLength();
			log.debug("Error Spec RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Error Spec RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		if(resvConfirm != null){
			resvConfirm.encode();
			length = length + resvConfirm.getLength();
			log.debug("ResvConfirm RSVP Object found");
			
		}else{
			// Campo Obligatorio, si no existe hay fallo
			log.error("Resv Confirm RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
		}
		if(style != null){
			style.encode();
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
			try{
			fd.encode();
			length = length + fd.getLength();
			log.debug("Flow Descriptor RSVP Construct found");
			}catch(RSVPProtocolViolationException e){
				
				log.error("Errors during Flow Descriptor number " + i + " encoding");
				
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
		// Campo Obligatorio
		
		System.arraycopy(resvConfirm.getBytes(), 0, bytes, currentIndex, resvConfirm.getLength());
		currentIndex = currentIndex + resvConfirm.getLength();
		// Campo Obligatorio
		
		System.arraycopy(style.getBytes(), 0, bytes, currentIndex, style.getLength());
		currentIndex = currentIndex + style.getLength();
		
		// Lista de Flow Descriptors
		for(int i = 0; i < fdSize; i++){
			
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);

				
				System.arraycopy(fd.getBytes(), 0, bytes, currentIndex, fd.getLength());
				currentIndex = currentIndex + fd.getLength();


						
		}
	
		log.debug("RSVP Resv Confirmation Message encoding accomplished");
		
	}

	@Override
	public void decode() throws RSVPProtocolViolationException {
		decodeHeader();
		flowDescriptors = new LinkedList<FlowDescriptor>();

		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			
			int classNum = RSVPObject.getClassNum(bytes,offset);
			if(classNum == 1){
				
				// Session Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// Session IPv4
					session = new SessionIPv4(bytes, offset);
					
					offset = offset + session.getLength();
					
				}else if(cType == 2){
					
					// Session IPv6
					session = new SessionIPv6(bytes, offset);
					offset = offset + session.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
			}else if(classNum == 6){
				
				// Error Spec Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					// Error Spec IPv4
					errorSpec = new ErrorSpecIPv4(bytes, offset);
					
					offset = offset + errorSpec.getLength();
					
				}else if(cType == 2){
					
					// Error Spec IPv6
					errorSpec = new ErrorSpecIPv6(bytes, offset);
					
					offset = offset + errorSpec.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}				
			}
			else if(classNum == 4){
				
				// Integrity Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					integrity = new Integrity(bytes, offset);
					offset = offset + integrity.getLength();
					
				}else{
					
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
					if(style.getOptionVector()==es.tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_FF_STYLE){
						
						// Decodifico el primero que es distinto en su formacion.
						FFFlowDescriptor fffd = new FFFlowDescriptor(true);
						fffd.decode(bytes, offset);
						offset = offset + fffd.getLength();
						flowDescriptors.add(fffd);
						while(offset < length){		// Mientras quede mensaje
						
							// Decodifico los siguientes
							FFFlowDescriptor fffd2 = new FFFlowDescriptor(true);
							fffd2.decode(bytes, offset);
							offset = offset + fffd2.getLength();
							flowDescriptors.add(fffd2);
						}
						
					}else if(style.getOptionVector()==es.tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_WF_STYLE){

						// Los Flow Descriptor WF son todos iguales
						while(offset < length){		// Mientras quede mensaje
							WFFlowDescriptor wffd = new WFFlowDescriptor();
							wffd.decode(bytes, offset);
							offset = offset + wffd.getLength();
							flowDescriptors.add(wffd);
						}
						
					}else if(style.getOptionVector()==es.tid.rsvp.objects.RSVPObjectParameters.RSVP_STYLE_OPTION_VECTOR_SE_STYLE){

						// Los Flow Descriptor SE son todos iguales
						while(offset < length){		// Mientras quede mensaje
							SEFlowDescriptor sefd = new SEFlowDescriptor();
							sefd.decode(bytes, offset);
							offset = offset + sefd.getLength();
							flowDescriptors.add(sefd);
						}
						
					}
					
				}else{
					
					// Malformed Resv Message
					log.error("Malformed RSVP Resv Confirmation Message, Style Object not Found");
					throw new RSVPProtocolViolationException();
					
				}
				
				
			}	
			else{
				
				// Fallo en classNum
				log.error("Malformed RSVP Resv Confirmation Message, Object classNum incorrect");
				throw new RSVPProtocolViolationException();
				
			}
			
		}
		
	}

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

	public ResvConfirm getResvConfirm() {
		return resvConfirm;
	}

	public void setResvConfirm(ResvConfirm resvConfirm) {
		this.resvConfirm = resvConfirm;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public LinkedList<FlowDescriptor> getFlowDescriptors() {
		return flowDescriptors;
	}

	public void setFlowDescriptors(LinkedList<FlowDescriptor> flowDescriptors) {
		this.flowDescriptors = flowDescriptors;
	}

}
