package tid.pce.pcep.objects.tlvs;

import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;

/**
 * Represents the maximum time that a Request can be in the PCE.
 * It is made of 4 bytes and represents a postive integer.
 * 
 * @author ogondio
 *
 */
public class BandwidthTLV extends PCEPTLV {


	/**
	 * Bandwidth (32 bits):  The requested bandwidth is encoded in 32 bits
      in IEEE floating point format (see [IEEE.754.1985]), expressed in
      bytes per second.  Refer to Section 3.1.2 of [RFC3471] for a table
      of commonly used values.
	 */
	public float bw  = 0;
	
	public BandwidthTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_BANDWIDTH);
		
	}
	
	public BandwidthTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void decode(){
		int bwi = 0;		
		for (int k = 0; k < 4; k++) {
			bwi = (bwi << 8) | (tlv_bytes[k+4] & 0xff);
		}
		bw=Float.intBitsToFloat(bwi);
	}
	/**
	 * Encodes the maximum Request Time TLV.
	 */
	public void encode() {
		log.info("codificando Bandwidth TLV");
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
			
		int bwi=Float.floatToIntBits(bw);
		this.tlv_bytes[4]=(byte)(bwi >>> 24);
		this.tlv_bytes[5]=(byte)(bwi >> 16 & 0xff);
		this.tlv_bytes[6]=(byte)(bwi >> 8 & 0xff);
		this.tlv_bytes[7]=(byte)(bwi & 0xff);	
	}

	public float getBw() {
		return bw;
	}

	public void setBw(float bw) {
		this.bw = bw;
	}

}
