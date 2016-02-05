package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.objects.tlvs.BandwidthTLV;
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
	
	private BandwidthTLV bwTLV;
	
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
		//NO OPTIONAL TLVS NOW!
		this.ObjectLength=8;
		if (bwTLV!=null){
			bwTLV.encode();
			ObjectLength=ObjectLength+bwTLV.getTotalTLVLength();
		}
		object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=(byte)((OFcode>>8) & 0xFF);
		object_bytes[5]=(byte)(OFcode & 0xFF);
		object_bytes[6]=0X00;
		object_bytes[7]=0X00;
		int offset=8;
		if (bwTLV!=null){
			System.arraycopy(bwTLV.getTlv_bytes(),0,this.object_bytes,offset,bwTLV.getTotalTLVLength());
			offset=offset+bwTLV.getTotalTLVLength();
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
			log.debug("Beginning TLV decoding");
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			log.debug("TLV type "+tlvtype+" TLV length "+tlvlength);//FIXME: Cambiar a log.debug cuando est� estable!!!
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_OF_LIST_TLV:
				log.debug("OF_LIST_TLV found");
				bwTLV=new BandwidthTLV(this.getObject_bytes(), offset);
				log.debug(bwTLV.toString());
				break;
			
		
			default:
				log.warn("UNKNOWN TLV found: "+tlvtype);
				//UnknownTLV unknownTLV = new UnknownTLV();			
				//FIXME: Que hacemos con los desconocidos????
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
	
	
	
	public BandwidthTLV getBwTLV() {
		return bwTLV;
	}

	public void setBwTLV(BandwidthTLV bwTLV) {
		this.bwTLV = bwTLV;
	}

	public String toString(){
		String ret="<OF code= "+OFcode+">";
		return ret;
	}

}
