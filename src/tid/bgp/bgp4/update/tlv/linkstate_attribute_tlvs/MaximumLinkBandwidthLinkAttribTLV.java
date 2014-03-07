package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class MaximumLinkBandwidthLinkAttribTLV extends BGP4TLVFormat{
public float maximumBandwidth;
	
	public MaximumLinkBandwidthLinkAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MAXIMUM_BANDWITH);
	}
	
	public MaximumLinkBandwidthLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int bwi=Float.floatToIntBits(maximumBandwidth);
		this.tlv_bytes[4]=(byte)(bwi >>> 24);
		this.tlv_bytes[5]=(byte)(bwi >> 16 & 0xff);
		this.tlv_bytes[6]=(byte)(bwi >> 8 & 0xff);
		this.tlv_bytes[7]=(byte)(bwi & 0xff);	
	}
	
	protected void decode(){
		int bwi = 0;		
		for (int k = 0; k < 4; k++) {
			bwi = (bwi << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
		this.maximumBandwidth=Float.intBitsToFloat(bwi);
	}
	
	

	public float getMaximumBandwidth() {
		return maximumBandwidth;
	}

	public void setMaximumBandwidth(float maximumBandwidth) {
		this.maximumBandwidth = maximumBandwidth;
	}

	public String toString(){
		return " \t> MaximumBandwidth: "+Float.toString(maximumBandwidth);
	}
	public String toStringShort(){
		return "MaxBW: "+Float.toString(maximumBandwidth);
	}
	

}
