package es.tid.rsvp.objects.subobjects;

/**
 * IPv4 prefix ERO Subobject. RFC 3209
 *    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    | IPv4 address (4 bytes)        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv4 address (continued)      | Prefix Length |      Resvd    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      L

         The L bit is an attribute of the subobject.  The L bit is set
         if the subobject represents a loose hop in the explicit route.
         If the bit is not set, the subobject represents a strict hop in
         the explicit route.

      Type

         0x01  IPv4 address

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.  The Length is always 8.

      IPv4 address

         An IPv4 address.  This address is treated as a prefix based on
         the prefix length value below.  Bits beyond the prefix are
         ignored on receipt and SHOULD be set to zero on transmission.

      Prefix length

         Length in bits of the IPv4 prefix

      Padding

         Zero on transmission.  Ignored on receipt.

   The contents of an IPv4 prefix subobject are a 4-octet IPv4 address,
   a 1-octet prefix length, and a 1-octet pad.  The abstract node
   represented by this subobject is the set of nodes that have an IP
   address which lies within this prefix.  Note that a prefix length of
   32 indicates a single IPv4 node.
   
 * @author Oscar Gonzalez de Dios
 */
import java.net.*;

public class IPv4prefixEROSubobject extends EROSubobject{
	
	public Inet4Address ipv4address;//IPv4 address
	public int prefix;//IPv4 prefix
	
	public IPv4prefixEROSubobject(){
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX);
	}
	public IPv4prefixEROSubobject(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	
	public void encode(){
		erosolength=8;
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		System.arraycopy(ipv4address.getAddress(), 0, this.subobject_bytes, 2, 4);
		this.subobject_bytes[6]=(byte)prefix;
		this.subobject_bytes[7]=0x00;
	}
	
	public void decode(){
		byte[] ipadd=new byte[4]; 
		System.arraycopy(this.subobject_bytes,2, ipadd, 0, 4);
		try {
			ipv4address=(Inet4Address)Inet4Address.getByAddress(ipadd);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		prefix=this.subobject_bytes[6]&0xFF;
	}

	public Inet4Address getIpv4address() {
		return ipv4address;
	}

	public void setIpv4address(Inet4Address ipv4address) {
		this.ipv4address = ipv4address;
	}

	public int getPrefix() {
		return prefix;
	}

	public void setPrefix(int prefix) {
		this.prefix = prefix;
	}
	
	public String toString(){
		String resp=ipv4address+"/"+prefix;
		return resp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((ipv4address == null) ? 0 : ipv4address.hashCode());
		result = prime * result + prefix;
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
		IPv4prefixEROSubobject other = (IPv4prefixEROSubobject) obj;
		if (ipv4address == null) {
			if (other.ipv4address != null)
				return false;
		} else if (!ipv4address.equals(other.ipv4address))
			return false;
		if (prefix != other.prefix)
			return false;
		return true;
	}
	
	
	

}
