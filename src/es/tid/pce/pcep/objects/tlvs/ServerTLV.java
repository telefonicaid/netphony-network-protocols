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
  * From GEYSERS
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
				log.finest("NetworkSpec SubTLV");
				this.networkSpec=new NetworkSpecSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER:
				log.finest("Power SubTLV");
				this.power=new PowerSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_SERVER_STORAGE:
				log.finest("ServerStorage SubTLV");
				this.serverStorage=new ServerStorageSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_PROCESSOR:
				log.finest("Procesor SubTLV");
				ProcessorSubTLV processor = new ProcessorSubTLV(this.getTlv_bytes(), offset);
				if (processorList==null){
					log.finest("Creating ProcessorSubTLVList");
					processorList=new LinkedList<ProcessorSubTLV>();
				}
				this.processorList.add(processor);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MEMORY:
				log.finest("Memory SubTLV");
				this.memory = new MemorySubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_OS:
				log.finest("OperativeSystem SubTLV");
				this.operativeSystem = new OperativeSystemSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_APPLICATION:
				ApplicationSubTLV application = new ApplicationSubTLV(this.getTlv_bytes(), offset);
				log.finest("Application SubTLV");
				if (applicationList==null){
					log.finest("Creating VolumeSubTLVList");
					applicationList=new LinkedList<ApplicationSubTLV>();
				}
				this.applicationList.add(application);
				break;
	
		}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.finest("No more SubTLVs in Server TLV");
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
	
}