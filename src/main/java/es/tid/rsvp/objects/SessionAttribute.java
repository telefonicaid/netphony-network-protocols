package es.tid.rsvp.objects;

import es.tid.rsvp.RSVPProtocolViolationException;

/**

<p>RFC 3209 RSVP-TE		Session Attribute Object</p>

<p>The Session Attribute Class is 207.  Two C_Types are defined,
   LSP_TUNNEL, C-Type = 7 and LSP_TUNNEL_RA, C-Type = 1.  The
   LSP_TUNNEL_RA C-Type includes all the same fields as the LSP_TUNNEL
   C-Type.  Additionally it carries resource affinity information.  The
   formats are as follows:

4.7.1. Format without resource affinities

   SESSION_ATTRIBUTE class = 207, LSP_TUNNEL C-Type = 7

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //          Session Name      (NULL padded display string)      //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Setup Priority

         The priority of the session with respect to taking resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         The Setup Priority is used in deciding whether this session can
         preempt another session.

      Holding Priority

         The priority of the session with respect to holding resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         Holding Priority is used in deciding whether this session can
         be preempted by another session.

      Flags

         0x01  Local protection desired

               This flag permits transit routers to use a local repair
               mechanism which may result in violation of the explicit
               route object.  When a fault is detected on an adjacent
               downstream link or node, a transit router can reroute
               traffic for fast service restoration.

         0x02  Label recording desired

               This flag indicates that label information should be
               included when doing a route record.

         0x04  SE Style desired

               This flag indicates that the tunnel ingress node may
               choose to reroute this tunnel without tearing it down.
               A tunnel egress node SHOULD use the SE Style when
               responding with a Resv message.

      Name Length

         The length of the display string before padding, in bytes.

      Session Name

         A null padded string of characters.
</p>


 */

public abstract class SessionAttribute extends RSVPObject{

	public abstract void encode() throws RSVPProtocolViolationException;
			
	public abstract void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
	

}
