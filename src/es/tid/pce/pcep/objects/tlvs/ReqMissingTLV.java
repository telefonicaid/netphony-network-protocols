package es.tid.pce.pcep.objects.tlvs;

/**
 * <p> Represents a REQ-MISSING TLV, as defined in RFC 5440</p>
 * <pre>
 * The REQ-MISSING TLV is compliant with the PCEP TLV format defined
      in section 7.1 and is comprised of 2 bytes for the type, 2 bytes
      specifying the TLV length (length of the value portion in bytes),
      followed by a fixed-length value field of 4 bytes.

         Type:   3
         Length: 4 bytes
         Value:  4 bytes that indicate the Request-ID-number that
                 corresponds to the missing request.
</pre>
 * @author ogondio
 *
 */
public class ReqMissingTLV extends PCEPTLV {
	
	/**
	 * Indicates the Request-ID-number that
     * corresponds to the missing request.
	 */
	private long requestIdNumber;

	public ReqMissingTLV(){
		
	}
	
	public ReqMissingTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}
	
	
	public void encode() {
		// TODO Auto-generated method stub

	}

	
	public void decode() {
		// TODO Auto-generated method stub

	}

	public void setRequestIdNumber(long requestIdNumber) {
		this.requestIdNumber = requestIdNumber;
	}

	public long getRequestIdNumber() {
		return requestIdNumber;
	}

}
