package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * Unreserved Bandwidth


   The Unreserved Bandwidth sub-TLV specifies the amount of bandwidth
   not yet reserved at each of the eight priority levels in IEEE
   floating point format.  The values correspond to the bandwidth that
   can be reserved with a setup priority of 0 through 7, arranged in
   increasing order with priority 0 occurring at the start of the sub-
   TLV, and priority 7 at the end of the sub-TLV.  The initial values
   (before any bandwidth is reserved) are all set to the Maximum
   Reservable Bandwidth.  Each value will be less than or equal to the
   Maximum Reservable Bandwidth.  The units are bytes per second.

   
 * @author ogondio
 *
 */
public class UnreservedBandwidth extends OSPFSubTLV {

/*
 * The Unreserved Bandwidth sub-TLV is TLV type 8, and is 32 octets in
   length.
 */
	public float[] unreservedBandwidth;
	
	public UnreservedBandwidth(){
		this.setTLVType(OSPFSubTLVTypes.UnreservedBandwidth);
		unreservedBandwidth=new float[8];
	}
	
	public UnreservedBandwidth(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		unreservedBandwidth=new float[8];
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(32);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int bwi;
		int offset=4;
		for (int i=0;i<8;++i){
			bwi=Float.floatToIntBits(unreservedBandwidth[i]);
			this.tlv_bytes[offset]=(byte)(bwi >>> 24);
			this.tlv_bytes[offset+1]=(byte)(bwi >> 16 & 0xff);
			this.tlv_bytes[offset+2]=(byte)(bwi >> 8 & 0xff);
			this.tlv_bytes[offset+3]=(byte)(bwi & 0xff);
			offset=offset+4;
		}

	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=32){
			throw new MalformedOSPFSubTLVException();
		}
		int bwi = 0;
		int offset=4;
		for (int i=0;i<8;++i){
			bwi=0;
			for (int k = 0; k < 4; k++) {
				bwi = (bwi << 8) | (this.tlv_bytes[offset+k] & 0xff);
			}
			this.unreservedBandwidth[i]=Float.intBitsToFloat(bwi);
			offset=offset+4;
		}
				
		
	}
	
	public String toString(){
		String ret="";
		for (int i=0;i<7;++i){
			ret=ret+"unreservedBandwidth["+i+"]: "+Float.toString(unreservedBandwidth[i])+"\r\n";
		}
		ret=ret+"unreservedBandwidth[7]: "+Float.toString(unreservedBandwidth[7]);
		return ret;
	}
	
	public String toStringShort(){
		String ret="unrBw[0]: "+unreservedBandwidth[0];
		return ret;
	}

	public float[] getUnreservedBandwidth() {
		return unreservedBandwidth;
	}

	public void setUnreservedBandwidth(float[] unreservedBandwidth) {
		this.unreservedBandwidth = unreservedBandwidth;
	}



}
