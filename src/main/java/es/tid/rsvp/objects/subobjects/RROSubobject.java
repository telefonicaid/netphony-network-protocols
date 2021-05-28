package es.tid.rsvp.objects.subobjects;

import java.util.Arrays;

/**
 * RRO Subobjects as defined in RFC 3209
 * 4.4.1. Subobjects

   The contents of a RECORD_ROUTE object are a series of variable-length
   data items called subobjects.  Each subobject has its own Length
   field.  The length contains the total length of the subobject in
   bytes, including the Type and Length fields.  The length MUST always
   be a multiple of 4, and at least 4.




Awduche, et al.             Standards Track                    [Page 31]

RFC 3209           Extensions to RSVP for LSP Tunnels      December 2001


   Subobjects are organized as a last-in-first-out stack.  The first
   subobject relative to the beginning of RRO is considered the top.
   The last subobject is considered the bottom.  When a new subobject is
   added, it is always added to the top.

   An empty RRO with no subobjects is considered illegal.

   Three kinds of subobjects are currently defined.

4.4.1.1. Subobject 1: IPv4 address

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |      Type     |     Length    | IPv4 address (4 bytes)        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv4 address (continued)      | Prefix Length |      Flags    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Type

         0x01  IPv4 address

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.  The Length is always 8.

      IPv4 address

         A 32-bit unicast, host address.  Any network-reachable
         interface address is allowed here.  Illegal addresses, such as
         certain loopback addresses, SHOULD NOT be used.

      Prefix length

         32

      Flags

         0x01  Local protection available

               Indicates that the link downstream of this node is
               protected via a local repair mechanism.  This flag can
               only be set if the Local protection flag was set in the
               SESSION_ATTRIBUTE object of the corresponding Path
               message.




Awduche, et al.             Standards Track                    [Page 32]

RFC 3209           Extensions to RSVP for LSP Tunnels      December 2001


         0x02  Local protection in use

               Indicates that a local repair mechanism is in use to
               maintain this tunnel (usually in the face of an outage
               of the link it was previously routed over).

4.4.1.2. Subobject 2: IPv6 address

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |      Type     |     Length    | IPv6 address (16 bytes)       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)                                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | IPv6 address (continued)      | Prefix Length |      Flags    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Type

         0x02  IPv6 address

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.  The Length is always 20.

      IPv6 address

         A 128-bit unicast host address.

      Prefix length

         128

      Flags

         0x01  Local protection available

               Indicates that the link downstream of this node is
               protected via a local repair mechanism.  This flag can
               only be set if the Local protection flag was set in the
               SESSION_ATTRIBUTE object of the corresponding Path
               message.



Awduche, et al.             Standards Track                    [Page 33]

RFC 3209           Extensions to RSVP for LSP Tunnels      December 2001


         0x02  Local protection in use

               Indicates that a local repair mechanism is in use to
               maintain this tunnel (usually in the face of an outage
               of the link it was previously routed over).

4.4.1.3. Subobject 3, Label

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |     Type      |     Length    |    Flags      |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       Contents of Label Object                                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Type

         0x03  Label

      Length

         The Length contains the total length of the subobject in bytes,
         including the Type and Length fields.

      Flags

         0x01 = Global label
           This flag indicates that the label will be understood
           if received on any interface.

      C-Type

         The C-Type of the included Label Object.  Copied from the Label
         Object.

      Contents of Label Object

         The contents of the Label Object.  Copied from the Label Object

 * @author ogondio
 *
 */

public abstract class RROSubobject {
	
	protected int type;
	protected int rrosolength;//RRO Subobject Length	
	protected byte [] subobject_bytes;

	
	public abstract void encode();
	
	public abstract void decode();
	
	public RROSubobject(){
		
	}
	
	public RROSubobject(byte[] bytes, int offset) {
		rrosolength=(int)bytes[offset+1];
		this.subobject_bytes=new byte[rrosolength];
		System.arraycopy(bytes, offset, subobject_bytes, 0, rrosolength);
		decodeSoHeader();
	}
	
	public void encodeSoHeader(){
		subobject_bytes[0]=(byte)type;
		subobject_bytes[1]=(byte)rrosolength;		
	}
	
	public void decodeSoHeader() {		
		type=subobject_bytes[0];
		rrosolength=(int)subobject_bytes[1];
	}	
	
	
	public static int getLength(byte []bytes, int offset) {
		int len=(int)bytes[offset+1];
		return len;
	}
	
	public static int getType(byte []bytes, int offset) {
		int typ=bytes[offset]& 0x7F;
		return typ;
	}
	
	public int getRrosolength() {
		return rrosolength;
	}
	
	public void setRrosolength(int rrosolength) {
		this.rrosolength = rrosolength;
	}
	
	public byte[] getSubobject_bytes() {
		return subobject_bytes;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rrosolength;
		result = prime * result + Arrays.hashCode(subobject_bytes);
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RROSubobject other = (RROSubobject) obj;
		if (rrosolength != other.rrosolength)
			return false;
		if (!Arrays.equals(subobject_bytes, other.subobject_bytes))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	
	

}
