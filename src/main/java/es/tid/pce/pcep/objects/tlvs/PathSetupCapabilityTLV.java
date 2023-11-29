package es.tid.pce.pcep.objects.tlvs;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * Path Setup Capability TLV (Type 34)
 * 
 * @author ogondio
 *
 */
public class PathSetupCapabilityTLV extends PCEPTLV {
	
	private LinkedList<Integer> pathSetupTypes;
	
	private SRCapabilityTLV srCapabilitySubTLV;
	
	//
//    0                   1                   2                   3
//    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//   |           Type (34)           |             Length            |
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//   |                           Reserved            |  Num of PSTs  |
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//   |     PST#1     |      ...      |     PST#N     |    Padding    |
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
//   |                                                               |
//   //               Optional sub-TLVs (variable)                  //
//   |                                                               |
//   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	
	public PathSetupCapabilityTLV() {
		this.TLVType=ObjectParameters.PCEP_TLV_PATH_SETUP_TYPE_CAPABILITY;
		pathSetupTypes= new LinkedList<Integer>();
	}

	public PathSetupCapabilityTLV(byte[] bytes, int offset) {
		super(bytes, offset);
		pathSetupTypes= new LinkedList<Integer>();
		decode();
	}

	
	public void encode() {
		int numTypes=pathSetupTypes.size();
		int len=4+numTypes;
		if ((len%4)!=0){
			//Padding must be done before adding the tlvs
			len=len+4-(len%4);
		}		
		if (srCapabilitySubTLV!=null) {
			srCapabilitySubTLV.encode();
			len+=srCapabilitySubTLV.getTotalTLVLength();
		}
		this.setTLVValueLength(len);
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);
		this.encodeHeader();
		int offset=4;
		this.getTlv_bytes()[offset]=0x00;
		this.getTlv_bytes()[offset+1]=0x00;
		this.getTlv_bytes()[offset+2]=0x00;
		offset+=3;
		ByteHandler.encode1byteInteger(numTypes, this.getTlv_bytes(), offset);
		offset+=1;
		for (int i=0;i<pathSetupTypes.size();++i) {
			ByteHandler.encode1byteInteger(pathSetupTypes.get(i).intValue(), this.getTlv_bytes(), offset);
			offset+=1;
		}
		int old_offset=offset;
		if ((offset%4)!=0){
			//Padding must be done!!
			offset=offset+4-(offset%4);
		}		
		for (int i=old_offset;i<offset;++i) {
			this.getTlv_bytes()[i]=0x00;
		}
		if (srCapabilitySubTLV!=null) {
			System.arraycopy(srCapabilitySubTLV.getTlv_bytes(), 0, this.getTlv_bytes(), offset, srCapabilitySubTLV.getTotalTLVLength());
		}

	}
	
	public void decode() {
		int offset=7;
		int numTypes=ByteHandler.decode1byteInteger(this.getTlv_bytes(), offset);
		offset+=1;
		for (int i=0;i<numTypes;++i) {
			int pathSetupType=ByteHandler.decode1byteInteger(this.getTlv_bytes(), offset);
			pathSetupTypes.add(Integer.valueOf(pathSetupType));
			offset+=1;
		}
		if ((offset%4)!=0){
			//skip padding
			offset=offset+4-(offset%4);
		}	
		boolean fin=false;
		if (offset>=this.getTotalTLVLength()){
			fin=true;
		}
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getTlv_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getTlv_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_TYPE_SR_CAPABILITY :
				try {
					srCapabilitySubTLV=new SRCapabilityTLV(this.getTlv_bytes(), offset);
				} catch (MalformedPCEPObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			default:
				log.debug("UNKNOWN TLV found");
				break;
			}
			offset=offset+tlvlength;
			if (offset>=this.getTotalTLVLength()){
				fin=true;
			}
		}
	}

	public LinkedList<Integer> getPathSetupTypes() {
		return pathSetupTypes;
	}

	public void setPathSetupTypes(LinkedList<Integer> pathSetupTypes) {
		this.pathSetupTypes = pathSetupTypes;		
	}

	public SRCapabilityTLV getSrCapabilitySubTLV() {
		return srCapabilitySubTLV;
	}

	public void setSrCapabilitySubTLV(SRCapabilityTLV srCapabilitySubTLV) {
		this.srCapabilitySubTLV = srCapabilitySubTLV;
	}
	
	

}
