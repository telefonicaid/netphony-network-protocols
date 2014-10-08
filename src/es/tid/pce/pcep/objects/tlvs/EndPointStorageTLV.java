package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;
import es.tid.pce.pcep.objects.tlvs.subtlvs.RequestedStorageSizeSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.RequestedVolumeSizeSubTLV;


/**
  * From GEYSERS
  *  
	Storage description – in this case the End-point description field includes the
	description of the characteristics of the requested storage and is structured in
	a set of TLVs.


   The format of the Storage END-POINT TLV object for is as follows:

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
public class EndPointStorageTLV extends PCEPTLV {
	
	
	public RequestedStorageSizeSubTLV requestedStorageSize;
	public RequestedVolumeSizeSubTLV requestedVolumeSize;
	
	
	public EndPointStorageTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_STORAGE);
		
	}
	
	public EndPointStorageTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		
		int length=0;
		
		if (requestedStorageSize!=null){
			requestedStorageSize.encode();
			length=length+requestedStorageSize.getTotalSubTLVLength();
		}
		
		if (requestedVolumeSize!=null){
			requestedVolumeSize.encode();
			length=length+requestedVolumeSize.getTotalSubTLVLength();
		}
				
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		if (requestedStorageSize!=null){
			System.arraycopy(requestedStorageSize.getSubTLV_bytes(),0,this.tlv_bytes,offset,requestedStorageSize.getTotalSubTLVLength());
			offset=offset+requestedStorageSize.getTotalSubTLVLength();
		}
		
		if (requestedVolumeSize!=null){
			System.arraycopy(requestedVolumeSize.getSubTLV_bytes(),0,this.tlv_bytes,offset,requestedVolumeSize.getTotalSubTLVLength());
			offset=offset+requestedVolumeSize.getTotalSubTLVLength();
		}
		
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
			log.info("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_STORAGE_SIZE:
				log.info("StorageSize is requested");
				this.requestedStorageSize=new RequestedStorageSizeSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_REQUESTED_VOLUME_SIZE:
				log.info("VolumeSize is requested");
				this.requestedVolumeSize=new RequestedVolumeSizeSubTLV(this.getTlv_bytes(), offset);
				break;
		
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				log.info("No more SubTLVs in Storage TLV");
				fin=true;
			}

		}
	}

	
	public RequestedStorageSizeSubTLV getRequestedStorageSizeSubTLV() {
		return requestedStorageSize;
	}

	public void setRequestedStorageSize (RequestedStorageSizeSubTLV RequestedStorageSizeSubTLV) {
		this.requestedStorageSize = RequestedStorageSizeSubTLV;
	}

	public RequestedVolumeSizeSubTLV getRequestedVolumeSizeSubTLV() {
		return requestedVolumeSize;
	}

	public void setRequestedVolumeSizeSubTLV(RequestedVolumeSizeSubTLV RequestedVolumeSizeSubTLV) {
		this.requestedVolumeSize = RequestedVolumeSizeSubTLV;
	}
	
}