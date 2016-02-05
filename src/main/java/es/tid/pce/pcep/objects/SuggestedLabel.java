package es.tid.pce.pcep.objects;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.constructs.gmpls.DWDMWavelengthLabel;

/**
 * ¥	Attaching a new LABEL object (class 129, type 1) as an attribute of the computed path. If there is only one label object it is the SUGGESTED_LABEL.
 
     0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Object-Class  |   OT  |Res|P|I|   Object Length (bytes)       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                  label                                        |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * @author Oscar Gonzalez de Dios
 * @version 0.1
 */

public class SuggestedLabel extends PCEPObject{

	byte[] label;
	DWDMWavelengthLabel dwdmWavelengthLabel;
	
	//Constructors
	
	public SuggestedLabel(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_SUGGESTED_LABEL);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_SUGGESTED_LABEL);
	}

	public SuggestedLabel(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	//Encode and Decode
	
	/**
	 * Encode CLOSE message
	 * Only reason is encoded. The rest is set to 0
	 */
	public void encode(){
		if (dwdmWavelengthLabel!=null) {
			try {
				dwdmWavelengthLabel.encode();
			} catch (RSVPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			label=new byte[dwdmWavelengthLabel.getLength()];
			System.arraycopy(dwdmWavelengthLabel.getBytes(), 0, this.label, 4, this.label.length);
		}
		this.setObjectLength(4+label.length);
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		System.arraycopy(this.label, 0, this.getBytes() , 4, this.label.length);
	}
	
	/**
	 * Decode SuggestedLabel message. 
	 */
	public void decode() throws MalformedPCEPObjectException{
		int labelLength=this.getLength()-4;
		if (labelLength == 4 || labelLength==8){
			dwdmWavelengthLabel =  new DWDMWavelengthLabel();
			try {
				dwdmWavelengthLabel.decode(this.getObject_bytes(),4);
			} catch (RSVPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		label=new byte[labelLength];
		System.arraycopy(this.getObject_bytes(), 4, label, 0, labelLength);
	}

	public byte[] getLabel() {
		return label;
	}

	public void setLabel(byte[] label) {
		this.label = label;
	}

	public DWDMWavelengthLabel getDwdmWavelengthLabel() {
		return dwdmWavelengthLabel;
	}

	public void setDwdmWavelengthLabel(DWDMWavelengthLabel dwdmWavelengthLabel) {
		this.dwdmWavelengthLabel = dwdmWavelengthLabel;
	}
	
		//Getters and Setters
	
	public String toString(){
		if (dwdmWavelengthLabel!=null){
			return dwdmWavelengthLabel.toString();
		}else {
			return "";
		}
	}
	
}
