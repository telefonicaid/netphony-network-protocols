package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/*
 * The Tunnel ID TLV MAY be included in the LSPA object.

   The format of the TUNNEL TLV is shown in the following figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |           Length=4            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             Reserved          |           Tunnel ID           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                      Figure 24: Tunnel-ID TLV format

   The type of the TLV is [TBD] and it has a fixed length of 4 octets.
   The value contains a single field:

   Tunnel ID:  contains the 16-bit 'Tunnel ID' identifier defined in
      [RFC3209], Section 4.6.1.1 for the LSP_TUNNEL_IPv4 Session Object.
      Tunnel ID remains constant over the life time of a tunnel.
      However, when Global Path Protection or Global Default Restoration
      is used, both the primary and secondary LSPs have their own Tunnel
      IDs.
      
      @author jaume
 */

public class TunnelIDTLV extends PCEPTLV
{
	
	protected int tunnelID;
	
	public TunnelIDTLV()
	{
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_TUNNEL_ID;
	}

	public TunnelIDTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException
	{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{
		log.info("Encoding TunnelIDTLV TLV");
		
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset = 4;
		offset += 2;
		ByteHandler.IntToBuffer(0,offset * 8, 16,tunnelID,this.tlv_bytes);
	}
	
	public void decode() 
	{
		log.info("Decoding TunnelIDTLV TLV");
		int offset = 4;
		tunnelID = ByteHandler.easyCopy(0, 15, tlv_bytes[offset+2],tlv_bytes[offset+3]);
	}

}
