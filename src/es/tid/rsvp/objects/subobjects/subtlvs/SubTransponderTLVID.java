package es.tid.rsvp.objects.subobjects.subtlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class SubTransponderTLVID extends SubTLV {

	private long id;
	
	public SubTransponderTLVID(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_ID);
	}
	
	public SubTransponderTLVID(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
					
		int offset = 4;
		
		tlv_bytes[offset]=(byte)((id>>24) & 0xFF);
	    tlv_bytes[offset+1]=(byte)((id>>16) & 0xFF);
		tlv_bytes[offset+2]=(byte)((id>>8) & 0xFF);
		tlv_bytes[offset+3]=(byte)(id & 0xFF);	
		
	}
	
	protected void decode(){
		
		int offset = 4;
		id = 0;
		
		log.info("******************* Decodificando SubTransponderTLVID *****************");
		
		for (int k = 0; k < 4; k++) {
			id = (id << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
		log.info("Valor del id del SubTransponderTLV_ID: "+id+".");
		
		log.info("***************** FIN Decodificando SubTransponderTLVFS ***************");
	}
	
	public String toString(){
		String str =  "<SubTransponderTLVID" + " ID: " + id ;
		str+=">";
		return str;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
