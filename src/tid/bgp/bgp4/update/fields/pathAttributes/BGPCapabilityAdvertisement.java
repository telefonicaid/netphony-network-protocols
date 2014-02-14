package tid.bgp.bgp4.update.fields.pathAttributes;

/**
 * RFC 4760           Multiprotocol Extensions for BGP-4       January 2007
 * 
 *  A BGP speaker that uses Multiprotocol Extensions SHOULD use the
   Capability Advertisement procedures [BGP-CAP] to determine whether
   the speaker could use Multiprotocol Extensions with a particular
   peer.

   The fields in the Capabilities Optional Parameter are set as follows.
   The Capability Code field is set to 1 (which indicates Multiprotocol
   Extensions capabilities).  The Capability Length field is set to 4.
   The Capability Value field is defined as:
 * <pre>
                     0       7      15      23      31
                     +-------+-------+-------+-------+
                     |      AFI      | Res.  | SAFI  |
                     +-------+-------+-------+-------+
 * </pre>
   The use and meaning of this field is as follow:

      AFI  - Address Family Identifier (16 bit), encoded the same way as
          in the Multiprotocol Extensions

      Res. - Reserved (8 bit) field.  SHOULD be set to 0 by the sender
          and ignored by the receiver.

          Note that not setting the field value to 0 may create issues
          for a receiver not ignoring the field.  In addition, this
          definition is problematic if it is ever attempted to redefine
          the field.

      SAFI - Subsequent Address Family Identifier (8 bit), encoded the
          same way as in the Multiprotocol Extensions.

   A speaker that supports multiple <AFI, SAFI> tuples includes them as
   multiple Capabilities in the Capabilities Optional Parameter.

   To have a bi-directional exchange of routing information for a
   particular <AFI, SAFI> between a pair of BGP speakers, each such
   speaker MUST advertise to the other (via the Capability Advertisement
   mechanism) the capability to support that particular <AFI, SAFI>
   route.

 * @author mcs
 *
 */
public class BGPCapabilityAdvertisement {
	private int AFI;
	private int SAFI;
	

}
