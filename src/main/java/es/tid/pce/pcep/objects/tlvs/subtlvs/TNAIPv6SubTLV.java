package es.tid.pce.pcep.objects.tlvs.subtlvs;

import java.net.Inet6Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


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
The format of the TNA�s TLVs is the same defined in [OIF-ENNI-OSPF] (see Figure 3.4). The following types are defined:
�	IPv4 TNA: 32776
�	IPv6 TNA: 32778
�	NSAP TNA: 32779
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
 * @author Alejandro Tovar de Due�as
 *
 */
public class TNAIPv6SubTLV extends PCEPSubTLV {
	
	private int Addr_length;
	public Inet6Address IPv6address;
	
	public TNAIPv6SubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv6);
		
	}
	
	public TNAIPv6SubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(20);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (Addr_length & 0xFF);
		System.arraycopy(IPv6address.getAddress(), 0, this.subtlv_bytes, 8, 16);
	}

	
	public void decode() {
		log.debug("Decoding TNA IPv6 Addreess");
		this.Addr_length=(int)this.subtlv_bytes[4];
		byte[] ip=new byte[16]; 
		System.arraycopy(this.subtlv_bytes,8, ip, 0, 16);
		try {
			IPv6address=(Inet6Address)Inet6Address.getByAddress(ip);
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
	
	public Inet6Address getIPv6address() {
		return IPv6address;
	}

	public void setIPv6address(Inet6Address IPv6address) {
		this.IPv6address = IPv6address;
	}
		
	public String toString(){
		return "IPv6 address: "+IPv6address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Addr_length;
		result = prime * result
				+ ((IPv6address == null) ? 0 : IPv6address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TNAIPv6SubTLV other = (TNAIPv6SubTLV) obj;
		if (Addr_length != other.Addr_length)
			return false;
		if (IPv6address == null) {
			if (other.IPv6address != null)
				return false;
		} else if (!IPv6address.equals(other.IPv6address))
			return false;
		return true;
	}
	
	
}