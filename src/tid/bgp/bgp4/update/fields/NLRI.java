package tid.bgp.bgp4.update.fields;

import tid.bgp.bgp4.objects.BGP4Object;
/**
 * Network Layer Reachability Information:

         This variable length field contains a list of IP address
         prefixes.  The length, in octets, of the Network Layer
         Reachability Information is not encoded explicitly, but can be
         calculated as:

               UPDATE message Length - 23 - Total Path Attributes Length
               - Withdrawn Routes Length

         where UPDATE message Length is the value encoded in the fixed-
         size BGP header, Total Path Attribute Length, and Withdrawn
         Routes Length are the values encoded in the variable part of
         the UPDATE message, and 23 is a combined length of the fixed-
         size BGP header, the Total Path Attribute Length field, and the
         Withdrawn Routes Length field.

         Reachability information is encoded as one or more 2-tuples of
         the form <length, prefix>, whose fields are described below:
 * <pre>
                  +---------------------------+
                  |   Length (1 octet)        |
                  +---------------------------+
                  |   Prefix (variable)       |
                  +---------------------------+
 * </pre>
         The use and the meaning of these fields are as follows:

         a) Length:

            The Length field indicates the length in bits of the IP
            address prefix.  A length of zero indicates a prefix that
            matches all IP addresses (with prefix, itself, of zero
            octets).

         b) Prefix:

            The Prefix field contains an IP address prefix, followed by
            enough trailing bits to make the end of the field fall on an
            octet boundary.  Note that the value of the trailing bits is
            irrelevant.




 * @author mcs
 *
 */
public abstract class NLRI  extends BGP4Object{
	//protected PrefixFieldNLRI prefix;
	//FIXME: pone que no tiene length que se envie. Entonces no podria extender de BGP4Object no?

	//protected byte[] NLRI_bytes;
	
	
}
