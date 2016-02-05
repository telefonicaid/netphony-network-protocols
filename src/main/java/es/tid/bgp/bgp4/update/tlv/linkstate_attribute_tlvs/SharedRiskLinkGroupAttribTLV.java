
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class SharedRiskLinkGroupAttribTLV extends BGP4TLVFormat{

	private int srlg_length;
	private int srlg_type;
	public long srlg_value[];
	
	public SharedRiskLinkGroupAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_SHARED_RISK_LINK_GROUP);
	}
	
	public SharedRiskLinkGroupAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		
		int offset = 4;
		int i = 0 ;
		
		//log.info("******************* Codificando SRLG *****************");
		//log.info("Offset1 SRLG: "+offset+".");
		
		srlg_length = this.getTLVValueLength()/4;
		
		//log.info("Entrando en el for");
		
		
		for (i=0 ; i<srlg_length ; i++){
			tlv_bytes[offset]=(byte)((srlg_value[i]>>24) & 0xFF);
	    	tlv_bytes[offset+1]=(byte)((srlg_value[i]>>16) & 0xFF);
			tlv_bytes[offset+2]=(byte)((srlg_value[i]>>8) & 0xFF);
			tlv_bytes[offset+3]=(byte)(srlg_value[i] & 0xFF);	
		}
		
		offset = offset + srlg_length;
		//log.info("Offset2 SRLG: "+offset+ ".");
		//log.info("***************** FIN Codificando SRLG ***************");
	}
	
	protected void decode(){
		
		int offset = 4;
		int i;
		
		log.info("******************* Decodificando SRLG *****************");
		//log.info("Offset1 SRLG: "+offset+".");
		
		srlg_length = this.getTLVValueLength()/4;
		log.info("Longitud SRLG: "+srlg_length+".");
		srlg_value= new long[srlg_length];
	   	//No funciona el for
		for (i=0 ; i<srlg_length ; i++){
			
			srlg_value[i] =0;
			
			for (int k = 0; k < 4; k++) {
				srlg_value[i]  = (srlg_value[i]  << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
			}	
			offset=offset+4;
			log.info("Valor del SRLG: "+srlg_value[i]+".");
		
		offset = offset + 4;
		}
		//log.info("Offset2 SRLG: "+offset+ ".");
		log.info("***************** FIN Decodificando SRLG ***************");
	}
	
	

	public float getSharedRisk_type() {
		return srlg_type;
	}

	public void setSharedRisk_type(int srlg_type) {
		this.srlg_type = srlg_type;
	}

	
	public String toString(){
		
		int i;
		String str = "";
		
		for (i=0; i<srlg_length ; i++){
		str =  "<RP"+" SRLG Value: " + srlg_value[i];
		str+=">";
		}
		return str;
	}
	
	

}
