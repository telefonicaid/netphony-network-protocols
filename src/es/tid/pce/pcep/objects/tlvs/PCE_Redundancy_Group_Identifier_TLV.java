package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/*
 * PREDUNDANCY-GROUP-ID is an optional TLV that MAY be included in the
   OPEN Object when a PCEP Speaker wishes to determine if State
   Synchronization can be skipped when a PCEP session is restarted.  It
   contains a unique identifier for the node that does not change during
   the life time of the PCEP Speaker.  It identifies the PCEP Speaker to
   its peers if the Speaker's IP address changed.

   The format of the PREDUNDANCY-GROUP-ID TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=[TBD]          |       Length (variable)       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     //                 PCE redundancy group Identifier              //
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 16: PREDUNDANCY-GROUP-ID TLV format

   The type of the TLV is [TBD] and it has a a variable length, which

   MUST be greater than 0.  The value contains a node identifier that
   MUST be unique in the network.  The node identifier MAY be configured
   by an operator.  If the node identifier is not configured by the
   operator, it can be derived from a PCC's MAC address or serial
   number.
   
   @author jaume
 */

public class PCE_Redundancy_Group_Identifier_TLV extends PCEPTLV 
{
	private byte[] redundancyId;


	public PCE_Redundancy_Group_Identifier_TLV()
	{
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_PCE_REDUNDANCY_GROUP_INDENTIFIER;
	}

	public PCE_Redundancy_Group_Identifier_TLV(byte[] bytes, int offset)throws MalformedPCEPObjectException
	{		
		super(bytes,offset);		
		decode();
	}
	
	
	@Override
	public void encode() 
	{
		
		log.fine("Encoding PCE_Redundancy_Group_Identifier_TLV TLV");
		
		//You have to set the correct TLVValueLength length before encoding!!
		
		int length = redundancyId.length;
		
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset = 4;
		log.fine("Redundancy ID length is: "+ redundancyId.length);
		
		System.arraycopy(redundancyId, 0, this.tlv_bytes, offset, redundancyId.length);	
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.fine("Decoding PCE_Redundancy_Group_Identifier_TLV TLV");
		
		redundancyId = new byte[this.getTotalTLVLength()-4];
		int offset = 4;
		
		try
		{
			System.arraycopy(this.tlv_bytes, offset, redundancyId, 0, redundancyId.length);
			
			log.finest("Redundancy ID is : ");
		}
		catch (Exception e)
		{
			log.severe("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();
		}
		//Check if array zero
		boolean isZero = true;
		for (int i = 0; i < redundancyId.length; i++) 
		{
			if (redundancyId[i]!=0)
			{
				isZero = false;
				break;
			}
				
		}
		
		if (isZero)
		{
			log.finest("Received redundancy group identifier value can not be zero");
			//throw new MalformedPCEPObjectException();
		}
		
		//MUST be greater than 0. To check this we look at the first bit
		boolean firstBit = (ByteHandler.easyCopy(0,0,redundancyId[0]) == 1) ? true : false ;
		if (firstBit)
		{
			log.finest("Received redundancy group identifier value can not be negative");
			throw new MalformedPCEPObjectException();
		}
		
		
	}
	
	public byte[] getRedundancyId()
	{
		return redundancyId;
	}

	public void setRedundancyId(byte[] redundancyId)
	{
		this.redundancyId = redundancyId;
	}

}
