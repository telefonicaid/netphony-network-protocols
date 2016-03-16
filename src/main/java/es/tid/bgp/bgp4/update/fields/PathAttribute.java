package es.tid.bgp.bgp4.update.fields;

import es.tid.bgp.bgp4.objects.BGP4Object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Path Attributes

         A variable-length sequence of path attributes is present in
         every UPDATE message, except for an UPDATE message that carries
         only the withdrawn routes.  Each path attribute is a triple
        {@code <attribute type, attribute length, attribute value> }of variable
         length.

         Attribute Type is a two-octet field that consists of the
         Attribute Flags octet, followed by the Attribute Type Code
         octet.
 * {@code
               0                   1
               0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5
               +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
               |  Attr. Flags  |Attr. Type Code|
               +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
  * }             
         If the Extended Length bit of the Attribute Flags octet is set
         to 0, the third octet of the Path Attribute contains the length
         of the attribute data in octets.

         If the Extended Length bit of the Attribute Flags octet is set
         to 1, the third and fourth octets of the path attribute contain
         the length of the attribute data in octets.

     
 * @author pac
 *
 */
public abstract class PathAttribute extends BGP4Object {
	protected static final Logger log = LoggerFactory.getLogger("BGP4Parser");
	//protected byte attributeFlags;
	protected int typeCode;
	/**
	 * Optional bit. The high-order bit (bit 0) of the Attribute Flags octet is the
         Optional bit.  It defines whether the attribute is optional (if
         set to 1) or well-known (if set to 0).
	 */
	protected boolean optionalBit=false;
	/**
	 * Transitive bit. The second high-order bit (bit 1) of the Attribute Flags octet
         is the Transitive bit.  It defines whether an optional
         attribute is transitive (if set to 1) or non-transitive (if set
         to 0). For well-known attributes, the Transitive bit MUST be set to 1.
	 */
	protected boolean transitiveBit=true;
	/**
	 * Partial Bit.	The third high-order bit (bit 2) of the Attribute Flags octet
    is the Partial bit.  It defines whether the information
    contained in the optional transitive attribute is partial (if
    set to 1) or complete (if set to 0).  For well-known attributes
    and for optional non-transitive attributes, the Partial bit
    MUST be set to 0.*/
	protected boolean partialBit=false;
	/**  ExtendedLengthBit. The fourth high-order bit (bit 3) of the Attribute Flags octet
         is the Extended Length bit.  It defines whether the Attribute
         Length is one octet (if set to 0) or two octets (if set to 1). */
	protected boolean extendedLengthBit=false;

	protected int mandatoryLength = 3;
	protected int pathAttributeLength;
	public PathAttribute(){
	}

	
	public PathAttribute(byte []bytes, int offset){

		//Atribute Flags
		optionalBit=(bytes[offset]&0x80)==0x80;
		transitiveBit = (bytes[offset]&0x40)==0x40;
		partialBit =(bytes[offset]&0x20)==0x20;
		extendedLengthBit = (bytes[offset]&0x10)==0x10;
		
		//Type Code
		typeCode = bytes[offset+1]&0xFF;
		
		if (extendedLengthBit){//Attribute Length is two octects
			mandatoryLength=4;
			pathAttributeLength=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		}
		else //It is one octec
			pathAttributeLength =  bytes[offset+2]&0xFF;
		this.length= pathAttributeLength+ mandatoryLength;
		this.bytes=new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);	
	}
	public void encodeHeader(){
		bytes[0] = 0x00;
		bytes[0] = (byte) ((((optionalBit?1:0)<<7) & 0x80) | (((transitiveBit?1:0)<<6) & 0x40) | (((partialBit?1:0)<<5) & 0x20) | (((extendedLengthBit?1:0)<<4) & 0x10));

		bytes[1]=(byte)(typeCode & 0xFF);
		if (extendedLengthBit){
			bytes[2]=(byte)(pathAttributeLength>>>8 & 0xFF);
			bytes[3]=(byte)(pathAttributeLength & 0xFF);
			mandatoryLength=4;
		}
		else
			bytes[2]=(byte)(pathAttributeLength & 0xFF );
		
	}
	
	protected void setPathAttributeLength(int pal){
		this.pathAttributeLength= pal;
		if (pathAttributeLength>255){
			this.mandatoryLength=4;
			this.length=pathAttributeLength+this.mandatoryLength;
		}else {
			this.mandatoryLength=3;
			this.length=pathAttributeLength+this.mandatoryLength;
		}
	}
	
	
	
	public int getPathAttributeLength() {
		return pathAttributeLength;
	}


	public int getMandatoryLength() {
		return mandatoryLength;
	}


	public static int getAttibuteTypeCode(byte[] bytes, int offset){
		try {
			int obc= (int)(bytes[offset+1]&0xFF);
			return obc;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return 0;
		}
	}
	
	public static int getMandatoryLength(byte[] bytes, int offset){
		boolean extendedLengthBit = (bytes[offset]&0x10)==0x10;
		if (extendedLengthBit) {
			return 4;
		}else {
			return 3;
		}
	}
	
	public static int getAttributeLength (byte[] bytes, int offset){
		boolean extendedLengthBit = (bytes[offset]&0x10)==0x10;
		if (extendedLengthBit) {
			return ((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		}else {
			return  bytes[offset+2]&0xFF;
		}
	}
	


	public int getTypeCode() {
		return typeCode;
	}


	protected void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (extendedLengthBit ? 1231 : 1237);
		result = prime * result + mandatoryLength;
		result = prime * result + (optionalBit ? 1231 : 1237);
		result = prime * result + (partialBit ? 1231 : 1237);
		result = prime * result + pathAttributeLength;
		result = prime * result + (transitiveBit ? 1231 : 1237);
		result = prime * result + typeCode;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathAttribute other = (PathAttribute) obj;
		if (extendedLengthBit != other.extendedLengthBit)
			return false;
		if (mandatoryLength != other.mandatoryLength)
			return false;
		if (optionalBit != other.optionalBit)
			return false;
		if (partialBit != other.partialBit)
			return false;
		if (pathAttributeLength != other.pathAttributeLength)
			return false;
		if (transitiveBit != other.transitiveBit)
			return false;
		if (typeCode != other.typeCode)
			return false;
		return true;
	}



//	public String toString() {
//		return "[typeCode=" + typeCode + ", optionalBit="
//				+ optionalBit + ", transitiveBit=" + transitiveBit
//				+ ", partialBit=" + partialBit + ", extendedLengthBit="
//				+ extendedLengthBit + ", length=" + pathAttributeLength + "]";
//	}


	

	
}
