package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * SR-PCE-CAPABILITY (Type 26) (deprecated)	[RFC8664]
 *    The SR-PCE-CAPABILITY TLV is an optional TLV for use in the OPEN
   Object to negotiate Segment Routing capability on the PCEP session.
  
   
   @author ayk
   
 */


public class SRCapabilityTLV extends PCEPTLV 
{
	
	/*
	 *  The format of the SR-PCE-CAPABILITY TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |            Type=TBD           |            Length=4           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Reserved              |     Flags     |      MSD      |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


                  Figure 1: SR-PCE-CAPABILITY TLV format

   The code point for the TLV type is to be defined by IANA.  The TLV
   length is 4 octets.

   The 32-bit value is formatted as follows.  The "Maximum SID Depth" (1
   octet) field (MSD) specifies the maximum number of SIDs that a PCC is
   capable of imposing on a packet.  The "Flags" (1 octet) and
   "Reserved" (2 octets) fields are currently unused, and MUST be set to
   zero and ignored on receipt.
	 */
	protected int MSD;
	
	public SRCapabilityTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_SR_CAPABILITY;
	}

	public SRCapabilityTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{		
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int Zero = 0;
		int offset = 4;
		log.debug("Encoding SRCapabilityTLV: MSD ="+MSD+" bytes: "+this.getTotalTLVLength());

		ByteHandler.IntToBuffer(0,offset * 8, 32,Zero,this.tlv_bytes);
		
		
		//Last octet MSD
		byte[] aux = new byte[1];
		aux[0] = (byte)(MSD & 0x000000ff);
		System.arraycopy(aux, 0, tlv_bytes, 7, 1);
		log.debug("finished Encoding SRCapabilityTLV: MSD ="+MSD);
	}
	
	public void decode()
	{

		log.debug("Decoding SRCapabilityTLV");
		int offset = 7;
		//TODO: No se si lo hace bien
		byte[] aux = new byte[1];
		System.arraycopy(this.tlv_bytes,offset, aux, 0, 1);
		MSD = (aux[0]&0xff);
		log.debug("MSD decoded, value: "+MSD);
	}

	public int getMSD() 
	{		
		return this.MSD;
	}

	public void setMSD(int MSD) 
	{
		this.MSD = MSD;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + MSD;
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
		SRCapabilityTLV other = (SRCapabilityTLV) obj;
		if (MSD != other.MSD)
			return false;
		return true;
	}
	
	
	

}
