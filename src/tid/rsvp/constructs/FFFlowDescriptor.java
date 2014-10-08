package tid.rsvp.constructs;

import java.util.logging.Logger;
import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.objects.FilterSpec;
import tid.rsvp.objects.FilterSpecIPv4;
import tid.rsvp.objects.FilterSpecIPv6;
import tid.rsvp.objects.FlowLabelFilterSpecIPv6;
import tid.rsvp.objects.FlowSpec;
import tid.rsvp.objects.RSVPObject;
import tid.rsvp.objects.RSVPObjectParameters;


/**
 * 
 * <p>Represents a FF Style Flow Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <UL TYPE="CIRCLE">
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
 * <UL TYPE="CIRCLE">
 * <LI>FF style: &lt;flow descriptor list&gt; ::= &lt;FLOWSPEC&gt; &lt;FILTER_SPEC&gt;  |
 * &lt;Flow descriptor list&gt; &lt;FF flow descriptor&gt;
 * <LI>&lt;FF flow descriptor&gt; ::= [ &lt;FLOWSPEC&gt; ] &lt;FILTER_SPEC&gt;
 * <p>Each elementary FF style request is defined by a single
 * (FLOWSPEC, FILTER_SPEC) pair, and multiple such requests may be packed
 * into the flow descriptor list of a single Resv message. A FLOWSPEC
 * object can be omitted if it is identical to the most recent such object
 * that appeared in the list; the first FF flow descriptor must contain a
 * FLOWSPEC.</p>
 * </UL>
 * @author Fernando Mu�oz del Nuevo
 *
 */

public class FFFlowDescriptor extends FlowDescriptor {

	/**
	 * Flow Spec is a mandatory object when FF style is used and it is its first appearance
	 * in the flow descriptor list
	 */
	
	protected FlowSpec flowSpec;
	
	/**
	 * Filter Spec is a mandatory object when FF style is used
	 */
	
	protected FilterSpec filterSpec;
	
	
	/**
	 * Log
	 */
	
	private Logger log;
	
	/**
	 * First attribute will be true in case of creating the first flow descriptor of the list
	 * It will be false if not.
	 */
	
	private boolean first;
	
	/**
	 * Constructor to be used when a FF Flow Descriptor has been received and it is wanted to decode it
	 * @param first
	 */
	
	public FFFlowDescriptor(boolean first) {
		
		log = Logger.getLogger("ROADM");
		
		this.first = first;
		log.finest("FF Flow Descriptor Created");
		
	}
	
	/**
	 * Constructor to be used when a new FF Flow Descriptor it wanted to be created and sent 
	 * @param flowSpec
	 * @param filterSpec
	 * @param first
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public FFFlowDescriptor(FlowSpec flowSpec, FilterSpec filterSpec, boolean first) throws RSVPProtocolViolationException{
		
		log = Logger.getLogger("ROADM");
	
		
		this.first = first;
		
		if(flowSpec != null){
		
			this.length = this.length + flowSpec.getLength();
			this.flowSpec = flowSpec;
			log.finest("Flow Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(filterSpec != null){
			
			this.length = this.length + filterSpec.getLength();
			this.filterSpec = filterSpec;
			log.finest("Filter Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Filter Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		log.finest("FF Flow Descriptor Created");
		
	}
	
	/**
	 * 
	 * FF Flow Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.finest("Starting WF Flow Descriptor Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		
		if(flowSpec != null){
			// Campo s�lo obligatorio en el primer FFlowDescriptor
			flowSpec.encode();
			System.arraycopy(flowSpec.getBytes(), 0, bytes, offset, flowSpec.getLength());
			offset = offset + flowSpec.getLength();
		}else{
			if(first){
				// Campo obligatorio
				log.severe("Mandatory field Flow Spec not found");
				throw new RSVPProtocolViolationException();
				
			}			
		}
		if(filterSpec != null){
			// Campo obligatorio
			filterSpec.encode();
			System.arraycopy(filterSpec.getBytes(), 0, bytes, offset, filterSpec.getLength());
			offset = offset + filterSpec.getLength();
			
		}else{
			
			log.severe("Mandatory field Filter Spec not found");
			throw new RSVPProtocolViolationException();
			
		}
		
		log.finest("Encoding FF Flow Descriptor Accomplished");
		
	}

	/**
	 * 
	 * FF Flow Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.finest("FF Flow Descriptor Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FLOW_SPEC){
			
			if(cType == 2){		// Tipo Correcto
				
				flowSpec = new FlowSpec(bytes,offset);
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.severe("Malformed Flow Spec cType field");
				throw new RSVPProtocolViolationException();
				
			}
			flowSpec.decode(bytes,offset);
			offset = offset + flowSpec.getLength();
			length = length + flowSpec.getLength();
			bytesLeft = bytesLeft - flowSpec.getLength();
			log.finest("Sender Template decoded");
			
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
					log.severe("Malformed Filter Spec cType field");
					throw new RSVPProtocolViolationException();
					
				}
				log.finest("Flow Template decoded");
			}else{	
				// Campo obligatorio, por lo tanto se lanza excepcion si no existe
				log.severe("Flow Spec not found, It is mandatory");
				throw new RSVPProtocolViolationException();
				
			}
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Flow Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
		}
				
		this.setLength(length);
		log.finest("Decoding FF Flow Descriptor Accomplished");
		

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

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	
}
