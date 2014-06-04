package tid.rsvp.objects.subobjects;

import tid.protocol.commons.ByteHandler;
import tid.util.UtilsFunctions;



/**
 * EROSubobject copy of UnnumberIfIDEROSubobject but instead of an Inet4Address there will
 * be an OpenFlow_Switch_ID of 8 bytes.
 * 
 *     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |    Reserved (MUST be zero)    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                           Switch Id                           |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Switch Id(continuation)                   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Interface ID (32 bits)                    |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * 
 * 
 * 
 * @author jaume
 *
 */


public class OpenFlowUnnumberIfIDEROSubobject extends EROSubobject
{

	private String switchID;
	public long interfaceID;//32 bit Interface ID
	
	
	public OpenFlowUnnumberIfIDEROSubobject()
	{
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID_OPEN_FLOW);
	}
	
	public OpenFlowUnnumberIfIDEROSubobject(byte[] bytes, int offset)
	{
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Encode Unnumbered interface ERO Subobject
	 */
	public void encode()
	{
		this.erosolength=16;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[2]=0x00;
		this.subobject_bytes[3]=0x00;
		
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID), 0, this.subobject_bytes, 4, 8);
		
		int offset = 4 + 8;
		
		this.subobject_bytes[offset]=(byte)(interfaceID >>> 24);
		this.subobject_bytes[offset + 1]=(byte)(interfaceID >>> 16 & 0xff);
		this.subobject_bytes[offset + 2]=(byte)(interfaceID >>> 8 & 0xff);
		this.subobject_bytes[offset + 3]=(byte)(interfaceID & 0xff);	
	}
	
	/**
	 * Decode Unnumbered interface ERO Subobject
	 */
	public void decode()
	{
		decodeSoHeader();
		
		byte[] mac = new byte[8]; 
		System.arraycopy(this.subobject_bytes,4, mac, 0, 8);
		switchID = ByteHandler.ByteMACToString(mac);
		
		int offset = 12;
		
		interfaceID=0;
		for (int k = 0; k < 4; k++) {
			interfaceID = (interfaceID << 8) | (this.subobject_bytes[k+offset] & 0xff);
		}			
	}
	// GETERS SETTERS
	
	public String getSwitchID() 
	{
		return switchID;
	}

	public void setSwitchID(String switchID) 
	{
		this.switchID = switchID;
	}
	
	public long getInterfaceID() 
	{
		return interfaceID;
	}

	public void setInterfaceID(long interfaceID) 
	{
		this.interfaceID = interfaceID;
	}
	public String toString(){
		return this.switchID+"/"+this.interfaceID;
	}


}