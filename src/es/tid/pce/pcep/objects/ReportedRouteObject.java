package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.rsvp.objects.subobjects.IPv4AddressRROSubobject;
import es.tid.rsvp.objects.subobjects.IPv6AddressRROSubobject;
import es.tid.rsvp.objects.subobjects.RROSubobject;
import es.tid.rsvp.objects.subobjects.SubObjectValues;

/**
 * PCEP Reported Route Object (RRO) as defined in RFC 5440
 * 
 * From RFC 5440 Section 7.10. Reported Route Object

   The RRO is exclusively carried within a PCReq message so as to report
   the route followed by a TE LSP for which a reoptimization is desired.

   The contents of this object are identical in encoding to the contents
   of the Route Record Object defined in [RFC3209], [RFC3473], and
   [RFC3477].  That is, the object is constructed from a series of sub-
   objects.  Any RSVP-TE RRO sub-object already defined or that could be
   defined in the future for use in the RSVP-TE RRO is acceptable in
   this object.

   The meanings of all of the sub-objects and fields in this object are
   identical to those defined for the RSVP-TE RRO.

   PCEP RRO sub-object types correspond to RSVP-TE RRO sub-object types.

   RRO Object-Class is 8.

   RRO Object-Type is 1.

 * 
 * From RFC 3209 4.4. Record Route Object

   Routes can be recorded via the RECORD_ROUTE object (RRO).
   Optionally, labels may also be recorded.  The Record Route Class is
   21.  Currently one C_Type is defined, Type 1 Record Route.  The
   RECORD_ROUTE object has the following format:

   Class = 21, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                        (Subobjects)                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Subobjects

         The contents of a RECORD_ROUTE object are a series of
         variable-length data items called subobjects.  The subobjects
         are defined in section 4.4.1 below.

   The RRO can be present in both RSVP Path and Resv messages.  If a
   Path message contains multiple RROs, only the first RRO is
   meaningful.  Subsequent RROs SHOULD be ignored and SHOULD NOT be
   propagated.  Similarly, if in a Resv message multiple RROs are
   encountered following a FILTER_SPEC before another FILTER_SPEC is
   encountered, only the first RRO is meaningful.  Subsequent RROs
   SHOULD be ignored and SHOULD NOT be propagated.

 * @author ogondio
 *
 */

public class ReportedRouteObject extends PCEPObject{
	
	private LinkedList<RROSubobject> rroSubObjectList;

	public ReportedRouteObject(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_RRO);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_RRO);
		rroSubObjectList=new LinkedList<RROSubobject>();
		
	}
	
	/**
	 * Constructs a Reported Route Object (RRO) from a sequence of bytes 
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 */
	public ReportedRouteObject(byte[] bytes, int offset) throws MalformedPCEPObjectException {
		super(bytes,offset);
		rroSubObjectList=new LinkedList<RROSubobject>();
		decode();
	}
	
	/**
	 * Encode Reported Route Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		for (int k=0; k<rroSubObjectList.size();k=k+1){
			rroSubObjectList.get(k).encode();			
			len=len+rroSubObjectList.get(k).getRrosolength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<rroSubObjectList.size(); k=k+1) {					
			System.arraycopy(rroSubObjectList.get(k).getSubobject_bytes(),0, this.object_bytes, pos, rroSubObjectList.get(k).getRrosolength());
			pos=pos+rroSubObjectList.get(k).getRrosolength();
		}					
	}

	/**
	 * Decode Reported Route Object
	 */
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (ObjectLength==4){
			fin=true;
		}
		while (!fin) {
			int subojectclass=RROSubobject.getType(this.getObject_bytes(), offset);
			int subojectlength=RROSubobject.getLength(this.getObject_bytes(), offset);
			switch(subojectclass) {
				case SubObjectValues.RRO_SUBOBJECT_IPV4ADDRESS:
					IPv4AddressRROSubobject sobjt4=new IPv4AddressRROSubobject(this.getObject_bytes(), offset);
					rroSubObjectList.add(sobjt4);
					break;
			
				case SubObjectValues.RRO_SUBOBJECT_IPV6ADDRESS:
					IPv6AddressRROSubobject sobjt6=new IPv6AddressRROSubobject(this.getObject_bytes(), offset);
					rroSubObjectList.add(sobjt6);
					break;		
				//FIXME: COMPLETAR!!!!!!!!!!!!!!!
//				case SubObjectValues.ERO_SUBOBJECT_ASNUMBER:
//					ASNumberEROSubobject sobjas=new ASNumberEROSubobject (this.getObject_bytes(), offset);
//					IROList.add(sobjas);
//					break;
//				
//				case SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID:
//					UnnumberIfIDEROSubobject subun=new UnnumberIfIDEROSubobject(this.getObject_bytes(), offset);
//					IROList.add(subun);
//					break;
//					
//				case SubObjectValues.ERO_SUBOBJECT_LABEL:
//					int ctype=LabelEROSubobject.getCType(this.getObject_bytes(), offset);
//					switch (ctype){
//					
//						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL:
//							GeneralizedLabelEROSubobject subgl=new GeneralizedLabelEROSubobject(this.getObject_bytes(), offset);
//							IROList.add(subgl);
//						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_WAVEBAND_LABEL:
//							WavebandLabelEROSubobject subwl=new WavebandLabelEROSubobject(this.getObject_bytes(), offset);
//							IROList.add(subwl);
//						default:
//							log.finest("ERO LABEL Subobject Ctype Unknown");
//							break;							
//					}
				default:
					log.finest("ERO Subobject Unknown");
					//FIXME What do we do??
					break;
			}
			offset=offset+subojectlength;
			if (offset>=ObjectLength){
				//No more subobjects in ERO
				fin=true;
			}
		}		
	}

}
