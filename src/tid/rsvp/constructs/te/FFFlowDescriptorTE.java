package tid.rsvp.constructs.te;

import java.util.logging.Level;
import java.util.logging.Logger;

import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.constructs.FFFlowDescriptor;
import tid.rsvp.objects.FilterSpec;
import tid.rsvp.objects.FilterSpecLSPTunnelIPv4;
import tid.rsvp.objects.FilterSpecLSPTunnelIPv6;
import tid.rsvp.objects.FlowSpec;
import tid.rsvp.objects.Label;
import tid.rsvp.objects.RRO;
import tid.rsvp.objects.RSVPObject;
import tid.rsvp.objects.RSVPObjectParameters;
import tid.rsvp.objects.gmpls.GeneralizedLabel;


/**
 * 
            <flow descriptor list> ::=  <empty> |

                            <flow descriptor list> <flow descriptor>


         If the INTEGRITY object is present, it must immediately follow
         the common header.  The STYLE object followed by the flow
         descriptor list must occur at the end of the message, and
         objects within the flow descriptor list must follow the BNF
         given below.  There are no other requirements on transmission
         order, although the above order is recommended.

         The NHOP (i.e., the RSVP_HOP) object contains the IP address of
         the interface through which the Resv message was sent and the
         LIH for the logical interface on which the reservation is
         required.

         The appearance of a RESV_CONFIRM object signals a request for a
         reservation confirmation and carries the IP address of the
         receiver to which the ResvConf should be sent.  Any number of
         POLICY_DATA objects may appear.

         The BNF above defines a flow descriptor list as simply a list
         of flow descriptors.  The following style-dependent rules
         specify in more detail the composition of a valid flow
         descriptor list for each of the reservation styles.

         o    FF style:

                <flow descriptor list> ::=

                          <FLOWSPEC>  <FILTER_SPEC>  |

                          <flow descriptor list> <FF flow descriptor>

                <FF flow descriptor> ::=

                          [ <FLOWSPEC> ] <FILTER_SPEC>

              Each elementary FF style request is defined by a single
              (FLOWSPEC, FILTER_SPEC) pair, and multiple such requests
              may be packed into the flow descriptor list of a single
              Resv message.  A FLOWSPEC object can be omitted if it is
              identical to the most recent such object that appeared in
              the list; the first FF flow descriptor must contain a
              FLOWSPEC.

  

 * @author Fernando Mu�oz del Nuevo fmn@tid.es
 *
 */

public class FFFlowDescriptorTE extends FFFlowDescriptor {

	/**
	 * Label is a mandatory object when FF style is used and it is its first appearance
	 * in the flow descriptor list
	 */
	
	private Label label;
	
	/**
	 * RRO is an optional object when FF style is used
	 */
	
	private RRO rro;
	
	
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
	
	public FFFlowDescriptorTE(boolean first) {
		
		super(first);
		log = Logger.getLogger("ROADM");
		log.setLevel(Level.ALL);
		this.first = first;
		log.finest("FF Flow Descriptor Created");
		
	}
	
	/**
	 * Constructor to be used when a new FF Flow Descriptor TE is wanted to be created and sent 
	 * @param flowSpec
	 * @param filterSpec
	 * @param first
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public FFFlowDescriptorTE(FlowSpec flowSpec, FilterSpec filterSpec, Label label, RRO rro, boolean first) throws RSVPProtocolViolationException{
		
		super(first);
		
		log = Logger.getLogger("ROADM");
		log.setLevel(Level.ALL);
		this.first = first;
		
		if (first)
		
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
		if(label != null){
			this.length = this.length + label.getLength();
			this.label = label;
			log.finest("Label found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Label not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(rro != null){
			
			this.length = this.length + rro.getLength();
			this.rro = rro;
			log.finest("RRO found");
			
		}
				
		log.finest("FF Flow Descriptor TE Created");
	}
	
	/**
	 * 
	 * FF Flow Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.finest("Starting FF Flow Descriptor TE Encode");
		
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
		if(label != null){
			// Campo obligatorio
			label.encode();
			System.arraycopy(label.getBytes(), 0, bytes, offset, label.getLength());
			offset = offset + label.getLength();
			
		}else{
			log.severe("Mandatory field Label not found");
			throw new RSVPProtocolViolationException();
		}
		if(rro != null){

			rro.encode();
			System.arraycopy(rro.getBytes(), 0, bytes, offset, rro.getLength());
			offset = offset + rro.getLength();
			
		}		
		log.finest("Encoding FF Flow Descriptor Accomplished");
		
	}

	/**
	 * 
	 * FF Flow Descriptor TE decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.finest("FF Flow Descriptor TE Decode");
		
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
			offset = offset + flowSpec.getLength();
			length = length + flowSpec.getLength();
			bytesLeft = bytesLeft - flowSpec.getLength();
			log.finest("Flow Spec decoded");
		}
		if(bytesLeft > 0){
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes,offset);
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FILTER_SPEC){
				
				if(cType == 7){		// FilterSpecLSPTunnelIPv4
					
					filterSpec = new FilterSpecLSPTunnelIPv4(bytes,offset);
					filterSpec.decode(bytes,offset);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
					
				}else if(cType == 8){		// FilterSpecLSPTunnelIPv6
					
					filterSpec = new FilterSpecLSPTunnelIPv6(bytes,offset);
					filterSpec.decode(bytes,offset);
					offset = offset + filterSpec.getLength();
					length = length + filterSpec.getLength();
					bytesLeft = bytesLeft - filterSpec.getLength();
				
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.severe("Malformed Filter Spec cType field");
					throw new RSVPProtocolViolationException();
					
				}
				log.finest("Flow Spec decoded");
			}else{	
				
				// Campo obligatorio, por lo tanto se lanza excepcion si no existe
				log.severe("Filter Spec not found, It is mandatory");
				throw new RSVPProtocolViolationException();
				
			}
		}else{
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Filter Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
		}if(bytesLeft > 0){
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes,offset);
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_LABEL){
				
				if(cType == 1){		// LABEL
					
					label = new Label(bytes,offset);
					label.decode(bytes,offset);
					offset = offset + label.getLength();
					length = length + label.getLength();
					bytesLeft = bytesLeft - label.getLength();
					
				}else if (cType == 2){	//Generalized Label
					label = new GeneralizedLabel(bytes, offset);
					label.decode(bytes, offset);
					offset = offset + label.getLength();
					length = length + label.getLength();
					bytesLeft = bytesLeft - label.getLength();
				}else{
					// No se ha formado correctamente el objeto Label
					log.severe("Malformed Label cType field");
					throw new RSVPProtocolViolationException();
					
				}
				log.finest("Label decoded");
			}else{	
				// Campo obligatorio, por lo tanto se lanza excepcion si no existe
				log.severe("Label not found, It is mandatory");
				throw new RSVPProtocolViolationException();
				
			}
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Label not found, It is mandatory");
			throw new RSVPProtocolViolationException();
		}
		
		if(bytesLeft > 0){
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes, offset);
			
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_RRO){
				
				if(cType == 1){		// cType adecuado
					
					rro = new RRO();
					
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.severe("Malformed RRO cType field");
					throw new RSVPProtocolViolationException();
					
				}
				rro.decode(bytes,offset);
				offset = offset + rro.getLength();
				length = length + rro.getLength();
				log.finest("RRO decoded");
				
			}
			
		}
				
		this.setLength(length);
		log.finest("Decoding FF Flow Descriptor TE Accomplished");
	}

	// Getters and Setters
		
	public FlowSpec getFlowSpec() {
		return flowSpec;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public RRO getRro() {
		return rro;
	}

	public void setRro(RRO rro) {
		this.rro = rro;
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
