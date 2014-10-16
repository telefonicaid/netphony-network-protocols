package es.tid.pce.pcep.objects.tlvs;

import es.tid.ospf.ospfv2.lsa.InterASTEv2LSA;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * OSPF TE LSA TLV
 * @author ogondio
 *
 */
public class OSPFTE_LSA_TLV extends PCEPTLV {
	
	InterASTEv2LSA interASTEv2LSA;
	
	public OSPFTE_LSA_TLV(){
		//En los parametros tengo el tipo de TLV que quiero, si es LinkTLV o routerTLV????
		this.TLVType=ObjectParameters.PCEP_TLV_OSPFTE_LSA_TLV;		
	}

	public OSPFTE_LSA_TLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		log.finest("Encoding OSPFTE LSA TLV");		
		interASTEv2LSA.encode();
		this.setTLVValueLength(interASTEv2LSA.getLength());
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		System.arraycopy(interASTEv2LSA.getLSAbytes(),0, this.tlv_bytes, 4, interASTEv2LSA.getLength());		
	}

	
	public void decode() throws MalformedPCEPObjectException{
		log.finest("Decoding OSPFTE LSA TLV");
		try {
			interASTEv2LSA= new InterASTEv2LSA(this.tlv_bytes,4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warning("Problem decoding OSPFTE LSA TLV");
			throw new MalformedPCEPObjectException();
		}
	}

	public InterASTEv2LSA getInterASTEv2LSA() {
		return interASTEv2LSA;
	}

	public void setInterASTEv2LSA(InterASTEv2LSA interASTEv2LSA) {
		this.interASTEv2LSA = interASTEv2LSA;
	}
	

}
