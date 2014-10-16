package es.tid.rsvp.constructs.gmpls;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.RSVPConstruct;

/**
 *  DWDM Wavelength Label (RFC 6205).
 * 

   For the case of lambda switching of DWDM, the information carried in
   a wavelength label is:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |Grid | C.S.  |    Identifier   |              n                |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |       m                       |  Reserved                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   (1) Grid: 3 bits

   The value for Grid is set to 1 for the ITU-T DWDM grid as defined in
   [G.694.1].

   +----------+---------+
   |   Grid   |  Value  |
   +----------+---------+
   | Reserved |    0    |
   +----------+---------+
   |ITU-T DWDM|    1    |
   +----------+---------+
   |ITU-T CWDM|    2    |
   +----------+---------+
   |Future use|  3 - 7  |
   +----------+---------+

   (2) C.S. (channel spacing): 4 bits

   DWDM channel spacing is defined as follows.

   +----------+---------+
   |C.S. (GHz)|  Value  |
   +----------+---------+
   | Reserved |    0    |
   +----------+---------+
   |    100   |    1    |
   +----------+---------+
   |    50    |    2    |
   +----------+---------+
   |    25    |    3    |
   +----------+---------+
   |    12.5  |    4    |
   +----------+---------+
   |    6.25  |    5    |
   +----------+---------+
   |Future use|  6 - 15 |
   +----------+---------+

   (3) Identifier: 9 bits

   The Identifier field in lambda label format is used to distinguish
   different lasers (in one node) when they can transmit the same
   frequency lambda.  The Identifier field is a per-node assigned and
   scoped value.  This field MAY change on a per-hop basis.  In all
   cases but one, a node MAY select any value, including zero (0), for
   this field.  Once selected, the value MUST NOT change until the LSP
   is torn down, and the value MUST be used in all LSP-related messages,
   e.g., in Resv messages and label Record Route Object (RRO)
   subobjects.  The sole special case occurs when this label format is
   used in a label Explicit Route Object (ERO) subobject.  In this case,
   the special value of zero (0) means that the referenced node MAY
   assign any Identifier field value, including zero (0), when
   establishing the corresponding LSP.  When a non-zero value is
   assigned to the Identifier field in a label ERO subobject, the
   referenced node MUST use the assigned value for the Identifier field
   in the corresponding LSP-related messages.

   (4) n: 16 bits

   n is a two's-complement integer to take either a positive, negative,
   or zero value.  This value is used to compute the frequency as shown
   above.
 * @author ogondio
 *
 */

public class DWDMWavelengthLabel extends RSVPConstruct{
	
	/**
	 * 
   The value for Grid is set to 1 for the ITU-T DWDM grid as defined in [G.694.1].
	 */
	private int grid;
	
	/**
	 * Channel spacing
	 */
	private int channelSpacing;
	
	/**
	 * The Identifier field in lambda label format is used to distinguish
   different lasers (in one node) when they can transmit the same
   frequency lambda.
	 */
	private int identifier;
	
	/**
	 * n is a two's-complement integer to take either a positive, negative,
   or zero value.  This value is used to compute the frequency 
	 */
	private int n;


	private int m;
	
	/**
	 * Encode a DWDM Wavelength Label
	 */
	public void encode() throws RSVPProtocolViolationException {
		//The length is always 4 bytes
		if(grid==DWDMWavelengthLabelValues.ITU_T_FLEX){
			this.setLength(8);
			this.bytes=new byte[this.getLength()];
			this.bytes[0]=(byte)((grid<<5)|(channelSpacing<<1)|(identifier>>>8));
			this.bytes[1]=(byte)(identifier&0xFF);
			this.bytes[2]=(byte)((n>>8)&0xFF);
			this.bytes[3]=(byte)(n&0xFF);
			this.bytes[4]=(byte)((m>>8)&0xFF);
			this.bytes[5]=(byte)(m&0xFF);
			
		}else{
			this.setLength(4);
			this.bytes=new byte[this.getLength()];
			this.bytes[0]=(byte)((grid<<5)|(channelSpacing<<1)|(identifier>>>8));
			this.bytes[1]=(byte)(identifier&0xFF);
			this.bytes[2]=(byte)((n>>8)&0xFF);
			this.bytes[3]=(byte)(n&0xFF);
		}
 		
	}

	/**
	 * Decode a DWDM Wavelength Label
	 */
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException {
		grid=(bytes[offset]&0xE0)>>>5;
		channelSpacing=(bytes[offset]&0x1E)>>>1;
		identifier=((bytes[offset]&0x01)<<8)|(bytes[offset+1]&0xFF);
		n=((bytes[offset+2]&0xFF)<<8)|(bytes[offset+3]&0xFF);
		if (grid==DWDMWavelengthLabelValues.ITU_T_FLEX){
			m=(((bytes[offset+4]&0xFF)<<8)|(bytes[offset+5]&0xFF));	
		}
	}

	public int getGrid() {
		return grid;
	}

	public void setGrid(int grid) {
		this.grid = grid;
	}

	public int getChannelSpacing() {
		return channelSpacing;
	}

	public void setChannelSpacing(int channelSpacing) {
		this.channelSpacing = channelSpacing;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	@Override
	public String toString() {
		String ret= "";
		ret = ret + "Grid "+this.grid +" n:"+Integer.toString(n) +" m:"+Integer.toString(m);
		return ret;
	}

	
}
