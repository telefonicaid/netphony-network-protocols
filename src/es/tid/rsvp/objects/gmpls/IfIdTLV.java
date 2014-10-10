/**
 * @author Fernando Muñoz del Nuevo
 * 
 * 9.1.1. Required Information


   The following information is carried in Interface_ID:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   ~                              TLVs                             ~
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+








Berger                      Standards Track                    [Page 25]

 
RFC 3471        GMPLS Signaling Functional Description


   Where each TLV has the following format:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |              Type             |             Length            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   ~                             Value                             ~
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Length: 16 bits

         Indicates the total length of the TLV, i.e., 4 + the length of
         the value field in octets.  A value field whose length is not a
         multiple of four MUST be zero-padded so that the TLV is four-
         octet aligned.

      Type: 16 bits

         Indicates type of interface being identified.  Defined values
         are:

   Type Length Format     Description
   --------------------------------------------------------------------
    1      8   IPv4 Addr. IPv4
    2     20   IPv6 Addr. IPv6
    3     12   See below  IF_INDEX                (Interface Index)
    4     12   See below  COMPONENT_IF_DOWNSTREAM (Component interface)
    5     12   See below  COMPONENT_IF_UPSTREAM   (Component interface)

   For types 3, 4 and 5 the Value field has the format:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                            IP Address                         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           Interface ID                        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      IP Address: 32 bits

         The IP address field may carry either an IP address of a link
         or an IP address associated with the router, where associated
         address is the value carried in a router address TLV of
         routing.



Berger                      Standards Track                    [Page 26]

 
RFC 3471        GMPLS Signaling Functional Description


      Interface ID: 32 bits

         For type 3 usage, the Interface ID carries an interface
         identifier.

         For types 4 and 5, the Interface ID indicates a bundled
         component link.  The special value 0xFFFFFFFF can be used to
         indicate the same label is to be valid across all component
         links.
 */


package es.tid.rsvp.objects.gmpls;

public abstract class IfIdTLV {

	private int type;
	private int length;
	private byte[] bytes;
	
	public abstract void encodeHeader();

	public abstract void encode();

	public abstract void decodeHeader();

	public abstract void decode();
	
	public abstract void decode(byte[] bytes, int offset);

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	
	
}
