package es.tid.bgp.bgp4.open;

/**
 * From Multiprotocol Extensions for BGP-4 
 * <a href="http://tools.ietf.org/html/rfc4760">RFC 4760</a>.
 *    The fields in the Capabilities Optional Parameter are set as follows.
   The Capability Code field is set to 1 (which indicates Multiprotocol
   Extensions capabilities).  The Capability Length field is set to 4.
   The Capability Value field is defined as:
 *  The Capability Value field is defined as:
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

   A speaker that supports multiple AFI, SAFI tuples includes them as
   multiple Capabilities in the Capabilities Optional Parameter.

   To have a bi-directional exchange of routing information for a
   particular AFI, SAFI between a pair of BGP speakers, each such
   speaker MUST advertise to the other (via the Capability Advertisement
   mechanism) the capability to support that particular AFI, SAFI
   route.
   
 * @author mcs
 */
public class BGP4OctetsASByteCapabilityAdvertisement extends BGP4Capability{

	public long getAS() {
		return AS;
	}

	public void setAS(long AS) {
		this.AS=AS;
	}


	private long AS;

	public BGP4OctetsASByteCapabilityAdvertisement(){
		super();
		this.setCapabitityCode(BGP4OptionalParametersTypes.CAPABILITY_CODE_AS_4_BYTES);
	}
	public BGP4OctetsASByteCapabilityAdvertisement(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	public void encode(){
		//Encoding MultiprotocolExtensionCapabilityAdvertisement
		this.setCapabilityLength(4);
		this.bytes = new byte[this.getLength()];
		encodeHeader();
		int offset = 2;//2 is the header length

		this.bytes[offset]=(byte)(AS>>>24 & 0xFF);
		this.bytes[offset+1]=(byte)(AS>>>16 & 0xFF);
		this.bytes[offset+2]=(byte)(AS>>>8 & 0xFF);
		this.bytes[offset+3]=(byte)(AS>>>0 & 0xFF);

	}

	public void decode(){
		//"Decoding MultiprotocolExtensionCapabilityAdvertisement"
		int offset = 2;
		AS=0;
		for (int k=0;k<4;k++){
			AS=(AS<<4)|((long)this.bytes[k+offset]& 0xFF);
		}
	}

	@Override
	public String toString() {
		return "AS PATH MCAPABILITY";
	}
	
}