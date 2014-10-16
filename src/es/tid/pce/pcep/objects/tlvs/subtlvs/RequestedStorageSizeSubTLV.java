package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

In GEYSERS, when the End-point describes a Storage element, it includes
 the description of the characteristics of the requested storage in a
 set of TLVs.
 Requested Storage Size is expressed with a 32 bit field, indicating the
 amount of the requested resource in byte.
 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class RequestedStorageSizeSubTLV extends PCEPSubTLV {
	
	private byte[] RequestedStorageSize;
	
	public RequestedStorageSizeSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_STORAGE_SIZE);
		
	}
	
	public RequestedStorageSizeSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(4);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(RequestedStorageSize, 0, this.subtlv_bytes, 4, 4);
	}

	
	public void decode() {
		System.arraycopy(this.subtlv_bytes, 4, this.RequestedStorageSize, 0, 4);
	}


	public void setRequestedStorageSize(byte[] RequestedStorageSize) {
		this.RequestedStorageSize = RequestedStorageSize;
	}
	
	public byte[] isRequestedStorageSize() {
		return RequestedStorageSize;
	}

}