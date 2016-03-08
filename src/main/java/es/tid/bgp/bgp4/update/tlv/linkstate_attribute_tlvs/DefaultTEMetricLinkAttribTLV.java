package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


/**
 * 3.7 Traffic Engineering Metric


      This sub-TLV contains a 24-bit unsigned integer.  This metric is
   administratively assigned and can be used to present a differently
   weighted topology to traffic engineering SPF calculations.

   To preclude overflow within a traffic engineering SPF implementation,
   all metrics greater than or equal to MAX_PATH_METRIC SHALL be
   considered to have a metric of MAX_PATH_METRIC.  It is easiest to
   select MAX_PATH_METRIC such that MAX_PATH_METRIC plus a single link
   metric does not overflow the number of bits for internal metric
   calculation.  We assume that this is 32 bits.  Therefore, we have
   chosen MAX_PATH_METRIC to be 4,261,412,864 (0xFE000000, 2^32 - 2^25).

   This sub-TLV is optional.  This sub-TLV SHOULD appear once at most in
   each extended IS reachability TLV.  If a link is advertised without
   this sub-TLV, traffic engineering SPF calculations MUST use the
   normal default metric of this link, which is advertised in the fixed
   part of the extended IS reachability TLV.
   
 * @author Oscar Gonzalez de Dios
 * @author Fernando Munoz del Nuevo
 *
 */
public class DefaultTEMetricLinkAttribTLV extends BGP4TLVFormat {

	private long linkMetric;
	
	public DefaultTEMetricLinkAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_TE_DEFAULT_METRIC);
	}
	
	
	public DefaultTEMetricLinkAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	@Override
	public void encode() {///preguntar a oscar como lo ha hecho
		this.setTLVValueLength(3);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;		
		this.tlv_bytes[offset] = (byte)(linkMetric >> 16 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(linkMetric >> 8 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(linkMetric >> 0 & 0xff);
		//this.tlv_bytes[offset + 3] = (byte)(linkMetric & 0xff);

	}
	
	protected void decode(){		//idem
		linkMetric=0;
			/**for (int k = 0; k < 4; k++) {
				linkMetric = (linkMetric << 8) | ((long)tlv_bytes[k+4] & (long)0xff);
			}*/
			
			for (int k = 0; k < 3; k++) {
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
