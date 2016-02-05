package es.tid.rsvp.objects.subobjects.subtlvs;

public class SubTransponderTLVModFormat extends SubTLV {

	/**
	 *  0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|        Type = 5001            |           Length = 16         |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|S|I|       Modulation ID       |           (reserved)          |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                         Symbol rate                           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|           Num carriers        |          Bit/Symbol           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	 */
	
	private int standardizedFormat;
	private int input; 
	private int modulationID;
	private long symbolRate;	
	private int numCarriers;
	private int bitSymbol;
	
	public SubTransponderTLVModFormat(){
		super();
		this.setTLVType(SubTLVTypes.ERO_SUBTLV_SUBTRANSPONDER_MOD_FORMAT);
	}
	
	public SubTransponderTLVModFormat(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		this.setTotalTLVLength(16);	
		
		int offset =4;
		
		tlv_bytes[offset]=(byte)((standardizedFormat<<7)|(input<<6)|(modulationID>>>8));
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(modulationID&0xFF);
		
		offset = offset + 3;
		
		tlv_bytes[offset]=(byte)((symbolRate>>24) & 0xFF);
		
		offset = offset + 1;
		
	    tlv_bytes[offset]=(byte)((symbolRate>>16) & 0xFF);
	    
	    offset = offset + 1;
	    
		tlv_bytes[offset]=(byte)((symbolRate>>8) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(symbolRate & 0xFF);
		
		offset = offset + 1;
				
		tlv_bytes[offset]=(byte)((numCarriers>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(numCarriers&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((bitSymbol>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(bitSymbol&0xFF);
		
		  
	}
	
	protected void decode(){
		
		
		int offset = 4;
		
		standardizedFormat=((int)tlv_bytes[offset]&0x80)>>>7;
		input=(tlv_bytes[offset]&0x1E)>>>6;
		modulationID=((tlv_bytes[offset]&0x01)<<8)|(tlv_bytes[offset+1]&0xFF);
		
		offset = offset + 2; 
		//symbolRate=((tlv_bytes[offset]&0xFF)<<8)|(tlv_bytes[offset+1]&0xFF);
		symbolRate=0;
		for (int k = 0; k < 4; k++) {
			symbolRate = (symbolRate << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
		}
		
		offset = offset + 4;
		
		numCarriers=(((tlv_bytes[offset]&0xFF)<<8)|(tlv_bytes[offset+1]&0xFF));
		bitSymbol=(((tlv_bytes[offset+2]&0xFF)<<8)|(tlv_bytes[offset+3]&0xFF));
		
	}
	
	public String toString(){
		String str =  "[MF" + " SF: " + standardizedFormat + " I: " + input + "ModID: " + modulationID + "SR: " + symbolRate + "NC: " + numCarriers + "b/S: " + bitSymbol;
		str+=">";
		return str;
	}	
	

	public int getstandardizedFormat() {
		return standardizedFormat;
	}

	public void setstandardizedFormat(int standardizedFormat) {
		this.standardizedFormat = standardizedFormat;
	}

	public int getInput() {
		return input;
	}

	public void setInput(int input) {
		this.input = input;
	}

	public int getModulationID() {
		return modulationID;
	}

	public void setModulationID(int modulationID) {
		this.modulationID = modulationID;
	}

	public long getSymbolRate() {
		return symbolRate;
	}

	public void setSymbolRate(long symbolRate) {
		this.symbolRate = symbolRate;
	}

	public int getNumCarriers() {
		return numCarriers;
	}

	public void setNumCarriers(int numCarriers) {
		this.numCarriers = numCarriers;
	}

	public int getBitSymbol() {
		return bitSymbol;
	}

	public void setBitSymbol(int bitSymbol) {
		this.bitSymbol = bitSymbol;
	}
	
	
	
	
}
