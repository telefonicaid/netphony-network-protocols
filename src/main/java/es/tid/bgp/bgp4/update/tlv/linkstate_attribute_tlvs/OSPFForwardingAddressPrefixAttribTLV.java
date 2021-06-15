package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

//FIXME: IMPLEMENT IPV6

public class OSPFForwardingAddressPrefixAttribTLV extends BGP4TLVFormat {
	Inet4Address OSPFAddress;
	


	public OSPFForwardingAddressPrefixAttribTLV() {
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_OSPF_FORWARDING_ADDRESS);	
		}

	public OSPFForwardingAddressPrefixAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		this.setTLVValueLength(4);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int offset=4;
		System.arraycopy(OSPFAddress.getAddress(),0, this.tlv_bytes, 4, 4);
		
	}
	
	public void decode(){
		int len = this.getTLVValueLength();
		switch(len){
		case 4:
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
			try {
				OSPFAddress=(Inet4Address)Inet4Address.getByAddress(ip);
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		}
		default: log.debug("IPv6 NOT SUPPORTED YET");
		}
		
	}

	public Inet4Address getOSPFAddress() {
		return OSPFAddress;
	}

	public void setOSPFAddress(Inet4Address oSPFAddress) {
		OSPFAddress = oSPFAddress;
	}

	
	public String toString() {
		return "OSPF ADDRESS [ospf_addr=" + OSPFAddress.toString() + "]";
	}
}
