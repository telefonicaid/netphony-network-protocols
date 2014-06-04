package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class PrefixMetricPrefixAttribTLV extends BGP4TLVFormat {
	
	private long prefix_metric;
	

	public PrefixMetricPrefixAttribTLV() {
		this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_PREFIX_METRIC);
		// TODO Auto-generated constructor stub
	}

	public PrefixMetricPrefixAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;		
		this.tlv_bytes[offset] = (byte)(prefix_metric >> 16 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(prefix_metric >> 8 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(prefix_metric >> 0 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(prefix_metric & 0xff);

	}
	
	public void decode(){
		for (int k = 0; k < 4; k++) {
			prefix_metric = (prefix_metric << 8) | ((long)tlv_bytes[k+4] & (long)0xff);
		}
		
	}

	public long getPrefix_metric() {
		return prefix_metric;
	}

	public void setPrefix_metric(long prefix_metric) {
		this.prefix_metric = prefix_metric;
	}
	
	public String toString() {
		return "Prefix Metric [prefixMetric=" + prefix_metric + "]";
	}
}
