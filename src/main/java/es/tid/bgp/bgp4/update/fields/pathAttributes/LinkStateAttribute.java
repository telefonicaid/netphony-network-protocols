package es.tid.bgp.bgp4.update.fields.pathAttributes;



import es.tid.bgp.bgp4.update.fields.PathAttribute;
import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.AdministrativeGroupLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.DefaultTEMetricLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IGPFlagBitsPrefixAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDLocalNodeLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDLocalNodeNodeAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IPv4RouterIDRemoteNodeLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.IS_IS_AreaIdentifierNodeAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkProtectionTypeLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkStateAttributeTLVTypes;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MaxReservableBandwidthLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MaximumLinkBandwidthLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MetricLinkAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.NodeFlagBitsNodeAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.NodeNameNodeAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.OSPFForwardingAddressPrefixAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.PrefixMetricPrefixAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.RouteTagPrefixAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.SidLabelNodeAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.UnreservedBandwidthLinkAttribTLV;
//********** RUBEN *************
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.SharedRiskLinkGroupAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.TransceiverClassAndAppAttribTLV;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.MF_OTPAttribTLV;
//******************************
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.AvailableLabels;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.MalformedOSPFSubTLVException;

/**
 * Link-State Info Distribution using BGP, July 2012
 *
 * 3.3.  The LINK_STATE Attribute

   This is an optional non-transitive BGP attribute that is used to
   carry link and node link-state parameters and attributes.  It is
   defined as a set of Type/Length/Value (TLV) triplets, described in
   the following section.  This attribute SHOULD only be included with
   Link State NLRIs.  This attribute MUST be ignored for all other NLRI
   types.
 * 3.3.1.  Link Attribute TLVs

    Link attribute TLVs are TLVs that may be encoded in the BGP-LS
   attribute with a link NLRI.  Each 'Link Attribute' is a Type/Length/
   Value (TLV) triplet formatted as defined in Section 3.1.  The format
   and semantics of the 'value' fields in some 'Link Attribute' TLVs
   correspond to the format and semantics of value fields in IS-IS
   Extended IS Reachability sub-TLVs, defined in [RFC5305] and
   [RFC5307].  Other 'Link Attribute' TLVs are defined in this document.
   Although the encodings for 'Link Attribute' TLVs were originally
   defined for IS-IS, the TLVs can carry data sourced either by IS-IS or
   OSPF.

   The following 'Link Attribute' TLVs are are valid in the LINK_STATE
   attribute:

   +----------+----------------------+---------------+-----------------+
   | TLV Code | Description          |   IS-IS TLV   | Defined in:     |
   |  Point   |                      |    /Sub-TLV   |                 |
   +----------+----------------------+---------------+-----------------+
   |   1028   | IPv4 Router-ID of    |    134/---    | [RFC5305]/4.3   |
   |          | Local Node           |               |                 |
   |   1029   | IPv6 Router-ID of    |    140/---    | [RFC6119]/4.1   |
   |          | Local Node           |               |                 |
   |   1030   | IPv4 Router-ID of    |    134/---    | [RFC5305]/4.3   |
   |          | Remote Node          |               |                 |
   |   1031   | IPv6 Router-ID of    |    140/---    | [RFC6119]/4.1   |
   |          | Remote Node          |               |                 |
   |   1088   | Administrative group |      22/3     | [RFC5305]/3.1   |
   |          | (color)              |               |                 |
   |   1089   | Maximum link         |      22/9     | [RFC5305]/3.3   |
   |          | bandwidth            |               |                 |
   |   1090   | Max. reservable link |     22/10     | [RFC5305]/3.5   |
   |          | bandwidth            |               |                 |
   |   1091   | Unreserved bandwidth |     22/11     | [RFC5305]/3.6   |
   |   1092   | TE Default Metric    |     22/18     | [RFC5305]/3.7   |
   |   1093   | Link Protection Type |     22/20     | [RFC5307]/1.2   |
   |   1094   | MPLS Protocol Mask   |      ---      | Section 3.3.2.2 |
   |   1095   | Metric               |      ---      | Section 3.3.2.3 |
   |   1096   | Shared Risk Link     |      ---      | Section 3.3.2.4 |
   |          | Group                |               |                 |
   |   1097   | Opaque link          |      ---      | Section 3.3.2.5 |
   |          | attribute            |               |                 |
   |   1098   | Link Name attribute  |      ---      | Section 3.3.2.6 |
   +----------+----------------------+---------------+-----------------+

                       Table 7: Link Attribute TLVs
3.3.1.  Node Attribute TLVs

   Node attribute TLVs are the TLVs that may be encoded in the BGP-LS
   attribute with a node NLRI.  The following node attribute TLVs are
   defined:

Gredler, et al.           Expires May 22, 2014                 [Page 18]
Internet-Draft   Link-State Info Distribution using BGP    November 2013

   +-----------+----------------------+------------+-------------------+
   |  TLV Code | Description          |     Length | Value defined in: |
   |   Point   |                      |            |                   |
   +-----------+----------------------+------------+-------------------+
   |    263    | Multi-Topology       |   variable | Section 3.2.1.5   |
   |           | Identifier           |            |                   |
   |    1024   | Node Flag Bits       |          1 | Section 3.3.1.1   |
   |    1025   | Opaque Node          |   variable | Section 3.3.1.5   |
   |           | Properties           |            |                   |
   |    1026   | Node Name            |   variable | Section 3.3.1.3   |
   |    1027   | IS-IS Area           |   variable | Section 3.3.1.2   |
   |           | Identifier           |            |                   |
   |    1028   | IPv4 Router-ID of    |          4 | [RFC5305]/4.3     |
   |           | Local Node           |            |                   |
   |    1029   | IPv6 Router-ID of    |         16 | [RFC6119]/4.1     |
   |           | Local Node           |            |                   |
   +-----------+----------------------+------------+-------------------+

                       Table 5: Node Attribute TLVs

                                           3.3.3.  Prefix Attribute TLVs

   Prefixes are learned from the IGP topology (IS-IS or OSPF) with a set
   of IGP attributes (such as metric, route tags, etc.) that MUST be
   reflected into the LINK_STATE attribute.  This section describes the
   different attributes related to the IPv4/IPv6 prefixes.  Prefix
   Attributes TLVs SHOULD be used when advertising NLRI types 3 and 4
   only.  The following attributes TLVs are defined:

Gredler, et al.           Expires May 22, 2014                 [Page 25]
Internet-Draft   Link-State Info Distribution using BGP    November 2013

   +-------------+---------------------+--------------+----------------+
   |   TLV Code  | Description         |       Length | Reference      |
   |    Point    |                     |              |                |
   +-------------+---------------------+--------------+----------------+
   |     1152    | IGP Flags           |            1 | Section        |
   |             |                     |              | 3.3.3.1        |
   |     1153    | Route Tag           |          4*n | Section        |
   |             |                     |              | 3.3.3.2        |
   |     1154    | Extended Tag        |          8*n | Section        |
   |             |                     |              | 3.3.3.3        |
   |     1155    | Prefix Metric       |            4 | Section        |
   |             |                     |              | 3.3.3.4        |
   |     1156    | OSPF Forwarding     |            4 | Section        |
   |             | Address             |              | 3.3.3.5        |
   |     1157    | Opaque Prefix       |     variable | Section        |
   |             | Attribute           |              | 3.3.3.6        |
   +-------------+---------------------+--------------+----------------+

                      Table 9: Prefix Attribute TLVs

 * @author pac
 *
 */
public class LinkStateAttribute  extends PathAttribute{


	/** Link Attribute TLVs */
	AdministrativeGroupLinkAttribTLV administrativeGroupTLV;
	MaximumLinkBandwidthLinkAttribTLV maximumLinkBandwidthTLV;
	MaxReservableBandwidthLinkAttribTLV maxReservableBandwidthTLV; 
	UnreservedBandwidthLinkAttribTLV unreservedBandwidthTLV;
	LinkProtectionTypeLinkAttribTLV linkProtectionTLV;
	MetricLinkAttribTLV metricTLV;
	AvailableLabels availableLabels;//Añadida por nosotros
	IPv4RouterIDLocalNodeLinkAttribTLV IPv4RouterIDLocalNodeLATLV;
	IPv4RouterIDRemoteNodeLinkAttribTLV IPv4RouterIDRemoteNodeLATLV;
	DefaultTEMetricLinkAttribTLV TEMetricTLV;
	//********** RUBEN *************
	SharedRiskLinkGroupAttribTLV SharedRiskLinkGroupATLV;
	TransceiverClassAndAppAttribTLV TransceiverClassAndAppATLV;
	//MF_OTPAttribTLV MF_OTP_ATLV;
	//******************************
	

	/** NODE ATTRIBUTE TLVs */
	NodeFlagBitsNodeAttribTLV nodeFlagBitsTLV;
	NodeNameNodeAttribTLV nodeNameTLV;
	IS_IS_AreaIdentifierNodeAttribTLV areaIDTLV;
//	IPv4RouterIDLocalNodeNodeAttribTLV IPv4RouterIDLocalNodeNATLV;
	SidLabelNodeAttribTLV sidLabelTLV;

	/** PREFIX ATTRIBUTE TLVs */
	IGPFlagBitsPrefixAttribTLV igpFlagBitsTLV;
	RouteTagPrefixAttribTLV routeTagTLV;
	PrefixMetricPrefixAttribTLV prefixMetricTLV;
	OSPFForwardingAddressPrefixAttribTLV OSPFForwardingAddrTLV;


	public LinkStateAttribute(){		
		super();
		//los flags estaban mal puestos
		this.optionalBit = true;
		this.transitiveBit = false;
		this.typeCode = PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_BGP_LS_ATTRIBUTE;


	}
	public LinkStateAttribute(byte []bytes, int offset){
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		//Encode LinkStateAttribute
		pathAttributeLength=0;
		//LINK ATTRIBUTES
		if (maximumLinkBandwidthTLV!=null){
			maximumLinkBandwidthTLV.encode();
			pathAttributeLength=pathAttributeLength+maximumLinkBandwidthTLV.getTotalTLVLength();
		}
		if (maxReservableBandwidthTLV!=null){
			maxReservableBandwidthTLV.encode();
			pathAttributeLength=pathAttributeLength+maxReservableBandwidthTLV.getTotalTLVLength();
		}

		if (unreservedBandwidthTLV!=null){
			unreservedBandwidthTLV.encode();
			pathAttributeLength=pathAttributeLength+unreservedBandwidthTLV.getTotalTLVLength();
		}

		if (metricTLV!=null){
			metricTLV.encode();
			pathAttributeLength=pathAttributeLength+metricTLV.getTotalTLVLength();
		}

		if(administrativeGroupTLV!=null){
			administrativeGroupTLV.encode();
			pathAttributeLength=pathAttributeLength+administrativeGroupTLV.getTotalTLVLength();
		}

		if(linkProtectionTLV!=null){
			linkProtectionTLV.encode();
			pathAttributeLength=pathAttributeLength+linkProtectionTLV.getTotalTLVLength();
		}

		if(IPv4RouterIDLocalNodeLATLV!=null){
			IPv4RouterIDLocalNodeLATLV.encode();
			pathAttributeLength=pathAttributeLength+IPv4RouterIDLocalNodeLATLV.getTotalTLVLength();
		}
		if(IPv4RouterIDRemoteNodeLATLV!=null){
			IPv4RouterIDRemoteNodeLATLV.encode();
			pathAttributeLength=pathAttributeLength+IPv4RouterIDRemoteNodeLATLV.getTotalTLVLength();
		}
		if(TEMetricTLV!=null){
			TEMetricTLV.encode();
			pathAttributeLength=pathAttributeLength+TEMetricTLV.getTotalTLVLength();
		}
		//********** RUBEN *************
		if(SharedRiskLinkGroupATLV!=null){
			SharedRiskLinkGroupATLV.encode();
			pathAttributeLength=pathAttributeLength+SharedRiskLinkGroupATLV.getTotalTLVLength();
		}	
		if(TransceiverClassAndAppATLV!=null){
			TransceiverClassAndAppATLV.encode();
			pathAttributeLength=pathAttributeLength+TransceiverClassAndAppATLV.getTotalTLVLength();
		}
//		if(MF_OTP_ATLV!=null){
//			MF_OTP_ATLV.encode();
//			pathAttributeLength=pathAttributeLength+MF_OTP_ATLV.getTotalTLVLength();
//		}
		//******************************
		
		//NODE Attributes

		if(nodeFlagBitsTLV!=null){
			nodeFlagBitsTLV.encode();
			pathAttributeLength=pathAttributeLength+nodeFlagBitsTLV.getTotalTLVLength();
		}
		if(nodeNameTLV!=null){
			nodeNameTLV.encode();
			pathAttributeLength=pathAttributeLength+nodeNameTLV.getTotalTLVLength();
		}
		if(areaIDTLV!=null){
			areaIDTLV.encode();
			pathAttributeLength=pathAttributeLength+areaIDTLV.getTotalTLVLength();
		}
//		if(IPv4RouterIDLocalNodeNATLV!=null){
//			IPv4RouterIDLocalNodeNATLV.encode();
//			pathAttributeLength=pathAttributeLength+IPv4RouterIDLocalNodeNATLV.getTotalTLVLength();
//		}
		if(sidLabelTLV!=null){
			sidLabelTLV.encode();
			pathAttributeLength=pathAttributeLength+sidLabelTLV.getTotalTLVLength();
		}
		//PREFIX Attributes
		if(igpFlagBitsTLV!=null){
			igpFlagBitsTLV.encode();
			pathAttributeLength=pathAttributeLength+igpFlagBitsTLV.getTotalTLVLength();
		}
		if(routeTagTLV!=null){
			routeTagTLV.encode();
			pathAttributeLength=pathAttributeLength+routeTagTLV.getTotalTLVLength();
		}
		if(prefixMetricTLV!=null){
			prefixMetricTLV.encode();
			pathAttributeLength=pathAttributeLength+prefixMetricTLV.getTotalTLVLength();
		}
		if(OSPFForwardingAddrTLV!=null){
			OSPFForwardingAddrTLV.encode();
			pathAttributeLength=pathAttributeLength+OSPFForwardingAddrTLV.getTotalTLVLength();
		}			
		if (availableLabels != null){
			try {
				availableLabels.encode();
			} catch (MalformedOSPFSubTLVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pathAttributeLength=pathAttributeLength+availableLabels.getTotalTLVLength();
		}

		//Length
		this.setPathAttributeLength(pathAttributeLength);
		this.bytes=new byte[this.length];

		//Encode Header
		encodeHeader();
		//Write the bytes
		int offset=mandatoryLength;
		if (maximumLinkBandwidthTLV!=null){
			System.arraycopy(maximumLinkBandwidthTLV.getTlv_bytes(),0, this.bytes,offset, maximumLinkBandwidthTLV.getTotalTLVLength());
			offset=offset+maximumLinkBandwidthTLV.getTotalTLVLength();
		}
		if (maxReservableBandwidthTLV!=null){
			System.arraycopy(maxReservableBandwidthTLV.getTlv_bytes(),0, this.bytes,offset, maxReservableBandwidthTLV.getTotalTLVLength());
			offset=offset+maxReservableBandwidthTLV.getTotalTLVLength();
		}
		if (unreservedBandwidthTLV!=null){
			System.arraycopy(unreservedBandwidthTLV.getTlv_bytes(),0, this.bytes,offset, unreservedBandwidthTLV.getTotalTLVLength());
			offset=offset+unreservedBandwidthTLV.getTotalTLVLength();
		}

		if (metricTLV!=null){
			System.arraycopy(metricTLV.getTlv_bytes(),0, this.bytes,offset, metricTLV.getTotalTLVLength());
			offset=offset+metricTLV.getTotalTLVLength();
		}

		if (administrativeGroupTLV!=null){
			System.arraycopy(administrativeGroupTLV.getTlv_bytes(),0, this.bytes,offset, administrativeGroupTLV.getTotalTLVLength());
			offset=offset+administrativeGroupTLV.getTotalTLVLength();
		}

		if (linkProtectionTLV!=null){
			System.arraycopy(linkProtectionTLV.getTlv_bytes(),0, this.bytes,offset, linkProtectionTLV.getTotalTLVLength());
			offset=offset+linkProtectionTLV.getTotalTLVLength();
		}
		if (IPv4RouterIDLocalNodeLATLV!=null){
			System.arraycopy(IPv4RouterIDLocalNodeLATLV.getTlv_bytes(),0, this.bytes,offset, IPv4RouterIDLocalNodeLATLV.getTotalTLVLength());
			offset=offset+IPv4RouterIDLocalNodeLATLV.getTotalTLVLength();
		}
		if (IPv4RouterIDRemoteNodeLATLV!=null){
			System.arraycopy(IPv4RouterIDRemoteNodeLATLV.getTlv_bytes(),0, this.bytes,offset, IPv4RouterIDRemoteNodeLATLV.getTotalTLVLength());
			offset=offset+IPv4RouterIDRemoteNodeLATLV.getTotalTLVLength();
		}

		if(TEMetricTLV!=null){
			System.arraycopy(TEMetricTLV.getTlv_bytes(),0, this.bytes,offset, TEMetricTLV.getTotalTLVLength());
			offset=offset+TEMetricTLV.getTotalTLVLength();
		}

		if(SharedRiskLinkGroupATLV!=null){
			System.arraycopy(SharedRiskLinkGroupATLV.getTlv_bytes(),0, this.bytes,offset, SharedRiskLinkGroupATLV.getTotalTLVLength());
			offset=offset+SharedRiskLinkGroupATLV.getTotalTLVLength();
		}
		
		if(TransceiverClassAndAppATLV!=null){
			System.arraycopy(TransceiverClassAndAppATLV.getTlv_bytes(),0, this.bytes,offset, TransceiverClassAndAppATLV.getTotalTLVLength());
			offset=offset+TransceiverClassAndAppATLV.getTotalTLVLength();
		}
		
//		if(MF_OTP_ATLV!=null){
//			System.arraycopy(MF_OTP_ATLV.getTlv_bytes(),0, this.bytes,offset, MF_OTP_ATLV.getTotalTLVLength());
//			offset=offset+MF_OTP_ATLV.getTotalTLVLength();
//		}
		//******************************
		
		if(areaIDTLV!=null){
			System.arraycopy(areaIDTLV.getTlv_bytes(),0, this.bytes,offset, areaIDTLV.getTotalTLVLength());
			offset=offset+areaIDTLV.getTotalTLVLength();
		}

		if(nodeFlagBitsTLV!=null){
			System.arraycopy(nodeFlagBitsTLV.getTlv_bytes(),0, this.bytes,offset, nodeFlagBitsTLV.getTotalTLVLength());
			offset=offset+nodeFlagBitsTLV.getTotalTLVLength();
		}

		if(nodeNameTLV!=null){
			System.arraycopy(nodeNameTLV.getTlv_bytes(),0, this.bytes,offset, nodeNameTLV.getTotalTLVLength());
			offset=offset+nodeNameTLV.getTotalTLVLength();
		}

		if(sidLabelTLV!=null){
			System.arraycopy(sidLabelTLV.getTlv_bytes(),0, this.bytes,offset, sidLabelTLV.getTotalTLVLength());
			offset=offset+sidLabelTLV.getTotalTLVLength();
		}

//		if(IPv4RouterIDLocalNodeNATLV!=null){
//			System.arraycopy(IPv4RouterIDLocalNodeNATLV.getTlv_bytes(),0, this.bytes,offset, IPv4RouterIDLocalNodeNATLV.getTotalTLVLength());
//			offset=offset+IPv4RouterIDLocalNodeNATLV.getTotalTLVLength();
//		}

		if(igpFlagBitsTLV!=null){
			System.arraycopy(igpFlagBitsTLV.getTlv_bytes(),0, this.bytes,offset, igpFlagBitsTLV.getTotalTLVLength());
			offset=offset+igpFlagBitsTLV.getTotalTLVLength();
		}

		if(routeTagTLV!=null){
			System.arraycopy(routeTagTLV.getTlv_bytes(),0, this.bytes,offset, routeTagTLV.getTotalTLVLength());
			offset=offset+routeTagTLV.getTotalTLVLength();
		}

		if(prefixMetricTLV!=null){
			System.arraycopy(prefixMetricTLV.getTlv_bytes(),0, this.bytes,offset, prefixMetricTLV.getTotalTLVLength());
			offset=offset+prefixMetricTLV.getTotalTLVLength();
		}

		if(OSPFForwardingAddrTLV!=null){
			System.arraycopy(OSPFForwardingAddrTLV.getTlv_bytes(),0, this.bytes,offset, OSPFForwardingAddrTLV.getTotalTLVLength());
			offset=offset+OSPFForwardingAddrTLV.getTotalTLVLength();
		}
		if (availableLabels!=null){
			System.arraycopy(availableLabels.getTlv_bytes(),0, this.bytes,offset, availableLabels.getTotalTLVLength());
			offset=offset+availableLabels.getTotalTLVLength();
		}

	}
	public void decode(){
		boolean fin=false;
		int offset = mandatoryLength;
		//Decoding LinkState Attribute
		while (!fin) {
			int TLVType=BGP4TLVFormat.getType(this.bytes, offset);
			int TLVLength=BGP4TLVFormat.getTotalTLVLength(this.bytes, offset);
			//System.out.println ("TLV Type: "+TLVType+" TLV Legnth: "+TLVLength);
			//diferenciar en links y nodes para que no de error de compilacion ya que los id de los IPv4 son iguales 
			switch (TLVType){
			//LINK ATTRIBUTES
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MAX_RESERVABLE_BANDWITH:
				this.maxReservableBandwidthTLV=new MaxReservableBandwidthLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MAXIMUM_BANDWITH:
				this.maximumLinkBandwidthTLV=new MaximumLinkBandwidthLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_UNRESERVED_BANDWITH:
				this.unreservedBandwidthTLV=new UnreservedBandwidthLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_METRIC:
				this.metricTLV=new MetricLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_ADMINISTRATIVE_GROUP:
				this.administrativeGroupTLV=new AdministrativeGroupLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_LINK_PROTECTION_TYPE:
				this.linkProtectionTLV=new LinkProtectionTypeLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_LOCAL_NODE:
				this.IPv4RouterIDLocalNodeLATLV=new IPv4RouterIDLocalNodeLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_REMOTE_NODE:
				this.IPv4RouterIDRemoteNodeLATLV=new IPv4RouterIDRemoteNodeLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_TE_DEFAULT_METRIC:
				this.TEMetricTLV=new DefaultTEMetricLinkAttribTLV(this.bytes, offset);
				break;
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_SHARED_RISK_LINK_GROUP:
				this.SharedRiskLinkGroupATLV=new SharedRiskLinkGroupAttribTLV(this.bytes, offset);
				break;	
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_TRANSCEIVER_CLASS_AND_APPLICATION:
				this.TransceiverClassAndAppATLV=new TransceiverClassAndAppAttribTLV(this.bytes, offset);
				break;	
//			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MF_OTP:
//				this.MF_OTP_ATLV=new MF_OTPAttribTLV(this.bytes, offset);
//				break;
		    //******************************	
			case LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_AVAILABLELABELS:
				try {
					this.availableLabels=new AvailableLabels(this.bytes, offset);
				} catch (MalformedOSPFSubTLVException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				//NODE ATTRIBUTES

				/* Se utiliza tanto en link attrib tlvs como en nodos ya que tiene el mismo type code para ambos*/
				/**case LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_LOCAL_NODE:
						this.IPv4RouterIDLocalNodeNATLV=new IPv4RouterIDLocalNodeNodeAttribTLV(this.bytes, offset);

				 */	
			case LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IS_IS_AREA_ID:
				this.areaIDTLV=new IS_IS_AreaIdentifierNodeAttribTLV(this.bytes, offset);
				break;				
			case LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_NODE_FLAG_BITS:
				this.nodeFlagBitsTLV=new NodeFlagBitsNodeAttribTLV(this.bytes, offset);
				break;

	        case LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_NODE_NAME:
				this.nodeNameTLV=new NodeNameNodeAttribTLV(this.bytes, offset);
				break;

			case LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_SID_LABEL:
				this.sidLabelTLV=new SidLabelNodeAttribTLV(this.bytes, offset);
				break;
				
				//PREFIX ATTRIBUTES

			case LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_IGP_FLAGS:
				this.igpFlagBitsTLV=new IGPFlagBitsPrefixAttribTLV(this.bytes, offset);
				break;


			case LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_OSPF_FORWARDING_ADDRESS:
				this.OSPFForwardingAddrTLV=new OSPFForwardingAddressPrefixAttribTLV(this.bytes, offset);
				break;

			case LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_PREFIX_METRIC:
				this.prefixMetricTLV=new PrefixMetricPrefixAttribTLV(this.bytes, offset);
				break;

			case LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_ROUTE_TAG:
				this.routeTagTLV=new RouteTagPrefixAttribTLV(this.bytes, offset);
				break;

			default:
				log.warn("Unknown TLV found: "+TLVType);


			}

			offset=offset+TLVLength;
			if (offset>=(this.pathAttributeLength)){
				fin=true;
			}

		}
	}


	public IGPFlagBitsPrefixAttribTLV getIgpFlagBitsTLV() {
		return igpFlagBitsTLV;
	}
	public void setIgpFlagBitsTLV(IGPFlagBitsPrefixAttribTLV igpFlagBitsTLV) {
		this.igpFlagBitsTLV = igpFlagBitsTLV;
	}
	public RouteTagPrefixAttribTLV getRouteTagTLV() {
		return routeTagTLV;
	}
	public void setRouteTagTLV(RouteTagPrefixAttribTLV routeTagTLV) {
		this.routeTagTLV = routeTagTLV;
	}
	public PrefixMetricPrefixAttribTLV getPrefixMetricTLV() {
		return prefixMetricTLV;
	}
	public void setPrefixMetricTLV(PrefixMetricPrefixAttribTLV prefixMetricTLV) {
		this.prefixMetricTLV = prefixMetricTLV;
	}
	public OSPFForwardingAddressPrefixAttribTLV getOSPFForwardingAddrTLV() {
		return OSPFForwardingAddrTLV;
	}
	public void setOSPFForwardingAddrTLV(
			OSPFForwardingAddressPrefixAttribTLV oSPFForwardingAddrTLV) {
		OSPFForwardingAddrTLV = oSPFForwardingAddrTLV;
	}
	public MaximumLinkBandwidthLinkAttribTLV getMaximumLinkBandwidthTLV() {
		return maximumLinkBandwidthTLV;
	}
	public void setMaximumLinkBandwidthTLV(
			MaximumLinkBandwidthLinkAttribTLV maximumLinkBandwidthTLV) {
		this.maximumLinkBandwidthTLV = maximumLinkBandwidthTLV;
	}
	public MaxReservableBandwidthLinkAttribTLV getMaxReservableBandwidthTLV() {
		return maxReservableBandwidthTLV;
	}
	public void setMaxReservableBandwidthTLV(
			MaxReservableBandwidthLinkAttribTLV maxReservableBandwidthTLV) {
		this.maxReservableBandwidthTLV = maxReservableBandwidthTLV;
	}
	public UnreservedBandwidthLinkAttribTLV getUnreservedBandwidthTLV() {
		return unreservedBandwidthTLV;
	}
	public void setUnreservedBandwidthTLV(
			UnreservedBandwidthLinkAttribTLV unreservedBandwidthTLV) {
		this.unreservedBandwidthTLV = unreservedBandwidthTLV;
	}

	public MetricLinkAttribTLV getMetricTLV() {
		return metricTLV;
	}
	public void setMetricTLV(MetricLinkAttribTLV metricTLV) {
		this.metricTLV = metricTLV;
	}
	
	public AdministrativeGroupLinkAttribTLV getAdministrativeGroupTLV() {
		return administrativeGroupTLV;
	}
	public void setAdministrativeGroupTLV(AdministrativeGroupLinkAttribTLV administrativeGroupTLV) {
		this.administrativeGroupTLV = administrativeGroupTLV;
	}
	public LinkProtectionTypeLinkAttribTLV getLinkProtectionTLV() {
		return linkProtectionTLV;
	}
	public void setLinkProtectionTLV(
			LinkProtectionTypeLinkAttribTLV linkProtectionTLV) {
		this.linkProtectionTLV = linkProtectionTLV;
	}
	public IPv4RouterIDLocalNodeLinkAttribTLV getIPv4RouterIDLocalNodeLATLV() {
		return IPv4RouterIDLocalNodeLATLV;
	}
	public void setIPv4RouterIDLocalNodeLATLV(IPv4RouterIDLocalNodeLinkAttribTLV iPv4RouterIDLocalNodeLATLV) {
		IPv4RouterIDLocalNodeLATLV = iPv4RouterIDLocalNodeLATLV;
	}
	public IPv4RouterIDRemoteNodeLinkAttribTLV getIPv4RouterIDRemoteNodeLATLV() {
		return IPv4RouterIDRemoteNodeLATLV;
	}
	public void setIPv4RouterIDRemoteNodeLATLV(IPv4RouterIDRemoteNodeLinkAttribTLV iPv4RouterIDRemoteNodeLATLV) {
		IPv4RouterIDRemoteNodeLATLV = iPv4RouterIDRemoteNodeLATLV;
	}
	public DefaultTEMetricLinkAttribTLV getTEMetricTLV() {
		return TEMetricTLV;
	}
	public void setTEMetricTLV(DefaultTEMetricLinkAttribTLV tEMetricTLV) {
		TEMetricTLV = tEMetricTLV;
	}	
	
	
	//******************************
	
	public NodeFlagBitsNodeAttribTLV getNodeFlagBitsTLV() {
		return nodeFlagBitsTLV;
	}
	public SharedRiskLinkGroupAttribTLV getSharedRiskLinkGroupATLV() {
		return SharedRiskLinkGroupATLV;
	}
	public void setSharedRiskLinkGroupATLV(SharedRiskLinkGroupAttribTLV sharedRiskLinkGroupATLV) {
		SharedRiskLinkGroupATLV = sharedRiskLinkGroupATLV;
	}
	public TransceiverClassAndAppAttribTLV getTransceiverClassAndAppATLV() {
		return TransceiverClassAndAppATLV;
	}
	public void setTransceiverClassAndAppATLV(TransceiverClassAndAppAttribTLV transceiverClassAndAppATLV) {
		TransceiverClassAndAppATLV = transceiverClassAndAppATLV;
	}
//	public MF_OTPAttribTLV getMF_OTP_ATLV() {
//		return MF_OTP_ATLV;
//	}
//	public void setMF_OTP_ATLV(MF_OTPAttribTLV mF_OTP_ATLV) {
//		MF_OTP_ATLV = mF_OTP_ATLV;
//	}
	public void setNodeFlagBitsTLV(NodeFlagBitsNodeAttribTLV nodeFlagBitsTLV) {
		this.nodeFlagBitsTLV = nodeFlagBitsTLV;
	}
	public SidLabelNodeAttribTLV getSidLabelTLV() {
		return sidLabelTLV;
	}
	public void setSidLabelTLV(SidLabelNodeAttribTLV sidLabelTLV) {
		this.sidLabelTLV = sidLabelTLV;
	}
	public NodeNameNodeAttribTLV getNodeNameTLV() {
		return nodeNameTLV;
	}
	public void setNodeNameTLV(NodeNameNodeAttribTLV nodeNameTLV) {
		this.nodeNameTLV = nodeNameTLV;
	}
	public IS_IS_AreaIdentifierNodeAttribTLV getAreaIDTLV() {
		return areaIDTLV;
	}
	public void setAreaIDTLV(IS_IS_AreaIdentifierNodeAttribTLV areaIDTLV) {
		this.areaIDTLV = areaIDTLV;
	}
//	public IPv4RouterIDLocalNodeNodeAttribTLV getIPv4RouterIDLocalNodeNATLV() {
//		return IPv4RouterIDLocalNodeNATLV;
//	}
//	public void setIPv4RouterIDLocalNodeNATLV(IPv4RouterIDLocalNodeNodeAttribTLV iPv4RouterIDLocalNodeNATLV) {
//		IPv4RouterIDLocalNodeNATLV = iPv4RouterIDLocalNodeNATLV;
//	}

	public AvailableLabels getAvailableLabels() {
		return availableLabels;
	}
	public void setAvailableLabels(AvailableLabels availableLabels) {
		this.availableLabels = availableLabels;
	}
	@Override
	public String toString() {
		return "LinkStateAttribute [administrativeGroupTLV=" + administrativeGroupTLV + ", maximumLinkBandwidthTLV="
				+ maximumLinkBandwidthTLV + ", maxReservableBandwidthTLV=" + maxReservableBandwidthTLV
				+ ", unreservedBandwidthTLV=" + unreservedBandwidthTLV + ", linkProtectionTLV=" + linkProtectionTLV
				+ ", metricTLV=" + metricTLV + ", availableLabels=" + ", IPv4RouterIDLocalNodeLATLV="
				+ IPv4RouterIDLocalNodeLATLV + ", IPv4RouterIDRemoteNodeLATLV=" + IPv4RouterIDRemoteNodeLATLV
				+ ", TEMetricTLV=" + TEMetricTLV + ", SharedRiskLinkGroupATLV=" + SharedRiskLinkGroupATLV
				+ ", TransceiverClassAndAppATLV=" + TransceiverClassAndAppATLV + ", nodeFlagBitsTLV=" + nodeFlagBitsTLV
				+ ", nodeNameTLV=" + nodeNameTLV + ", areaIDTLV=" + areaIDTLV + ", sidLabelTLV=" + sidLabelTLV
				+ ", igpFlagBitsTLV=" + igpFlagBitsTLV + ", routeTagTLV=" + routeTagTLV + ", prefixMetricTLV="
				+ prefixMetricTLV + ", OSPFForwardingAddrTLV=" + OSPFForwardingAddrTLV + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((IPv4RouterIDLocalNodeLATLV == null) ? 0 : IPv4RouterIDLocalNodeLATLV.hashCode());
		result = prime * result + ((IPv4RouterIDRemoteNodeLATLV == null) ? 0 : IPv4RouterIDRemoteNodeLATLV.hashCode());
		result = prime * result + ((OSPFForwardingAddrTLV == null) ? 0 : OSPFForwardingAddrTLV.hashCode());
		result = prime * result + ((SharedRiskLinkGroupATLV == null) ? 0 : SharedRiskLinkGroupATLV.hashCode());
		result = prime * result + ((TEMetricTLV == null) ? 0 : TEMetricTLV.hashCode());
		result = prime * result + ((TransceiverClassAndAppATLV == null) ? 0 : TransceiverClassAndAppATLV.hashCode());
		result = prime * result + ((administrativeGroupTLV == null) ? 0 : administrativeGroupTLV.hashCode());
		result = prime * result + ((areaIDTLV == null) ? 0 : areaIDTLV.hashCode());
		result = prime * result + ((igpFlagBitsTLV == null) ? 0 : igpFlagBitsTLV.hashCode());
		result = prime * result + ((linkProtectionTLV == null) ? 0 : linkProtectionTLV.hashCode());
		result = prime * result + ((maxReservableBandwidthTLV == null) ? 0 : maxReservableBandwidthTLV.hashCode());
		result = prime * result + ((maximumLinkBandwidthTLV == null) ? 0 : maximumLinkBandwidthTLV.hashCode());
		result = prime * result + ((metricTLV == null) ? 0 : metricTLV.hashCode());
		result = prime * result + ((nodeFlagBitsTLV == null) ? 0 : nodeFlagBitsTLV.hashCode());
		result = prime * result + ((nodeNameTLV == null) ? 0 : nodeNameTLV.hashCode());
		result = prime * result + ((prefixMetricTLV == null) ? 0 : prefixMetricTLV.hashCode());
		result = prime * result + ((routeTagTLV == null) ? 0 : routeTagTLV.hashCode());
		result = prime * result + ((sidLabelTLV == null) ? 0 : sidLabelTLV.hashCode());
		result = prime * result + ((unreservedBandwidthTLV == null) ? 0 : unreservedBandwidthTLV.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (getClass() != obj.getClass())
			return false;
		LinkStateAttribute other = (LinkStateAttribute) obj;
		if (IPv4RouterIDLocalNodeLATLV == null) {
			if (other.IPv4RouterIDLocalNodeLATLV != null)
				return false;
		} else if (!IPv4RouterIDLocalNodeLATLV.equals(other.IPv4RouterIDLocalNodeLATLV))
			return false;
		if (IPv4RouterIDRemoteNodeLATLV == null) {
			if (other.IPv4RouterIDRemoteNodeLATLV != null)
				return false;
		} else if (!IPv4RouterIDRemoteNodeLATLV.equals(other.IPv4RouterIDRemoteNodeLATLV))
			return false;
		if (OSPFForwardingAddrTLV == null) {
			if (other.OSPFForwardingAddrTLV != null)
				return false;
		} else if (!OSPFForwardingAddrTLV.equals(other.OSPFForwardingAddrTLV))
			return false;
		if (SharedRiskLinkGroupATLV == null) {
			if (other.SharedRiskLinkGroupATLV != null)
				return false;
		} else if (!SharedRiskLinkGroupATLV.equals(other.SharedRiskLinkGroupATLV))
			return false;
		if (TEMetricTLV == null) {
			if (other.TEMetricTLV != null)
				return false;
		} else if (!TEMetricTLV.equals(other.TEMetricTLV))
			return false;
		if (TransceiverClassAndAppATLV == null) {
			if (other.TransceiverClassAndAppATLV != null)
				return false;
		} else if (!TransceiverClassAndAppATLV.equals(other.TransceiverClassAndAppATLV))
			return false;
		if (administrativeGroupTLV == null) {
			if (other.administrativeGroupTLV != null)
				return false;
		} else if (!administrativeGroupTLV.equals(other.administrativeGroupTLV))
			return false;
		if (areaIDTLV == null) {
			if (other.areaIDTLV != null)
				return false;
		} else if (!areaIDTLV.equals(other.areaIDTLV))
			return false;
		if (igpFlagBitsTLV == null) {
			if (other.igpFlagBitsTLV != null)
				return false;
		} else if (!igpFlagBitsTLV.equals(other.igpFlagBitsTLV))
			return false;
		if (linkProtectionTLV == null) {
			if (other.linkProtectionTLV != null)
				return false;
		} else if (!linkProtectionTLV.equals(other.linkProtectionTLV))
			return false;
		if (maxReservableBandwidthTLV == null) {
			if (other.maxReservableBandwidthTLV != null)
				return false;
		} else if (!maxReservableBandwidthTLV.equals(other.maxReservableBandwidthTLV))
			return false;
		if (maximumLinkBandwidthTLV == null) {
			if (other.maximumLinkBandwidthTLV != null)
				return false;
		} else if (!maximumLinkBandwidthTLV.equals(other.maximumLinkBandwidthTLV))
			return false;
		if (metricTLV == null) {
			if (other.metricTLV != null)
				return false;
		} else if (!metricTLV.equals(other.metricTLV))
			return false;
		if (nodeFlagBitsTLV == null) {
			if (other.nodeFlagBitsTLV != null)
				return false;
		} else if (!nodeFlagBitsTLV.equals(other.nodeFlagBitsTLV))
			return false;
		if (nodeNameTLV == null) {
			if (other.nodeNameTLV != null)
				return false;
		} else if (!nodeNameTLV.equals(other.nodeNameTLV))
			return false;
		if (prefixMetricTLV == null) {
			if (other.prefixMetricTLV != null)
				return false;
		} else if (!prefixMetricTLV.equals(other.prefixMetricTLV))
			return false;
		if (routeTagTLV == null) {
			if (other.routeTagTLV != null)
				return false;
		} else if (!routeTagTLV.equals(other.routeTagTLV))
			return false;
		if (sidLabelTLV == null) {
			if (other.sidLabelTLV != null)
				return false;
		} else if (!sidLabelTLV.equals(other.sidLabelTLV))
			return false;
		if (unreservedBandwidthTLV == null) {
			if (other.unreservedBandwidthTLV != null)
				return false;
		} else if (!unreservedBandwidthTLV.equals(other.unreservedBandwidthTLV))
			return false;
		return true;
	}

	



}
