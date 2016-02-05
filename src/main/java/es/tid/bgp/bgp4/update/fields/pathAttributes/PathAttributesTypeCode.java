package es.tid.bgp.bgp4.update.fields.pathAttributes;
/**
 * RFC 4271 BGP-4
 * <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>.
 *
 * @author mcs
 *
 */
public class PathAttributesTypeCode {
	//ORIGIN (Type Code 1): rfc 4271
	public final static int PATH_ATTRIBUTE_TYPECODE_ORIGIN = 1;
	//AS_PATH (Type Code 2): rfc 4271
	public final static int PATH_ATTRIBUTE_TYPECODE_ASPATH = 2;
	//NEXT_HOP (Type Code 3): rfc 4271
	public final static int PATH_ATTRIBUTE_TYPECODE_NEXTHOP = 3;
	//MULTI_EXIT_DISC (Type Code 4): 
	public final static int PATH_ATTRIBUTE_TYPECODE_MULTI_EXIT_DISC = 4;
	//LOCAL_PREF (Type Code 5):
	public final static int PATH_ATTRIBUTE_TYPECODE_LOCAL_PREF = 5;
	//ATOMIC_AGGREGATE (Type Code 6):
	public final static int PATH_ATTRIBUTE_TYPECODE_ATOMIC_AGGREGATE=6;
	//AGGREGATOR (Type Code 7)
	public final static int PATH_ATTRIBUTE_TYPECODE_AGGREGATOR=7;
	//NLRI - MP_REACH_NLRI rfc4760
	public final static int PATH_ATTRIBUTE_TYPECODE_MP_REACH_NLRI=14;
	//NLRI - MP_UNREACH_NLRI rfc4760
	public final static int PATH_ATTRIBUTE_TYPECODE_MP_UN_REACH_NLRI=15;
	
	//LINKSTATE (Inventado!!!!!! no lo encuentro)
	//public final static int PATH_ATTRIBUTE_TYPECODE_LINKSTATE = 99;
	//29 	BGP-LS Attribute (TEMPORARY - registered 2014-03-11, expires 2016-03-11)
	//draft-ietf-idr-ls-distribution
	public final static int PATH_ATTRIBUTE_TYPECODE_BGP_LS_ATTRIBUTE = 29;
	
	//Para origin (rfc 4271)
	public final static int PATH_ATTRIBUTE_ORIGIN_IGP = 0;
	public final static int PATH_ATTRIBUTE_ORIGIN_EGP = 1;
	public final static int PATH_ATTRIBUTE_ORIGIN_INCOMPLETE = 2;
	//Para AS_PATH (rfc 4271)
	public final static int PATH_ATTRIBUTE_ASPATH_AS_SET = 1;
	public final static int PATH_ATTRIBUTE_ASPATH_AS_SEQUENCE = 2;
	
}
