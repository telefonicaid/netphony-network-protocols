package tid.rsvp.objects.subobjects;

import java.net.Inet6Address;
import java.net.UnknownHostException;

public class IPv6AddressRROSubobject extends RROSubobject {

	public Inet6Address ipv6address;//IPv4 address
	public int prefix;//IPv4 prefix
	
	public IPv6AddressRROSubobject(){
		this.setType(SubObjectValues.RRO_SUBOBJECT_IPV6ADDRESS);
	}
	
	public IPv6AddressRROSubobject(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	
	
	public void encode(){
		this.rrosolength=20;
		this.subobject_bytes=new byte[this.rrosolength];
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
