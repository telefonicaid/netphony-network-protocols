package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class SidLabelNodeAttribTLV extends BGP4TLVFormat{
	private int sid;
	
	public SidLabelNodeAttribTLV() {
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_SID_LABEL);
		// TODO Auto-generated constructor stub
	}
	
	public SidLabelNodeAttribTLV(byte []bytes, int offset){
		super(bytes, offset);
		decode();
	}

	private void decode() {
		for (int k = 0; k < 4; k++) {
			   sid = (sid << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
	}

	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		this.tlv_bytes[4] = (byte)(sid>>> 24 & 0xff);
		this.tlv_bytes[5] = (byte)(sid>>> 16 & 0xff);
		this.tlv_bytes[6] = (byte)(sid>>> 8 & 0xff);
		this.tlv_bytes[7] = (byte)(sid>>> 0 & 0xff);
	}
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String toString() {
		return "SidLabelNodeAttribTLV [SID=" + this.getSid() + "]";
	}

}
