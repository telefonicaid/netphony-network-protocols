package es.tid.bgp.bgp4.update.fields.pathAttributes;

import es.tid.bgp.bgp4.update.fields.PathAttribute;

/**
 * 
4.  Multiprotocol Unreachable NLRI - MP_UNREACH_NLRI (Type Code 15):

   This is an optional non-transitive attribute that can be used for the
   purpose of withdrawing multiple unfeasible routes from service.

   The attribute is encoded as shown below:

        +---------------------------------------------------------+
        | Address Family Identifier (2 octets)                    |
        +---------------------------------------------------------+
        | Subsequent Address Family Identifier (1 octet)          |
        +---------------------------------------------------------+
        | Withdrawn Routes (variable)                             |
        +---------------------------------------------------------+

   The use and the meaning of these fields are as follows:

      Address Family Identifier (AFI):

         This field in combination with the Subsequent Address Family
         Identifier field identifies the set of Network Layer protocols
         to which the address carried in the Next Hop field must belong,
         the way in which the address of the next hop is encoded, and
         the semantics of the Network Layer Reachability Information
         that follows.  If the Next Hop is allowed to be from more than
         one Network Layer protocol, the encoding of the Next Hop MUST
         provide a way to determine its Network Layer protocol.




Bates, et al.               Standards Track                     [Page 5]
 
RFC 4760           Multiprotocol Extensions for BGP-4       January 2007


         Presently defined values for the Address Family Identifier
         field are specified in the IANA's Address Family Numbers
         registry [IANA-AF].

      Subsequent Address Family Identifier (SAFI):

         This field in combination with the Address Family Identifier
         field identifies the set of Network Layer protocols to which
         the address carried in the Next Hop must belong, the way in
         which the address of the next hop is encoded, and the semantics
         of the Network Layer Reachability Information that follows.  If
         the Next Hop is allowed to be from more than one Network Layer
         protocol, the encoding of the Next Hop MUST provide a way to
         determine its Network Layer protocol.

      Withdrawn Routes Network Layer Reachability Information:

         A variable-length field that lists NLRI for the routes that are
         being withdrawn from service.  The semantics of NLRI is
         identified by a combination of <AFI, SAFI> carried in the
         attribute.

         When the Subsequent Address Family Identifier field is set to
         one of the values defined in this document, each NLRI is
         encoded as specified in the "NLRI encoding" section of this
         document.

   An UPDATE message that contains the MP_UNREACH_NLRI is not required
   to carry any other path attributes.


 * @author mcs
 *
 */
public class MP_Unreach_Attribute extends PathAttribute{

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}

}
