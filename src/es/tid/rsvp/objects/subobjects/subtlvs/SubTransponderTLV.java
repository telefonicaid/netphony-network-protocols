package es.tid.rsvp.objects.subobjects.subtlvs;

public class SubTransponderTLV extends SubTLV{
	
	private SubTransponderTLVModFormat ST_TLV_ModFormat; 	//5001
	private SubTransponderTLVFEC ST_TLV_FEC; 				//5002
	private SubTransponderTLVID ST_TLV_ID; 					//5005
	private SubTransponderTLVFS ST_TLV_FS; 					//5006
	private SubTransponderTLVTC ST_TLV_TC; 					//5020
	
	
	public SubTransponderTLV(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER);
	}
	
	public SubTransponderTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	
	
	
	public void encode(){	
		
		int len = 0;//Header TLV
		
		if (ST_TLV_ModFormat != null){
			ST_TLV_ModFormat.encode();
			len = len + ST_TLV_ModFormat.getTotalTLVLength();
		}
		if (ST_TLV_FEC != null){
			ST_TLV_FEC.encode();
			len = len + ST_TLV_FEC.getTotalTLVLength();
		}
		if (ST_TLV_ID != null){
			ST_TLV_ID.encode();
			len = len + ST_TLV_ID.getTotalTLVLength();
		}
		if (ST_TLV_FS != null){
			ST_TLV_FS.encode();
			len = len + ST_TLV_FS.getTotalTLVLength();
		}
		if (ST_TLV_TC != null){
			ST_TLV_TC.encode();
			len = len + ST_TLV_TC.getTotalTLVLength();
		}
		
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);
		encodeHeader();
		int offset=4;//Header TLV
	
		if (ST_TLV_ModFormat != null){
			System.arraycopy(ST_TLV_ModFormat.getTlv_bytes(),0,this.tlv_bytes,offset,ST_TLV_ModFormat.getTotalTLVLength());			
			offset=offset+ST_TLV_ModFormat.getTotalTLVLength();
		}
		if (ST_TLV_FEC != null){
			System.arraycopy(ST_TLV_FEC.getTlv_bytes(),0,this.tlv_bytes,offset,ST_TLV_FEC.getTotalTLVLength());			
			offset=offset+ST_TLV_FEC.getTotalTLVLength();
		}
		if (ST_TLV_ID != null){
			System.arraycopy(ST_TLV_ID.getTlv_bytes(),0,this.tlv_bytes,offset,ST_TLV_ID.getTotalTLVLength());			
			offset=offset+ST_TLV_ID.getTotalTLVLength();
		}		
		if (ST_TLV_FS != null){
			System.arraycopy(ST_TLV_FS.getTlv_bytes(),0,this.tlv_bytes,offset,ST_TLV_FS.getTotalTLVLength());			
			offset=offset+ST_TLV_FS.getTotalTLVLength();
		}
		if (ST_TLV_TC != null){
			System.arraycopy(ST_TLV_TC.getTlv_bytes(),0,this.tlv_bytes,offset,ST_TLV_TC.getTotalTLVLength());			
			offset=offset+ST_TLV_TC.getTotalTLVLength();
		}
		
	}
	
	
	
	
	@Override
	protected void decode() {
				
		//Decoding SubTransponderTLV
		boolean fin=false;
		int offset=4;//Position of the next subobject
		//if (ObjectLength==4){
		//fin=true;
		//}

		while (!fin) {
		
			int subtlvType=SubTLV.getType(tlv_bytes, offset);
			int subtlvLength=SubTLV.getTotalTLVLength(tlv_bytes, offset);
			
			switch(subtlvType) {
			
				case SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_MOD_FORMAT:
						ST_TLV_ModFormat = new SubTransponderTLVModFormat(this.tlv_bytes, offset);
						break;		
						
				case SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_FEC:
						ST_TLV_FEC = new  SubTransponderTLVFEC(this.tlv_bytes, offset);
						break;
						
				case SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_ID:
						ST_TLV_ID = new SubTransponderTLVID(this.tlv_bytes, offset);
						break;
						
				case SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_FS:
						ST_TLV_FS = new SubTransponderTLVFS(this.tlv_bytes, offset);
						break;
							
				case SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_TC:
						ST_TLV_TC = new SubTransponderTLVTC(this.tlv_bytes, offset);
						break;
							
				default:
						log.debug("Local Node Descriptor subtlv Unknown, "+subtlvType);
						break;
			}
			
			offset=offset+subtlvLength;
			
			if (offset>=this.TLVValueLength){
				fin=true;
			}
			else{
				log.debug("sigo leyendo SubTransponderTLV ");
			}
		}
	}

	public SubTransponderTLVModFormat getST_TLV_ModFormat() {
		return ST_TLV_ModFormat;
	}

	public void setST_TLV_ModFormat(SubTransponderTLVModFormat sT_TLV_ModFormat) {
		ST_TLV_ModFormat = sT_TLV_ModFormat;
	}

	public SubTransponderTLVFEC getST_TLV_FEC() {
		return ST_TLV_FEC;
	}

	public void setST_TLV_FEC(SubTransponderTLVFEC sT_TLV_FEC) {
		ST_TLV_FEC = sT_TLV_FEC;
	}

	public SubTransponderTLVID getST_TLV_ID() {
		return ST_TLV_ID;
	}

	public void setST_TLV_ID(SubTransponderTLVID sT_TLV_ID) {
		ST_TLV_ID = sT_TLV_ID;
	}

	public SubTransponderTLVFS getST_TLV_FS() {
		return ST_TLV_FS;
	}

	public void setST_TLV_FS(SubTransponderTLVFS sT_TLV_FS) {
		ST_TLV_FS = sT_TLV_FS;
	}

	public SubTransponderTLVTC getST_TLV_TC() {
		return ST_TLV_TC;
	}

	public void setST_TLV_TC(SubTransponderTLVTC sT_TLV_TC) {
		ST_TLV_TC = sT_TLV_TC;
	}
	
	
	public String toString() {
		
		StringBuffer sb=new StringBuffer(1000);
		
		if (ST_TLV_ModFormat != null)
			sb.append("\n\t> "+ST_TLV_ModFormat.toString()+"\n");
			
		if (ST_TLV_FEC != null)
			sb.append("\n\t> "+ST_TLV_FEC.toString()+"\n");
			
		if (ST_TLV_ID != null)
			sb.append("\n\t> "+ST_TLV_ID.toString()+"\n");
		
		if (ST_TLV_FS != null)
			sb.append("\n\t> "+ST_TLV_FS.toString()+"\n");
		
		if (ST_TLV_TC != null)
			sb.append("\n\t> "+ST_TLV_TC.toString()+"\n");
						
		return sb.toString();
	}
	
}
