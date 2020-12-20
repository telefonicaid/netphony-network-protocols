package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * Link Type OSPF Sub-TLV (Type 1) RFC 3630
 *
 * The Link Type sub-TLV defines the type of the link:
 *
 *     1 - Point-to-point
 *     2 - Multi-access
 * IANA Assignment in https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6
 *
 * @see <a href="https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6">IANA assignments of OSPF Traffic Engneering TLVs</a>
 * @see <a href="http://www.ietf.org/rfc/rfc3630"> RFC 3630</a>
 * 
 * @author ogondio
 *
 */
public class LinkType extends OSPFSubTLV {

	/*
	 *    The Link Type sub-TLV is TLV type 1, and is one octet in length.
     * 
	 */
	
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
	 * @param bytes bytes
	 * @param offset offset 
	 * @throws MalformedOSPFSubTLVException Malformed OSPF SubTLV Exception
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
		encodeHeader();
		this.tlv_bytes[4]=(byte)(linkType & 0xFF);
	}

	/**
	 * Decode a LinkType OSPF Sub TLV
	 * @throws MalformedOSPFSubTLVException Malformed OSPF SubTLV Exception
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


	public void setLinkType(int linkType) {
		this.linkType = linkType;
	}
	
	/**
	 * Prints the details of the LinkType;
	 * @return details of the LinkType
	 */
	public String toString(){
		String text="Link Type: "+this.getLinkType();
		return text;
	}



}
