package es.tid.pce.pcep.objects.tlvs;

import java.util.LinkedList;

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
		this.setTLVValueLength(numTypes+4);
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
	}

	public LinkedList<Integer> getPathSetupTypes() {
		return pathSetupTypes;
	}

	public void setPathSetupTypes(LinkedList<Integer> pathSetupTypes) {
		this.pathSetupTypes = pathSetupTypes;		
	}
	
	

}
