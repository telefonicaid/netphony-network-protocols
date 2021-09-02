package es.tid.pce.pcep.objects.tlvs;

import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * SR Policy Candidate Path Preference TLV (59)
 *  
 *  The SRPOLICY-CPATH-PREFERENCE TLV is an optional TLV for the SRPAT
   ASSOCIATION.  Only one SRPOLICY-CPATH-PREFERENCE TLV SHOULD be
   encoded by the sender and only the first occurrence is processed and
   any others MUST be ignored.
 * @see <a href="https://datatracker.ietf.org/doc/html/draft-ietf-pce-segment-routing-policy-cp-05">draft-barth-pce-segment-routing-policy-cp v5</a>
 * @author Luis Cepeda Martínez
 *
 */

public class SRPolicyCandidatePathPreferenceTLV extends PCEPTLV{
	
	/*
	 * 
       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |             Type              |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                           Preference                          |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

            Figure 5: The SRPOLICY-CPATH-PREFERENCE TLV format

   Type: 59 for "SRPOLICY-CPATH-PREFERENCE" TLV.

	 */
	
	private long preference;
	
	
	public SRPolicyCandidatePathPreferenceTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_SRPOLICY_CANDIDATE_PATH_PREFERENCE;
	}

	public SRPolicyCandidatePathPreferenceTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}

	public long getPreference() {
		return preference;
	}

	public void setPreference(long preference) {
		this.preference = preference;
	}

	
	@Override
	public String toString() {
		return "SRPolicyCandidatePathPreferenceTLV [preference=" + preference + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(preference);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SRPolicyCandidatePathPreferenceTLV other = (SRPolicyCandidatePathPreferenceTLV) obj;
		return preference == other.preference;
	}

	@Override
	public void encode() {
		log.debug("Encoding PolicyCandidatePathPreference TLV");
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		ByteHandler.encode4bytesLong(preference,this.tlv_bytes,offset);
		
	}
	
	public void decode() throws MalformedPCEPObjectException {
		log.debug("Decoding PolicyCandidatePathPreference TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
			
		try {
			this.preference = ByteHandler.decode4bytesLong(this.tlv_bytes,offset);
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
	}

}
