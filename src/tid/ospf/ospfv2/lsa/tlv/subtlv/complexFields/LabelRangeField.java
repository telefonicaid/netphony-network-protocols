package tid.ospf.ospfv2.lsa.tlv.subtlv.complexFields;

import tid.ospf.ospfv2.lsa.tlv.subtlv.MalformedOSPFSubTLVException;
import tid.rsvp.RSVPProtocolViolationException;

/**
 *  2.2.2. Inclusive/Exclusive Label Ranges

   In the case of inclusive/exclusive ranges the label set format is
   given by:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |2 or 3 | Num Labels(not used)  |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                    Start Label                                |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                     End Label                                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+



   Note that the start and end label must in some sense "compatible" in
   the technology being used.
 * @author Marta Cuaresma Saturio
 *
 */
public class LabelRangeField  extends LabelSetField {



	    public 	LabelRangeField(){
	//FIXME: puede ser 0 o 1...mirarrrrr
	    	action = 0;
	    	numLabels = 0;
			this.encode();
			
		}

		public LabelRangeField(byte[] bytes, int offset) throws MalformedOSPFSubTLVException {
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
