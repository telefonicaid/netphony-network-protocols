package tid.ospf.ospfv2.lsa.tlv.subtlv;

public class OSPFSubTLVTypes {
	
	public static final int LinkType=1;//RFC 3630: 
	public static final int LinkID=2;//RFC 3630:
	public static final int LocalInterfaceIPAddress=3;//RFC 3630:
	public static final int RemoteInterfaceIPAddress=4;//RFC 3630:
	public static final int TrafficEngineeringMetric=5;//RFC 3630:
	public static final int MaximumBandwidth=6;//RFC 3630:
	public static final int MaximumReservableBandwidth=7;//RFC 3630:
	public static final int UnreservedBandwidth=8;//RFC 3630:
	public static final int AdministrativeGroup=9;//RFC 3630:
	
	public static final int LinkLocalRemoteIdentifiers=11;//RFC 4203
	public static final int LinkProtectionType=14;//RFC 4203
	public static final int InterfaceSwitchingCapabilityDescriptor=15;//RFC 4203
	public static final int SharedRiskLinkGroup=16;//RFC 4203
	
	public static final int RemoteASNumber=21;//RFC 5392: 
	public static final int IPv4RemoteASBRID=22;//RFC 5392: 
	public static final int IPv6RemoteASBRID=24;//RFC 5392:
	
	public static final int LinkTypePointToPoint=1;
	public static final int LinkTypeMultiAccess=2;
	
	//FIXME:	A definir por IANA el tipo de la subtlv Port Label Restriction, Available Labels
	// Draft http://tools.ietf.org/html/draft-ietf-ccamp-general-constraint-encode-05#section-2.6
	
	public static final int PortLabelRestriction = 100;
	public static final int AvailableLabels = 1200;
	


}
