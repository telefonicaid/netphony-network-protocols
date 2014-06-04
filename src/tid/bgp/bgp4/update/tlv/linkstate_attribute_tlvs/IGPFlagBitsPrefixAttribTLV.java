package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class IGPFlagBitsPrefixAttribTLV extends BGP4TLVFormat{
	
	private boolean up_dw_bit = false;
	private byte flags;

	public IGPFlagBitsPrefixAttribTLV() {
		this.setTLVValueLength(1);
		// TODO Auto-generated constructor stub
	}
	
	public IGPFlagBitsPrefixAttribTLV(byte[] bytes, int offset){		
		super(bytes,offset);		
		decode();
	}
	
	@Override
	public void encode() {
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;
		if(isUp_dw_bit()){
			(this.tlv_bytes[offset])|=(byte) 0x80;
		}
		// TODO Auto-generated method stub
		
	}
	
	public void decode(){
		int offset =4;
		flags = tlv_bytes[offset];
		if ((flags & 0x80) == 0x80){
			this.setUp_dw_bit(true);
		}
	
	}

	public boolean isUp_dw_bit() {
		return up_dw_bit;
	}

	public void setUp_dw_bit(boolean up_dw_bit) {
		this.up_dw_bit = up_dw_bit;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}
	
	public String toString(){
		return "IGP FLAGS [D="+ this.isUp_dw_bit() + "]";
	}

}
