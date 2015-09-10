package es.tid.rsvp.objects.subobjects.subtlvs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SubTLV {

	/** 0                   1                   2                   3
	       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
	      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	      |              Type             |             Length            |
	      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
	      |                            Value...                           |
	      .                                                               .
	      .                                                               .
	      .                                                               .
	      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	 * @author ogondio
	 *
	 */


	/**
	 * Type of the  TLV
	 */
	protected int TLVType;

	/**
	 * Length of the VALUE ONLY!!!
	 */
	protected int TLVValueLength;

	/**
	 * Total length of the TLV (including type, length and padding)
	 */
	protected int TotalTLVLength;

	/**
	 * Bytes of the TLV
	 */
	protected byte[] tlv_bytes;

	/**
	 * Logger
	 */
	protected static final Logger log = LoggerFactory.getLogger("PCEPParser");

	public SubTLV(){
	}


	public SubTLV(byte []bytes, int offset) {			
		this.TLVType=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		this.TLVValueLength=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		this.TotalTLVLength=TLVValueLength+4;			
		if ((this.TotalTLVLength%4)!=0){
			//Padding must be done!!
			this.TotalTLVLength=this.TotalTLVLength+4-(this.TotalTLVLength%4);
		}	
		this.tlv_bytes=new byte[TotalTLVLength];
		System.arraycopy(bytes, offset, tlv_bytes, 0, TotalTLVLength);
	}

	protected void encodeHeader(){
		this.tlv_bytes[0]=(byte)((TLVType>>>8) & 0xFF);
		this.tlv_bytes[1]=(byte)(TLVType & 0xFF);
		this.tlv_bytes[2]=(byte)(TLVValueLength>>>8 & 0xFF);
		this.tlv_bytes[3]=(byte)(TLVValueLength & 0xFF);
	}

	public int getTLVValueLength(){
		return TLVValueLength;
	}
	public int getTotalTLVLength(){
		return TotalTLVLength;
	}

	public static int getTotalTLVLength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		len+=4;
		if ((len%4)!=0){
			//Padding must be done!!
			len=len+4-(len%4);
		}		
		return len;
	}

	public static int getTLVLength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		return len;
	}


	public static int getType(byte []bytes, int offset) {
		int typ=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		return typ;
	}



	public int getTLVType() {
		return TLVType;
	}


	protected void setTLVType(int tLVType) {
		TLVType = tLVType;
	}


	public byte[] getTlv_bytes() {
		return tlv_bytes;
	}


	protected void setTlv_bytes(byte[] tlv_bytes) {
		this.tlv_bytes = tlv_bytes;
	}


	/**
	 * Sets the lenght of the VALUE of the TLV. The total length is computed!!!
	 * @param TLVValueLength
	 */
	protected void setTLVValueLength(int TLVValueLength) {
		this.TLVValueLength = TLVValueLength;
		this.TotalTLVLength=TLVValueLength+4;
		if ((this.TotalTLVLength%4)!=0){
			//Padding must be done!!
			this.TotalTLVLength=this.TotalTLVLength+(this.TotalTLVLength%4);
		}	

	}



	public abstract void encode();
	protected abstract void decode();




}
