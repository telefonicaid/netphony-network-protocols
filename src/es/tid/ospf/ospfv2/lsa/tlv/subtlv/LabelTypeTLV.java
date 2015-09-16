package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import es.tid.bgp.bgp4.update.tlv.complexFields.BitmapLabelSet;


public class LabelTypeTLV  extends OSPFSubTLV {

	
	private int priority;
	private long reserved;
	private BitmapLabelSet BitMapLS;
	
	
	public LabelTypeTLV(){
		super();
		this.setTLVType(OSPFSubTLVTypes.LabelTypeTLV);		
	}
	
	
	public LabelTypeTLV(byte[] bytes, int offset) {
		super(bytes,offset);
		decode();
	}
	

	
	public void encode(){	
		
		int valueLength = 4;
		
		
		if (BitMapLS != null){
		
			BitMapLS.encode();
			valueLength += BitMapLS.getLength();
		}
		this.setTLVValueLength(valueLength);
		
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		
		int offset = 4;
		
		tlv_bytes[offset]=(byte)(priority&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((reserved>>16) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((reserved>>8) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(reserved & 0xFF);
		
		offset = offset + 1;
		
		if (BitMapLS != null)
			System.arraycopy(this.BitMapLS.getBytes(),0 , this.tlv_bytes,offset,BitMapLS.getLength() );	
	}
	

	
	public void decode() {
		
		int offset=4;
		
		priority=((tlv_bytes[offset]&0xFF));
		
		offset = offset + 4;
		
		this.BitMapLS = new BitmapLabelSet (tlv_bytes,offset);
		
	}
	

	public LabelTypeTLV duplicate() {
		LabelTypeTLV lt = new LabelTypeTLV();
		lt.setPriority(this.priority);
		lt.setBitMapLS(this.getBitMapLS().duplicate());
		
		return lt;
	}


	public String toString(){
		
		String str =  "[LabelTypeTLV " + "\n Priority: " + priority + "\n Reserved: " + reserved + "\n BitMapLS: " + BitMapLS.toString();
		str+=">";
		return str;
	
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public BitmapLabelSet getBitMapLS() {
		return BitMapLS;
	}


	public void setBitMapLS(BitmapLabelSet bitMapLS) {
		BitMapLS = bitMapLS;
	}
	
	
		
	
	
}