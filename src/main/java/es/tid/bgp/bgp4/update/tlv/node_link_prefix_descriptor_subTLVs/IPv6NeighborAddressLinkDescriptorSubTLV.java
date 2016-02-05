package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;



public class IPv6NeighborAddressLinkDescriptorSubTLV extends BGP4TLVFormat{

	public IPv6NeighborAddressLinkDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6NEIGHBOR);
	}
	
	
	public IPv6NeighborAddressLinkDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	public void decode(){
		
	}

}
