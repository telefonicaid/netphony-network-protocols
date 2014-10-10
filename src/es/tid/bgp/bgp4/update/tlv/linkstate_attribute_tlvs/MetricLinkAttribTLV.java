package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class MetricLinkAttribTLV extends BGP4TLVFormat{
	
	int length;
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
		length = this.getTLVValueLength();
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
			log.info("No metric tlv defined for this tlv length");
			break;
		}
		decode();
	}
	
	@Override
	public void encode() {//de momento solo queremos decodificar, el encode ya lo haremos
		// TODO Auto-generated method stub
		
	}
	
	public void decode(){
		int offset = 4;
		switch(metric_type){
		case METRIC_TYPE_OSPF:
			setMetric((this.tlv_bytes[offset]&0xFF));
			break;
		case METRIC_TYPE_IS_IS_SHORT:
			setMetric(((this.tlv_bytes[offset]&0xFF)<<8) | ((this.tlv_bytes[offset+1]&0xFF)));	
			break;
		case METRIC_TYPE_IS_IS_LONG:
			setMetric(((this.tlv_bytes[offset]&0xFF)<<16) | ((this.tlv_bytes[offset+1]&0xFF)<<8) | ((this.tlv_bytes[offset+2]&0xFF)));	
			break;
		default:
			log.info("This metric type does not exist");
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
