package tid.rsvp.objects.subobjects;

import tid.protocol.commons.ByteHandler;




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


public class OpenFlowIDEROSubobject extends EROSubobject
{

	private String switchID;	
	
	public OpenFlowIDEROSubobject()
	{
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_ID_OPEN_FLOW);
	}
	
	public OpenFlowIDEROSubobject(byte[] bytes, int offset)
	{
		super(bytes, offset);
		decode();		
	}
	
	/**
	 * Encode Unnumbered interface ERO Subobject
	 */
	public void encode()
	{
		this.erosolength=12;
		this.subobject_bytes=new byte[this.erosolength];
		encodeSoHeader();
		this.subobject_bytes[2]=0x00;
		this.subobject_bytes[3]=0x00;
		
		System.arraycopy(ByteHandler.MACFormatStringtoByteArray(switchID), 0, this.subobject_bytes, 4, 8);
			
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
	
	public String toString() {
		return this.switchID;
	}


}