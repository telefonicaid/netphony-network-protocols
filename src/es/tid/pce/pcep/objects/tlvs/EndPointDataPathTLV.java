package es.tid.pce.pcep.objects.tlvs;

import es.tid.of.DataPathID;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * 
 * @author b.mvas
 *
 */

public class EndPointDataPathTLV extends PCEPTLV {

	public DataPathID switchID;

	public EndPointDataPathTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_DATAPATHID); //PCEP_GENERALIZED_END_POINTS_TYPE_P2P);
	}

	public EndPointDataPathTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		//log.info("Encoding DataPathID EndPoint TLV");
		int length = 8; // 8 bytes from swithID

		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;

		//System.arraycopy(switchID, 0, this.tlv_bytes, offset, 8);
		System.arraycopy(ByteHandler.DataPathFormatStringtoByteArray(switchID.getDataPathID()), 0, this.tlv_bytes, offset, 8);
		//log.info("Tras Encode EndPointDataPathTLV:: "+switchID.toString());
	}

	/**
	 * Decode
	 */
	public void decode(){
		//log.info("Decoding DataPathID EndPoint TLV");

		int offset = 4;

		byte[] id=new byte[8]; 

		//log.info("ENCODE EndPointDataPathTLV; TLV Length: " + this.getTLVValueLength()+ " tlv_bytes.tostring "+this.tlv_bytes.toString()+" offset "+offset +" id "+id);

		System.arraycopy(this.tlv_bytes, offset, id, 0, 8);

		try {
			//switchID=id.toString();
			switchID.setDataPathID(ByteHandler.ByteDataPathToString(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//log.info("Tras Decode EndPointDataPathTLV:: "+switchID.toString());
	}


	/*
	 * GETTERS AND SETTERS
	 */


	public String getSwitchID() {
		return switchID.getDataPathID();
	}

	public void setSwitchID(String switchID) {
		this.switchID.setDataPathID(switchID);
	}
	
	public String toString(){
		return switchID.toString();
	}


}
