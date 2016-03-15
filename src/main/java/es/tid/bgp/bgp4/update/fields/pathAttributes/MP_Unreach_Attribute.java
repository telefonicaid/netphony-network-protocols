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
         identified by a combination of {@code<AFI, SAFI>}carried in the
         attribute.

         When the Subsequent Address Family Identifier field is set to
         one of the values defined in this document, each NLRI is
         encoded as specified in the "NLRI encoding" section of this
         document.

   An UPDATE message that contains the MP_UNREACH_NLRI is not required
   to carry any other path attributes.


 * @author mcs
 *
 * TODO: It is assumed "Withdraw routes" is empty (length=0). To be implemented.
 */
public abstract class MP_Unreach_Attribute extends PathAttribute{

	private int addressFamilyIdentifier;

	private int subsequentAddressFamilyIdentifier;

	public MP_Unreach_Attribute(){
		super();
		this.setTypeCode(PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_MP_UN_REACH_NLRI);
		this.optionalBit=true;
		this.transitiveBit=false;
	}

	public MP_Unreach_Attribute(byte[] bytes, int offset){
		super(bytes,offset);
		int newOffset = this.getMandatoryLength();
		this.addressFamilyIdentifier = ((this.bytes[newOffset]&0xFF)<<8) | (this.bytes[newOffset+1]&0xFF);
		this.subsequentAddressFamilyIdentifier = this.bytes[newOffset+2]&0xFF;
	}

	public void encodeMP_Unreach_Header() {
		int offset = this.getMandatoryLength();
		//AFI
		this.bytes[offset] = (byte)((this.addressFamilyIdentifier>>8)&0xFF);
		this.bytes[offset+1] = (byte)((this.addressFamilyIdentifier)&0xFF);
		//SAFI
		this.bytes[offset+2] = (byte)((this.subsequentAddressFamilyIdentifier)&0xFF);
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + addressFamilyIdentifier;
		result = prime * result + subsequentAddressFamilyIdentifier;
		return result;
	}

	@Override
	public String toString(){
		return "mpu";
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!super.equals(obj))
			return false;
		if(getClass() != obj.getClass())
			return false;
		MP_Unreach_Attribute other = (MP_Unreach_Attribute) obj;
		if(addressFamilyIdentifier != other.getAddressFamilyIdentifier())
			return false;
		if(subsequentAddressFamilyIdentifier != other.getSubsequentAddressFamilyIdentifier())
			return false;
		return true;
	}

	public int getAddressFamilyIdentifier(){
		return addressFamilyIdentifier;
	}

	public int getSubsequentAddressFamilyIdentifier(){
		return subsequentAddressFamilyIdentifier;
	}

	public void setAddressFamilyIdentifier(int addressFamilyIdentifier){
		this.addressFamilyIdentifier = addressFamilyIdentifier;
	}

	public void setSubsequentAddressFamilyIdentifier(int subsequentAddressFamilyIdentifier){
		this.subsequentAddressFamilyIdentifier = subsequentAddressFamilyIdentifier;
	}

	public static int getAFI(byte [] bytes, int offset){
		int ml= PathAttribute.getMandatoryLength(bytes, offset);
		int offset2= offset+ml;
		int addressFamilyIdentifier=((bytes[offset2]&0xFF)<<8) | (bytes[offset2+1]&0xFF);
		return addressFamilyIdentifier;
	}

	public static int getSAFI(byte [] bytes, int offset){
		int ml= PathAttribute.getMandatoryLength(bytes, offset);
		int offset2= offset+ml;
		int subsequentAddressFamilyIdentifier = (bytes[offset2+2]&0xFF);
		return subsequentAddressFamilyIdentifier;
	}


}
