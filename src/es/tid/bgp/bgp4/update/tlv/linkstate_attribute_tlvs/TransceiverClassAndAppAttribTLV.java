
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class TransceiverClassAndAppAttribTLV extends BGP4TLVFormat{

	private int trans_app_code;
	private int trans_class;
	
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
		int offset = 1;
		
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
		int offset = 0;
		
		log.info("******************* Decodificando TCAA *****************");
				
		trans_app_code = ( (((int)tlv_bytes[offset]&(int)0xFF)<<24) | (((int)tlv_bytes[offset+1]&(int)0xFF)<<16) | (((int)tlv_bytes[offset+2]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+3]&(int)0xFF) );
		trans_class = ( (((int)tlv_bytes[offset+4]&(int)0xFF)<<24) | (((int)tlv_bytes[offset+5]&(int)0xFF)<<16) | (((int)tlv_bytes[offset+6]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+7]&(int)0xFF) );
		
		log.info("Transceiver Application Code : "+trans_app_code+ ".");
		log.info("Transceiver Class : "+trans_class+ ".");
		
		log.info("***************** FIN Decodificando TCAA ***************");
	}
	
	

	
	public int getTrans_app_code() {
		return trans_app_code;
	}

	public void setTrans_app_code(int trans_app_code) {
		this.trans_app_code = trans_app_code;
	}

	public int getTrans_class() {
		return trans_class;
	}

	public void setTrans_class(int trans_class) {
		this.trans_class = trans_class;
	}

	public String toString(){
		String str =  "<RP" + " Transceiver Application Code: " + trans_app_code + " Transceiver Class: " + trans_class;
		str+=">";
		return str;
	}
	
	

}
