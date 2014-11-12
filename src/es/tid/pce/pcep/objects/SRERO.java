package es.tid.pce.pcep.objects;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.pce.pcep.objects.subobjects.SREROSubobject;
import org.slf4j.LoggerFactory;

/*
 * SRERO.
 * 
 */

public class SRERO extends PCEPObject{	
	
	public LinkedList<SREROSubobject> SREROSubobjectList;
	
	//Constructors
  private static Logger log = LoggerFactory.getLogger("PCEServer");

	public SRERO() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_SR_ERO);
		SREROSubobjectList=new LinkedList<SREROSubobject>();
	}
	
	/**
	 * Constructs a new ERO (Explicit Route Object) from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public SRERO (byte []bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes, offset);
		SREROSubobjectList=new LinkedList<SREROSubobject>();
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode Explicit Route Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		for (int k=0; k<SREROSubobjectList.size();k=k+1){
			SREROSubobjectList.get(k).encode();			
			len=len+SREROSubobjectList.get(k).getErosolength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<SREROSubobjectList.size(); k=k+1) {		
			System.arraycopy(SREROSubobjectList.get(k).getSubobject_bytes(),0, this.object_bytes, pos, SREROSubobjectList.get(k).getErosolength());
			pos=pos+SREROSubobjectList.get(k).getErosolength();
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
			//int subojectclass=SREROSubobject.getType(this.getObject_bytes(), offset);
			int subojectlength=SREROSubobject.getLength(this.getObject_bytes(), offset);
			SREROSubobject sobjt = new SREROSubobject(this.getObject_bytes(), offset);
			this.addSREROSubobject(sobjt);
			offset=offset+subojectlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
		
	}
	
	//Getters and setters
		
	public void addSREROSubobject(SREROSubobject eroso){
		SREROSubobjectList.add(eroso);
	}
	
	public void addSREROSubobjectList(LinkedList<SREROSubobject> erosovec){
		SREROSubobjectList.addAll(erosovec);
	}

	public LinkedList<SREROSubobject> getSREROSubobjectList() {
		return SREROSubobjectList;
	}

	public void setSREROSubobjectList(LinkedList<SREROSubobject> eROSubobjectList) {
		SREROSubobjectList = eROSubobjectList;
	}
	

	public String toString(){
		StringBuffer sb=new StringBuffer(SREROSubobjectList.size()*100);
		sb.append("<SR_ERO: ");
		for (int i=0;i<SREROSubobjectList.size();++i){
			sb.append(SREROSubobjectList.get(i).toString());
		}
		sb.append(">");
		return sb.toString();
	}


}
