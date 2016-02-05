package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

public class NodeDescriptorsSubTLVTypes {
/*	      +--------------------+-------------------+----------+
           | Sub-TLV Code Point | Description       |   Length |
           +--------------------+-------------------+----------+
           |         512        | Autonomous System |        4 |
           |         513        | BGP-LS Identifier |        4 |
           |         514        | Area-ID           |        4 |
           |         515        | IGP Router-ID     | Variable |
           +--------------------+-------------------+----------+

                     Table 2: Node Descriptor Sub-TLVs             */
	
	//Para node descriptors
	public static final int NODE_DESCRIPTORS_SUBTLV_TYPE_AUTONOMOUS_SYSTEM=512;
	public static final int NODE_DESCRIPTORS_SUBTLV_TYPE_BGP_LS_IDENTIFIER=513;
	public static final int NODE_DESCRIPTORS_SUBTLV_TYPE_AREA_ID=514;
	public static final int NODE_DESCRIPTORS_SUBTLV_TYPE_IGP_ROUTER_ID=515;
}
