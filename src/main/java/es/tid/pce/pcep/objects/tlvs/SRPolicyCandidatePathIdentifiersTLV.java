package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
 * SR Policy Candidate Path Identifiers TLV (57)
 * @author Luis Cepeda Martínez
 *
 */
public class SRPolicyCandidatePathIdentifiersTLV extends PCEPTLV{

	/*
	 * 
	   0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |             Type              |             Length            |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      | Proto. Origin |                    Reserved                   |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                         Originator ASN                        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      |                       Originator Address                      |
      |                                                               |
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                         Discriminator                         |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 3: The SRPOLICY-CPATH-ID TLV format

   Type: 57 for "SRPOLICY-CPATH-ID" TLV.
   
   Protocol Origin: 8-bit value that encodes the protocol origin, as
   specified in [I-D.ietf-spring-segment-routing-policy] Section 2.3.

   Reserved: MUST be set to zero on transmission and ignored on receipt.

   Originator ASN: Represented as 4 byte number, part of the originator
   identifier, as specified in [I-D.ietf-spring-segment-routing-policy]
   Section 2.4.

   Originator Address: Represented as 128 bit value where IPv4 address
   are encoded in lowest 32 bits, part of the originator identifier, as
   specified in [I-D.ietf-spring-segment-routing-policy] Section 2.4.

   Discriminator: 32-bit value that encodes the Discriminator of the
   candidate path.
   
	 */
	
	private int protocol;
	
	private long originatorASN;
	
	private Inet4Address originatorAddress;
	
	private long discriminator;
	
	public SRPolicyCandidatePathIdentifiersTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_SRPOLICY_CANDIDATE_PATH_IDS;
	}
	
	public SRPolicyCandidatePathIdentifiersTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);		
		decode();
	}
	
	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public long getOriginatorASN() {
		return originatorASN;
	}

	public void setOriginatorASN(long originatorASN) {
		this.originatorASN = originatorASN;
	}

	public Inet4Address getOriginatorAddress() {
		return originatorAddress;
	}

	public void setOriginatorAddress(Inet4Address originatorAddress) {
		this.originatorAddress = originatorAddress;
	}

	public long getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(long discriminator) {
		this.discriminator = discriminator;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(discriminator, originatorASN, originatorAddress, protocol);
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
		SRPolicyCandidatePathIdentifiersTLV other = (SRPolicyCandidatePathIdentifiersTLV) obj;
		return discriminator == other.discriminator && originatorASN == other.originatorASN
				&& Objects.equals(originatorAddress, other.originatorAddress) && protocol == other.protocol;
	}

	

	@Override
	public String toString() {
		return "SRPolicyCandidatePathIdentifiersTLV [protocol=" + protocol + ", originatorASN=" + originatorASN
				+ ", originatorAddress=" + originatorAddress + ", discriminator=" + discriminator + "]";
	}

	@Override
	public void encode() {
		this.setTLVValueLength(32);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		int reserved=0;
		
		ByteHandler.encode1byteInteger(protocol, this.tlv_bytes, offset);
		offset+=1;
		ByteHandler.encode3bytesInteger(reserved, this.tlv_bytes, offset);
		offset+=3;
		ByteHandler.encode4bytesLong(originatorASN, this.tlv_bytes, offset);
		offset+=4;
		ByteHandler.encode4bytesLong(reserved, tlv_bytes, offset);
		offset+=4;
		ByteHandler.encode4bytesLong(reserved, tlv_bytes, offset);
		offset+=4;
		ByteHandler.encode4bytesLong(reserved, tlv_bytes, offset);
		offset+=4;
		System.arraycopy(originatorAddress.getAddress(),0,this.tlv_bytes,offset,4);
		offset+=4;
		ByteHandler.encode4bytesLong(discriminator, this.tlv_bytes, offset);
	
	}
	

	public void decode() throws MalformedPCEPObjectException {
		log.debug("Decoding SRPolicyCandidatePathIdentifiersTLV TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
			
		try {
			this.protocol = ByteHandler.decode1byteInteger(this.tlv_bytes, offset);
			offset+=4;
			this.originatorASN = ByteHandler.decode4bytesLong(this.tlv_bytes, offset);
			offset+=16;
			byte[] ip=new byte[4]; 
			System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
			originatorAddress=(Inet4Address)Inet4Address.getByAddress(ip);
			offset+=4;
			this.discriminator = ByteHandler.decode4bytesLong(this.tlv_bytes, offset);
					
					
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
	}
}
