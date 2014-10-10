package es.tid.rsvp.constructs;

import java.util.logging.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.FlowLabelSenderTemplateIPv6;
import es.tid.rsvp.objects.IntservADSPEC;
import es.tid.rsvp.objects.IntservSenderTSpec;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import es.tid.rsvp.objects.SenderTSpec;
import es.tid.rsvp.objects.SenderTemplate;
import es.tid.rsvp.objects.SenderTemplateIPv4;
import es.tid.rsvp.objects.SenderTemplateIPv6;

/**
 * 
 * <p>Represents a Sender Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <p>The format of a Path message is as follows:</p>
 * 
 * <UL TYPE="CIRCLE">
 * <LI> &lt;Path Message&gt; ::= &lt;Common Header&gt; [ &lt;INTEGRITY&gt; ]
 * &lt;SESSION&gt; &lt;RSVP_HOP&gt; &lt;TIME_VALUES&gt; [ &lt;POLICY_DATA&gt; ... ]
 * [ &lt;sender descriptor&gt; ]
 * <LI>&lt;sender descriptor&gt; ::= &lt;SENDER_TEMPLATE&gt; &lt;SENDER_TSPEC&gt;
 * [ &lt;ADSPEC&gt; ]
 * </UL>
 * @author Fernando Mu�oz del Nuevo
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
		
	private Logger log;
	
	/**
	 * Builder to be used when a received Sender Descriptor and it is wanted to decode it
	 */
	
	public SenderDescriptor(){
		
		log = Logger.getLogger("ROADM");
	
		log.finest("Sender Descriptor Created");
		
	}
	
	/**
	 * Builder to be used when a new Sender Descriptor it wanted to be created and sent 
	 * @param senderTemplate
	 * @param senderTSPEC
	 * @param adspec	Optional, might be null
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public SenderDescriptor(SenderTemplate senderTemplate, IntservSenderTSpec senderTSPEC, IntservADSPEC adspec) throws RSVPProtocolViolationException{
		
		log = Logger.getLogger("ROADM");


		if(senderTemplate != null){
			
			this.length = this.length + senderTemplate.getLength();
			this.senderTemplate = senderTemplate;
			log.finest("Sender Template found");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(senderTSPEC != null){
			
			this.length = this.length + senderTSPEC.getLength();
			this.senderTSPEC = senderTSPEC;
			log.finest("Intserv Sender TSpec found");
			
		}else{
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Intserv Sender TSpec not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		if(adspec != null){

			this.adspec = adspec;
			this.length = this.length + adspec.getLength();
			log.finest("Intserv ADSPEC found");
			
		}

		log.finest("Sender Descriptor Created");
		
	}
		
	/**
	 * 
	 * Sender Descriptor encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.finest("Starting Sender Descriptor Encode");
		
		this.bytes = new byte[length];
		
		int offset=0;
		// Campo Obligatorio
		senderTemplate.encode();
		System.arraycopy(senderTemplate.getBytes(), 0, bytes, offset, senderTemplate.getLength());
		offset = offset + senderTemplate.getLength();
		// Campo Obligatorio
		senderTSPEC.encode();
		System.arraycopy(senderTSPEC.getBytes(), 0, bytes, offset, senderTSPEC.getLength());
		offset = offset + senderTSPEC.getLength();
		if(adspec != null){
			// Campo Opcional
			adspec.encode();
			System.arraycopy(adspec.getBytes(), 0, bytes, offset, adspec.getLength());
		}
		
		log.finest("Encoding Sender Descriptor Accomplished");
		
	}

	/**
	 * 
	 * Sender Descriptor decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.finest("Starting Sender Descriptor Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TEMPLATE){
			
			if(cType == 1){		// Tipo IPv4
				
				senderTemplate = new SenderTemplateIPv4();
				
			}else if(cType == 2){
				
				senderTemplate = new SenderTemplateIPv6();
				
			}else if(cType == 3){
				
				senderTemplate = new FlowLabelSenderTemplateIPv6();
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.severe("Malformed Sender Template cType field");
				throw new RSVPProtocolViolationException();
				
			}
			senderTemplate.decode(bytes,offset);
			offset = offset + senderTemplate.getLength();
			length = length + senderTemplate.getLength();
			bytesLeft = bytesLeft - senderTemplate.getLength();
			log.finest("Sender Template decoded");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(bytesLeft <= 0){
			
			log.severe("Incomplete Sender Descriptor");
			throw new RSVPProtocolViolationException();
			
		}
		
		classNum = RSVPObject.getClassNum(bytes,offset);
		cType = RSVPObject.getcType(bytes, offset);
		
		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TSPEC){
			
			if(cType == 2){		// cType adecuado
				
				senderTSPEC = new IntservSenderTSpec();
				
			}else{
				
				// No se ha formado correctamente el objeto sender template
				log.severe("Malformed Sender TSPEC cType field");
				throw new RSVPProtocolViolationException();
				
			}
			senderTSPEC.decode(bytes,offset);
			offset = offset + senderTSPEC.getLength();
			length = length + senderTSPEC.getLength();
			bytesLeft = bytesLeft - senderTSPEC.getLength();
			log.finest("Sender TSPEC decoded");
			
		}else{	
			
			// Campo obligatorio, por lo tanto se lanza excepcion si no existe
			log.severe("Sender Template not found, It is mandatory");
			throw new RSVPProtocolViolationException();
			
		}
		
		if(bytesLeft > 0){
		
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes, offset);
			
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_ADSPEC){
				
				if(cType == 2){		// cType adecuado
					
					adspec = new IntservADSPEC();
					
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.severe("Malformed ADSPEC cType field");
					throw new RSVPProtocolViolationException();
					
				}
				adspec.decode(bytes,offset);
				offset = offset + adspec.getLength();
				length = length + adspec.getLength();
				log.finest("ADSPEC decoded");
				
			}
			
		}
		
		this.setLength(length);
		log.finest("Decoding Sender Descriptor Accomplished");
		

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
