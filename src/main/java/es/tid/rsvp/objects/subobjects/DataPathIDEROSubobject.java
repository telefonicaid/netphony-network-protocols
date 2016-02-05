package es.tid.rsvp.objects.subobjects;

import es.tid.of.DataPathID;
import es.tid.protocol.commons.ByteHandler;

/** 
 *  0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |        DPID (8  bytes)        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        DPID (continued)                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |         DPID (continued)      |            Resvd              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   
 * @author b.mvas, b.jmgj
 *
 */
public class DataPathIDEROSubobject extends EROSubobject{

	public DataPathID dataPath;

	public DataPathIDEROSubobject(){
		super();
		this.erosolength=12; 
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
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(dataPath.getDataPathID()), 0, this.subobject_bytes, 2, 8); // 2bytes header , 8 byts dpid
	}

	/**
	 * Decode DataPathID ERO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		byte[] dpid=new byte[23]; 
		System.arraycopy(this.subobject_bytes, 2, dpid, 0, 8);
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
