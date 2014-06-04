package tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields;

import tid.ospf.ospfv2.lsa.tlv.subtlv.MalformedOSPFSubTLVException;


/**
  2.2.1. Inclusive/Exclusive Label Lists

   In the case of the inclusive/exclusive lists the wavelength set
   format is given by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |0 or 1 | Num Labels (not used) |          Length               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                         Base Label                            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     :                                                               :
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                         Last  Label                           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   Where:

   Num Labels is not used in this particular format since the Length
   parameter is sufficient to determine the number of labels in the
   list.

   
 * @author Marta Cuaresma Saturio
 *
 */
public class LabelListField extends LabelSetField {


    public LabelListField() {
//FIXME: puede ser 0 o 1...mirarrrrr
    	action = 0;
    	numLabels = 0;
		this.encode();
		
	}

	public LabelListField(byte[] bytes, int offset) throws MalformedOSPFSubTLVException {
		decodeHeader();		
		this.bytes = bytes;
		//this.decode(offset);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void encode(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decode()
			throws MalformedOSPFSubTLVException {
		// TODO Auto-generated method stub
		
	}


}
