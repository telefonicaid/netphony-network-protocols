package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

public class LinkDescriptorSubTLVTypes {

	/**  
	 * Para link descriptors TLVs
	    +------------+--------------------+---------------+-----------------+
   |  TLV Code  | Description        |   IS-IS TLV   | Value defined   |
   |   Point    |                    |    /Sub-TLV   | in:             |
   +------------+--------------------+---------------+-----------------+
   |    258     | Link Local/Remote  |      22/4     | [RFC5307]/1.1   |
   |            | Identifiers        |               |                 |
   |    259     | IPv4 interface     |      22/6     | [RFC5305]/3.2   |
   |            | address            |               |                 |
   |    260     | IPv4 neighbor      |      22/8     | [RFC5305]/3.3   |
   |            | address            |               |                 |
   |    261     | IPv6 interface     |     22/12     | [RFC6119]/4.2   |
   |            | address            |               |                 |
   |    262     | IPv6 neighbor      |     22/13     | [RFC6119]/4.3   |
   |            | address            |               |                 |
   |    263     | Multi-Topology     |      ---      | Section 3.2.1.5 |
   |            | Identifier         |               |                 |
   +------------+--------------------+---------------+-----------------+

                       Table 3: Link Descriptor TLVs
	   */
		
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_LINKIDENTIFIERS=258;//Draft BGP-LS
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv4INTERFACE=259;//Draft BGP-LS
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv4NEIGHBOR = 260;
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6INTERFACE = 261;
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6NEIGHBOR = 262;
	public static final int LINK_DESCRIPTOR_SUB_TLV_TYPE_MULTITOPOLOGY_ID=263;//Draft BGP-LS

}
