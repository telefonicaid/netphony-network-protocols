package es.tid.rsvp.constructs;

import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.FlowSpec;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import org.slf4j.LoggerFactory;


/**
 * <p>Represents a WF Style Error Flow Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <UL>
 * <LI>&lt;flow descriptor list&gt; ::=  &lt;empty&gt; | &lt;flow descriptor list&gt; &lt;flow descriptor&gt;
 * </UL>
 * 
 * <p>If the INTEGRITY object is present, it must immediately follow the
 * common header.  The STYLE object followed by the flow descriptor list
 * must occur at the end of the message, and objects within the flow
 * descriptor list must follow the BNF given below. There are no other
 * requirements on transmission order, although the above order is
 * recommended.</p>
 * 
 * <p>The NHOP (i.e., the RSVP_HOP) object contains the IP address of
 * the interface through which the Resv message was sent and the LIH for
 * the logical interface on which the reservation is required.</p>
 * 
 * <p>The appearance of a RESV_CONFIRM object signals a request for a
 * reservation confirmation and carries the IP address of the receiver
 * to which the ResvConf should be sent.  Any number of POLICY_DATA
 * objects may appear.</p>
 * 
 * <p>The BNF above defines a flow descriptor list as simply a list of
 * flow descriptors.  The following style-dependent rules specify in more
 * detail the composition of a valid flow descriptor list for each of
 * the reservation styles.</p>
 * 
 * <UL>
 * <LI>WF Style: &lt;flow descriptor list&gt; ::=  &lt;WF flow descriptor&gt;
 * &lt;WF flow descriptor&gt; ::= &lt;FLOWSPEC&gt;
 * </UL>
 * @author Fernando Munoz del Nuevo
 *
 */

public class WFErrorFlowDescriptor extends ErrorFlowDescriptor {

	/**
	 * 	Flow Spec is a mandatory object when WF style is used
	 */
	
	private FlowSpec flowSpec;
	
	/**
	 * Log
	 */
  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Builder to be used when a received WF Error Flow Descriptor and it is wanted to decode it
	 */
	
	public WFErrorFlowDescriptor() {
		
		log.debug("WF Flow Descriptor Created");
		
	}
	
	/**
	 * Builder to be used when a new WF Error Flow Descriptor it wanted to be created and sent 
	 * @param flowSpec Flow Spec
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public WFErrorFlowDescriptor(FlowSpec flowSpec) throws RSVPProtocolViolationException{
		
		if(flowSpec != null){
		
			this.length = this.length + flowSpec.getLength();
			this.flowSpec = flowSpec;
			log.debug("Flow Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		log.debug("WF Error Flow Descriptor Created");
		
	}
		
	/**
	 * 
	 * WF Error Flow Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException  Thrown when a mandatory field is not present
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting WF Error Flow Descriptor Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		// Campo Obligatorio
		flowSpec.encode();
		System.arraycopy(flowSpec.getBytes(), 0, bytes, offset, flowSpec.getLength());
		offset = offset + flowSpec.getLength();
			
		log.debug("Encoding WF Flow Descriptor Accomplished");
		
	}

	/**
	 * 
	 * Error Flow Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException  Thrown when there is a problem with the decoding
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("Starting Error Flow Descriptor Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FLOW_SPEC){
			
			if(cType == 2){		// Tipo Correcto
				
				flowSpec = new FlowSpec(bytes,offset);
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.error("Malformed Flow Spec cType field");
				throw new RSVPProtocolViolationException();
				
			}
			offset = offset + flowSpec.getLength();
			length = length + flowSpec.getLength();
			bytesLeft = bytesLeft - flowSpec.getLength();
			log.debug("Sender Template decoded");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
				
		this.setLength(length);
		log.debug("Decoding WF Flow Descriptor Accomplished");
		

	}
	
	// Getters and Setters
	

	public FlowSpec getFlowSpec() {
		return flowSpec;
	}

	public void setFlowSpec(FlowSpec flowSpec) {
		this.flowSpec = flowSpec;
	}

}
