package es.tid.ospf.ospfv2;

import java.util.LinkedList;

import es.tid.ospf.ospfv2.lsa.LSA;
import es.tid.ospf.ospfv2.lsa.LSATypes;
import es.tid.ospf.ospfv2.lsa.MalformedOSPFLSAException;
import es.tid.ospf.ospfv2.lsa.OSPFTEv2LSA;

/**
 * A.3.5 The Link State Update packet

    Link State Update packets are OSPF packet type 4.  These packets
    implement the flooding of LSAs.  Each Link State Update packet
    carries a collection of LSAs one hop further from their origin.
    Several LSAs may be included in a single packet.

    Link State Update packets are multicast on those physical networks
    that support multicast/broadcast.  In order to make the flooding
    procedure reliable, flooded LSAs are acknowledged in Link State
    Acknowledgment packets.  If retransmission of certain LSAs is
    necessary, the retransmitted LSAs are always sent directly to the
    neighbor.  For more information on the reliable flooding of LSAs,
    consult Section 13.

        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |   Version #   |       4       |         Packet length         |
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
       |                            # LSAs                             |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                                                               |
       +-                                                            +-+
       |                             LSAs                              |
       +-                                                            +-+
       |                              ...                              |
 * @author ogondio
 *
 */

public class OSPFv2LinkStateUpdatePacket extends OSPFv2Packet{

	private LinkedList<LSA> LSAlist;
	
	public OSPFv2LinkStateUpdatePacket(){
		super();
		this.setType(OSPFPacketTypes.OSPFv2_LINK_STATE_UPDATE);
		this.LSAlist= new LinkedList<LSA>();
	}

	public OSPFv2LinkStateUpdatePacket(byte[] bytes, int offset){
		super(bytes,offset);

		this.LSAlist= new LinkedList<LSA>();
		//Codifico las LSAs
		int type= 0;
		int length_lsa= 0;
		offset = offset + 24;
		int num_lsa = (((bytes[offset]&0xFF)<<24) | (((bytes[offset+1]&0xFF)<<16)) |(((bytes[offset+2]&0xFF)<<8)) |  (bytes[offset+3]&0xFF) );

		offset=offset+4;
		for (int i=0;i<num_lsa;i++){
			type= LSA.getLStype(bytes, offset);
			length_lsa= LSA.getLSlength(bytes, offset);
		if (type == LSATypes.TYPE_10_OPAQUE_LSA){
		
			try {
				LSAlist.add(new OSPFTEv2LSA(bytes,offset));
			} catch (MalformedOSPFLSAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		offset = offset + length_lsa;

		}
	}
		
		//this.LSAlist= new LinkedList<LSA>();
		
	
	public void encode(){
		int len=28;//Header bytes+ 5 bytes of the number of LSAs
		long num_lsas=LSAlist.size();
		for (int i=0;i<num_lsas;++i){
			LSA lsa=LSAlist.get(i);
			lsa.encode();
			len+=lsa.getLength();
		}
		this.setLength(len);
		this.bytes=new byte[this.getLength()];
		this.encodeOSPFV2PacketHeader();
		this.bytes[24]=(byte)(num_lsas>>>24);
		this.bytes[25]=(byte)(num_lsas>>>16);
		this.bytes[26]=(byte)(num_lsas>>>8);
		this.bytes[27]=(byte)num_lsas;
		int offset=28;
		log.finest("num_lsas "+num_lsas);
	
		for (int i=0;i<num_lsas;++i){	
			LSA lsa=LSAlist.get(i);
			System.arraycopy(lsa.getLSAbytes(), 0, this.bytes, offset, lsa.getLength());
			
		}
//		log.finest("El mensaje es "+printHexString());
	}

	public LinkedList<LSA> getLSAlist() {
		return LSAlist;
	}

	public void setLSAlist(LinkedList<LSA> lSAlist) {
		LSAlist = lSAlist;
	}
	
	public String printHexString(){
		String ret="";
		for (int i=0;i<this.getLength();++i){
			if ((bytes[i]&0xFF)<=0x0F){
			ret=ret+"0";		
			}
			ret=ret+Integer.toHexString(bytes[i]&0xFF);
			
		}
		return ret;
	}

	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer(2000);		
		for (LSA lsa:LSAlist ){
			sb.append(lsa.toString());	
			sb.append("\r\n");	
		}
		return sb.toString();
	}
	
	
		
}
