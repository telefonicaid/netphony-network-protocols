package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * PCEP Monitoring Object from RFC 5886
 * 4.1. PCC-ID-REQ Object

   The PCC-ID-REQ object is used to specify the IP address of the
   requesting PCC.

   The PCC-ID-REQ MUST be inserted within a PCReq or a PCMonReq message
   to specify the IP address of the requesting PCC.

   Two PCC-ID-REQ objects (for IPv4 and IPv6) are defined.  PCC-ID-REQ
   Object-Class (20) has been assigned by IANA.  PCC-ID-REQ Object-Type
   (1 for IPv4 and 2 for IPv6) has been assigned by IANA.

	The format of the PCC-ID-REQ object body for IPv4 and IPv6 are as
   follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           IPv4 Address                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   |                           IPv6 Address                        |
   |                                                               |
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The PCC-ID-REQ object body has a fixed length of 4 octets for IPv4
   and 16 octets for IPv6.

 * @author ogondio 
 *
 */
public class PccReqId extends PCEPObject {
	private Inet4Address PCCIpAddress;
	public PccReqId(){
		super(); 
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_PCC_REQ_ID);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_PCC_REQ_ID);
	}
	
	public PccReqId(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);			
		decode();
		
	}
	
	@Override
	public void encode() {

		// FIXME: no estoy segura que sean 8 bytes
		ObjectLength=8;/* 4 bytes de la cabecera + 4 de la direccion */
		object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(PCCIpAddress.getAddress(),0, this.object_bytes, 4, 4);
		
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		// TODO Auto-generated method stub
		if (this.ObjectLength!=8){
			throw new MalformedPCEPObjectException();
		}
		byte[] ip=new byte[4]; 
		System.arraycopy(this.object_bytes,4, ip, 0, 4);
		try {
			PCCIpAddress=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void setPCCIpAddress(Inet4Address address) {
		this.PCCIpAddress = address;
	}
	
	public String toString(){
		return "PCC IP: "+PCCIpAddress;
	}

	public Inet4Address getPCCIpAddress() {
		return PCCIpAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((PCCIpAddress == null) ? 0 : PCCIpAddress.hashCode());
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
		PccReqId other = (PccReqId) obj;
		if (PCCIpAddress == null) {
			if (other.PCCIpAddress != null)
				return false;
		} else if (!PCCIpAddress.equals(other.PCCIpAddress))
			return false;
		return true;
	}
	
	

}
