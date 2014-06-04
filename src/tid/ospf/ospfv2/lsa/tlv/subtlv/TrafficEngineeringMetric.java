package tid.ospf.ospfv2.lsa.tlv.subtlv;


/**
 * 2.5.5. Traffic Engineering Metric


   The Traffic Engineering Metric sub-TLV specifies the link metric for
   traffic engineering purposes.  This metric may be different than the
   standard OSPF link metric.  Typically, this metric is assigned by a
   network administrator.

   The Traffic Engineering Metric sub-TLV is TLV type 5, and is four
   octets in length.
   
 * @author Oscar Gonz�lez de Dios
 * @author Fernando Mu�oz del Nuevo
 *
 */
public class TrafficEngineeringMetric extends OSPFSubTLV {

	private long linkMetric;
	
	public TrafficEngineeringMetric(){
		this.setTLVType(OSPFSubTLVTypes.TrafficEngineeringMetric);
	}
	
	
	public TrafficEngineeringMetric(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;		
		this.tlv_bytes[offset] = (byte)(linkMetric >> 24 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(linkMetric >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(linkMetric >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(linkMetric & 0xff);

	}
	
	protected void decode ()throws MalformedOSPFSubTLVException{		
		int offset=4;
		linkMetric=0;
			for (int k = 0; k < 4; k++) {
				linkMetric = (linkMetric << 8) | ((long)tlv_bytes[k+4] & (long)0xff);
			}		
	}

	public float getLinkMetric() {
		return linkMetric;
	}

	public void setLinkMetric(long linkMetric) {
		this.linkMetric = linkMetric;
	}

	public String toString() {
		return "TrafficEngineeringMetric [linkMetric=" + linkMetric + "]";
	}

	
}
