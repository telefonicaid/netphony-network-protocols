package es.tid.rsvp.objects.subobjects;

import java.util.logging.Logger;

import tid.pce.tedb.DataPathID;

/** 
 * 
 * @author b.mvas
 *
 */
public class DataPathIDEROSubobject extends EROSubobject{

	public DataPathID dataPath;

	public DataPathIDEROSubobject(){
		super();
		this.erosolength=25; 
		this.setType(SubObjectValues.ERO_SUBOBJECT_DATAPATH_ID);
	}

	public DataPathIDEROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}

	/**
	 * Encode DataPathID ERO Subobject
	 */
	public void encode(){
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		System.arraycopy(dataPath.getDataPathID().getBytes(), 0, this.subobject_bytes, 2, 23);
	}

	/**
	 * Decode DataPathID ERO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		byte[] dpid=new byte[23]; 
		System.arraycopy(this.subobject_bytes, 2, dpid, 0, 23);
		dataPath=(DataPathID)DataPathID.getByNameBytes(dpid);
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
