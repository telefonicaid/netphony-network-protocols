package es.tid.bgp.bgp4.open;

/**
 * RFC 4760           Multiprotocol Extensions for BGP-4       January 2007
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

   A speaker that supports multiple <AFI, SAFI> tuples includes them as
   multiple Capabilities in the Capabilities Optional Parameter.

   To have a bi-directional exchange of routing information for a
   particular <AFI, SAFI> between a pair of BGP speakers, each such
   speaker MUST advertise to the other (via the Capability Advertisement
   mechanism) the capability to support that particular <AFI, SAFI>
   route.
   
 * @author mcs
 */
public class MultiprotocolExtensionCapabilityAdvertisement extends BGP4Capability{
	int AFI;
	int SAFI;
	
	public MultiprotocolExtensionCapabilityAdvertisement(){		
		super();
		this.setCapabitityCode(BGP4OptionalParametersTypes.CAPABILITY_CODE_MULTIPROTOCOLEXTENSION);
	}
	public MultiprotocolExtensionCapabilityAdvertisement(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	public void encode(){
		//Encoding MultiprotocolExtensionCapabilityAdvertisement
		this.setCapabilityLength(4);
		this.bytes = new byte[this.getLength()];
		encodeHeader();
		int offset = 2;//2 is the header length
		this.bytes[offset]=(byte)(AFI>>>8 & 0xFF);
		this.bytes[offset+1]=(byte)(AFI & 0xFF);
		this.bytes[offset+3]=(byte)(SAFI & 0xFF);
	}
	
	public void decode(){
		//"Decoding MultiprotocolExtensionCapabilityAdvertisement"
		int offset = 2;
		
		AFI = ((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);

		SAFI = (int)bytes[offset+3] & 0xFF;
	}
	public int getAFI() {
		return AFI;
	}
	public void setAFI(int aFI) {
		AFI = aFI;
	}
	public int getSAFI() {
		return SAFI;
	}
	public void setSAFI(int sAFI) {
		SAFI = sAFI;
	}
	@Override
	public String toString() {
		return "MultiprotocolExtensionCapabilityAdvertisement [AFI=" + AFI
				+ ", SAFI=" + SAFI + "]";
	}
	
}