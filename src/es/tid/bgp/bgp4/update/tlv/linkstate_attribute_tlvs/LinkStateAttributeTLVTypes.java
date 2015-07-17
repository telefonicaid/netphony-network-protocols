package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

public class LinkStateAttributeTLVTypes {

	/**
	 * 
	      +------------+---------------------+--------------+-----------------+
   |  TLV Code  | Description         |     IS-IS    | Defined in:     |
   |    Point   |                     |  TLV/Sub-TLV |                 |
   +------------+---------------------+--------------+-----------------+
   |    1028    | IPv4 Router-ID of   |    134/---   | [RFC5305]/4.3   |
   |            | Local Node          |              |                 |
   |    1029    | IPv6 Router-ID of   |    140/---   | [RFC6119]/4.1   |
   |            | Local Node          |              |                 |
   |    1030    | IPv4 Router-ID of   |    134/---   | [RFC5305]/4.3   |
   |            | Remote Node         |              |                 |
   |    1031    | IPv6 Router-ID of   |    140/---   | [RFC6119]/4.1   |
   |            | Remote Node         |              |                 |
   |    1088    | Administrative      |     22/3     | [RFC5305]/3.1   |
   |            | group (color)       |              |                 |
   |    1089    | Maximum link        |     22/9     | [RFC5305]/3.3   |
   |            | bandwidth           |              |                 |
   |    1090    | Max. reservable     |     22/10    | [RFC5305]/3.5   |
   |            | link bandwidth      |              |                 |
   |    1091    | Unreserved          |     22/11    | [RFC5305]/3.6   |
   |            | bandwidth           |              |                 |
   |    1092    | TE Default Metric   |     22/18    | [RFC5305]/3.7   |
   |    1093    | Link Protection     |     22/20    | [RFC5307]/1.2   |
   |            | Type                |              |                 |
   |    1094    | MPLS Protocol Mask  |      ---     | Section 3.3.2.2 |
   |    1095    | Metric              |      ---     | Section 3.3.2.3 |
   |    1096    | Shared Risk Link    |      ---     | Section 3.3.2.4 |
   |            | Group               |              |                 |
   |    1097    | Opaque link         |      ---     | Section 3.3.2.5 |
   |            | attribute           |              |                 |
   |    1098    | Link Name attribute |      ---     | Section 3.3.2.6 |
   +------------+---------------------+--------------+-----------------+

                       Table 7: Link Attribute TLVs
	 */
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_MAXIMUM_BANDWITH = 1089;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_MAX_RESERVABLE_BANDWITH = 1090;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_UNRESERVED_BANDWITH = 1091;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_LOCAL_NODE = 1028;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_IPv6_ROUTER_ID_OF_LOCAL_NODE = 1029;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_REMOTE_NODE = 1030;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_IPv6_ROUTER_ID_OF_REMOTE_NODE = 1031;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_TE_DEFAULT_METRIC = 1092;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_ADMINISTRATIVE_GROUP = 1088;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_LINK_PROTECTION_TYPE = 1093;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_METRIC = 1095;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_AVAILABLELABELS = 1200;
	
	
	//********** RUBEN *************  
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_SHARED_RISK_LINK_GROUP = 1096;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_TRANSCEIVER_CLASS_AND_APPLICATION = 2498;
	public static final int  LINK_ATTRIBUTE_TLV_TYPE_MF_OTP = 2499;
	// *****************************
	
	/**          
	 *     
   +--------------+-----------------------+----------+-----------------+
   |   TLV Code   | Description           |   Length | Value defined   |
   |     Point    |                       |          | in:             |
   +--------------+-----------------------+----------+-----------------+
   |      263     | Multi-Topology        | variable | Section 3.2.1.5 |
   |              | Identifier            |          |                 |
   |     1024     | Node Flag Bits        |        1 | Section 3.3.1.1 |
   |     1025     | Opaque Node           | variable | Section 3.3.1.5 |
   |              | Properties            |          |                 |
   |     1026     | Node Name             | variable | Section 3.3.1.3 |
   |     1027     | IS-IS Area Identifier | variable | Section 3.3.1.2 |
   |     1028     | IPv4 Router-ID of     |        4 | [RFC5305]/4.3   |
   |              | Local Node            |          |                 |
   |     1029     | IPv6 Router-ID of     |       16 | [RFC6119]/4.1   |
   |              | Local Node            |          |                 |
   +--------------+-----------------------+----------+-----------------+

                       Table 5: Node Attribute TLVs
	 */
	
	
	//FIXME:	A definir por IANA el tipo de la subtlv Port Label Restriction, Available Labels
	// Draft http://tools.ietf.org/html/draft-ietf-ccamp-general-constraint-encode-05#section-2.6
	
	public static final int NODE_ATTRIBUTE_TLV_TYPE_NODE_FLAG_BITS = 1024;
	public static final int NODE_ATTRIBUTE_TLV_TYPE_NODE_NAME = 1026;
	public static final int NODE_ATTRIBUTE_TLV_TYPE_IS_IS_AREA_ID = 1027;
	public static final int NODE_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_LOCAL_NODE = 1028;
	public static final int NODE_ATTRIBUTE_TLV_TYPE_SID_LABEL = 1069;
	
	public static final int PREFIX_ATTRIBUTE_TLV_TYPE_OSPF_FORWARDING_ADDRESS = 1156;
	public static final int PREFIX_ATTRIBUTE_TLV_TYPE_IGP_FLAGS = 1152;
	public static final int PREFIX_ATTRIBUTE_TLV_TYPE_PREFIX_METRIC = 1155;
	public static final int PREFIX_ATTRIBUTE_TLV_TYPE_ROUTE_TAG = 1153;
	
	
	
}
