package es.tid.pce.pcep.objects;

import java.util.Arrays;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.NCF;

/**
 * �	Attaching a new LABEL object (class 129, type 1) as an attribute of the computed path. If there is only one label object it is the SUGGESTED_LABEL.
     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Object-Class  |   OT  |Res|P|I|   Object Length (bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    4  |    Num Labels         |          Length               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    NCF                                                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    Bit Map Word #1 (Lowest numerical labels)                  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   :                                                               :
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    Bit Map Word #N (Highest numerical labels)                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */

public class BitmapLabelSet extends LabelSet{
	
	private int numLabels;
	private byte[] bytesBitmap;
	private NCF ncf;

	//Constructors
	
	public BitmapLabelSet(){
		super();
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_LABEL_SET_BITMAP);
	}

	public BitmapLabelSet(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode CLOSE message
	 * Only reason is encoded. The rest is set to 0
	 */
	public void encode(){
		int offset = 4;//header
		int numberBytes =getNumberBytes(numLabels);	
		if (ncf!=null){
			try {
				ncf.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			ncf=new NCF();
			try {
				ncf.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 		
		int size_bytes = 4+ncf.getLength()+numberBytes;/*Cabecera+dwdmWavelengthLabel+bytesBitMap*/
		
		if ((size_bytes%4)!=0){
			size_bytes=size_bytes+(4-(size_bytes%4));
		}
		
		this.setObjectLength(size_bytes);
		this.object_bytes = new byte[size_bytes];
		
		encode_header();
		
		System.arraycopy(ncf.getBytes(), 0, this.getBytes(), offset, ncf.getLength());
		offset = offset+4;
		System.arraycopy(this.bytesBitmap,0, this.getBytes(), offset,numberBytes);
		offset = offset+numberBytes;
		for (int i=offset;i<size_bytes;i++)
			this.object_bytes[i] = 0x00;
	}
	
	/**
	 * Decode SuggestedLabel message. 
	 */
	public void decode() throws MalformedPCEPObjectException{
		int offset=4;
		this.numLabels=((this.getBytes()[offset]&0x0F)<<8)|((this.getBytes()[offset+1]&0xFF));
		ncf=new NCF(this.getBytes(),offset);

		offset=offset+ncf.getLength();	
		offset=8;
		offset=offset+ncf.getLength();	
		
		int numberBytes = getNumberBytes(this.numLabels);
		bytesBitmap =  new byte[numberBytes];		
		System.arraycopy(this.getBytes(),offset, bytesBitmap , 0, numberBytes);
	}



	
	public int getNumLabels() {
		return numLabels;
	}

	public void setNumLabels(int numLabels) {
		this.numLabels = numLabels;
	}

	public byte[] getBytesBitmap() {
		return bytesBitmap;
	}

	public void setBytesBitmap(byte[] bytesBitmap) {
		this.bytesBitmap = bytesBitmap;
	}

	public NCF getNcf() {
		return ncf;
	}

	public void setNcf(NCF ncf) {
		this.ncf = ncf;
	}

	public int getNumberBytes(int num){
		int numberBytes = num/8;
		if ((numberBytes*8)<num){
			numberBytes++;
		}
		return numberBytes;
	}	
	
	public String toString() {
		String ret="";
		
		if (ncf!= null){
			ret=ret+"n base: "+ String.valueOf(ncf.getN())+" - ";
		}
		for (int i=0;i<bytesBitmap.length;i++){
			if((bytesBitmap[i]&0xFF)<=0x0F){
				ret=ret+"0"+Integer.toHexString(bytesBitmap[i]&0xFF);
			
			}else{
				ret=ret+Integer.toHexString(bytesBitmap[i]&0xFF);
			}
		}
	
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(bytesBitmap);
		result = prime * result + ((ncf == null) ? 0 : ncf.hashCode());
		result = prime * result + numLabels;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitmapLabelSet other = (BitmapLabelSet) obj;
		if (!Arrays.equals(bytesBitmap, other.bytesBitmap))
			return false;
		if (ncf == null) {
			if (other.ncf != null)
				return false;
		} else if (!ncf.equals(other.ncf))
			return false;
		if (numLabels != other.numLabels)
			return false;
		return true;
	}
	
	

	
}
