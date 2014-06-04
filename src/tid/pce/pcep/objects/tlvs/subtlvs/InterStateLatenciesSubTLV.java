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
 Inter State Latencies includes two fields, both on 32 bits: Wake-up Latency and Power-up Latency, expressed in ms. 
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                        Wake-up latency                        |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |   		             Power-up latency                       |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class InterStateLatenciesSubTLV extends PCEPSubTLV {
	
	private byte[] wake_up_latency;
	private byte[] power_up_latency;
	
	public InterStateLatenciesSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_INTER_STATE_LATENCIES);
		
	}
	
	public InterStateLatenciesSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode 
	 */
	public void encode() {
		this.setSubTLVValueLength(8);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(wake_up_latency, 0, this.subtlv_bytes, 4, 4);
		System.arraycopy(power_up_latency, 0, this.subtlv_bytes, 8, 4);
	}

	
	public void decode() {
		System.arraycopy(this.subtlv_bytes, 4, this.wake_up_latency, 0, 4);
		System.arraycopy(this.subtlv_bytes, 8, this.power_up_latency, 0, 4);
	}


	public void setWakeUpLatency(byte[] wake) {
		this.wake_up_latency = wake;
	}
	
	public byte[] getWakeUpLatency() {
		return wake_up_latency;
	}
	
	public void setPowerUpLatency(byte[] power) {
		this.power_up_latency = power;
	}
	
	public byte[] getPowerUpLatency() {
		return power_up_latency;
	}
	
}