package tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class IPReachabilityInformationPrefixDescriptorSubTLV extends
		BGP4TLVFormat {
	
	private Inet4Address ipv4Address;
	private int prefix_length;
	private byte[] address;


	public IPReachabilityInformationPrefixDescriptorSubTLV() {
		super();
		this.setTLVType(PrefixDescriptorSubTLVTypes.PREFIX_DESCRIPTOR_SUB_TLV_TYPE_IPV4_REACHABILITY_INFO);	}

	public IPReachabilityInformationPrefixDescriptorSubTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		int len = 1+prefix_length;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int offset = 4;
		this.tlv_bytes[offset] = (byte)prefix_length;
		offset+=1;
		System.arraycopy(ipv4Address.getAddress(),0, this.tlv_bytes, offset, prefix_length);
		
	}
	
	public void decode(){
		int offset = 4;
		address=new byte[4];
		prefix_length = this.tlv_bytes[offset]&(0xFF);
		offset+=1;
			for(int i = 0;i<address.length;i++){
				address[i]=0;
			}
			System.arraycopy(this.tlv_bytes,offset, address, 0, prefix_length);
		try {
				ipv4Address= (Inet4Address) Inet4Address.getByAddress(address);
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

	public int getPrefix_length() {
		return prefix_length;
	}

	public void setPrefix_length(int prefix_length) {
		this.prefix_length = prefix_length;
	}
	
	public String toString() {
		return "IPReachability [Reachability=" + ipv4Address.toString() + "]";
	}

}
