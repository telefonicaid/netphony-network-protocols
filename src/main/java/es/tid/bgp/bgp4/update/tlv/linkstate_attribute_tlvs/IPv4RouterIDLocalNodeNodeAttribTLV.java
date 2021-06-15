package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * IPv4 Router-ID of Local Node (Type 1028)
 * 		[RFC5305, Section 4.3]
 * @author pac
 * @author ogondio
*/

public class IPv4RouterIDLocalNodeNodeAttribTLV extends BGP4TLVFormat{
 
	private Inet4Address ipv4Address;
	
	

	public IPv4RouterIDLocalNodeNodeAttribTLV() {
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IPv4_ROUTER_ID_OF_LOCAL_NODE);
		// TODO Auto-generated constructor stub
	}

	public IPv4RouterIDLocalNodeNodeAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		System.arraycopy(ipv4Address.getAddress(),0, this.tlv_bytes, 4, 4);
	}
	
	public void decode(){
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			ipv4Address=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Inet4Address getIpv4Address() {
		return ipv4Address;
	}

	public void setIpv4Address(Inet4Address ipv4Address) {
		this.ipv4Address = ipv4Address;
	}
	
	public String toString() {
		return "IPv4LocalNodeRouterID [ipv4Address=" + ipv4Address.toString() + "]";
	}

}
