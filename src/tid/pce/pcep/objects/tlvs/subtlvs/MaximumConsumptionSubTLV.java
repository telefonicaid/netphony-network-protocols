package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;


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

In GEYSERS,
 MTU, Max Speed, Maximum Consumption, Idle Consumption, Sleep Consumption, and Block Size are all expressed with a 32 bit field.  
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |     		            Value                             |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class MaximumConsumptionSubTLV extends PCEPSubTLV {
	
	private byte[] maximumConsumption;
	
	public MaximumConsumptionSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MAXIMUM_CONSUMPTION);
		
	}
	
	public MaximumConsumptionSubTLV(byte[] bytes, int offset){
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
		System.arraycopy(maximumConsumption, 0, this.subtlv_bytes, 4, 4);
	}

	
	public void decode() {
		System.arraycopy(this.subtlv_bytes, 4, this.maximumConsumption, 0, 4);
	}


	public void setMaximumConsumption(byte[] max) {
		this.maximumConsumption = max;
	}
	
	public byte[] getMaximumConsumption() {
		return maximumConsumption;
	}
	
}