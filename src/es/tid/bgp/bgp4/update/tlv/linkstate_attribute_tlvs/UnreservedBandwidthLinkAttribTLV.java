package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class UnreservedBandwidthLinkAttribTLV extends BGP4TLVFormat{
public float[] unreservedBandwidth;
	
	public UnreservedBandwidthLinkAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_UNRESERVED_BANDWITH);
		unreservedBandwidth=new float[8];
	}
	
	public UnreservedBandwidthLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		unreservedBandwidth=new float[8];
		decode();
	}
	
	public void encode() {
		log.finest("Encoding UnreservedBandwidthTLV");
		this.setTLVValueLength(32);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int bwi;
		int offset=4;
		for (int i=0;i<8;++i){
			bwi=Float.floatToIntBits(unreservedBandwidth[i]);
			this.tlv_bytes[offset]=(byte)(bwi >>> 24);
			this.tlv_bytes[offset+1]=(byte)(bwi >> 16 & 0xff);
			this.tlv_bytes[offset+2]=(byte)(bwi >> 8 & 0xff);
			this.tlv_bytes[offset+3]=(byte)(bwi & 0xff);
			offset=offset+4;
		}

	}
	
	protected void decode(){
		log.finest("Decoding UnreservedBandwidthTLV");
		if (this.getTLVValueLength()!=32){
			//throw new MalformedOSPFSubTLVException();
		}
		int bwi = 0;
		int offset=4;
		for (int i=0;i<8;++i){
			bwi=0;
			for (int k = 0; k < 4; k++) {
				bwi = (bwi << 8) | (this.tlv_bytes[offset+k] & 0xff);
			}
			this.unreservedBandwidth[i]=Float.intBitsToFloat(bwi);
			offset=offset+4;
		}
				
		
	}
	
	public String toString(){
		String ret="";
		for (int i=0;i<7;++i){
			ret=ret+"\t> unreservedBandwidth["+i+"]: "+Float.toString(unreservedBandwidth[i])+"\r\n";
		}
		ret=ret+"\t> unreservedBandwidth[7]: "+Float.toString(unreservedBandwidth[7]);
		return ret;
	}
	
	public String toStringShort(){
		String ret="unrBw[0]: "+unreservedBandwidth[0];
		return ret;
	}

	public float[] getUnreservedBandwidth() {
		return unreservedBandwidth;
	}

	public void setUnreservedBandwidth(float[] unreservedBandwidth) {
		this.unreservedBandwidth = unreservedBandwidth;
	}
}
