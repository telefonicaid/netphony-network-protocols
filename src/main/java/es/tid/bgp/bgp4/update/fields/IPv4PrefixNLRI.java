package es.tid.bgp.bgp4.update.fields;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.LocalNodeDescriptorsTLV;
import es.tid.bgp.bgp4.update.tlv.RoutingUniverseIdentifierTypes;
import es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs.*;

/**
 * 
 * @author ogondio
 *
 */
public class IPv4PrefixNLRI extends LinkStateNLRI {
	
	private int protocolID;//inicializado a 0(unknown)
	private long routingUniverseIdentifier;
	private LocalNodeDescriptorsTLV localNodeDescriptors;
	private OSPFRouteTypeTLV OSPFRouteType;
	private IPReachabilityInformationTLV ipReachability;
	//private IPReachabilityInfo IPReachabilityINFO;
	
	public IPv4PrefixNLRI() {
		this.setNLRIType(NLRITypes.Prefix_v4_NLRI);
		this.setRoutingUniverseIdentifier(RoutingUniverseIdentifierTypes.Level3Identifier);
	}

	

	public IPv4PrefixNLRI(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}
	
@Override
	public void encode() {
	
		/*
		 *       0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+
     |  Protocol-ID  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                           Identifier                          |
     |                            (64 bits)                          |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //              Local Node Descriptors (variable)              //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                Prefix Descriptors (variable)                //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

            Figure 9: The IPv4/IPv6 Topology Prefix NLRI Format
		 */
	
		int len=13;// The four bytes of the header + 1 of 
		if (localNodeDescriptors!=null){
			localNodeDescriptors.encode();
			len=len+localNodeDescriptors.getTotalTLVLength();		
		}
		
		if(OSPFRouteType!=null){
			OSPFRouteType.encode();
			len = len + OSPFRouteType.getTotalTLVLength();
		}
		
		if(ipReachability!=null){
			ipReachability.encode();
			len = len + ipReachability.getTotalTLVLength();
		}
		
		this.setTotalNLRILength(len); 
		//len = len+1; //Length (1 octet, NRLI) 
		this.setLength(len);
		this.bytes=new byte[len];
		this.encodeHeader();
	
		this.bytes[4]=(byte)protocolID;
		this.bytes[5]=(byte)(routingUniverseIdentifier>>>56 & 0xFF);
		this.bytes[6]=(byte)(routingUniverseIdentifier>>>48 & 0xFF);
		this.bytes[7]=(byte)(routingUniverseIdentifier >>> 40 & 0xFF);
		this.bytes[8]=(byte)(routingUniverseIdentifier>>>32 & 0xFF);
		this.bytes[9]=(byte)(routingUniverseIdentifier>>>24 & 0xFF);
		this.bytes[10]=(byte)(routingUniverseIdentifier >>> 16 & 0xFF);
		this.bytes[11]=(byte)(routingUniverseIdentifier >>>8 & 0xFF);
		this.bytes[12]=(byte)(routingUniverseIdentifier & 0xFF);
		
		int offset=13;
		
		if (localNodeDescriptors!=null){
			System.arraycopy(localNodeDescriptors.getTlv_bytes(), 0, this.bytes, offset,localNodeDescriptors.getTotalTLVLength());
			offset=offset+localNodeDescriptors.getTotalTLVLength();
		}
		
		if (OSPFRouteType!=null){
			System.arraycopy(OSPFRouteType.getTlv_bytes(), 0, this.bytes, offset, OSPFRouteType.getTotalTLVLength());
			offset=offset+OSPFRouteType.getTotalTLVLength();
		}
		
		if (ipReachability!=null){
			System.arraycopy(ipReachability.getTlv_bytes(), 0, this.bytes, offset, ipReachability.getTotalTLVLength());
			offset=offset+ipReachability.getTotalTLVLength();
		}
	
	}

	private void decode() {	
		//Decoding Prefix NLRI");
		int offset = 4; //Cabecera del LinkState NLRI
		protocolID = this.bytes[offset];
		offset=offset +1; //identifier
		
		byte[] ip=new byte[8]; 
		System.arraycopy(this.bytes,offset, ip, 0, 8);
		
		long routingUniverseIdentifieraux1 = ((  ((long)bytes[offset]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+1]<<16) & 0xFF0000) | (((long)bytes[offset+2]<<8) & 0xFF00) |(((long)bytes[offset+3]) & 0xFF);
		long routingUniverseIdentifieraux2 = ((  ((long)bytes[offset+4]&0xFF)   <<24)& 0xFF000000) |  (((long)bytes[offset+5]<<16) & 0xFF0000) | (((long)bytes[offset+6]<<8) & 0xFF00) |(((long)bytes[offset+7]) & 0xFF);
		//this.setRoutingUniverseIdentifier((2^32)*routingUniverseIdentifieraux1+routingUniverseIdentifieraux2);
		this.setRoutingUniverseIdentifier((routingUniverseIdentifieraux1 <<32)&0xFFFFFFFF00000000L | routingUniverseIdentifieraux2);
		offset = offset +8;
	
		this.localNodeDescriptors=new LocalNodeDescriptorsTLV(this.bytes, offset);
		offset = offset + localNodeDescriptors.getTotalTLVLength();
		
		boolean fin=false;
		if (offset>=(this.getTotalNLRILength())){
			fin=true;
		}
		while (!fin) {
			int subTLVType=BGP4TLVFormat.getType(bytes, offset);
			int subTLVLength=BGP4TLVFormat.getTotalTLVLength(bytes, offset);
			
			switch (subTLVType){
			case PrefixDescriptorSubTLVTypes.PREFIX_DESCRIPTOR_SUB_TLV_TYPE_IPV4_REACHABILITY_INFO:
				this.ipReachability=new IPReachabilityInformationTLV(bytes, offset);
				break;
				
			case PrefixDescriptorSubTLVTypes.PREFIX_DESCRIPTOR_SUB_TLV_TYPE_OSPF_ROUTE_TYPE:
				this.OSPFRouteType=new OSPFRouteTypeTLV(bytes, offset);
				break;
			default:
				log.warn("Unknown sub TLV found, subtype "+subTLVType);
			}
		
		offset=offset+subTLVLength;
		if (offset>=(this.getTotalNLRILength()/*+4*/)){
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

	public LocalNodeDescriptorsTLV getLocalNodeDescriptors() {
		return localNodeDescriptors;
	}

	public void setLocalNodeDescriptors(LocalNodeDescriptorsTLV localNodeDescriptors) {
		this.localNodeDescriptors = localNodeDescriptors;
	}
	
	public void setRoutingUniverseIdentifier(long level3identifier) {
		// TODO Auto-generated method stub
		
	}
	
	public long getRoutingUniverseIdentifier() {
		return routingUniverseIdentifier;
	}



	public OSPFRouteTypeTLV getOSPFRouteType() {
		return OSPFRouteType;
	}



	public void setOSPFRouteType(OSPFRouteTypeTLV oSPFRouteType) {
		OSPFRouteType = oSPFRouteType;
	}



	public IPReachabilityInformationTLV getIpReachability() {
		return ipReachability;
	}



	public void setIpReachability(
			IPReachabilityInformationTLV ipReachability) {
		this.ipReachability = ipReachability;
	}

	

}
