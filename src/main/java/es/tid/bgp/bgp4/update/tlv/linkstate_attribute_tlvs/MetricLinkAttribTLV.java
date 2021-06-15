package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.protocol.commons.ByteHandler;
/**
 * IGP Metric TLV (Type 1095)
 * @see https://www.iana.org/go/rfc7752	
 * @author ogondio
 *
 */
public class MetricLinkAttribTLV extends BGP4TLVFormat{
	
	private int metric_type;
	private static final int METRIC_TYPE_OSPF = 1;
	private static final int METRIC_TYPE_IS_IS_SHORT = 2;
	private static final int METRIC_TYPE_IS_IS_LONG = 3;
	private int metric;

	
	
	public MetricLinkAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_METRIC);
	}
	
	public MetricLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
	
		decode();
	}
	
	@Override
	public void encode() {//de momento solo queremos decodificar, el encode ya lo haremos
		int offset =4;
		if (metric_type==METRIC_TYPE_IS_IS_SHORT) {
			this.setTLVValueLength(1);
			this.tlv_bytes=new byte[this.getTotalTLVLength()];
			encodeHeader();
			ByteHandler.encode1byteInteger(metric, this.getTlv_bytes(), offset);
		}else if (metric_type==METRIC_TYPE_OSPF) {
			this.setTLVValueLength(2);
			this.tlv_bytes=new byte[this.getTotalTLVLength()];
			encodeHeader();
			ByteHandler.encode2bytesInteger(metric, this.getTlv_bytes(), offset);
		}else if (metric_type==METRIC_TYPE_IS_IS_LONG) {
			this.setTLVValueLength(3);
			this.tlv_bytes=new byte[this.getTotalTLVLength()];
			encodeHeader();
			ByteHandler.encode3bytesInteger(metric, this.getTlv_bytes(), offset);
		} else {
			//This should not happen, only for the automatic testing not to crash.
			metric_type=METRIC_TYPE_IS_IS_LONG;
			this.setTLVValueLength(3);
			this.tlv_bytes=new byte[this.getTotalTLVLength()];
			encodeHeader();
			//Cut to the 3 bytes.
			metric = metric&0xFFFFFF;			
		}
		
	}
	
	public void decode(){
		int length = this.getTLVValueLength();
		switch(length){
		case 1:
			this.setMetric_type(METRIC_TYPE_IS_IS_SHORT);
			break;
		case 2:
			this.setMetric_type(METRIC_TYPE_OSPF);
			break;
		case 3:
			this.setMetric_type(METRIC_TYPE_IS_IS_LONG);
			break;
		default:
			log.debug("No metric tlv defined for this tlv length");
			break;
		}
		int offset = 4;
		switch(metric_type){
		case METRIC_TYPE_OSPF:
			setMetric(ByteHandler.decode2bytesInteger(this.getTlv_bytes(), offset));
			break;
		case METRIC_TYPE_IS_IS_SHORT:
			setMetric(ByteHandler.decode1byteInteger(this.getTlv_bytes(), offset));	
			break;
		case METRIC_TYPE_IS_IS_LONG:
			setMetric(ByteHandler.decode3bytesInteger(this.getTlv_bytes(), offset));	
			break;
		default:
			log.debug("This metric type does not exist");
			break;
		}
		
	}

	public int getMetric_type() {
		return metric_type;
	}

	public void setMetric_type(int metric_type) {
		this.metric_type = metric_type;
	}
	public int getMetric() {
			return metric;
	}
	
	public void setMetric(int metric) {
			this.metric = metric;
	}
	
	public String toString(){
		switch(metric_type){
		case METRIC_TYPE_OSPF:
			return "METRIC [type=" + this.getMetric_type() + ", OSPF METRIC="
			+ this.getMetric() + "]";
		case METRIC_TYPE_IS_IS_SHORT:
			return "METRIC [type=" + this.getMetric_type() + ", ISIS SHORT METRIC="
			+ this.getMetric() + "]";
		case METRIC_TYPE_IS_IS_LONG:
			return "METRIC [type=" + this.getMetric_type() + ", ISIS LONG METRIC="
			+ this.getMetric() + "]";
		default:
			return "METRIC [type= UNKWOWN METRIC TYPE" + "]";
		}
	}

	
	

}
