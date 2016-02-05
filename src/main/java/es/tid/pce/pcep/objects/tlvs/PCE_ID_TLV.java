package es.tid.pce.pcep.objects.tlvs;

/**
 *   0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |           Address Type        |            Reserved           |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      //                     PCE IP Address                          //
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                           Figure 3: PCE-ID TLV
 */

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

public class PCE_ID_TLV extends PCEPTLV {

	int addresType;
	Inet4Address pceId;
	
	public PCE_ID_TLV(){
		this.addresType=1;
		this.TLVType=ObjectParameters.PCEP_TLV_PCE_ID_TLV;
	}
	
	public PCE_ID_TLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	public void encode() {
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		int offset=4;
		this.tlv_bytes[offset]=(byte)((this.addresType>>8) & 0xFF);
		this.tlv_bytes[offset+1]=(byte)(this.addresType & 0xFF);
		System.arraycopy(pceId.getAddress(),0, this.tlv_bytes, 8, 4);

	}

	
	private void decode()throws MalformedPCEPObjectException {
		if (this.TLVValueLength!=8){
			throw new MalformedPCEPObjectException("Bad Length of PCE_ID_TLV");
		}
		int offset=4;
		addresType=((this.tlv_bytes[offset]<<8)& 0xFF00) |  (this.tlv_bytes[offset+1] & 0xFF);
		byte[] ip=new byte[4]; 
		offset=8;
		System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
		try {
			pceId=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {			
			e.printStackTrace();
			throw new MalformedPCEPObjectException("Bad IP Address");
		}
		
	}
	
	
	
	public Inet4Address getPceId() {
		return pceId;
	}

	public void setPceId(Inet4Address pceId) {
		this.pceId = pceId;
	}

	public String toString(){
		String res = "PCE ID: "+pceId;
		return res;
	}
	

}
