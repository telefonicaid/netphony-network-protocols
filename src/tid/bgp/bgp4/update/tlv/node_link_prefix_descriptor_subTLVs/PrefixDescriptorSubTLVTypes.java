package tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

/**
 The 'Prefix Descriptor' field is a set of Type/Length/Value (TLV)
triplets.  'Prefix Descriptor' TLVs uniquely identify an IPv4 or IPv6
Prefix originated by a Node.  The following TLVs are valid as Prefix
Descriptors in the IPv4/IPv6 Prefix NLRI:

+-----------+--------------------------+------------+---------------+
|  TLV Code | Description              |   Length   | Value defined |
|   Point   |                          |            | in:           |
+-----------+--------------------------+------------+---------------+
|    263    | Multi-Topology           |  variable  | Section       |
|           | Identifier               |            | 3.2.1.5       |
|    264    | OSPF Route Type          |     1      | Section       |
|           |                          |            | 3.2.3.1       |
|    265    | IP Reachability          |  variable  | Section       |
|           | Information              |            | 3.2.3.2       |
+-----------+--------------------------+------------+---------------+

                   Table 4: Prefix Descriptor TLVs
*/

public class PrefixDescriptorSubTLVTypes {

	public static final int PREFIX_DESCRIPTOR_SUB_TLV_TYPE_OSPF_ROUTE_TYPE=264;//Draft BGP-LS
	public static final int PREFIX_DESCRIPTOR_SUB_TLV_TYPE_IPV4_REACHABILITY_INFO=265;//Draft BGP-LS

}
