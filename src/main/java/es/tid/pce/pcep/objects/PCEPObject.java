package es.tid.pce.pcep.objects;

import java.util.Arrays;

import es.tid.pce.pcep.PCEPElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 /**
 * Base class for PCEPObject.
 * Implements PCEP Object Header as defined in RFC 5440.
 * PCEP Object must extend this class.
 * This class is abstract, so it cannot be instantiated
 * 0                   1                   2                   3
 * 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Object-Class  |   OT  |Res|P|I|   Object Length (bytes)       |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                                                               |
 * //                        (Object body)                        //
 * |                                                               |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * Object-Class (8 bits):  identifies the PCEP object class.

   OT (Object-Type - 4 bits):  identifies the PCEP object type.

      The Object-Class and Object-Type fields are managed by IANA.

      The Object-Class and Object-Type fields uniquely identify each
      PCEP object.

   Res flags (2 bits):  Reserved field.  This field MUST be set to zero
      on transmission and MUST be ignored on receipt.

   P flag (Processing-Rule - 1-bit):  the P flag allows a PCC to specify
      in a PCReq message sent to a PCE whether the object must be taken
      into account by the PCE during path computation or is just
      optional.  When the P flag is set, the object MUST be taken into
      account by the PCE.  Conversely, when the P flag is cleared, the
      object is optional and the PCE is free to ignore it.

   I flag (Ignore - 1 bit):  the I flag is used by a PCE in a PCRep
      message to indicate to a PCC whether or not an optional object was
      processed.  The PCE MAY include the ignored optional object in its
      reply and set the I flag to indicate that the optional object was
      ignored during path computation.  When the I flag is cleared, the
      PCE indicates that the optional object was processed during the
      path computation.  The setting of the I flag for optional objects
      is purely indicative and optional.  The I flag has no meaning in a
      PCRep message when the P flag has been set in the corresponding
      PCReq message.

   If the PCE does not understand an object with the P flag set or
   understands the object but decides to ignore the object, the entire
   PCEP message MUST be rejected and the PCE MUST send a PCErr message
   with Error-Type="Unknown Object" or "Not supported Object" along with
   the corresponding RP object.  Note that if a PCReq includes multiple
   requests, only requests for which an object with the P flag set is
   unknown/unrecognized MUST be rejected.

   Object Length (16 bits):  Specifies the total object length including
      the header, in bytes.  The Object Length field MUST always be a
      multiple of 4, and at least 4.  The maximum object content length
      is 65528 bytes.

 * @author Oscar Gonzalez de Dios
 * @serial 0.1
*/
public abstract class PCEPObject implements PCEPElement {

	/**
	 * Object-Class (8 bits):  identifies the PCEP object class.
	 */
	protected int ObjectClass;//Object Class

	/**
	 * OT (Object-Type - 4 bits):  identifies the PCEP object type.
	 */
	protected int OT;//Object Type

	/**
	 * Res flags (2 bits):  Reserved field.  This field MUST be set to zero
     * on transmission and MUST be ignored on receipt.
	 */
	protected int Res;

	/**
	 * P flag (Processing-Rule - 1-bit):  the P flag allows a PCC to specify
      in a PCReq message sent to a PCE whether the object must be taken
      into account by the PCE during path computation or is just
      optional.  When the P flag is set, the object MUST be taken into
      account by the PCE.  Conversely, when the P flag is cleared, the
      object is optional and the PCE is free to ignore it.
	 */
	protected boolean Pbit;

	/**
	 *  I flag (Ignore - 1 bit):  the I flag is used by a PCE in a PCRep
      message to indicate to a PCC whether or not an optional object was
      processed.  The PCE MAY include the ignored optional object in its
      reply and set the I flag to indicate that the optional object was
      ignored during path computation.  When the I flag is cleared, the
      PCE indicates that the optional object was processed during the
      path computation.  The setting of the I flag for optional objects
      is purely indicative and optional.  The I flag has no meaning in a
      PCRep message when the P flag has been set in the corresponding
      PCReq message.
	 */
	protected boolean Ibit;

	/**
	 * Object Length (16 bits):  Specifies the total object length including
      the header, in bytes.  The Object Length field MUST always be a
      multiple of 4, and at least 4.  The maximum object content length
      is 65528 bytes.
	 */
	protected int ObjectLength;

	/**
	 * Bytes of the object
	 */
	protected byte object_bytes[];

	protected static final Logger log = LoggerFactory.getLogger("PCEPParser");

	/**
	 * Constructs a PCEPObject 
	 */
	public PCEPObject() {
		// Default values
		Res=0;
		Pbit=false;
		Ibit=false;

	}

	/**
	 * Construct a new PCEP Object from a sequence of bytes.
	 * @param bytes bytes
	 * @param offset offset
	 * @throws MalformedPCEPObjectException Exception when the object is malformed
	 */
	public PCEPObject(byte []bytes, int offset) throws MalformedPCEPObjectException{
		ObjectLength=((bytes[offset+2]<<8)& 0xFF00) |  (bytes[offset+3] & 0xFF);
		this.object_bytes=new byte[ObjectLength];
		System.arraycopy(bytes, offset, object_bytes, 0, ObjectLength);
		decodeHeader();	
	}


	/**
	 * Encode the object. It is specific to the PCEP Object. Must be implemented
	 * in each object!!!!!
	 */
	public abstract void encode();

	/**
	 * Decode the object. It is specific to the PCEP Object. Must be implemented
	 * in each object!!!!
	 */
	public abstract void decode() throws MalformedPCEPObjectException;

	/**
	 * Decodes an object from a sequence of bytes starting in offset. It is the same as calling
	 * the constructor with the sequence of bytes. 
	 * @param bytes bytes
	 * @param offset offset
	 * @throws MalformedPCEPObjectException Exception when the object is malformed
	 */
	public void decode(byte[] bytes,int offset) throws MalformedPCEPObjectException{
		ObjectLength=((bytes[offset+2]<<8)& 0xFF00) |  (bytes[offset+3] & 0xFF);
		this.object_bytes=new byte[ObjectLength];
		System.arraycopy(bytes, offset, object_bytes, 0, ObjectLength);
		decodeHeader();
		decode();
	}

	/**
	 * Encodes the header of the PCEP object (4 bytes)
	 */
	public void encode_header() {
		object_bytes[0]=(byte)ObjectClass;
		object_bytes[1]=(byte)( ( (OT<<4) & 0xF0) | ( (Res<<2) & 0x0C) | (((Pbit?1:0)<<1) & 0x02) | (Ibit?1:0));
		object_bytes[2]=(byte)((ObjectLength>>8) & 0xFF);
		object_bytes[3]=(byte)(ObjectLength & 0xFF);
	}

	/**
	 * Decodes the PCEP Object Header
	 */
	public void decodeHeader(){
		ObjectClass=(int)object_bytes[0]&0XFF;
		OT=(object_bytes[1]>>4)& 0x0F;
		ObjectLength=((object_bytes[2]<<8)& 0xFF00) |  (object_bytes[3] & 0xFF);
		Res=(object_bytes[1]&0x0C)>>2;
		Pbit=(object_bytes[1]&0x02)==0x02;
		Ibit=(object_bytes[1]&0x01)==0x01;
	}

	/**
	 * Returns the Object-Class (8 bits) which  identifies the PCEP object class.
	 * @return the object class
	 */
	public int getObjectClass() {
		return ObjectClass;
	}

	/**
	 * Sets the Object-Class (8 bits) which  identifies the PCEP object class.
	 * @param objectClass Object-Class
	 */
	public void setObjectClass(int objectClass) {
		ObjectClass = objectClass;
	}

	/**
	 * 
	 * @return OT
	 */
	public int getOT(){
		return OT;
	}

	/**
	 * 
	 * @param oT
	 */
	public void setOT(int oT){
		OT = oT;
	}

	/**
	 * 
	 * @return Res
	 */
	public int getRes() {
		return Res;
	}

	/**
	 * 
	 * @param res
	 */
	public void setRes(int res) {
		Res = res;
	}



	public boolean isPbit() {
		return Pbit;
	}

	public void setPbit(boolean pbit) {
		Pbit = pbit;
	}

	public boolean isIbit() {
		return Ibit;
	}

	public void setIbit(boolean ibit) {
		Ibit = ibit;
	}

	/**
	 * Get the object Length. Generic for all objects
	 * @return Object Length
	 */
	public int getLength() {
		return ObjectLength;
	}



//	/**
//	 * 
//	 * @param objectLength
//	 */
//	public void setObjectLength(int objectLength) {
//		ObjectLength = objectLength;
//	}

	protected void setObjectLength(int objectLength) {
		ObjectLength = objectLength;
	}

	public byte[] getObject_bytes() {
		return object_bytes;
	}

	public void setObject_bytes(byte[] object_bytes) {
		this.object_bytes = object_bytes;
	}

	/**
	 * Get the bytes of the message. Remember to call encode first!!!
	 * @return Bytes of the message
	 */
	public byte[] getBytes() {
		return object_bytes;
	}	


	/**
	 * Static method to obtain the object class of an object encoded in a byte array
	 * @param bytes Byte array where the object appears
	 * @param offset Byte where the the object starts in the byte array
	 * @return ObjectClass
	 */
	public static int getObjectClass(byte[] bytes, int offset){
		try {
			int obc= (int)(bytes[offset]&0xFF);
			return obc;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return 0;
		}

	}

	/**
	 * Static method to get the Object type of an object encoded in a byte array
	 * @param bytes Byte array where the object appears
	 * @param offset Byte where the the object starts in the byte array
	 * @return ObjectType
	 */
	public static int getObjectType(byte[] bytes, int offset){
		try {
			int obc=(((bytes[offset+1]&0xFF)>>>4)& 0x0F);
			return obc;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return 0;
		}
	}

	/**
	 * Static method to get the length of an object in a sequence of bytes
	 * @param bytes Byte array where the object appears
	 * @param offset Byte where the the object starts in the byte array
	 * @return ObjectLegth
	 */
	public static int getObjectLength(byte[] bytes, int offset){
		return (((int)((bytes[offset+2]&0xFF)<<8)& 0xFF00)|  ((int)bytes[offset+3] & 0xFF));
	}


	public static boolean supportedObject(int oc){
		if ((oc>ObjectParameters.PCEP_OBJECT_CLASS_CLOSE)|(oc==0)){
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (Ibit ? 1231 : 1237);
		result = prime * result + OT;
		result = prime * result + ObjectClass;
		result = prime * result + ObjectLength;
		result = prime * result + (Pbit ? 1231 : 1237);
		result = prime * result + Res;
		result = prime * result + Arrays.hashCode(object_bytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PCEPObject other = (PCEPObject) obj;
		if (Ibit != other.Ibit)
			return false;
		if (OT != other.OT)
			return false;
		if (ObjectClass != other.ObjectClass)
			return false;
		if (ObjectLength != other.ObjectLength)
			return false;
		if (Pbit != other.Pbit)
			return false;
		if (Res != other.Res)
			return false;
		if (!Arrays.equals(object_bytes, other.object_bytes))
			return false;
		return true;
	}
	
	
}
