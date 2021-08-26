package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.Objects;

import es.tid.pce.pcep.objects.tlvs.ExtendedAssociationIDTLV;
import es.tid.pce.pcep.objects.tlvs.GlobalAssociationSourceTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.SRPolicyCandidatePathIdentifiersTLV;
import es.tid.pce.pcep.objects.tlvs.SRPolicyCandidatePathNameTLV;
import es.tid.pce.pcep.objects.tlvs.SRPolicyCandidatePathPreferenceTLV;
import es.tid.pce.pcep.objects.tlvs.SRPolicyName;
import es.tid.protocol.commons.ByteHandler;

/**
 * IPv6 ASSOCIATION Object
 * Association groups and their memberships are defined using a new
   ASSOCIATION object.

 * @author ogondio
 *
 */
public class AssociationIPv6 extends Association {
	
	/*
	 *  The ASSOCIATION Object-Class value is 40.

   The ASSOCIATION Object-Type value is 1 for IPv4, and its format is
   shown in Figure 3:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Reserved              |            Flags            |R|
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |      Association Type         |      Association ID           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              IPv4 Association Source                          |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                   Optional TLVs                             //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 3: The IPv4 ASSOCIATION Object Format

   The ASSOCIATION Object-Type value is 2 for IPv6, and its format is
   shown in Figure 4:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Reserved              |            Flags            |R|
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |      Association Type         |      Association ID           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                                                               |
     |                    IPv6 Association Source                    |
     |                                                               |
     |                                                               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                   Optional TLVs                             //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 4: The IPv6 ASSOCIATION Object Format

   Reserved (2 bytes):  MUST be set to 0 and ignored upon receipt.

   Flags (2 bytes):  The following flag is currently defined:

      R (Removal - 1 bit):  When set, the requesting PCEP peer requires
         the removal of an LSP from the association group.  When unset,
         the PCEP peer indicates that the LSP is added or retained as
         part of the association group.  This flag is used for the
         ASSOCIATION object in the Path Computation Report (PCRpt) and
         Path Computation Update (PCUpd) messages.  It is ignored in
         other PCEP messages.

   The unassigned flags MUST be set to 0 on transmission and MUST be
   ignored on receipt.

   Association Type (2 bytes):  The Association Type (Section 7.4).  The
      Association Types will be defined in future documents.

   Association ID (2 bytes):  The identifier of the association group.
      When combined with other association parameters, such as an
      Association Type and Association Source, this value uniquely
      identifies an association group.  The values 0xffff and 0x0 are
      reserved.  The value 0xffff is used to indicate all association
      groups and could be used with the R flag to indicate removal for
      all associations for the LSP within the scope of the Association
      Type and Association Source.

   Association Source:  Contains a valid IPv4 address (4 bytes) if the
      ASSOCIATION Object-Type is 1 or a valid IPv6 address (16 bytes) if
      the ASSOCIATION Object-Type is 2.  The address provides scoping
      for the Association ID.  See Section 6.1.3 for details.

   Optional TLVs:  The optional TLVs follow the PCEP TLV format defined
      in [RFC5440].  This document defines two optional TLVs.  Other
      documents can define more TLVs in the future.

6.1.1.  Global Association Source TLV

   The Global Association Source TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Global Association
   Source TLV are as per Section 4 of [RFC6780].

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |            Length             |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Global Association Source                        |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

             Figure 5: The Global Association Source TLV Format

   Type:  30

   Length:  Fixed value of 4 bytes.

   Global Association Source:  As defined in Section 4 of [RFC6780].

	 */
	
	/**
	 * R (Removal - 1 bit):
	 */
	private boolean removal=false;
	
	/**
	 *  Association Type
	 */
	private int assocType=0;
	
	/**
	 * Association ID 
	 */
	private int assocID=0;
	
	private Inet6Address associationSource;
	
	private GlobalAssociationSourceTLV global_association_source_tlv=null;

	private ExtendedAssociationIDTLV extended_ssociation_id_tlv=null;
	
	private SRPolicyCandidatePathNameTLV sr_policy_candidate_path_tlv=null;
	
	private SRPolicyCandidatePathIdentifiersTLV sr_policy_candidate_path_identifiers_tlv=null;
	
	private SRPolicyCandidatePathPreferenceTLV sr_policy_candidate_path_preference_tlv=null;
	
	private SRPolicyName sr_policy_name = null;
	
	
	
	public AssociationIPv6() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV6);
	}

	public AssociationIPv6(byte[] bytes, int offset) throws MalformedPCEPObjectException {
		super(bytes, offset);
		decode();

	}

	@Override
	public void encode() {
		this.ObjectLength=28;
		if (global_association_source_tlv!=null){
			global_association_source_tlv.encode();
			ObjectLength=ObjectLength+global_association_source_tlv.getTotalTLVLength();
		}
		if (extended_ssociation_id_tlv!=null){
			extended_ssociation_id_tlv.encode();
			ObjectLength=ObjectLength+extended_ssociation_id_tlv.getTotalTLVLength();
		}
		if(sr_policy_candidate_path_tlv!=null) {
			sr_policy_candidate_path_tlv.encode();
			ObjectLength=ObjectLength+sr_policy_candidate_path_tlv.getTotalTLVLength();
		}
		if(sr_policy_candidate_path_identifiers_tlv!=null) {
			sr_policy_candidate_path_identifiers_tlv.encode();
			ObjectLength=ObjectLength+sr_policy_candidate_path_identifiers_tlv.getTotalTLVLength();
		}
		if(sr_policy_candidate_path_preference_tlv!=null) {
			sr_policy_candidate_path_preference_tlv.encode();
			ObjectLength=ObjectLength+sr_policy_candidate_path_preference_tlv.getTotalTLVLength();
		}
		if(sr_policy_name!=null) {
			sr_policy_name.encode();
			ObjectLength=ObjectLength+sr_policy_name.getTotalTLVLength();
		}
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=0;
		object_bytes[5]=0;
		object_bytes[6]=0;
		object_bytes[7]=(byte)(removal?1:0);
		int offset=8;
	    this.object_bytes[offset]=(byte)(assocType >> 8 & 0xff);
	    this.object_bytes[offset+1]=(byte)(assocType & 0xff);
	    this.object_bytes[offset+2]=(byte)(assocID >> 8 & 0xff);
	    this.object_bytes[offset+3]=(byte)(assocID & 0xff);
	    offset=12;
		System.arraycopy(associationSource.getAddress(),0, this.object_bytes, 12, 16);
		offset+=16;
		if (global_association_source_tlv!=null) {
			System.arraycopy(global_association_source_tlv.getTlv_bytes(),0,this.object_bytes,offset,global_association_source_tlv.getTotalTLVLength());
			offset=offset+global_association_source_tlv.getTotalTLVLength();
			
		}
		if (extended_ssociation_id_tlv!=null) {
			System.arraycopy(extended_ssociation_id_tlv.getTlv_bytes(),0,this.object_bytes,offset,extended_ssociation_id_tlv.getTotalTLVLength());
			offset=offset+extended_ssociation_id_tlv.getTotalTLVLength();
			
		}
		if(sr_policy_candidate_path_tlv!=null) {
			System.arraycopy(sr_policy_candidate_path_tlv.getTlv_bytes(),0,this.object_bytes,offset,sr_policy_candidate_path_tlv.getTotalTLVLength());
			offset=offset+sr_policy_candidate_path_tlv.getTotalTLVLength();
			
		}
		if(sr_policy_candidate_path_identifiers_tlv!=null) {
			System.arraycopy(sr_policy_candidate_path_identifiers_tlv.getTlv_bytes(),0,this.object_bytes,offset,sr_policy_candidate_path_identifiers_tlv.getTotalTLVLength());
			offset=offset+sr_policy_candidate_path_identifiers_tlv.getTotalTLVLength();
		}
		if(sr_policy_candidate_path_preference_tlv!=null) {
			System.arraycopy(sr_policy_candidate_path_preference_tlv.getTlv_bytes(),0,this.object_bytes,offset,sr_policy_candidate_path_preference_tlv.getTotalTLVLength());
			offset=offset+sr_policy_candidate_path_preference_tlv.getTotalTLVLength();
		}
		if(sr_policy_name!=null) {
			System.arraycopy(sr_policy_name.getTlv_bytes(),0,this.object_bytes,offset,sr_policy_name.getTotalTLVLength());
			offset=offset+sr_policy_name.getTotalTLVLength();
		}

	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		removal=(object_bytes[7]&0x01)==0x01;
		int offset=8;
		assocType=ByteHandler.decode2bytesInteger(this.object_bytes,offset);
		assocID=ByteHandler.decode2bytesInteger(this.object_bytes,offset+2);
		byte[] ip=new byte[16]; 
		System.arraycopy(this.object_bytes,12, ip, 0, 16);
		try {
			associationSource=(Inet6Address)Inet6Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean fin=false;
		if (ObjectLength==16){
			fin=true;
		}
		offset=28;
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_GLOBAL_ASSOCIATION_SOURCE:
				global_association_source_tlv=new GlobalAssociationSourceTLV(this.getObject_bytes(), offset);
				break;
			case ObjectParameters.PCEP_TLV_EXTENDED_ASSOCIATION_ID:
				extended_ssociation_id_tlv=new ExtendedAssociationIDTLV(this.getObject_bytes(), offset);
				break;
			case 56:
				sr_policy_name = new SRPolicyName(this.getObject_bytes(),offset);
				break;
			case 57:
				sr_policy_candidate_path_identifiers_tlv= new SRPolicyCandidatePathIdentifiersTLV(this.getObject_bytes(),offset);
				break;
			case 58:
				sr_policy_candidate_path_tlv=new SRPolicyCandidatePathNameTLV(this.getObject_bytes(),offset);
				break;
			case 59:
				sr_policy_candidate_path_preference_tlv=new SRPolicyCandidatePathPreferenceTLV(this.getObject_bytes(),offset);
				break;
			default:
				log.debug("UNKNOWN TLV found");
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
	}


	public boolean isRemoval() {
		return removal;
	}

	public void setRemoval(boolean removal) {
		this.removal = removal;
	}

	public int getAssocType() {
		return assocType;
	}

	public void setAssocType(int assocType) {
		this.assocType = assocType;
	}

	public int getAssocID() {
		return assocID;
	}

	public void setAssocID(int assocID) {
		this.assocID = assocID;
	}

	public Inet6Address getAssociationSource() {
		return associationSource;
	}

	public void setAssociationSource(Inet6Address associationSource) {
		this.associationSource = associationSource;
	}

	public GlobalAssociationSourceTLV getGlobal_association_source_tlv() {
		return global_association_source_tlv;
	}

	public void setGlobal_association_source_tlv(GlobalAssociationSourceTLV global_association_source_tlv) {
		this.global_association_source_tlv = global_association_source_tlv;
	}

	public ExtendedAssociationIDTLV getExtended_ssociation_id_tlv() {
		return extended_ssociation_id_tlv;
	}

	public void setExtended_ssociation_id_tlv(ExtendedAssociationIDTLV extended_ssociation_id_tlv) {
		this.extended_ssociation_id_tlv = extended_ssociation_id_tlv;
	}
	
	public SRPolicyCandidatePathNameTLV getSr_policy_candidate_path_tlv() {
		return sr_policy_candidate_path_tlv;
	}

	public void setSr_policy_candidate_path_tlv(SRPolicyCandidatePathNameTLV sr_policy_candidate_path_tlv) {
		this.sr_policy_candidate_path_tlv = sr_policy_candidate_path_tlv;
	}

	public SRPolicyCandidatePathIdentifiersTLV getSr_policy_candidate_path_identifiers_tlv() {
		return sr_policy_candidate_path_identifiers_tlv;
	}

	public void setSr_policy_candidate_path_identifiers_tlv(
			SRPolicyCandidatePathIdentifiersTLV sr_policy_candidate_path_identifiers_tlv) {
		this.sr_policy_candidate_path_identifiers_tlv = sr_policy_candidate_path_identifiers_tlv;
	}

	public SRPolicyCandidatePathPreferenceTLV getSr_policy_candidate_path_preference_tlv() {
		return sr_policy_candidate_path_preference_tlv;
	}

	public void setSr_policy_candidate_path_preference_tlv(
			SRPolicyCandidatePathPreferenceTLV sr_policy_candidate_path_preference_tlv) {
		this.sr_policy_candidate_path_preference_tlv = sr_policy_candidate_path_preference_tlv;
	}

	public SRPolicyName getSr_policy_name() {
		return sr_policy_name;
	}

	public void setSr_policy_name(SRPolicyName sr_policy_name) {
		this.sr_policy_name = sr_policy_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(assocID, assocType, associationSource, extended_ssociation_id_tlv,
				global_association_source_tlv, removal, sr_policy_candidate_path_identifiers_tlv,
				sr_policy_candidate_path_preference_tlv, sr_policy_candidate_path_tlv, sr_policy_name);
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
		AssociationIPv6 other = (AssociationIPv6) obj;
		return assocID == other.assocID && assocType == other.assocType
				&& Objects.equals(associationSource, other.associationSource)
				&& Objects.equals(extended_ssociation_id_tlv, other.extended_ssociation_id_tlv)
				&& Objects.equals(global_association_source_tlv, other.global_association_source_tlv)
				&& removal == other.removal
				&& Objects.equals(sr_policy_candidate_path_identifiers_tlv,
						other.sr_policy_candidate_path_identifiers_tlv)
				&& Objects.equals(sr_policy_candidate_path_preference_tlv,
						other.sr_policy_candidate_path_preference_tlv)
				&& Objects.equals(sr_policy_candidate_path_tlv, other.sr_policy_candidate_path_tlv)
				&& Objects.equals(sr_policy_name, other.sr_policy_name);
	}

	@Override
	public String toString() {
		return "AssociationIPv6 [removal=" + removal + ", assocType=" + assocType + ", assocID=" + assocID
				+ ", associationSource=" + associationSource + ", global_association_source_tlv="
				+ global_association_source_tlv + ", extended_ssociation_id_tlv=" + extended_ssociation_id_tlv
				+ ", sr_policy_candidate_path_tlv=" + sr_policy_candidate_path_tlv
				+ ", sr_policy_candidate_path_identifiers_tlv=" + sr_policy_candidate_path_identifiers_tlv
				+ ", sr_policy_candidate_path_preference_tlv=" + sr_policy_candidate_path_preference_tlv
				+ ", sr_policy_name=" + sr_policy_name + "]";
	}

	
	
	


	
	

}
