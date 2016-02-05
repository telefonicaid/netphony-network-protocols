package es.tid.pce.pcep.objects.subobjects;

import es.tid.of.DataPathID;
import es.tid.protocol.commons.ByteHandler;

/** 
 * @author b.mvas , b.jmgj
 *
 */
public class DataPathIDXROSubobject extends XROSubobject{

	public DataPathID dataPath;
	
	public DataPathIDXROSubobject(){
		super();
		this.erosolength=2+8+2;//header+dpid+padding
		this.setType(XROSubObjectValues.XRO_SUBOBJECT_DATAPATH_ID);
	}
	
	public DataPathIDXROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Encode  DataPathID XRO Subobject
	 */
	public void encode(){
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		int offset=2; //subobject header =L+Type+Length
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(dataPath.getDataPathID()), 0, this.subobject_bytes, offset, 8);
		
	}

	/**
	 * Decode DataPathID XRO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		int offset = 2;
		byte[] bytearray=new byte[8]; 
		System.arraycopy(this.subobject_bytes, offset, bytearray, 0, 8);
		dataPath=(DataPathID)DataPathID.getByNameBytes(bytearray);
	}
	
	public DataPathID getDataPath() {
		return dataPath;
	}
	public void setDataPath(DataPathID dataPath) {
		this.dataPath = dataPath;
	}
	public String toString(){
		return dataPath.toString();
	}


}
