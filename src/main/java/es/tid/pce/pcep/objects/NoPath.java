package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.objects.tlvs.NoPathTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;


/**
 * <p>Represents a PCEP NO-PATH object as defined in RFC 5440</p>
 * <p>From RFC 5440 Section 7.5. NO-PATH Object</p>
<pre>
   The NO-PATH object is used in PCRep messages in response to an
   unsuccessful path computation request (the PCE could not find a path
   satisfying the set of constraints).  When a PCE cannot find a path
   satisfying a set of constraints, it MUST include a NO-PATH object in
   the PCRep message.

   There are several categories of issue that can lead to a negative
   reply.  For example, the PCE chain might be broken (should there be
   more than one PCE involved in the path computation) or no path
   obeying the set constraints could be found.  The "NI (Nature of
   Issue)" field in the NO-PATH object is used to report the error
   category.

   Optionally, if the PCE supports such capability, the NO-PATH object
   MAY contain an optional NO-PATH-VECTOR TLV defined below and used to
   provide more information on the reasons that led to a negative reply.
   The PCRep message MAY also contain a list of objects that specify the
   set of constraints that could not be satisfied.  The PCE MAY just
   replicate the set of objects that was received that was the cause of
   the unsuccessful computation or MAY optionally report a suggested
   value for which a path could have been found (in which case, the
   value differs from the value in the original request).

   NO-PATH Object-Class is 3.

   NO-PATH Object-Type is 1.

   The format of the NO-PATH object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |Nature of Issue|C|          Flags              |   Reserved    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                      Optional TLVs                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                 Figure 11: NO-PATH Object Format

   NI - Nature of Issue (8 bits):  The NI field is used to report the
      nature of the issue that led to a negative reply.  Two values are
      currently defined:

         0: No path satisfying the set of constraints could be found

         1: PCE chain broken

      The Nature of Issue field value can be used by the PCC for various
      purposes:

      *  Constraint adjustment before reissuing a new path computation
         request,

      *  Explicit selection of a new PCE chain,

      *  Logging of the error type for further action by the network
         administrator.

      IANA management of the NI field codespace is described in
      Section 9.

   Flags (16 bits).

   The following flag is currently defined:

   o  C flag (1 bit): when set, the PCE indicates the set of unsatisfied
      constraints (reasons why a path could not be found) in the PCRep
      message by including the relevant PCEP objects.  When cleared, no
      failing constraints are specified.  The C flag has no meaning and
      is ignored unless the NI field is set to 0x00.

   Unassigned bits are considered as reserved.  They MUST be set to zero
   on transmission and MUST be ignored on receipt.

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   The NO-PATH object body has a variable length and may contain
   additional TLVs.  The only TLV currently defined is the NO-PATH-
   VECTOR TLV defined below.

   Example: consider the case of a PCC that sends a path computation
   request to a PCE for a TE LSP of X Mbit/s.  Suppose that PCE cannot
   find a path for X Mbit/s.  In this case, the PCE must include in the
   PCRep message a NO-PATH object.  Optionally, the PCE may also include
   the original BANDWIDTH object so as to indicate that the reason for
   the unsuccessful computation is the bandwidth constraint (in this
   case, the NI field value is 0x00 and C flag is set).  If the PCE
   supports such capability, it may alternatively include the BANDWIDTH
   object and report a value of Y in the bandwidth field of the
   BANDWIDTH object (in this case, the C flag is set) where Y refers to
   the bandwidth for which a TE LSP with the same other characteristics
   (such as Setup/Holding priorities, TE LSP attribute, local
   protection, etc.) could have been computed.

   When the NO-PATH object is absent from a PCRep message, the path
   computation request has been fully satisfied and the corresponding
   paths are provided in the PCRep message.

   An optional TLV named NO-PATH-VECTOR MAY be included in the NO-PATH
   object in order to provide more information on the reasons that led
   to a negative reply.

   The NO-PATH-VECTOR TLV is compliant with the PCEP TLV format defined
   in Section 7.1 and is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length (length of the value portion in bytes)
   followed by a fixed-length 32-bit flags field.

   Type:   1
   Length: 4 bytes
   Value:  32-bit flags field

   IANA manages the space of flags carried in the NO-PATH-VECTOR TLV
   (see Section 9).

   The following flags are currently defined:

   o  Bit number: 31 - PCE currently unavailable

   o  Bit number: 30 - Unknown destination

   o  Bit number: 29 - Unknown source
 * </pre>
 * @author Oscar Gonzalez de Dios
 * @author Carlos Garcia Argos (cgarcia@novanotio.es) Feb. 22 2010
 * 
 */

public class NoPath  extends PCEPObject{
	
	/**
	 *  NI - Nature of Issue (8 bits):  The NI field is used to report the
      nature of the issue that led to a negative reply.  Two values are
      currently defined:

         0: No path satisfying the set of constraints could be found

         1: PCE chain broken

      The Nature of Issue field value can be used by the PCC for various
      purposes:

      *  Constraint adjustment before reissuing a new path computation
         request,

      *  Explicit selection of a new PCE chain,

      *  Logging of the error type for further action by the network
         administrator.

      IANA management of the NI field codespace is described in
      Section 9.
	 */
	private int natureOfIssue;
	
	/**
	 * C flag (1 bit): when set, the PCE indicates the set of unsatisfied
      constraints (reasons why a path could not be found) in the PCRep
      message by including the relevant PCEP objects.  When cleared, no
      failing constraints are specified.  The C flag has no meaning and
      is ignored unless the NI field is set to 0x00.
	 */
	private boolean cFlag;
	private NoPathTLV noPathTLV;

	public NoPath(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_NOPATH);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_NOPATH);
	}
	
	/**
	 * Constructs a new NO-PATH Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public NoPath(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encode NoPath Object
	 */
	public void encode() {
		this.ObjectLength=8;
		if (noPathTLV!=null){	
			noPathTLV.encode();
			this.ObjectLength=this.ObjectLength+noPathTLV.getTotalTLVLength();			
		}
		object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=(byte)natureOfIssue;
		this.object_bytes[5]=(byte) ( (this.cFlag?1:0)<<7 ); 
		this.object_bytes[6]=0x00;
		this.object_bytes[7]=0x00;
		if (noPathTLV!=null){
			System.arraycopy(noPathTLV.getTlv_bytes(), 0, this.object_bytes, 8, noPathTLV.getTotalTLVLength());
		}
		
	}

	/**
	 * Decode NoPath Object
	 */
	public void decode() throws MalformedPCEPObjectException{
		if (ObjectLength<8){
			throw new MalformedPCEPObjectException();
		}
		natureOfIssue=this.object_bytes[4];
		this.cFlag=(this.object_bytes[5]&0x80)==0x80;
		//boolean fin;
		int offset=8;//Position of the next TLV
		if (ObjectLength>8){
			//fin=true;
			int tlvType=PCEPTLV.getType(this.getObject_bytes(), offset);
			if (tlvType==ObjectParameters.PCEP_TLV_TYPE_NO_PATH_VECTOR){
				noPathTLV=new NoPathTLV(this.object_bytes,offset);
			}
			else {
				//FIXME: PASAMOS DEL OBJETO, O LANZAMOS ERROR??
				throw new MalformedPCEPObjectException();
			}
		}		
		
	}

	public int getNatureOfIssue() {
		return natureOfIssue;
	}

	public void setNatureOfIssue(int natureOfIssue) {
		this.natureOfIssue = natureOfIssue;
	}

	public boolean iscFlag() {
		return cFlag;
	}

	public void setcFlag(boolean cFlag) {
		this.cFlag = cFlag;
	}

	public NoPathTLV getNoPathTLV() {
		return noPathTLV;
	}

	public void setNoPathTLV(NoPathTLV noPathTLV) {
		this.noPathTLV = noPathTLV;
	}
	
	/**
	 * returns a String with the main contents
	 * FIXME: No cFlag and no NoPathTLV in response!!!!
	 */
	public String toString(){
		String returnString;
		if (noPathTLV!=null){
			returnString="<NOPATH nof: "+natureOfIssue+noPathTLV.toString()+">";	
		}else {
			returnString="<NOPATH nof: "+natureOfIssue+">";	
		}
		
		return returnString;
	}
	
}
