package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * BGP-LS Traffic Engineering (TE) Metric Extensions    February 29, 2016
 * https://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-00#section-3.2
 * Section 3.2
 *
 This sub-TLV advertises the minimum and maximum delay values between
   two directly connected IGP link-state neighbors.  The semantic of the
   TLV is described in [I-D.ietf-isis-te-metric-extensions] and
   [RFC7471].

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type                      |           Length                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |A| RESERVED    |                   Min Delay                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   RESERVED    |                   Max Delay                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   Type: TBA (suggested value: 1105).

   Length: 8.

   
 * @author victor uceda
 *
 */
public class MinMaxUndirectionalLinkDelayDescriptorSubTLV extends BGP4TLVFormat{
	int lowDelay;
	int highDelay;
	public MinMaxUndirectionalLinkDelayDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_MINMAXUNDIRLINKDELAY_ID);
	}
	
	
	public MinMaxUndirectionalLinkDelayDescriptorSubTLV(byte []bytes, int offset) {		
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		int len = 8;
		this.setTLVValueLength(len);		
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		//System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		//System.arraycopy(lowDelay,1, this.tlv_bytes, 1, 3);
		//System.arraycopy(0, 0,  this.tlv_bytes, 4, 1);
		//System.arraycopy(highDelay,1, this.tlv_bytes, 5, 3);
		int offset=4;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(lowDelay >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(lowDelay >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(lowDelay & 0xff);
		offset=8;
		this.tlv_bytes[offset]=0;
		this.tlv_bytes[offset + 1] = (byte)(highDelay >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(highDelay >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(highDelay & 0xff);
	}
	public void decode(){
		if (this.getTLVValueLength()!=12){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		//System.arraycopy(this.tlv_bytes,1, lowDelay, 1, 3);
		//System.arraycopy(this.tlv_bytes, 5, highDelay,  1, 3);
		int offset=4;
		this.lowDelay=0;
		this.lowDelay= (((int)(tlv_bytes[offset+1]<<16)& (int)0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		offset=8;
		this.highDelay=0;
		this.highDelay= (((int)(tlv_bytes[offset+1]<<16)& (int)0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		
	}

	@Override
	public String toString() {
		return "MinMaxUndirectionalLinkDelay [low_delay_ms=" + lowDelay + " , high_delay_ms="+ highDelay +"]";
	}
	
	public int getLowDelay() {
		return lowDelay;
	}


	public void setLowDelay(int lowDelay) {
		if(lowDelay < 0)lowDelay=0;
		if(lowDelay > 16777215)lowDelay=16777215;
		this.lowDelay = lowDelay;
	}


	public int getHighDelay() {
		return highDelay;
	}


	public void setHighDelay(int highDelay) {
		if(highDelay < 0)highDelay=0;
		if(highDelay > 16777215)highDelay=16777215;
		this.highDelay = highDelay;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + highDelay;
		result = prime * result + lowDelay;
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
		MinMaxUndirectionalLinkDelayDescriptorSubTLV other = (MinMaxUndirectionalLinkDelayDescriptorSubTLV) obj;
		if (highDelay != other.highDelay)
			return false;
		if (lowDelay != other.lowDelay)
			return false;
		return true;
	}
	

}
