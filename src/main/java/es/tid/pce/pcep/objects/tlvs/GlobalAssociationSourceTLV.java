package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.ObjectParameters;

/**
 *  Global Association Source TLV (Type: 30)	[RFC8697]
 *  
 *  The Global Association Source TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Global Association
   Source TLV are as per Section 4 of [RFC6780].
 *  
 * @author Oscar Gonzalez de Dios
 *
 */
public class GlobalAssociationSourceTLV extends PCEPTLV {
	
	/*
	 *

   The Global Association Source TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Global Association
   Source TLV are as per Section 4 of [RFC6780].

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Global Association Source                        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

             Figure 5: The Global Association Source TLV Format

   Type:  30

   Length:  Fixed value of 4 bytes.

   Global Association Source:  As defined in Section 4 of [RFC6780]. 
	 */
	
	
	public Inet4Address  globalAssociationSource ;
	
	
	public GlobalAssociationSourceTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_GLOBAL_ASSOCIATION_SOURCE);
		
	}
	
	public GlobalAssociationSourceTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		System.arraycopy(globalAssociationSource.getAddress(),0, this.tlv_bytes, 4, 4);
	}

	
	public void decode(){
		byte[] ip=new byte[4]; 
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			globalAssociationSource=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	 
	}

	public Inet4Address getGlobalAssociationSource() {
		return globalAssociationSource;
	}

	public void setGlobalAssociationSource(Inet4Address globalAssociationSource) {
		this.globalAssociationSource = globalAssociationSource;
	}
	

}
