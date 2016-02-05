package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * RFC 5305        IS-IS Extensions for Traffic Engineering    October 2008
 *
 * Section 3.2
 *
 * This sub-TLV contains a 4-octet IPv4 address for the interface
   described by the (main) TLV.  This sub-TLV can occur multiple times.

   Implementations MUST NOT inject a /32 prefix for the interface
   address into their routing or forwarding table because this can lead
   to forwarding loops when interacting with systems that do not support
   this sub-TLV.

   If a router implements the basic TLV extensions in this document, it
   MAY add or omit this sub-TLV from the description of an adjacency.
   If a router implements traffic engineering, it MUST include this sub-
   TLV.
   
 * @author mcs
 *
 */
public class IPv4InterfaceAddressLinkDescriptorsSubTLV extends BGP4TLVFormat{
	Inet4Address ipv4Address;
	public IPv4InterfaceAddressLinkDescriptorsSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_IPv4INTERFACE);
	}
	
	
	public IPv4InterfaceAddressLinkDescriptorsSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
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
		if (this.getTLVValueLength()!=8){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
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


	@Override
	public String toString() {
		return "IPv4InterfaceAddress [ipv4Address=" + ipv4Address.toString() + "]";
	}

}
