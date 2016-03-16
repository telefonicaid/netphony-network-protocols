package es.tid.rsvp.objects;

/**
 * 
 * @author Fernando Munoz del Nuevo
 * 
 * Controlled-Load Service ADSPEC Data Fragment

The Controlled-Load service does not require any extra ADSPEC data; the only service-specific ADSPEC data is the Controlled-Load break bit.

 31           24 23           16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     5 (a)     |x|  (b)        |            N-1 (c)            |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
. Service-specific general parameter headers/values, if present .
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(a)
    Per-Service header, service number 5 (Controlled-Load). 
(b)
    Break bit. 
(c)
    Length of per-service data in 32 bit words not including the header word. 
    
 *
 */


public class ControlledLoadService{
	
	private int perServiceHeader;
	
	private int globalBreakBit;
	
	private int perServiceLength;
	
	private long serviceGeneralParameters;
	
	private byte[] bytes;
	
	private int length;
	
	
	public ControlledLoadService(){
		
		this.perServiceHeader = 5;
		this.globalBreakBit = 0;
		this.perServiceLength = 0;
		
		this.length = 4;
		this.bytes = new byte[this.length];
		
	}
	
	public ControlledLoadService(long serviceGeneralParameters){
		
		this.perServiceHeader = 5;
		this.globalBreakBit = 8;
		this.perServiceLength = 1;
		
		this.serviceGeneralParameters = serviceGeneralParameters;
		
		this.length = 8;
		this.bytes = new byte[this.length];
		
	}
	
	public void encode(){
		
		int offset = 0;
		
		this.bytes[offset] = (byte)(this.perServiceHeader & 0xFF);
		this.bytes[offset+1] = (byte)(this.globalBreakBit >> 7 & 0xff);
		this.bytes[offset+2] = (byte)(this.perServiceLength >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.perServiceLength & 0xFF);
		
		offset = offset + 4;
		
		if(offset < this.length){
					
			this.bytes[offset] = (byte)(this.serviceGeneralParameters >>> 24);
			this.bytes[offset+1] = (byte)(this.serviceGeneralParameters >> 16 & 0xff);
			this.bytes[offset+2] = (byte)(this.serviceGeneralParameters >> 8 & 0xff);
			this.bytes[offset+3] = (byte)(this.serviceGeneralParameters & 0xff);
							
		}
				
	}

	public int getPerServiceHeader() {
		return perServiceHeader;
	}

	public void setPerServiceHeader(int perServiceHeader) {
		this.perServiceHeader = perServiceHeader;
	}

	public int getGlobalBreakBit() {
		return globalBreakBit;
	}

	public void setGlobalBreakBit(int globalBreakBit) {
		this.globalBreakBit = globalBreakBit;
	}
	
	public int getPerServiceLength() {
		return perServiceLength;
	}

	public void setPerServiceLength(int perServiceLength) {
		this.perServiceLength = perServiceLength;
	}

	public long getServiceGeneralParameters() {
		return serviceGeneralParameters;
	}

	public void setServiceGeneralParameters(long serviceGeneralParameters) {
		this.serviceGeneralParameters = serviceGeneralParameters;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
	
}
