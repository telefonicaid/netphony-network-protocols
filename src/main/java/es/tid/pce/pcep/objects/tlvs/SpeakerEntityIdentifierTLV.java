package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
 * Speaker Entity Identifier TLV (Type 24)
 * 
 * The Speaker Entity Identifier TLV (SPEAKER-ENTITY-ID) is an optional
   TLV that MAY be included in the OPEN object when a PCEP speaker
   wishes to determine if State Synchronization can be skipped when a
   PCEP session is restarted.  It contains a unique identifier for the
   node that does not change during the lifetime of the PCEP speaker.
   It identifies the PCEP speaker to its peers even if the speaker's IP
   address is changed.

   In case of a remote peer IP address change, a PCEP speaker would
   learn the Speaker Entity Identifier on receiving the open message,
   but it MAY have already sent its open message without realizing that
   it is a known PCEP peer.  In such a case, either a full
   synchronization is done or the PCEP session is terminated.  This may
   be a local policy decision.  The new IP address is associated with
   the Speaker Entity Identifier for the future either way.  In the
   latter case when the PCEP session is re-established, it would be
   correctly associated with the Speaker Entity Identifier and not be
   considered as an unknown peer.

   The format of the SPEAKER-ENTITY-ID TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=24             |       Length (variable)       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     //                 Speaker Entity Identifier                    //
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 5: SPEAKER-ENTITY-ID TLV Format

   The type of the TLV is 24, and it has a variable length, which MUST
   be greater than 0.  The value is padded to a 4-octet alignment.  The
   padding is not included in the Length field.  The value contains the
   Speaker Entity Identifier (an identifier of the PCEP speaker
   transmitting this TLV).  This identifier is required to be unique
   within its scope of visibility, which is usually limited to a single
   domain.  It MAY be configured by the operator.  Alternatively, it can
   be derived automatically from a suitably stable unique identifier,

   such as a Media Access Control (MAC) address, serial number, Traffic
   Engineering Router ID, or similar.  In the case of inter-domain
   connections, the speaker SHOULD prefix its usual identifier with the
   domain identifier of its residence, such as an Autonomous System
   number, an IGP area identifier, or similar to make sure it remains
   unique.

   The relationship between this identifier and entities in the Traffic
   Engineering database is intentionally left undefined.

   From a manageability point of view, a PCE or PCC implementation
   SHOULD allow the operator to configure this Speaker Entity
   Identifier.

   If a PCEP speaker receives the SPEAKER-ENTITY-ID on a new PCEP
   session, that matches with an existing alive PCEP session, the PCEP
   speaker MUST send a PCErr with Error-type=20 and Error-value=7
   'Received an invalid Speaker Entity Identifier', and close the PCEP
   session.

 
 * @author Oscar Gonzalez de Dios
 *
 */
public class SpeakerEntityIdentifierTLV extends PCEPTLV 
{
	
	
	private byte[] speakerEntityIdentifier;
	
	
	public SpeakerEntityIdentifierTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_SPEAKER_ENTITY_ID);
		
	}
	
	public SpeakerEntityIdentifierTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		log.debug("Encoding Speaker Entity Identifier TLV");
		this.setTLVValueLength(speakerEntityIdentifier.length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		log.debug("TOTA  "+this.getTotalTLVLength()+" SW "+speakerEntityIdentifier.length);
		this.encodeHeader();
		int offset=4;
		System.arraycopy(speakerEntityIdentifier, 0, this.tlv_bytes, offset, speakerEntityIdentifier.length);
	}

	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding Speaker Entity Identifier TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		
		speakerEntityIdentifier = new byte[getTLVValueLength()];
		try
		{
			System.arraycopy(this.tlv_bytes, offset, speakerEntityIdentifier, 0, getTLVValueLength());
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
		
		//Check if array zero
		boolean isZero = true;
		for (int i = 0; i < speakerEntityIdentifier.length; i++) 
		{
			if (speakerEntityIdentifier[i]!=0)
			{
				isZero = false;
				break;
			}	
		}
		
		if (isZero)
		{
			log.error("Received Speaker Entity Identifier  value can not be zero");
			//throw new MalformedPCEPObjectException();
		}
		
		//MUST be greater than 0. To check this we look at the first bit
		boolean firstBit = (ByteHandler.easyCopy(0,0,speakerEntityIdentifier[0]) == 1) ? true : false ;
		if (firstBit)
		{
			log.error("Received redundancy group identifier value can not be negative");
			throw new MalformedPCEPObjectException();
		}
	}


	
	public byte[] getSpeakerEntityIdentifier() {
		return speakerEntityIdentifier;
	}

	public void setSpeakerEntityIdentifier(byte[] speakerEntityIdentifier) {
		this.speakerEntityIdentifier = speakerEntityIdentifier;
	}

	public String toString(){
		String speakerEntityIdentifierBytes = "Speaker Entity Id: "+ByteHandler.ByteMACToString(speakerEntityIdentifier);
		return speakerEntityIdentifierBytes;
	}
	
}