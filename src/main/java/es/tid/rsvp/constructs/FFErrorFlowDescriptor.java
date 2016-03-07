package es.tid.rsvp.constructs;

import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.FilterSpec;
import es.tid.rsvp.objects.FilterSpecIPv4;
import es.tid.rsvp.objects.FilterSpecIPv6;
import es.tid.rsvp.objects.FlowLabelFilterSpecIPv6;
import es.tid.rsvp.objects.FlowSpec;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import org.slf4j.LoggerFactory;


/**
 * <p>Represents a FF Style Error Flow Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <UL TYPE="CIRCLE">
 * <LI>&lt;flow descriptor list&gt; ::=  &lt;empty&gt; | &lt;flow descriptor list&gt; &lt;flow descriptor&gt;
 * </UL>
 * <p>If the INTEGRITY object is present, it must immediately follow the
 * common header.  The STYLE object followed by the flow descriptor list
 * must occur at the end of the message, and objects within the flow
 * descriptor list must follow the BNF given below.  There are no other
 * requirements on transmission order, although the above order is
 * recommended.</p>
 * 
 * <p>The NHOP (i.e., the RSVP_HOP) object contains the IP address of the
 * interface through which the Resv message was sent and the LIH for the
 * logical interface on which the reservation is required.</p>
 * 
 * <p>The appearance of a RESV_CONFIRM object signals a request for a
 * reservation confirmation and carries the IP address of the receiver
 * to which the ResvConf should be sent.  Any number of POLICY_DATA
 * objects may appear.</p>
 * 
 * <p>The BNF above defines a flow descriptor list as simply a list of
 * flow descriptors.  The following style-dependent rules specify in more
 * detail the composition of a valid flow descriptor list for each of the
 * reservation styles.</p>
 * 
 * <UL TYPE="CIRCLE">
 * <LI>FF style: &lt;flow descriptor list&gt; ::= &lt;FLOWSPEC&gt; &lt;FILTER_SPEC&gt;  |
 * &lt;flow descriptor list&gt; &lt;FF flow descriptor&gt; &lt;FF flow descriptor&gt; ::=
 * [ &lt;FLOWSPEC&gt; ] &lt;FILTER_SPEC&gt;
 * <LI><p>Each elementary FF style request is defined by a single
 * (FLOWSPEC, FILTER_SPEC) pair, and multiple such requests may be packed
 * into the flow descriptor list of a single Resv message.  A FLOWSPEC
 * object can be omitted if it is identical to the most recent such
 * object that appeared in  the list; the first FF flow descriptor
 * must contain a FLOWSPEC.
 * </UL>
 * @author Fernando Munoz del Nuevo
 *
 */

public class FFErrorFlowDescriptor extends ErrorFlowDescriptor {

	/**
	 * Flow Spec is a mandatory object when FF style is used and it is its first appearance
	 * in the flow descriptor list
	 */
	
	private FlowSpec flowSpec;
	
	/**
	 * Filter Spec is a mandatory object when FF style is used
	 */
	
	private FilterSpec filterSpec;
	
	
	/**
	 * Log
	 */
	
	private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	
	/**
	 * Constructor to be used when a FF Error Flow Descriptor has been received and it is wanted to decode it
	 */
	
	public FFErrorFlowDescriptor() {
		
		log.debug("FF Error Flow Descriptor Created");
		
	}
	
	/**
	 * Constructor to be used when a new FF Error Flow Descriptor it wanted to be created and sent 
	 * @param flowSpec
	 * @param filterSpec
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public FFErrorFlowDescriptor(FlowSpec flowSpec, FilterSpec filterSpec) throws RSVPProtocolViolationException{
		

		if(flowSpec != null){
		
			this.length = this.length + flowSpec.getLength();
			this.flowSpec = flowSpec;
			log.debug("Flow Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(filterSpec != null){
			
			this.length = this.length + filterSpec.getLength();
			this.filterSpec = filterSpec;
			log.debug("Filter Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Filter Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		log.debug("FF Error Flow Descriptor Created");
		
	}
	
	/**
	 * 
	 * FF Error Flow Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting WF Error Flow Descriptor Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		
		if(flowSpec != null){
			// Campo s�lo obligatorio en el primer FFlowDescriptor
			flowSpec.encode();
			System.arraycopy(flowSpec.getBytes(), 0, bytes, offset, flowSpec.getLength());
			offset = offset + flowSpec.getLength();
		}else{
			// Campo obligatorio
			log.error("Mandatory field Flow Spec not found");
			throw new RSVPProtocolViolationException();
	
		}
		if(filterSpec != null){
			// Campo obligatorio
			filterSpec.encode();
			System.arraycopy(filterSpec.getBytes(), 0, bytes, offset, filterSpec.getLength());
			offset = offset + filterSpec.getLength();
			
		}else{
			
			log.error("Mandatory field Filter Spec not found");
			throw new RSVPProtocolViolationException();
			
		}
		
		log.debug("Encoding FF Error Flow Descriptor Accomplished");
		
	}

	/**
	 * 
	 * FF Error Flow Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("FF Error Flow Descriptor Decode");
		
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
			flowSpec.decode(bytes,offset);
			offset = offset + flowSpec.getLength();
			length = length + flowSpec.getLength();
			bytesLeft = bytesLeft - flowSpec.getLength();
			log.debug("Flow Spec decoded");
			
		}
		if(bytesLeft > 0){
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes,offset);
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FILTER_SPEC){
				
				if(cType == 1){		// FilterSpecIPv4
					
					filterSpec = new FilterSpecIPv4();
					filterSpec.decode(bytes,offset);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
					
				}else if(cType == 2){		// FilterSpecIPv6
					
					filterSpec = new FilterSpecIPv6();
					filterSpec.decode(bytes,offset);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
				
				}else if(cType == 3){		// FilterSpecIPv6
					
					filterSpec = new FlowLabelFilterSpecIPv6();
					filterSpec.decode(bytes,offset);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
				
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.error("Malformed Filter Spec cType field");
					throw new RSVPProtocolViolationException();
					
				}
				log.debug("Flow Template decoded");
			}else{	
				
				// Campo obligatorio, por lo tanto se lanza excepcion si no existe
				log.error("Flow Spec not found, It is mandatory");
				throw new RSVPProtocolViolationException();
				
			}
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
		}
				
		this.setLength(length);
		log.debug("Decoding FF Error Flow Descriptor Accomplished");
		

	}

	
	
	// Getters and Setters
	
	public FlowSpec getFlowSpec() {
		return flowSpec;
	}

	public void setFlowSpec(FlowSpec flowSpec) {
		this.flowSpec = flowSpec;
	}

	public FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public void setFilterSpec(FilterSpec filterSpec) {
		this.filterSpec = filterSpec;
	}

}
