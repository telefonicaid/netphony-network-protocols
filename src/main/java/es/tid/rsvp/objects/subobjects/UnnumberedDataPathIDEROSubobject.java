package es.tid.rsvp.objects.subobjects;

import es.tid.of.DataPathID;
import es.tid.protocol.commons.ByteHandler;

/** Unnumbered DataPath Interface (Based on unnumbered interfce in RFC 3477) (Section 4)
 * 
 *  A new subobject of the Explicit Route Object (ERO) is used to specify
   unnumbered links.  This subobject has the following format:
   
 *  0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |        DPID (8  bytes)        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        DPID (continued)                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |         DPID (continued)      |     InterfaceID (4bytes)      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    InterfaceID (continued)    |            Resvd              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Type is 59 (Unnumbered Datapath ID).  The Length is 16.

   The DPID is the identifier assigned to the link by the LSR
   specified by the DataPath ID.
 * 
 * @author b.jmgj
 *
 */
public class UnnumberedDataPathIDEROSubobject extends EROSubobject{

	public DataPathID dataPath;
	public long interfaceID;//32 bit Interface ID

	public long getInterfaceID() {
		return interfaceID;
	}

	public void setInterfaceID(long interfaceID) {
		this.interfaceID = interfaceID;
	}

	public UnnumberedDataPathIDEROSubobject(){
		super();
		this.erosolength=2+8+4+2; //header+dpid+interface+padding = 16 
		this.setType(SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_DATAPATH_ID);
	}

	public UnnumberedDataPathIDEROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}

	/**
	 * Encode Unnumbered DataPathID ERO Subobject
	 */
	public void encode(){
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		int offset=2; //subobject header =L+Type+Length
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(dataPath.getDataPathID()), 0, this.subobject_bytes, offset, 8);
		
		offset += 8;
		
		this.subobject_bytes[offset]=(byte)(interfaceID >>> 24);
		this.subobject_bytes[offset+1]=(byte)(interfaceID >>> 16 & 0xff);
		this.subobject_bytes[offset+2]=(byte)(interfaceID >>> 8 & 0xff);
		this.subobject_bytes[offset+3]=(byte)(interfaceID & 0xff);
		
	
		//System.arraycopy(interfaceID, 0, this.subobject_bytes, 10, 4);
	}

	/**
	 * Decode Unnumbered DataPathID ERO Subobject
	 */
	public void decode(){
		decodeSoHeader();
		int offset = 2;
		byte[] bytearray=new byte[8]; 
		System.arraycopy(this.subobject_bytes, offset, bytearray, 0, 8);
		dataPath=(DataPathID)DataPathID.getByNameBytes(bytearray);
		dataPath.setDataPathID(dataPath.getDataPathID().toUpperCase());
		offset += 8;
		interfaceID=0;
		for (int k = 0; k < 4; k++) {
			interfaceID = (interfaceID << 8) | (this.subobject_bytes[k+offset] & 0xff);
		}
	}

	public DataPathID getDataPath() {
		return dataPath;
	}
	public void setDataPath(DataPathID dataPath) {
		this.dataPath = dataPath;
	}
	public String toString(){
		return dataPath.toString()+"::"+interfaceID;
	}
}
