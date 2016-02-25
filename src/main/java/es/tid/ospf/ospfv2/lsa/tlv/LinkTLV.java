package es.tid.ospf.ospfv2.lsa.tlv;

import es.tid.ospf.ospfv2.lsa.tlv.subtlv.AdministrativeGroup;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.AvailableLabels;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.IPv4RemoteASBRID;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.InterfaceSwitchingCapabilityDescriptor;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkID;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkLocalRemoteIdentifiers;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkProtectionType;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkType;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LocalInterfaceIPAddress;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.MalformedOSPFSubTLVException;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.MaximumBandwidth;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.MaximumReservableBandwidth;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.OSPFSubTLV;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.OSPFSubTLVTypes;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.RemoteASNumber;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.RemoteInterfaceIPAddress;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.SharedRiskLinkGroup;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.TrafficEngineeringMetric;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.UnreservedBandwidth;

/**
 * Link TLV from RFC 3630 (TE Extensions to OSPF Version 2).
 * 
 *2.4.2. Link TLV
   The Link TLV describes a single link.  It is constructed of a set of
   sub-TLVs.  There are no ordering requirements for the sub-TLVs.

   Only one Link TLV shall be carried in each LSA, allowing for fine
   granularity changes in topology.

   The Link TLV is type 2, and the length is variable.

   The following sub-TLVs of the Link TLV are defined:

      1 - Link type (1 octet)
      2 - Link ID (4 octets)
      3 - Local interface IP address (4 octets)
      4 - Remote interface IP address (4 octets)
      5 - Traffic engineering metric (4 octets)
      6 - Maximum bandwidth (4 octets)
      7 - Maximum reservable bandwidth (4 octets)
      8 - Unreserved bandwidth (32 octets)
      9 - Administrative group (4 octets)

   This memo defines sub-Types 1 through 9.  See the IANA Considerations
   section for allocation of new sub-Types.

   The Link Type and Link ID sub-TLVs are mandatory, i.e., must appear
   exactly once.  All other sub-TLVs defined here may occur at most
   once.  These restrictions need not apply to future sub-TLVs.
   Unrecognized sub-TLVs are ignored.
   Various values below use the (32 bit) IEEE Floating Point format.
   For quick reference, this format is as follows:
       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |S|    Exponent   |                  Fraction                   |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   S is the sign, Exponent is the exponent base 2 in "excess 127"
   notation, and Fraction is the mantissa - 1, with an implied binary
   point in front of it.  Thus, the above represents the value:

      (-1)**(S) * 2**(Exponent-127) * (1 + Fraction)

   For more details, refer to [4].
 *  @author ogondio
 */
public class LinkTLV extends OSPFTLV {

	private LinkType linkType;

	private LinkID linkID;

	private LocalInterfaceIPAddress localInterfaceIPAddress;

	private RemoteInterfaceIPAddress remoteInterfaceIPAddress;
	
	private TrafficEngineeringMetric trafficEngineeringMetric;

	private MaximumBandwidth maximumBandwidth; 

	private MaximumReservableBandwidth maximumReservableBandwidth;

	private UnreservedBandwidth unreservedBandwidth; 

	private AdministrativeGroup administrativeGroup;
	
	private LinkLocalRemoteIdentifiers linkLocalRemoteIdentifiers;
	
	private LinkProtectionType linkProtectionType;
	
	private InterfaceSwitchingCapabilityDescriptor interfaceSwitchingCapabilityDescriptor;
	
	private SharedRiskLinkGroup sharedRiskLinkGroup;	
	
	private RemoteASNumber remoteASNumber;
	
	private IPv4RemoteASBRID iPv4RemoteASBRID;

	private AvailableLabels availableLabels;

	public LinkTLV(){
		this.setTLVType(OSPFTLVTypes.LinkTLV);
	}

	public LinkTLV(byte[] bytes, int offset)throws MalformedOSPFTLVException{
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		int valueLength=0;
		if (linkType!=null){
			linkType.encode();
			valueLength=valueLength+linkType.getTotalTLVLength();
		}
		else {
			//FIXME: THROW EXCEPTION??
		}
		if (linkID!=null){
			linkID.encode();
			valueLength=valueLength+linkID.getTotalTLVLength();

		}
		else {
			//FIXME: THROW EXCEPTION??
		}

		if (localInterfaceIPAddress!=null){
			localInterfaceIPAddress.encode();
			valueLength=valueLength+localInterfaceIPAddress.getTotalTLVLength();
		}
		if (remoteInterfaceIPAddress!=null){
			remoteInterfaceIPAddress.encode();
			valueLength=valueLength+remoteInterfaceIPAddress.getTotalTLVLength();
		}

		if (maximumBandwidth!=null){
			maximumBandwidth.encode();
			valueLength=valueLength+maximumBandwidth.getTotalTLVLength();
		}
		
		if (maximumReservableBandwidth!=null){
			maximumReservableBandwidth.encode();
			valueLength=valueLength+maximumReservableBandwidth.getTotalTLVLength();
		}
		
		if (unreservedBandwidth!=null){
			unreservedBandwidth.encode();
			valueLength=valueLength+unreservedBandwidth.getTotalTLVLength();
		}
		
		if (administrativeGroup!=null){
			try {
				administrativeGroup.encode();
			} catch (MalformedOSPFSubTLVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valueLength=valueLength+administrativeGroup.getTotalTLVLength();
		}
		
		
		if (linkLocalRemoteIdentifiers!=null){
			linkLocalRemoteIdentifiers.encode();
			valueLength=valueLength+linkLocalRemoteIdentifiers.getTotalTLVLength();
		}
		
		if (remoteASNumber!=null){
			remoteASNumber.encode();
			valueLength=valueLength+remoteASNumber.getTotalTLVLength();
		}
		
		if (iPv4RemoteASBRID!=null){
			iPv4RemoteASBRID.encode();
			valueLength=valueLength+iPv4RemoteASBRID.getTotalTLVLength();
		}
		if (availableLabels != null){
			try {
				availableLabels.encode();
			} catch (MalformedOSPFSubTLVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			valueLength=valueLength+availableLabels.getTotalTLVLength();
		}
		//After encoding, the valueLength is known
		this.setTLVValueLength(valueLength);
		//After calling setTLVValueLength, the total TLV length is computed
		//Then create the byte array
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		//Encode the header
		this.encodeHeader();
		int offset=4;
		if (linkType!=null){
			System.arraycopy(linkType.getTlv_bytes(),0, this.tlv_bytes,offset, linkType.getTotalTLVLength());
			offset=offset+linkType.getTotalTLVLength();
		}
		if (linkID!=null){
			System.arraycopy(linkID.getTlv_bytes(),0, this.tlv_bytes,offset, linkID.getTotalTLVLength());
			offset=offset+linkID.getTotalTLVLength();
		}
		if (localInterfaceIPAddress!=null){
			System.arraycopy(localInterfaceIPAddress.getTlv_bytes(),0, this.tlv_bytes,offset, localInterfaceIPAddress.getTotalTLVLength());
			offset=offset+localInterfaceIPAddress.getTotalTLVLength();
		}
		if (remoteInterfaceIPAddress!=null){
			System.arraycopy(remoteInterfaceIPAddress.getTlv_bytes(),0, this.tlv_bytes,offset, remoteInterfaceIPAddress.getTotalTLVLength());
			offset=offset+remoteInterfaceIPAddress.getTotalTLVLength();
		}
		if (maximumBandwidth!=null){
			System.arraycopy(maximumBandwidth.getTlv_bytes(),0, this.tlv_bytes,offset, maximumBandwidth.getTotalTLVLength());
			offset=offset+maximumBandwidth.getTotalTLVLength();
		}		
		if (maximumReservableBandwidth!=null){
			System.arraycopy(maximumReservableBandwidth.getTlv_bytes(),0, this.tlv_bytes,offset, maximumReservableBandwidth.getTotalTLVLength());
			offset=offset+maximumReservableBandwidth.getTotalTLVLength();
		}
		if (unreservedBandwidth!=null){
			System.arraycopy(unreservedBandwidth.getTlv_bytes(),0, this.tlv_bytes,offset, unreservedBandwidth.getTotalTLVLength());
			offset=offset+unreservedBandwidth.getTotalTLVLength();
		}		
		if (administrativeGroup!=null){
			System.arraycopy(administrativeGroup.getTlv_bytes(),0, this.tlv_bytes,offset, administrativeGroup.getTotalTLVLength());
			offset=offset+administrativeGroup.getTotalTLVLength();
		}
		if (linkLocalRemoteIdentifiers!=null){
			System.arraycopy(linkLocalRemoteIdentifiers.getTlv_bytes(),0, this.tlv_bytes,offset, linkLocalRemoteIdentifiers.getTotalTLVLength());
			offset=offset+linkLocalRemoteIdentifiers.getTotalTLVLength();
		}	
		if (remoteASNumber!=null){
			System.arraycopy(remoteASNumber.getTlv_bytes(),0, this.tlv_bytes,offset, remoteASNumber.getTotalTLVLength());
			offset=offset+remoteASNumber.getTotalTLVLength();
		}		
		if (iPv4RemoteASBRID!=null){
			System.arraycopy(iPv4RemoteASBRID.getTlv_bytes(),0, this.tlv_bytes,offset, iPv4RemoteASBRID.getTotalTLVLength());
			offset=offset+iPv4RemoteASBRID.getTotalTLVLength();
		}
		if (availableLabels != null){
			System.arraycopy(availableLabels.getTlv_bytes(), 0, this.tlv_bytes, offset, availableLabels.getTotalTLVLength());
		}
	}

	private void decode()throws MalformedOSPFTLVException{
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedOSPFTLVException();
		}
		while (!fin) {
			int subTLVType=OSPFSubTLV.getType(this.getTlv_bytes(), offset);
			int subTLVLength=OSPFSubTLV.getTotalTLVLength(this.getTlv_bytes(), offset);
			try {
				switch (subTLVType){
				case OSPFSubTLVTypes.LinkID:
					this.linkID=new LinkID(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.LinkType:
					this.linkType=new LinkType(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.LocalInterfaceIPAddress:
					this.localInterfaceIPAddress=new LocalInterfaceIPAddress(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.RemoteInterfaceIPAddress:
					this.remoteInterfaceIPAddress=new RemoteInterfaceIPAddress(this.getTlv_bytes(), offset);
					break;
				case OSPFSubTLVTypes.TrafficEngineeringMetric:
					this.trafficEngineeringMetric=new TrafficEngineeringMetric(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.MaximumBandwidth:
					this.maximumBandwidth=new MaximumBandwidth(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.MaximumReservableBandwidth:
					this.maximumReservableBandwidth=new MaximumReservableBandwidth(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.UnreservedBandwidth:
					this.unreservedBandwidth=new UnreservedBandwidth(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.AdministrativeGroup:
					this.administrativeGroup=new AdministrativeGroup(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.LinkLocalRemoteIdentifiers:
					this.linkLocalRemoteIdentifiers=new LinkLocalRemoteIdentifiers(this.getTlv_bytes(), offset);
					break;

				case OSPFSubTLVTypes.LinkProtectionType:
					this.linkProtectionType=new LinkProtectionType(this.getTlv_bytes(), offset);
					break;

				case OSPFSubTLVTypes.InterfaceSwitchingCapabilityDescriptor:
					this.interfaceSwitchingCapabilityDescriptor=new InterfaceSwitchingCapabilityDescriptor(this.getTlv_bytes(), offset);
					break;

				case OSPFSubTLVTypes.SharedRiskLinkGroup:
					this.sharedRiskLinkGroup=new SharedRiskLinkGroup(this.getTlv_bytes(), offset);
					break;

					
				case OSPFSubTLVTypes.RemoteASNumber:
					this.remoteASNumber=new RemoteASNumber(this.getTlv_bytes(), offset);
					break;
					
				case OSPFSubTLVTypes.IPv4RemoteASBRID:
					this.iPv4RemoteASBRID=new IPv4RemoteASBRID(this.getTlv_bytes(), offset);
					break;
				case OSPFSubTLVTypes.AvailableLabels:
					this.availableLabels = new AvailableLabels(this.getTlv_bytes(), offset);
					break;
				default:
					log.warn("Unknown TLV found: "+subTLVType);
					

				}
			} catch (MalformedOSPFSubTLVException e) {
				log.warn("Malformed SubTLV found ");
				throw new MalformedOSPFTLVException();
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				fin=true;
			}

		}
	}

	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public LinkID getLinkID() {
		return linkID;
	}

	public void setLinkID(LinkID linkID) {
		this.linkID = linkID;
	}

	public LocalInterfaceIPAddress getLocalInterfaceIPAddress() {
		return localInterfaceIPAddress;
	}

	public void setLocalInterfaceIPAddress(
			LocalInterfaceIPAddress localInterfaceIPAddress) {
		this.localInterfaceIPAddress = localInterfaceIPAddress;
	}

	public RemoteInterfaceIPAddress getRemoteInterfaceIPAddress() {
		return remoteInterfaceIPAddress;
	}

	public void setRemoteInterfaceIPAddress(
			RemoteInterfaceIPAddress remoteInterfaceIPAddress) {
		this.remoteInterfaceIPAddress = remoteInterfaceIPAddress;
	}

	public MaximumBandwidth getMaximumBandwidth() {
		return maximumBandwidth;
	}

	public void setMaximumBandwidth(MaximumBandwidth maximumBandwidth) {
		this.maximumBandwidth = maximumBandwidth;
	}

	public MaximumReservableBandwidth getMaximumReservableBandwidth() {
		return maximumReservableBandwidth;
	}

	public void setMaximumReservableBandwidth(
			MaximumReservableBandwidth maximumReservableBandwidth) {
		this.maximumReservableBandwidth = maximumReservableBandwidth;
	}

	public UnreservedBandwidth getUnreservedBandwidth() {
		return unreservedBandwidth;
	}

	public void setUnreservedBandwidth(UnreservedBandwidth unreservedBandwidth) {
		this.unreservedBandwidth = unreservedBandwidth;
	}

	public AvailableLabels getAvailableLabels() {
		return availableLabels;
	}

	public void setAvailableLabels(AvailableLabels availableLabels) {
		this.availableLabels = availableLabels;
	}

	public AdministrativeGroup getAdministrativeGroup() {
		return administrativeGroup;
	}

	public void setAdministrativeGroup(AdministrativeGroup administrativeGroup) {
		this.administrativeGroup = administrativeGroup;
	}
	
	
	public TrafficEngineeringMetric getTrafficEngineeringMetric() {
		return trafficEngineeringMetric;
	}

	public void setTrafficEngineeringMetric(
			TrafficEngineeringMetric trafficEngineeringMetric) {
		this.trafficEngineeringMetric = trafficEngineeringMetric;
	}
	
	

	public LinkLocalRemoteIdentifiers getLinkLocalRemoteIdentifiers() {
		return linkLocalRemoteIdentifiers;
	}

	public void setLinkLocalRemoteIdentifiers(
			LinkLocalRemoteIdentifiers linkLocalRemoteIdentifiers) {
		this.linkLocalRemoteIdentifiers = linkLocalRemoteIdentifiers;
	}

	public LinkProtectionType getLinkProtectionType() {
		return linkProtectionType;
	}

	public void setLinkProtectionType(LinkProtectionType linkProtectionType) {
		this.linkProtectionType = linkProtectionType;
	}

	public InterfaceSwitchingCapabilityDescriptor getInterfaceSwitchingCapabilityDescriptor() {
		return interfaceSwitchingCapabilityDescriptor;
	}

	public void setInterfaceSwitchingCapabilityDescriptor(
			InterfaceSwitchingCapabilityDescriptor interfaceSwitchingCapabilityDescriptor) {
		this.interfaceSwitchingCapabilityDescriptor = interfaceSwitchingCapabilityDescriptor;
	}

	public SharedRiskLinkGroup getSharedRiskLinkGroup() {
		return sharedRiskLinkGroup;
	}

	public void setSharedRiskLinkGroup(SharedRiskLinkGroup sharedRiskLinkGroup) {
		this.sharedRiskLinkGroup = sharedRiskLinkGroup;
	}

	public RemoteASNumber getRemoteASNumber() {
		return remoteASNumber;
	}

	public void setRemoteASNumber(RemoteASNumber remoteASNumber) {
		this.remoteASNumber = remoteASNumber;
	}

	public IPv4RemoteASBRID getiPv4RemoteASBRID() {
		return iPv4RemoteASBRID;
	}

	public void setIPv4RemoteASBRID(IPv4RemoteASBRID iPv4RemoteASBRID) {
		this.iPv4RemoteASBRID = iPv4RemoteASBRID;
	}

	/**
	 * 
	 */
	public String toString(){
		StringBuffer sb=new StringBuffer(1000);		
		if (linkType!=null){
			sb.append(linkType.toString());
			sb.append("\r\n");			
		}
		if (linkID!=null){
			sb.append(linkID.toString());
			sb.append("\r\n");
		}
		if (localInterfaceIPAddress!=null){
			sb.append(localInterfaceIPAddress.toString());
			sb.append("\r\n");
		}
		if (remoteInterfaceIPAddress!=null){
			sb.append(remoteInterfaceIPAddress.toString());
			sb.append("\r\n");
		}

		if (maximumBandwidth!=null){
			sb.append(maximumBandwidth.toString());
			sb.append("\r\n");
		}
		if (maximumReservableBandwidth!=null){
			sb.append(maximumReservableBandwidth.toString());
			sb.append("\r\n");
		}
		
		if (unreservedBandwidth!=null){
			sb.append(unreservedBandwidth.toString());
			sb.append("\r\n");
		}
		
		if (administrativeGroup!=null){
			sb.append(administrativeGroup.toString());
			sb.append("\r\n");
		}

		if (remoteASNumber!=null){
			sb.append(remoteASNumber.toString());
			sb.append("\r\n");
		}
		
		if (iPv4RemoteASBRID!=null){
			sb.append(iPv4RemoteASBRID.toString());
			sb.append("\r\n");
		}
		
		if (availableLabels!=null){
			sb.append(availableLabels.toString());
			sb.append("\r\n");
		}
		
		if (interfaceSwitchingCapabilityDescriptor!=null){
			sb.append(interfaceSwitchingCapabilityDescriptor.toString());
			sb.append("\r\n");
		}
		if (linkLocalRemoteIdentifiers!=null){
			sb.append(linkLocalRemoteIdentifiers.toString());
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 */
	public String printShort(){
		StringBuffer sb=new StringBuffer(1000);		
		if (linkID!=null){
			sb.append(linkID.toString());
			sb.append("\r\n");
		}
		if (localInterfaceIPAddress!=null){
			sb.append(localInterfaceIPAddress.toString());
			sb.append("\r\n");
		}
		if (remoteInterfaceIPAddress!=null){
			sb.append(remoteInterfaceIPAddress.toString());
			sb.append("\r\n");
		}

		if (remoteASNumber!=null){
			sb.append(remoteASNumber.toString());
			sb.append("\r\n");
		}
		
		if (iPv4RemoteASBRID!=null){
			sb.append(iPv4RemoteASBRID.toString());
			sb.append("\r\n");
		}
		
		if (availableLabels!=null){
			sb.append(availableLabels.toString());
			sb.append("\r\n");
		}
		
		
		
		return sb.toString();
	}
}
