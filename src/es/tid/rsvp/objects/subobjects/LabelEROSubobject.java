package es.tid.rsvp.objects.subobjects;


/** Label ERO Subobject (RFC 4373).
 * 
 * From RFC 3473:(section 5.1) FIXME COMPLETAR
   
   5.1. Label ERO subobject

   The Label ERO subobject is defined as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |U|   Reserved  |   C-Type      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                             Label                             |
   |                              ...                              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   See [RFC3471] for a description of L, U and Label parameters.

   Type

      3  Label

   Length

      The Length contains the total length of the subobject in bytes,
      including the Type and Length fields.  The Length is always
      divisible by 4.

   C-Type

      The C-Type of the included Label Object.  Copied from the Label
      Object.
      
      * BUSCANDO:
      Generalized abeC-Type (2) 
      Waveband Label uses C-Type (3)

5.1.1. Procedures

   The Label subobject follows a subobject containing the IP address, or
   the interface identifier [RFC3477], associated with the link on which
   it is to be used.  Up to two label subobjects may be present, one for
   the downstream label and one for the upstream label.  The following
   SHOULD result in "Bad EXPLICIT_ROUTE object" errors:

   o If the first label subobject is not preceded by a subobject
     containing an IP address, or an interface identifier [RFC3477],
     associated with an output link.


   o For a label subobject to follow a subobject that has the L-bit set

   o On unidirectional LSP setup, for there to be a label subobject with
     the U-bit set

   o For there to be two label subobjects with the same U-bit values

   To support the label subobject, a node must check to see if the
   subobject following its associate address/interface is a label
   subobject.  If it is, one subobject is examined for unidirectional
   LSPs and two subobjects for bidirectional LSPs.  If the U-bit of the
   subobject being examined is clear (0), then value of the label is
   copied into a new Label_Set object.  This Label_Set object MUST be
   included on the corresponding outgoing Path message.

   If the U-bit of the subobject being examined is set (1), then value
   of the label is label to be used for upstream traffic associated with
   the bidirectional LSP.  If this label is not acceptable, a "Bad
   EXPLICIT_ROUTE object" error SHOULD be generated.  If the label is
   acceptable, the label is copied into a new Upstream_Label object.
   This Upstream_Label object MUST be included on the corresponding
   outgoing Path message.

   After processing, the label subobjects are removed from the ERO.

   Note an implication of the above procedures is that the label
   subobject should never be the first subobject in a newly received
   message.  If the label subobject is the the first subobject an a
   received ERO, then it SHOULD be treated as a "Bad strict node" error.

   Procedures by which an LSR at the head-end of an LSP obtains the
   information needed to construct the Label subobject are outside the
   scope of this document.      
      

 * 
 * 
 * @author Oscar Gonzalez de Dios
 *
 */
public abstract class LabelEROSubobject extends EROSubobject{
	
	private int ctype; //The C-Type of the included Label Object
	private short ubit;
	
	public LabelEROSubobject(){
		this.setType(SubObjectValues.ERO_SUBOBJECT_LABEL);
	}
	
	public LabelEROSubobject(byte []bytes, int offset){
		super(bytes, offset);		
	}

	public int getCtype() {
		return ctype;
	}

	public void setCtype(int ctype) {
		this.ctype = ctype;
	}		
	
	protected void decodeLabelHeader(){
		ctype=(int)subobject_bytes[3];
		ubit=(short)((subobject_bytes[2]&0x01) >>>7);
	}
	
	protected void encodeLabelHeader(){
		subobject_bytes[3]=(byte)(ctype&0xFF);
		subobject_bytes[2]=(byte)(ubit<<7);		
	}
	
	
	public short getUbit() {
		return ubit;
	}

	public void setUbit(short ubit) {
		this.ubit = ubit;
	}

	public static int getCType(byte []bytes, int offset){
		return (int)(bytes[offset+3] & 0xFF);
	}
	

}
