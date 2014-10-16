package es.tid.pce.pcep.objects.subobjects;

/**
 * Subobject Values for Exclude Routing Object subobjects from RFC 5521.
 * 
 *       Type           Subobject
      -------------+-------------------------------
      1              IPv4 prefix
      2              IPv6 prefix
      4              Unnumbered Interface ID
      32             Autonomous system number
      34             SRLG

 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */
public class XROSubObjectValues {
	
	public static final int XRO_SUBOBJECT_IPV4PREFIX=0x01;//From RFC 5521
	public static final int XRO_SUBOBJECT_IPV6PREFIX=0x02;//From RFC 5521

	public static final int XRO_SUBOBJECT_UNNUMBERED_IF_ID=0x04;//From RFC 5521
	public static final int XRO_SUBOBJECT_ASNUMBER=32;//From RFC 5521
	public static final int XRO_SUBOBJECT_SRLG=34;//From RFC 5521
		
}
