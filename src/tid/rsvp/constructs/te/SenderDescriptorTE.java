package tid.rsvp.constructs.te;

import java.util.logging.Level;
import java.util.logging.Logger;

import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.constructs.SenderDescriptor;
import tid.rsvp.objects.IntservADSPEC;
import tid.rsvp.objects.IntservSenderTSpec;
import tid.rsvp.objects.RRO;
import tid.rsvp.objects.RSVPObject;
import tid.rsvp.objects.SenderTemplate;
import tid.rsvp.objects.RSVPObjectParameters;
import tid.rsvp.objects.SenderTemplateLSPTunnelIPv4;
import tid.rsvp.objects.SenderTemplateLSPTunnelIPv6;

/**
 * 
Awduche, et al.             Standards Track                    [Page 15]

RFC 3209           Extensions to RSVP for LSP Tunnels      December 2001

 * 
      <sender descriptor> ::=  <SENDER_TEMPLATE> <SENDER_TSPEC>
                               [ <ADSPEC> ]
                               [ <RECORD_ROUTE> ]


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
		
	private Logger log;
	
	/**
	 * Builder to be used when a received Sender Descriptor TE and it is wanted to decode it
	 */
	
	public SenderDescriptorTE(){
		
		log = Logger.getLogger("ROADM");
		log.setLevel(Level.ALL);
		log.finest("Sender Descriptor TE Created");
		
	}
	
	/**
	 * Builder to be used when a new Sender Descriptor TE wanted to be created and sent 
	 * @param senderTemplate
	 * @param senderTSPEC
	 * @param adspec	Optional, might be null
	 * @param rro		Optional, migth be null
	 * @throws RSVPProtocolViolationException It is thrown when a mandatory field is not present
	 */
		
	public SenderDescriptorTE(SenderTemplate senderTemplate, IntservSenderTSpec senderTSPEC, IntservADSPEC adspec, RRO rro) throws RSVPProtocolViolationException{
		
		log = Logger.getLogger("ROADM");
		log.setLevel(Level.ALL);

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
		if(rro != null){

			this.rro = rro;
			rro.encode();
			this.length = this.length + rro.getLength();
			log.finest("RRO found");
			
		}
		log.finest("Sender Descriptor TE Created");
		
	}
		
	/**
	 * 
	 * Sender Descriptor TE encoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 * 
	 */
			
	public void encode() throws RSVPProtocolViolationException{
		
		log.finest("Starting Sender Descriptor TE Encode");
		
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
		
		log.finest("Encoding Sender Descriptor TE Accomplished");
		
	}

	/**
	 * 
	 * Sender Descriptor TE decoding method. In failure case it throws an exception.
	 * 
	 * @throws RSVPProtocolViolationException 
	 */
	
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		
		log.finest("Starting Sender Descriptor TE Decode");
		
		int classNum = RSVPObject.getClassNum(bytes,offset);
		int cType = RSVPObject.getcType(bytes, offset);
		int length = 0;
		int bytesLeft = bytes.length - offset; 
		System.out.println("offset: "+offset+"Length: "+RSVPObject.getLength(bytes,offset));

		if(classNum == RSVPObjectParameters.RSVP_OBJECT_CLASS_SENDER_TEMPLATE){
			
			if(cType == 7){		// Tipo IPv4
				
				senderTemplate = new SenderTemplateLSPTunnelIPv4(bytes,offset);
				
			}else if(cType == 8){
				
				senderTemplate = new SenderTemplateLSPTunnelIPv6(bytes,offset);
				
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
		System.out.println("offset: "+offset+"Length: "+RSVPObject.getLength(bytes,offset));

		classNum = RSVPObject.getClassNum(bytes,offset);
		cType = RSVPObject.getcType(bytes, offset);
		System.out.println("ClassNum: "+classNum);
		
		
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
			log.severe("Sender TSPEC not found, It is mandatory");
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
		log.finest("Decoding Sender Descriptor TE Accomplished");
		

	}
	// Getters and Setters

	public RRO getRro() {
		return rro;
	}

	public void setRro(RRO rro) {
		this.rro = rro;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
	

	
	
	
}
