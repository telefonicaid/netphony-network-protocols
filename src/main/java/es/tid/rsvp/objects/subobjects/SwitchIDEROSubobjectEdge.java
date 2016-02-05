package es.tid.rsvp.objects.subobjects;

import es.tid.protocol.commons.ByteHandler;

/*
 * ERO inventado como un campeón
 * 
 * ID will be 8 bytes
 * 
 *  0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |L|    Type     |     Length    |          ID (6 bytes)         |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     ID (continued)                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     ID (continued)                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     source interface                          |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     destination interface                     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Associated MAC (Optional)                 |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |  MAC (Optional) (Continued)   |     Second Associated MAC     |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                 Second Associated MAC (Continued)             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                             vlan                              |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   
 */
/**
 * 
 * @author jaume
 *
 */


public class SwitchIDEROSubobjectEdge  extends EROSubobject
{	
	/**
	 * Id of the device, called MAC because it will usually be a MAC
	 */
	
	private byte[] source_SwitchID;
	
	
	/**
	 * Id of the device, called MAC because it will usually be a MAC
	 */
	
	private byte[] dest_SwitchID;
	
	/**
	 * This will probably be the source port
	 */
	
	private int source_int;
	
	
	/**
	 * This will probably be the dest port
	 */
	
	private int dest_int;
	
	/**
	 * This would be the mac of a possible host connected to the switch
	 */
	private byte[] associated_mac = null;
	
	/**
	 * This is only used when a switch is connected to two hosts (one LSP between two hosts connected to the same swtich)
	 */
	private byte[] second_associated_mac = null;
	
	/**
	 * vlan, it will always be of the link with origin switch with SwitchID
	 */
	private Integer vlan;
	
	
	public SwitchIDEROSubobjectEdge()
	{
		super();
		// Length can be 20 or 28 depending if the optional camp is used
		erosolength=48;
		this.setType(SubObjectValues.ERO_SUBOBJECT_SWITCH_ID_EDGE);
	}
	
	public SwitchIDEROSubobjectEdge(byte [] bytes, int offset)
	{
		super(bytes, offset);
		decode();
	}
	
	public void encode()
	{
		this.subobject_bytes=new byte[erosolength];
		encodeSoHeader();
		System.arraycopy(source_SwitchID, 0, this.subobject_bytes, 2, source_SwitchID.length>8? 8:source_SwitchID.length);
		
		int offset = 2 + 10;	
		System.arraycopy(dest_SwitchID, 0, this.subobject_bytes, offset, dest_SwitchID.length>8? 8:dest_SwitchID.length);
		
		offset += 10;
		ByteHandler.IntToBuffer(0,offset*8, 32,source_int,this.subobject_bytes);
		
		offset += 4;
		ByteHandler.IntToBuffer(0,offset*8, 32,dest_int,this.subobject_bytes);
		offset += 4;
		
		if (associated_mac != null)
		{		
			//PCEServer.log.debug("associated_mac.length::"+associated_mac.length);
			//PCEServer.log.debug("subobject_bytes.length::"+subobject_bytes.length);
			System.arraycopy(associated_mac, 0, this.subobject_bytes, offset, 6);
		}
		offset += 6;
		
		if (second_associated_mac != null)
		{		
			//PCEServer.log.debug("associated_mac.length::"+associated_mac.length);
			//PCEServer.log.debug("subobject_bytes.length::"+subobject_bytes.length);
			System.arraycopy(second_associated_mac, 0, this.subobject_bytes, offset, 6);
		}
		offset += 6;
		
		if (vlan != null)
		{
			ByteHandler.IntToBuffer(0,offset*8, 32,vlan,this.subobject_bytes);
		}
		else
		{
			ByteHandler.IntToBuffer(0,offset*8, 32,0,this.subobject_bytes);
		}
	}
	
	public void decode()
	{
		decodeSoHeader();
		
//		if (erosolength != 48)
//		{
//			PCEServer.log.warn("Malformed SwitchIDEROSubobject");
//		}
		
		source_SwitchID=new byte[8]; 
		System.arraycopy(this.subobject_bytes,2, source_SwitchID, 0, 8);
		
		int offset = 2 + 10;
		
		dest_SwitchID=new byte[8]; 
		System.arraycopy(this.subobject_bytes, offset, dest_SwitchID, 0, 8);
		
		offset += 10;
		source_int = ByteHandler.easyCopy(0,31,subobject_bytes[offset],subobject_bytes[offset+1],subobject_bytes[offset+2],subobject_bytes[offset+3]);
		dest_int = ByteHandler.easyCopy(0,31,subobject_bytes[offset+4],subobject_bytes[offset+5],subobject_bytes[offset+6],subobject_bytes[offset+7]);

		offset += 4 + 4;
		associated_mac = new byte[6];
		System.arraycopy(this.subobject_bytes,offset, associated_mac, 0, 6);
		
		offset += 6;
		
		second_associated_mac = new byte[6];
		System.arraycopy(this.subobject_bytes,offset, second_associated_mac, 0, 6);
		
		offset += 6;
		
		vlan = ByteHandler.easyCopy(0,31,subobject_bytes[offset],subobject_bytes[offset + 1],subobject_bytes[offset + 2],subobject_bytes[offset + 3]);
	}
	
	/* GETTERS AND SETTERS */
	
	

	public int getSource_int() 
	{
		return source_int;
	}

	public byte[] getSource_SwitchID() 
	{
		return source_SwitchID;
	}

	public void setSource_SwitchID(byte[] source_SwitchID) 
	{
		this.source_SwitchID = source_SwitchID;
	}

	public byte[] getDest_SwitchID()
	{
		return dest_SwitchID;
	}

	public void setDest_SwitchID(byte[] dest_SwitchID) 
	{
		this.dest_SwitchID = dest_SwitchID;
	}

	public void setSource_int(int source_int) 
	{
		this.source_int = source_int;
	}

	public int getDest_int() 
	{
		return dest_int;
	}

	public void setDest_int(int dest_int) 
	{
		this.dest_int = dest_int;
	}

	public byte[] getAssociated_mac() 
	{
		return associated_mac;
	}

	public void setAssociated_mac(byte[] associated_mac) 
	{
		this.associated_mac = associated_mac;
	}

	public Integer getVlan() 
	{
		return vlan;
	}

	public void setVlan(Integer vlan)
	{
		this.vlan = vlan;
	}

	public byte[] getSecond_associated_mac() 
	{
		return second_associated_mac;
	}

	public void setSecond_associated_mac(byte[] second_associated_mac)
	{
		this.second_associated_mac = second_associated_mac;
	}	
}
