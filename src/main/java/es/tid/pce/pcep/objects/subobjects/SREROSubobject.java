package es.tid.pce.pcep.objects.subobjects;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.objects.subobjects.EROSubobject;
import es.tid.rsvp.objects.subobjects.SubObjectValues;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 
 *   An SR-ERO subobject consists of a 32-bit header followed by the SID
   and the NAI associated with the SID.  The SID is a 32-bit number.
   The size of the NAI depends on its respective type, as described in
   the following sections.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |  ST   |     Flags     |F|S|C|M|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                              SID                              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   //                        NAI (variable)                       //
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                     Figure 2: SR-ERO Subobject format

   The fields in the ERO Subobject are as follows:



Sivabalan, et al.        Expires April 19, 2014                [Page 10]
 
Internet-Draft     PCEP Extensions for Segment Routing      October 2013


   The 'L' Flag  indicates whether the subobject represents a loose-hop
      in the explicit route [RFC3209].  If this flag is unset, a PCC
      MUST not overwrite the SID value present in the SR-ERO subobject.
      Otherwise, a PCC MAY expand or replace one or more SID value(s) in
      the received SR-ERO based on its local policy.


   Type  is the type of the SR-ERO Subobject.  This document defines the
      SR-ERO Subobject type.  A new code point will be requested for the
      SR-ERO Subobject from IANA.


   Length  contains the total length of the subobject in octets,
      including the L, Type and Length fields.  Length MUST be at least
      4, and MUST be a multiple of 4.


   SID Type (ST)  indicates the type of information associated with the
      SID contained in the object body.  The SID-Type values are
      described later in this document.


   Flags  is used to carry any additional information pertaining to SID.
      Currently, the following flag bits are defined:



      *  M: When this bit is set, the SID value represents an MPLS label
         stack entry as specified in [RFC5462] where only the label
         value is specified by the PCE.  Other fields (TC, S, and TTL)
         fields MUST be considered invalid, and PCC MUST set these
         fields according to its local policy and MPLS forwarding rules.


      *  C: When this bit as well as the M bit are set, then the SID
         value represents an MPLS label stack entry as specified in
         [RFC5462], where all the entry's fields (Label, TC, S, and TTL)
         are specified by the PCE.  However, a PCC MAY choose to
         override TC, S, and TTL values according its local policy and
         MPLS forwarding rules.


      *  S: When this bit is set, the SID value in the subobject body is
         null.  In this case, the PCC is responsible for choosing the
         SID value, e.g., by looking up its Traffic Engineering Database
         (TED) using node/adjacency identifier in the subobject body.


      *  F: When this bit is set, the NAI value in the subobject body is
         null.


   SID  is the Segment Identifier.


   NAI  contains the NAI associated with the SID.  Depending on the
      value of ST, the NAI can have different format as described in the
      following section. 
 * 
 * @author Ayk
 * 
 */

public class SREROSubobject extends EROSubobject{

	public static final int ST_IPv4NodeID = 1;
	public static final int ST_IPv6NodeID = 2;
	public static final int ST_IPv4Adjacency = 3; 
	public static final int ST_IPv6Adjacency = 4;
	public static final int ST_UnnumberedAdjacencyIPv4NodeID = 5;
	
	public static final int length_IPv4NodeID = 12;
	public static final int length_IPv6NodeID = 24;
	public static final int length_IPv4Adjacency = 16; 
	public static final int length_IPv6Adjacency = 40;
	public static final int length_UnnumberedAdjacencyIPv4NodeID = 24;	
		
		
	protected byte ST;
	protected boolean fflag;
	protected boolean sflag;
	protected boolean cflag;
	protected boolean mflag;
	protected int SID;


	
	public SREROSubobject(){
		//TODO: this will be variable in future updates
		erosolength = 8;
		fflag = true;
		sflag = false;
		cflag = false;
		mflag = false;
		loosehop = false;
		ST = 0;
		type = SubObjectValues.ERO_SUBOBJECT_SR_ERO;

	}
	
	public SREROSubobject(byte[] bytes, int offset) {
		//TODO: this will have variable size if the flag is variable
		erosolength=(int)bytes[offset+1];
		this.subobject_bytes=new byte[erosolength];
		System.arraycopy(bytes, offset, subobject_bytes, 0, erosolength);
		decode();
		
	}
	
	public void encode(){
		this.subobject_bytes=new byte[this.erosolength];
		//TODO: Cambiar esto..?
		if (loosehop){
			subobject_bytes[0]=(byte)(0x80 | (type & 0x7F));
		}
		else {
			subobject_bytes[0]=(byte)(type & 0x7F);
		}
		//TODO: ver si el length varia con los NAI
		subobject_bytes[1]=(byte)erosolength;
		subobject_bytes[2]=(byte)((ST & 0x0F) << 4);
		ByteHandler.BoolToBuffer(4 + 3 * 8,fflag,this.subobject_bytes);
		ByteHandler.BoolToBuffer(5 + 3 * 8,sflag,this.subobject_bytes);
		ByteHandler.BoolToBuffer(6 + 3 * 8,cflag,this.subobject_bytes);
		ByteHandler.BoolToBuffer(7 + 3 * 8,mflag,this.subobject_bytes);
		//SID
		
		this.subobject_bytes[4]=(byte)(SID >>> 24 & 0xff);
		this.subobject_bytes[5]=(byte)(SID >>> 16 & 0xff);
		this.subobject_bytes[6]=(byte)(SID >>> 8 & 0xff);
		this.subobject_bytes[7]=(byte)(SID & 0xff);	
		//TODO: los NAI?		
		
		
	}
	
	public void decode(){
		loosehop = (ByteHandler.easyCopy(0,0,this.subobject_bytes[0]) == 1);
		type=subobject_bytes[0]&0x7F;
		erosolength=(int)subobject_bytes[1];
		ST = (byte)((subobject_bytes[2] >> 4) & 0x0f);
		
		fflag = (ByteHandler.easyCopy(4,4,this.subobject_bytes[3]) == 1);	
		sflag = (ByteHandler.easyCopy(5,5,this.subobject_bytes[3]) == 1);	
		cflag = (ByteHandler.easyCopy(6,6,this.subobject_bytes[3]) == 1);	
		mflag = (ByteHandler.easyCopy(7,7,this.subobject_bytes[3]) == 1);			

		SID=0;
		for (int k = 0; k < 4; k++) {
			SID = (SID << 8) | (this.subobject_bytes[k+4] & 0xff);
		}		
		
	}
	

	public String toString()
	{
		StringBuffer sb=new StringBuffer(100);
		sb.append("<SR subobject");
		sb.append(" SID: "+SID);
		sb.append(" loosehop: "+loosehop);
		sb.append(" type: "+type);
		sb.append(" length: "+erosolength);
		sb.append(" ST: "+ST);
		sb.append(" flags: |f|="+fflag+" |s|="+sflag+" |c|="+cflag+" |m|="+mflag);
		sb.append(">");
		
		
		return sb.toString();
	}

	
	/*
	 * 
	 *   This document defines the following NAIs:

   'IPv4 Node ID'  is specified as an IPv4 address.  In this case, ST
      and Length are 1 and 12 respectively.


   'IPv6 Node ID'  is specified as an IPv6 address.  In this case, ST
      and Length are 2 and 24 respectively.


   'IPv4 Adjacency'  is specified as a pair of IPv4 addresses.  In this
      case, ST and Length are 3 and 16, respectively
      
      
   'IPv6 Adjacency'  is specified as a pair of IPv6 addresses.  In this
      case, ST and Length are 4 and 40 respectively
      
      
  'Unnumbered Adjacency with IPv4 NodeIDs'  is specified as a pair of
      Node ID / Interface ID tuples.  In this case, ST and Length are 5
      and 24 respectively
	 * 
	 */
	public void decodeNAI(){
		switch((int)this.ST) {
		case SREROSubobject.ST_IPv4NodeID:
			decodeIPv4NodeID();
			break;
		case SREROSubobject.ST_IPv6NodeID:
			decodeIPv6NodeID();
			break;		
		case SREROSubobject.ST_IPv4Adjacency:
			decodeIPv4Adjacency();;
			break;
		case SREROSubobject.ST_IPv6Adjacency:
			decodeIpv6Adjacency();
			break;
		case SREROSubobject.ST_UnnumberedAdjacencyIPv4NodeID:
			decodeUnnumberedAdjacencyIPv4NodeID();
			break;
		default:
			//TODO: Error??
			break;
		
		
		}
	}

	//TODO: this should be implemented
	public void decodeIPv4NodeID(){
	}
	public void decodeIPv6NodeID(){
		
	}
	public void decodeIPv4Adjacency(){
		
	}
	public void decodeIpv6Adjacency(){
		
	}
	public void decodeUnnumberedAdjacencyIPv4NodeID(){	
	}
	
	
	
	
	
	
	
	//GETTERS 
	
	public boolean isfFlag() 
	{
		return fflag;
	}
	
	public boolean iscFlag() 
	{
		return cflag;
	}
	
	
	public boolean issFlag() 
	{
		return sflag;
	}
	
	public boolean ismFlag() 
	{
		return mflag;
	}
	

	public boolean isloosehop() {
		return loosehop;
	}
	


	public int getType() {
		return type;
	}

	public int getSID() {
		return SID;
	}
	
	//SETTERS


	public void setFflag(boolean fFlag) 
	{
		this.fflag = fFlag;
	}

	public void setCflag(boolean cFlag) 
	{
		this.cflag = cFlag;
	}


	public void setSflag(boolean sFlag) 
	{
		this.sflag = sFlag;
	}

	public void setMflag(boolean mFlag) 
	{
		this.mflag = mFlag;
	}
	
	public void setSID(int SID)
	{
		this.SID = SID;
	}

	public byte getST() {
		return ST;
	}

	public void setST(byte sT) {
		ST = sT;
	}
	
	
	
}