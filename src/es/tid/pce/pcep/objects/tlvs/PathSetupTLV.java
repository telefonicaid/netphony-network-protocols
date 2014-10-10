package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/*
 *    The SR-PCE-CAPABILITY TLV is an optional TLV for use in the OPEN
   Object to negotiate Segment Routing capability on the PCEP session.
   The format of the SR-PCE-CAPABILITY TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |            Type=TBD           |            Length=4           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Reserved              |     Flags     |      PST      |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


                  Figure 1: SR-PCE-CAPABILITY TLV format

   The code point for the TLV type is to be defined by IANA.  The TLV
   length is 4 octets.

   The 32-bit value is formatted as follows.  The "Maximum SID Depth" (1
   octet) field (PST) specifies the maximum number of SIDs that a PCC is
   capable of imposing on a packet.  The "Flags" (1 octet) and
   "Reserved" (2 octets) fields are currently unused, and MUST be set to
   zero and ignored on receipt.
   
   @author ayk
   
 */


public class PathSetupTLV extends PCEPTLV 
{
	
	/*
	 *  Path setup options
	 */
	public static final int DEFAULT = 0;
	public static final int SR = 1;	
	
	protected int PST;
	
	public PathSetupTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_PATH_SETUP;
	}

	public PathSetupTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{		
		this.TotalTLVLength = 8;
		this.TLVValueLength = 4;
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int Zero = 0;
		int offset = 4;
		log.info("Encoding SRCapabilityTLV: PST ="+PST+" bytes: "+this.getTotalTLVLength());

		ByteHandler.IntToBuffer(0,offset * 8, 32,Zero,this.tlv_bytes);
		
		
		//Last octet PST
		byte[] aux = new byte[1];
		aux[0] = (byte)(PST & 0x000000ff);
		System.arraycopy(aux, 0, tlv_bytes, 7, 1);
		log.info("finished Encoding PathSetupTLV: PST ="+PST);		
	}
	
	public void decode()
	{

		log.info("Decoding PathSetupTLV");
		int offset = 7;
		//TODO: No se si lo hace bien
		byte[] aux = new byte[1];
		System.arraycopy(this.tlv_bytes,offset, aux, 0, 1);
//		PST = tlv_bytes[offset]
//		PST = ByteHandler.easyCopy(0,7,this.tlv_bytes[offset]);
		PST = (aux[0]&0xff);
		log.info("PST decoded, value: "+PST);
	}

	public int getPST() 
	{		
		return this.PST;
	}

	public void setPST(int PST) 
	{
		this.PST = PST;
	}
	
	public boolean isSR()
	{
		return (this.PST == PathSetupTLV.SR);
	}

}
