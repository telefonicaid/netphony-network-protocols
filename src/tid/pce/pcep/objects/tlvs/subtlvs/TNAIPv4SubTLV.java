package tid.pce.pcep.objects.tlvs.subtlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;


/**
 All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

In GEYSERS,
The format of the TNA’s TLVs is the same defined in [OIF-ENNI-OSPF] (see Figure 3.4). The following types are defined:
•	IPv4 TNA: 32776
•	IPv6 TNA: 32778
•	NSAP TNA: 32779
The address length (in bits) is used to represent the TNA address prefix. 
          0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |               Type            |       Length                  |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Addr length   |                Reserved     			|
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	 	|                                                               |
        //                             TNA                             //
        |                     											|
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
Figure 3.4: TNA TLV

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class TNAIPv4SubTLV extends PCEPSubTLV {
	
	private int Addr_length;
	public Inet4Address IPv4address;
	
	public TNAIPv4SubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv4);
		
	}
	
	public TNAIPv4SubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(8);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (Addr_length & 0xFF);
		System.arraycopy(IPv4address.getAddress(), 0, this.subtlv_bytes, 8, 4);
	}

	
	public void decode() {
		log.finest("Decoding TNA IPv4 Addreess");
		this.Addr_length=(int)this.subtlv_bytes[4];
		byte[] ip=new byte[4]; 
		System.arraycopy(this.subtlv_bytes,8, ip, 0, 4);
		try {
			IPv4address=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
	}


	public void setAddr_length(int Addr_length) {
		this.Addr_length = Addr_length;
	}
	
	public int getAddr_length() {
		return Addr_length;
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