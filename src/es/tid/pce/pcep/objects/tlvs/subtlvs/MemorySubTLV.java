package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 All PCEP SubTLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

Memory (Figure 3.26) includes two fields, both on 32 bits: Total Size and 
Available Size, expressed in bytes.
 
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                          Total Size                           |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |   				Available Size                         |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class MemorySubTLV extends PCEPSubTLV {
	
	private int totalSize, availableSize;
	
	public MemorySubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MEMORY);
		
	}
	
	public MemorySubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedStorageSize TLV
	 */
	public void encode() {
		this.setSubTLVValueLength(8);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (totalSize>>24 & 0xFF);
		this.subtlv_bytes[5]=(byte) (totalSize>>16 & 0xFF);
		this.subtlv_bytes[6]=(byte) (totalSize>>8 & 0xFF);
		this.subtlv_bytes[7]=(byte) (totalSize & 0xFF);
		this.subtlv_bytes[8]=(byte) (availableSize>>24 & 0xFF);
		this.subtlv_bytes[9]=(byte) (availableSize>>16 & 0xFF);
		this.subtlv_bytes[10]=(byte) (availableSize>>8 & 0xFF);
		this.subtlv_bytes[11]=(byte) (availableSize& 0xFF);
	}

	
	public void decode() {
		totalSize = (int)((this.subtlv_bytes[4] & 0xFF000000) | (this.subtlv_bytes[5] & 0x00FF0000) | (this.subtlv_bytes[6] & 0x0000FF00) | (this.subtlv_bytes[7] & 0x000000FF));
		availableSize = (int)((this.subtlv_bytes[8] & 0xFF000000) | (this.subtlv_bytes[9] & 0x00FF0000) | (this.subtlv_bytes[10] & 0x0000FF00) | (this.subtlv_bytes[11] & 0x000000FF));
	}

	
	public void setTotalSize(int TotalSize) {
		this.totalSize = TotalSize;
	}
	
	public int getTotalSize() {
		return totalSize;
	}

	public void setAvailableSize(int AvailableSize) {
		this.availableSize = AvailableSize;
	}
	
	public int getAvailableSize() {
		return availableSize;
	}
	
}