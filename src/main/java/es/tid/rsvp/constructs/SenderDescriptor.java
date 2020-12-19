package es.tid.rsvp.constructs;

import org.slf4j.Logger;

import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.FlowLabelSenderTemplateIPv6;
import es.tid.rsvp.objects.IntservADSPEC;
import es.tid.rsvp.objects.IntservSenderTSpec;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import es.tid.rsvp.objects.SSONSenderTSpec;
import es.tid.rsvp.objects.SenderTSpec;
import es.tid.rsvp.objects.SenderTemplate;
import es.tid.rsvp.objects.SenderTemplateIPv4;
import es.tid.rsvp.objects.SenderTemplateIPv6;
import org.slf4j.LoggerFactory;

/**
 * Sender Descriptor construct.
 * <p>Represents a Sender Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <p>The format of a Path message is as follows:</p>
 * 
 * <UL>
 * <LI> &lt;Path Message&gt; ::= &lt;Common Header&gt; [ &lt;INTEGRITY&gt; ]
 * &lt;SESSION&gt; &lt;RSVP_HOP&gt; &lt;TIME_VALUES&gt; [ &lt;POLICY_DATA&gt; ... ]
 * [ &lt;sender descriptor&gt; ]
 * <LI>&lt;sender descriptor&gt; ::= &lt;SENDER_TEMPLATE&gt; &lt;SENDER_TSPEC&gt;
 * [ &lt;ADSPEC&gt; ]
 * </UL>
 * @author Fernando Munoz del Nuevo
 *
 */

public class SenderDescriptor extends RSVPConstruct {

	/**
	 * 	Sender Template is a mandatory RSVP Object
	 */
	
	protected SenderTemplate senderTemplate;
	
	/**
	 * Sender TSPEC is a mandatory RSVP Object
	 */
	
	protected SenderTSpec senderTSPEC;
	
	/**
	 * ADSPEC is an optional RSVP Object 
	 */
	
	protected IntservADSPEC adspec;
	
	/**
	 * Log
	 */
		
	private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Builder to be used when a received Sender Descriptor and it is wanted to decode it
	 */
	
	public SenderDescriptor(){
		
		log.debug("Sender Descriptor Created");
		
	}
	
	/**
	 * Builder to be used when a new Sender Descriptor it wanted to be created and sent 
	 * @param senderTemplate Sender Template 
	 * @param senderTSPEC Sender TSPEC∫
	 * @param adspec	Optional, might be null
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public SenderDescriptor(SenderTemplate senderTemplate, IntservSenderTSpec senderTSPEC, IntservADSPEC adspec) throws RSVPProtocolViolationException{
		
		if(senderTemplate != null){
			
			this.length = this.length + senderTemplate.getLength();
			this.senderTemplate = senderTemplate;
			log.debug("Sender Template found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(senderTSPEC != null){
			
			this.length = this.length + senderTSPEC.getLength();
			this.senderTSPEC = senderTSPEC;
			log.debug("Intserv Sender TSpec found");
			
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Intserv Sender TSpec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(adspec != null){

			this.adspec = adspec;
			this.length = this.length + adspec.getLength();
			log.debug("Intserv ADSPEC found");
			
		}

		log.debug("Sender Descriptor Created");
		
	}
		
	/**
	 * 
	 * Sender Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException Thrown when a mandatory field is not set.
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		length=0;
		log.debug("Starting Sender Descriptor Encode");
		if(senderTemplate != null){
			senderTemplate.encode();
			this.length = this.length + senderTemplate.getLength();
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(senderTSPEC != null){
			senderTSPEC.encode();
			this.length = this.length + senderTSPEC.getLength();
			log.debug("Intserv Sender TSpec found");
			
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Intserv Sender TSpec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(adspec != null){
			adspec.encode();
			this.length = this.length + adspec.getLength();
			log.debug("Intserv ADSPEC found");
			
		}
		this.bytes = new byte[length];
		
		int offset=0;
		// Campo Obligatorio
		
		System.arraycopy(senderTemplate.getBytes(), 0, bytes, offset, senderTemplate.getLength());
		offset = offset + senderTemplate.getLength();
		// Campo Obligatorio
		
		System.arraycopy(senderTSPEC.getBytes(), 0, bytes, offset, senderTSPEC.getLength());
		offset = offset + senderTSPEC.getLength();
		if(adspec != null){
			// Campo Opcional
			
			System.arraycopy(adspec.getBytes(), 0, bytes, offset, adspec.getLength());
		}
		
		log.debug("Encoding Sender Descriptor Accomplished");
	}

	/**
	 * 
	 * Sender Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException  Thrown then there is a problem decoding message
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("Starting Sender Descriptor Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TEMPLATE){
			
			if(cType == 1){		// Tipo IPv4
				
				senderTemplate = new SenderTemplateIPv4(bytes,offset);
				
			}else if(cType == 2){
				
				senderTemplate = new SenderTemplateIPv6(bytes,offset);
				
			}else if(cType == 3){
				
				senderTemplate = new FlowLabelSenderTemplateIPv6(bytes,offset);
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.error("Malformed Sender Template cType field");
				throw new RSVPProtocolViolationException();
				
			}
			offset = offset + senderTemplate.getLength();
			length = length + senderTemplate.getLength();
			bytesLeft = bytesLeft - senderTemplate.getLength();
			log.debug("Sender Template decoded");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(bytesLeft <= 0){
			
			log.error("Incomplete Sender Descriptor");
			throw new RSVPProtocolViolationException();
			
		}
		
		classNum = RSVPObject.getClassNum(bytes,offset);
		cType = RSVPObject.getcType(bytes, offset);
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TSPEC){
			
			if(cType == 2){		// cType adecuado
				
				senderTSPEC = new IntservSenderTSpec();
				
			} else if (cType == 8){		// cType adecuado
				
				senderTSPEC = new SSONSenderTSpec(bytes,offset);
				
				
			}
			
			else{
				
				// No se ha formado correctamente el objeto sender template
				log.error("Malformed Sender TSPEC cType field");
				throw new RSVPProtocolViolationException();
				
			}
			offset = offset + senderTSPEC.getLength();
			length = length + senderTSPEC.getLength();
			bytesLeft = bytesLeft - senderTSPEC.getLength();
			log.debug("Sender TSPEC decoded");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.error("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(bytesLeft > 0){
		
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes, offset);
			
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_ADSPEC){
				
				if(cType == 2){		// cType adecuado
					
					adspec = new IntservADSPEC(bytes,offset);
					
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.error("Malformed ADSPEC cType field");
					throw new RSVPProtocolViolationException();
					
				}
				offset = offset + adspec.getLength();
				length = length + adspec.getLength();
				log.debug("ADSPEC decoded");
				
			}
			
		}
		
		this.setLength(length);
		log.debug("Decoding Sender Descriptor Accomplished");
		

	}
	
	
	
	
	
	
	// Getters and Setters
	
	public SenderTemplate getSenderTemplate() {
		return senderTemplate;
	}

	public void setSenderTemplate(SenderTemplate senderTemplate) {
		this.senderTemplate = senderTemplate;
	}

	public SenderTSpec getSenderTSPEC() {
		return senderTSPEC;
	}

	public void setSenderTSPEC(SenderTSpec senderTSPEC) {
		this.senderTSPEC = senderTSPEC;
	}

	public IntservADSPEC getAdspec() {
		return adspec;
	}

	public void setAdspec(IntservADSPEC adspec) {
		this.adspec = adspec;
	}

	
	
	
}
