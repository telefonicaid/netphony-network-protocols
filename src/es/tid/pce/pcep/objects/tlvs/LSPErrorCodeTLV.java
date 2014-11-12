package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/*
 * If an LSP Update Request failed, an LSP State Report MUST be sent to
   all connected stateful PCEs.  LSP State Report MUST contain the LSP
   Error Code TLV, indicating the cause of the failure.

   The format of the LSP-ERROR-CODE TLV is shown in the following

Crabbe, et al.          Expires November 9, 2013               [Page 41]
Internet-Draft      PCEP Extensions for Stateful PCE            May 2013

   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |            Length=4           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          Error Code                           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                   Figure 21: LSP-ERROR-CODE TLV format

   The type of the TLV is [TBD] and it has a fixed length of 4 octets.
   The value contains the error code that indicates the cause of the
   failure.  Error codes will be defined in a later revision of this
   
   @author jaume
 */

public class LSPErrorCodeTLV extends PCEPTLV 
{
	protected int errorCode;
	
	public LSPErrorCodeTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_LSP_ERROR_CODE;
	}

	public LSPErrorCodeTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{
		log.debug("Encoding LSPErrorCodeTLV TLV");
		
		int length=4;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset = 4;
		ByteHandler.IntToBuffer(0,offset * 8, 32,errorCode,this.tlv_bytes);
		
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding LSPErrorCodeTLV TLV");
		int offset = 4;
		errorCode = ByteHandler.easyCopy(0, 31, tlv_bytes[offset],tlv_bytes[offset+1],
				tlv_bytes[offset+2],tlv_bytes[offset+3]);
	}

}
