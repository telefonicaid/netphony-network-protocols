package es.tid.rsvp.constructs.te;

import java.util.logging.Level;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.SenderDescriptor;
import es.tid.rsvp.objects.IntservADSPEC;
import es.tid.rsvp.objects.IntservSenderTSpec;
import es.tid.rsvp.objects.RRO;
import es.tid.rsvp.objects.RSVPObject;
import es.tid.rsvp.objects.RSVPObjectParameters;
import es.tid.rsvp.objects.SenderTemplate;
import es.tid.rsvp.objects.SenderTemplateLSPTunnelIPv4;
import es.tid.rsvp.objects.SenderTemplateLSPTunnelIPv6;
import org.slf4j.LoggerFactory;

/**
 * 

 * {@code
      <sender descriptor> ::=  <SENDER_TEMPLATE> <SENDER_TSPEC>
                               [ <ADSPEC> ]
                               [ <RECORD_ROUTE> ]}


 * @author fmn
 *
 */

public class SenderDescriptorTE extends SenderDescriptor {

	/**
	 * 	Sender Template is a mandatory RSVP-TE Object
	 */
	
	private RRO rro;
	
	/**
	 * Log
	 */

  private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Builder to be used when a received Sender Descriptor TE and it is wanted to decode it
	 */
	
	public SenderDescriptorTE(){
		
		log.debug("Sender Descriptor TE Created");
		
	}
	
	/**
	 * Builder to be used when a new Sender Descriptor TE wanted to be created and sent 
	 * @param senderTemplate Sender Template
	 * @param senderTSPEC Sender TSPEC
	 * @param adspec	Optional, might be null
	 * @param rro		Optional, migth be null
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public SenderDescriptorTE(SenderTemplate senderTemplate, IntservSenderTSpec senderTSPEC, IntservADSPEC adspec, RRO rro) throws RSVPProtocolViolationException{
		
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
		if(rro != null){

			this.rro = rro;
			rro.encode();
			this.length = this.length + rro.getLength();
			log.debug("RRO found");
			
		}
		log.debug("Sender Descriptor TE Created");
		
	}
		
	/**
	 * 
	 * Sender Descriptor TE encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException Thrown when there is a problem with the encoding
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.debug("Starting Sender Descriptor TE Encode");
		
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
			offset = offset + adspec.getLength();
		

		}
		if(rro != null){
			// Campo Opcional
			rro.encode();
		
			System.arraycopy(rro.getBytes(), 0, bytes, offset, rro.getLength());
		}
		
		log.debug("Encoding Sender Descriptor TE Accomplished");
		
	}

	/**
	 * 
	 * Sender Descriptor TE decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException Thrown when there is a problem with the decoding
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.debug("Starting Sender Descriptor TE Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 

		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TEMPLATE){
			
			if(cType == 7){		// Tipo IPv4
		
				senderTemplate = new SenderTemplateLSPTunnelIPv4(bytes,offset);
				
			}else if(cType == 8){
				
				senderTemplate = new SenderTemplateLSPTunnelIPv6(bytes,offset);
				
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
				
				senderTSPEC = new IntservSenderTSpec(bytes,offset);
				
			}else{
				
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
			log.error("Sender TSPEC not found, It is mandatory");
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
		
		if(bytesLeft > 0){
			
			classNum = RSVPObject.getClassNum(bytes,offset);
			cType = RSVPObject.getcType(bytes, offset);
			
			if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_RRO){
				
				if(cType == 1){		// cType adecuado
					
					rro = new RRO(bytes,offset);
					
				}else{
					
					// No se ha formado correctamente el objeto sender template
					log.error("Malformed RRO cType field");
					throw new RSVPProtocolViolationException();
					
				}
				offset = offset + rro.getLength();
				length = length + rro.getLength();
				log.debug("RRO decoded");
				
			}
			
		}
		
		this.setLength(length);
		log.debug("Decoding Sender Descriptor TE Accomplished");
		

	}
	// Getters and Setters

	public RRO getRro() {
		return rro;
	}

	public void setRro(RRO rro) {
		this.rro = rro;
	}
}
