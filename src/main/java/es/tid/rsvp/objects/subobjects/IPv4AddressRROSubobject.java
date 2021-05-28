package es.tid.rsvp.objects.subobjects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class IPv4AddressRROSubobject extends RROSubobject {

	public Inet4Address ipv4address;//IPv4 address
	public int prefix;//IPv4 prefix
	
	public IPv4AddressRROSubobject(){
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX);
		this.setRrosolength(8);
	}
	public IPv4AddressRROSubobject(byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	
	public void encode(){
		this.rrosolength=8;
		this.subobject_bytes=new byte[this.rrosolength];
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
		prefix=(int)this.subobject_bytes[6];
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
		return ipv4address.getHostAddress();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipv4address == null) ? 0 : ipv4address.hashCode());
		result = prime * result + prefix;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IPv4AddressRROSubobject other = (IPv4AddressRROSubobject) obj;
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
