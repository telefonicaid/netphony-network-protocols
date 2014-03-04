package tid.bgp.bgp4.update.fields.pathAttributes;

import tid.bgp.bgp4.update.fields.PathAttribute;

/**
 * RFC 4271                         BGP-4
 *  b) AS_PATH (Type Code 2):

            AS_PATH is a well-known mandatory attribute that is composed
            of a sequence of AS path segments.  Each AS path segment is
            represented by a triple <path segment type, path segment
            length, path segment value>.

            The path segment type is a 1-octet length field with the
            following values defined:

               Value      Segment Type

               1         AS_SET: unordered set of ASes a route in the
                            UPDATE message has traversed

               2         AS_SEQUENCE: ordered set of ASes a route in
                            the UPDATE message has traversed

            The path segment length is a 1-octet length field,
            containing the number of ASes (not the number of octets) in
            the path segment value field.

            The path segment value field contains one or more AS
            numbers, each encoded as a 2-octet length field.

            Usage of this attribute is defined in 5.1.2.
            
 * @author mcs
 *
 */
public class AS_Path_Attribute extends PathAttribute{
	int type;
	int numberASes;
	int value;
	
	public AS_Path_Attribute(){		
		super();
		//Poner los flags. 
		this.typeCode = PathAttributesTypeCode.PATH_ATTRIBUTE_TYPECODE_ASPATH;
		

	}
	public AS_Path_Attribute(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	@Override
	public void encode() {
		log.info("As_Path");
		pathAttributeLength = 4;
		this.length=pathAttributeLength+mandatoryLength;
		this.bytes=new byte[this.length];
		encodeHeader();
		bytes[3]=(byte)(type & 0xFF );
		bytes[4]=(byte)(numberASes & 0xFF);		
		bytes[5]=(byte)(value>>>8 & 0xFF);	
		bytes[6]= (byte)(value & 0xFF);
	}
	public void decode(){
		type =  bytes[mandatoryLength];
		numberASes = bytes[mandatoryLength+1];
		value = ((((int)bytes[mandatoryLength+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[mandatoryLength+3] & 0xFF);
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumberASes() {
		return numberASes;
	}
	public void setNumberASes(int numberASes) {
		this.numberASes = numberASes;
	}
	@Override
	public String toString() {
		return "AS_PATH [Value=" + value + " NumberAS: " + numberASes + "type: " + type + "]";
	}

}
