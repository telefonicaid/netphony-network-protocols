package es.tid.pce.pcep.objects.tlvs;
/**
 * OF-List TLV, from RFC 5541
 *
   The PCEP OF-List TLV is optional.  It MAY be carried within an OPEN
   object sent by a PCE in an Open message to a PCEP peer so as to
   indicate the list of supported objective functions.

   The OF-List TLV format is compliant with the PCEP TLV format defined
   in [RFC5440].  That is, the TLV is composed of 2 octets for the type,
   2 octets specifying the TLV length, and a Value field.  The Length
   field defines the length of the value portion in octets.  The TLV is
   padded to 4-octet alignment, and padding is not included in the
   Length field (e.g., a 3-octet value would have a length of three, but
   the total size of the TLV would be eight octets).

   The PCEP OF-List TLV has the following format:

   TYPE:    4
   LENGTH:  N * 2 (where N is the number of objective functions)
   VALUE:   list of 2-byte objective function code points, identifying
            the objective functions supported by the sender of the Open
            message.

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             OF Code #1        |      OF Code #2               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                                                             //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             OF Code #N        |       padding                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   OF Code (2 bytes): Objective Function code point identifier.  IANA
   manages the "PCE Objective Function" code point registry (see Section
   6).
 */
import java.util.LinkedList;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

public class OF_LIST_TLV extends PCEPTLV {
	
	private LinkedList<Integer> ofCodes;
	
	public OF_LIST_TLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_OF_LIST_TLV;
		ofCodes=new LinkedList<Integer>();
	}
	
	public OF_LIST_TLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		ofCodes=new LinkedList<Integer>();
		decode();
	}

	@Override
	public void encode() {
		this.setTLVValueLength(ofCodes.size()*2);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		int offset=4;
		for (int i=0;i<ofCodes.size();++i){
			this.tlv_bytes[offset]=(byte)(ofCodes.get(i)>>>8 & 0xFF);
			this.tlv_bytes[offset+1]=(byte)(ofCodes.get(i) & 0xFF);
			offset=offset+2;
		}

	}
	
	public void decode() throws MalformedPCEPObjectException{
		int numOfCodes=this.TLVValueLength/2;
		int offset=4;
		for (int i=0;i<numOfCodes;++i){
			int ofCode=((  ((int)this.tlv_bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)this.tlv_bytes[offset+1] & 0xFF);
			ofCodes.add(ofCode);
			offset=offset+2;
		}
		
	}
	
	
	
	public LinkedList<Integer> getOfCodes() {
		return ofCodes;
	}

	public void setOfCodes(LinkedList<Integer> ofCodes) {
		this.ofCodes = ofCodes;
	}

	public String toString(){
		String res="OF codes: ";
		for  (int i=0;i<ofCodes.size();++i){
			res=res+ofCodes.get(i)+" ";
		}
		return res;
	}

}
