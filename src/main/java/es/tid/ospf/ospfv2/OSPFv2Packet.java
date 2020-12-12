package es.tid.ospf.ospfv2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Base class for OSPFv2 Packet.
 * 
 * RFC 2328  


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

public abstract class OSPFv2Packet implements OSPFv2Element{
	
	protected static final Logger log = LoggerFactory.getLogger("OSPFParser");
	
	
	private int version;
	private int type;
	private int length;
	private Inet4Address routerID;
	private Inet4Address areaID;
	protected byte[] bytes;
	
	public OSPFv2Packet(){
		this.version=2;
	}
	
	/**
	 * Decodes the header of a LSA
	 * @param bytes bytes
	 * @param offset offset
	 */
	public OSPFv2Packet(byte[] bytes, int offset){
		//Decoding OSPFv2 Packet
		this.version=bytes[offset]&0xFF;
		this.type=bytes[offset+1]&0xFF;
		this.length=((bytes[offset+2]&0xFF)<<8) |  (bytes[offset+3]&0xFF);
		this.bytes = new byte[this.length];
		byte[] ip=new byte[4]; 
		System.arraycopy(bytes,offset+4, ip, 0, 4);
		try {
			this.routerID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warn("ERROR IN routerID: "+e.toString());
		}
		System.arraycopy(bytes,offset+8, ip, 0, 4);
		try {
			this.areaID=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warn("ERROR in areaID: "+e.toString());
		}
		
		//FIXME: Faltan checksum, autype, authentication....
		//Por ahora, paso de ellos..
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
	}
	
	public void encodeOSPFV2PacketHeader(){
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
			try {
				this.routerID=(Inet4Address) Inet4Address.getByName("0.0.0.0");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.areaID!=null){
			System.arraycopy(this.areaID.getAddress(),0,this.bytes, 8, 4);	
		}else {
			this.bytes[8]=0;
			this.bytes[9]=0;
			this.bytes[10]=0;
			this.bytes[11]=0;
			try {
				this.areaID=(Inet4Address) Inet4Address.getByName("0.0.0.0");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaID == null) ? 0 : areaID.hashCode());
		result = prime * result + Arrays.hashCode(bytes);
		result = prime * result + length;
		result = prime * result + ((routerID == null) ? 0 : routerID.hashCode());
		result = prime * result + type;
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OSPFv2Packet other = (OSPFv2Packet) obj;
		if (areaID == null) {
			if (other.areaID != null)
				return false;
		} else if (!areaID.equals(other.areaID))
			return false;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		if (length != other.length)
			return false;
		if (routerID == null) {
			if (other.routerID != null)
				return false;
		} else if (!routerID.equals(other.routerID))
			return false;
		if (type != other.type)
			return false;
		if (version != other.version)
			return false;
		return true;
	}
	
	

}
