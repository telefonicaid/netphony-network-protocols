package es.tid.bgp.bgp4.update.fields.pathAttributes;

import es.tid.bgp.bgp4.update.fields.PathAttribute;


/**
 * LOCAL_PREF is a well known attribute that is a four-octet unsigned integer.
 * A BGP speaker uses it to inform its other internal peers of the advertising
 * speaker's degree of preference for an advertised route.
   

                            
 * @author Andrea and Ajmal
 *
 */
public class LOCAL_PREF_Attribute extends PathAttribute{

	int preference_value;

	public LOCAL_PREF_Attribute(){
		super();
		this.typeCode = PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_LOCAL_PREF;
		this.optionalBit = true;
		this.transitiveBit=false;
		this.partialBit=false;
		this.extendedLengthBit=false;
	}
	public LOCAL_PREF_Attribute(byte []bytes, int offset){
		super(bytes, offset);
		decode(bytes,offset+mandatoryLength);		
	}
	@Override
	public void encode() {
		pathAttributeLength = 4; //Length of LOCAL PREF
		this.length=pathAttributeLength+mandatoryLength;
		this.bytes=new byte[this.length];
		encodeHeader();
		this.bytes[3]=(byte)(preference_value>>>24 & 0xFF);
		this.bytes[4]=(byte)(preference_value >>> 16 & 0xFF);
		this.bytes[5]=(byte)(preference_value >>>8 & 0xFF);
		this.bytes[6]=(byte)(preference_value & 0xFF);

	}
	public void decode(byte []bytes, int offset){
		preference_value=((bytes[offset]&0xFF)<<0) |  ((bytes[offset+1]&0xFF)<<8) |  ((bytes[offset+2]&0xFF)<<16)|  ((bytes[offset+3]&0xFF)<<24);


	}
	public int getValue() {
		log.info("Decoded local preference= "+ preference_value);
		return preference_value;
	}
	public void setValue(int value) {
		log.info("Encoded local preference= "+ value);

		this.preference_value = value;
	}
	
	@Override
	public String toString() {
		//String sb = super.toString();
		return "Local Preference [Value=" + preference_value+ "]";
	}
	


}
