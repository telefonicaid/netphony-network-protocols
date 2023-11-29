
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class TransceiverClassAndAppAttribTLV extends BGP4TLVFormat{

	public long trans_app_code;
	public long trans_class;
	
	public TransceiverClassAndAppAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_TRANSCEIVER_CLASS_AND_APPLICATION);
	}
	
	public TransceiverClassAndAppAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;	
		
	    tlv_bytes[offset]=(byte)((trans_app_code>>24) & 0xFF);
	    tlv_bytes[offset+1]=(byte)((trans_app_code>>16) & 0xFF);
		tlv_bytes[offset+2]=(byte)((trans_app_code>>8) & 0xFF);
		tlv_bytes[offset+3]=(byte)(trans_app_code & 0xFF);	
		
		tlv_bytes[offset+4]=(byte)((trans_class>>24) & 0xFF);
	    tlv_bytes[offset+5]=(byte)((trans_class>>16) & 0xFF);
		tlv_bytes[offset+6]=(byte)((trans_class>>8) & 0xFF);
		tlv_bytes[offset+7]=(byte)(trans_class & 0xFF);	
		  
	}
	
	protected void decode(){
		int offset = 4;
		
		log.info("******************* Decodificando TCAA *****************");
			
	
		trans_app_code = 0;
		
		for (int k = 0; k < 4; k++) {
			trans_app_code = (trans_app_code << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}	
		
		offset = offset + 4;
		
		trans_class=0;
		
		for (int k = 0; k < 4; k++) {
			trans_class = (trans_class << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
		/*trans_app_code = ( (((long)tlv_bytes[offset]&(long)0xFF)<<24) | (((long)tlv_bytes[offset+1]&(long)0xFF)<<16) | (((long)tlv_bytes[offset+2]&(long)0xFF)<<8) | ((long)tlv_bytes[offset+3]&(long)0xFF) );
		trans_class = ( (((long)tlv_bytes[offset+4]&(long)0xFF)<<24) | (((long)tlv_bytes[offset+5]&(long)0xFF)<<16) | (((long)tlv_bytes[offset+6]&(long)0xFF)<<8) | ((long)tlv_bytes[offset+7]&(long)0xFF) );
		*/
		log.info("Transceiver Application Code : "+trans_app_code+ ".");
		log.info("Transceiver Class : "+trans_class+ ".");
		
		log.info("***************** FIN Decodificando TCAA ***************");
	}
	
	

	
	public long getTrans_app_code() {
		return trans_app_code;
	}

	public void setTrans_app_code(long trans_app_code) {
		this.trans_app_code = trans_app_code;
	}

	public long getTrans_class() {
		return trans_class;
	}

	public void setTrans_class(long trans_class) {
		this.trans_class = trans_class;
	}

	public String toString(){
		String str =  "<RP" + " Transceiver Application Code: " + trans_app_code + " Transceiver Class: " + trans_class;
		str+=">";
		return str;
	}
	
	

}
