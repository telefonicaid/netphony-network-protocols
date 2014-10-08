package tid.pce.pcep.objects;

import java.util.LinkedList;
import java.util.Vector;

import tid.rsvp.objects.subobjects.ASNumberEROSubobject;
import tid.rsvp.objects.subobjects.*;

/**
 * <h1> Include Route Object as described in RFC 5440</h1>
 * 
 * <p> From RFC 5440 Section 7.12. Include Route Object</p>


   The IRO (Include Route Object) is optional and can be used to specify
   that the computed path MUST traverse a set of specified network
   elements.  The IRO MAY be carried within PCReq and PCRep messages.
   When carried within a PCRep message with the NO-PATH object, the IRO
   indicates the set of elements that cause the PCE to fail to find a
   path.

   IRO Object-Class is 10.

   IRO Object-Type is 1.

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                        (Sub-objects)                        //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                    Figure 17: IRO Body Format

   Sub-objects:  The IRO is made of sub-objects identical to the ones
      defined in [RFC3209], [RFC3473], and [RFC3477], where the IRO sub-
      object type is identical to the sub-object type defined in the
      related documents.

      The following sub-object types are supported.

          Type   Sub-object
           1     IPv4 prefix
           2     IPv6 prefix
           4     Unnumbered Interface ID
           32    Autonomous system number

   The L bit of such sub-object has no meaning within an IRO.

 * @author ogondio
 *
 */
public class IncludeRouteObject extends PCEPObject{
	
	private LinkedList<EROSubobject> IROList;
	
	public IncludeRouteObject(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_IRO);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_IRO);
		IROList = new LinkedList<EROSubobject>();
	}
	
	/**
	 * Constructs a new IRO (Include Route Object) from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public IncludeRouteObject(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		IROList = new LinkedList<EROSubobject>();
		decode();

	}

	//Encode and Decode
	
	/**
	 * Encode Explicit Route Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		for (int k=0; k<IROList.size();k=k+1){
			IROList.get(k).encode();			
			len=len+IROList.get(k).getErosolength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<IROList.size(); k=k+1) {					
			System.arraycopy(IROList.get(k).getSubobject_bytes(),0, this.object_bytes, pos, IROList.get(k).getErosolength());
			pos=pos+IROList.get(k).getErosolength();
		}				
	}

	/**
	 * Decodes Explicit Route Object
	 */
	public void decode() throws MalformedPCEPObjectException{
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (ObjectLength==4){
			fin=true;
		}
		while (!fin) {
			int subojectclass=EROSubobject.getType(this.getObject_bytes(), offset);
			int subojectlength=EROSubobject.getLength(this.getObject_bytes(), offset);
			switch(subojectclass) {
				case SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX:
					IPv4prefixEROSubobject sobjt4=new IPv4prefixEROSubobject(this.getObject_bytes(), offset);
					IROList.add(sobjt4);
					break;
			
				case SubObjectValues.ERO_SUBOBJECT_IPV6PREFIX:
					IPv6prefixEROSubobject sobjt6=new IPv6prefixEROSubobject(this.getObject_bytes(), offset);
					IROList.add(sobjt6);
					break;		
				
				case SubObjectValues.ERO_SUBOBJECT_ASNUMBER:
					ASNumberEROSubobject sobjas=new ASNumberEROSubobject (this.getObject_bytes(), offset);
					IROList.add(sobjas);
					break;
				
				case SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID:
					UnnumberIfIDEROSubobject subun=new UnnumberIfIDEROSubobject(this.getObject_bytes(), offset);
					IROList.add(subun);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_LABEL:
					int ctype=LabelEROSubobject.getCType(this.getObject_bytes(), offset);
					switch (ctype){
					
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL:
							GeneralizedLabelEROSubobject subgl=new GeneralizedLabelEROSubobject(this.getObject_bytes(), offset);
							IROList.add(subgl);
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_WAVEBAND_LABEL:
							WavebandLabelEROSubobject subwl=new WavebandLabelEROSubobject(this.getObject_bytes(), offset);
							IROList.add(subwl);
						default:
							log.info("ERO LABEL Subobject Ctype Unknown");
							break;							
					}
					break;
				default:
					log.info("ERO Subobject Unknown");
					//FIXME What do we do??
					break;
			}
			offset=offset+subojectlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
		
	}
	
	//Getters and setters
	
	//FIXME: Ver si es mejor Vector o LInkedList, y A�ADIR MAS METODOS UTILES, estos son escasos.
	
	public void addIROSubobject(EROSubobject eroso){
		IROList.add(eroso);
	}
	
	
	public void addEROSubobjectVector(Vector<EROSubobject> erosovec){
		IROList.addAll(erosovec);		
	}

	public LinkedList<EROSubobject> getIROList() {
		return IROList;
	}

	public void setIROList(LinkedList<EROSubobject> iROList) {		
		IROList = iROList;
	}
	
		
}
