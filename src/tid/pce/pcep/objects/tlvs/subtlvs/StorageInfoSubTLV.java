package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;


/**
 All PCEP SubTLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

Storage Info and Volume Info include two fields, Access Status and Volatile. The Access Status defines the current status of the storage. The following values are defined:
•	0x01: queuing.
•	0x02: production.
•	0x03: closed.
•	0x04: draining.
The Volatile field indicate if the held data are lost when the power is removed. The following values are defined:
•	0x01: volatile.
•	0x02: not volatile.
•	0x03: undefined.
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |     Access Status             |   Volatile    |   Reserved    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class StorageInfoSubTLV extends PCEPSubTLV {
	
	private int AccessStatus, Volatil;
	
	public StorageInfoSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_INFO);
		
	}
	
	public StorageInfoSubTLV(byte[] bytes, int offset){
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
		this.subtlv_bytes[4]=(byte) (AccessStatus>>8 & 0xFF);
		this.subtlv_bytes[5]=(byte) (AccessStatus & 0xFF);
		this.subtlv_bytes[6]=(byte) (Volatil & 0xFF);
	}

	
	public void decode() {
		AccessStatus = (int)((this.subtlv_bytes[4] & 0xFF00) | (this.subtlv_bytes[5] & 0x00FF));
		Volatil = (int)(this.subtlv_bytes[6] & 0xFF);
	}

	
	public void setAccessStatus(int AccessStatus) {
		this.AccessStatus = AccessStatus;
	}
	
	public int getAccessStatus() {
		return AccessStatus;
	}

	public void setVolatil(int Volatil) {
		this.Volatil = Volatil;
	}
	
	public int getVolatil() {
		return Volatil;
	}

	
}