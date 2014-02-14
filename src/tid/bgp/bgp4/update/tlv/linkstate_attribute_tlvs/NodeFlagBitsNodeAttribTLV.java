package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class NodeFlagBitsNodeAttribTLV extends BGP4TLVFormat{
	
	private boolean overload_bit = false;
	private boolean attached_bit = false;
	private boolean external_bit = false;
	private boolean abr_bit = false;
	private boolean reserved = false;
	private byte flags;
	
	public NodeFlagBitsNodeAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_NODE_FLAG_BITS);
	}
	
	public NodeFlagBitsNodeAttribTLV(byte[] bytes, int offset){		
		super(bytes,offset);		
		decode();
	}
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	
	public void decode(){
		int offset =4;
		flags = tlv_bytes[offset];
		if ((flags & 0x80) == 0x80){
			this.setOverload_bit(true);
		}
		if((flags & 0x40) == 0x40){
			this.setAttached_bit(true);
		}
		if((flags & 0x20) == 0x20){
			this.setExternal_bit(true);
		}
		if((flags & 0x10) == 0x10){
			this.setAbr_bit(true);
		}
		
	}

	public boolean isOverload_bit() {
		return overload_bit;
	}

	public void setOverload_bit(boolean overload_bit) {
		this.overload_bit = overload_bit;
	}

	public boolean isAttached_bit() {
		return attached_bit;
	}

	public void setAttached_bit(boolean attached_bit) {
		this.attached_bit = attached_bit;
	}

	public boolean isExternal_bit() {
		return external_bit;
	}

	public void setExternal_bit(boolean external_bit) {
		this.external_bit = external_bit;
	}

	public boolean isAbr_bit() {
		return abr_bit;
	}

	public void setAbr_bit(boolean abr_bit) {
		this.abr_bit = abr_bit;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}
	
	public String toString(){
		return "NODE FLAGS [OB="+ this.isOverload_bit() + "AB="+this.isAttached_bit()+ 
				"EB=" + this.isAttached_bit() + "AB=" + this.isAbr_bit() + "]";
	}
}
