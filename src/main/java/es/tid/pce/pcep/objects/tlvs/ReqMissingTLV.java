package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * REQ-MISSING TLV (Type 3). Defined in RFC 5440

 * @author ogondio
 *
 */
public class ReqMissingTLV extends PCEPTLV {

	/*
	 *  * <p> Represents a REQ-MISSING TLV, as </p>
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

	 */

	/**
	 * Indicates the Request-ID-number that
	 * corresponds to the missing request.
	 */
	private long requestIdNumber;

	public ReqMissingTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_REQ_MISSING_TLV);

	}

	public ReqMissingTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}


	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		ByteHandler.encode4bytesLong(requestIdNumber, this.tlv_bytes,offset);
	}


	public void decode() {
		int offset=4;
		requestIdNumber=ByteHandler.decode4bytesLong(this.tlv_bytes, offset);

	}

	public void setRequestIdNumber(long requestIdNumber) {
		this.requestIdNumber = requestIdNumber;
	}

	public long getRequestIdNumber() {
		return requestIdNumber;
	}

	@Override
	public String toString() {
		return "ReqMissingTLV [requestIdNumber=" + requestIdNumber + "]";
	}
	
	

}
