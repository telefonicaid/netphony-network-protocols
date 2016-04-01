package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;



import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * BGP-LS Traffic Engineering (TE) Metric Extensions    February 29, 2016
 * https://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-00#section-3.3
 * Section 3.3
 *
This sub-TLV advertises the average link delay variation between two
   directly connected IGP link-state neighbors.  The semantic of the TLV
   is described in [I-D.ietf-isis-te-metric-extensions] and [RFC7471].

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type                      |           Length                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  RESERVED     |               Delay Variation                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   Type: TBA (suggested value: 1106).

   Length: 4.
   
 * @author victorUceda
 *
 */
public class UndirectionalDelayVariationDescriptorSubTLV extends BGP4TLVFormat{
	int delayVar;
	public UndirectionalDelayVariationDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRDELAYVAR_ID);
	}
	
	
	public int getDelayVar() {
		return delayVar;
	}


	public void setDelayVar(int delay) {
		if(delay < 0)delay=0;
		if(delay > 16777215)delay=16777215;
		this.delayVar = delay;
	}


	public UndirectionalDelayVariationDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}
	@Override
	public void encode() {
		int len = 4;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		//System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		//System.arraycopy(delayVar,1, this.tlv_bytes, 1, 3);
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(delayVar >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(delayVar >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(delayVar & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=8){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,1, delayVar, 1, 3);
		int offset=4;
		this.delayVar=0;
		this.delayVar= (((int)(tlv_bytes[offset+1]<<16)& (int)0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "UndirectionalDelayVariation [delayVar_ms=" + delayVar + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + delayVar;
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
		UndirectionalDelayVariationDescriptorSubTLV other = (UndirectionalDelayVariationDescriptorSubTLV) obj;
		if (delayVar != other.delayVar)
			return false;
		return true;
	}
	

}
