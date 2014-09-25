package tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import tid.pce.pcep.objects.ObjectParameters;

/**
 * UNNUMBERED-ENDPOINT TLV (as per draft-ietf-pce-gmpls-pcep-extensions-09 ).
 * 

   This TLV represent an unnumbered interface.  This TLV has the same
   semantic as in [RFC3477] The TLV value is encoded as follow (TLV-
   Type=TBA)

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          LSR's Router ID                      |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Interface ID (32 bits)                  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   This TLV MAY be ignored, in which case a PCRep with NO-PATH should be
   responded, as described in Section 2.5.1.
   
 * @author Oscar Gonzalez de Dios
 *
 */
public class UnnumberedEndpointTLV extends PCEPTLV {

	public Inet4Address IPv4address;
	
	private long ifID;
	
	public UnnumberedEndpointTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_UNNUMBERED_ENDPOINT);
	}
	
	public UnnumberedEndpointTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encode
	 */
	public void encode() {
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(IPv4address.getAddress(),0, this.tlv_bytes, 4, 4);
		this.getTlv_bytes()[8]=(byte)(this.ifID >>> 24);
		this.getTlv_bytes()[9]=(byte)(this.ifID >>> 16 & 0xff);
		this.getTlv_bytes()[10]=(byte)(this.ifID >>> 8 & 0xff);
		this.getTlv_bytes()[11]=(byte)(this.ifID & 0xff);
	}

	
	public void decode(){
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			IPv4address=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		int offset=8;
		ifID = 0;		
		for (int k = 0; k < 4; k++) {
			ifID = (ifID << 8) | (this.getTlv_bytes()[k+offset] & 0xff);
		}
	}


	public Inet4Address getIPv4address() {
		return IPv4address;
	}


	public void setIPv4address(Inet4Address iPv4address) {
		IPv4address = iPv4address;
	}
	
	public long getIfID() {
		return ifID;
	}
	public void setIfID(long ifID) {
		this.ifID = ifID;
	}
	
	public String toString(){
		if (IPv4address!=null){
			return this.IPv4address.getHostAddress()+":"+this.ifID;
		}else {
			return "";
		}
	}

}
