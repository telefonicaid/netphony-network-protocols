package tid.ospf.ospfv2.lsa;


/**
 * Opaque LSA (<a href="http://www.ietf.org/rfc/rfc5250"> RFC 5250</a>).
 * 
 * <P>Opaque LSAs provide a generalized mechanism to allow for the future
   extensibility of OSPF.  The information contained in Opaque LSAs may
   be used directly by OSPF or indirectly by some application wishing to
   distribute information throughout the OSPF domain.  The exact use of
   Opaque LSAs is beyond the scope of this document.</P>

   <P>Opaque LSAs consist of a standard LSA header followed by a 32-bit
   aligned application-specific information field.  Like any other LSA,
   the Opaque LSA uses the link-state database distribution mechanism
   for flooding this information throughout the topology.  The link-
   state type field of the Opaque LSA identifies the LSA's range of
   topological distribution.  This range is referred to as the flooding
   scope.</P>

   <P>It is envisioned that an implementation of the Opaque option provides
   an application interface for 1) encapsulating application-specific
   information in a specific Opaque type, 2) sending and receiving
   application-specific information, and 3) if required, informing the
   application of the change in validity of previously received
   information when topological changes are detected.</P>
   
   <P>Opaque LSAs are types 9, 10, and 11 link state advertisements.
   Opaque LSAs consist of a standard LSA header followed by a 32-bit
   aligned application-specific information field.  Standard link-state
   database flooding mechanisms are used for distribution of Opaque
   LSAs.  The range of topological distribution (i.e., the flooding
   scope) of an Opaque LSA is identified by its link-state type.  This
   section documents the flooding of Opaque LSAs.</P>

   <P>The flooding scope associated with each Opaque link-state type is
   defined as follows.<UL>

   <li>Link-state type-9 denotes a link-local scope.  Type-9 Opaque LSAs
      are not flooded beyond the local (sub)network.</li>

   <li>  Link-state type-10 denotes an area-local scope.  Type-10 Opaque
      LSAs are not flooded beyond the borders of their associated area.</li>

   <li>  Link-state type-11 denotes that the LSA is flooded throughout the
      Autonomous System (AS).  The flooding scope of type-11 LSAs are
      equivalent to the flooding scope of AS-External (type-5) LSAs.
      Specifically, type-11 Opaque LSAs are 1) flooded throughout all
      transit areas, 2) not flooded into stub areas or Not-So-Stubby
      Areas (NSSAs), see [NSSA], from the backbone, and 3) not
      originated by routers into their connected stub areas or NSSAs.
      As with type-5 LSAs, if a type-11 Opaque LSA is received in a stub
      area or NSSA from a neighboring router within the stub area or
      NSSA, the LSA is rejected.</li>
      
      </UL></P>
      
      <p>The link-state ID of the Opaque LSA is divided into an Opaque type
   field (the first 8 bits) and a type-specific ID (the remaining 24
   bits).  </P>
   <pre>
     0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |            LS age             |     Options   |  9, 10, or 11 |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |  Opaque Type  |               Opaque ID                       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                      Advertising Router                       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                      LS Sequence Number                       |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |         LS checksum           |           Length              |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      +                                                               +
      |                      Opaque Information                       |
      +                                                               +
      |                              ...                              |
   </pre>

 * 
 * @author ogondio
 *
 */
public abstract class OpaqueLSA extends LSA{
	
	/**
	 * 8 bit Opaque Type
	 */
	private int opaqueType;
	
	/**
	 * 24 bit Opaque Id
	 */
	private int opaqueId;
	
	/**
	 * Default Constructor
	 */
	public OpaqueLSA(){
		
	} 
	
	/**
	 * Create opaque LSA from a bunch of bytes
	 * @param bytes
	 * @param offset
	 * @throws MalformedOSPFLSAException
	 */
	public OpaqueLSA(byte[] bytes, int offset)throws MalformedOSPFLSAException{
		super(bytes,offset);
		opaqueType=this.LSAbytes[4]&0xFF;	
		opaqueId=0;
		opaqueId=opaqueId|(this.LSAbytes[5]&0xFF)<<16|(this.LSAbytes[6]&0xFF)<<8|(this.LSAbytes[7]&0xFF);		
	}

	public int getOpaqueType() {
		return opaqueType;
	}

	public void setOpaqueType(int opaqueType) {
		this.opaqueType = opaqueType;
	}

	public int getOpaqueId() {
		return opaqueId;
	}

	public void setOpaqueId(int opaqueId) {
		this.opaqueId = opaqueId;
	}
	
	public void encodeLSAHeader(){
		super.encodeLSAHeader();
		this.LSAbytes[4]=(byte)(this.opaqueType&0xFF);
	}
	
	public boolean equals (Object object){
		return super.equals(object);
	}
	
	

	

}
