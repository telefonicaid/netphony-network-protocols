package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/*
 * The STATEFUL-PCE-CAPABILITY TLV is an optional TLV for use in the
   OPEN Object for stateful PCE capability negotiation.  Its format is
   shown in the following figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |               Type            |            Length=4           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                             Flags                   |D|T|I|S|U|
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 14: STATEFUL-PCE-CAPABILITY TLV format

   The type of the TLV is 16 and it has a fixed length of 4 octets.

   The value comprises a single field - Flags (32 bits):
   
   0-20	Unassigned	
21	PD-LSP-CAPABILITY (PD-bit)	[RFC8934] (NOT IMPLEMENTED)
22	LSP-SCHEDULING-CAPABILITY (B-bit)	[RFC8934] (NOT IMPLEMENTED)
23	P2MP-LSP-INSTANTIATION-CAPABILITY	[RFC8623] (NOT IMPLEMENTED)
24	P2MP-LSP-UPDATE-CAPABILITY	[RFC8623] (NOT IMPLEMENTED)
25	P2MP-CAPABILITY	[RFC8623] (NOT IMPLEMENTED)
26	TRIGGERED-INITIAL-SYNC	[RFC8232]
27	DELTA-LSP-SYNC-CAPABILITY	[RFC8232]
28	TRIGGERED-RESYNC	[RFC8232]
29	LSP-INSTANTIATION-CAPABILITY (I)	[RFC8281]
30	INCLUDE-DB-VERSION	[RFC8232]
31	LSP-UPDATE-CAPABILITY	[RFC8231] --> U

   U (LSP-UPDATE-CAPABILITY - 1 bit): RFC8231

   S (INCLUDE-DB-VERSION = IDB - 1 bit):  if set to 1 by both PCEP Speakers,
      the PCC will include the LSP-DB-VERSION TLV in each LSP Object.

   I (LSP-INSTANTIATION-CAPABILITY - 1 bit):  defined in
      [I-D.crabbe-pce-pce-initiated-lsp].

   T (TRIGGERED-SYNC - 1 bit):  if set to 1 by both PCEP Speakers, the
      PCE can trigger synchronization of LSPs at any point in the life
      of the session.

   D (DELTA-LSP-SYNC-CAPABILITY - 1 bit):  if set to 1 by a PCEP
      speaker, it indicates that the PCEP speaker allows incremental
      state synchronization.

   Unassigned bits are considered reserved.  They MUST be set to 0 on
   transmission and MUST be ignored on receipt.
   
   @author jimbo, ayk
   
 */


public class StatefulCapabilityTLV extends PCEPTLV 
{
	
	protected boolean uFlag;
	protected boolean sFlag;
	protected boolean iFlag;
	protected boolean tFlag;
	protected boolean dFlag;
	
	public StatefulCapabilityTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_STATEFUL_CAPABILITY;
	}

	public StatefulCapabilityTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{		
		log.debug("Encoding StatefulCapabilityTLV");
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int Zero = 0;
		int offset = 4;
		ByteHandler.IntToBuffer(0,offset * 8, 64,Zero,this.tlv_bytes);
		
		offset += 3;
		ByteHandler.BoolToBuffer(3 + offset * 8,dFlag,this.tlv_bytes);
		ByteHandler.BoolToBuffer(4 + offset * 8,tFlag,this.tlv_bytes);
		ByteHandler.BoolToBuffer(5 + offset * 8,iFlag,this.tlv_bytes);
		ByteHandler.BoolToBuffer(6 + offset * 8,sFlag,this.tlv_bytes);
		ByteHandler.BoolToBuffer(7 + offset * 8,uFlag,this.tlv_bytes);
	}
	
	public void decode()
	{
		log.debug("Decoding StatefulCapabilityTLV");
		int offset = 4;
		
		dFlag = (ByteHandler.easyCopy(3,3,this.tlv_bytes[offset+3]) == 1);
		tFlag = (ByteHandler.easyCopy(4,4,this.tlv_bytes[offset+3]) == 1);
		iFlag = (ByteHandler.easyCopy(5,5,this.tlv_bytes[offset+3]) == 1);
		sFlag = (ByteHandler.easyCopy(6,6,this.tlv_bytes[offset+3]) == 1);
		uFlag = (ByteHandler.easyCopy(7,7,this.tlv_bytes[offset+3]) == 1);
	}
	
	//GETTERS & SETTERS
	
	public boolean issFlag() 
	{
		return sFlag;
	}
	
	public boolean isuFlag() 
	{
		return uFlag;
	}

	public void setSFlag(boolean sFlag) 
	{
		this.sFlag = sFlag;
	}

	public void setUFlag(boolean uFlag) 
	{
		this.uFlag = uFlag;
	}

	public boolean isiFlag() {
		return iFlag;
	}

	public void setIFlag(boolean iFlag) {
		this.iFlag = iFlag;
	}

	public boolean istFlag() {
		return tFlag;
	}

	public void setTFlag(boolean tFlag) {
		this.tFlag = tFlag;
	}

	public boolean isdFlag() {
		return dFlag;
	}

	public void setDFlag(boolean dFlag) {
		this.dFlag = dFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (dFlag ? 1231 : 1237);
		result = prime * result + (iFlag ? 1231 : 1237);
		result = prime * result + (sFlag ? 1231 : 1237);
		result = prime * result + (tFlag ? 1231 : 1237);
		result = prime * result + (uFlag ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatefulCapabilityTLV other = (StatefulCapabilityTLV) obj;
		if (dFlag != other.dFlag)
			return false;
		if (iFlag != other.iFlag)
			return false;
		if (sFlag != other.sFlag)
			return false;
		if (tFlag != other.tFlag)
			return false;
		if (uFlag != other.uFlag)
			return false;
		return true;
	}

	
}
