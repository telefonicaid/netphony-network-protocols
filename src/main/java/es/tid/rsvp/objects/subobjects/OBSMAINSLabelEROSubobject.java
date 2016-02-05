package es.tid.rsvp.objects.subobjects;

/**
 * Represents a ERO Subobject with the OBS Label defined in MAINS.
 * @author ogondio
 *
 */
public class OBSMAINSLabelEROSubobject extends LabelEROSubobject {
	
	byte[] label;
	
	public OBSMAINSLabelEROSubobject(){
		super();
		this.setCtype(SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_OBS_MAINS_LABEL);
	}
	
	public OBSMAINSLabelEROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	public void encode() {
		this.setErosolength(4+label.length);
		this.subobject_bytes=new byte[this.erosolength];
		this.setCtype(SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_OBS_MAINS_LABEL);
		this.encodeLabelHeader();
		this.encodeSoHeader();
		System.arraycopy(this.label, 0, this.subobject_bytes, 4, this.label.length);
		
	}

	
	public void decode() {
		int labelLength=this.getErosolength()-4;
		label=new byte[labelLength];
		System.arraycopy(this.getSubobject_bytes(), 4, label, 0, labelLength);
		
	}


	public byte[] getLabel() {
		return label;
	}


	public void setLabel(byte[] label) {
		this.label = label;
	}

	public String toString(){
		String resp="/ELC: OBS Label";			
		return resp;
	}

	
}
