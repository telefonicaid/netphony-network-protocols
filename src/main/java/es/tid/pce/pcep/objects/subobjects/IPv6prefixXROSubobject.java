package es.tid.pce.pcep.objects.subobjects;

import java.net.*;

/**
 * IPv6 prefix Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |X|  Type = 2   |     Length    | IPv6 address (16 bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)      | Prefix Length |   Attribute   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
*/
public class IPv6prefixXROSubobject extends XROSubobject{

		public Inet6Address ipv6address;//IPv4 address
		public int prefix;//IPv4 prefix
		
		public IPv6prefixXROSubobject(){
			this.setType(XROSubObjectValues.XRO_SUBOBJECT_IPV6PREFIX);
		}
		
		public IPv6prefixXROSubobject(byte [] bytes, int offset){
			super(bytes, offset);
			decode();
		}
		
		
		public void encode(){
			this.erosolength=20;
			this.subobject_bytes=new byte[this.erosolength];
			encodeSoHeader();
			System.arraycopy(ipv6address.getAddress(), 0, this.subobject_bytes, 2, 16);
			this.subobject_bytes[18]=(byte)prefix;
			this.subobject_bytes[19]=(byte)attribute;
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
			prefix=this.subobject_bytes[18]&0xFF;
			attribute=this.subobject_bytes[19]&0xFF;
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
