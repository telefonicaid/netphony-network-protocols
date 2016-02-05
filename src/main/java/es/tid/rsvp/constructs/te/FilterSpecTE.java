package es.tid.rsvp.constructs.te;

import java.util.logging.Level;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.RSVPConstruct;
import es.tid.rsvp.objects.FilterSpec;
import es.tid.rsvp.objects.FilterSpecLSPTunnelIPv4;
import es.tid.rsvp.objects.FilterSpecLSPTunnelIPv6;
import es.tid.rsvp.objects.Label;
import es.tid.rsvp.objects.RRO;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import org.slf4j.LoggerFactory;


/**
 * 

      <SE filter spec> ::=     <FILTER_SPEC> <LABEL> [ <RECORD_ROUTE> ]

      Note:  LABEL and RECORD_ROUTE (if present), are bound to the
             preceding FILTER_SPEC.  No more than one LABEL and/or
             RECORD_ROUTE may follow each FILTER_SPEC.

  

 * @author Fernando Mu�oz del Nuevo fmn@tid.es
 *
 */

public class FilterSpecTE extends RSVPConstruct {

	/**
	 * Filter Spec is a mandatory object when SE style is used
	 */
	
	private FilterSpec filterSpec;
	
	/**
	 * Label is a mandatory object when SE style is used
	 */
	
	private Label label;
	
	/**
	 * RRO is an optional object when SE style is used
	 */
	
	private RRO rro;
	
	/**
	 * Log
	 */
  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a FilterSpec TE is received and it is wanted to decode it
	 */
	
	public FilterSpecTE(){
		
		log.debug("SE Filter Spec Created");
		
	}
	
	/**
	 * Constructor to be used when a new FilterSpec TE is wanted to be created and sent 
	 * @param filterSpec
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public FilterSpecTE(FilterSpec filterSpec, Label label, RRO rro) throws RSVPProtocolViolationException{
		
		if(filterSpec != null){
		
			this.length = this.length + filterSpec.getLength();
			this.filterSpec = filterSpec;
			log.debug("Filter Spec found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Filter Spec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(label != null){
			
			this.length = this.length + label.getLength();
			this.label = label;
			log.debug("Label found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Label not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(rro != null){
			
			this.length = this.length + rro.getLength();
			this.rro = rro;
			log.debug("RRO found");
			
		}

		log.debug("FilterSpec Created");
		
	}
	
	/**
	 * 
	 * Filter Spec Construct encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting Filter Spec Construct Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		if(filterSpec != null){
			// Campo obligatorio
			filterSpec.encode();
			System.arraycopy(filterSpec.getBytes(), 0, bytes, offset, filterSpec.getLength());
			offset = offset + filterSpec.getLength();
		}else{
			
			log.error("Mandatory field Filter Spec not found");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(label != null){
			// Campo obligatorio
			label.encode();
			System.arraycopy(label.getBytes(), 0, bytes, offset, label.getLength());
			offset = offset + label.getLength();
		}else{
			
			log.error("Mandatory field Label not found");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(rro != null){
			// Campo obligatorio
			rro.encode();
			System.arraycopy(rro.getBytes(), 0, bytes, offset, rro.getLength());
			offset = offset + rro.getLength();
		}
		
		log.debug("Encoding Filter Spec Construct Accomplished");
		
	}

	/**
	 * 
	 * Filter Spec Construct decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("Filter Spec Construct Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_FILTER_SPEC){
			
			if(cType == 7){		// Filter Spec LSP Tunnel IPv4
				
				filterSpec = new FilterSpecLSPTunnelIPv4(bytes, offset);
				
			}
			else if(cType == 8){
			
				filterSpec = new FilterSpecLSPTunnelIPv6(bytes, offset);
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.error("Malformed Filter Spec cType field");
				throw new RSVPProtocolViolationException();
				
			}
			filterSpec.decode(bytes,offset);
			offset = offset + filterSpec.getLength();
			length = length + filterSpec.getLength();
			bytesLeft = bytesLeft - filterSpec.getLength();
			log.debug("Filter Spec decoded");
			
		}else{
			
			// No se ha formado correctamente el objeto sender template
			log.error("Malformed Filter Spec, Filter Spec object not found");
			throw new RSVPProtocolViolationException();
			
		}
		classNum = RSVPObject.getClassNum(bytes,offset);
		cType = RSVPObject.getcType(bytes,offset);
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_LABEL){
			
			if(cType == 1){		// LAbel
				
				label = new Label(bytes, offset);
				label.decode(bytes, offset);
				offset = offset + label.getLength();
				length = length + label.getLength();
				bytesLeft = bytesLeft - label.getLength();
				
			}else{
				
				// No se ha formado correctamente el objeto Filter Spec
				log.error("Malformed Label cType field");
				throw new RSVPProtocolViolationException();
				
			}
			log.debug("Label decoded");
		}else{
			
			// No se ha formado correctamente el objeto sender template
			log.error("Malformed Filter Spec, Label object not found");
			throw new RSVPProtocolViolationException();
			
		}
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_RRO){
			
			if(cType == 1){		// LAbel
				
				rro = new RRO(bytes, offset);
				rro.decode(bytes, offset);
				offset = offset + rro.getLength();
				length = length + rro.getLength();
				bytesLeft = bytesLeft - rro.getLength();
				
			}else{
				
				// No se ha formado correctamente el objeto Filter Spec
				log.error("Malformed RRO cType field");
				throw new RSVPProtocolViolationException();
				
			}
			log.debug("RRO decoded");
		}
				
		this.setLength(length);
		log.debug("Decoding Filter Spec Accomplished");
		

	}

	// Getters and Setters
	
	public FilterSpec getFilterSpec() {
		return filterSpec;
	}

	public void setFilterSpec(FilterSpec filterSpec) {
		this.filterSpec = filterSpec;
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

	
	
}
