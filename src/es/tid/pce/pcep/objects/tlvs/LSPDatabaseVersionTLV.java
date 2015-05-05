package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * LSP-DB-VERSION TLV draft-ietf-pce-stateful-sync-optimizations-00.
 * Encoding: follows draft-ietf-pce-stateful-sync-optimizations-00
 * TLV Type: 5556 (non-standard) 
 * 
 * LSP-DB-VERSION is an optional TLV that MAY be included in the OPEN
   Object when a PCEP Speaker wishes to determine if State
   Synchronization can be skipped when a PCEP session is restarted.  If

   sent from a PCE, the TLV contains the local LSP State Database
   version from the last valid LSP State Report received from a PCC.  If
   sent from a PCC, the TLV contains the PCC's local LSP State Database
   version, which is incremented each time the LSP State Database is
   updated.

   The format of the LSP-DB-VERSION TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |            Length=8           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                      LSP State DB Version                     |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                   Figure 15: LSP-DB-VERSION TLV format

   The type of the TLV is [TBD] and it has a fixed length of 8 octets.
   The value contains a 64-bit unsigned integer.
   
   Ref: http://datatracker.ietf.org/doc/draft-ietf-pce-stateful-pce/?include_text=1
   
   @author jaume
   
 */

public class LSPDatabaseVersionTLV extends PCEPTLV {

	private long LSPStateDBVersion;
	
	
	public LSPDatabaseVersionTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_LSP_DATABASE_VERSION;
	}

	public LSPDatabaseVersionTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}
	

	@Override
	public void encode() {
		log.debug("Encoding LSPDatabaseVersionTLV TLV");
		int length = 8;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		
		this.encodeHeader();
		
		int offset=4;
				
		ByteHandler.LongToBuffer(0,offset*8, 64,LSPStateDBVersion,this.tlv_bytes);
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding LSPDatabaseVersionTLV TLV");
		int offset = 4;
		LSPStateDBVersion = ByteHandler.easyCopyL(0,63,
				this.tlv_bytes[offset + 0],this.tlv_bytes[offset + 1],this.tlv_bytes[offset + 2],this.tlv_bytes[offset + 3],
				this.tlv_bytes[offset + 4],this.tlv_bytes[offset + 5],this.tlv_bytes[offset + 6],this.tlv_bytes[offset + 7]);
		log.debug("Databse Version TLV ID: "+LSPStateDBVersion);
	}
	
	//GETTERS SETTERS
	
	public long getLSPStateDBVersion() {
		return LSPStateDBVersion;
	}

	public void setLSPStateDBVersion(long lSPStateDBVersion) {
		LSPStateDBVersion = lSPStateDBVersion;
	}

}
