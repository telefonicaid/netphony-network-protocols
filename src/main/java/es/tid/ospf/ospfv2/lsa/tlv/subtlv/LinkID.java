package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Link ID OSPF Sub-TLV (<a href="http://www.ietf.org/rfc/rfc3630"> RFC 3630</a>).
 * 
 * 2.5.2. Link ID


   The Link ID sub-TLV identifies the other end of the link.  For
   point-to-point links, this is the Router ID of the neighbor.  For
   multi-access links, this is the interface address of the designated
   router.  The Link ID is identical to the contents of the Link ID
   field in the Router LSA for these link types.

   The Link ID sub-TLV is TLV type 2, and is four octets in length.
 * 
 * @author ogondio
 *
 */
public class LinkID extends OSPFSubTLV {

	/**
	 * Link ID. The Link ID sub-TLV identifies the other end of the link.
	 */
	
	private Inet4Address linkID;
	/**
	 * Default constructor
	 */
	public LinkID(){
		this.setTLVType(OSPFSubTLVTypes.LinkID);
	}

	public LinkID(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}

	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;

		System.arraycopy(this.linkID.getAddress(),0, this.tlv_bytes, offset, 4);

//		
//		this.setTLVValueLength(4);
//		this.tlv_bytes=new byte[this.getTotalTLVLength()];
//		encodeHeader();
//		this.tlv_bytes[4] = (byte)((linkID >> 24) & 0xff);
//		this.tlv_bytes[5] = (byte)((linkID >> 16) & 0xff);
//		this.tlv_bytes[6] = (byte)((linkID >> 8) & 0xff);
//		this.tlv_bytes[7] = (byte)(linkID & 0xff);

	}

	/**
	 * Decode the linkID OSPFv2 sub-TLV
	 * @throws MalformedOSPFSubTLVException MalformedOSPFSubTLVException
	 */
	protected void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=4){
			throw new MalformedOSPFSubTLVException();
		}
			
		byte[] ip=new byte[4];
		System.arraycopy(this.tlv_bytes,4, ip, 0, 4);
		try {
			this.linkID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new MalformedOSPFSubTLVException();
		}	
	}

//	public long getLinkID() {
//		return linkID;
//	}
//
//	public void setLinkID(long linkID) {
//		this.linkID = linkID;
//	}

	public Inet4Address getLinkID() {
		return linkID;
	}

	public void setLinkID(Inet4Address linkID) {
		this.linkID = linkID;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString(){
		return "LinkId: "+this.linkID.toString();
	}

}
