package es.tid.pce.pcep.objects.tlvs.subtlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.tlvs.subtlvs.BlockSizeSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.VolumeInfoSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.VolumeSizeSubTLV;

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
The Volume sub-TLV is further structured in the sub-TLVs shown:

Sub-TLV type	Sub-TLV name		Description							Occur
TBD				Volume size		Contains the total and the				0..1
 								available volume size					
TBD				Block size		Contains the size of each memory		0..1 
								block in the volume	
TBD				Volume info		Contains information about the			0..1 
								storage	


 * 
 * 
 * @author Alejandro Tovar de Duenas
 *
 */
public class VolumeSubTLV extends PCEPSubTLV {
	
	private VolumeSizeSubTLV volumeSize;
	private BlockSizeSubTLV blockSize;
	private VolumeInfoSubTLV volumeInfo;
	
	public VolumeSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME);
		
	}
	
	public VolumeSubTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedCPUs TLV
	 */
	public void encode() {
		
		int len=0;
		
		if (volumeSize!=null){
			volumeSize.encode();
			len=len+volumeSize.getTotalSubTLVLength();
		}
		
		if (blockSize!=null){
			blockSize.encode();
			len=len+blockSize.getTotalSubTLVLength();
		}
		
		if (volumeInfo!=null){
			volumeInfo.encode();
			len=len+volumeInfo.getTotalSubTLVLength();
		}
		
		this.setSubTLVValueLength(len);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		int offset = 4;
		
		if (volumeSize!=null){
			System.arraycopy(volumeSize.getSubTLV_bytes(),0,this.subtlv_bytes,offset,volumeSize.getTotalSubTLVLength());
			offset=offset+volumeSize.getTotalSubTLVLength();
		}
		
		if (blockSize!=null){
			System.arraycopy(blockSize.getSubTLV_bytes(),0,this.subtlv_bytes,offset,blockSize.getTotalSubTLVLength());
			offset=offset+blockSize.getTotalSubTLVLength();
		}
		
		if (volumeInfo!=null){
			System.arraycopy(volumeInfo.getSubTLV_bytes(),0,this.subtlv_bytes,offset,volumeInfo.getTotalSubTLVLength());
			offset=offset+volumeInfo.getTotalSubTLVLength();
		}
	}
	
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getSubTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getSubTLV_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getSubTLV_bytes(), offset);
			log.debug("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME_SIZE:
				log.debug("VolumeSize SubTLV found");
				this.volumeSize=new VolumeSizeSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_BLOCK_SIZE:
				log.debug("BlockSize SubTLV found");
				this.blockSize=new BlockSizeSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME_INFO:
				log.debug("VolumeInfo SubTLV found");
				this.volumeInfo=new VolumeInfoSubTLV(this.getSubTLV_bytes(), offset);
				break;			
			}
			
			offset=offset+subTLVLength;
			if (offset>=(this.getSubTLVValueLength()+4)){
				log.debug("No more SubTLVs in Volume Sub-TLV");
				fin=true;
			}

		}
	}
	
	
	
	public void setVolumeSize(VolumeSizeSubTLV volumeSize) {
		this.volumeSize = volumeSize;
	}
	
	public VolumeSizeSubTLV getVolumeSize() {
		return volumeSize;
	}

	public void setBlockSize(BlockSizeSubTLV blockSize) {
		this.blockSize = blockSize;
	}
	
	public BlockSizeSubTLV getBlockSizeSubTLV() {
		return blockSize;
	}
	
	public void setVolumeInfo(VolumeInfoSubTLV volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
	
	public VolumeInfoSubTLV getVolumeInfo() {
		return volumeInfo;
	}
			
}