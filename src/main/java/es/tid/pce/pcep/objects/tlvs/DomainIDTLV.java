package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import es.tid.protocol.commons.ByteHandler;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;

/**
 * Domain ID TLV (Type 14)
 * 
  The Domain-ID TLV, when used in the OPEN object, identifies the
   domains served by the PCE.  The child PCE uses this mechanism to
   provide the domain information to the parent PCE.
   
 * @author ogondio
 *
 */
public class DomainIDTLV extends PCEPTLV {
	
	/*
	 *  The Domain-ID TLV is defined below:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |               Type=14         |            Length             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   | Domain Type   |                  Reserved                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                          Domain ID                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                       Figure 2: Domain-ID TLV Format

   The type of the TLV is 14, and it has a variable Length of the value
   portion.  The value part comprises the following:

      Domain Type (8 bits):  Indicates the domain type.  Four types of
         domains are currently defined:

         Type=1:   The Domain ID field carries a 2-byte AS number.
                   Padded with trailing zeros to a 4-byte boundary.

         Type=2:   The Domain ID field carries a 4-byte AS number.

         Type=3:   The Domain ID field carries a 4-byte OSPF area ID.

         Type=4:   The Domain ID field carries a 2-byte Area-Len and a
                   variable-length IS-IS area ID.  Padded with trailing
                   zeros to a 4-byte boundary.

      Reserved:  Zero at transmission; ignored on receipt.

      Domain ID (variable):  Indicates an IGP area ID or AS number as
         per the Domain Type field.  It can be 2 bytes, 4 bytes, or
         variable length, depending on the domain identifier used.  It
         is padded with trailing zeros to a 4-byte boundary.  In the
         case of IS-IS, it includes the Area-Len as well.

   In the case where a PCE serves more than one domain, multiple Domain-
   ID TLVs are included for each domain it serves.
	 */
	
	int domainType;

	Inet4Address domainId;
	
	public DomainIDTLV(){
		this.TLVType=ObjectParameters.PCEP_TLV_DOMAIN_ID_TLV;
		domainType=1;//Default value
	}
	
	public DomainIDTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	/**
	 * Encodes the Domain Id TLV
	 */
	public void encode() {
		this.setTLVValueLength(8);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		int offset = 4;
		ByteHandler.encode1byteInteger(domainType,tlv_bytes,offset);
		System.arraycopy(domainId.getAddress(),0, this.tlv_bytes, 8, 4);
	}

	
	
	public void decode() throws MalformedPCEPObjectException{
		try {
			int offset=4;
			domainType=ByteHandler.decode1byteInteger(this.getTlv_bytes(), offset);
			offset = 8;
			byte[] ip=new byte[4];
			System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
			domainId=(Inet4Address)Inet4Address.getByAddress(ip);
		}
		catch (Exception e) {			
			e.printStackTrace();
			throw new MalformedPCEPObjectException("Bad DomainIDTLV");
		}
	
	}
	
	
	
	public Inet4Address getDomainId() {
		return domainId;
	}

	public void setDomainId(Inet4Address domainId) {
		this.domainId = domainId;
	}
	
	

	public int getDomainType() {
		return domainType;
	}

	public void setDomainType(int domainType) {
		this.domainType = domainType;
	}


	public String toString() {
		return "DomainIDTLV [domainType=" + domainType + ", domainId=" + domainId + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((domainId == null) ? 0 : domainId.hashCode());
		result = prime * result + domainType;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DomainIDTLV other = (DomainIDTLV) obj;
		if (domainId == null) {
			if (other.domainId != null)
				return false;
		} else if (!domainId.equals(other.domainId))
			return false;
		if (domainType != other.domainType)
			return false;
		return true;
	}
	
	


}
