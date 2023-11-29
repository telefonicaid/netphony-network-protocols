package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.protocol.commons.ByteHandler;


/**
 * Prefix Metric (Type 1155)
 * 	[RFC5305]
 * @author ogondio
 *
 */
public class PrefixMetricPrefixAttribTLV extends BGP4TLVFormat {
	
	private long prefix_metric;
	

	public PrefixMetricPrefixAttribTLV() {
		this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_PREFIX_METRIC);
	}

	public PrefixMetricPrefixAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;		
		ByteHandler.encode4bytesLong(prefix_metric, this.getTlv_bytes(), offset);
	}
	
	public void decode() {
		int offset=4;
		prefix_metric = ByteHandler.decode4bytesLong(this.getTlv_bytes(), offset);
		
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
