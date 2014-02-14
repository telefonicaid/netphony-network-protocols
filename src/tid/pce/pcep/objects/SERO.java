package tid.pce.pcep.objects;


import java.util.LinkedList;
import tid.rsvp.objects.subobjects.*;

/** SERO
 * The SERO is used to encode the path of a TE LSP through the network.
 * The SERO is carried within a PCRep message to provide the computed TE
 * LSP if the path computation was successful.
 *
 * The contents of this object are identical in encoding to the contents
 * of the Resource Reservation Protocol Traffic Engineering Extensions
 * (RSVP-TE) Explicit Route Object (SERO) defined in [RFC3209],
 * [RFC3473], and [RFC3477].  That is, the object is constructed from a
 * series of sub-objects.  Any RSVP-TE ERO sub-object already defined or
 * that could be defined in the future for use in the RSVP-TE ERO is
 * acceptable in this object.
 * PCEP ERO sub-object types correspond to RSVP-TE ERO sub-object types.
 *
 * Since the explicit path is available for immediate signaling by the
 * MPLS or GMPLS control plane, the meanings of all of the sub-objects
 * and fields in this object are identical to those defined for the ERO.
 *
 * SERO Object-Class is 7.
 *
 * SERO Object-Type is 1.
 *
 * RFC 3209:
 *   0                   1                   2                   3
 *    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *  |                                                               |
 *  //                        (Subobjects)                          //
 *  |                                                               |
 *  +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author Oscar Gonzalez de Dios (ogondio@tid.es), jaume
 */
public class SERO extends PCEPObject{
	
	public LinkedList<EROSubobject> EROSubobjectList;
	
	//Constructors

	public SERO() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ERO);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ERO);
		EROSubobjectList=new LinkedList<EROSubobject>();
	}
	
	/**
	 * Constructs a new ERO (Explicit Route Object) from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public SERO (byte []bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes, offset);
		EROSubobjectList=new LinkedList<EROSubobject>();
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode Explicit Route Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		for (int k=0; k<EROSubobjectList.size();k=k+1){
			EROSubobjectList.get(k).encode();			
			len=len+EROSubobjectList.get(k).getErosolength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<EROSubobjectList.size(); k=k+1) {					
			System.arraycopy(EROSubobjectList.get(k).getSubobject_bytes(),0, this.object_bytes, pos, EROSubobjectList.get(k).getErosolength());
			pos=pos+EROSubobjectList.get(k).getErosolength();
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
					this.addEROSubobject(sobjt4);
					break;
			
				case SubObjectValues.ERO_SUBOBJECT_IPV6PREFIX:
					IPv6prefixEROSubobject sobjt6=new IPv6prefixEROSubobject(this.getObject_bytes(), offset);
					addEROSubobject(sobjt6);
					break;		
				
				case SubObjectValues.ERO_SUBOBJECT_ASNUMBER:
					ASNumberEROSubobject sobjas=new ASNumberEROSubobject (this.getObject_bytes(), offset);
					addEROSubobject(sobjas);
					break;
				
				case SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID:
					UnnumberIfIDEROSubobject subun=new UnnumberIfIDEROSubobject(this.getObject_bytes(), offset);
					addEROSubobject(subun);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_LAYER_INFO:
					ServerLayerInfo sli =new ServerLayerInfo(this.getObject_bytes(), offset);
					addEROSubobject(sli);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_SWITCH_ID:
					SwitchIDEROSubobject macS =new SwitchIDEROSubobject(this.getObject_bytes(), offset);
					addEROSubobject(macS);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_SWITCH_ID_EDGE:
					SwitchIDEROSubobjectEnd macSE =new SwitchIDEROSubobjectEnd(this.getObject_bytes(), offset);
					addEROSubobject(macSE);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_SWITCH_ID_END:
					SwitchIDEROSubobjectEdge macSEnd =new SwitchIDEROSubobjectEdge(this.getObject_bytes(), offset);
					addEROSubobject(macSEnd);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_LABEL:
					int ctype=LabelEROSubobject.getCType(this.getObject_bytes(), offset);
					switch (ctype){
					
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL:
							GeneralizedLabelEROSubobject subgl=new GeneralizedLabelEROSubobject(this.getObject_bytes(), offset);
							addEROSubobject(subgl);
							break;
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_WAVEBAND_LABEL:
							WavebandLabelEROSubobject subwl=new WavebandLabelEROSubobject(this.getObject_bytes(), offset);
							addEROSubobject(subwl);
							break;
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_OBS_MAINS_LABEL :
							OBSMAINSLabelEROSubobject oles=new OBSMAINSLabelEROSubobject(this.getObject_bytes(), offset);
							addEROSubobject(oles);
							break;	
						default:
							log.warning("ERO LABEL Subobject Ctype Unknown");
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
	
	public void addEROSubobject(EROSubobject eroso){
		EROSubobjectList.add(eroso);
	}
	
	public void addEROSubobjectList(LinkedList<EROSubobject> erosovec){
		EROSubobjectList.addAll(erosovec);
	}

	public LinkedList<EROSubobject> getEROSubobjectList() {
		return EROSubobjectList;
	}

	public void setEROSubobjectList(LinkedList<EROSubobject> eROSubobjectList) {
		EROSubobjectList = eROSubobjectList;
	}
	

	public String toString(){
		StringBuffer sb=new StringBuffer(EROSubobjectList.size()*100);
		sb.append("<ERO: ");
		for (int i=0;i<EROSubobjectList.size();++i){
			sb.append(EROSubobjectList.get(i).toString());
		}
		sb.append(">");
		return sb.toString();
	}


}

