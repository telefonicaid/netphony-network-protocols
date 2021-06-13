package es.tid.rsvp.objects.subobjects;

/**
 * Subobject Values for Explicit Routing Object subobjects from RFC 3209, RFC 3473, RFC 3477
 * Subobject Values for Record Routing Object subobjects from RFC 3209
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */

public class SubObjectValues {
	public static final int ERO_SUBOBJECT_IPV4PREFIX=0x01;//From RFC 3209
	public static final int ERO_SUBOBJECT_IPV6PREFIX=0x02;//From RFC 3209
	public static final int ERO_SUBOBJECT_LABEL=0x03;//From RFC 3473
	public static final int ERO_SUBOBJECT_UNNUMBERED_IF_ID=0x04;//From RFC 3477
	public static final int ERO_SUBOBJECT_ASNUMBER=32;//From RFC 3209 FIXME: CHECK IF 32 IS DECIMAL OR HEX	
	public static final int ERO_SUBOBJECT_SR_ERO=36; //From RFC 8664 PCEP ONLY
	public static final int ERO_SUBOBJECT_SWITCH_ID=55;
	public static final int ERO_SUBOBJECT_SWITCH_ID_EDGE=56;
	public static final int ERO_SUBOBJECT_SWITCH_ID_END=57;
	public static final int ERO_SUBOBJECT_DATAPATH_ID=58;
	public static final int ERO_SUBOBJECT_UNNUMBERED_DATAPATH_ID=59;
	
	public static final int ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL=0x02;//From RFC 3473 (section 2.3 and )
	public static final int ERO_SUBOBJECT_LABEL_CTYPE_WAVEBAND_LABEL=0x03;//From RFC 3473 (section 2.4 and )
	public static final int ERO_SUBOBJECT_LAYER_INFO = 40;//Invented
	public static final int ERO_SUBOBJECT_LABEL_CTYPE_OBS_MAINS_LABEL =0xFE;//Particular of MAINS OBS
	
	
	public static final int RRO_SUBOBJECT_IPV4ADDRESS=0x01;//From RFC 3209
	public static final int RRO_SUBOBJECT_IPV6ADDRESS=0x02;//From RFC 3209
	public static final int RRO_SUBOBJECT_LABEL=0x03;//From RFC 3473
	public static final int ERO_SUBOBJECT_UNNUMBERED_IF_ID_OPEN_FLOW = 92;
	public static final int ERO_SUBOBJECT_ID_OPEN_FLOW = 90;
	
	
	public static final int ERO_SUBOBJECT_ETC=10; 
}
