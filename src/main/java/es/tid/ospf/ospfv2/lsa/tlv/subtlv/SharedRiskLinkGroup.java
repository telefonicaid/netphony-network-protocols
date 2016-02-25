package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

/**

Kompella & Rekhter          Standards Track                     [Page 2]

 
RFC 4203                OSPF Extensions in MPLS             October 2005

1.3. Shared Risk Link Group (SRLG)


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
 * 
 *	@author Oscar Gonz�lez de Dios
 *	@author Fernando Mu�oz del Nuevo
 *	
 * 
 */

public class SharedRiskLinkGroup extends OSPFSubTLV {

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
			System.arraycopy(this.sharedRiskLinkGroupValues.get(i),0, this.tlv_bytes, offset, 4);
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
