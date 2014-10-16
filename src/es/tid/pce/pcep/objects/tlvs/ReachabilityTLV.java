package es.tid.pce.pcep.objects.tlvs;


import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.rsvp.objects.subobjects.ASNumberEROSubobject;
import es.tid.rsvp.objects.subobjects.EROSubobject;
import es.tid.rsvp.objects.subobjects.IPv4prefixEROSubobject;
import es.tid.rsvp.objects.subobjects.IPv6prefixEROSubobject;
import es.tid.rsvp.objects.subobjects.SubObjectValues;
import es.tid.rsvp.objects.subobjects.UnnumberIfIDEROSubobject;

public class ReachabilityTLV extends PCEPTLV {

	public LinkedList<EROSubobject> EROSubobjectList;

	public ReachabilityTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_REACHABILITY_TLV;
		EROSubobjectList=new LinkedList<EROSubobject>();
	}

	public ReachabilityTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		EROSubobjectList=new LinkedList<EROSubobject>();
		decode();
	}


	public void encode() {
		int len=0;
		for (int k=0; k<EROSubobjectList.size();k=k+1){
			EROSubobjectList.get(k).encode();			
			len=len+EROSubobjectList.get(k).getErosolength();
		}
		if (len==0){
			log.severe("At least one ERO Subobject in the TLV must be set");
			//throw new MalformedPCEPObjectException();
			return;//FIXME: ARREGLAR PARA LANZAR EXCEPCIONES
		}		
		this.setTLVValueLength(len);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		//System.arraycopy(eroso.getSubobject_bytes(),0, this.tlv_bytes, 4, eroso.getErosolength());
		int pos=4;
		for (int k=0 ; k<EROSubobjectList.size(); k=k+1) {					
			System.arraycopy(EROSubobjectList.get(k).getSubobject_bytes(),0, this.tlv_bytes, pos, EROSubobjectList.get(k).getErosolength());
			pos=pos+EROSubobjectList.get(k).getErosolength();
		}				

	}

	public void decode() throws MalformedPCEPObjectException{
		int offset=4;
		log.finest("Decoding Reachability TLV");//FIXME: Cambiar a fine
		boolean fin=false;
		if (this.TLVValueLength ==0){
			fin=true;
		}
		try {
			while (!fin) {
				int subojectclass=EROSubobject.getType(this.tlv_bytes, offset);
				int subojectlength=EROSubobject.getLength(this.tlv_bytes, offset);
				switch(subojectclass) {
				case SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX:
					IPv4prefixEROSubobject sobjt4=new IPv4prefixEROSubobject(this.tlv_bytes, offset);
					log.finest("IPv4 prefix found:"+ sobjt4);		
					this.addEROSubobject(sobjt4);
					break;

				case SubObjectValues.ERO_SUBOBJECT_IPV6PREFIX:
					IPv6prefixEROSubobject sobjt6=new IPv6prefixEROSubobject(this.tlv_bytes, offset);
					log.finest("IPv6 prefix found:"+ sobjt6);					
					addEROSubobject(sobjt6);
					break;		

				case SubObjectValues.ERO_SUBOBJECT_ASNUMBER:
					ASNumberEROSubobject sobjas=new ASNumberEROSubobject (this.tlv_bytes, offset);
					log.finest("AS Number found found:"+ sobjas);					
					addEROSubobject(sobjas);
					break;

				case SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID:
					UnnumberIfIDEROSubobject subun=new UnnumberIfIDEROSubobject(this.tlv_bytes, offset);
					log.finest("Unnumbered If Id found:"+ subun);					
					addEROSubobject(subun);
					break;	
				default:
					log.finest("ERO Subobject Unknown");
					break;
				}
				offset=offset+subojectlength;
				if (offset>=this.TLVValueLength){
					log.finest("No more subobjects in the Reachability TLV");
					fin=true;
				}
			}
		}catch (Exception e){
			throw new MalformedPCEPObjectException();
		}		
	}

	public EROSubobject getEroso(int i) {
		return this.EROSubobjectList.get(i);
	}

	public void addEROSubobject(EROSubobject eroso) {
		this.EROSubobjectList.add(eroso);
	}

	public LinkedList<EROSubobject> getEROSubobjectList() {
		return EROSubobjectList;
	}

	public void setEROSubobjectList(LinkedList<EROSubobject> eROSubobjectList) {
		EROSubobjectList = eROSubobjectList;
	}

}
