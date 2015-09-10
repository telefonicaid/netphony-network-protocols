package es.tid.rsvp.objects.subobjects.subtlvs;

public class SubTransponderTLVTC extends SubTLV {

	private long trans_class;
		
	public SubTransponderTLVTC(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_TC);
	}
	
	public SubTransponderTLVTC(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		
		int offset = 4;
		
		tlv_bytes[offset]=(byte)((trans_class>>24) & 0xFF);
	    tlv_bytes[offset+1]=(byte)((trans_class>>16) & 0xFF);
		tlv_bytes[offset+2]=(byte)((trans_class>>8) & 0xFF);
		tlv_bytes[offset+3]=(byte)(trans_class & 0xFF);
		  
	}
	
	protected void decode(){
		
		int offset = 4;
		trans_class = 0;
		
		log.info("******************* Decodificando SubTransponderTLVTC *****************");
		
		for (int k = 0; k < 4; k++) {
			trans_class = (trans_class << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
		log.info("Valor del id del SubTransponderTLV_TC: "+trans_class+".");
		
		log.info("***************** FIN Decodificando SubTransponderTLVTC ***************");
		
	}
	
	public String toString(){
		String str =  "<SubTransponderTLVTC" + " Transceiver Class: " + trans_class ;
		str+=">";
		return str;
	}

	public long getTrans_class() {
		return trans_class;
	}

	public void setTrans_class(long trans_class) {
		this.trans_class = trans_class;
	}
	
	
	
}
