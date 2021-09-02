package es.tid.pce.pcep.objects.tlvs;

import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * SR Policy Candidate Path Name TLV
 * @author b.lcm
 *
 */

public class SRPolicyCandidatePathNameTLV extends PCEPTLV{

	/**
	 * 0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |             Type              |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                 SR Policy Candidate Path Name                 ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                 Figure: The SRPOLICY-CPATH-NAME TLV format
	 */
	
	private String SRPolicyCandidatePathName;
	
	public SRPolicyCandidatePathNameTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_SRPOLICY_CANDIDATE_PATH_NAME;
	}

	public SRPolicyCandidatePathNameTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}
	
	@Override
	public void encode() {
		log.debug("Encoding SR Policy Name TLV");
		this.setTLVValueLength(SRPolicyCandidatePathName.length());
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		System.arraycopy(SRPolicyCandidatePathName.getBytes(), 0, this.tlv_bytes, offset, SRPolicyCandidatePathName.length());
		
	}
	
	public void decode() throws MalformedPCEPObjectException{
		log.debug("Decoding SR Policy Name TLV");
		byte[] SRPolicyCandidatePathNamePaso = new byte[this.getTLVValueLength()];
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		try {
			System.arraycopy(this.tlv_bytes, offset, SRPolicyCandidatePathNamePaso, 0, this.getTLVValueLength());
			this.SRPolicyCandidatePathName = new String(SRPolicyCandidatePathNamePaso);
			log.debug("Candidate name : "+this.SRPolicyCandidatePathName);
		}catch (Exception e){
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();
		}
		
	}

	public String getSRPolicyCandidatePathName() {
		return SRPolicyCandidatePathName;
	}

	public void setSRPolicyCandidatePathName(String sRPolicyCandidatePathName) {
		SRPolicyCandidatePathName = sRPolicyCandidatePathName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(SRPolicyCandidatePathName);
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
		SRPolicyCandidatePathNameTLV other = (SRPolicyCandidatePathNameTLV) obj;
		return Objects.equals(SRPolicyCandidatePathName, other.SRPolicyCandidatePathName);
	}

	@Override
	public String toString() {
		return "SRPolicyCandidatePathNameTLV [SRPolicyCandidatePathName=" + SRPolicyCandidatePathName + "]";
	}

	
	
	

}
