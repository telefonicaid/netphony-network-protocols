package tid.rsvp.objects.subobjects;

/**
 * ERO inventado como un campeón
 * 
 */
/**
 * 
 * @author jaume
 *
 */

public class SwitchIDEROSubobjectEnd extends EROSubobject
{
	
	/**
	 * Id of the device, called MAC because it will usually be a MAC
	 */
	
	private byte[] switchID;
	
	public SwitchIDEROSubobjectEnd()
	{
		super();
		// Length can be 20 or 28 depending if the optional camp is used
		erosolength=48;
		this.setType(SubObjectValues.ERO_SUBOBJECT_SWITCH_ID_EDGE);
	}
	
	public SwitchIDEROSubobjectEnd(byte [] bytes, int offset)
	{
		super(bytes, offset);
		decode();
	}

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decode() {
		// TODO Auto-generated method stub
		
	}

	public byte[] getSwitchID() {
		return switchID;
	}

	public void setSwitchID(byte[] switchID) {
		this.switchID = switchID;
	}
	

}
