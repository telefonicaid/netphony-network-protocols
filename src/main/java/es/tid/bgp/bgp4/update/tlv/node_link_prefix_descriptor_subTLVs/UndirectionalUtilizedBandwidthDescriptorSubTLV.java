package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * BGP-LS Traffic Engineering (TE) Metric Extensions    February 29, 2016
 * https://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-00#section-3.7
 * Section 3.7
 *

  This sub-TLV advertises the bandwidth utilization between two
   directly connected IGP link-state neighbors.  The semantic of the TLV
   is described in [I-D.ietf-isis-te-metric-extensions] and [RFC7471].

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type                      |           Length                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Utilized Bandwidth                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   where:

                                 Figure 5

   Type: TBA (suggested value: 1110).

   Length: 4.
   
 * @author victorUceda
 *
 */
public class UndirectionalUtilizedBandwidthDescriptorSubTLV extends BGP4TLVFormat{
	
	
	int utilizedBw;
	public UndirectionalUtilizedBandwidthDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLUTILIZEDBW_ID);
	}
	
	
	public int getUtilizedBw() {
		return utilizedBw;
	}


	public void setUtilizedBw(int availableBw) {
		this.utilizedBw = availableBw;
	}


	public UndirectionalUtilizedBandwidthDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int offset=4;
		this.tlv_bytes[offset] = (byte)(utilizedBw >> 24 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(utilizedBw >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(utilizedBw >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(utilizedBw & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=4){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,0, utilizedBw, 0, 4);
		int offset=4;
		this.utilizedBw=(((int)(this.tlv_bytes[offset]<<24)& (int)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalUtilizedBW [bw_bytes_per_second=" + utilizedBw + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + utilizedBw;
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
		UndirectionalUtilizedBandwidthDescriptorSubTLV other = (UndirectionalUtilizedBandwidthDescriptorSubTLV) obj;
		if (utilizedBw != other.utilizedBw)
			return false;
		return true;
	}

}
