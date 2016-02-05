package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * DomainID TLV, non-standard encoding implemented.
 * Defined in draft-ietf-pce-hierarchy-extensions-02.
 * TLV ID: 32771, non-standard
 * Enconding: as per draft-ietf-pce-hierarchy-extensions-02.

 *  
 *  0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |           Domain Type         |            Reserved           |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                       Domain ID                               |
      //                                                             //
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                          Figure 1: Domain-ID TLV

 * 
 * @author ogondio
 *
 */
public class DomainIDTLV extends PCEPTLV {
	
	int domainType;

	Inet4Address domainId;
	
	public DomainIDTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_DOMAIN_ID_TLV;
		try {
			domainType=1;//Default value
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
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		
		System.arraycopy(domainId.getAddress(),0, this.tlv_bytes, 8, 4);
	}

	
	
	public void decode() throws MalformedPCEPObjectException{
		if (this.TLVValueLength!=8){
			throw new MalformedPCEPObjectException("Bad DomainIDTLV lenght: "+this.TLVValueLength);
		}
		 
		int offset=4;
		domainType=((this.tlv_bytes[offset]<<8)& 0xFF00) |  (this.tlv_bytes[offset+1] & 0xFF);
		offset = 8;
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
		try {		
			domainId=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
			throw new MalformedPCEPObjectException("Bad DomainIDTLV address");
		}
		

		
	}
	
	
	
	public Inet4Address getDomainId() {
		return domainId;
	}

	public void setDomainId(Inet4Address domainId) {
		this.domainId = domainId;
	}

	public String toString(){
		String res; 
		if (domainType==1) {
			res = "IGP Area ID with Domain ID:  "+	domainId;
		}else if (domainType==1) {
		 res = "AS Number with Domain ID: "+	domainId;
		} else {
			res ="unknown domain ID type: "+ domainId;
		}
		return res;
	}
	


}
