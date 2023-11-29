package es.tid.pce.pcep.objects.tlvs;

import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/*
 * SR Policy Name TLV
 * @author Luis Cepeda Martínez
 */

public class SRPolicyName extends PCEPTLV{
	/**
	 * 0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |             Type              |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                       SR Policy Name                          ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure: The SRPOLICY-POL-NAME TLV format
       Type: 56 for "SRPOLICY-POL-NAME" TLV.

   	Length: indicates the length of the value portion of the TLV in
   	octets and MUST be greater than 0.  The TLV MUST be zero-padded so
   	that the TLV is 4-octet aligned.

   	SR Policy Name: SR Policy name, as defined in
   	[I-D.ietf-spring-segment-routing-policy].  It SHOULD be a string of
   	printable ASCII characters, without a NULL terminator.            
                  
	 */

	private String policyName;
	
	
	public SRPolicyName(){
		this.TLVType=ObjectParameters.PCEP_TLV_SRPOLICY_POL_NAME;
	}

	public SRPolicyName(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}
	
	
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	@Override
	public void encode() {
		log.debug("Encoding SR Policy TLV");
		int length = 0;
		length = policyName.length()/4;
		System.out.println("POLICY NAME: " + policyName);
		System.out.println("LONGITUD 1:  " + length);
		if((policyName.length() % 4) != 0) {
			length++;
			
		}
		length= length *4;
		System.out.println("LONGITUD 2:  " + length);
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		System.arraycopy(policyName.getBytes(), 0, this.tlv_bytes, offset, policyName.length());
		
	}
	
	public void decode() throws MalformedPCEPObjectException{
		log.debug("Decoding SR Policy Name TLV");
		byte[] policyNamePaso = new byte[this.getTLVValueLength()];
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
		try {
			System.arraycopy(this.tlv_bytes, offset, policyNamePaso, 0, this.getTLVValueLength());
			policyName = new String(policyNamePaso);
			log.debug("Sended name : "+this.policyName);
		}catch (Exception e){
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(policyName);
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
		SRPolicyName other = (SRPolicyName) obj;
		return Objects.equals(policyName, other.policyName);
	}

	@Override
	public String toString() {
		return "SRPolicyName [policyName=" + policyName + "]";
	}

	
	

}
