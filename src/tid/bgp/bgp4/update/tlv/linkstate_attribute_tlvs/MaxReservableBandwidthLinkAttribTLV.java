package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class MaxReservableBandwidthLinkAttribTLV extends BGP4TLVFormat{
public float maximumReservableBandwidth;
	


	public MaxReservableBandwidthLinkAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MAX_RESERVABLE_BANDWITH);
	}
	
	public MaxReservableBandwidthLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int bwi=Float.floatToIntBits(maximumReservableBandwidth);
		this.tlv_bytes[4]=(byte)(bwi >>> 24);
		this.tlv_bytes[5]=(byte)(bwi >> 16 & 0xff);
		this.tlv_bytes[6]=(byte)(bwi >> 8 & 0xff);
		this.tlv_bytes[7]=(byte)(bwi & 0xff);	
	}
	
	protected void decode(){
		if (this.getTLVValueLength()!=4){
			//
		}
		int bwi = 0;		
		for (int k = 0; k < 4; k++) {
			bwi = (bwi << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
		this.maximumReservableBandwidth=Float.intBitsToFloat(bwi);
	}
	public void setMaximumReservableBandwidth(float maximumReservableBandwidth) {
		this.maximumReservableBandwidth = maximumReservableBandwidth;
	}
	
	public float getMaximumReservableBandwidth() {
		return maximumReservableBandwidth;
	}

	public String toString(){
		return " MAXIMUM BW [ Bandwidth = "+Float.toString(maximumReservableBandwidth) + "]";
	}

}
