package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

public class RouteTagPrefixAttribTLV extends BGP4TLVFormat {

	private  int number = 0;
	int length;
	private byte[] address = null;
	private LinkedList <Inet4Address> routeTags = new LinkedList <Inet4Address>();

	public RouteTagPrefixAttribTLV() {
		this.setTLVType(LinkStateAttributeTLVTypes.PREFIX_ATTRIBUTE_TLV_TYPE_ROUTE_TAG);
		// TODO Auto-generated constructor stub
	}

	public RouteTagPrefixAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		this.setTLVValueLength(4*number);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		//int offset = 4;	
		// TODO Auto-generated method stub

	}
	
	public void decode(){
		length = this.getTLVValueLength();
		number = length/4;
		int offset = 4;
	    address=new byte[4]; 
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
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
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
