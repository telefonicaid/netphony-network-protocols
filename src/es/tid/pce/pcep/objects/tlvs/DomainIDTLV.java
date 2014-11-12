package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * DomainID TLV, non-standard encoding implemented.
 * Defined in draft-ietf-pce-hierarchy-extensions-01.
 * Enconding: propietary based on STRONGEST project interoperability.
 * TLV ID: 32771, non-standard
 * 
 * @author ogondio
 *
 */
public class DomainIDTLV extends PCEPTLV {

	Inet4Address domainId;
	
	public DomainIDTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_DOMAIN_ID_TLV;
		try {
			domainId=(Inet4Address) Inet4Address.getByName("0.0.0.1");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DomainIDTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encodes the Domain Id TLV
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		System.arraycopy(domainId.getAddress(),0, this.tlv_bytes, 4, 4);
	}

	
	
	public void decode() throws MalformedPCEPObjectException{
		if (this.TLVValueLength!=4){
			throw new MalformedPCEPObjectException();
		}
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			domainId=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
			throw new MalformedPCEPObjectException();
		}
		
	}
	
	
	
	public Inet4Address getDomainId() {
		return domainId;
	}

	public void setDomainId(Inet4Address domainId) {
		this.domainId = domainId;
	}

	public String toString(){
		String res = "DOMAIN ID: "+	domainId;
		return res;
	}
	


}
