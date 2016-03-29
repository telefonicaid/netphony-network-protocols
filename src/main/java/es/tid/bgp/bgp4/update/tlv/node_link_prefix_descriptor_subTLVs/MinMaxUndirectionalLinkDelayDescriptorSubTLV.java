package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *  
 * [ISIS-TE-METRIC] Internet-Draft        IS-IS Extensions for Traffic Engineering (TE) Metric Extensions    April 2015
 * https://tools.ietf.org/html/draft-ietf-isis-te-metric-extensions-04#section-4.1
 * Section 4.2
 *
 This sub-TLV advertises the minimum and maximum delay values between
   two directly connected IS-IS neighbors.  The delay advertised by this
   sub-TLV MUST be the delay from the local neighbor to the remote one
   (i.e. the forward path latency).  The format of this sub-TLV is shown
   in the following diagram:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Type        |     Length    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |A| RESERVED    |                   Low Delay                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   RESERVED    |                   High Delay                  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   
 * @author victor uceda
 *
 */
public class MinMaxUndirectionalLinkDelayDescriptorSubTLV extends BGP4TLVFormat{
	int lowDelay;
	int highDelay;
	public MinMaxUndirectionalLinkDelayDescriptorSubTLV(){
		super();
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_UNDIRLINKDELAY_ID);
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
		System.arraycopy(0, 0,  this.tlv_bytes, 0, 1);
		System.arraycopy(lowDelay,1, this.tlv_bytes, 1, 3);
		System.arraycopy(0, 0,  this.tlv_bytes, 4, 1);
		System.arraycopy(highDelay,1, this.tlv_bytes, 5, 3);
	}
	public void decode(){
		if (this.getTLVValueLength()!=12){
			//throw new MalformedPCEPObjectException();
			//FIXME: esta mal formado Que hacer
		}
		System.arraycopy(this.tlv_bytes,1, lowDelay, 1, 3);
		System.arraycopy(this.tlv_bytes, 5, highDelay,  1, 3);
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
	

}
