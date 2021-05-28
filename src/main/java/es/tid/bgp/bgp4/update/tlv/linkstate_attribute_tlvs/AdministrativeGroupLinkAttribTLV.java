package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * Administrative group (color) TLV	(Type 1088)	[RFC5305, Section 3.1]
 The administrative group sub-TLV contains a 4-octet bit mask assigned
   by the network administrator.  Each set bit corresponds to one
   administrative group assigned to the interface.

   By convention, the least significant bit is referred to as 'group 0',
   and the most significant bit is referred to as 'group 31'.

   This sub-TLV is OPTIONAL.  This sub-TLV SHOULD appear once at most in
   each extended IS reachability TLV.
   
 * @author pac
 */

public class AdministrativeGroupLinkAttribTLV extends BGP4TLVFormat {
	
	/**
	 * Administrative Group
	 */
	private int administrativeGroup;
	
	public AdministrativeGroupLinkAttribTLV(){
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_ADMINISTRATIVE_GROUP);
		administrativeGroup=0;
	}
	
	public AdministrativeGroupLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the 4 byte Administrative group
	 */
	public void encode(){
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;
	
		this.tlv_bytes[offset] = (byte)((administrativeGroup >> 24) & 0xff);
		this.tlv_bytes[offset + 1] = (byte)((administrativeGroup >> 16) & 0xff);
		this.tlv_bytes[offset + 2] = (byte)((administrativeGroup >> 8) & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(administrativeGroup & 0xff);

	}
	
	protected void decode(){
		int offset=4;		
		this.administrativeGroup= ((this.tlv_bytes[offset]&0xFF)<<24) | ((this.tlv_bytes[offset+1]&0xFF)<<16) | ((this.tlv_bytes[offset+2]&0xFF)<<8) |  (this.tlv_bytes[offset+3]&0xFF) ;
	}


	public int getAdministrativeGroup() {
		return administrativeGroup;
	}

	public void setAdministrativeGroup(int administrativeGroup) {
		this.administrativeGroup = administrativeGroup;
	}
	
	public boolean isGroup(int groupNumber){
		return (administrativeGroup>>>groupNumber)==1;
	}
	
	public void setGroup(int groupNumber){	
		administrativeGroup=administrativeGroup|(1<<groupNumber);
	}
	public String toString(){
		return "ADMINISTRATIVE GROUP [Administrative Group =" +Integer.toBinaryString(administrativeGroup) + "]";
	}
}
