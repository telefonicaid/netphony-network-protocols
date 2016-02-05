package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class PceIdIPv4 extends PceId {
	/**
	 * PCE IPv4 address
	 */
	private Inet4Address pceIPAddress;
	
	public PceIdIPv4(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_PCE_ID);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_PCE_ID_IPV4);
	}
	public PceIdIPv4(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		decode();
	}
	@Override
	public void encode() {
		this.ObjectLength=8;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(pceIPAddress.getAddress(),0, this.object_bytes, 4, 4);		
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		if (this.ObjectLength!=8){
			throw new MalformedPCEPObjectException();
		}
		byte[] ip=new byte[4]; 
		System.arraycopy(this.object_bytes,4, ip, 0, 4);
		try {
			pceIPAddress=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	public Inet4Address getPceIPAddress() {
		return pceIPAddress;
	}
	public void setPceIPAddress(Inet4Address pceIPAddress) {
		this.pceIPAddress = pceIPAddress;
	}
	public String toString(){
		String ret="<PCE_ID ";
		if (pceIPAddress!=null){
			ret=ret+pceIPAddress+" >";
		}else {
			ret+=">";
		}
		return ret;
	}

}
