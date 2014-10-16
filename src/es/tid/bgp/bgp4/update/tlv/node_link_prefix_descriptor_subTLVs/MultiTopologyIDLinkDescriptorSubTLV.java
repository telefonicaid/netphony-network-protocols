package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * 3.2.2.1.  Multi Topology ID TLV

   The Multi Topology ID TLV (Type 268) carries the Multi Topology ID
   for this link.  The semantics of the Multi Topology ID are defined in
   RFC5120, Section 7.2 [RFC5120], and the OSPF Multi Topology ID),
   defined in RFC4915, Section 3.7 [RFC4915].  If the value in the Multi
   Topology ID TLV is derived from OSPF, then the upper 9 bits of the
   Multi Topology ID are set to 0.

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |R R R R|   Multi Topology ID   |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 12: Multi Topology ID TLV format
                  
                 
                  
-------------- rfc4915#section-3.7 -------------------
 MT-ID Values
 
   Since AS-External-LSAs use the high-order bit in the MT-ID field
   (E-bit) for the external metric-type, only MT-IDs in the 0 to 127
   range are valid.  The following MT-ID values are reserved:

            0      - Reserved for advertising the metric associated
                     with the default topology (see Section 4.2)
            1      - Reserved for advertising the metric associated
                     with the default multicast topology
            2      - Reserved for IPv4 in-band management purposes
           3-31    - Reserved for assignments by IANA
           32-127  - Reserved for development, experimental and
                     proprietary features [RFC3692]
           128-255 - Invalid and SHOULD be ignored

                  
 * @author mcs
 *
 */
public class MultiTopologyIDLinkDescriptorSubTLV extends BGP4TLVFormat{

	private int multitopologyID;
	
	public MultiTopologyIDLinkDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_MULTITOPOLOGY_ID);
	}
	public MultiTopologyIDLinkDescriptorSubTLV(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		log.finest("Encode MultiTopologyIDTLV TBD");
	
		
	}

	public void decode(){
		log.finest("Decoding MultiTopologyIDTLV TBD");
		
	}
	public int getMultitopologyID() {
		return multitopologyID;
	}
	public void setMultitopologyID(int multitopologyID) {
		this.multitopologyID = multitopologyID;
	}
	
	

}
