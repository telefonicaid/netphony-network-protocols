package es.tid.bgp.bgp4.open;

import java.util.logging.Logger;

/**
 * 
 *     +------------------------------+
       | Capability Code (1 octet)    |
       +------------------------------+
       | Capability Length (1 octet)  |
       +------------------------------+
       | Capability Value (variable)  |
       +------------------------------+
 * 
 * @author smta
 *
 */


public abstract class BGP4Capability {
	private int capabitityCode;
	private int capabilityLength;
	private int length;
	
	protected byte[] bytes;
	
	protected Logger log;
	
	public BGP4Capability(){
		log=Logger.getLogger("BGP4Parser");	
	}
	
	public BGP4Capability (byte [] bytes, int offset){
		log=Logger.getLogger("BGP4Parser");	
		this.capabitityCode =bytes[offset] & 0xFF;
		this.capabilityLength= ((int)bytes[offset+1] & 0xFF);
		this.length=capabilityLength+2; //1 octet code, 1 octet, length
		this.bytes=new byte[length];
		System.arraycopy(bytes, offset, this.bytes, 0, length);
	}
	
	public void encodeHeader(){
		this.bytes[0]=(byte)(capabitityCode & 0xFF);
		this.bytes[1]=(byte)(capabilityLength & 0xFF);	
	}
	
	public abstract void encode();
	
	
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCapabitityCode() {
		return capabitityCode;
	}

	public void setCapabitityCode(int capabitityCode) {
		this.capabitityCode = capabitityCode;
	}

	public int getCapabilityLength() {
		return capabilityLength;
	}

	public void setCapabilityLength(int capabilityLength) {
		this.capabilityLength = capabilityLength;
		this.length=this.capabilityLength+2;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public static int getCapalitityCode(byte []bytes, int offset) {
		int len=((int)bytes[offset]&0xFF);		
		return len;
	}
	
	public static int getCapabilityLength(byte []bytes, int offset) {
		int len=((int)bytes[offset+1]&0xFF);
		return len;
	}
	
}
