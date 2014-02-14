package tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * Link Type OSPF Sub-TLV (<a href="http://www.ietf.org/rfc/rfc3630"> RFC 3630</a>).
 * 
 * The Link Type sub-TLV defines the type of the link:

      1 - Point-to-point
      2 - Multi-access

   The Link Type sub-TLV is TLV type 1, and is one octet in length.
 * 
 * @author ogondio
 *
 */
public class LinkType extends OSPFSubTLV {

	/**
	 * Type of the link
	 */
	private int linkType;

	/**
	 * Use this constructor to create a new LinkType OSPF Sub TLV from scratch.
	 * Later call encode!!!!
	 */
	public LinkType(){
		this.setTLVType(OSPFSubTLVTypes.LinkType);
	}

	/**
	 * Use this to decode a LinkType OSPF Sub TLV from a sequence of bytes
	 * @param bytes
	 * @param offset
	 * @throws MalformedOSPFSubTLVException
	 */
	public LinkType(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode a LinkType OSPF Sub TLV
	 */
	public void encode() {
		this.setTLVValueLength(1);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.tlv_bytes[4]=(byte)(linkType & 0xFF);
	}

	/**
	 * Decode a LinkType OSPF Sub TLV
	 * @throws MalformedOSPFSubTLVException
	 */
	protected void decode()throws MalformedOSPFSubTLVException{
		this.linkType=this.tlv_bytes[4] & 0xFF;
	}

	/**
	 * Gets the type of link
	 * @return Type of link
	 */
	public int getLinkType() {
		return linkType;
	}

	/**
	 * 
	 * @param linkType
	 */
	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
	/**
	 * Prints the details of the LinkType;
	 */
	public String toString(){
		String text="Link Type: "+this.getLinkType();
		return text;
	}



}
