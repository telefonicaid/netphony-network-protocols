package tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * 
 * 
 * @author pac
 *
 */

public class IS_IS_AreaIdentifierNodeAttribTLV extends BGP4TLVFormat{

	
	int length;
	private byte[] address = null;
	private LinkedList <Inet4Address> ipv4areaIDs = new LinkedList <Inet4Address>();
	
	public IS_IS_AreaIdentifierNodeAttribTLV() {
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IS_IS_AREA_ID);
		// TODO Auto-generated constructor stub
	}

	public IS_IS_AreaIdentifierNodeAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		length = this.getTLVValueLength();
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}
	
	public void decode(){
		int number_addresses = length/4;
		int offset = 4;
	    address=new byte[4]; 
	    Inet4Address idarea = null;
		for (int i=0; i<number_addresses; i++){
			System.arraycopy(this.tlv_bytes,offset, address, 0, 4);
			try {
				idarea= (Inet4Address) Inet4Address.getByAddress(address);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ipv4areaIDs.add(idarea);
			offset+=4;
		}
	}

	public byte[] getAddress() {
		return address;
	}

	public void setAddress(byte[] address) {
		this.address = address;
	}

	public LinkedList <Inet4Address> getIpv4areaIDs() {
		return ipv4areaIDs;
	}

	public void setIpv4areaIDs(LinkedList <Inet4Address> ipv4areaIDs) {
		this.ipv4areaIDs = ipv4areaIDs;
	}
	
	public String toString(){
			String ret="";
			for (int i=0;i<ipv4areaIDs.size();++i) {
				ret="ISIS AREA ["+i+"] IDENTIFIER: "+ipv4areaIDs.get(i).toString();
			} 
			return ret;
	}

}
