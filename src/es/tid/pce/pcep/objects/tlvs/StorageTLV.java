package es.tid.pce.pcep.objects.tlvs;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.subtlvs.CostSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.LocationSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.NetworkSpecSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PowerSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ResourceIDSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.StorageInfoSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.StorageSizeSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.VolumeSubTLV;


/**
  * Storage TLV (From GEYSERS).
  *  
	Storage description – is part of the Notification, it includes the
	description of the characteristics of the requested storage and is structured in
	a set of TLVs.


   The format of the Storage TLV object for is as follows:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |  Length                       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          			                         |
     |							SubTLVs								 |
     |																 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class StorageTLV extends PCEPTLV {
	
	
	public ResourceIDSubTLV resourceID;
	public LocationSubTLV location;
	public LinkedList <CostSubTLV> costList;
	public NetworkSpecSubTLV networkSpec;
	public PowerSubTLV power;
	public StorageSizeSubTLV storageSize;
	public StorageInfoSubTLV storageInfo;
	public LinkedList <VolumeSubTLV> volumeList;
	
	public StorageTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_STORAGE);
		
	}
	
	public StorageTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		
		int length=0;
		
		resourceID.encode();
		length=length+resourceID.getTotalSubTLVLength();
		
		if (location!=null){
			location.encode();
			length=length+location.getTotalSubTLVLength();
		}
		
		if (costList!=null){
			for (int i=0;i<costList.size();++i){
				(costList.get(i)).encode();
				length=length+(costList.get(i)).getTotalSubTLVLength();
			}	
		}
		
		if (networkSpec!=null){
			networkSpec.encode();
			length=length+networkSpec.getTotalSubTLVLength();
		}
		
		if (power!=null){
			power.encode();
			length=length+power.getTotalSubTLVLength();
		}
		
		if (storageSize!=null){
			storageSize.encode();
			length=length+storageSize.getTotalSubTLVLength();
		}
		
		if (storageInfo!=null){
			storageInfo.encode();
			length=length+storageInfo.getTotalSubTLVLength();
		}
		
		if (volumeList!=null){
			for (int i=0;i<volumeList.size();++i){
				(volumeList.get(i)).encode();
				length=length+(volumeList.get(i)).getTotalSubTLVLength();
			}
		}
				
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		System.arraycopy(resourceID.getSubTLV_bytes(),0,this.tlv_bytes,offset,resourceID.getTotalSubTLVLength());
		offset=offset+resourceID.getTotalSubTLVLength();
		
		if (location!=null){
			System.arraycopy(location.getSubTLV_bytes(),0,this.tlv_bytes,offset,location.getTotalSubTLVLength());
			offset=offset+location.getTotalSubTLVLength();
		}
		
		if (costList!=null){
			for (int i=0;i<costList.size();++i){
				System.arraycopy(costList.get(i).getSubTLV_bytes(),0,this.tlv_bytes,offset,costList.get(i).getTotalSubTLVLength());
				offset=offset+costList.get(i).getTotalSubTLVLength();
			}	
		}
		
		if (networkSpec!=null){
			System.arraycopy(networkSpec.getSubTLV_bytes(),0,this.tlv_bytes,offset,networkSpec.getTotalSubTLVLength());
			offset=offset+networkSpec.getTotalSubTLVLength();		
		}
		
		if (power!=null){
			System.arraycopy(power.getSubTLV_bytes(),0,this.tlv_bytes,offset,power.getTotalSubTLVLength());
			offset=offset+power.getTotalSubTLVLength();
		}
		
		if (storageSize!=null){
			System.arraycopy(storageSize.getSubTLV_bytes(),0,this.tlv_bytes,offset,storageSize.getTotalSubTLVLength());
			offset=offset+storageSize.getTotalSubTLVLength();
		}
		
		if (storageInfo!=null){
			System.arraycopy(storageInfo.getSubTLV_bytes(),0,this.tlv_bytes,offset,storageInfo.getTotalSubTLVLength());
			offset=offset+storageInfo.getTotalSubTLVLength();
		}
		
		if (volumeList!=null){
			for (int i=0;i<volumeList.size();++i){
				System.arraycopy(volumeList.get(i).getSubTLV_bytes(),0,this.tlv_bytes,offset,volumeList.get(i).getTotalSubTLVLength());
				offset=offset+volumeList.get(i).getTotalSubTLVLength();
			}
		}
		
		log.finest("Encoding Storage TLV");
				
	}

	
	public void decode() throws MalformedPCEPObjectException{
		log.finest("Decoding Storage EndPoint TLV");
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getTlv_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getTlv_bytes(), offset);
			log.finest("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_RESOURCE_ID:
				log.finest("Storage Resource ID");
				this.resourceID=new ResourceIDSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_LOCATION:
				log.finest("Location SubTLV");
				this.location=new LocationSubTLV(this.getTlv_bytes(), offset);
				break;
			
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_COST:
				CostSubTLV cost = new CostSubTLV(this.getTlv_bytes(), offset);
				log.finest("Cost SubTLV");
				if (costList==null){
					log.finest("Creating CostSubTLVList");
					costList=new LinkedList<CostSubTLV>();
				}
				this.costList.add(cost);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_NETWORK_SPEC:
				log.finest("NetworkSpec SubTLV");
				this.networkSpec=new NetworkSpecSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER:
				log.finest("Power SubTLV");
				this.power=new PowerSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_SIZE:
				log.finest("StorageSize SubTLV");
				this.storageSize=new StorageSizeSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_INFO:
				log.finest("StorageINFO SubTLV");
				this.storageInfo=new StorageInfoSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME:
				VolumeSubTLV volume = new VolumeSubTLV(this.getTlv_bytes(), offset);
				log.finest("Volume SubTLV");
				if (volumeList==null){
					log.finest("Creating VolumeSubTLVList");
					volumeList=new LinkedList<VolumeSubTLV>();
				}
				this.volumeList.add(volume);
				break;
	
		}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.finest("No more SubTLVs in Storage TLV");
				fin=true;
			}

		}
	}

	
	public ResourceIDSubTLV getResourceIDSubTLV() {
		return resourceID;
	}

	public void setResourceIDSubTLV (ResourceIDSubTLV ResourceIDSubTLV) {
		this.resourceID = ResourceIDSubTLV;
	}
		
	public LocationSubTLV getLocationSubTLV() {
		return location;
	}

	public void setLocationSubTLV(LocationSubTLV LocationSubTLV) {
		this.location = LocationSubTLV;
	}
	
	public LinkedList<CostSubTLV> getCostList() {
		return costList;
	}

	public void setCostList(LinkedList<CostSubTLV> CostList) {
		this.costList = CostList;
	}
	
	public NetworkSpecSubTLV getNetworkSpecSubTLV() {
		return networkSpec;
	}

	public void setNetworkSpecSubTLV(NetworkSpecSubTLV NetworkSpecSubTLV) {
		this.networkSpec = NetworkSpecSubTLV;
	}

	public PowerSubTLV getPowerSubTLV() {
		return power;
	}

	public void setPowerSubTLV(PowerSubTLV PowerSubTLV) {
		this.power = PowerSubTLV;
	}
	
	public StorageSizeSubTLV getStorageSizeSubTLV() {
		return storageSize;
	}

	public void setStorageSizeSubTLV(StorageSizeSubTLV StorageSizeSubTLV) {
		this.storageSize = StorageSizeSubTLV;
	}
	
	public StorageInfoSubTLV getStorageInfoSubTLV() {
		return storageInfo;
	}

	public void setStorageInfoSubTLV(StorageInfoSubTLV StorageInfoSubTLV) {
		this.storageInfo= StorageInfoSubTLV;
	}

	public LinkedList<VolumeSubTLV> getVolumeList() {
		return volumeList;
	}

	public void setVolumeList(LinkedList<VolumeSubTLV> VolumeList) {
		this.volumeList = VolumeList;
	}
	
}