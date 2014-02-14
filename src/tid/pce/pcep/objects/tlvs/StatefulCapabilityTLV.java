package tid.pce.pcep.objects.tlvs;

import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;
import tid.protocol.commons.ByteHandler;

/*
 * The STATEFUL-PCE-CAPABILITY TLV is an optional TLV for use in the
   OPEN Object for stateful PCE capability negotiation.  Its format is
   shown in the following figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |               Type=[TBD]      |            Length=4           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                             Flags                         |S|U|
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 14: STATEFUL-PCE-CAPABILITY TLV format

   The type of the TLV is [TBD] and it has a fixed length of 4 octets.

   The value comprises a single field - Flags (32 bits):

   U (LSP-UPDATE-CAPABILITY - 1 bit):  if set to 1 by a PCC, the U Flag
      indicates that the PCC allows modification of LSP parameters; if
      set to 1 by a PCE, the U Flag indicates that the PCE is capable of
      updating LSP parameters.  The LSP-UPDATE-CAPABILITY Flag must be
      advertised by both a PCC and a PCE for PCUpd messages to be
      allowed on a PCEP session.

   S (INCLUDE-DB-VERSION - 1 bit):  if set to 1 by both PCEP Speakers,
      the PCC will include the LSP-DB-VERSION TLV in each LSP Object.

   Unassigned bits are considered reserved.  They MUST be set to 0 on
   transmission and MUST be ignored on receipt.
   
   @author jaume
   
 */


public class StatefulCapabilityTLV extends PCEPTLV 
{
	
	protected boolean sFlag;

	protected boolean uFlag;
	
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
		log.fine("Encoding StatefulCapabilityTLV");
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int Zero = 0;
		int offset = 4;
		ByteHandler.IntToBuffer(0,offset * 8, 64,Zero,this.tlv_bytes);
		
		offset += 3;
		ByteHandler.BoolToBuffer(6 + offset * 8,sFlag,this.tlv_bytes);
		ByteHandler.BoolToBuffer(7 + offset * 8,uFlag,this.tlv_bytes);
	}
	
	public void decode()
	{
		log.fine("Decoding StatefulCapabilityTLV");
		int offset = 4;
		sFlag = (ByteHandler.easyCopy(6,6,this.tlv_bytes[offset+3]) == 1) ? true : false ;
		uFlag = (ByteHandler.easyCopy(7,7,this.tlv_bytes[offset+3]) == 1) ? true : false ;
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

	public void setsFlag(boolean sFlag) 
	{
		this.sFlag = sFlag;
	}

	public void setuFlag(boolean uFlag) 
	{
		this.uFlag = uFlag;
	}

}
