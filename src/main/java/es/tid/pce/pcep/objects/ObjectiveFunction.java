package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.objects.tlvs.BandwidthTLV;
import es.tid.pce.pcep.objects.tlvs.OF_LIST_TLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;


/**
 * <p> Represents a PCEP Objective Function (OF) Object, as defined in RFC 5541</p>
 * 3.1. OF Object


   The PCEP OF (Objective Function) object is optional.  It MAY be
   carried within a PCReq message so as to indicate the desired/required
   objective function to be applied by the PCE during path computation
   or within a PCRep message so as to indicate the objective function
   that was used by the PCE during path computation.

   The OF object format is compliant with the PCEP object format defined
   in [RFC5440].

   The OF Object-Class is 21.
   The OF Object-Type is 1.

   The format of the OF object body is:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  OF Code                      |     Reserved                  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //              Optional TLV(s)                                //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   OF Code (2 bytes): The identifier of the objective function.  IANA
   manages the "PCE Objective Function" code point registry (see Section
   6).

   Reserved (2 bytes): This field MUST be set to zero on transmission
   and MUST be ignored on receipt.

   Optional TLVs may be defined in the future so as to encode objective
   function parameters.

 * <p> TO BE IMPLEMENTED!!!!!!</p>
 * 
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public class ObjectiveFunction extends PCEPObject {
	
	private int OFcode;
	
	private OF_LIST_TLV oflist;
	
	public ObjectiveFunction(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_OBJECTIVE_FUNCTION);
	}
	
	public ObjectiveFunction(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		this.ObjectLength=8;
		if (oflist!=null){
			oflist.encode();
			ObjectLength=ObjectLength+oflist.getTotalTLVLength();
		}
		object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=(byte)((OFcode>>8) & 0xFF);
		object_bytes[5]=(byte)(OFcode & 0xFF);
		object_bytes[6]=0X00;
		object_bytes[7]=0X00;
		int offset=8;
		if (oflist!=null){
			System.arraycopy(oflist.getTlv_bytes(),0,this.object_bytes,offset,oflist.getTotalTLVLength());
			offset=offset+oflist.getTotalTLVLength();
		}
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		if (ObjectLength<8){
			throw new MalformedPCEPObjectException();
		}
		OFcode=(((object_bytes[4]&0xFF)<<8)& 0xFF00) |  (object_bytes[5] & 0xFF);
		boolean fin=false;
		if (ObjectLength==8){
			fin=true;
		}
		int offset=8;
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_OF_LIST_TLV:
				oflist=new OF_LIST_TLV(this.getObject_bytes(), offset);
				log.debug(oflist.toString());
				break;
			
		
			default:
				log.warn("UNKNOWN TLV found: "+tlvtype);
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
	}

	public void setOFcode(int oFcode) {
		OFcode = oFcode;
	}

	public int getOFcode() {
		return OFcode;
	}

	public OF_LIST_TLV getOflist() {
		return oflist;
	}

	public void setOflist(OF_LIST_TLV oflist) {
		this.oflist = oflist;
	}

	public String toString(){
		String ret="<OF code= "+OFcode;
		if (this.getOflist()!=null){
			ret+=ret.toString();
		}
		ret+=">";
		return ret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + OFcode;
		result = prime * result + ((oflist == null) ? 0 : oflist.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectiveFunction other = (ObjectiveFunction) obj;
		if (OFcode != other.OFcode)
			return false;
		if (oflist == null) {
			if (other.oflist != null)
				return false;
		} else if (!oflist.equals(other.oflist))
			return false;
		return true;
	}
	
	

}
