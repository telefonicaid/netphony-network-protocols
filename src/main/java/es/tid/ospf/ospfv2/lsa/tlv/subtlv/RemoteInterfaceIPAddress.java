package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Remote Interface IP Address.


   The Remote Interface IP Address sub-TLV specifies the IP address(es)
   of the neighbor's interface corresponding to this link.  This and the
   local address are used to discern multiple parallel links between
   systems.  If the Link Type of the link is Multi-access, the Remote
   Interface IP Address is set to 0.0.0.0; alternatively, an
   implementation MAY choose not to send this sub-TLV.

   The Remote Interface IP Address sub-TLV is TLV type 4, and is 4N
   octets in length, where N is the number of neighbor addresses.
 * @author ogondio
 *
 */

public class RemoteInterfaceIPAddress extends OSPFSubTLV {

	/**
	 * 
	 */
	private LinkedList<Inet4Address> remoteInterfaceIPAddressList;
	
	public RemoteInterfaceIPAddress(){
		this.setTLVType(OSPFSubTLVTypes.RemoteInterfaceIPAddress);
		remoteInterfaceIPAddressList=new LinkedList<Inet4Address>();
	}
	
	public RemoteInterfaceIPAddress(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		remoteInterfaceIPAddressList=new LinkedList<Inet4Address>();
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(remoteInterfaceIPAddressList.size()*4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;
		for (int i=0;i<remoteInterfaceIPAddressList.size();++i) {
			System.arraycopy(this.remoteInterfaceIPAddressList.get(i).getAddress(),0, this.tlv_bytes, offset, 4);
			offset=offset+4;
		}
	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		int numIPAddresses=(this.getTLVValueLength()/4);
		byte[] ip=new byte[4];
		int offset=4;
		for (int i=0;i<numIPAddresses;++i){
			System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
			try {
				this.remoteInterfaceIPAddressList.add((Inet4Address)Inet4Address.getByAddress(ip));
				offset=offset+4;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				throw new MalformedOSPFSubTLVException();
			}	
		}
	}
	
	public void addRemoteInterfaceIPAddress(Inet4Address address){
		remoteInterfaceIPAddressList.add(address);
	}
	
	public Inet4Address getRemoteInterfaceIPAddress(int i){
		return remoteInterfaceIPAddressList.get(i);
	}

	public LinkedList<Inet4Address> getRemoteInterfaceIPAddressList() {
		return remoteInterfaceIPAddressList;
	}

	public void setRemoteInterfaceIPAddressList(
			LinkedList<Inet4Address> localInterfaceIPAddressList) {
		this.remoteInterfaceIPAddressList = localInterfaceIPAddressList;
	}
	
	public String toString(){
		String ret="";
		for (int i=0;i<remoteInterfaceIPAddressList.size();++i) {
			ret="remoteInterfaceIPAddress["+i+"]: "+remoteInterfaceIPAddressList.get(i).toString();
		} 
		return ret;
		
	}
}
