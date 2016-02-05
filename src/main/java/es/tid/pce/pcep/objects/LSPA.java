package es.tid.pce.pcep.objects;

/**
 * <p>Represents a PCEP LSP Attributes object (LSPA), as defined in RFC 5440.</p>
 * 
 * <p>From RFC 5440, 7.11. LSPA Object</p>
<pre>	 
 * The LSPA (LSP Attributes) object is optional and specifies various TE
   LSP attributes to be taken into account by the PCE during path
   computation.  The LSPA object can be carried within a PCReq message,
   or a PCRep message in case of unsuccessful path computation (in this
   case, the PCRep message also contains a NO-PATH object, and the LSPA
   object is used to indicate the set of constraints that could not be
   satisfied).  Most of the fields of the LSPA object are identical to
   the fields of the SESSION-ATTRIBUTE object (C-Type = 7) defined in
   [RFC3209] and [RFC4090].  When absent from the PCReq message, this
   means that the Setup and Holding priorities are equal to 0, and there
   are no affinity constraints.  See Section 4.7.4 of [RFC3209] for a
   detailed description of the use of resource affinities.

   LSPA Object-Class is 9.

   LSPA Object-Types is 1.

   The format of the LSPA object body is:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Exclude-any                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Include-any                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                       Include-all                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  Setup Prio   |  Holding Prio |     Flags   |L|   Reserved    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                     Optional TLVs                           //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 16: LSPA Object Body Format

   Setup Prio (Setup Priority - 8 bits):  The priority of the TE LSP
      with respect to taking resources, in the range of 0 to 7.  The
      value 0 is the highest priority.  The Setup Priority is used in
      deciding whether this session can preempt another session.

   Holding Prio (Holding Priority - 8 bits):  The priority of the TE LSP
      with respect to holding resources, in the range of 0 to 7.  The
      value 0 is the highest priority.  Holding Priority is used in
      deciding whether this session can be preempted by another session.

   Flags (8 bits)

      L flag:  Corresponds to the "Local Protection Desired" bit
         ([RFC3209]) of the SESSION-ATTRIBUTE Object.  When set, this
         means that the computed path must include links protected with
         Fast Reroute as defined in [RFC4090].

      Unassigned flags MUST be set to zero on transmission and MUST be
      ignored on receipt.

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Note that optional TLVs may be defined in the future to carry
   additional TE LSP attributes such as those defined in [RFC5420].
 </pre>
 
 * 
 * <p>From RFC 3209 (Extensions to RSVP for LSP Tunnels )</p>
 * <pre>
 * 4.7.2. Format with resource affinities

    SESSION_ATTRIBUTE class = 207, LSP_TUNNEL_RA C-Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Exclude-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-any                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Include-all                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Setup Prio  | Holding Prio  |     Flags     |  Name Length  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //          Session Name      (NULL padded display string)      //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

      Exclude-any

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link
         unacceptable.

      Include-any

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link acceptable
         (with respect to this test).  A null set (all bits set to zero)
         automatically passes.

      Include-all

         A 32-bit vector representing a set of attribute filters
         associated with a tunnel all of which must be present for a
         link to be acceptable (with respect to this test).  A null set
         (all bits set to zero) automatically passes.

      Setup Priority

         The priority of the session with respect to taking resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         The Setup Priority is used in deciding whether this session can
         preempt another session.

      Holding Priority

         The priority of the session with respect to holding resources,
         in the range of 0 to 7.  The value 0 is the highest priority.
         Holding Priority is used in deciding whether this session can
         be preempted by another session.
     Name Length

         The length of the display string before padding, in bytes.

      Session Name

         A null padded string of characters.
	</pre>
	@author Oscar Gonzalez de Dios (ogondio@tid.es)
	@version 0.1
 */



public class LSPA extends PCEPObject {
		
	/**
	 *   Exclude-any: A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link
         unacceptable.
	 */
	private int excludeAny;
	/**
	 *  Include-any: A 32-bit vector representing a set of attribute filters
         associated with a tunnel any of which renders a link acceptable
         (with respect to this test).  A null set (all bits set to zero)
         automatically passes.
	 */
	private int includeAny;
	/**
	 * Include-all: A 32-bit vector representing a set of attribute filters
         associated with a tunnel all of which must be present for a
         link to be acceptable (with respect to this test).  A null set
         (all bits set to zero) automatically passes.
	 */
	private int includeAll;	
	/**
	 * Setup Prio (Setup Priority - 8 bits):  The priority of the TE LSP
      with respect to taking resources, in the range of 0 to 7.  The
      value 0 is the highest priority.  The Setup Priority is used in
      deciding whether this session can preempt another session.
	 */
	private int setupPrio;
	/**
	 * Holding Prio (Holding Priority - 8 bits):  The priority of the TE LSP
      with respect to holding resources, in the range of 0 to 7.  The
      value 0 is the highest priority.  Holding Priority is used in
      deciding whether this session can be preempted by another session.
	 */
	private int holdingPrio;
	/**
	 *  L flag:  Corresponds to the "Local Protection Desired" bit
         ([RFC3209]) of the SESSION-ATTRIBUTE Object.  When set, this
         means that the computed path must include links protected with
         Fast Reroute as defined in [RFC4090].
	 */
	private boolean lbit;
	
	//Constructors

	public LSPA(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_LSPA);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_LSPA);
	}
	
	/**
	 * Constructs a new LSPA Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public LSPA(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	//Encode and decode
	
	/**
	 * Encode the LSPA Object
	 */
	public void encode() {
		ObjectLength=20;/* 4 bytes de la cabecera + 4 del cuerpo */
		object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=(byte)(this.excludeAny >>> 24);
		this.object_bytes[5]=(byte)(this.excludeAny >> 16 & 0xff);
		this.object_bytes[6]=(byte)(this.excludeAny >> 8 & 0xff);
		this.object_bytes[7]=(byte)(this.excludeAny & 0xff);	
		this.object_bytes[8]=(byte)(this.includeAny >>> 24);
		this.object_bytes[9]=(byte)(this.includeAny >> 16 & 0xff);
		this.object_bytes[10]=(byte)(this.includeAny >> 8 & 0xff);
		this.object_bytes[11]=(byte)(this.includeAny & 0xff);	
		this.object_bytes[12]=(byte)(this.includeAll >>> 24);
		this.object_bytes[13]=(byte)(this.includeAll >> 16 & 0xff);
		this.object_bytes[14]=(byte)(this.includeAll >> 8 & 0xff);
		this.object_bytes[15]=(byte)(this.includeAll & 0xff);
		this.object_bytes[16]=(byte)(this.setupPrio&0xff);
		this.object_bytes[17]=(byte)(this.holdingPrio&0xff);
		this.object_bytes[18]=(byte)(this.lbit?1:0);
		this.object_bytes[19]=0x00;		
	}

	/**
	 * Decode the LSPA Object
	 */
	public void decode() throws MalformedPCEPObjectException {
		if (ObjectLength<20){
			throw new MalformedPCEPObjectException();
		}
		this.excludeAny =0; 
			for (int k = 0; k < 4; k++) {
				this.excludeAny = (this.excludeAny << 8) | (object_bytes[k+4] & 0xff);
			}
		this.includeAny =0; 
			for (int k = 0; k < 4; k++) {
				this.includeAny = (this.includeAny << 8) | (object_bytes[k+8] & 0xff);
			}					
		this.includeAll =0; 
			for (int k = 0; k < 4; k++) {
				this.includeAll = (this.includeAll << 8) | (object_bytes[k+12] & 0xff);
			}
		this.setupPrio = object_bytes[16];
		this.holdingPrio = object_bytes[17];
		this.lbit = ((object_bytes[18]&0x01)==0x01);
		//Any OPTIONAL TLVs are ignored
	}

	//Getters and Setters
	public int getSetupPrio() {
		return setupPrio;
	}

	public void setSetupPrio(int setupPrio) {
		this.setupPrio = setupPrio;
	}

	public int getHoldingPrio() {
		return holdingPrio;
	}

	public void setHoldingPrio(int holdingPrio) {
		this.holdingPrio = holdingPrio;
	}

	public boolean isLbit() {
		return lbit;
	}

	public void setLbit(boolean lbit) {
		this.lbit = lbit;
	}

	public int getExcludeAny() {
		return excludeAny;
	}

	public void setExcludeAny(int excludeAny) {
		this.excludeAny = excludeAny;
	}

	public int getIncludeAny() {
		return includeAny;
	}

	public void setIncludeAny(int includeAny) {
		this.includeAny = includeAny;
	}

	public int getIncludeAll() {
		return includeAll;
	}

	public void setIncludeAll(int includeAll) {
		this.includeAll = includeAll;
	}
}