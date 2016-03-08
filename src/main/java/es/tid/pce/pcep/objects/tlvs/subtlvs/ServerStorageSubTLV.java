package es.tid.pce.pcep.objects.tlvs.subtlvs;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.StorageInfoSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.StorageSizeSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.VolumeSubTLV;


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
The Server Storage sub-TLV is further structured in the sub-TLVs shown in
the following table, which are the same specified for the Server sub-TLVs:

Sub-TLV type	Sub-TLV name	Description								Occur
TBD				Storage Size	Contains the total and the available	0..1
 								storage size							
TBD				Storage Info	Contains information about the storage	0..1
TBD				Volume			Contains information about the volumes	0..n
 								for the given storage 	


 * 
 * 
 * @author Alejandro Tovar de Duenas
 *
 */
public class ServerStorageSubTLV extends PCEPSubTLV {
	
	private StorageSizeSubTLV storageSize;
	private StorageInfoSubTLV storageInfo;
	private LinkedList <VolumeSubTLV> volumeList;
	
	public ServerStorageSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_SERVER_STORAGE);
		
	}
	
	public ServerStorageSubTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedCPUs TLV
	 */
	public void encode() {
		
		int len=0;
		
		if (storageSize!=null){
			storageSize.encode();
			len=len+storageSize.getTotalSubTLVLength();
		}
		
		if (storageInfo!=null){
			storageInfo.encode();
			len=len+storageInfo.getTotalSubTLVLength();
		}
		
		if (volumeList!=null){
			for (int i=0;i<volumeList.size();++i){
				(volumeList.get(i)).encode();
				len=len+(volumeList.get(i)).getTotalSubTLVLength();
			}
		}
		
		this.setSubTLVValueLength(len);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		int offset = 4;
		
		if (storageSize!=null){
			System.arraycopy(storageSize.getSubTLV_bytes(),0,this.subtlv_bytes,offset,storageSize.getTotalSubTLVLength());
			offset=offset+storageSize.getTotalSubTLVLength();
		}
		
		if (storageInfo!=null){
			System.arraycopy(storageInfo.getSubTLV_bytes(),0,this.subtlv_bytes,offset,storageInfo.getTotalSubTLVLength());
			offset=offset+storageInfo.getTotalSubTLVLength();
		}
		
		if (volumeList!=null){
			for (int i=0;i<volumeList.size();++i){
				System.arraycopy(volumeList.get(i).getSubTLV_bytes(),0,this.subtlv_bytes,offset,volumeList.get(i).getTotalSubTLVLength());
				offset=offset+volumeList.get(i).getTotalSubTLVLength();
			}
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
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_SIZE:
				log.debug("StorageSize SubTLV found");
				this.storageSize=new StorageSizeSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_INFO:
				log.debug("StorageInfo SubTLV found");
				this.storageInfo=new StorageInfoSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME:
				VolumeSubTLV volume = new VolumeSubTLV(this.getSubTLV_bytes(), offset);
				log.debug("Volume SubTLV found");
				if (volumeList==null){
					log.debug("Creating VolumeSubTLVList");
					volumeList=new LinkedList<VolumeSubTLV>();
				}
				this.volumeList.add(volume);			
			}
			
			offset=offset+subTLVLength;
			if (offset>=(this.getSubTLVValueLength()+4)){
				log.debug("No more SubTLVs in ServerStorage Sub-TLV");
				fin=true;
			}

		}
	}
	
	
	
	public void setStorageSize(StorageSizeSubTLV StorageSize) {
		this.storageSize = StorageSize;
	}
	
	public StorageSizeSubTLV getStorageSize() {
		return storageSize;
	}

	public void setStorageInfo(StorageInfoSubTLV StorageInfo) {
		this.storageInfo = StorageInfo;
	}
	
	public StorageInfoSubTLV getStorageInfoSubTLV() {
		return storageInfo;
	}
	
	public LinkedList<VolumeSubTLV> getVolumeList() {
		return volumeList;
	}

	public void setVolumeList(LinkedList<VolumeSubTLV> VolumeList) {
		this.volumeList = VolumeList;
	}
			
}