package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 
         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |           Type (TBD)          |           Length              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |     		            Value                         		    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


 *
 */
public class SymbolicPathNameSubTLV extends PCEPSubTLV {
	
	private long symbolicPathName;
	
	public SymbolicPathNameSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_SYMBOLIC_PATH_NAME);
		
	}
	
	public SymbolicPathNameSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode 
	 */
	public void encode() {
		this.setSubTLVValueLength(4);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		System.arraycopy(symbolicPathName, 0, this.subtlv_bytes, 4, 4);
	}

	
	public void decode() {
		System.arraycopy(this.subtlv_bytes, 4, this.symbolicPathName, 0, 4);
	}

	public long getSymbolicPathName() {
		return symbolicPathName;
	}

	public void setSymbolicPathName(long symbolicPathName) {
		this.symbolicPathName = symbolicPathName;
	}
}