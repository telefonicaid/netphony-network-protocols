package es.tid.pce.pcep.objects.tlvs;


import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ApplicationSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.CostSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.LocationSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.MemorySubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.NetworkSpecSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.OperativeSystemSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PowerSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ProcessorSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ResourceIDSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ServerStorageSubTLV;


/**
 * ServerTLV from GEYSERS.
 * Enconding: propietary from GEYSERS EU project.
 * TLV Type: 1012 (non-standard)
  * From GEYSERS
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
public class ServerTLV extends PCEPTLV {
	
	
	public ResourceIDSubTLV resourceID;
	public LocationSubTLV location;
	public LinkedList <CostSubTLV> costList;
	public NetworkSpecSubTLV networkSpec;
	public PowerSubTLV power;
	public ServerStorageSubTLV serverStorage;
	public LinkedList <ProcessorSubTLV> processorList;
	public MemorySubTLV memory;
	public OperativeSystemSubTLV operativeSystem;
	public LinkedList <ApplicationSubTLV> applicationList;
	
	public ServerTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_SERVER);
		
	}
	
	public ServerTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
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
		
		if (serverStorage!=null){
			serverStorage.encode();
			length=length+serverStorage.getTotalSubTLVLength();
		}
		
		if (processorList!=null){
			for (int i=0;i<processorList.size();++i){
				(processorList.get(i)).encode();
				length=length+(processorList.get(i)).getTotalSubTLVLength();
			}
		}
		
		if (memory!=null){
			memory.encode();
			length=length+memory.getTotalSubTLVLength();
		}
		
		if (operativeSystem!=null){
			operativeSystem.encode();
			length=length+operativeSystem.getTotalSubTLVLength();
		}
		
		if (applicationList!=null){
			for (int i=0;i<applicationList.size();++i){
				(applicationList.get(i)).encode();
				length=length+(applicationList.get(i)).getTotalSubTLVLength();
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
		
		if (serverStorage!=null){
			System.arraycopy(serverStorage.getSubTLV_bytes(),0,this.tlv_bytes,offset,serverStorage.getTotalSubTLVLength());
			offset=offset+serverStorage.getTotalSubTLVLength();
		}
		
		if (processorList!=null){
			for (int i=0;i<processorList.size();++i){
				System.arraycopy(processorList.get(i).getSubTLV_bytes(),0,this.tlv_bytes,offset,processorList.get(i).getTotalSubTLVLength());
				offset=offset+processorList.get(i).getTotalSubTLVLength();
			}
		}
		
		if (memory!=null){
			System.arraycopy(memory.getSubTLV_bytes(),0,this.tlv_bytes,offset,memory.getTotalSubTLVLength());
			offset=offset+memory.getTotalSubTLVLength();
		}
		
		if (operativeSystem!=null){
			System.arraycopy(operativeSystem.getSubTLV_bytes(),0,this.tlv_bytes,offset,operativeSystem.getTotalSubTLVLength());
			offset=offset+operativeSystem.getTotalSubTLVLength();
		}
		
		if (applicationList!=null){
			for (int i=0;i<applicationList.size();++i){
				System.arraycopy(applicationList.get(i).getSubTLV_bytes(),0,this.tlv_bytes,offset,applicationList.get(i).getTotalSubTLVLength());
				offset=offset+applicationList.get(i).getTotalSubTLVLength();
			}
		}
				
	}

	
	public void decode() throws MalformedPCEPObjectException{
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getTlv_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getTlv_bytes(), offset);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_RESOURCE_ID:
				this.resourceID=new ResourceIDSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_LOCATION:
				this.location=new LocationSubTLV(this.getTlv_bytes(), offset);
				break;
			
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_COST:
				CostSubTLV cost = new CostSubTLV(this.getTlv_bytes(), offset);
				if (costList==null){
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
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_SERVER_STORAGE:
				log.debug("ServerStorage SubTLV");
				this.serverStorage=new ServerStorageSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_PROCESSOR:
				log.debug("Procesor SubTLV");
				ProcessorSubTLV processor = new ProcessorSubTLV(this.getTlv_bytes(), offset);
				if (processorList==null){
					log.debug("Creating ProcessorSubTLVList");
					processorList=new LinkedList<ProcessorSubTLV>();
				}
				this.processorList.add(processor);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MEMORY:
				log.debug("Memory SubTLV");
				this.memory = new MemorySubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_OS:
				log.debug("OperativeSystem SubTLV");
				this.operativeSystem = new OperativeSystemSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_APPLICATION:
				ApplicationSubTLV application = new ApplicationSubTLV(this.getTlv_bytes(), offset);
				log.debug("Application SubTLV");
				if (applicationList==null){
					log.debug("Creating VolumeSubTLVList");
					applicationList=new LinkedList<ApplicationSubTLV>();
				}
				this.applicationList.add(application);
				break;
	
		}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.debug("No more SubTLVs in Server TLV");
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
	
	public ServerStorageSubTLV getServerStorageSubTLV() {
		return serverStorage;
	}

	public void setServerStorageSubTLV(ServerStorageSubTLV ServerStorageSubTLV) {
		this.serverStorage = ServerStorageSubTLV;
	}
		
	public LinkedList<ProcessorSubTLV> getProcessorList() {
		return processorList;
	}

	public void setProcessorList(LinkedList<ProcessorSubTLV> ProcessorList) {
		this.processorList = ProcessorList;
	}

	public MemorySubTLV getMemorySubTLV() {
		return memory;
	}

	public void setMemorySubTLV(MemorySubTLV MemorySubTLV) {
		this.memory= MemorySubTLV;
	}
	
	public OperativeSystemSubTLV getOperativeSystemSubTLV() {
		return operativeSystem;
	}

	public void setOperativeSystemSubTLV(OperativeSystemSubTLV OperativeSystemSubTLV) {
		this.operativeSystem= OperativeSystemSubTLV;
	}
	
	public LinkedList<ApplicationSubTLV> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(LinkedList<ApplicationSubTLV> ApplicationList) {
		this.applicationList = ApplicationList;
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

	public ServerStorageSubTLV getServerStorage() {
		return serverStorage;
	}

	public void setServerStorage(ServerStorageSubTLV serverStorage) {
		this.serverStorage = serverStorage;
	}

	public MemorySubTLV getMemory() {
		return memory;
	}

	public void setMemory(MemorySubTLV memory) {
		this.memory = memory;
	}

	public OperativeSystemSubTLV getOperativeSystem() {
		return operativeSystem;
	}

	public void setOperativeSystem(OperativeSystemSubTLV operativeSystem) {
		this.operativeSystem = operativeSystem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((applicationList == null) ? 0 : applicationList.hashCode());
		result = prime * result
				+ ((costList == null) ? 0 : costList.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result
				+ ((networkSpec == null) ? 0 : networkSpec.hashCode());
		result = prime * result
				+ ((operativeSystem == null) ? 0 : operativeSystem.hashCode());
		result = prime * result + ((power == null) ? 0 : power.hashCode());
		result = prime * result
				+ ((processorList == null) ? 0 : processorList.hashCode());
		result = prime * result
				+ ((resourceID == null) ? 0 : resourceID.hashCode());
		result = prime * result
				+ ((serverStorage == null) ? 0 : serverStorage.hashCode());
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
		ServerTLV other = (ServerTLV) obj;
		if (applicationList == null) {
			if (other.applicationList != null)
				return false;
		} else if (!applicationList.equals(other.applicationList))
			return false;
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
		if (memory == null) {
			if (other.memory != null)
				return false;
		} else if (!memory.equals(other.memory))
			return false;
		if (networkSpec == null) {
			if (other.networkSpec != null)
				return false;
		} else if (!networkSpec.equals(other.networkSpec))
			return false;
		if (operativeSystem == null) {
			if (other.operativeSystem != null)
				return false;
		} else if (!operativeSystem.equals(other.operativeSystem))
			return false;
		if (power == null) {
			if (other.power != null)
				return false;
		} else if (!power.equals(other.power))
			return false;
		if (processorList == null) {
			if (other.processorList != null)
				return false;
		} else if (!processorList.equals(other.processorList))
			return false;
		if (resourceID == null) {
			if (other.resourceID != null)
				return false;
		} else if (!resourceID.equals(other.resourceID))
			return false;
		if (serverStorage == null) {
			if (other.serverStorage != null)
				return false;
		} else if (!serverStorage.equals(other.serverStorage))
			return false;
		return true;
	}
	
	
	
}