package es.tid.bgp.bgp4.update.fields.pathAttributes;

/**
 * BGP Path Attributes Type Codes
 * Based on IANA Assignments and RFC 4271
 * Updated to 2026 standards
 * * https://www.iana.org/assignments/bgp-parameters/bgp-parameters.xhtml
 */
public class PathAttributesTypeCode {

    // ORIGIN (Type Code 1): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_ORIGIN = 1;

    // AS_PATH (Type Code 2): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_ASPATH = 2;

    // NEXT_HOP (Type Code 3): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_NEXTHOP = 3;

    // MULTI_EXIT_DISC (Type Code 4): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_MULTI_EXIT_DISC = 4;

    // LOCAL_PREF (Type Code 5): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_LOCAL_PREF = 5;

    // ATOMIC_AGGREGATE (Type Code 6): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_ATOMIC_AGGREGATE = 6;

    // AGGREGATOR (Type Code 7): RFC 4271
    public final static int PATH_ATTRIBUTE_TYPECODE_AGGREGATOR = 7;

    // COMMUNITIES (Type Code 8): RFC 1997
    public final static int PATH_ATTRIBUTE_TYPECODE_COMMUNITIES = 8;

    // ORIGINATOR_ID (Type Code 9): RFC 4456
    public final static int PATH_ATTRIBUTE_TYPECODE_ORIGINATOR_ID = 9;

    // CLUSTER_LIST (Type Code 10): RFC 4456
    public final static int PATH_ATTRIBUTE_TYPECODE_CLUSTER_LIST = 10;

    // MP_REACH_NLRI (Type Code 14): RFC 4760
    public final static int PATH_ATTRIBUTE_TYPECODE_MP_REACH_NLRI = 14;

    // MP_UNREACH_NLRI (Type Code 15): RFC 4760
    public final static int PATH_ATTRIBUTE_TYPECODE_MP_UN_REACH_NLRI = 15;

    // EXTENDED_COMMUNITIES (Type Code 16): RFC 4360
    public final static int PATH_ATTRIBUTE_TYPECODE_EXTENDED_COMMUNITIES = 16;

    // AS4_PATH (Type Code 17): RFC 6793
    public final static int PATH_ATTRIBUTE_TYPECODE_AS4PATH = 17;

    // AS4_AGGREGATOR (Type Code 18): RFC 6793
    public final static int PATH_ATTRIBUTE_TYPECODE_AS4AGGREGATOR = 18;

    // PMSI_TUNNEL (Type Code 22): RFC 6514
    public final static int PATH_ATTRIBUTE_TYPECODE_PMSI_TUNNEL = 22;

    // TUNNEL_ENCAPSULATION (Type Code 23): RFC 9012
    public final static int PATH_ATTRIBUTE_TYPECODE_TUNNEL_ENCAP = 23;

    // TRAFFIC_ENGINEERING (Type Code 24): RFC 5543
    public final static int PATH_ATTRIBUTE_TYPECODE_TRAFFIC_ENGINEERING = 24;

    // IPV6_SPECIFIC_EXTENDED_COMMUNITY (Type Code 25): RFC 5701
    public final static int PATH_ATTRIBUTE_TYPECODE_IPV6_EXTENDED_COMMUNITIES = 25;

    // AIGP (Type Code 26): RFC 7311
    public final static int PATH_ATTRIBUTE_TYPECODE_AIGP = 26;

    // PE_DISTINGUISHER_LABELS (Type Code 27): RFC 6514
    public final static int PATH_ATTRIBUTE_TYPECODE_PE_DISTINGUISHER_LABELS = 27;

    // BGP-LS Attribute (Type Code 29): RFC 9552
    public final static int PATH_ATTRIBUTE_TYPECODE_BGP_LS_ATTRIBUTE = 29;
    public final static int PATH_ATTRIBUTE_TYPECODE_BGP_LS_ATTRIBUTE_LEGACY = 99;

    // LARGE_COMMUNITY (Type Code 32): RFC 8092
    public final static int PATH_ATTRIBUTE_TYPECODE_LARGE_COMMUNITY = 32;

    // BGPSEC_PATH (Type Code 33): RFC 8205
    public final static int PATH_ATTRIBUTE_TYPECODE_BGPSEC_PATH = 33;

    // BGP_COMMUNITY_CONTAINER (Type Code 34): draft-ietf-idr-wide-bgp-communities
    public final static int PATH_ATTRIBUTE_TYPECODE_COMMUNITY_CONTAINER = 34;

    // ONLY_TO_CUSTOMER (Type Code 35): RFC 9234
    public final static int PATH_ATTRIBUTE_TYPECODE_OTC = 35;

    // BGP_DOMAIN_PATH (Type Code 36): draft-ietf-bess-evpn-ipvpn-interworking
    public final static int PATH_ATTRIBUTE_TYPECODE_DPATH = 36;

    // SFP_ATTRIBUTE (Type Code 37): RFC 9015
    public final static int PATH_ATTRIBUTE_TYPECODE_SFP = 37;

    // BFD_DISCRIMINATOR (Type Code 38): RFC 9026
    public final static int PATH_ATTRIBUTE_TYPECODE_BFD_DISCRIMINATOR = 38;

    // BGP_NEXT_HOP_DEPENDENT_CHARACTERISTIC (Type Code 39)
    public final static int PATH_ATTRIBUTE_TYPECODE_NHC = 39;

    // BGP_PREFIX_SID (Type Code 40): RFC 8669
    public final static int PATH_ATTRIBUTE_TYPECODE_BGP_PREFIX_SID = 40;

    // BIER (Type Code 41): RFC 9793
    public final static int PATH_ATTRIBUTE_TYPECODE_BIER = 41;

    // EDGE_METADATA (Type Code 42): draft-ietf-idr-5g-edge-service-metadata
    public final static int PATH_ATTRIBUTE_TYPECODE_EDGE_METADATA = 42;

    // ATTR_SET (Type Code 128): RFC 6368
    public final static int PATH_ATTRIBUTE_TYPECODE_ATTR_SET = 128;

    // RESERVED_FOR_DEVELOPMENT (Type Code 255): RFC 2042
    public final static int PATH_ATTRIBUTE_TYPECODE_RESERVED_DEVELOPMENT = 255;

    // --- Sub-codes & values ---

    // Para ORIGIN (RFC 4271)
    public final static int PATH_ATTRIBUTE_ORIGIN_IGP = 0;
    public final static int PATH_ATTRIBUTE_ORIGIN_EGP = 1;
    public final static int PATH_ATTRIBUTE_ORIGIN_INCOMPLETE = 2;

    // Para AS_PATH (RFC 4271)
    public final static int PATH_ATTRIBUTE_ASPATH_AS_SET = 1;
    public final static int PATH_ATTRIBUTE_ASPATH_AS_SEQUENCE = 2;
}