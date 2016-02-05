package es.tid.rsvp.objects.subobjects;

import java.net.*;

/**
 * IPv6 prefix ERO Subobject. RFC 3209
 *     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    | IPv6 address (16 bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)      | Prefix Length |      Resvd    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      L

         The L bit is an attribute of the subobject.  The L bit is set
         if the subobject represents a loose hop in the explicit route.
         If the bit is not set, the subobject represents a strict hop in
         the explicit route.

      Type

         0x02  IPv6 address

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.  The Length is always 20.

      IPv6 address

         An IPv6 address.  This address is treated as a prefix based on
         the prefix length value below.  Bits beyond the prefix are
         ignored on receipt and SHOULD be set to zero on transmission.

      Prefix Length

         Length in bits of the IPv6 prefix.

      Padding

         Zero on transmission.  Ignored on receipt.

   The contents of an IPv6 prefix subobject are a 16-octet IPv6 address,
   a 1-octet prefix length, and a 1-octet pad.  The abstract node
   represented by this subobject is the set of nodes that have an IP
   address which lies within this prefix.  Note that a prefix length of
   128 indicates a single IPv6 node.

 * 
*/
public class IPv6prefixEROSubobject extends EROSubobject{

		public Inet6Address ipv6address;//IPv4 address
		public int prefix;//IPv4 prefix
		
		public IPv6prefixEROSubobject(){
			this.setType(SubObjectValues.ERO_SUBOBJECT_IPV6PREFIX);
		}
		
		public IPv6prefixEROSubobject(byte [] bytes, int offset){
			super(bytes, offset);
			decode();
		}
		
		
		public void encode(){
			this.erosolength=20;
			this.subobject_bytes=new byte[this.erosolength];
			encodeSoHeader();
			System.arraycopy(ipv6address.getAddress(), 0, this.subobject_bytes, 2, 16);
			this.subobject_bytes[18]=(byte)prefix;
			this.subobject_bytes[19]=0x00;
		}
		
		/**
		 * Decodes the body of the SubObject
		 */
		public void decode(){
			byte[] ipadd=new byte[16]; 
			System.arraycopy(this.subobject_bytes,2, ipadd, 0, 16);
			try {
				ipv6address=(Inet6Address)Inet6Address.getByAddress(ipadd);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			prefix=(int)this.subobject_bytes[18];
		}

		public Inet6Address getIpv6address() {
			return ipv6address;
		}

		public void setIpv6address(Inet6Address ipv6address) {
			this.ipv6address = ipv6address;
		}

		public int getPrefix() {
			return prefix;
		}

		public void setPrefix(int prefix) {
			this.prefix = prefix;
		}

}
