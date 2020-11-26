package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.util.Hashtable;

import es.tid.pce.pcep.objects.tlvs.LSPDatabaseVersionTLV;
import es.tid.pce.pcep.objects.tlvs.LSPErrorCodeTLV;
import es.tid.pce.pcep.objects.tlvs.IPv4LSPIdentifiersTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.RSVPErrorSpecTLV;
import es.tid.pce.pcep.objects.tlvs.SymbolicPathNameTLV;
import es.tid.protocol.commons.ByteHandler;



/**
 * LSP Object
 * @see <a href="https://tools.ietf.org/html/rfc8231">RFC 8231</a>
 * @author Fernando Munoz del Nuevo
 * @author Oscar Gonzalez de Dios
 */
public class LSP extends PCEPObject{
	
	/*
	 * 7.3. LSP Object

The LSP object MUST be present within PCRpt and PCUpd messages.  The
   LSP object MAY be carried within PCReq and PCRep messages if the
   stateful PCE capability has been negotiated on the session.  The LSP
   object contains a set of fields used to specify the target LSP, the
   operation to be performed on the LSP, and LSP Delegation.  It also
   contains a flag indicating to a PCE that the LSP state
   synchronization is in progress.  This document focuses on LSPs that
   are signaled with RSVP, many of the TLVs used with the LSP object
   mirror RSVP state.

   LSP Object-Class is [TBD].

   LSP Object-Type is 1.

   The format of the LSP object body is shown in Figure 11:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                PLSP-ID                |Flags  |C|    O|A|R|S|D|
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                        TLVs                                 //
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                     Figure 11: The LSP Object format

   PLSP-ID (20 bits): A PCEP-specific identifier for the LSP.  A PCC
   creates a unique PLSP-ID for each LSP that is constant for the
   lifetime of a PCEP session.  The PCC will advertise the same PLSP-ID
   on all PCEP sessions it maintains at a given times.  The mapping of
   the Symbolic Path Name to PLSP-ID is communicated to the PCE by
   sending a PCRpt message containing the SYMBOLIC-PATH-NAME TLV.  All
   subsequent PCEP messages then address the LSP by the PLSP-ID.  The
   values of 0 and 0xFFFFF are reserved.  Note that the PLSP-ID is a
   value that is constant for the lifetime of the PCEP session, during
   which time for an RSVP-signaled LSP there might be a different RSVP
   identifiers (LSP-id, tunnel-id) allocated it.

   Flags (12 bits):

   D (Delegate - 1 bit):  on a PCRpt message, the D Flag set to 1
      indicates that the PCC is delegating the LSP to the PCE.  On a
      PCUpd message, the D flag set to 1 indicates that the PCE is
      confirming the LSP Delegation.  To keep an LSP delegated to the
      PCE, the PCC must set the D flag to 1 on each PCRpt message for
      the duration of the delegation - the first PCRpt with the D flag
      set to 0 revokes the delegation.  To keep the delegation, the PCE
      must set the D flag to 1 on each PCUpd message for the duration of
      the delegation - the first PCUpd with the D flag set to 0 returns
      the delegation.

   S (SYNC - 1 bit):  the S Flag MUST be set to 1 on each PCRpt sent
      from a PCC during State Synchronization.  The S Flag MUST be set
      to 0 in other PCRpt messages sent from the PCC.

   R(Remove - 1 bit):  On PCRpt messages the R Flag indicates that the
      LSP has been removed from the PCC and the PCE SHOULD remove all
      state from its database.  Upon receiving an LSP State Report with
      the R Flag set to 1 for an RSVP-signaled LSP, the PCE SHOULD
      remove all state for the path identified by the LSP Identifiers
      TLV from its database.  When the all-zeros LSP-IDENTIFIERS-TLV is
      used, the PCE SHOULD remove all state for the PLSP-ID from its
      database.

   A(Administrative - 1 bit):  On PCRpt messages, the A Flag indicates
      the PCC's target operational status for this LSP.  On PCUpd
      messages, the A Flag indicates the LSP status that the PCE desires
      for this LSP.  In both cases, a value of '1' means that the
      desired operational state is active, and a value of '0' means that
      the desired operational state is inactive.  A PCC ignores the A
      flag on a PCUpd message unless the operator's policy allows the
      PCE to control the corresponding LSP's administrative state.

   O(Operational - 3 bits):  On PCRpt messages, the O Field represents
      the operational status of the LSP.

      The following values are defined:

      0 - DOWN:  not active.

      1 - UP:  signalled.

      2 - ACTIVE:  up and carrying traffic.

      3 - GOING-DOWN:  LSP is being torn down, resources are being
         released.

      4 - GOING-UP:  LSP is being signalled.

      5-7 - Reserved:  these values are reserved for future use.
      
   c (Create - 1 bit): A new flag, the Create (C) flag is introduced.  On a PCRpt message,
   the C Flag set to 1 indicates that this LSP was created via a
   PCInitiate message.  The C Flag MUST be set to 1 on each PCRpt
   message for the duration of existence of the LSP.  The Create flag
   allows PCEs to be aware of which LSPs were PCE-initiated (a state
   that would otherwise only be known by the PCC and the PCE that
   initiated them).

   Unassigned bits are considered reserved.  They MUST be set to 0 on
   transmission and MUST be ignored on receipt.

   TLVs that may be included in the LSP Object are described in the
   following sections.
   */ 
	
	/*
   FLAGS: 
   0	Unassigned	
1	ERO-compression	[RFC8623]
2	Fragmentation	[RFC8623]
3	P2MP	[RFC8623]
4	Create	[RFC8281]
5-7	Operational (3 bits)	[RFC8231]
8	Administrative	[RFC8231]
9	Remove	[RFC8231]
10	SYNC	[RFC8231]
11	Delegate	[RFC8231]
	 */

	protected int lspId;
	
	protected boolean delegateFlag=false;
	
	protected boolean syncFlag=false;
	
	protected boolean removeFlag=false;
	
	protected boolean administrativeFlag=false;
	
	protected int opFlags;
	
	protected boolean createFlag=false;
	
	protected boolean p2mpFlag=false;
	
	protected boolean fragmentationFlag=false;
	
	protected boolean eroCompressionFlag=false;
	
	/* 
	 * TLVs
	 */
	
	
	private SymbolicPathNameTLV symbolicPathNameTLV_tlv = null;
	
	private IPv4LSPIdentifiersTLV lspIdentifiers_tlv = null;
	
	private LSPErrorCodeTLV lspErrorCodes_tlv = null;
	
	private RSVPErrorSpecTLV rsvpErrorSpec_tlv = null;
	
	private LSPDatabaseVersionTLV lspDBVersion_tlv = null;
	
	/* 
	 * Constructors
	 */
		
	public LSP(){
		super();
		this.ObjectClass = ObjectParameters.PCEP_OBJECT_CLASS_LSP;
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_LSP);	
	}
	
	public LSP(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes, offset);
		decode();
	}
	
	/* 
	 * Encode and decode 
	 */
	
	@Override
	public void encode() 
	{
		
		ObjectLength = 8;
		if (symbolicPathNameTLV_tlv!=null){
			symbolicPathNameTLV_tlv.encode();
			ObjectLength=ObjectLength+symbolicPathNameTLV_tlv.getTotalTLVLength();
		}
		if (lspIdentifiers_tlv!=null){
			lspIdentifiers_tlv.encode();
			ObjectLength=ObjectLength+lspIdentifiers_tlv.getTotalTLVLength();
		}
		if (lspErrorCodes_tlv!=null){
			lspErrorCodes_tlv.encode();
			ObjectLength=ObjectLength+lspErrorCodes_tlv.getTotalTLVLength();
		}
		if (rsvpErrorSpec_tlv!=null){
			rsvpErrorSpec_tlv.encode();
			ObjectLength=ObjectLength+rsvpErrorSpec_tlv.getTotalTLVLength();
		}
	
		if (lspDBVersion_tlv!=null){
			lspDBVersion_tlv.encode();
			ObjectLength=ObjectLength+lspDBVersion_tlv.getTotalTLVLength();
		}
		object_bytes = new byte[ObjectLength];
		encode_header();
		
		int offset = 4;
		
		
		ByteHandler.IntToBuffer(12,offset*8, 20,lspId,this.object_bytes);

		offset += 2;
		ByteHandler.BoolToBuffer(5 + offset*8, eroCompressionFlag,object_bytes);
		ByteHandler.BoolToBuffer(6 + offset*8, fragmentationFlag,object_bytes);
		ByteHandler.BoolToBuffer(7 + offset*8, p2mpFlag,object_bytes);
		offset+=1;
		
		ByteHandler.BoolToBuffer(0 + offset*8, createFlag,object_bytes);
		//ByteHandler.IntToBuffer (0, 1 + offset*8, 3, opFlags, this.object_bytes);
		ByteHandler.IntToBuffer (29, 1+offset*8, 3, opFlags, this.object_bytes);
		ByteHandler.BoolToBuffer(4 + offset*8, administrativeFlag,object_bytes);
		ByteHandler.BoolToBuffer(5 + offset*8, removeFlag,object_bytes);
		ByteHandler.BoolToBuffer(6 + offset*8, syncFlag,object_bytes);
		ByteHandler.BoolToBuffer(7 + offset*8, delegateFlag,object_bytes);

//		offset += 1;
//		offset += 3;
//		
//		ByteHandler.IntToBuffer(0,offset*8, 8, LSP_sig_type, this.object_bytes);
		
		offset += 1;
		
		if (symbolicPathNameTLV_tlv!=null){
			System.arraycopy(symbolicPathNameTLV_tlv.getTlv_bytes(),0,this.object_bytes,offset,symbolicPathNameTLV_tlv.getTotalTLVLength());
			offset=offset+symbolicPathNameTLV_tlv.getTotalTLVLength();		
		}
		if (lspIdentifiers_tlv!=null){
			System.arraycopy(lspIdentifiers_tlv.getTlv_bytes(),0,this.object_bytes,offset,lspIdentifiers_tlv.getTotalTLVLength());
			offset=offset+lspIdentifiers_tlv.getTotalTLVLength();
		}
		if (lspErrorCodes_tlv!=null){
			System.arraycopy(lspErrorCodes_tlv.getTlv_bytes(),0,this.object_bytes,offset,lspErrorCodes_tlv.getTotalTLVLength());
			offset=offset+lspErrorCodes_tlv.getTotalTLVLength();
		}
		if (rsvpErrorSpec_tlv!=null){
			System.arraycopy(rsvpErrorSpec_tlv.getTlv_bytes(),0,this.object_bytes,offset,rsvpErrorSpec_tlv.getTotalTLVLength());
			offset=offset+rsvpErrorSpec_tlv.getTotalTLVLength();
		}

		if (lspDBVersion_tlv!=null){
			System.arraycopy(lspDBVersion_tlv.getTlv_bytes(),0,this.object_bytes,offset,lspDBVersion_tlv.getTotalTLVLength());
			offset=offset+lspDBVersion_tlv.getTotalTLVLength();
		}	
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {		
		symbolicPathNameTLV_tlv = null;
		lspIdentifiers_tlv = null;
		lspErrorCodes_tlv = null;
		rsvpErrorSpec_tlv = null;
		lspDBVersion_tlv = null;
		
		if (ObjectLength<8){
			throw new MalformedPCEPObjectException();
		}
		
		lspId = ByteHandler.easyCopy(0,19,object_bytes[4],object_bytes[5],object_bytes[6]);
		p2mpFlag=(object_bytes[6]&0x01)==0x01;
		fragmentationFlag=(object_bytes[6]&0x02)==0x02;
		eroCompressionFlag=(object_bytes[6]&0x4)==0x04;
		createFlag = (ByteHandler.easyCopy(0,0,object_bytes[7]) == 1) ? true : false ;
		log.debug("cFlag="+createFlag);
		opFlags = ByteHandler.easyCopy(1,3,object_bytes[7]);
		log.debug("opFlag="+opFlags);
		administrativeFlag = (ByteHandler.easyCopy(4,4,object_bytes[7]) == 1) ? true : false ;
		removeFlag = (ByteHandler.easyCopy(5,5,object_bytes[7]) == 1) ? true : false ;
		syncFlag = (ByteHandler.easyCopy(6,6,object_bytes[7]) == 1) ? true : false ;
		delegateFlag = (ByteHandler.easyCopy(7,7,object_bytes[7]) == 1) ? true : false ;
			
		boolean fin;
		int offset = 8;
		
		if (ObjectLength==8){
			fin=true;
		}else {
			fin = false;
		}
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);

			switch (tlvtype){
				case ObjectParameters.PCEP_TLV_TYPE_SYMBOLIC_PATH_NAME:
					symbolicPathNameTLV_tlv=new SymbolicPathNameTLV(this.getObject_bytes(), offset);
					break;
				case ObjectParameters.PCEP_TLV_TYPE_IPV4_LSP_IDENTIFIERS:
					lspIdentifiers_tlv =new IPv4LSPIdentifiersTLV(this.getObject_bytes(), offset);
					break;
				case ObjectParameters.PCEP_TLV_TYPE_LSP_ERROR_CODE:
					lspErrorCodes_tlv =new LSPErrorCodeTLV(this.getObject_bytes(), offset);
					break;
				case ObjectParameters.PCEP_TLV_TYPE_RSVP_ERROR_SPEC:
					rsvpErrorSpec_tlv =new RSVPErrorSpecTLV(this.getObject_bytes(), offset);
					break;
				case ObjectParameters.PCEP_TLV_TYPE_LSP_DATABASE_VERSION:
					lspDBVersion_tlv =new LSPDatabaseVersionTLV(this.getObject_bytes(), offset);
					break;
				
				/*
				 * In the future Delegation TLV will be here
				 */
				 
				default:
					log.warn("Unknown or unexpected TLV found");
					//FIXME: Que hacemos con los desconocidos
					break;
			}
			
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
		
	}
	
	/*
	 * GETTERS, SETTERS, HASH & EQUALS
	 * (autogenerated with eclipe) 
	 */
	
	public int getLspId() 
	{
		return lspId;
	}
	public SymbolicPathNameTLV getSymbolicPathNameTLV_tlv() 
	{
		return symbolicPathNameTLV_tlv;
	}

	public void setSymbolicPathNameTLV_tlv(
			SymbolicPathNameTLV symbolicPathNameTLV_tlv) 
	{
		this.symbolicPathNameTLV_tlv = symbolicPathNameTLV_tlv;
	}

	public IPv4LSPIdentifiersTLV getLspIdentifiers_tlv() 
	{
		return lspIdentifiers_tlv;
	}
	
	public void setLspIdentifiers_tlv(IPv4LSPIdentifiersTLV lspIdentifiers_tlv) 
	{
		this.lspIdentifiers_tlv = lspIdentifiers_tlv;
	}

	public LSPErrorCodeTLV getLspErrorCodes_tlv() 
	{
		return lspErrorCodes_tlv;
	}

	public void setLspErrorCodes_tlv(LSPErrorCodeTLV lspErrorCodes_tlv) 
	{
		this.lspErrorCodes_tlv = lspErrorCodes_tlv;
	}

	public RSVPErrorSpecTLV getRsvpErrorSpec_tlv() 
	{
		return rsvpErrorSpec_tlv;
	}

	public void setRsvpErrorSpec_tlv(RSVPErrorSpecTLV rsvpErrorSpec_tlv) 
	{
		this.rsvpErrorSpec_tlv = rsvpErrorSpec_tlv;
	}

	public void setLspId(int lspId) 
	{
		this.lspId = lspId;
	}
	public boolean iscFlag() 
	{
		return createFlag;
	}
	public void setCFlag(boolean cFlag) 
	{
		this.createFlag = cFlag;
	}
	public boolean isdFlag() 
	{
		return delegateFlag;
	}
	public void setDFlag(boolean dFlag) 
	{
		this.delegateFlag = dFlag;
	}
	public boolean issFlag() 
	{
		return syncFlag;
	}
	public void setSFlag(boolean sFlag) 
	{
		this.syncFlag = sFlag;
	}
	public int getOpFlags() 
	{
		return opFlags;
	}

	public void setOpFlags(int opFlags) 
	{
		this.opFlags = opFlags;
	}

	public boolean isrFlag() 
	{
		return removeFlag;
	}
	public void setRFlag(boolean rFlag) 
	{
		this.removeFlag = rFlag;
	}
	public boolean isaFlag() 
	{
		return administrativeFlag;
	}
	public void setAFlag(boolean aFlag) 
	{
		this.administrativeFlag = aFlag;
	}
	public LSPDatabaseVersionTLV getLspDBVersion_tlv() 
	{
		return lspDBVersion_tlv;
	}
	public void setLspDBVersion_tlv(LSPDatabaseVersionTLV lspDBVersion_tlv) 
	{
		this.lspDBVersion_tlv = lspDBVersion_tlv;
	}
	
	public boolean isP2mpFlag() {
		return p2mpFlag;
	}

	public void setP2mpFlag(boolean p2mpFlag) {
		this.p2mpFlag = p2mpFlag;
	}

	public boolean isFragmentationFlag() {
		return fragmentationFlag;
	}

	public void setFragmentationFlag(boolean fragmentationFlag) {
		this.fragmentationFlag = fragmentationFlag;
	}

	public boolean isEroCompressionFlag() {
		return eroCompressionFlag;
	}

	public void setEroCompressionFlag(boolean eroCompressionFlag) {
		this.eroCompressionFlag = eroCompressionFlag;
	}
	
	public boolean isDelegateFlag() {
		return delegateFlag;
	}

	public void setDelegateFlag(boolean delegateFlag) {
		this.delegateFlag = delegateFlag;
	}

	public boolean isSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(boolean syncFlag) {
		this.syncFlag = syncFlag;
	}

	public boolean isRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(boolean removeFlag) {
		this.removeFlag = removeFlag;
	}

	public boolean isAdministrativeFlag() {
		return administrativeFlag;
	}

	public void setAdministrativeFlag(boolean administrativeFlag) {
		this.administrativeFlag = administrativeFlag;
	}

	public boolean isCreateFlag() {
		return createFlag;
	}

	public void setCreateFlag(boolean createFlag) {
		this.createFlag = createFlag;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (administrativeFlag ? 1231 : 1237);
		result = prime * result + (createFlag ? 1231 : 1237);
		result = prime * result + (delegateFlag ? 1231 : 1237);
		result = prime * result + (eroCompressionFlag ? 1231 : 1237);
		result = prime * result + (fragmentationFlag ? 1231 : 1237);
		result = prime * result + ((lspDBVersion_tlv == null) ? 0 : lspDBVersion_tlv.hashCode());
		result = prime * result + ((lspErrorCodes_tlv == null) ? 0 : lspErrorCodes_tlv.hashCode());
		result = prime * result + lspId;
		result = prime * result + ((lspIdentifiers_tlv == null) ? 0 : lspIdentifiers_tlv.hashCode());
		result = prime * result + opFlags;
		result = prime * result + (p2mpFlag ? 1231 : 1237);
		result = prime * result + (removeFlag ? 1231 : 1237);
		result = prime * result + ((rsvpErrorSpec_tlv == null) ? 0 : rsvpErrorSpec_tlv.hashCode());
		result = prime * result + ((symbolicPathNameTLV_tlv == null) ? 0 : symbolicPathNameTLV_tlv.hashCode());
		result = prime * result + (syncFlag ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LSP other = (LSP) obj;
		if (administrativeFlag != other.administrativeFlag)
			return false;
		if (createFlag != other.createFlag)
			return false;
		if (delegateFlag != other.delegateFlag)
			return false;
		if (eroCompressionFlag != other.eroCompressionFlag)
			return false;
		if (fragmentationFlag != other.fragmentationFlag)
			return false;
		if (lspDBVersion_tlv == null) {
			if (other.lspDBVersion_tlv != null)
				return false;
		} else if (!lspDBVersion_tlv.equals(other.lspDBVersion_tlv))
			return false;
		if (lspErrorCodes_tlv == null) {
			if (other.lspErrorCodes_tlv != null)
				return false;
		} else if (!lspErrorCodes_tlv.equals(other.lspErrorCodes_tlv))
			return false;
		if (lspId != other.lspId)
			return false;
		if (lspIdentifiers_tlv == null) {
			if (other.lspIdentifiers_tlv != null)
				return false;
		} else if (!lspIdentifiers_tlv.equals(other.lspIdentifiers_tlv))
			return false;
		if (opFlags != other.opFlags)
			return false;
		if (p2mpFlag != other.p2mpFlag)
			return false;
		if (removeFlag != other.removeFlag)
			return false;
		if (rsvpErrorSpec_tlv == null) {
			if (other.rsvpErrorSpec_tlv != null)
				return false;
		} else if (!rsvpErrorSpec_tlv.equals(other.rsvpErrorSpec_tlv))
			return false;
		if (symbolicPathNameTLV_tlv == null) {
			if (other.symbolicPathNameTLV_tlv != null)
				return false;
		} else if (!symbolicPathNameTLV_tlv.equals(other.symbolicPathNameTLV_tlv))
			return false;
		if (syncFlag != other.syncFlag)
			return false;
		return true;
	}

	/*
	 * toString
	 * Use this method to represent the most significant information of the object 
	 */
	
	public String toString(){
		StringBuffer sb=new StringBuffer(100);
		sb.append("<LSP id = ");
		sb.append(lspId);	
		if (symbolicPathNameTLV_tlv!=null){
			sb.append(symbolicPathNameTLV_tlv.toString());
		}
		sb.append(">");
		return sb.toString();	
	}
	
	

	
	
}
