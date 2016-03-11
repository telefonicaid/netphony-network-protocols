package es.tid.ospf.ospfv2.lsa;

import es.tid.ospf.ospfv2.lsa.tlv.LinkTLV;
import es.tid.ospf.ospfv2.lsa.tlv.MalformedOSPFTLVException;
import es.tid.ospf.ospfv2.lsa.tlv.OSPFTLV;
import es.tid.ospf.ospfv2.lsa.tlv.OSPFTLVTypes;
import es.tid.ospf.ospfv2.lsa.tlv.RouterAddressTLV;

/**
 * Represents a Inter AS TE v2 LSA. 
 * <a href="http://tools.ietf.org/html/rfc5392"> RFC 5392</a>. 
 * 
 * <P> For the advertisement of OSPFv2 inter-AS TE links, a new Opaque LSA,
   the Inter-AS-TE-v2 LSA, is defined in this document.  The
   Inter-AS-TE-v2 LSA has the same format as "Traffic Engineering LSA",
   which is defined in [OSPF-TE]. </P>
<P>
   The inter-AS TE link advertisement SHOULD be carried in a Type 10
   Opaque LSA [RFC5250] if the flooding scope is to be limited to within
   the single IGP area to which the ASBR belongs, or MAY be carried in a
   Type 11 Opaque LSA [RFC5250] if the information is intended to reach
   all routers (including area border routers, ASBRs, and PCEs) in the
   AS.  The choice between the use of a Type 10 (area-scoped) or Type 11
   (AS-scoped) Opaque LSA is an AS-wide policy choice, and configuration
   control of it SHOULD be provided in ASBR implementations that support
   the advertisement of inter-AS TE links.
<P>
   The Link State ID of an Opaque LSA as defined in [RFC5250] is divided
   into two parts.  One of them is the Opaque type (8-bit), the other is
   the Opaque ID (24-bit).  The value for the Opaque type of
   Inter-AS-TE-v2 LSA is 6 and has been assigned by IANA (see Section
   6.1).  The Opaque ID of the Inter-AS-TE-v2 LSA is an arbitrary value
   used to uniquely identify Traffic Engineering LSAs.  The Link State
   ID has no topological significance.
<P>
   The TLVs within the body of an Inter-AS-TE-v2 LSA have the same
   format as used in OSPF-TE.  The payload of the TLVs consists of one
   or more nested Type/Length/Value triplets.  New sub-TLVs specifically
   for inter-AS TE Link advertisement are described in Section 3.2.
 * 
 * @author ogondio
 *
 */
public class InterASTEv2LSA extends OpaqueLSA {
	
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
	public InterASTEv2LSA(){
		//Type 10 Opaque LSA by default
		//If type 11 is needed, use setLStype method from LSA
		this.setLStype(LSATypes.TYPE_10_OPAQUE_LSA);
		this.setOpaqueType(LSATypes.OPAQUE_TYPE_INTER_AS_TE_V2_LSA);
	}
	
	/**
	 * Construct from a byte array and a given offest
	 * @param bytes bytes
	 * @param offset offset 
	 * @throws MalformedOSPFLSAException Malformed OSPF LSA Exception
	 */
	public InterASTEv2LSA(byte[] bytes, int offset)throws MalformedOSPFLSAException{
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
	 * @throws MalformedOSPFLSAException  Malformed OSPF LSA Exception
	 */
	private void decode() throws MalformedOSPFLSAException{
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
	 * @param routerAddressTLV routerAddressTLV
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
	 * @param linkTLV linkTLV
	 */
	public void setLinkTLV(LinkTLV linkTLV) {
		this.linkTLV = linkTLV;
	}

	/**
	 * 
	 */
	public String toString(){
		StringBuffer sb=new StringBuffer(1000);		
		sb.append("Inter-AS-TE-LSA-v2: LSA Header: "+this.printHeader());
		sb.append("\r\n");
		if (routerAddressTLV!=null){		
			sb.append("RouterAddressTLV: ");
			sb.append(routerAddressTLV.toString());
		}
		else if (linkTLV!=null){
			sb.append("linkTLV: ");
			sb.append(linkTLV.toString());
		}else {
			sb.append("Empty!!");
		}
		return sb.toString();
	}
	
	public String printShort(){
		StringBuffer sb=new StringBuffer(1000);		
		sb.append("Inter-AS-TE-LSA-v2: ");
		if (routerAddressTLV!=null){		
			sb.append("RouterAddressTLV: ");
			sb.append(routerAddressTLV.toString());
		}
		else if (linkTLV!=null){
			sb.append("linkTLV: ");
			sb.append(linkTLV.printShort());
		}else {
			sb.append("Empty!!");
		}
		return sb.toString();
	}
	
	public boolean equals(Object lsaToCompare){
		 if (lsaToCompare == null) {
		        return false;
		    }
		    if (getClass() != lsaToCompare.getClass()) {
		        return false;
		    }
		    return super.equals(lsaToCompare);
		
	}
}
