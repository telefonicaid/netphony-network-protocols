package es.tid.pce.pcep.objects.tlvs;

import java.util.Iterator;
import java.util.LinkedList;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 *  ASSOC-Type-List TLV (35) 

   The PCEP ASSOC-Type-List TLV is OPTIONAL.  It MAY be carried within
   an OPEN object sent by a PCEP speaker in an Open message to a PCEP
   peer so as to indicate the list of supported Association Types.
   
   @see <a href="https://tools.ietf.org/html/rfc8697">RFC 8697</a>

 * @author ogondio
 *
 */
public class ASSOCTypeListTLV extends PCEPTLV {
	
	/*
	 *   The PCEP ASSOC-Type-List TLV format is compliant with the PCEP TLV
   format defined in [RFC5440].  That is, the TLV is composed of 2
   octets for the type, 2 octets specifying the TLV length, and a Value
   field.  The Length field defines the length of the value portion in
   octets.  The TLV is padded to 4-octet alignment, and padding is not
   included in the Length field (e.g., a 3-octet value would have a
   length of three, but the total size of the TLV would be 8 octets).

   The PCEP ASSOC-Type-List TLV has the following format:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |          Assoc-Type #1        |      Assoc-Type #2            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                                                             //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |          Assoc-Type #N        |       padding                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 1: The ASSOC-Type-List TLV Format

   Type:  35

   Length:  N * 2 (where N is the number of Association Types).

   Value:  List of 2-byte Association Type code points, identifying the
      Association Types supported by the sender of the Open message.

   Assoc-Type (2 bytes):  Association Type code point identifier.  IANA
      manages the "ASSOCIATION Type Field" code point registry (see
      Section 7.4).

	 */
	
	private LinkedList<Integer> association_types=new LinkedList<Integer>();

	public ASSOCTypeListTLV() {
		this.setTLVType(ObjectParameters.PCEP_TLV_ASSOC_TYPE_LIST);
	}

	public ASSOCTypeListTLV(byte[] bytes, int offset) {
		super(bytes,offset);
		decode();
	}

	/**
	 * 
	 */
	public void encode() {
		int num_assoc=association_types.size();
		int by=num_assoc*2;
		this.setTLVValueLength(by);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		for (Iterator<Integer> i = association_types.iterator(); i.hasNext();) {
			 int valor=i.next().intValue();
	         this.tlv_bytes[offset]=(byte)(valor >> 8 & 0xff);
	         this.tlv_bytes[offset+1]=(byte)(valor & 0xff);
	         offset+=2;
	      }
		if ((num_assoc%2)>0) {
			this.tlv_bytes[offset]=(byte)(0xff);
	        this.tlv_bytes[offset+1]=(byte)(0xff);
		}

	}

	public void decode(){
		int num_assoc=this.getTLVValueLength()/2;
		int offset=4;
		for (int i = 0; i < num_assoc; i++) {
			int val=ByteHandler.decode2bytesInteger(this.tlv_bytes,offset);
			association_types.add(new Integer(val));
			offset+=2;
		}
		                                                                          
	}
	
	public String toString() {
		String res="Association types: ";
		for (Iterator<Integer> i = association_types.iterator(); i.hasNext();) {
			 int valor=i.next().intValue();
	         res+=valor+"; ";
	      }
		return res;
	}                            
                                      
	public LinkedList<Integer> getAssociation_types() {
		return association_types;
	}

	public void setAssociation_types(LinkedList<Integer> association_types) {
		this.association_types = association_types;
	}
	
	

}
