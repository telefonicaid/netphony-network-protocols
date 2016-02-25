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
	Storage description � is part of the Notification, it includes the
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

 
 * @author Alejandro Tovar de Due�as
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
		
		log.debug("Encoding Storage TLV");
				
	}

	
	public void decode() throws MalformedPCEPObjectException{
		log.debug("Decoding Storage EndPoint TLV");
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getTlv_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getTlv_bytes(), offset);
			log.debug("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_RESOURCE_ID:
				log.debug("Storage Resource ID");
				this.resourceID=new ResourceIDSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_LOCATION:
				log.debug("Location SubTLV");
				this.location=new LocationSubTLV(this.getTlv_bytes(), offset);
				break;
			
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_COST:
				CostSubTLV cost = new CostSubTLV(this.getTlv_bytes(), offset);
				log.debug("Cost SubTLV");
				if (costList==null){
					log.debug("Creating CostSubTLVList");
					costList=new LinkedList<CostSubTLV>();
				}
				this.costList.add(cost);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_NETWORK_SPEC:
				log.debug("NetworkSpec SubTLV");
				this.networkSpec=new NetworkSpecSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER:
				log.debug("Power SubTLV");
				this.power=new PowerSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_SIZE:
				log.debug("StorageSize SubTLV");
				this.storageSize=new StorageSizeSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_STORAGE_INFO:
				log.debug("StorageINFO SubTLV");
				this.storageInfo=new StorageInfoSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_VOLUME:
				VolumeSubTLV volume = new VolumeSubTLV(this.getTlv_bytes(), offset);
				log.debug("Volume SubTLV");
				if (volumeList==null){
					log.debug("Creating VolumeSubTLVList");
					volumeList=new LinkedList<VolumeSubTLV>();
				}
				this.volumeList.add(volume);
				break;
	
		}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.debug("No more SubTLVs in Storage TLV");
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

	public ResourceIDSubTLV getResourceID() {
		return resourceID;
	}

	public void setResourceID(ResourceIDSubTLV resourceID) {
		this.resourceID = resourceID;
	}

	public LocationSubTLV getLocation() {
		return location;
	}

	public void setLocation(LocationSubTLV location) {
		this.location = location;
	}

	public NetworkSpecSubTLV getNetworkSpec() {
		return networkSpec;
	}

	public void setNetworkSpec(NetworkSpecSubTLV networkSpec) {
		this.networkSpec = networkSpec;
	}

	public PowerSubTLV getPower() {
		return power;
	}

	public void setPower(PowerSubTLV power) {
		this.power = power;
	}

	public StorageSizeSubTLV getStorageSize() {
		return storageSize;
	}

	public void setStorageSize(StorageSizeSubTLV storageSize) {
		this.storageSize = storageSize;
	}

	public StorageInfoSubTLV getStorageInfo() {
		return storageInfo;
	}

	public void setStorageInfo(StorageInfoSubTLV storageInfo) {
		this.storageInfo = storageInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((costList == null) ? 0 : costList.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((networkSpec == null) ? 0 : networkSpec.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result
				+ ((resourceID == null) ? 0 : resourceID.hashCode());
		result = prime * result
				+ ((storageInfo == null) ? 0 : storageInfo.hashCode());
		result = prime * result
				+ ((storageSize == null) ? 0 : storageSize.hashCode());
		result = prime * result
				+ ((volumeList == null) ? 0 : volumeList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StorageTLV other = (StorageTLV) obj;
		if (costList == null) {
			if (other.costList != null)
				return false;
		} else if (!costList.equals(other.costList))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (networkSpec == null) {
			if (other.networkSpec != null)
				return false;
		} else if (!networkSpec.equals(other.networkSpec))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (resourceID == null) {
			if (other.resourceID != null)
				return false;
		} else if (!resourceID.equals(other.resourceID))
			return false;
		if (storageInfo == null) {
			if (other.storageInfo != null)
				return false;
		} else if (!storageInfo.equals(other.storageInfo))
			return false;
		if (storageSize == null) {
			if (other.storageSize != null)
				return false;
		} else if (!storageSize.equals(other.storageSize))
			return false;
		if (volumeList == null) {
			if (other.volumeList != null)
				return false;
		} else if (!volumeList.equals(other.volumeList))
			return false;
		return true;
	}
	
	
}