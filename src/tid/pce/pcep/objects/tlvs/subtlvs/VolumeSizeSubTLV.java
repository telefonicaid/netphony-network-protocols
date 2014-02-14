package tid.pce.pcep.objects.tlvs.subtlvs;

import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;


/**
 All PCEP SubTLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

  Storage Size and Volume Size include two fields, both on 32 bits: Total Size and 
  Available Size, expressed in GB.
   
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                          Total Size                           |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |   				Available Size                      	    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class VolumeSizeSubTLV extends PCEPSubTLV {
	
	private int TotalSize, AvailableSize;
	
	public VolumeSizeSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME_SIZE);
		
	}
	
	public VolumeSizeSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode VolumeSize SubTLV
	 */
	public void encode() {
		this.setSubTLVValueLength(8);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		this.subtlv_bytes[4]=(byte) (TotalSize>>24 & 0xFF);
		this.subtlv_bytes[5]=(byte) (TotalSize>>16 & 0xFF);
		this.subtlv_bytes[6]=(byte) (TotalSize>>8 & 0xFF);
		this.subtlv_bytes[7]=(byte) (TotalSize & 0xFF);
		this.subtlv_bytes[8]=(byte) (AvailableSize>>24 & 0xFF);
		this.subtlv_bytes[9]=(byte) (AvailableSize>>16 & 0xFF);
		this.subtlv_bytes[10]=(byte) (AvailableSize>>8 & 0xFF);
		this.subtlv_bytes[11]=(byte) (AvailableSize & 0xFF);
	}

	
	public void decode() {
		TotalSize = (int)((this.subtlv_bytes[4] & 0xFF000000) | (this.subtlv_bytes[5] & 0x00FF0000) | (this.subtlv_bytes[6] & 0x0000FF00) | (this.subtlv_bytes[7] & 0x000000FF));
		AvailableSize = (int)((this.subtlv_bytes[8] & 0xFF000000) | (this.subtlv_bytes[9] & 0x00FF0000) | (this.subtlv_bytes[10] & 0x0000FF00) | (this.subtlv_bytes[11] & 0x000000FF));
	}

	
	public void setTotalSize(int TotalSize) {
		this.TotalSize = TotalSize;
	}
	
	public int getTotalSize() {
		return TotalSize;
	}

	public void setAvailableSize(int AvailableSize) {
		this.AvailableSize = AvailableSize;
	}
	
	public int getAvailableSize() {
		return AvailableSize;
	}

	
}