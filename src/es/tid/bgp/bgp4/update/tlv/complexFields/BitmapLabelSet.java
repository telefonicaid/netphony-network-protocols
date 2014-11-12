package es.tid.bgp.bgp4.update.tlv.complexFields;

import org.slf4j.Logger;



import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.gmpls.DWDMWavelengthLabel;

/**
 * 
2.2.3. Bitmap Label Set

In the case of Action = 4, the bitmap the label set format is given
by:

   0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |  4    |   Num Labels          |             Length            |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |                         Base Label                            |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |    Bit Map Word #1 (Lowest numerical labels)                  |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  :                                                               :
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  |    Bit Map Word #N (Highest numerical labels)                 |
  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


Where Num Labels in this case tells us the number of labels
represented by the bit map. Each bit in the bit map represents a
particular label with a value of 1/0 indicating whether the label is
in the set or not. Bit position zero represents the lowest label and
corresponds to the base label, while each succeeding bit position
represents the next label logically above the previous.

The size of the bit map is Num Label bits, but the bit map is padded
out to a full multiple of 32 bits so that the TLV is a multiple of
four bytes. Bits that do not represent labels (i.e., those in
positions (Num Labels) and beyond SHOULD be set to zero and MUST be
ignored.
 * @author Marta Cuaresma Saturio
 *
 */
public class BitmapLabelSet  extends LabelSetField {

	private byte[] bytesBitmap;
	private byte[] bytesBitmapReserved;
	private int sumBytesBitmap;
	//private int n;
	private DWDMWavelengthLabel dwdmWavelengthLabel;

    public BitmapLabelSet() {
    	action = 4;	
	}

	public BitmapLabelSet(byte[] bytes, int offset){
		this.length = (int) (((bytes[offset+2]<<8)& 0xFF00) |  (bytes[offset+3] & 0xFF));
		this.bytes = new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
		dwdmWavelengthLabel = new DWDMWavelengthLabel();
		decodeHeader();
		this.decode();
	}
	
	public BitmapLabelSet(byte[] bytesBitMap){		
		super();
		
	}
	
	public void encode(){		
		int offset = 4;//Cabecera
		int numberBytes =getNumberBytes( numLabels);	
		try {
			dwdmWavelengthLabel.encode();
		} catch (RSVPProtocolViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		int size_bytes= 4+4+numberBytes;/*Cabecera+dwdmWavelengthLabel+bytesBitMap*/
		
		if ((size_bytes%4)!=0){
			size_bytes=size_bytes+(4-(size_bytes%4));
		}
		
		this.setLength(size_bytes);
		this.bytes = new byte[size_bytes];
		
		encodeHeader();
		
		System.arraycopy(dwdmWavelengthLabel.getBytes(), 0, this.bytes, offset, 4);
		offset = offset+4;
		System.arraycopy(this.bytesBitmap,0, this.bytes, offset,numberBytes);
		offset = offset+numberBytes;
		for (int i=offset;i<size_bytes;i++)
			this.bytes[i] = 0x00;
	
		}
	

	@Override
	public void decode() {
		int offset=4;
		try {
			dwdmWavelengthLabel.decode(this.bytes,offset);
		} catch (RSVPProtocolViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//n = dwdmWavelengthLabel.getN();
		offset=offset+4;	
		int numberBytes = getNumberBytes(this.numLabels);
		bytesBitmap =  new byte[numberBytes];
		
		System.arraycopy(this.bytes,offset, bytesBitmap , 0, numberBytes);
	}
	

	public byte[] getBytesBitmapReserved() {
		return bytesBitmapReserved;
	}

	public DWDMWavelengthLabel getDwdmWavelengthLabel() {
		return dwdmWavelengthLabel;
	}

	public void setBytesBitmapReserved(byte[] bytesBitmapReserved) {
		this.bytesBitmapReserved = bytesBitmapReserved;

	}

	public void setDwdmWavelengthLabel(DWDMWavelengthLabel dwdmWavelengthLabel) {
		this.dwdmWavelengthLabel = dwdmWavelengthLabel;
	}

	public byte[] getBytesBitMap() {
		return bytesBitmap;
	}
	public void createBytesBitMap(byte[] bytesBitMap) {
		this.bytesBitmap = new byte[bytesBitMap.length];
		System.arraycopy(bytesBitMap,0,this.bytesBitmap, 0,bytesBitMap.length);
		
	}
	public void arraycopyBytesBitMap(byte[] bytesBitMap) {				
		System.arraycopy(bytesBitMap,0,this.bytesBitmap, 0,bytesBitMap.length);
		
	}

	public void arraycopyBytesBitMap(byte[] bytesBitMap,int lambdaIni, int lambdaEnd) {
		System.arraycopy(bytesBitMap,lambdaIni,this.bytesBitmap, lambdaIni,lambdaEnd-lambdaIni);
		}
	

	public void createBytesBitMapRes(byte[] bytesBitMapRes) {
		this.bytesBitmapReserved = new byte[bytesBitMapRes.length];
		System.arraycopy(bytesBitMapRes,0,this.bytesBitmapReserved, 0,bytesBitMapRes.length);
		
	}

	public void setBytesBitmap(byte[] bytesBitmap) {
		this.bytesBitmap = bytesBitmap;
	}

	private boolean equalsBytes(byte[] bytes1, byte[] bytes2){		
		for (int i =0;i<bytes1.length;i++){
			if ((bytes1[i] | bytes2[i]) != (bytes1[i])){
				return false;
			}				
		}
		return true;
	}
	public int getNumberBytes(){
		return bytesBitmap.length;
	}

	public int getNumberBytes(int num){
		int numberBytes = num/8;
		if ((numberBytes*8)<num){
			numberBytes++;
		}
		return numberBytes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (dwdmWavelengthLabel.equals(((BitmapLabelSet)obj).getDwdmWavelengthLabel()))
			return equalsBytes(bytesBitmap,((BitmapLabelSet)obj).getBytesBitMap());		
		return false;
	}

	@Override
	public String toString() {
		String ret="";
		
		if (dwdmWavelengthLabel!= null){
			ret=ret+"n: "+ String.valueOf(dwdmWavelengthLabel.getN())+"\r\n";
		}
		ret=ret+"Bytes Bitmap: ";
		for (int i=0;i<bytesBitmap.length;i++){
			if((bytesBitmap[i]&0xFF)<=0x0F){
				ret=ret+"0"+Integer.toHexString(bytesBitmap[i]&0xFF);
			
			}else{
				ret=ret+Integer.toHexString(bytesBitmap[i]&0xFF);
			}
		}
		ret=ret+"\r\n";
		ret=ret+"Bytes Reserved Bitmap: ";
		for (int i=0;i<bytesBitmapReserved.length;i++){
			if((bytesBitmapReserved[i]&0xFF)<=0x0F){
				ret=ret+"0"+Integer.toHexString(bytesBitmapReserved[i]&0xFF);
			
			}else{
			ret=ret+Integer.toHexString(bytesBitmapReserved[i]&0xFF);
			}
		}
		
		ret=ret+"\r\n";
		return ret;
	}
	
	public int getSumaBits(){
		sumBytesBitmap=0;
		for (int i=0; i<bytesBitmap.length*8; i++){
			if ((bytesBitmap[i/8]&(0x80>>(i%8))) == (0x80>>i%8)){
				sumBytesBitmap = sumBytesBitmap + 1;
			}
		}
		
		return sumBytesBitmap;
	}

}
