package es.tid.bgp.bgp4.update.fields;

import es.tid.bgp.bgp4.objects.BGP4Object;

/**
 * Withdrawn Routes:

         This is a variable-length field that contains a list of IP
         address prefixes for the routes that are being withdrawn from
         service.  Each IP address prefix is encoded as a 2-tuple of the
         form <length, prefix>, whose fields are described below:
<pre>
                  +---------------------------+
                  |   Length (1 octet)        |
                  +---------------------------+
                  |   Prefix (variable)       |
                  +---------------------------+
</pre>
   The use and the meaning of these fields are as follows:

         a) Length:

            The Length field indicates the length in bits of the IP
            address prefix.  A length of zero indicates a prefix that
            matches all IP addresses (with prefix, itself, of zero
            octets).

         b) Prefix:

            The Prefix field contains an IP address prefix, followed by
            the minimum number of trailing bits needed to make the end
            of the field fall on an octet boundary.  Note that the value
            of trailing bits is irrelevant.
 * @author mcs
 *
 */
public class WithdrawnRoutes extends BGP4Object{
	private int length;
	private byte[] prefix;//FIXME: pensar
	
	public void encode(){
		
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getPrefix() {
		return prefix;
	}

	public void setPrefix(byte[] prefix) {
		this.prefix = prefix;
	}
	
}
