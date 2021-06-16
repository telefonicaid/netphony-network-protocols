package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * IP Reachability Information TLV (Type 265)	
 * 
 * The IP Reachability Information TLV is a mandatory TLV for IPv4 &
   IPv6 Prefix NLRI types.  The TLV contains one IP address prefix (IPv4
   or IPv6) originally advertised in the IGP topology.  Its purpose is
   to glue a particular BGP service NLRI by virtue of its BGP next hop
   to a given node in the LSDB.  A router SHOULD advertise an IP Prefix
   NLRI for each of its BGP next hops.  The format of the IP
   Reachability Information TLV is shown in the following figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | Prefix Length | IP Prefix (variable)                         //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

             Figure 14: IP Reachability Information TLV Format

   The Type and Length fields of the TLV are defined in Table 5.  The
   following two fields determine the reachability information of the
   address family.  The Prefix Length field contains the length of the
   prefix in bits.  The IP Prefix field contains the most significant
   octets of the prefix, i.e., 1 octet for prefix length 1 up to 8, 2
   octets for prefix length 9 to 16, 3 octets for prefix length 17 up to
   24, 4 octets for prefix length 25 up to 32, etc.
 * @author ogondio
 *
 */
public class IPReachabilityInformationPrefixDescriptorSubTLV extends BGP4TLVFormat {

	private Inet4Address ipv4Address;
	private int prefix_length;
	

	public IPReachabilityInformationPrefixDescriptorSubTLV() {
		super();
		this.setTLVType(PrefixDescriptorSubTLVTypes.PREFIX_DESCRIPTOR_SUB_TLV_TYPE_IPV4_REACHABILITY_INFO);
	}

	public IPReachabilityInformationPrefixDescriptorSubTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		int len = 1 + prefix_length;
		this.setTLVValueLength(len);
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);
		encodeHeader();
		int offset = 4;
		this.tlv_bytes[offset] = (byte) prefix_length;
		offset += 1;
		System.arraycopy(ipv4Address.getAddress(), 0, this.tlv_bytes, offset, prefix_length);

	}

	public void decode() {
		int offset = 4;
		 byte[] address = new byte[4];
		prefix_length = this.tlv_bytes[offset] & (0xFF);
		offset += 1;
		for (int i = 0; i < address.length; i++) {
			address[i] = 0;
		}
		System.arraycopy(this.tlv_bytes, offset, address, 0, prefix_length);
		try {
			ipv4Address = (Inet4Address) Inet4Address.getByAddress(address);
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
