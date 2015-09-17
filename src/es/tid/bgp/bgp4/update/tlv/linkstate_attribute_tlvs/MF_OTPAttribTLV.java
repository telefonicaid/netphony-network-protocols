
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.MF_OTPAttribSubTLV;


public class MF_OTPAttribTLV extends BGP4TLVFormat{
	
	private MF_OTPAttribSubTLV MF_OTP;
		
	
	public MF_OTPAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MF_OTP);
	}
	
	public MF_OTPAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		
		int offset = 4;
		int valueLength = 0;
		
		if (MF_OTP != null){
			
			MF_OTP.encode();
			valueLength += MF_OTP.getTotalTLVLength();
		}
		
		this.setTLVValueLength(valueLength);
		
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		
		this.encodeHeader();
		
		if (MF_OTP != null){
			
			System.arraycopy(this.MF_OTP.getTlv_bytes(),0 , this.tlv_bytes,offset,MF_OTP.getTotalTLVLength() );	
		}
		
		
	}
	
	protected void decode(){
		
		int offset = 4;
		
		MF_OTP = new MF_OTPAttribSubTLV(this.tlv_bytes,offset);		
		
	}


	public String toString(){
		if (MF_OTP != null){
			return MF_OTP.toString();
		}else {
			return "Empty MF_OTP";
		}
	}


	
		
	public MF_OTPAttribSubTLV getMF_OTP() {
		return MF_OTP;
	}

	public void setMF_OTP(MF_OTPAttribSubTLV mF_OTP) {
		MF_OTP = mF_OTP;
	}

	public MF_OTPAttribTLV duplicate(){
		
		MF_OTPAttribTLV a = new MF_OTPAttribTLV();
		
		if (MF_OTP != null){
			a.setMF_OTP(MF_OTP.duplicate());
		}
			
		return a;
		
	}

}
