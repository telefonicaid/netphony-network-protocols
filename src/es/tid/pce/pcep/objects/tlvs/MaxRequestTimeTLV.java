package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * Represents the maximum time that a Request can be in the PCE.
 * It is made of 4 bytes and represents a postive integer.
 * 
 * @author ogondio
 *
 */
public class MaxRequestTimeTLV extends PCEPTLV {

	/**
	 * The maximum time that the Request can expend in the PCE.
	 */
	private long maxRequestTime;
	
	public MaxRequestTimeTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_MAX_REQ_TIME);
		
	}
	
	public MaxRequestTimeTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void decode(){
		this.maxRequestTime= 0;		
		for (int k = 0; k < 4; k++) {
			maxRequestTime = (maxRequestTime << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
	}
	/**
	 * Encodes the maximum Request Time TLV.
	 */
	public void encode() {
		log.finest("codificando Max Request Time TLV");
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		this.tlv_bytes[4]=(byte)(maxRequestTime >>> 24);
		this.tlv_bytes[5]=(byte)(maxRequestTime >>> 16);
		this.tlv_bytes[6]=(byte)(maxRequestTime >>> 8);
		this.tlv_bytes[7]=(byte)maxRequestTime ;
	}

	public long getMaxRequestTime() {
		return maxRequestTime;
	}

	public void setMaxRequestTime(long maxRequestTime) {
		this.maxRequestTime = maxRequestTime;
	}

	
	
	
}
