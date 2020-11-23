package es.tid.ospf.ospfv2.lsa.tlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Router Address TLV from RFC 3630
 * 2.4.1. Router Address TLV


The Router Address TLV specifies a stable IP address of the
advertising router that is always reachable if there is any
connectivity to it; this is typically implemented as a "loopback
address".  The key attribute is that the address does not become
unusable if an interface is down.  In other protocols, this is known
as the "router ID," but for obvious reasons this nomenclature is
avoided here.  If a router advertises BGP routes with the BGP next
hop attribute set to the BGP router ID, then the Router Address
SHOULD be the same as the BGP router ID.
If IS-IS is also active in the domain, this address can also be used
to compute the mapping between the OSPF and IS-IS topologies.  For
example, suppose a router R is advertising both IS-IS and OSPF
Traffic Engineering LSAs, and suppose further that some router S is
building a single Traffic Engineering Database (TED) based on both
IS-IS and OSPF TE information.  R may then appear as two separate
nodes in S's TED.  However, if both the IS-IS and OSPF LSAs generated
by R contain the same Router Address, then S can determine that the
IS-IS TE LSA and the OSPF TE LSA from R are indeed from a single
router.

The router address TLV is type 1, has a length of 4, and a value that
is the four octet IP address.  It must appear in exactly one Traffic
Engineering LSA originated by a router.
 */

public class RouterAddressTLV extends OSPFTLV {

	private Inet4Address routerAddress;

	/**
	 * Use the constructor to create a new Router Address TLV from scratch
	 * Fill the fields, and later call encode
	 */
	public RouterAddressTLV(){
		this.setTLVType(OSPFTLVTypes.RouterAddressTLVType);
	}
	
	/**
	 * Use this constructor to create and decode 
	 * @param bytes bytes
	 * @param offset offset
	 * @throws MalformedOSPFTLVException Malformed OSPF TLV Exception
	 */
	public RouterAddressTLV(byte[] bytes, int offset)throws MalformedOSPFTLVException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		if (this.routerAddress!=null){
			System.arraycopy(this.routerAddress,0, this.tlv_bytes, 4, 4);
		}
		else {
			this.tlv_bytes[4]=0;
			this.tlv_bytes[5]=0;
			this.tlv_bytes[6]=0;
			this.tlv_bytes[7]=0;
		}
	}
	
	private void decode()throws MalformedOSPFTLVException{
		if (this.getTLVValueLength()!=4){
			log.warn("Incorrect VALUE LENGTH!!!");
			throw new MalformedOSPFTLVException();
		}
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			this.routerAddress=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new MalformedOSPFTLVException();
		}
		
	}

	public Inet4Address getRouterAddress() {
		return routerAddress;
	}

	public void setRouterAddress(Inet4Address routerAddress) {
		this.routerAddress = routerAddress;
	}
	
	/**
	 * String representation of the RouterAddressTLV
	 */
	public String toString(){		
		return "routerAddress: "+routerAddress.getHostAddress();
	}
	

}
