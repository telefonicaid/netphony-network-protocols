package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class IPv6InterfaceAddressLinkDescriptorSubTLV extends  BGP4TLVFormat{
	public IPv6InterfaceAddressLinkDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6INTERFACE);
	}
	
	
	public IPv6InterfaceAddressLinkDescriptorSubTLV(byte []bytes, int offset) {		
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
