package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class RouteTagPrefixAttribTLV extends BGP4TLVFormat {

	public LinkedList <Inet4Address> routeTags;

	public RouteTagPrefixAttribTLV() {
		this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_ROUTE_TAG);
		routeTags = new LinkedList <Inet4Address>();
	}

	public RouteTagPrefixAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		routeTags = new LinkedList <Inet4Address>();
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		int number = routeTags.size();
		this.setTLVValueLength(4*number);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;	
		for (int i=0;i<number;++i) {
			System.arraycopy(routeTags.get(i).getAddress(), 0, this.getTlv_bytes(), offset, 4);
			offset+=4;
		}

	}
	
	public void decode(){
		
		int length = this.getTLVValueLength();
		int number = length/4;
		int offset = 4;
	    byte [] address=new byte[4]; 
	    Inet4Address igp_tag = null;
		for (int i=0; i<number; i++){
			System.arraycopy(this.tlv_bytes,offset, address, 0, 4);
			try {
				igp_tag= (Inet4Address) Inet4Address.getByAddress(address);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			routeTags.add(igp_tag);
			offset+=4;
		}
		
	}
	
	public LinkedList<Inet4Address> getRouteTags() {
		return routeTags;
	}

	public void setRouteTags(LinkedList<Inet4Address> routeTags) {
		this.routeTags = routeTags;
	}

	public String toString(){
		String ret="";
		for (int i=0;i<routeTags.size();++i) {
			ret="ROUTE TAG ["+i+"] IDENTIFIER: "+routeTags.get(i).toString();
		} 
		return ret;
	}

}
