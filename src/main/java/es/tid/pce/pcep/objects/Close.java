package es.tid.pce.pcep.objects;

/**
 * <p> Represents a PCEP CLOSE Object, as defined in RFC 5440.</p>
 * <pre>
 * From RFC 5440 Section 7.17. CLOSE Object
 * 
 * The CLOSE object MUST be present in each Close message.  There MUST
 * be only one CLOSE object per Close message.  If a Close message is
 * received that contains more than one CLOSE object, the first CLOSE
 * object is the one that must be processed.  Other CLOSE objects MUST
 * be silently ignored.
 * CLOSE Object-Class is 15.
 * CLOSE Object-Type is 1.
 *  The format of the CLOSE object body is as follows:
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |          Reserved             |      Flags    |    Reason     |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                                                               |
 * //                         Optional TLVs                       //
 * |                                                               |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  Reserved (16 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.
   Flags (8 bits):  No flags are currently defined.  The Flag field MUST
      be set to zero on transmission and MUST be ignored on receipt.
   Reason (8 bits):  specifies the reason for closing the PCEP session.
      The setting of this field is optional.  IANA manages the codespace
      of the Reason field.  The following values are currently defined:
       Reasons
        Value        Meaning
          1          No explanation provided
          2          DeadTimer expired
          3          Reception of a malformed PCEP message
          4          Reception of an unacceptable number of unknown
                     requests/replies
          5          Reception of an unacceptable number of unrecognized
                     PCEP messages
   Optional TLVs may be included within the CLOSE object body.  The
   specification of such TLVs is outside the scope of this document.
   </pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 * @version 0.1
 */

public class Close extends PCEPObject{

	/**
	 * Reason of the close
	 */
	private int reason; 
	
	public static int reason_MAX_VALUE=0xFF;
	
	//Constructors
	
	public Close(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_CLOSE);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_CLOSE);
	}

	public Close(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode CLOSE message
	 * Only reason is encoded. The rest is set to 0
	 */
	public void encode(){
		ObjectLength=8;/* 4 bytes de la cabecera + 4 del cuerpo No hay TLVs opcionales por ahora */
		object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=0;
		object_bytes[5]=0;
		object_bytes[6]=0;
		object_bytes[7]=(byte)reason;
	}
	
	/**
	 * Decode CLOSE message. 
	 * Reserved and flags are ignored (RFC 5440)
	 * Reason is read
	 */
	public void decode() throws MalformedPCEPObjectException{
		if (ObjectLength<8){
			throw new MalformedPCEPObjectException();
		}
		reason=(int)object_bytes[7]&0xFF;
	}
	
	//Getters and Setters
	
	/**
	 * Gets the reason for closing the PCEP Session
	 * @return reason for closing the PCEP Session
	 */
	public int getReason() {
		return reason;
	}

	/**
	 * Sets the reason for closing the PCEP Session
	 * @param reason reason for closing the PCEP Session
	 */
	public void setReason(int reason) {
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + reason;
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
		Close other = (Close) obj;
		if (reason != other.reason) {
			return false;
		}
		return true;
	}
	
	
	
}
