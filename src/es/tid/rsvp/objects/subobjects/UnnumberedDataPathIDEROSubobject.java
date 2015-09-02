package es.tid.rsvp.objects.subobjects;

import es.tid.of.DataPathID;
import es.tid.protocol.commons.ByteHandler;

/** Unnumbered DataPath Interface (RFC 3477) (Section 4)
 * 
 *  A new subobject of the Explicit Route Object (ERO) is used to specify
   unnumbered links.  This subobject has the following format:

   Figure 2: Unnumbered Interface ID Subobject

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |    Reserved (MUST be zero)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        DPID (8bytes)                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        DPID (continued)                       |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                Interface ID (32 bits) (4bytes)                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Type is 50 (Unnumbered Unnumbered Datapath ID).  The Length is 12.

   The Interface ID is the identifier assigned to the link by the LSR
   specified by the router ID.
 * 
 * @author b.jmgj
 *
 */
public class UnnumberedDataPathIDEROSubobject extends EROSubobject{

	public DataPathID dataPath;
	public long interfaceID;//32 bit Interface ID

	public UnnumberedDataPathIDEROSubobject(){
		super();
		this.erosolength=16; 
		this.setType(SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_DATAPATH_ID);
	}

	public UnnumberedDataPathIDEROSubobject(byte[] bytes, int offset){
		super(bytes, offset);
		decode();		
	}

	/**
	 * Encode DataPathID ERO Subobject
	 */
	public void encode(){
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(dataPath.getDataPathID()), 0, this.subobject_bytes, 4, 8);
		System.arraycopy(interfaceID, 0, this.subobject_bytes, 12, 4);
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
