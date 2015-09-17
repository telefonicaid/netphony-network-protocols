package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import es.tid.bgp.bgp4.update.tlv.complexFields.BitmapLabelSet;


public class LabelTypeTLV  extends OSPFSubTLV {

	
	private int priority;
	private long reserved;
	private int action;
	private int numLabels;
	private int length;
	
	/**
	 * 
   The value for Grid is set to 1 for the ITU-T DWDM grid as defined in [G.694.1].
	 */
	private int grid;
	
	/**
	 * Channel spacing
	 */
	private int channelSpacing;
	
	/**
	 * The Identifier field in lambda label format is used to distinguish
   different lasers (in one node) when they can transmit the same
   frequency lambda.
	 */
	private int identifier;
	
	/**
	 * n is a two's-complement integer to take either a positive, negative,
   or zero value.  This value is used to compute the frequency 
	 */
	private int n;
	
	private byte[] bytesBitmap;
	
	private int sumBytesBitmap;
	
	
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
		
		/*
		if (BitMapLS != null){
		
			BitMapLS.encode();
			valueLength += BitMapLS.getLength();
		}*/
		this.setTLVValueLength(valueLength);
		
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		
		this.encodeHeader();
		
		int offset = 4;
		
		tlv_bytes[offset]=(byte)(priority&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((reserved>>16) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((reserved>>8) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(reserved & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((action << 4) | ((numLabels >> 8) & 0x0F));
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(numLabels & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((length >> 8) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(length & 0xFF);
				
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((grid<<5)|(channelSpacing<<1)|(identifier>>>8));
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(identifier&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((n>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(n&0xFF);
		
		offset = offset + 1;
		
		int numberBytes =getNumberBytes( numLabels);
		
		System.arraycopy(this.bytesBitmap,0, tlv_bytes, offset,numberBytes);
		offset = offset+numberBytes;
	
		
		/*
		if (BitMapLS != null)
			System.arraycopy(this.BitMapLS.getBytes(),0 , this.tlv_bytes,offset,BitMapLS.getLength() );	*/
		
		
		
		
		
		
	}
	

	
	public void decode() {
		
		int offset=4;
		
		priority=((tlv_bytes[offset]&0xFF));
		
		offset = offset + 1;
		
		reserved = 0;
		for (int k = 0; k < 3; k++) {
			reserved = (reserved << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
		offset = offset + 3;
		
		action = (int) ((tlv_bytes[offset] & 0xF0) >> 4);
		
		numLabels = (int) (((tlv_bytes[offset]& 0x0F)<< 8)  | (tlv_bytes[offset+1] & 0xFF));
		
		offset = offset + 2;
		
		length = (int) (((tlv_bytes[offset]&0xFF)<<8)|(tlv_bytes[offset+1]&0xFF));
		
		offset = offset + 2;
				
		grid = (int) ((tlv_bytes[offset]&0xE0)>>>5);
		
		channelSpacing = (int) ((tlv_bytes[offset]&0x1E)>>>1);
		
		identifier = (int) ((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
		
		offset = offset + 2;
		n=0;
		n=( (int)(tlv_bytes[offset]&0xFF)<<8)| (int)(tlv_bytes[offset+1]&0xFF)|n;
		String str="";
		for (int i=offset;i<offset+2;i++){
			if((tlv_bytes[i]&0xFF)<=0x0F){
				str=str+"0"+Integer.toHexString(tlv_bytes[i]&0xFF);
			
			}else{
				str=str+Integer.toHexString(tlv_bytes[i]&0xFF);
			}
		}	
		log.info("Los bytes de n son: " + str + "." );
		offset = offset + 2;
		
		int numberBytes = getNumberBytes(this.numLabels);
		bytesBitmap =  new byte[numberBytes];
		
		System.arraycopy(tlv_bytes,offset, bytesBitmap , 0, numberBytes);
		
		
		
		//this.BitMapLS = new BitmapLabelSet (tlv_bytes,offset);
		
	}
	
	public int getNumberBytes(int num){
		int numberBytes = num/8;
		if ((numberBytes*8)<num){
			numberBytes++;
		}
		return numberBytes;
	}
	

	public LabelTypeTLV duplicate() {
		LabelTypeTLV lt = new LabelTypeTLV();
		lt.setPriority(this.priority);
		lt.setReserved(this.reserved);
		lt.setAction(this.action);
		lt.setNumLabels(this.numLabels);	
		lt.setLength(this.length);
		lt.setGrid(this.grid);
		lt.setChannelSpacing(this.channelSpacing);
		lt.setIdentifier(this.identifier);
		lt.setN(this.n);
		lt.setBytesBitmap(this.getBytesBitmap().clone());
		//lt.setBitMapLS(this.getBitMapLS().duplicate());
		
		return lt;
	}


	public byte[] getBytesBitmap() {
		return bytesBitmap;
	}


	public void setBytesBitmap(byte[] bytesBitmap) {
		this.bytesBitmap = bytesBitmap;
	}


	public String toString(){
		
		String str =  "[LabelTypeTLV " + 
		"\n Priority: "   + priority + 
		"\n Reserved: "   + reserved + /*"\n BitMapLS: " + BitMapLS.toString()*/
		"\n Action: "     + action   +
		"\n NumLabels: "  + numLabels +
		"\n Length: "     + length +
		"\n Grid: "       + grid +
		"\n CS: "         + channelSpacing +
		"\n Identifier: " + identifier +
		"\n n: "          + n +
		"\n BitMap: "  ;
				
		for (int i=0;i<bytesBitmap.length;i++){
			if((bytesBitmap[i]&0xFF)<=0x0F){
				str=str+"0"+Integer.toHexString(bytesBitmap[i]&0xFF);
			
			}else{
				str=str+Integer.toHexString(bytesBitmap[i]&0xFF);
			}
		}
		str+=">";
		
		return str;
	
	}

	
	

	public int getPriority() {
		return priority;
	}


	public long getReserved() {
		return reserved;
	}


	public void setReserved(long reserved) {
		this.reserved = reserved;
	}


	public int getAction() {
		return action;
	}


	public void setAction(int action) {
		this.action = action;
	}


	public int getNumLabels() {
		return numLabels;
	}


	public void setNumLabels(int numLabels) {
		this.numLabels = numLabels;
	}


	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}


	public int getGrid() {
		return grid;
	}


	public void setGrid(int grid) {
		this.grid = grid;
	}


	public int getChannelSpacing() {
		return channelSpacing;
	}


	public void setChannelSpacing(int channelSpacing) {
		this.channelSpacing = channelSpacing;
	}


	public int getIdentifier() {
		return identifier;
	}


	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}

/*
	public BitmapLabelSet getBitMapLS() {
		return BitMapLS;
	}


	public void setBitMapLS(BitmapLabelSet bitMapLS) {
		BitMapLS = bitMapLS;
	}
	
	*/
		
	
	
}