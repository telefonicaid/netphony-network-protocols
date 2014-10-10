package es.tid.rsvp.objects.subobjects;

/** 
 * Explicit Route Object Subobject. RFC 3209,  RFC 3473, RFC 3477
 * From RFC3209:
 * The contents of an EXPLICIT_ROUTE object are a series of variable-
 * length data items called subobjects.  Each subobject has the form:
 * 
 *  0                   1
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-------------//----------------+
 * |L|    Type     |     Length    | (Subobject contents)          |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-------------//----------------+
 *   L

         The L bit is an attribute of the subobject.  The L bit is set
         if the subobject represents a loose hop in the explicit route.
         If the bit is not set, the subobject represents a strict hop in
         the explicit route.

      Type

         The Type indicates the type of contents of the subobject.
         Currently defined values are:

                   1   IPv4 prefix
                   2   IPv6 prefix
                  32   Autonomous system number
      Length

         The Length contains the total length of the subobject in bytes,
         including the L, Type and Length fields.  The Length MUST be at
         least 4, and MUST be a multiple of 4.

4.3.3.1. Strict and Loose Subobjects

   The L bit in the subobject is a one-bit attribute.  If the L bit is
   set, then the value of the attribute is 'loose.'  Otherwise, the
   value of the attribute is 'strict.'  For brevity, we say that if the
   value of the subobject attribute is 'loose' then it is a 'loose
   subobject.'  Otherwise, it's a 'strict subobject.'  Further, we say
   that the abstract node of a strict or loose subobject is a strict or
   a loose node, respectively.  Loose and strict nodes are always
   interpreted relative to their prior abstract nodes.

   The path between a strict node and its preceding node MUST include
   only network nodes from the strict node and its preceding abstract
   node.

   The path between a loose node and its preceding node MAY include
   other network nodes that are not part of the strict node or its
   preceding abstract node.
   
   From RFC 3473:
   
   5.1. Label ERO subobject

   The Label ERO subobject is defined as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |U|   Reserved  |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                             Label                             |
   |                              ...                              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   See [RFC3471] for a description of L, U and Label parameters.

   Type

      3  Label

   Length

      The Length contains the total length of the subobject in bytes,
      including the Type and Length fields.  The Length is always
      divisible by 4.

   C-Type

      The C-Type of the included Label Object.  Copied from the Label
      Object.

5.1.1. Procedures

   The Label subobject follows a subobject containing the IP address, or
   the interface identifier [RFC3477], associated with the link on which
   it is to be used.  Up to two label subobjects may be present, one for
   the downstream label and one for the upstream label.  The following
   SHOULD result in "Bad EXPLICIT_ROUTE object" errors:

   o If the first label subobject is not preceded by a subobject
     containing an IP address, or an interface identifier [RFC3477],
     associated with an output link.


   o For a label subobject to follow a subobject that has the L-bit set

   o On unidirectional LSP setup, for there to be a label subobject with
     the U-bit set

   o For there to be two label subobjects with the same U-bit values

   To support the label subobject, a node must check to see if the
   subobject following its associate address/interface is a label
   subobject.  If it is, one subobject is examined for unidirectional
   LSPs and two subobjects for bidirectional LSPs.  If the U-bit of the
   subobject being examined is clear (0), then value of the label is
   copied into a new Label_Set object.  This Label_Set object MUST be
   included on the corresponding outgoing Path message.

   If the U-bit of the subobject being examined is set (1), then value
   of the label is label to be used for upstream traffic associated with
   the bidirectional LSP.  If this label is not acceptable, a "Bad
   EXPLICIT_ROUTE object" error SHOULD be generated.  If the label is
   acceptable, the label is copied into a new Upstream_Label object.
   This Upstream_Label object MUST be included on the corresponding
   outgoing Path message.

   After processing, the label subobjects are removed from the ERO.

   Note an implication of the above procedures is that the label
   subobject should never be the first subobject in a newly received
   message.  If the label subobject is the the first subobject an a
   received ERO, then it SHOULD be treated as a "Bad strict node" error.

   Procedures by which an LSR at the head-end of an LSP obtains the
   information needed to construct the Label subobject are outside the
   scope of this document.

   FROM RFC 3477:
    A new subobject of the Explicit Route Object (ERO) is used to specify
   unnumbered links.  This subobject has the following format:

   Figure 2: Unnumbered Interface ID Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |    Reserved (MUST be zero)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           Router ID                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Interface ID (32 bits)                    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Type is 4 (Unnumbered Interface ID).  The Length is 12.

   The Interface ID is the identifier assigned to the link by the LSR
   specified by the router ID.

 * @author ogondio
 *
 */
public abstract class EROSubobject {

	protected int type;
	protected int erosolength;//ERO Subobject Length
	protected boolean loosehop;
	protected byte [] subobject_bytes;
	
	public EROSubobject(){
	}
	
	public EROSubobject(byte[] bytes, int offset) {
		erosolength=(int)bytes[offset+1];
		this.subobject_bytes=new byte[erosolength];
		System.arraycopy(bytes, offset, subobject_bytes, 0, erosolength);
		decodeSoHeader();
	}
	
	public abstract void encode();
	
	public abstract void decode();
	
	public void encodeSoHeader(){
		if (loosehop){
			subobject_bytes[0]=(byte)(0x80 | (type & 0x7F));
		}
		else {
			subobject_bytes[0]=(byte)(type & 0x7F);
		}
			subobject_bytes[1]=(byte)erosolength;		
	}
	
	public void decodeSoHeader() {
		int lhopbit= (int)((subobject_bytes[0]>>7)&0x01);
		if (lhopbit==1){
			loosehop=true;
		}
		else {
			loosehop=false;
		}
		type=subobject_bytes[0]&0x7F;
		erosolength=(int)subobject_bytes[1];
	}	
		
	
	public static int getLength(byte []bytes, int offset) {
		int len=(int)bytes[offset+1];
		return len;
	}
	
	public static int getType(byte []bytes, int offset) {
		int typ=bytes[offset]& 0x7F;
		return typ;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getErosolength() {
		return erosolength;
	}

	public void setErosolength(int erosolength) {
		this.erosolength = erosolength;
	}

	public boolean isLoosehop() {
		return loosehop;
	}

	public void setLoosehop(boolean loosehop) {
		this.loosehop = loosehop;
	}

	public byte[] getSubobject_bytes() {
		return subobject_bytes;
	}

	public void setSubobject_bytes(byte[] subobject_bytes) {
		this.subobject_bytes = subobject_bytes;
	}
}