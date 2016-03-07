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
 * 
 * http://tools.ietf.org/html/draft-ietf-pce-stateful-pce-9
 * 
 * 
7.3. LSP Object

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
     |                PLSP-ID                |    Flag |    O|A|R|S|D|
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

   Unassigned bits are considered reserved.  They MUST be set to 0 on
   transmission and MUST be ignored on receipt.

   TLVs that may be included in the LSP Object are described in the
   following sections.

 * 
 * @author Fernando Munoz del Nuevo
 * @author Oscar Gonzalez de Dios
 */

public class LSP extends PCEPObject{

	protected int lspId;
	protected boolean dFlag;
	
	protected boolean sFlag;
	
	protected boolean rFlag;
	
	protected boolean aFlag;
	
	protected int opFlags;
	
	private int LSP_sig_type;
	
	
	private SymbolicPathNameTLV symbolicPathNameTLV_tlv = null;
	
	private IPv4LSPIdentifiersTLV lspIdentifiers_tlv = null;
	
	private LSPErrorCodeTLV lspErrorCodes_tlv = null;
	
	private RSVPErrorSpecTLV rsvpErrorSpec_tlv = null;
	
	private LSPDatabaseVersionTLV lspDBVersion_tlv = null;
		
	public LSP(){
		super();
		this.ObjectClass = ObjectParameters.PCEP_OBJECT_CLASS_LSP;
		this.setOT(1);
		
	}
	
	public LSP(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes, offset);
		decode();
	}
	
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
		
		offset += 3;
		
		ByteHandler.IntToBuffer (1, offset*8, 3, opFlags, this.object_bytes);
		ByteHandler.BoolToBuffer(4 + offset*8, aFlag,object_bytes);
		ByteHandler.BoolToBuffer(5 + offset*8, rFlag,object_bytes);
		ByteHandler.BoolToBuffer(6 + offset*8, sFlag,object_bytes);
		ByteHandler.BoolToBuffer(7 + offset*8, dFlag,object_bytes);

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
				
		opFlags = ByteHandler.easyCopy(1,3,object_bytes[7]);
		aFlag = (ByteHandler.easyCopy(4,4,object_bytes[7]) == 1) ? true : false ;
		rFlag = (ByteHandler.easyCopy(5,5,object_bytes[7]) == 1) ? true : false ;
		sFlag = (ByteHandler.easyCopy(6,6,object_bytes[7]) == 1) ? true : false ;
		dFlag = (ByteHandler.easyCopy(7,7,object_bytes[7]) == 1) ? true : false ;
			
		boolean fin;
		int offset = 8;
		
//		LSP_sig_type = ByteHandler.easyCopy(0,7,object_bytes[offset+3]);
//		offset += 4;
		
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
	
	
	//GETTERS & SETTERS

	
	
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
	public boolean isdFlag() 
	{
		return dFlag;
	}
	public void setDFlag(boolean dFlag) 
	{
		this.dFlag = dFlag;
	}
	public boolean issFlag() 
	{
		return sFlag;
	}
	public void setSFlag(boolean sFlag) 
	{
		this.sFlag = sFlag;
	}
	public int getOpFlags() 
	{
		return opFlags;
	}

	public void setOpFlags(int opFlags) 
	{
		this.opFlags = opFlags;
	}

	public int getLSP_sig_type() 
	{
		return LSP_sig_type;
	}
	public void setLSP_sig_type(int lSP_sig_type) 
	{
		LSP_sig_type = lSP_sig_type;
	}
	public boolean isrFlag() 
	{
		return rFlag;
	}
	public void setRFlag(boolean rFlag) 
	{
		this.rFlag = rFlag;
	}
	public boolean isaFlag() 
	{
		return aFlag;
	}
	public void setAFlag(boolean aFlag) 
	{
		this.aFlag = aFlag;
	}
	public LSPDatabaseVersionTLV getLspDBVersion_tlv() 
	{
		return lspDBVersion_tlv;
	}
	public void setLspDBVersion_tlv(LSPDatabaseVersionTLV lspDBVersion_tlv) 
	{
		this.lspDBVersion_tlv = lspDBVersion_tlv;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + LSP_sig_type;
		result = prime * result + (aFlag ? 1231 : 1237);
		result = prime * result + (dFlag ? 1231 : 1237);
		result = prime
				* result
				+ ((lspDBVersion_tlv == null) ? 0 : lspDBVersion_tlv.hashCode());
		result = prime
				* result
				+ ((lspErrorCodes_tlv == null) ? 0 : lspErrorCodes_tlv
						.hashCode());
		result = prime * result + lspId;
		result = prime
				* result
				+ ((lspIdentifiers_tlv == null) ? 0 : lspIdentifiers_tlv
						.hashCode());
		result = prime * result + opFlags;
		result = prime * result + (rFlag ? 1231 : 1237);
		result = prime
				* result
				+ ((rsvpErrorSpec_tlv == null) ? 0 : rsvpErrorSpec_tlv
						.hashCode());
		result = prime * result + (sFlag ? 1231 : 1237);
		result = prime
				* result
				+ ((symbolicPathNameTLV_tlv == null) ? 0
						: symbolicPathNameTLV_tlv.hashCode());
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
		if (LSP_sig_type != other.LSP_sig_type)
			return false;
		if (aFlag != other.aFlag)
			return false;
		if (dFlag != other.dFlag)
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
		if (rFlag != other.rFlag)
			return false;
		if (rsvpErrorSpec_tlv == null) {
			if (other.rsvpErrorSpec_tlv != null)
				return false;
		} else if (!rsvpErrorSpec_tlv.equals(other.rsvpErrorSpec_tlv))
			return false;
		if (sFlag != other.sFlag)
			return false;
		if (symbolicPathNameTLV_tlv == null) {
			if (other.symbolicPathNameTLV_tlv != null)
				return false;
		} else if (!symbolicPathNameTLV_tlv
				.equals(other.symbolicPathNameTLV_tlv))
			return false;

		return true;
	}
	
	

	
	
}
