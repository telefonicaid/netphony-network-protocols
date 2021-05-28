package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Remote AS Number from RFC 5392 (Type 21)
 * 
 * IANA Assignment in https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6  * 
 * see <a href="https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6">IANA assignments of OSPF Traffic Engneering TLVs</a>

 * @author ogondio
 *
 */
public class IPv4RemoteASBRID extends OSPFSubTLV {

	/*
	 *  * 3.3.1. Remote AS Number Sub-TLV


   A new sub-TLV, the Remote AS Number sub-TLV is defined for inclusion
   in the Link TLV when advertising inter-AS links.  The Remote AS
   Number sub-TLV specifies the AS number of the neighboring AS to which
   the advertised link connects.  The Remote AS Number sub-TLV is
   REQUIRED in a Link TLV that advertises an inter-AS TE link.

   The Remote AS Number sub-TLV is TLV type 21 (see Section 6.2), and is
   four octets in length.  The format is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |              Type             |             Length            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Remote AS Number                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Remote AS Number field has 4 octets.  When only two octets are
   used for the AS number, as in current deployments, the left (high-
   order) two octets MUST be set to zero.
	 */
	
	private Inet4Address iPv4RemoteASBRID;
	
	public IPv4RemoteASBRID(){
		this.setTLVType(OSPFSubTLVTypes.IPv4RemoteASBRID);
	}
	
	public IPv4RemoteASBRID(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;

		System.arraycopy(this.iPv4RemoteASBRID.getAddress(),0, this.tlv_bytes, offset, 4);


	}
	
	public void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=4){
			throw new MalformedOSPFSubTLVException();
		}
			
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			this.iPv4RemoteASBRID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new MalformedOSPFSubTLVException();
		}	
	}

	public Inet4Address getIPv4RemoteASBRID() {
		return iPv4RemoteASBRID;
	}

	public void setIPv4RemoteASBRID(Inet4Address iPv4RemoteASBRID) {
		this.iPv4RemoteASBRID = iPv4RemoteASBRID;
	}
	@Override
	public boolean equals(Object obj) {
		return iPv4RemoteASBRID.equals(((IPv4RemoteASBRID) obj).getIPv4RemoteASBRID());
	}
	@Override
	public String toString(){
		String ret="IPv4RemoteASBRID: "+iPv4RemoteASBRID.toString();
		return ret;		
	}
	

}
