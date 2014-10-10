package es.tid.pce.pcep.objects.subobjects;

import java.net.Inet4Address;
import java.net.UnknownHostException;


/**
 *   IPv4 prefix Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |X|  Type = 1   |     Length    | IPv4 address (4 bytes)        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv4 address (continued)      | Prefix Length |   Attribute   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author Oscar Gonzalez de Dios
 */

public class IPv4PrefixXROSubobject extends XROSubobject{

		public Inet4Address ipv4address;//IPv4 address
		
		public int prefix;//IPv4 prefix
		
		public IPv4PrefixXROSubobject(){
			super();
			this.setType(XROSubObjectValues.XRO_SUBOBJECT_IPV4PREFIX);
		}
		public IPv4PrefixXROSubobject(byte [] bytes, int offset){
			super(bytes, offset);
			decode();
		}
		
		public void encode(){
			this.erosolength=8;
			this.subobject_bytes=new byte[this.erosolength];
			encodeSoHeader();
			System.arraycopy(ipv4address.getAddress(), 0, this.subobject_bytes, 2, 4);
			this.subobject_bytes[6]=(byte)prefix;
			this.subobject_bytes[7]=(byte)attribute;
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
			attribute=subobject_bytes[7]&0xFF;
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

	
}
