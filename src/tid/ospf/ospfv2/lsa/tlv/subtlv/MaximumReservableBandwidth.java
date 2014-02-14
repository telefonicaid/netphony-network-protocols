package tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * 2.5.7. Maximum Reservable Bandwidth


   The Maximum Reservable Bandwidth sub-TLV specifies the maximum
   bandwidth that may be reserved on this link, in this direction, in
   IEEE floating point format.  Note that this may be greater than the
   maximum bandwidth (in which case the link may be oversubscribed).
   This SHOULD be user-configurable; the default value should be the
   Maximum Bandwidth.  The units are bytes per second.

   The Maximum Reservable B
 * @author ogondio
 *
 */
public class MaximumReservableBandwidth extends OSPFSubTLV {

	public float maximumReservableBandwidth;
	


	public MaximumReservableBandwidth(){
		this.setTLVType(OSPFSubTLVTypes.MaximumBandwidth);
	}
	
	public MaximumReservableBandwidth(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		int bwi=Float.floatToIntBits(maximumReservableBandwidth);
		this.tlv_bytes[4]=(byte)(bwi >>> 24);
		this.tlv_bytes[5]=(byte)(bwi >> 16 & 0xff);
		this.tlv_bytes[6]=(byte)(bwi >> 8 & 0xff);
		this.tlv_bytes[7]=(byte)(bwi & 0xff);	
	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=4){
			throw new MalformedOSPFSubTLVException();
		}
		int bwi = 0;		
		for (int k = 0; k < 4; k++) {
			bwi = (bwi << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
		this.maximumReservableBandwidth=Float.intBitsToFloat(bwi);
	}
	public void setMaximumReservableBandwidth(float maximumReservableBandwidth) {
		this.maximumReservableBandwidth = maximumReservableBandwidth;
	}
	
	public float getMaximumReservableBandwidth() {
		return maximumReservableBandwidth;
	}

	public String toString(){
		return "maximumReservableBandwidth: "+Float.toString(maximumReservableBandwidth);
	}

}
