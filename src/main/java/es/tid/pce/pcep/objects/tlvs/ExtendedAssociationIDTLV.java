package es.tid.pce.pcep.objects.tlvs;


import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Objects;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;


/**
 *  Extended Association ID TLV
 * @author Oscar Gonzalez de Dios
 *
 */
public class ExtendedAssociationIDTLV extends PCEPTLV 
{
	/*
	 * 

   The Extended Association ID TLV is an optional TLV for use in the
   ASSOCIATION object.  The meaning and usage of the Extended
   Association ID TLV are as per Section 4 of [RFC6780].

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |           Type = 31           |       Length = 8 or 20        |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                             Color                             |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      ~                           Endpoint                            ~
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

              Figure 6: The Extended Association ID TLV Format

   Type:  31

   Length:  Variable.
   
   Color: SR Policy color value.

   Endpoint: can be either IPv4 or IPv6,
	 */
	
	private long color;
	private InetAddress endpoint;
	
	
	
	public ExtendedAssociationIDTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_EXTENDED_ASSOCIATION_ID);
		
	}
	
	public ExtendedAssociationIDTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	public long getColor() {
		return color;
	}

	public void setColor(long color) {
		this.color = color;
	}

	public InetAddress getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(InetAddress endpoint) {
		this.endpoint = endpoint;
	}

	
	@Override
	public String toString() {
		return "ExtendedAssociationIDTLV [color=" + color + ", endpoint=" + endpoint + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(color, endpoint);
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
		ExtendedAssociationIDTLV other = (ExtendedAssociationIDTLV) obj;
		return color == other.color && Objects.equals(endpoint, other.endpoint);
	}

	/**
	 * Encode
	 */
	public void encode() 
	{
		log.debug("Encoding ExtendedAssociationIDTLV TLV");
		//length
		if(endpoint instanceof java.net.Inet4Address) {
			this.setTLVValueLength(8);
		}else {
			this.setTLVValueLength(20);
		}
		
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		ByteHandler.encode4bytesLong(color,this.tlv_bytes,offset);
		
		offset+=4;
		
		if(endpoint instanceof java.net.Inet4Address) {
			System.arraycopy(endpoint.getAddress(),0,this.tlv_bytes,offset,4);
		}else {
			System.arraycopy(endpoint.getAddress(),0,this.tlv_bytes,offset,16);
		}
		
	}

	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding SymbolicPathName TLV");
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0)
		{
			throw new MalformedPCEPObjectException();
		}
			
		try
		{
			this.color = ByteHandler.decode4bytesLong(this.tlv_bytes,offset);
			offset +=4;
			if(this.getTLVValueLength()==8) {
				byte[] ip=new byte[4]; 
				System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
				endpoint=(Inet4Address)Inet4Address.getByAddress(ip);
				log.debug("Sended endpoint ipv4: "+endpoint);
			}else {
				byte[] ip=new byte[16]; 
				System.arraycopy(this.tlv_bytes,offset, ip, 0, 16);
				endpoint=(Inet6Address)Inet6Address.getByAddress(ip);
				log.debug("Sended endpoint ipv6: "+endpoint);
			}
		}
		catch (Exception e)
		{
			log.error("Exception occurred, Possibly TLV size is not what expected");
			throw new MalformedPCEPObjectException();			
		}
			

	}
	
	

	

	
}