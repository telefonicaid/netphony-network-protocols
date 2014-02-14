package tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import tid.pce.pcep.objects.ObjectParameters;

/**
  *  
	PCEP extensions for GMPLS draft-ietf-pce-gmpls-pcep-extensions-01
2.4.2.1. IPV4-ADDRESS


   The format of the END-POINTS TLV object for IPv4 (TLV-Type=To be
   assigned) is as follows:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |  Length                       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          IPv4 address                         |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 
 * @author Alejandro Tovar de Due�as
 *
 */
public class EndPointIPv4TLV extends PCEPTLV {
	
	
	public Inet4Address IPv4address;
	
	
	public EndPointIPv4TLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_ENDPOINT_IPV4);
		
	}
	
	public EndPointIPv4TLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(IPv4address.getAddress(),0, this.tlv_bytes, 4, 4);
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
	}

	public Inet4Address getIPv4address() {
		return IPv4address;
	}

	public void setIPv4address(Inet4Address IPv4address) {
		this.IPv4address = IPv4address;
	}
		
	public String toString(){
		return "IPv4 address: "+IPv4address;
	}

}
