package es.tid.rsvp.constructs;

import java.util.LinkedList;
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
 * <p>Represents a SE Style Flow Descriptor construct, as defined in RFC 2205.</p>
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
 * <LI>SE style: &lt;flow descriptor list&gt; ::= &lt;SE flow descriptor&gt;
 * &lt;SE flow descriptor&gt; ::= &lt;FLOWSPEC&gt; &lt;filter spec list&gt;
 * &lt;filter spec list&gt; ::=  &lt;FILTER_SPEC&gt; | &lt;filter spec list&gt; &lt;FILTER_SPEC&gt;
 * 
 * <p>The reservation scope, i.e., the set of senders towards which a particular reservation is to be forwarded (after merging), is
 * determined as follows:</p>
 * <LI>Explicit sender selection
 * <p>The reservation is forwarded to all senders whose SENDER_TEMPLATE
 * objects recorded in the path state match a FILTER_SPEC object in the
 * reservation.  This match must follow the rules of Section 3.2.</p>
 * </UL>
 * @author Fernando Munoz del Nuevo
 *
 */

public class SEFlowDescriptor extends FlowDescriptor {

	/**
	 * Flow Spec is a mandatory object when SE style is used and it is its first appearance
	 * in the flow descriptor list
	 */
	
	private FlowSpec flowSpec;
	
	/**
	 * Filter Spec is an optional object collection when SE style is used.
	 * It at least has to contain one Filter Spec Object
	 */
	
	private LinkedList<FilterSpec> filterSpecList;
	
	
	/**
	 * Log
	 */
	
	private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a SE Flow Descriptor is received and it is wanted to decode it
	 */
	
	public SEFlowDescriptor() {
		
		filterSpecList = new LinkedList<FilterSpec>();
		log.debug("FF Flow Descriptor Created");
		
	}
	
	/**
	 * Constructor to be used when a new SE Flow Descriptor it wanted to be created and sent 
	 * @param flowSpec Flow Spec
	 * @param filterSpec Filter Spec
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public SEFlowDescriptor(FlowSpec flowSpec, FilterSpec filterSpec) throws RSVPProtocolViolationException{
		
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
			filterSpecList = new LinkedList<FilterSpec>();
			filterSpecList.add(filterSpec);
			log.debug("Filter Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Filter Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}

		log.debug("SE Flow Descriptor Created");
		
	}
	
	/**
	 * Method to add new Filter Spec object to the filter Spec list
	 * @param filterSpec Filter Spec
	 */
	
	public void addFilterSpec(FilterSpec filterSpec){
		
		filterSpecList.add(filterSpec);
		this.length = this.length + filterSpec.getLength();
		
	}
		
	/**
	 * 
	 * SE Flow Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException Thrown when there is a problem with the encoding
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting SE Flow Descriptor Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		if(flowSpec != null){
			// Campo s�lo obligatorio en el primer FFlowDescriptor
			flowSpec.encode();
			System.arraycopy(flowSpec.getBytes(), 0, bytes, offset, flowSpec.getLength());
			offset = offset + flowSpec.getLength();
		}else{
			
			log.error("Mandatory field Flow Spec not found");
			throw new RSVPProtocolViolationException();
			
		}
		
		int fslSize = filterSpecList.size();
		
		if(fslSize >0){
			for(int i = 0; i < fslSize; i++){
				
								
				// Recorro todos los flow descriptor y los codifico
				
				FilterSpec fs = filterSpecList.get(i);
				fs.encode();
				System.arraycopy(fs.getBytes(), 0, bytes, offset, fs.getLength());
				offset = offset + fs.getLength();
				
			}
		}else{
			
			log.error("Mandatory field Filter Spec not found");
			throw new RSVPProtocolViolationException();
		}
		log.debug("Encoding SE Flow Descriptor Accomplished");
		
	}

	/**
	 * 
	 * SE Flow Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException Thrown when there is a problem with the decoding
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("SE Flow Descriptor Decode");
		
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
			
			// No se ha formado correctamente el objeto sender template
			log.error("Malformed SE Flow Descriptor, Flow Spec object not found");
			throw new RSVPProtocolViolationException();
			
		}
		classNum = RSVPObject.getClassNum(bytes,offset);
		cType = RSVPObject.getcType(bytes,offset);
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FILTER_SPEC){
			
			if(cType == 1){		// FilterSpecIPv4
				
				FilterSpec filterSpec = new FilterSpecIPv4(bytes,offset);
				filterSpecList.add(filterSpec);
				offset = offset + filterSpec.getLength();
				length = length + filterSpec.getLength();
				bytesLeft = bytesLeft - filterSpec.getLength();
				
			}else if(cType == 2){		// FilterSpecIPv6
				
				FilterSpec filterSpec = new FilterSpecIPv6(bytes,offset);
				filterSpecList.add(filterSpec);
				offset = offset + filterSpec.getLength();
				length = length + filterSpec.getLength();
				bytesLeft = bytesLeft - filterSpec.getLength(bytes,offset);
			
			}else if(cType == 3){		// FilterSpecIPv6
				
				FilterSpec filterSpec = new FlowLabelFilterSpecIPv6(bytes,offset);
				filterSpecList.add(filterSpec);
				offset = offset + filterSpec.getLength();
				length = length + filterSpec.getLength();
				bytesLeft = bytesLeft - filterSpec.getLength();
			
			}else{
				
				// No se ha formado correctamente el objeto Filter Spec
				log.error("Malformed Filter Spec cType field");
				throw new RSVPProtocolViolationException();
				
			}
			log.debug("Filter Spec decoded");
		}
		while(bytesLeft > 0){
			
			// Comprobamos la existencia de mas flow specs
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes,offset);
			
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FILTER_SPEC){
			
				// Hay un nuevo Filter Spec
				if(cType == 1){		// FilterSpecIPv4
					
					FilterSpec filterSpec = new FilterSpecIPv4(bytes,offset);
					filterSpecList.add(filterSpec);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
					
				}else if(cType == 2){		// FilterSpecIPv6
					
					FilterSpec filterSpec = new FilterSpecIPv6(bytes,offset);
					filterSpecList.add(filterSpec);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
				
				}else if(cType == 3){		// FilterSpecIPv6
					
					FilterSpec filterSpec = new FlowLabelFilterSpecIPv6(bytes,offset);
					filterSpecList.add(filterSpec);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
				
				}else{
					
					// No se ha formado correctamente el objeto Filter Spec
					log.error("Malformed Filter Spec cType field");
					throw new RSVPProtocolViolationException();
					
				}
				log.debug("Filter Spec decoded");
				
			}else{
				// Otro objeto diferente
				log.error("Filter Spec expected and not found");
				throw new RSVPProtocolViolationException();			
			}
		
		}
				
		this.setLength(length);
		log.debug("Decoding FF Flow Descriptor Accomplished");
		

	}

	// Getters and Setters
	
	public FlowSpec getFlowSpec() {
		return flowSpec;
	}

	public void setFlowSpec(FlowSpec flowSpec) {
		this.flowSpec = flowSpec;
	}

	public LinkedList<FilterSpec> getFilterSpecList() {
		return filterSpecList;
	}

	public void setFilterSpecList(LinkedList<FilterSpec> filterSpecList) {
		this.filterSpecList = filterSpecList;
	}
}
