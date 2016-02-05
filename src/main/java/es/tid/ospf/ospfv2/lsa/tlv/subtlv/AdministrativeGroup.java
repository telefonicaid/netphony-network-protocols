package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * Represents Administrative Group Sub-TLV, as defined in 
 * <a href="http://tools.ietf.org/html/rfc3630">RFC 3630</a>.

   The Administrative Group sub-TLV contains a 4-octet bit mask assigned
   by the network administrator.  Each set bit corresponds to one
   administrative group assigned to the interface.  A link may belong to
   multiple groups.

   By convention, the least significant bit is referred to as 'group 0',
   and the most significant bit is referred to as 'group 31'.

   The Administrative Group is also called Resource Class/Color [5].

   The Administrative Group sub-TLV is TLV type 9, and is four octets in
   length.
   
 * @author Oscar González de Dios
 * @author Fernando Muñoz del Nuevo
 */

public class AdministrativeGroup extends OSPFSubTLV {
	
	/**
	 * Administrative Group
	 */
	private int administrativeGroup;
	
	public AdministrativeGroup(){
		this.setTLVType(OSPFSubTLVTypes.AdministrativeGroup);
		administrativeGroup=0;
	}
	
	public AdministrativeGroup(byte[] bytes, int offset)throws MalformedOSPFSubTLVException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode the 4 byte Administrative group
	 */
	public void encode() throws MalformedOSPFSubTLVException{
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		int offset = 4;
	
		this.tlv_bytes[offset] = (byte)((administrativeGroup >> 24) & 0xff);
		this.tlv_bytes[offset + 1] = (byte)((administrativeGroup >> 16) & 0xff);
		this.tlv_bytes[offset + 2] = (byte)((administrativeGroup >> 8) & 0xff);
		this.tlv_bytes[offset + 3] = (byte)(administrativeGroup & 0xff);

	}
	
	protected void decode()throws MalformedOSPFSubTLVException{
		if (this.getTLVValueLength()!=4){
			throw new MalformedOSPFSubTLVException();
		}
		int offset=4;		
		this.administrativeGroup= ((this.tlv_bytes[offset]&0xFF)<<24) | ((this.tlv_bytes[offset+1]&0xFF)<<16) | ((this.tlv_bytes[offset+2]&0xFF)<<8) |  (this.tlv_bytes[offset+3]&0xFF) ;
	}
	
	public String toString(){
		return "AdministrativeGroup: "+Integer.toBinaryString(administrativeGroup);
	}

	public int getAdministrativeGroup() {
		return administrativeGroup;
	}

	public void setAdministrativeGroup(int administrativeGroup) {
		this.administrativeGroup = administrativeGroup;
	}
	
	public boolean isGroup(int groupNumber){
		return (administrativeGroup>>>groupNumber)==1;
	}
	
	public void setGroup(int groupNumber){	
		administrativeGroup=administrativeGroup|(1<<groupNumber);
	}
	
}
