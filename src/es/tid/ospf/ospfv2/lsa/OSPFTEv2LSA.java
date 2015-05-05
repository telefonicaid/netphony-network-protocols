package es.tid.ospf.ospfv2.lsa;


import es.tid.ospf.ospfv2.lsa.tlv.LinkTLV;
import es.tid.ospf.ospfv2.lsa.tlv.MalformedOSPFTLVException;
import es.tid.ospf.ospfv2.lsa.tlv.OSPFTLV;
import es.tid.ospf.ospfv2.lsa.tlv.OSPFTLVTypes;
import es.tid.ospf.ospfv2.lsa.tlv.RouterAddressTLV;

/**
 * The LSA ID of an Opaque LSA is defined as having eight bits of type
   data and 24 bits of type-specific data.  The Traffic Engineering LSA
   uses type 1.  The remaining 24 bits are the Instance field, as
   follows:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |       1       |                   Instance                    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   The Instance field is an arbitrary value used to maintain multiple
   Traffic Engineering LSAs.  A maximum of 16777216 Traffic Engineering
   LSAs may be sourced by a single system.  The LSA ID has no
   topological significance.

2.3.  LSA Format Overview

2.3.1.  LSA Header

   The Traffic Engineering LSA starts with the standard LSA header:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |            LS age             |    Options    |      10       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |       1       |                   Instance                    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                     Advertising Router                        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                     LS sequence number                        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |         LS checksum           |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


 * @author mcs
 *
 */
public class OSPFTEv2LSA extends OpaqueLSA {
	/**
	 * Router Address TLV 
	 */
	private RouterAddressTLV routerAddressTLV;
	
	/**
	 * Link TLV
	 */
	private LinkTLV linkTLV;
	
	/**
	 * Default constructor
	 */
	public OSPFTEv2LSA(){
		//Type 10 Opaque LSA by default
		//If type 11 is needed, use setLStype method from LSA
		this.setLStype(LSATypes.TYPE_10_OPAQUE_LSA);
		this.setOpaqueType(LSATypes.OPAQUE_TYPE_OSPF_TE_V2_LSA);
	}
	
	/**
	 * Construct from a byte array and a given offest
	 * @param bytes
	 * @param offset
	 * @throws MalformedOSPFLSAException
	 */
	public OSPFTEv2LSA(byte[] bytes, int offset)throws MalformedOSPFLSAException{		
		super(bytes,offset);		
		decode();
	}
	
	/**
	 * Encode the Inter AS TE v2 LSA
	 */
	public void encode(){
		//Length of the LSA header
		int lsalength=20;
		if (routerAddressTLV!=null){
			routerAddressTLV.encode();
			lsalength=lsalength+routerAddressTLV.getTotalTLVLength();
		}
		if (linkTLV!=null){
			linkTLV.encode();
			lsalength=lsalength+linkTLV.getTotalTLVLength();
		}
		this.setLength(lsalength);
		this.LSAbytes=new byte[this.getLength()];
		this.encodeLSAHeader();
		int offset=20;
		if (routerAddressTLV!=null){
			System.arraycopy(routerAddressTLV.getTlv_bytes(),0,this.LSAbytes,offset,routerAddressTLV.getTotalTLVLength());
			offset=offset+routerAddressTLV.getTotalTLVLength();
		}
		if (linkTLV!=null){
			System.arraycopy(linkTLV.getTlv_bytes(),0,this.LSAbytes,offset,linkTLV.getTotalTLVLength());
			offset=offset+linkTLV.getTotalTLVLength();
		}		
	}
	
	/**
	 * Decode the Inter AS TE LSA 
	 * @throws MalformedOSPFLSAException
	 */
	private void decode() throws MalformedOSPFLSAException{
		//Decoding OSPFTEv2LSA
		boolean fin=false;
		int offset=20;//Position of the next subobject
		if (this.getLength()==20){
			//Empty LSA!!
			log.warn("Empty LSA");
			throw new MalformedOSPFLSAException();
		}
		while (!fin) {
			int TLVType=OSPFTLV.getType(this.LSAbytes, offset);
			int TLVLength=OSPFTLV.getTotalTLVLength(this.LSAbytes, offset);
			try {
				switch (TLVType){
				case OSPFTLVTypes.RouterAddressTLVType:
					this.routerAddressTLV=new RouterAddressTLV(this.LSAbytes, offset);
					break;
					
				case OSPFTLVTypes.LinkTLV:
					this.linkTLV=new LinkTLV(this.LSAbytes, offset);
					break;
				}
			} catch (MalformedOSPFTLVException e) {
				throw new MalformedOSPFLSAException();
			}
			offset=offset+TLVLength;
			if (offset>=this.getLength()){
				fin=true;
			}

		}
	}
	
	
	/**
	 * 
	 * @return routerAddressTLV
	 */
	public RouterAddressTLV getRouterAddressTLV() {
		return routerAddressTLV;
	}

	/**
	 * 
	 * @param routerAddressTLV
	 */
	public void setRouterAddressTLV(RouterAddressTLV routerAddressTLV) {
		this.routerAddressTLV = routerAddressTLV;
	}

	/**
	 * 
	 * @return linkTLV
	 */
	public LinkTLV getLinkTLV() {
		return linkTLV;
	}

	/**
	 * 
	 * @param linkTLV
	 */
	public void setLinkTLV(LinkTLV linkTLV) {
		this.linkTLV = linkTLV;
	}

	/**
	 * 
	 */
	public String toString(){
		String ret;
		if (routerAddressTLV!=null){
			ret=routerAddressTLV.toString();
		}
		else if (linkTLV!=null){
			ret=linkTLV.toString();
		}else {
			ret="";
		}
		return ret;
	}
	

}