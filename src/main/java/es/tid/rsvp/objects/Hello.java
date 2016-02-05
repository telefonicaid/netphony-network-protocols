package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

/**

<p>RFC 3209 RSVP-TE		Hello Object</p>

<p>5.2. HELLO Object formats

   The HELLO Class is 22.  There are two C_Types defined.

5.2.1. HELLO REQUEST object

   Class = HELLO Class, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Src_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Dst_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

5.2.2. HELLO ACK object

   Class = HELLO Class, C_Type = 2

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Src_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Dst_Instance                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Src_Instance: 32 bits

      a 32 bit value that represents the sender's instance.  The
      advertiser maintains a per neighbor representation/value.  This
      value MUST change when the sender is reset, when the node reboots,
      or when communication is lost to the neighboring node and
      otherwise remains the same.  This field MUST NOT be set to zero
      (0).

      Dst_Instance: 32 bits

      The most recently received Src_Instance value received from the
      neighbor.  This field MUST be set to zero (0) when no value has
      ever been seen from the neighbor.

</p>


 */

public abstract class Hello extends RSVPObject{

	public abstract void encode() throws RSVPProtocolViolationException;
			
	public abstract void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException;

}
