package tid.ospf.ospfv2;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for OSPFv2 Packet
 * RFC 2328                     OSPF Version 2                   April 1998


A.3 OSPF Packet Formats

    There are five distinct OSPF packet types.  All OSPF packet types
    begin with a standard 24 byte header.  This header is described
    first.  Each packet type is then described in a succeeding section.
    In these sections each packet's division into fields is displayed,
    and then the field definitions are enumerated.

    All OSPF packet types (other than the OSPF Hello packets) deal with
    lists of LSAs.  For example, Link State Update packets implement the
    flooding of LSAs throughout the OSPF routing domain.  Because of
    this, OSPF protocol packets cannot be parsed unless the format of
    LSAs is also understood.  The format of LSAs is described in Section
    A.4.

    The receive processing of OSPF packets is detailed in Section 8.2.
    The sending of OSPF packets is explained in Section 8.1.

A.3.1 The OSPF packet header

    Every OSPF packet starts with a standard 24 byte header.  This
    header contains all the information necessary to determine whether
    the packet should be accepted for further processing.  This
    determination is described in Section 8.2 of the specification.


        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |   Version #   |     Type      |         Packet length         |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                          Router ID                            |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                           Area ID                             |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |           Checksum            |             AuType            |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                       Authentication                          |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                       Authentication                          |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+



    Version #
        The OSPF version number.  This specification documents version 2
        of the protocol.

    Type
        The OSPF packet types are as follows. See Sections A.3.2 through
        A.3.6 for details.
                          Type   Description
                          ________________________________
                          1      Hello
                          2      Database Description
                          3      Link State Request
                          4      Link State Update
                          5      Link State Acknowledgment
  Packet length
        The length of the OSPF protocol packet in bytes.  This length
        includes the standard OSPF header.
 * @author ogondio
 * 
 */

public abstract class OSPFv2Packet {
	
	protected Logger log;
	
	
	private int version;
	private int type;
	private int length;
	private Inet4Address routerID;
	private Inet4Address areaID;
	protected byte[] bytes;
	
	public OSPFv2Packet(){
		log=Logger.getLogger("OSPFParser");
		this.version=2;
		log.setLevel(Level.SEVERE);
	}
	
	/**
	 * Decodes the header of a LSA
	 * @param bytes
	 * @param offset
	 */
	public OSPFv2Packet(byte[] bytes, int offset){
		log=Logger.getLogger("OSPFParser");
		log.finest("Decoding OSPFv2 Packet");
		this.version=bytes[offset]&0xFF;
		this.type=bytes[offset+1]&0xFF;
		this.length=((bytes[offset+2]&0xFF)<<8) |  (bytes[offset+3]&0xFF);
		this.bytes = new byte[this.length];
		byte[] ip=new byte[4]; 
		System.arraycopy(bytes,offset+4, ip, 0, 4);
		try {
			this.routerID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warning("ERROR IN routerID: "+e.toString());
		}
		System.arraycopy(bytes,offset+8, ip, 0, 4);
		try {
			this.areaID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warning("ERROR in areaID: "+e.toString());
		}
		
		//FIXME: Faltan checksum, autype, authentication....
		//Por ahora, paso de ellos..
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
	}
	
	/**
	 * 
	 */
	public void encodeOSPFV2PacketHeader(){
		log.info("version: "+version);
		this.bytes[0]=(byte)version;
		this.bytes[1]=(byte)type;
		this.bytes[2]=(byte)(this.length>>>8);
		this.bytes[3]=(byte)this.length;	
	
		if (this.routerID!=null){
			System.arraycopy(this.routerID.getAddress(),0, this.bytes, 4, 4);	
		}else {
			this.bytes[4]=0;
			this.bytes[5]=0;
			this.bytes[6]=0;
			this.bytes[7]=0;
		}
		if (this.areaID!=null){
			System.arraycopy(this.areaID.getAddress(),0,this.bytes, 8, 4);	
		}else {
			this.bytes[8]=0;
			this.bytes[9]=0;
			this.bytes[10]=0;
			this.bytes[11]=0;
		}
		
		this.bytes[12]=0;
		this.bytes[13]=0;
		this.bytes[14]=0;
		this.bytes[15]=0;	
		
		this.bytes[16]=0;
		this.bytes[17]=0;
		this.bytes[18]=0;
		this.bytes[19]=0;	
		
		this.bytes[20]=0;
		this.bytes[21]=0;
		this.bytes[22]=0;
		this.bytes[23]=0;	
			
	}
	/**
	 * Metodo statico: lenght y type.
	 * 
	 */
	public static int getLStype(byte[] bytes, int offset){
		return bytes[offset+1]&0xFF;
	}

	public static int getLSlength(byte[] bytes, int offset){
		int length;
		length=((bytes[offset+2]&0xFF)<<8) |  (bytes[offset+3]&0xFF);
		 return length;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Inet4Address getRouterID() {
		return routerID;
	}

	public void setRouterID(Inet4Address routerID) {
		this.routerID = routerID;
	}

	public Inet4Address getAreaID() {
		return areaID;
	}

	public void setAreaID(Inet4Address areaID) {
		this.areaID = areaID;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	

}
