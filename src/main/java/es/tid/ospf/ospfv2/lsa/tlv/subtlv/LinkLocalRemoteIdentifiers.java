package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * 1.1.  Link Local/Remote Identifiers


   Link Local/Remote Identifiers is a sub-TLV of the Link TLV.  The type
   of this sub-TLV is 11, and length is eight octets.  The value field
   of this sub-TLV contains four octets of Link Local Identifier
   followed by four octets of Link Remote Identifier (see Section
   "Support for unnumbered links" of [GMPLS-ROUTING]).  If the Link
   Remote Identifier is unknown, it is set to 0.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Link Local Identifier                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Link Remote Identifier                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   A node can communicate its Link Local Identifier to its neighbor
   using a link local Opaque LSA, as described in Section "Exchanging
   Link Local TE Information".

 * @author ogondio
 *
 */
public class LinkLocalRemoteIdentifiers extends OSPFSubTLV {
	
	private long linkLocalIdentifier;
	
	private long linkRemoteIdentifier;
	
	public LinkLocalRemoteIdentifiers(){
		this.setTLVType(OSPFSubTLVTypes.LinkLocalRemoteIdentifiers);
	}
	
	public LinkLocalRemoteIdentifiers(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		this.setTLVValueLength(8);
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset = 4;
	
		this.tlv_bytes[offset] = (byte)(linkLocalIdentifier >> 24 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(linkLocalIdentifier >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(linkLocalIdentifier >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(linkLocalIdentifier & 0xff);
		
		offset = offset + 4;
		
		this.tlv_bytes[offset] = (byte)(linkRemoteIdentifier >> 24 & 0xff);
		this.tlv_bytes[offset + 1] = (byte)(linkRemoteIdentifier >> 16 & 0xff);
		this.tlv_bytes[offset + 2] = (byte)(linkRemoteIdentifier >> 8 & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(linkRemoteIdentifier & 0xff);
		
	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=8){
			throw new MalformedOSPFSubTLVException();
		}
		int offset=4;
		this.linkLocalIdentifier=(((long)(this.tlv_bytes[offset]<<24)& (long)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );
		offset=8;
		this.linkRemoteIdentifier=(((long)(this.tlv_bytes[offset]<<24)& (long)0xFF000000) | ((tlv_bytes[offset+1]<<16)& 0xFF0000) |((tlv_bytes[offset+2]<<8)& 0xFF00) |  (tlv_bytes[offset+3] & 0xFF) );

	}


	public long getLinkLocalIdentifier() {
		return linkLocalIdentifier;
	}


	public void setLinkLocalIdentifier(long linkLocalIdentifier) {
		this.linkLocalIdentifier = linkLocalIdentifier;
	}


	public long getLinkRemoteIdentifier() {
		return linkRemoteIdentifier;
	}


	public void setLinkRemoteIdentifier(long linkRemoteIdentifier) {
		this.linkRemoteIdentifier = linkRemoteIdentifier;
	}
	
	
	@Override
	public boolean equals(Object obj) {
	if (((Long.valueOf(((LinkLocalRemoteIdentifiers)obj).getLinkLocalIdentifier())).equals(Long.valueOf(linkLocalIdentifier)))&&				
			((Long.valueOf(((LinkLocalRemoteIdentifiers)obj).getLinkRemoteIdentifier())).equals(Long.valueOf(linkRemoteIdentifier))))
			return true;
		return false;
	}
	
	@Override
	public String toString(){
		String ret="linkLocalIdentifier: "+linkLocalIdentifier+" linkRemoteIdentifier: "+linkRemoteIdentifier;
		return ret;
	}
	

}
