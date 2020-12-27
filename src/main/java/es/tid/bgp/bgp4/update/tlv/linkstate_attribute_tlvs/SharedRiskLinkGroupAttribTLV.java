
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import java.util.LinkedList;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;

/**
 * Shared Risk Link Group TLV (Type 1096)		[RFC7752, Section 3.3.2.5]
 * 
 * @author ogondio
 *
 */

public class SharedRiskLinkGroupAttribTLV extends BGP4TLVFormat{
	
	/*
	 *  The Shared Risk Link Group (SRLG) TLV carries the Shared Risk Link
   Group information (see Section 2.3 ("Shared Risk Link Group
   Information") of [RFC4202]).  It contains a data structure consisting
   of a (variable) list of SRLG values, where each element in the list
   has 4 octets, as shown in Figure 22.  The length of this TLV is 4 *
   (number of SRLG values).








Gredler, et al.              Standards Track                   [Page 26]
 
RFC 7752         Link-State Info Distribution Using BGP       March 2016


      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                  Shared Risk Link Group Value                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     //                         ............                        //
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                  Shared Risk Link Group Value                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 22: Shared Risk Link Group TLV Format

   The SRLG TLV for OSPF-TE is defined in [RFC4203].  In IS-IS, the SRLG
   information is carried in two different TLVs: the IPv4 (SRLG) TLV
   (Type 138) defined in [RFC5307] and the IPv6 SRLG TLV (Type 139)
   defined in [RFC6119].  In Link-State NLRI, both IPv4 and IPv6 SRLG
   information are carried in a single TLV.
	 */

	public LinkedList <Long> srlg_values;
	
	public SharedRiskLinkGroupAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_SHARED_RISK_LINK_GROUP);
		srlg_values=new  LinkedList <Long>();
	}
	
	public SharedRiskLinkGroupAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		
		int offset = 4;
		int i = 0 ;
		

		this.setTLVValueLength(srlg_values.size()*4);
		this.setTlv_bytes(new byte[this.getTotalTLVLength()]);		
		encodeHeader();

		
		for (i=0 ; i<srlg_values.size() ; i++){
			tlv_bytes[offset]=(byte)((srlg_values.get(i)>>24) & 0xFF);
	    	tlv_bytes[offset+1]=(byte)((srlg_values.get(i)>>16) & 0xFF);
			tlv_bytes[offset+2]=(byte)((srlg_values.get(i)>>8) & 0xFF);
			tlv_bytes[offset+3]=(byte)(srlg_values.get(i) & 0xFF);	
			offset = offset +4;
		}
		
	}
	
	protected void decode(){
		
		int offset = 4;
		int i;
				
		int srlg_length = this.getTLVValueLength()/4;
		srlg_values=new  LinkedList <Long>();
		for (i=0 ; i<srlg_length ; i++){
			
			long srlg_value =0;
			
			for (int k = 0; k < 4; k++) {
				srlg_value  = (srlg_value  << 8) | ((long)tlv_bytes[k+offset] & (long)0xff);
			}	
			srlg_values.add(new Long(srlg_value));
			offset=offset+4;
		
		}
	}

	
	public LinkedList<Long> getSrlg_values() {
		return srlg_values;
	}

	public void setSrlg_values(LinkedList<Long> srlg_values) {
		this.srlg_values = srlg_values;
	}

	@Override
	public String toString() {
		return "SharedRiskLinkGroupAttribTLV [srlg_values=" + srlg_values + "]";
	}
	
	

}
