package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
  *  Each LSP (path) MUST have a symbolic name that is unique in the PCC.
   This symbolic path name MUST remain constant throughout a path's
   lifetime, which may span across multiple consecutive PCEP sessions
   and/or PCC restarts.  The symbolic path name MAY be specified by an
   operator in a PCC's CLI configuration.  If the operator does not
   specify a symbolic name for a path, the PCC MUST auto-generate one.

   The SYMBOLIC-PATH-NAME TLV MUST be included in the LSP State Report
   when during a given PCEP session an LSP is first reported to a PCE.
   A PCC sends to a PCE the first LSP State Report either during State
   Synchronization, or when a new LSP is configured at the PCC.  The
   symbolic path name MAY be included in subsequent LSP State Reports
   for the LSP.

   The SYMBOLIC-PATH-NAME TLV MAY appear as a TLV in both the LSP Object
   and the LSPA Object.

   The format of the SYMBOLIC-PATH-NAME TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |       Length (variable)       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     //                      Symbolic Path Name                     //
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                 Figure 18: SYMBOLIC-PATH-NAME TLV format

   The type of the TLV is [TBD] and it has a variable length, which MUST
   be greater than 0.

 
 * @author smta fixed by ogondio
 *
 */
public class SymbolicPathNameTLV extends PCEPTLV 
{
	
	
	private byte[] SymbolicPathNameID;
	
	
	public SymbolicPathNameTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_SYMBOLIC_PATH_NAME);
		
	}
	
	public SymbolicPathNameTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		log.debug("Encoding SymbolicPathName TLV");
		this.setTLVValueLength(SymbolicPathNameID.length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		log.debug("TOTA  "+this.getTotalTLVLength()+" SW "+SymbolicPathNameID.length);
		this.encodeHeader();
		int offset=4;
		System.arraycopy(SymbolicPathNameID, 0, this.tlv_bytes, offset, SymbolicPathNameID.length);
	}

	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		
		SymbolicPathNameID = new byte[getTLVValueLength()];
		try
		{
			System.arraycopy(this.tlv_bytes, offset, SymbolicPathNameID, 0, getTLVValueLength());
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
		
		//Check if array zero
		boolean isZero = true;
		for (int i = 0; i < SymbolicPathNameID.length; i++) 
		{
			if (SymbolicPathNameID[i]!=0)
			{
				isZero = false;
				break;
			}	
		}
		
		if (isZero)
		{
			log.error("Received redundancy group identifier value can not be zero");
			//throw new MalformedPCEPObjectException();
		}
		
		//MUST be greater than 0. To check this we look at the first bit
		boolean firstBit = (ByteHandler.easyCopy(0,0,SymbolicPathNameID[0]) == 1) ? true : false ;
		if (firstBit)
		{
			log.error("Received redundancy group identifier value can not be negative");
			throw new MalformedPCEPObjectException();
		}
	}

	public byte[] getSymbolicPathNameID() 
	{
		return SymbolicPathNameID;
	}

	public void setSymbolicPathNameID(byte[] symbolicPathNameID) 
	{
		SymbolicPathNameID = symbolicPathNameID;
	}
	
	public String toString(){
		return SymbolicPathNameID.toString();
	}
	
}