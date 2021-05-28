package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**
 * Shared Risk Link Group (SRLG) (Type 16) RFC 4203
 * 
 * IANA Assignment in https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6
 *
 * @see <a href="https://www.iana.org/assignments/ospf-traffic-eng-tlvs/ospf-traffic-eng-tlvs.xhtml#subtlv6">IANA assignments of OSPF Traffic Engneering TLVs</a>
 * @see <a href="http://www.ietf.org/rfc/rfc4203"> RFC 4203</a>
 * 
 *	@author Oscar Gonzalez de Dios
 *	@author Fernando Munoz del Nuevo
 *	
 * 
 */

public class SharedRiskLinkGroup extends OSPFSubTLV {

	/*
	 * * *
 
   The SRLG is a sub-TLV (of type 16) of the Link TLV.  The length is
   the length of the list in octets.  The value is an unordered list of
   32 bit numbers that are the SRLGs that the link belongs to.  The
   format of the value field is as shown below:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Shared Risk Link Group Value                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                        ............                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                  Shared Risk Link Group Value                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   This sub-TLV carries the Shared Risk Link Group information (see
   Section "Shared Risk Link Group Information" of [GMPLS-ROUTING]).

   The SRLG sub-TLV may occur at most once within the Link TLV.
	 */
	
	private LinkedList<Inet4Address> sharedRiskLinkGroupValues;
	
	public SharedRiskLinkGroup(){
		
		this.setTLVType(OSPFSubTLVTypes.SharedRiskLinkGroup);
		sharedRiskLinkGroupValues = new LinkedList<Inet4Address>();
		
	}
	
	public SharedRiskLinkGroup(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		sharedRiskLinkGroupValues = new LinkedList<Inet4Address>();
		decode();
	}
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		this.setTLVValueLength(sharedRiskLinkGroupValues.size()*4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset=4;
		for (int i=0;i<sharedRiskLinkGroupValues.size();++i) {
			System.arraycopy(this.sharedRiskLinkGroupValues.get(i).getAddress(),0, this.tlv_bytes, offset, 4);
			offset=offset+4;
		}
	}
	
	public void decode() throws MalformedOSPFSubTLVException{
		int numIPAddresses=(this.getTLVValueLength()/4);
		byte[] ip=new byte[4];
		int offset=4;
		for (int i=0;i<numIPAddresses;++i){
			System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
			try {
				this.sharedRiskLinkGroupValues.add((Inet4Address)Inet4Address.getByAddress(ip));
				offset=offset+4;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				throw new MalformedOSPFSubTLVException();
			}	
		}
	}

	public LinkedList<Inet4Address> getSharedRiskLinkGroupValues() {
		return sharedRiskLinkGroupValues;
	}

	public void setSharedRiskLinkGroupValues(
			LinkedList<Inet4Address> sharedRiskLinkGroupValues) {
		this.sharedRiskLinkGroupValues = sharedRiskLinkGroupValues;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((sharedRiskLinkGroupValues == null) ? 0
						: sharedRiskLinkGroupValues.hashCode());
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
		SharedRiskLinkGroup other = (SharedRiskLinkGroup) obj;
		if (sharedRiskLinkGroupValues == null) {
			if (other.sharedRiskLinkGroupValues != null)
				return false;
		} else if (!sharedRiskLinkGroupValues
				.equals(other.sharedRiskLinkGroupValues))
			return false;
		return true;
	}
	
	

}
