package es.tid.ospf.ospfv2.lsa;

/**
 * LSA Types (<a href="http://www.ietf.org/rfc/rfc2328"> RFC 2328</a>).
 * 
 * 
 * @author ogondio
 *
 */

public class LSATypes {

	/**
	 * Router-LSAs.
	 * 
    Router-LSAs are the Type 1 LSAs.  Each router in an area originates
    a router-LSA.  The LSA describes the state and cost of the router's
    links (i.e., interfaces) to the area.  All of the router's links to
    the area must be described in a single router-LSA. 
	 */
	public static final int ROUTER_LSA=1;

	/**
	 * Network-LSAs.

    Network-LSAs are the Type 2 LSAs.  A network-LSA is originated for
    each broadcast and NBMA network in the area which supports two or
    more routers.  The network-LSA is originated by the network's
    Designated Router.  The LSA describes all routers attached to the
    network, including the Designated Router itself.  The LSA's Link
    State ID field lists the IP interface address of the Designated
    Router.
	 */
	public static final int NETWORK_LSA=2;
	
	/**
	 * Summary-LSA Type 3.

    Summary-LSAs are the Type 3 and 4 LSAs.  These LSAs are originated
    by area border routers. Summary-LSAs describe inter-area
    destinations.  For details concerning the construction of summary-
    LSAs, see Section 12.4.3.

    Type 3 summary-LSAs are used when the destination is an IP network.
    In this case the LSA's Link State ID field is an IP network number
    (if necessary, the Link State ID can also have one or more of the
    network's "host" bits set; see Appendix E for details). W
	 */
	public static final int TYPE_3_SUMMARY_LSA=3;
	
	/**
	 * Summary-LSA Type 4.
	 * 
	 * 	When the
    destination is an AS boundary router, a Type 4 summary-LSA is used,
    and the Link State ID field is the AS boundary router's OSPF Router
    ID.  (To see why it is necessary to advertise the location of each
    ASBR, consult Section 16.4.)  Other than the difference in the Link
    State ID field, the format of Type 3 and 4 summary-LSAs is
    identical.
 
	 */
	public static final int TYPE_4_SUMMARY_LSA=4;
	
	/**
	 *  AS-external-LSA.

    AS-external-LSAs are the Type 5 LSAs.  These LSAs are originated by
    AS boundary routers, and describe destinations external to the AS.
    For details concerning the construction of AS-external-LSAs, see
    Section 12.4.3.

    AS-external-LSAs usually describe a particular external destination.
    For these LSAs the Link State ID field specifies an IP network
    number (if necessary, the Link State ID can also have one or more of
    the network's "host" bits set; see Appendix E for details).  AS-
    external-LSAs are also used to describe a default route.  Default
    routes are used when no specific route exists to the destination.
    When describing a default route, the Link State ID is always set to
    DefaultDestination (0.0.0.0) and the Network Mask is set to 0.0.0.0.
	 */
	public static final int AS_EXTERNAL_LSA=5;
	
	/**
	 * Type 9 Opaque LSA.
	 * A value of 9 denotes a link-local scope.  Opaque LSAs with a
         link-local scope MUST NOT be flooded beyond the local
         (sub)network.
         */
	public static final int TYPE_9_OPAQUE_LSA=9;
	
/**
 *  Type 10 Opaque LSA.
 *  
 *  These advertisements MAY be used directly by OSPF or indirectly by some
   application wishing to distribute information throughout the OSPF
   domain.  The function of the Opaque LSA option is to provide for
   future OSPF extensibility.
      o  A value of 10 denotes an area-local scope.  Opaque LSAs with an
         area-local scope MUST NOT be flooded beyond their area of
         origin.
         */
	
	public static final int TYPE_10_OPAQUE_LSA=10;

	/**
	 * Type 11 Opaque LSA.
      o  A value of 11 denotes that the LSA is flooded throughout the
         Autonomous System (e.g., has the same scope as type-5 LSAs).
         Opaque LSAs with AS-wide scope MUST NOT be flooded into stub
         areas or NSSAs.

	 */
	public static final int TYPE_11_OPAQUE_LSA=11;
	
	//OPAQUE LSA TYPES
	/**
	 *  The Traffic Engineering LSA uses type 1.
	 */
	public static final int OPAQUE_TYPE_OSPF_TE_V2_LSA=1;
	/**
	 *  Inter-AS-TE-v2 Opaque LSA
	 *  The value for the Opaque type of
   Inter-AS-TE-v2 LSA is 6 and has been assigned by IANA 
	 */
	public static final int OPAQUE_TYPE_INTER_AS_TE_V2_LSA=6;
	
	
}
