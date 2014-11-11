package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * 
 * @author jaume
 */

public class XifiEndPointTLV extends PCEPTLV
{
	
	private String switchID;
	private int port;
	private String mac;
	
	public XifiEndPointTLV()
	{
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_XIFI);
		
	}
	
	public XifiEndPointTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException
	{
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() 
	{
		
		int length = 8 + 6 + 4;
				
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset = 4;

		log.debug("switchID:::::"+switchID);
		log.debug("mac:::::"+mac);
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID),0, this.tlv_bytes, offset, 8);
		
		offset += 8;
		ByteHandler.IntToBuffer(0,offset*8, 32, port,this.tlv_bytes);
		
		offset += 4;
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(mac),0, this.tlv_bytes, offset, 6);
	
	}
	
	public void decode() throws MalformedPCEPObjectException
	{
		log.debug("Decoding Xifi EndPoint TLV");
		
		int offset = 4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		log.debug("TLV Length:" + this.getTLVValueLength());
		

		byte[] mac=new byte[8]; 
		System.arraycopy(this.tlv_bytes, offset, mac, 0, 8);
		switchID=ByteHandler.ByteMACToString(mac);
		log.debug("switchID:::::"+switchID);
		log.debug("mac:::::"+mac);
		
		offset += 8;
		port = ByteHandler.easyCopy(0,31,tlv_bytes[offset],tlv_bytes[offset + 1],tlv_bytes[offset + 2],tlv_bytes[offset + 3]);
		
		offset += 4;
		System.arraycopy(this.tlv_bytes,offset, mac, 0, 6);	
		this.mac = ByteHandler.ByteMACToString(mac);
	}
	
	
	//GETTERS AND SETTERS

	public String getSwitchID() 
	{
		return switchID;
	}

	public void setSwitchID(String switchID)
	{
		this.switchID = switchID;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}

	public String getMac() 
	{
		return mac;
	}

	public void setMac(String mac) 
	{
		this.mac = mac;
	}
}
