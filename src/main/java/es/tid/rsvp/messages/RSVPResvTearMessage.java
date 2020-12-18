package es.tid.rsvp.messages;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.FFFlowDescriptor;
import es.tid.rsvp.constructs.FlowDescriptor;
import es.tid.rsvp.constructs.SEFlowDescriptor;
import es.tid.rsvp.constructs.WFFlowDescriptor;
import es.tid.rsvp.objects.Integrity;
import es.tid.rsvp.objects.RSVPHop;
import es.tid.rsvp.objects.RSVPHopIPv4;
import es.tid.rsvp.objects.RSVPHopIPv6;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import es.tid.rsvp.objects.Scope;
import es.tid.rsvp.objects.ScopeIPv4;
import es.tid.rsvp.objects.ScopeIPv6;
import es.tid.rsvp.objects.Session;
import es.tid.rsvp.objects.SessionIPv4;
import es.tid.rsvp.objects.SessionIPv6;
import es.tid.rsvp.objects.Style;
import org.slf4j.LoggerFactory;

/**
 * 	RFC 2205   RSVP		Resv Teardown Message.

         Receipt of a ResvTear (reservation teardown) message deletes
         matching reservation state.  Matching reservation state must
         match the SESSION, STYLE, and FILTER_SPEC objects as well as
         the LIH in the RSVP_HOP object.  If there is no matching
         reservation state, a ResvTear message should be discarded.  A
         ResvTear message may tear down any subset of the filter specs
         in FF-style or SE-style reservation state.

         ResvTear messages are initiated explicitly by receivers or by
         any node in which reservation state has timed out, and they
         travel upstream towards all matching senders.

         A ResvTear message must be routed like the corresponding Resv
         message, and its IP destination address will be the unicast
         address of a previous hop.
{@code
             <ResvTear Message> ::= <Common Header> [<INTEGRITY>]

                                         <SESSION> <RSVP_HOP>

                                         [ <SCOPE> ] <STYLE>

                                         <flow descriptor list>

             <flow descriptor list> ::= (see earlier definition)

}

         FLOWSPEC objects in the flow descriptor list of a ResvTear
         message will be ignored and may be omitted.  The order
         requirements for INTEGRITY object, sender descriptor, STYLE
         object, and flow descriptor list are as given earlier for a
         Resv message, but the above order is recommended.  A ResvTear
         message may include a SCOPE object, but it must be ignored.

         A ResvTear message will cease to be forwarded at the node where
         merging would have suppressed forwarding of the corresponding
         Resv message.  Depending upon the resulting state change in a
         node, receipt of a ResvTear message may cause a ResvTear
         message to be forwarded, a modified Resv message to be
         forwarded, or no message to be forwarded.  These three cases
         can be illustrated in the case of the FF-style reservations
         shown in Figure 6.

         o    If receiver R2 sends a ResvTear message for its
              reservation S3{B}, the corresponding reservation is
              removed from interface (d) and a ResvTear for S3{B} is
              forwarded out (b).

         o    If receiver R1 sends a ResvTear for its reservation
              S1{4B}, the corresponding reservation is removed from
              interface (c) and a modified Resv message FF( S1{3B} ) is
              immediately forwarded out (a).

         o    If receiver R3 sends a ResvTear message for S1{B}, there
              is no change in the effective reservation S1{3B} on (d)
              and no message is forwarded.


 */


public class RSVPResvTearMessage extends RSVPMessage {

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
	private RSVPHop rsvpHop;
	private Scope scope;
	private Style style;
	private LinkedList<FlowDescriptor> flowDescriptors;

	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	public RSVPResvTearMessage(){
		
		vers = 0x01;
		flags = 0x00;
		msgType = RSVPMessageTypes.MESSAGE_RESVTEAR;
		rsvpChecksum = 0xFF;
		sendTTL = 0x00;
		reserved = 0x00;
		length = es.tid.rsvp.messages.RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		flowDescriptors = new LinkedList<FlowDescriptor>();
		
		log.debug("RSVP Resv Message Created");
		
	}
	
	
	/**
	 * 
	 * @param bytes bytes 
	 * @param length length 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public RSVPResvTearMessage(byte[] bytes, int length) throws RSVPProtocolViolationException{
		
		super(bytes);
		decode();
		
		
		log.debug("RSVP Path Message Created");
	}
	

	/**
	 * 
	 */
	
	public void encode() throws RSVPProtocolViolationException{
		log.debug("Starting RSVP Resv TearDown Message encode");
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
			
		}
		if(rsvpHop != null){
			rsvpHop.encode();
			length = length + rsvpHop.getLength();
			log.debug("Hop RSVP Object found");
			
		}else{
			
			// Campo Obligatorio, si no existe hay fallo
			log.error("Hop RSVP Object NOT found");
			throw new RSVPProtocolViolationException();
			
		}
		if(scope != null){
			scope.encode();
			length = length + scope.getLength();
			log.debug("Scope RSVP Object found");
			
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
}catch(RSVPProtocolViolationException e){
				
				log.error("Errors during Flow Descriptor number " + i + " encoding");
				
			}
			length = length + fd.getLength();
			log.debug("Sender Descriptor RSVP Construct found");
			
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
		
		System.arraycopy(rsvpHop.getBytes(), 0, bytes, currentIndex, rsvpHop.getLength());
		currentIndex = currentIndex + rsvpHop.getLength();
		if(scope != null){
			
			//Campo Opcional
			
			System.arraycopy(scope.getBytes(), 0, bytes, currentIndex, scope.getLength());
			currentIndex = currentIndex + scope.getLength();
			
		}
		// Campo Obligatorio
		
		System.arraycopy(style.getBytes(), 0, bytes, currentIndex, style.getLength());
		currentIndex = currentIndex + style.getLength();
		
		// Lista de Flow Descriptors
		for(int i = 0; i < fdSize; i++){
			
			FlowDescriptor fd = (FlowDescriptor) flowDescriptors.get(i);
			
				System.arraycopy(fd.getBytes(), 0, bytes, currentIndex, fd.getLength());
				currentIndex = currentIndex + fd.getLength();

			
						
		}
	
		log.debug("RSVP Resv Message encoding accomplished");
	}

	/**
	 * 
	 */
	
	public void decode() throws RSVPProtocolViolationException {

		decodeHeader();
		flowDescriptors = new LinkedList<FlowDescriptor>();

		int offset = RSVPMessageTypes.RSVP_MESSAGE_HEADER_LENGTH;
		while(offset < length){		// Mientras quede mensaje
			
			int classNum = RSVPObject.getClassNum(bytes,offset);

			if(classNum == RSVPObjectParameters. RSVP_OBJECT_CLASS_SESSION ){
				
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
			}
			else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_RSVP_HOP){
				
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
			else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_INTEGRITY){
				
				// Integrity Object
				int cType = RSVPObject.getcType(bytes,offset);
				if(cType == 1){
					
					integrity = new Integrity(bytes, offset);
					offset = offset + integrity.getLength();
					
				}else{
					
					// Fallo en cType
					throw new RSVPProtocolViolationException();
					
				}
			}else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SCOPE){
				
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
			}
			else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_STYLE){
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
			}else if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FLOW_SPEC){
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
					log.error("Malformed RSVP Resv Message, Style Object not Found");
					throw new RSVPProtocolViolationException();
					
				}
				
				
			}	
			else{
				
				// Fallo en classNum
				log.error("Malformed RSVP Resv Message, Object classNum incorrect");
				throw new RSVPProtocolViolationException();
				
			}
			
		}
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


	public Scope getScope() {
		return scope;
	}


	public void setScope(Scope scope) {
		this.scope = scope;
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