package es.tid.pce.pcep.objects.tlvs;

import java.util.Iterator;
import java.util.LinkedList;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * Operator-configured Association Range TLV (Type: 29)	[RFC8697]

    A new PCEP OP-CONF-ASSOC-RANGE (Operator-configured Association
   Range) TLV is defined.  The PCEP OP-CONF-ASSOC-RANGE TLV is carried
   within an OPEN object.  This way, during the PCEP session-setup
   phase, a PCEP speaker can advertise to a PCEP peer the Operator-
   configured Association Range for an Association Type.

 * @author ogondio
 *
 */
public class OpConfAssocRangeTLV extends PCEPTLV {
	
	/*
	 *   The PCEP OP-CONF-ASSOC-RANGE TLV is OPTIONAL.  It MAY be carried
   within an OPEN object sent by a PCEP speaker in an Open message to a
   PCEP peer.  The OP-CONF-ASSOC-RANGE TLV format is compliant with the
   PCEP TLV format defined in [RFC5440].  That is, the TLV is composed
   of 2 bytes for the type, 2 bytes specifying the TLV length, and a
   Value field.  The Length field defines the length of the value
   portion in bytes.

   The PCEP OP-CONF-ASSOC-RANGE TLV has the following format:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             Reserved          |          Assoc-Type #1        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |      Start-Assoc-ID #1        |           Range #1            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                                                             //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             Reserved          |          Assoc-Type #N        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |      Start-Assoc-ID #N        |           Range #N            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 2: The OP-CONF-ASSOC-RANGE TLV Format

   Type:  29

   Length:  N * 8 (where N is the number of Association Types).

   Value:  Includes the following fields, repeated for each Association
      Type:

      Reserved (2 bytes):  MUST be set to 0 on transmission and MUST be
         ignored on receipt.

      Assoc-Type (2 bytes):  The Association Type (Section 7.4).  The
         Association Types will be defined in future documents.

      Start-Assoc-ID (2 bytes):  The "start association" identifier for
         the Operator-configured Association Range for the particular
         Association Type.  The values 0 and 0xffff MUST NOT be used; on
         receipt of these values in the TLV, the session is rejected,
         and an error message is sent (see Section 5.1).

      Range (2 bytes):  The number of associations marked for the
         Operator-configured Associations.  Range MUST be greater than
         0, and it MUST be such that (Start-Assoc-ID + Range) does not
         cross the largest Association ID value of 0xffff.  If this
         condition is not satisfied, the session is rejected, and an
         error message is sent (see Section 5.1).

	 */
	
	private LinkedList<OperatorAssociation> operator_associations=new LinkedList<OperatorAssociation>();

	public OpConfAssocRangeTLV() {
		this.setTLVType(ObjectParameters.PCEP_TLV_OPERATOR_CONF_ASSOCIATION_RANGE);
	}

	public OpConfAssocRangeTLV(byte[] bytes, int offset) {
		super(bytes,offset);
		decode();
	}

	/**
	 * 
	 */
	public void encode() {
		int num_assoc=operator_associations.size();
		int by=num_assoc*8;
		this.setTLVValueLength(by);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		for (Iterator<OperatorAssociation> i = operator_associations.iterator(); i.hasNext();) {
			OperatorAssociation oa=i.next();
			 this.tlv_bytes[offset]=(byte)0x00;
	         this.tlv_bytes[offset+1]=(byte)0x00;
	         this.tlv_bytes[offset+2]=(byte)(oa.getAssocType() >> 8 & 0xff);
	         this.tlv_bytes[offset+3]=(byte)(oa.getAssocType()  & 0xff);
	         this.tlv_bytes[offset+4]=(byte)(oa.getStartAssocID()  >> 8 & 0xff);
	         this.tlv_bytes[offset+5]=(byte)(oa.getStartAssocID()  & 0xff);
	         this.tlv_bytes[offset+6]=(byte)(oa.getRange()  >> 8 & 0xff);
	         this.tlv_bytes[offset+7]=(byte)(oa.getRange()  & 0xff);
	         offset+=2;
	      }

	}

	public void decode(){
		int num_assoc=this.getTLVValueLength()/8;
		int offset=4;
		for (int i = 0; i < num_assoc; i++) {
			OperatorAssociation oa=new OperatorAssociation();
			offset+=2;
			oa.setAssocType(ByteHandler.decode2bytesInteger(this.tlv_bytes,offset));
			offset+=2;
			oa.setStartAssocID(ByteHandler.decode2bytesInteger(this.tlv_bytes,offset));
			offset+=2;
			oa.setRange(ByteHandler.decode2bytesInteger(this.tlv_bytes,offset));
			offset+=2;
			operator_associations.add(oa);
		}
		                                                                          
	}

	public LinkedList<OperatorAssociation> getOperator_associations() {
		return operator_associations;
	}

	public void setOperator_associations(LinkedList<OperatorAssociation> operator_associations) {
		this.operator_associations = operator_associations;
	}

	@Override
	public String toString() {
		return "OpConfAssocRangeTLV [operator_associations=" + operator_associations + "]";
	}
	
	
	
	

}
