package es.tid.pce.pcep.objects.tlvs;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/** IPV4-LSP-IDENTIFIERS TLV (Type: 18)
 * 
 * Encoding: 
   The format of the IPV4-LSP-IDENTIFIERS TLV is shown in the following
   figure:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |           Type=18             |           Length=16           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                   IPv4 Tunnel Sender Address                  |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |             LSP ID            |           Tunnel ID           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                        Extended Tunnel ID                     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                   IPv4 Tunnel Endpoint Address                |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                Figure 12: IPV4-LSP-IDENTIFIERS TLV Format

  

   IPv4 Tunnel Sender Address:  contains the sender node's IPv4 address,
      as defined in [RFC3209], Section 4.6.2.1 for the LSP_TUNNEL_IPv4
      Sender Template Object.

   LSP ID:  contains the 16-bit 'LSP ID' identifier defined in
      [RFC3209], Section 4.6.2.1 for the LSP_TUNNEL_IPv4 Sender Template
      Object.

   Tunnel ID:  contains the 16-bit 'Tunnel ID' identifier defined in
      [RFC3209], Section 4.6.1.1 for the LSP_TUNNEL_IPv4 Session Object.
      Tunnel ID remains constant over the life time of a tunnel.
      However, when Global Path Protection or Global Default Restoration
      is used, both the primary and secondary LSPs have their own Tunnel
      IDs.  A PCC will report a change in Tunnel ID when traffic
      switches over from primary LSP to secondary LSP (or vice versa).

   Extended Tunnel ID:  contains the 32-bit 'Extended Tunnel ID'
      identifier defined in [RFC3209], Section 4.6.1.1 for the
      LSP_TUNNEL_IPv4 Session Object.
      
      @author jaume, ogondio
 */

public class IPv4LSPIdentifiersTLV extends PCEPTLV 
{
	private Inet4Address tunnelSenderIPAddress;
	
	private int lspID;
	
	private int tunnelID;
	
	private long extendedTunnelID;
	
	private Inet4Address tunnelEndPointIPAddress;

	public IPv4LSPIdentifiersTLV()
	{
		this.TLVType=ObjectParameters.PCEP_TLV_TYPE_IPV4_LSP_IDENTIFIERS;
	}

	public IPv4LSPIdentifiersTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException
	{		
		super(bytes,offset);		
		decode();
	}

	@Override
	public void encode() 
	{		
		int length = 16;
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		encodeHeader();
		
		int offset = 4;
		System.arraycopy(tunnelSenderIPAddress.getAddress(),0, this.tlv_bytes, offset, 4);
		
		offset += 4;
		//this.tlv_bytes[offset]=0x01;
		this.tlv_bytes[offset]=(byte)(lspID>>>8 & 0xFF);
		this.tlv_bytes[offset+1]=(byte)(lspID & 0xFF);
		this.tlv_bytes[offset+2]=(byte)(tunnelID>>>8 & 0xFF);
		this.tlv_bytes[offset+3]=(byte)(tunnelID & 0xFF);
		
		offset += 4;
		
		ByteHandler.encode4bytesLong(extendedTunnelID, tlv_bytes, offset);
		
		offset += 4;
		System.arraycopy(tunnelEndPointIPAddress.getAddress(),0, this.tlv_bytes, offset, 4);
		
	}

	
	public void decode() throws MalformedPCEPObjectException 
	{	
		log.debug("Decoding IPv4LSPIdentifiers TLV");
		byte[] ip=new byte[4]; 
		int offset = 4;
		System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
		
		try 
		{
			tunnelSenderIPAddress=(Inet4Address)Inet4Address.getByAddress(ip);
			log.debug("Sender IP adress, tunnel: "+tunnelSenderIPAddress);
		} 
		catch (UnknownHostException e) 
		{			
			e.printStackTrace();
			throw new MalformedPCEPObjectException();
		}
		
		offset += 4;
		
		lspID = ByteHandler.decode2bytesInteger(tlv_bytes, offset);
		tunnelID = ByteHandler.decode2bytesInteger(tlv_bytes, offset+2);
		
		offset += 4;
		
		extendedTunnelID = ByteHandler.decode4bytesLong(tlv_bytes, offset);
		offset += 4;
		System.arraycopy(this.tlv_bytes,offset, ip, 0, 4);
		try 
		{
			tunnelEndPointIPAddress=(Inet4Address)Inet4Address.getByAddress(ip);
			log.debug("Destination IP adress, tunnel: "+tunnelEndPointIPAddress);
		} 
		catch (UnknownHostException e) 
		{			
			e.printStackTrace();
			throw new MalformedPCEPObjectException();
		}
		 
		
	}
	
	//GETTERS & SETTERS
	
	public Inet4Address getTunnelSenderIPAddress() 
	{
		return tunnelSenderIPAddress;
	}

	public void setTunnelSenderIPAddress(Inet4Address tunnelSenderIPAddress) 
	{
		this.tunnelSenderIPAddress = tunnelSenderIPAddress;
	}

	public int getLspID() 
	{
		return lspID;
	}

	public void setLspID(int lspID) 
	{
		this.lspID = lspID;
	}

	public int getTunnelID() 
	{
		return tunnelID;
	}

	public void setTunnelID(int tunnelID) 
	{
		this.tunnelID = tunnelID;
	}

	public long getExtendedTunnelID() 
	{
		return extendedTunnelID;
	}

	public void setExtendedTunnelID(long extendedTunnelID) 
	{
		this.extendedTunnelID = extendedTunnelID;
	}
	
	

	public Inet4Address getTunnelEndPointIPAddress() {
		return tunnelEndPointIPAddress;
	}

	public void setTunnelEndPointIPAddress(Inet4Address tunnelEndPointIPAddress) {
		this.tunnelEndPointIPAddress = tunnelEndPointIPAddress;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (extendedTunnelID ^ (extendedTunnelID >>> 32));
		result = prime * result + lspID;
		result = prime * result + ((tunnelEndPointIPAddress == null) ? 0 : tunnelEndPointIPAddress.hashCode());
		result = prime * result + tunnelID;
		result = prime * result + ((tunnelSenderIPAddress == null) ? 0 : tunnelSenderIPAddress.hashCode());
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
		IPv4LSPIdentifiersTLV other = (IPv4LSPIdentifiersTLV) obj;
		if (extendedTunnelID != other.extendedTunnelID)
			return false;
		if (lspID != other.lspID)
			return false;
		if (tunnelEndPointIPAddress == null) {
			if (other.tunnelEndPointIPAddress != null)
				return false;
		} else if (!tunnelEndPointIPAddress.equals(other.tunnelEndPointIPAddress))
			return false;
		if (tunnelID != other.tunnelID)
			return false;
		if (tunnelSenderIPAddress == null) {
			if (other.tunnelSenderIPAddress != null)
				return false;
		} else if (!tunnelSenderIPAddress.equals(other.tunnelSenderIPAddress))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IPv4LSPIdentifiersTLV [tunnelSenderIPAddress=" + tunnelSenderIPAddress + ", lspID=" + lspID
				+ ", tunnelID=" + tunnelID + ", extendedTunnelID=" + extendedTunnelID + ", tunnelEndPointIPAddress="
				+ tunnelEndPointIPAddress + "]";
	}

	
	
	
	

}
