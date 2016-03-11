package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Local Interface IP Address Sub-TLV.


   The Local Interface IP Address sub-TLV specifies the IP address(es)
   of the interface corresponding to this link.  If there are multiple
   local addresses on the link, they are all listed in this sub-TLV.

   The Local Interface IP Address sub-TLV is TLV type 3, and is 4N
   octets in length, where N is the number of local addresses.
 * @author ogondio
 *
 */
public class LocalInterfaceIPAddress extends OSPFSubTLV {
	
	/**
	 * List of Local Interface IP Addresses of the interface corresponding to this link
	 */
	private LinkedList<Inet4Address> localInterfaceIPAddressList;
	
	public LocalInterfaceIPAddress(){
		this.setTLVType(OSPFSubTLVTypes.LocalInterfaceIPAddress);
		localInterfaceIPAddressList=new LinkedList<Inet4Address>();
	}
	
	public LocalInterfaceIPAddress(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		localInterfaceIPAddressList=new LinkedList<Inet4Address>();
		decode();
	}
	
	/**
	 * 
	 */
	public void encode() {
		this.setTLVValueLength(localInterfaceIPAddressList.size()*4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;
		for (int i=0;i<localInterfaceIPAddressList.size();++i) {
			System.arraycopy(this.localInterfaceIPAddressList.get(i).getAddress(),0, this.tlv_bytes, offset, 4);
			offset=offset+4;
		}
	}
	
	/**
	 * 
	 * @throws MalformedOSPFSubTLVException MalformedOSPFSubTLVException
	 */
	protected void decode()throws MalformedOSPFSubTLVException{
		int numIPAddresses=(this.getTLVValueLength()/4);
		byte[] ip=new byte[4];
		int offset=4;
		for (int i=0;i<numIPAddresses;++i){
			System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
			try {
				this.localInterfaceIPAddressList.add((Inet4Address)Inet4Address.getByAddress(ip));
				offset=offset+4;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				throw new MalformedOSPFSubTLVException();
			}	
		}
	}
	
	public void addLocalInterfaceIPAddress(Inet4Address address){
		localInterfaceIPAddressList.add(address);
	}
	
	public Inet4Address getLocalInterfaceIPAddress(int i){
		return localInterfaceIPAddressList.get(i);
	}

	public LinkedList<Inet4Address> getLocalInterfaceIPAddressList() {
		return localInterfaceIPAddressList;
	}

	public void setLocalInterfaceIPAddressList(
			LinkedList<Inet4Address> localInterfaceIPAddressList) {
		this.localInterfaceIPAddressList = localInterfaceIPAddressList;
	}
	
	public String toString(){
		String ret="";
		for (int i=0;i<localInterfaceIPAddressList.size();++i) {
			ret="localInterfaceIPAddress["+i+"]: "+localInterfaceIPAddressList.get(i).toString();
		} 
		return ret;
		
	}

}
