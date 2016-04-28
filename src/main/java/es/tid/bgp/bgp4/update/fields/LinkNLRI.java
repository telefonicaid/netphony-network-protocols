package es.tid.bgp.bgp4.update.fields;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.LocalNodeDescriptorsTLV;
import es.tid.bgp.bgp4.update.tlv.RemoteNodeDescriptorsTLV;
import es.tid.bgp.bgp4.update.tlv.RoutingUniverseIdentifierTypes;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IPv4InterfaceAddressLinkDescriptorsSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IPv4NeighborAddressLinkDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IPv6InterfaceAddressLinkDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.IPv6NeighborAddressLinkDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.LinkDescriptorSubTLVTypes;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.LinkLocalRemoteIdentifiersLinkDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.MinMaxUndirectionalLinkDelayDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.MultiTopologyIDLinkDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalAvailableBandwidthDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalDelayVariationDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalLinkDelayDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalLinkLossDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalResidualBandwidthDescriptorSubTLV;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.UndirectionalUtilizedBandwidthDescriptorSubTLV;

/**
The Link NLRI (NLRI Type = 2) is shown in the following figure.

Gredler, et al.           Expires May 22, 2014                  [Page 9]
Internet-Draft   Link-State Info Distribution using BGP    November 2013

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+
   |  Protocol-ID  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           Identifier                          |
   |                            (64 bits)                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   //               Local Node Descriptors (variable)             //
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   //               Remote Node Descriptors (variable)            //
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   //                  Link Descriptors (variable)                //
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                      Figure 8: The Link NLRI format                  
                      
      The 'Protocol-ID' field can contain one of the following values:

      Type = 0: Unknown, The source of NLRI information could not be
      determined

      Type = 1: IS-IS Level 1, The NLRI information has been sourced by
      IS-IS Level 1

      Type = 2: IS-IS Level 2, The NLRI information has been sourced by
      IS-IS Level 2

      Type = 3: OSPF, The NLRI information has been sourced by OSPF

      Type = 4: Direct, The NLRI information has been sourced from local
      interface state

      Type = 5: Static, The NLRI information has been sourced by static
      configuration
      
  3.2.2.  Link Descriptors

   The 'Link Descriptor' field is a set of Type/Length/Value (TLV)
   triplets.  The format of each TLV is shown in Section 3.1.  The 'Link
   descriptor' TLVs uniquely identify a link between a pair of anchor
   Routers.  A link described by the Link descriptor TLVs actually is a
   "half-link", a unidirectional representation of a logical link.  In
   order to fully describe a single logical link two originating routers
   need to advertise a half-link each, i.e. two link NLRIs will be
   advertised.

   The format and semantics of the 'value' fields in most 'Link
   Descriptor' TLVs correspond to the format and semantics of value
   fields in IS-IS Extended IS Reachability sub-TLVs, defined in
   [RFC5305], [RFC5307] and [RFC6119].  Although the encodings for 'Link
   Descriptor' TLVs were originally defined for IS-IS, the TLVs can
   carry data sourced either by IS-IS or OSPF.

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
 * @author pac
 */
public class LinkNLRI extends LinkStateNLRI {
	private int protocolID;
	private long identifier;
	private LocalNodeDescriptorsTLV localNodeDescriptors;
	private RemoteNodeDescriptorsTLV remoteNodeDescriptorsTLV;
	//private LinkDescriptors linkDescriptors;
	
	//LINK DESCRIPTORS
	private LinkLocalRemoteIdentifiersLinkDescriptorSubTLV linkIdentifiersTLV;
	private IPv4InterfaceAddressLinkDescriptorsSubTLV ipv4InterfaceAddressTLV;
	private IPv4NeighborAddressLinkDescriptorSubTLV ipv4NeighborAddressTLV;
	private IPv6InterfaceAddressLinkDescriptorSubTLV ipv6InterfaceAddressTLV;
	private IPv6NeighborAddressLinkDescriptorSubTLV ipv6NeighborAddressTLV;
	private MultiTopologyIDLinkDescriptorSubTLV multiTopologyIDTLV;
	
	private UndirectionalLinkDelayDescriptorSubTLV undirectionalLinkDelayTLV;
	private MinMaxUndirectionalLinkDelayDescriptorSubTLV minMaxUndirectionalLinkDelayTLV;
	private UndirectionalDelayVariationDescriptorSubTLV undirectionalDelayVariationTLV;
	private UndirectionalLinkLossDescriptorSubTLV undirectionalLinkLossTLV;
	private UndirectionalResidualBandwidthDescriptorSubTLV undirectionalResidualBwTLV;
	private UndirectionalAvailableBandwidthDescriptorSubTLV undirectionalAvailableBwTLV;
	private UndirectionalUtilizedBandwidthDescriptorSubTLV undirectionalUtilizedBwTLV;
	
	public LinkNLRI(){
		//FIXME: BY DEFAULT L3?
		this.setIdentifier(RoutingUniverseIdentifierTypes.Level3Identifier);
		this.setNLRIType(NLRITypes.Link_NLRI);
		
	}
	
	public LinkNLRI(byte[] bytes, int offset) {//throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	@Override
	public void encode() {
		int len=4+1+8;//The four bytes of the header plus the 4 first bytes (Primeros cuatro bytes (protocol-id,reserved, instance identifier))
		if (localNodeDescriptors!=null){
			localNodeDescriptors.encode();
			len=len+localNodeDescriptors.getTotalTLVLength();		
		}

		if (remoteNodeDescriptorsTLV!=null){
			remoteNodeDescriptorsTLV.encode();
			len=len+remoteNodeDescriptorsTLV.getTotalTLVLength();
			
		}
		if (linkIdentifiersTLV!=null){
			linkIdentifiersTLV.encode();
			len=len+linkIdentifiersTLV.getTotalTLVLength();
		}
		if (ipv4InterfaceAddressTLV!=null){
			ipv4InterfaceAddressTLV.encode();
			len=len+ipv4InterfaceAddressTLV.getTotalTLVLength();
		}
		if (ipv4NeighborAddressTLV!=null){
			ipv4NeighborAddressTLV.encode();
			len=len+ipv4NeighborAddressTLV.getTotalTLVLength();
		}
		if (ipv6InterfaceAddressTLV!=null){
			ipv6InterfaceAddressTLV.encode();
			len=len+ipv6InterfaceAddressTLV.getTotalTLVLength();
		}
		if (ipv6NeighborAddressTLV!=null){
			ipv6NeighborAddressTLV.encode();
			len=len+ipv6NeighborAddressTLV.getTotalTLVLength();
		}
		if (multiTopologyIDTLV!=null){
			multiTopologyIDTLV.encode();
			len=len+multiTopologyIDTLV.getTotalTLVLength();
		}
		if(undirectionalLinkDelayTLV!=null){
			undirectionalLinkDelayTLV.encode();
			len += undirectionalLinkDelayTLV.getTotalTLVLength();
		}
		if(minMaxUndirectionalLinkDelayTLV!=null){
			minMaxUndirectionalLinkDelayTLV.encode();
			len += minMaxUndirectionalLinkDelayTLV.getTotalTLVLength();
		}
		if(undirectionalDelayVariationTLV!=null){
			undirectionalDelayVariationTLV.encode();
			len += undirectionalDelayVariationTLV.getTotalTLVLength();
		}
		if(undirectionalLinkLossTLV!=null){
			undirectionalLinkLossTLV.encode();
			len += undirectionalLinkLossTLV.getTotalTLVLength();
		}
		if(undirectionalResidualBwTLV!=null){
			undirectionalResidualBwTLV.encode();
			len += undirectionalResidualBwTLV.getTotalTLVLength();
		}
		if(undirectionalAvailableBwTLV!=null){
			undirectionalAvailableBwTLV.encode();
			len += undirectionalAvailableBwTLV.getTotalTLVLength();
		}
		if(undirectionalUtilizedBwTLV!=null){
			undirectionalUtilizedBwTLV.encode();
			len += undirectionalUtilizedBwTLV.getTotalTLVLength();
		}
		
		this.setTotalNLRILength(len); 
		this.setLength(len);
		this.bytes=new byte[len];
		this.encodeHeader();
		
		this.bytes[4]=(byte)protocolID;
		this.bytes[5]=(byte)(identifier>>>56 & 0xFF);
		this.bytes[6]=(byte)(identifier>>>48 & 0xFF);
		this.bytes[7]=(byte)(identifier >>> 40 & 0xFF);
		this.bytes[8]=(byte)(identifier>>>32 & 0xFF);
		this.bytes[9]=(byte)(identifier>>>24 & 0xFF);
		this.bytes[10]=(byte)(identifier >>> 16 & 0xFF);
		this.bytes[11]=(byte)(identifier >>>8 & 0xFF);
		this.bytes[12]=(byte)(identifier & 0xFF);
		
		int offset=13;
				
		if (localNodeDescriptors!=null){
			System.arraycopy(localNodeDescriptors.getTlv_bytes(), 0, this.bytes, offset, localNodeDescriptors.getTotalTLVLength());
			offset=offset+localNodeDescriptors.getTotalTLVLength();
		}
		
		if (remoteNodeDescriptorsTLV!=null){
			System.arraycopy(remoteNodeDescriptorsTLV.getTlv_bytes(), 0, this.bytes, offset, remoteNodeDescriptorsTLV.getTotalTLVLength());
			offset=offset+remoteNodeDescriptorsTLV.getTotalTLVLength();
		}
		
		
//		if (linkDescriptors!=null){
//			System.arraycopy(linkDescriptors.getBytes(), 0, this.bytes, offset, linkDescriptors.getLength());
//			offset=offset+linkDescriptors.getLength();
//		}
		if (linkIdentifiersTLV!=null){
			try{
			System.arraycopy(linkIdentifiersTLV.getTlv_bytes(), 0, this.bytes, offset, linkIdentifiersTLV.getTotalTLVLength());
			offset=offset+linkIdentifiersTLV.getTotalTLVLength();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		if (ipv4InterfaceAddressTLV!=null){
			System.arraycopy(ipv4InterfaceAddressTLV.getTlv_bytes(), 0, this.bytes, offset, ipv4InterfaceAddressTLV.getTotalTLVLength());
			offset=offset+ipv4InterfaceAddressTLV.getTotalTLVLength();
		}
		
		if (ipv4NeighborAddressTLV!=null){
			System.arraycopy(ipv4NeighborAddressTLV.getTlv_bytes(), 0, this.bytes, offset, ipv4NeighborAddressTLV.getTotalTLVLength());
			offset=offset+ipv4NeighborAddressTLV.getTotalTLVLength();
		}
		
		if (ipv6InterfaceAddressTLV!=null){
			System.arraycopy(ipv6InterfaceAddressTLV.getTlv_bytes(), 0, this.bytes, offset, ipv6InterfaceAddressTLV.getTotalTLVLength());
			offset=offset+ipv6InterfaceAddressTLV.getTotalTLVLength();
		}
		
		if (ipv6NeighborAddressTLV!=null){
			System.arraycopy(ipv6NeighborAddressTLV.getTlv_bytes(), 0, this.bytes, offset, ipv6NeighborAddressTLV.getTotalTLVLength());
			offset=offset+ipv6NeighborAddressTLV.getTotalTLVLength();
		}
	
		if (multiTopologyIDTLV!=null){
			System.arraycopy(multiTopologyIDTLV.getTlv_bytes(), 0, this.bytes, offset, multiTopologyIDTLV.getTotalTLVLength());
			offset=offset+multiTopologyIDTLV.getTotalTLVLength();
		}
		if (undirectionalLinkDelayTLV!=null){
			System.arraycopy(undirectionalLinkDelayTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalLinkDelayTLV.getTotalTLVLength());
			offset=offset+undirectionalLinkDelayTLV.getTotalTLVLength();
		}
		if (minMaxUndirectionalLinkDelayTLV!=null){
			System.arraycopy(minMaxUndirectionalLinkDelayTLV.getTlv_bytes(), 0, this.bytes, offset, minMaxUndirectionalLinkDelayTLV.getTotalTLVLength());
			offset=offset+minMaxUndirectionalLinkDelayTLV.getTotalTLVLength();
		}
		if (undirectionalDelayVariationTLV!=null){
			System.arraycopy(undirectionalDelayVariationTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalDelayVariationTLV.getTotalTLVLength());
			offset=offset+undirectionalDelayVariationTLV.getTotalTLVLength();
		}
		if (undirectionalLinkLossTLV!=null){
			System.arraycopy(undirectionalLinkLossTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalLinkLossTLV.getTotalTLVLength());
			offset=offset+undirectionalLinkLossTLV.getTotalTLVLength();
		}
		if (undirectionalResidualBwTLV!=null){
			System.arraycopy(undirectionalResidualBwTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalResidualBwTLV.getTotalTLVLength());
			offset=offset+undirectionalResidualBwTLV.getTotalTLVLength();
		}
		if (undirectionalAvailableBwTLV!=null){
			System.arraycopy(undirectionalAvailableBwTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalAvailableBwTLV.getTotalTLVLength());
			offset=offset+undirectionalAvailableBwTLV.getTotalTLVLength();
		}
		if (undirectionalUtilizedBwTLV!=null){
			System.arraycopy(undirectionalUtilizedBwTLV.getTlv_bytes(), 0, this.bytes, offset, undirectionalUtilizedBwTLV.getTotalTLVLength());
			offset=offset+undirectionalUtilizedBwTLV.getTotalTLVLength();
		}
		
		
	}
	public void decode(){
		int offset = 4; //Cabecera del LinkState NLRI
		protocolID = this.bytes[offset];
		offset=offset +1; //identifier
		
		byte[] ip=new byte[8]; 
		System.arraycopy(this.bytes,offset, ip, 0, 8);
				
		long routingUniverseIdentifieraux1 = ((  ((long)bytes[offset]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+1]<<16) & 0xFF0000) | (((long)bytes[offset+2]<<8) & 0xFF00) |(((long)bytes[offset+3]) & 0xFF);
		long routingUniverseIdentifieraux2 = ((  ((long)bytes[offset+4]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+5]<<16) & 0xFF0000) | (((long)bytes[offset+6]<<8) & 0xFF00) |(((long)bytes[offset+7]) & 0xFF);
		//this.setRoutingUniverseIdentifier((2^32)*routingUniverseIdentifieraux1+routingUniverseIdentifieraux2);
		this.setIdentifier((routingUniverseIdentifieraux1 <<32)&0xFFFFFFFF00000000L | routingUniverseIdentifieraux2);
		offset = offset +8;
	
		this.localNodeDescriptors=new LocalNodeDescriptorsTLV(this.bytes, offset);
		offset = offset + localNodeDescriptors.getTotalTLVLength();
		this.remoteNodeDescriptorsTLV=new RemoteNodeDescriptorsTLV(this.bytes, offset);
		offset = offset + remoteNodeDescriptorsTLV.getTotalTLVLength();
		boolean fin=false;
		if (offset>=(this.getTotalNLRILength()/*+4*/)){
			fin=true;
		}
		while (!fin) {
			int subTLVType=BGP4TLVFormat.getType(bytes, offset);
			int subTLVLength=BGP4TLVFormat.getTotalTLVLength(bytes, offset);
			
				switch (subTLVType){
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_LINKIDENTIFIERS:
					this.linkIdentifiersTLV=new LinkLocalRemoteIdentifiersLinkDescriptorSubTLV(bytes, offset);
					break;	
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv4INTERFACE:
					this.ipv4InterfaceAddressTLV=new IPv4InterfaceAddressLinkDescriptorsSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv4NEIGHBOR:
					this.ipv4NeighborAddressTLV=new IPv4NeighborAddressLinkDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6INTERFACE:
					this.ipv6InterfaceAddressTLV=new IPv6InterfaceAddressLinkDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv6NEIGHBOR:
					this.ipv6NeighborAddressTLV=new IPv6NeighborAddressLinkDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_MULTITOPOLOGY_ID:
					this.multiTopologyIDTLV=new MultiTopologyIDLinkDescriptorSubTLV(bytes, offset);
					break;
					
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLINKDELAY_ID:
					this.undirectionalLinkDelayTLV=new UndirectionalLinkDelayDescriptorSubTLV(bytes, offset);
					break;	
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_MINMAXUNDIRLINKDELAY_ID:
					this.minMaxUndirectionalLinkDelayTLV=new MinMaxUndirectionalLinkDelayDescriptorSubTLV(bytes, offset);
					break;	
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRDELAYVAR_ID:
					this.undirectionalDelayVariationTLV=new UndirectionalDelayVariationDescriptorSubTLV(bytes, offset);
					break;	
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLINKLOSS_ID:
					this.undirectionalLinkLossTLV=new UndirectionalLinkLossDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRESIDUALBW_ID:
					this.undirectionalResidualBwTLV=new UndirectionalResidualBandwidthDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRAVAILABLEBW_ID:
					this.undirectionalAvailableBwTLV=new UndirectionalAvailableBandwidthDescriptorSubTLV(bytes, offset);
					break;
				case LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLUTILIZEDBW_ID:
					this.undirectionalUtilizedBwTLV=new UndirectionalUtilizedBandwidthDescriptorSubTLV(bytes, offset);
					break;
			
				default:
					log.warn("Unknown TLV found, type :"+subTLVType);
					

				}
			
			offset=offset+subTLVLength;
			if (offset>=(this.getTotalNLRILength()/*+4*/)){
				//No more SubTLVs in LinkTLV
				fin=true;
			}
		}
	}
	
	
	public int getProtocolID() {
		return protocolID;
	}

	public void setProtocolID(int protocolID) {
		this.protocolID = protocolID;
	}


	public long getIdentifier() {
		return identifier;
	}

	public void setIdentifier(long identifier) {
		this.identifier = identifier;
	}

	public LocalNodeDescriptorsTLV getLocalNodeDescriptors() {
		return localNodeDescriptors;
	}

	public void setLocalNodeDescriptors(LocalNodeDescriptorsTLV localNodeDescriptors) {
		this.localNodeDescriptors = localNodeDescriptors;
	}

	public RemoteNodeDescriptorsTLV getRemoteNodeDescriptorsTLV() {
		return remoteNodeDescriptorsTLV;
	}

	public void setRemoteNodeDescriptorsTLV(
			RemoteNodeDescriptorsTLV remoteNodeDescriptorsTLV) {
		this.remoteNodeDescriptorsTLV = remoteNodeDescriptorsTLV;
	}

	public LinkLocalRemoteIdentifiersLinkDescriptorSubTLV getLinkIdentifiersTLV() {
		return linkIdentifiersTLV;
	}


	public void setLinkIdentifiersTLV(LinkLocalRemoteIdentifiersLinkDescriptorSubTLV linkIdentifiersTLV) {
		this.linkIdentifiersTLV = linkIdentifiersTLV;
	}


	public IPv4InterfaceAddressLinkDescriptorsSubTLV getIpv4InterfaceAddressTLV() {
		return ipv4InterfaceAddressTLV;
	}


	public void setIpv4InterfaceAddressTLV(
			IPv4InterfaceAddressLinkDescriptorsSubTLV ipv4InterfaceAddressTLV) {
		this.ipv4InterfaceAddressTLV = ipv4InterfaceAddressTLV;
	}


	public IPv4NeighborAddressLinkDescriptorSubTLV getIpv4NeighborAddressTLV() {
		return ipv4NeighborAddressTLV;
	}


	public void setIpv4NeighborAddressTLV(
			IPv4NeighborAddressLinkDescriptorSubTLV ipv4NeighborAddressTLV) {
		this.ipv4NeighborAddressTLV = ipv4NeighborAddressTLV;
	}


	public IPv6InterfaceAddressLinkDescriptorSubTLV getIpv6InterfaceAddressTLV() {
		return ipv6InterfaceAddressTLV;
	}


	public void setIpv6InterfaceAddressTLV(
			IPv6InterfaceAddressLinkDescriptorSubTLV ipv6InterfaceAddressTLV) {
		this.ipv6InterfaceAddressTLV = ipv6InterfaceAddressTLV;
	}


	public IPv6NeighborAddressLinkDescriptorSubTLV getIpv6NeighborAddressTLV() {
		return ipv6NeighborAddressTLV;
	}


	public void setIpv6NeighborAddressTLV(
			IPv6NeighborAddressLinkDescriptorSubTLV ipv6NeighborAddressTLV) {
		this.ipv6NeighborAddressTLV = ipv6NeighborAddressTLV;
	}


	public MultiTopologyIDLinkDescriptorSubTLV getMultiTopologyIDTLV() {
		return multiTopologyIDTLV;
	}


	public void setMultiTopologyIDTLV(MultiTopologyIDLinkDescriptorSubTLV multiTopologyIDTLV) {
		this.multiTopologyIDTLV = multiTopologyIDTLV;
	}

	public UndirectionalLinkDelayDescriptorSubTLV getUndirectionalLinkDelayTLV() {
		return undirectionalLinkDelayTLV;
	}

	public void setUndirectionalLinkDelayTLV(UndirectionalLinkDelayDescriptorSubTLV undirectionalLinkDelayTLV) {
		this.undirectionalLinkDelayTLV = undirectionalLinkDelayTLV;
	}

	public MinMaxUndirectionalLinkDelayDescriptorSubTLV getMinMaxUndirectionalLinkDelayTLV() {
		return minMaxUndirectionalLinkDelayTLV;
	}

	public void setMinMaxUndirectionalLinkDelayTLV(
			MinMaxUndirectionalLinkDelayDescriptorSubTLV minMaxUndirectionalLinkDelayTLV) {
		this.minMaxUndirectionalLinkDelayTLV = minMaxUndirectionalLinkDelayTLV;
	}

	public UndirectionalDelayVariationDescriptorSubTLV getUndirectionalDelayVariationTLV() {
		return undirectionalDelayVariationTLV;
	}

	public void setUndirectionalDelayVariationTLV(
			UndirectionalDelayVariationDescriptorSubTLV undirectionalDelayVariationTLV) {
		this.undirectionalDelayVariationTLV = undirectionalDelayVariationTLV;
	}

	public UndirectionalLinkLossDescriptorSubTLV getUndirectionalLinkLossTLV() {
		return undirectionalLinkLossTLV;
	}

	public void setUndirectionalLinkLossTLV(UndirectionalLinkLossDescriptorSubTLV undirectionalPackectLossTLV) {
		this.undirectionalLinkLossTLV = undirectionalPackectLossTLV;
	}

	public UndirectionalResidualBandwidthDescriptorSubTLV getUndirectionalResidualBwTLV() {
		return undirectionalResidualBwTLV;
	}

	public void setUndirectionalResidualBwTLV(UndirectionalResidualBandwidthDescriptorSubTLV undirectionalResidualBwTLV) {
		this.undirectionalResidualBwTLV = undirectionalResidualBwTLV;
	}

	public UndirectionalAvailableBandwidthDescriptorSubTLV getUndirectionalAvailableBwTLV() {
		return undirectionalAvailableBwTLV;
	}

	public void setUndirectionalAvailableBwTLV(
			UndirectionalAvailableBandwidthDescriptorSubTLV undirectionalAvailableBwTLV) {
		this.undirectionalAvailableBwTLV = undirectionalAvailableBwTLV;
	}

	public UndirectionalUtilizedBandwidthDescriptorSubTLV getUndirectionalUtilizedBwTLV() {
		return undirectionalUtilizedBwTLV;
	}

	public void setUndirectionalUtilizedBwTLV(UndirectionalUtilizedBandwidthDescriptorSubTLV undirectionalUtilizedBwTLV) {
		this.undirectionalUtilizedBwTLV = undirectionalUtilizedBwTLV;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(1000);
		sb.append("Link NLRI:");
		if (localNodeDescriptors != null){
			sb.append("\n> Local Node Descriptors: ");
			sb.append( localNodeDescriptors.toString());
		}
		
		if (remoteNodeDescriptorsTLV != null){
			sb.append(" ");
			sb.append("> Remote Node Descriptors: ");
			sb.append(remoteNodeDescriptorsTLV.toString());
			
		}
		sb.append("\n> Link Descriptors: ");
		if (linkIdentifiersTLV != null){
			sb.append(" ");
			sb.append("\n\t> "+ linkIdentifiersTLV.toString());
		}
		
		if (ipv4InterfaceAddressTLV != null){
			sb.append(" ");
			sb.append("> Ipv4 Interface Address TLV: ");
			sb.append("\n\t> "+ ipv4InterfaceAddressTLV.toString());
			
		}
		if (ipv4NeighborAddressTLV != null){
			sb.append(" ");
			sb.append("> Ipv4 Neighbor Address TLV: ");
			sb.append("\n\t> "+ipv4NeighborAddressTLV.toString());
		}
		if (ipv6InterfaceAddressTLV != null){
			sb.append(" ");
			sb.append("> Ipv6 Interface Address TLV: ");
			sb.append("\n\t> "+ipv6InterfaceAddressTLV.toString());
		}
		
		if (ipv6NeighborAddressTLV != null){
			sb.append(" ");
			sb.append("> Ipv6 Neighbor Address TLV:");
			sb.append("\n\t> "+ipv6NeighborAddressTLV.toString());
		}
		
		if (multiTopologyIDTLV != null){
			sb.append(" ");
			sb.append("> Multi Topology ID TLV:");
			sb.append("\n\t> "+multiTopologyIDTLV.toString());
		}
		if (undirectionalLinkDelayTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Link Delay TLV:");
			sb.append("\n\t> "+undirectionalLinkDelayTLV.toString());
		}
		if(minMaxUndirectionalLinkDelayTLV != null){
			sb.append(" ");
			sb.append("> Min/Max Undirectional Link Delay TLV:");
			sb.append("\n\t> "+minMaxUndirectionalLinkDelayTLV.toString());
		}
		if(undirectionalDelayVariationTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Delay Variation TLV:");
			sb.append("\n\t> "+undirectionalDelayVariationTLV.toString());
		}
		if(undirectionalLinkLossTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Link Loss TLV:");
			sb.append("\n\t> "+undirectionalLinkLossTLV.toString());
		}
		if(undirectionalResidualBwTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Residual Bandwidth TLV:");
			sb.append("\n\t> "+undirectionalResidualBwTLV.toString());
		}
		if(undirectionalAvailableBwTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Available Bandwidth TLV:");
			sb.append("\n\t> "+undirectionalAvailableBwTLV.toString());
		}
		if(undirectionalUtilizedBwTLV != null){
			sb.append(" ");
			sb.append("> Undirectional Utilized Bandwidth TLV:");
			sb.append("\n\t> "+undirectionalUtilizedBwTLV.toString());
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (identifier ^ (identifier >>> 32));
		result = prime * result + ((ipv4InterfaceAddressTLV == null) ? 0 : ipv4InterfaceAddressTLV.hashCode());
		result = prime * result + ((ipv4NeighborAddressTLV == null) ? 0 : ipv4NeighborAddressTLV.hashCode());
		result = prime * result + ((ipv6InterfaceAddressTLV == null) ? 0 : ipv6InterfaceAddressTLV.hashCode());
		result = prime * result + ((ipv6NeighborAddressTLV == null) ? 0 : ipv6NeighborAddressTLV.hashCode());
		result = prime * result + ((linkIdentifiersTLV == null) ? 0 : linkIdentifiersTLV.hashCode());
		result = prime * result + ((localNodeDescriptors == null) ? 0 : localNodeDescriptors.hashCode());
		result = prime * result
				+ ((minMaxUndirectionalLinkDelayTLV == null) ? 0 : minMaxUndirectionalLinkDelayTLV.hashCode());
		result = prime * result + ((multiTopologyIDTLV == null) ? 0 : multiTopologyIDTLV.hashCode());
		result = prime * result + protocolID;
		result = prime * result + ((remoteNodeDescriptorsTLV == null) ? 0 : remoteNodeDescriptorsTLV.hashCode());
		result = prime * result + ((undirectionalAvailableBwTLV == null) ? 0 : undirectionalAvailableBwTLV.hashCode());
		result = prime * result
				+ ((undirectionalDelayVariationTLV == null) ? 0 : undirectionalDelayVariationTLV.hashCode());
		result = prime * result + ((undirectionalLinkDelayTLV == null) ? 0 : undirectionalLinkDelayTLV.hashCode());
		result = prime * result + ((undirectionalLinkLossTLV == null) ? 0 : undirectionalLinkLossTLV.hashCode());
		result = prime * result + ((undirectionalResidualBwTLV == null) ? 0 : undirectionalResidualBwTLV.hashCode());
		result = prime * result + ((undirectionalUtilizedBwTLV == null) ? 0 : undirectionalUtilizedBwTLV.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LinkNLRI other = (LinkNLRI) obj;
		if (identifier != other.identifier)
			return false;
		if (ipv4InterfaceAddressTLV == null) {
			if (other.ipv4InterfaceAddressTLV != null)
				return false;
		} else if (!ipv4InterfaceAddressTLV.equals(other.ipv4InterfaceAddressTLV))
			return false;
		if (ipv4NeighborAddressTLV == null) {
			if (other.ipv4NeighborAddressTLV != null)
				return false;
		} else if (!ipv4NeighborAddressTLV.equals(other.ipv4NeighborAddressTLV))
			return false;
		if (ipv6InterfaceAddressTLV == null) {
			if (other.ipv6InterfaceAddressTLV != null)
				return false;
		} else if (!ipv6InterfaceAddressTLV.equals(other.ipv6InterfaceAddressTLV))
			return false;
		if (ipv6NeighborAddressTLV == null) {
			if (other.ipv6NeighborAddressTLV != null)
				return false;
		} else if (!ipv6NeighborAddressTLV.equals(other.ipv6NeighborAddressTLV))
			return false;
		if (linkIdentifiersTLV == null) {
			if (other.linkIdentifiersTLV != null)
				return false;
		} else if (!linkIdentifiersTLV.equals(other.linkIdentifiersTLV))
			return false;
		if (localNodeDescriptors == null) {
			if (other.localNodeDescriptors != null)
				return false;
		} else if (!localNodeDescriptors.equals(other.localNodeDescriptors))
			return false;
		if (minMaxUndirectionalLinkDelayTLV == null) {
			if (other.minMaxUndirectionalLinkDelayTLV != null)
				return false;
		} else if (!minMaxUndirectionalLinkDelayTLV.equals(other.minMaxUndirectionalLinkDelayTLV))
			return false;
		if (multiTopologyIDTLV == null) {
			if (other.multiTopologyIDTLV != null)
				return false;
		} else if (!multiTopologyIDTLV.equals(other.multiTopologyIDTLV))
			return false;
		if (protocolID != other.protocolID)
			return false;
		if (remoteNodeDescriptorsTLV == null) {
			if (other.remoteNodeDescriptorsTLV != null)
				return false;
		} else if (!remoteNodeDescriptorsTLV.equals(other.remoteNodeDescriptorsTLV))
			return false;
		if (undirectionalAvailableBwTLV == null) {
			if (other.undirectionalAvailableBwTLV != null)
				return false;
		} else if (!undirectionalAvailableBwTLV.equals(other.undirectionalAvailableBwTLV))
			return false;
		if (undirectionalDelayVariationTLV == null) {
			if (other.undirectionalDelayVariationTLV != null)
				return false;
		} else if (!undirectionalDelayVariationTLV.equals(other.undirectionalDelayVariationTLV))
			return false;
		if (undirectionalLinkDelayTLV == null) {
			if (other.undirectionalLinkDelayTLV != null)
				return false;
		} else if (!undirectionalLinkDelayTLV.equals(other.undirectionalLinkDelayTLV))
			return false;
		if (undirectionalLinkLossTLV == null) {
			if (other.undirectionalLinkLossTLV != null)
				return false;
		} else if (!undirectionalLinkLossTLV.equals(other.undirectionalLinkLossTLV))
			return false;
		if (undirectionalResidualBwTLV == null) {
			if (other.undirectionalResidualBwTLV != null)
				return false;
		} else if (!undirectionalResidualBwTLV.equals(other.undirectionalResidualBwTLV))
			return false;
		if (undirectionalUtilizedBwTLV == null) {
			if (other.undirectionalUtilizedBwTLV != null)
				return false;
		} else if (!undirectionalUtilizedBwTLV.equals(other.undirectionalUtilizedBwTLV))
			return false;
		return true;
	}
	
	
	
}
