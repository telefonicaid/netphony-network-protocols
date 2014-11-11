package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.RequestedCPUsSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.RequestedDiskSpaceSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.RequestedMemorySubTLV;


/**
  * From GEYSERS
  *  
	Server description � in this case the End-point description field includes the
	description of the characteristics of the requested Server and is structured in
	a set of TLVs.


   The format of the Server END-POINT TLV object for is as follows:

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
public class EndPointServerTLV extends PCEPTLV {
	
	
	public RequestedCPUsSubTLV requestedCPUs;
	public RequestedMemorySubTLV requestedMemory;
	public RequestedDiskSpaceSubTLV requestedDiskSpace;
	
	
	public EndPointServerTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_SERVER);
		
	}
	
	public EndPointServerTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		
		int length=0;
		
		if (requestedCPUs!=null){
			requestedCPUs.encode();
			length=length+requestedCPUs.getTotalSubTLVLength();
		}
		
		if (requestedMemory!=null){
			requestedMemory.encode();
			length=length+requestedMemory.getTotalSubTLVLength();
		}
		
		if (requestedMemory!=null){
			requestedMemory.encode();
			length=length+requestedMemory.getTotalSubTLVLength();
		}
		
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		if (requestedCPUs!=null){
			System.arraycopy(requestedCPUs.getSubTLV_bytes(),0,this.tlv_bytes,offset,requestedCPUs.getTotalSubTLVLength());
			offset=offset+requestedCPUs.getTotalSubTLVLength();
		}
		
		if (requestedMemory!=null){
			System.arraycopy(requestedMemory.getSubTLV_bytes(),0,this.tlv_bytes,offset,requestedMemory.getTotalSubTLVLength());
			offset=offset+requestedMemory.getTotalSubTLVLength();
		}
		
		if (requestedDiskSpace!=null){
			System.arraycopy(requestedDiskSpace.getSubTLV_bytes(),0,this.tlv_bytes,offset,requestedDiskSpace.getTotalSubTLVLength());
			offset=offset+requestedDiskSpace.getTotalSubTLVLength();
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
			log.debug("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_CPU:
				log.debug("CPU is requested");
				this.requestedCPUs=new RequestedCPUsSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_MEMORY:
				log.debug("Memory is requested");
				this.requestedMemory=new RequestedMemorySubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_DISK_SPACE:
				log.debug("Disk Space is requested");
				this.requestedDiskSpace=new RequestedDiskSpaceSubTLV(this.getTlv_bytes(), offset);
				break;
				
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.debug("No more SubTLVs in Server TLV");
				fin=true;
			}

		}
	}

	
	public RequestedCPUsSubTLV getRequestedCPUsSubTLV() {
		return requestedCPUs;
	}

	public void setRequestedCPUsSubTLV(RequestedCPUsSubTLV RequestedCPUsSubTLV) {
		this.requestedCPUs = RequestedCPUsSubTLV;
	}

	
	public RequestedMemorySubTLV getRequestedMemorySubTLV() {
		return requestedMemory;
	}

	public void setRequestedMemorySubTLV(RequestedMemorySubTLV RequestedMemorySubTLV) {
		this.requestedMemory = RequestedMemorySubTLV;
	}
	
	public RequestedDiskSpaceSubTLV getRequestedDiskSpaceSubTLV() {
		return requestedDiskSpace;
	}

	public void setRequestedDiskSpaceSubTLV(RequestedDiskSpaceSubTLV RequestedDiskSpaceSubTLV) {
		this.requestedDiskSpace = RequestedDiskSpaceSubTLV;
	}

}