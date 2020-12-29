package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 *IS-IS Area Identifier TLV (Type 1027)  [RFC7752, Section 3.3.1.2]
 * 
 * @author pac
 * @author ogondio
 */

public class IS_IS_AreaIdentifierNodeAttribTLV extends BGP4TLVFormat{

	
	//int length;
	//private byte[] address = null;
	private LinkedList <Inet4Address> ipv4areaIDs;
	
	public IS_IS_AreaIdentifierNodeAttribTLV() {
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.NODE_ATTRIBUTE_TLV_TYPE_IS_IS_AREA_ID);
		ipv4areaIDs = new LinkedList <Inet4Address>();
		// TODO Auto-generated constructor stub
	}

	public IS_IS_AreaIdentifierNodeAttribTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		decode();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void encode() {
		this.setTLVValueLength(ipv4areaIDs.size()*4);
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();
		int i=0;
		int offset=4;
		for (i=0;i<ipv4areaIDs.size();++i) {
			System.arraycopy(ipv4areaIDs.get(i).getAddress(),0, this.tlv_bytes, offset, 4);
			offset+=4;
		}
		
	}
	
	public void decode(){
		ipv4areaIDs = new LinkedList <Inet4Address>();
		int number_addresses = this.getTLVValueLength()/4;
		int offset = 4;
		 byte[] address=new byte[4]; 
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
