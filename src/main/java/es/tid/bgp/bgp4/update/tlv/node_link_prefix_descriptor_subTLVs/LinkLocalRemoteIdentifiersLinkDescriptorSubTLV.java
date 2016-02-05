package es.tid.bgp.bgp4.update.tlv.node_link_prefix_descriptor_subTLVs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.ospf.ospfv2.lsa.tlv.subtlv.LinkLocalRemoteIdentifiers;


/**
 * 
 * RFC 5307               IS-IS Extensions for GMPLS           October 2008
 * http://tools.ietf.org/html/rfc5307
 * 
 * A Link Local Interface Identifier is a sub-TLV of the extended IS
   reachability TLV.  The type of this sub-TLV is 4, and the length is 8
   octets.  The value field of this sub-TLV contains 4 octets of Link
   Local Identifier followed by 4 octets of Link Remote Identifier (see
   Section 2.1, "Support for Unnumbered Links", of [GMPLS-ROUTING]).  If
   the Link Remote Identifier is unknown, it is set to 0.

   The following illustrates encoding of the Value field of the Link
   Local/Remote Identifiers sub-TLV.








Kompella & Rekhter          Standards Track                     [Page 2]
 
RFC 5307               IS-IS Extensions for GMPLS           October 2008


       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                  Link Local Identifier                        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                  Link Remote Identifier                       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Link Local/Remote Identifiers sub-TLV MUST NOT occur more than
   once within the extended IS reachability TLV.  If the Link
   Local/Remote Identifiers sub-TLV occurs more than once within the
   extended IS reachability TLV, the receiver SHOULD ignore all these
   sub-TLVs.
------------------------------------------------------------------------
   RFC 4202
   2.1.  Support for Unnumbered Links

   An unnumbered link has to be a point-to-point link.  An LSR at each
   end of an unnumbered link assigns an identifier to that link.  This
   identifier is a non-zero 32-bit number that is unique within the
   scope of the LSR that assigns it.

   Consider an (unnumbered) link between LSRs A and B.  LSR A chooses an
   idenfitier for that link.  So does LSR B.  From A's perspective we
   refer to the identifier that A assigned to the link as the "link
   local identifier" (or just "local identifier"), and to the identifier
   that B assigned to the link as the "link remote identifier" (or just
   "remote identifier").  Likewise, from B's perspective the identifier
   that B assigned to the link is the local identifier, and the
   identifier that A assigned to the link is the remote identifier.

   Support for unnumbered links in routing includes carrying information
   about the identifiers of that link.  Specifically, when an LSR
   advertises an unnumbered TE link, the advertisement carries both the
   local and the remote identifiers of the link.  If the LSR doesn't
   know the remote identifier of that link, the LSR should use a value
   of 0 as the remote identifier.
   
 * @author mcs
 *
 */
public class LinkLocalRemoteIdentifiersLinkDescriptorSubTLV extends BGP4TLVFormat{
	private long linkLocalIdentifier;
	
	private long linkRemoteIdentifier;
	
	public LinkLocalRemoteIdentifiersLinkDescriptorSubTLV(){
		this.setTLVType(LinkDescriptorSubTLVTypes.LINK_DESCRIPTOR_SUB_TLV_TYPE_LINKIDENTIFIERS);
	}
	
	public LinkLocalRemoteIdentifiersLinkDescriptorSubTLV(byte[] bytes, int offset){
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
	
	protected void decode(){
		if (this.getTLVValueLength()!=8){
			log.error("PROBLEM in Link Local/Remote Identifier");
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
		String ret="LinkIdentifiers [Local Link Identifier=" + linkLocalIdentifier + "\n\t\tRemote Link Identifier=" +linkRemoteIdentifier;
		return ret;
	}
	
}
