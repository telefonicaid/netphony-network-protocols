package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Remote AS Number Sub-TLV From RFC 5392
 * 3.3.1. Remote AS Number Sub-TLV


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



Chen, et al.                Standards Track                    [Page 10]

 
RFC 5392            OSPF Extensions for Inter-AS TE         January 2009



   The Remote AS Number field has 4 octets.  When only two octets are
   used for the AS number, as in current deployments, the left (high-
   order) two octets MUST be set to zero.
 * @author ogondio
 *
 */
public class RemoteASNumber extends OSPFSubTLV {
	
	private Inet4Address remoteASNumber;

	public RemoteASNumber(){
		this.setTLVType(OSPFSubTLVTypes.RemoteASNumber);
		
	}
	
	public RemoteASNumber(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;
		System.arraycopy(this.remoteASNumber.getAddress(),0, this.tlv_bytes, offset, 4);

	}
	
	public void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=4){
			throw new MalformedOSPFSubTLVException();
		}
			
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			this.remoteASNumber=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new MalformedOSPFSubTLVException();
		}	
	}
	
	
	
	public Inet4Address getRemoteASNumber() {
		return remoteASNumber;
	}

	public void setRemoteASNumber(Inet4Address remoteASNumber) {
		this.remoteASNumber = remoteASNumber;
	}

	@Override
	public boolean equals(Object obj) {
		return remoteASNumber.equals(((RemoteASNumber) obj).getRemoteASNumber());
	}
	@Override
	public String toString(){
		String ret="remoteASNumber: "+remoteASNumber.toString();
		return ret;		
	}

}
