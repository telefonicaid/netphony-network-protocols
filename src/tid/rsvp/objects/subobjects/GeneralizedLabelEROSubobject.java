package tid.rsvp.objects.subobjects;

import tid.rsvp.RSVPProtocolViolationException;
import tid.rsvp.constructs.gmpls.DWDMWavelengthLabel;

/**
 * Label ERO Subobject with Generalized LABEL.
 * DWDM Wavelength Label Assumed
 * 
 * @author Oscar Gonzalez de Dios
 *
 */
public class GeneralizedLabelEROSubobject extends LabelEROSubobject{
	
	byte[] label;
	DWDMWavelengthLabel dwdmWavelengthLabel;

	public GeneralizedLabelEROSubobject(){
		super();
		this.setCtype(SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL);
	}
	
	public GeneralizedLabelEROSubobject(byte []bytes, int offset){
		super(bytes, offset);
		decode();		
	}
	
	
	/**
	 * Encode Generalized LABEL
	 */
	public void encode() {		
		if (dwdmWavelengthLabel!=null) {
			try {
				dwdmWavelengthLabel.encode();
			} catch (RSVPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			label=new byte[dwdmWavelengthLabel.getLength()];
			System.arraycopy(dwdmWavelengthLabel.getBytes(), 0, this.label, 0, this.label.length);
		}
		this.setErosolength(4+label.length);
		this.subobject_bytes=new byte[this.erosolength];
		this.setCtype(SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL);
		this.encodeLabelHeader();
		this.encodeSoHeader();
		System.arraycopy(this.label, 0, this.subobject_bytes, 4, this.label.length);
	}

	/**
	 * Decode Generalized LABEL
	 */
	public void decode() {
		int labelLength=this.getErosolength()-4;
		if (labelLength == 4 || labelLength==8){
			dwdmWavelengthLabel =  new DWDMWavelengthLabel();
			try {
				dwdmWavelengthLabel.decode(this.getSubobject_bytes(), 4);
			} catch (RSVPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		label=new byte[labelLength];
		System.arraycopy(this.getSubobject_bytes(), 4, label, 0, labelLength);
	}

	public DWDMWavelengthLabel getDwdmWavelengthLabel() {
		return dwdmWavelengthLabel;
	}

	public void setDwdmWavelengthLabel(DWDMWavelengthLabel dwdmWavelengthLabel) {
		this.dwdmWavelengthLabel = dwdmWavelengthLabel;
	}

	public byte[] getLabel() {
		return label;
	}

	public void setLabel(byte[] label) {
		this.label = label;
	}
	public String toString(){
		String resp="/ELC: ";
		if (dwdmWavelengthLabel != null)
			resp+=dwdmWavelengthLabel.toString();
		else{
			for (int i=0;i<label.length;++i){
				resp=resp+Integer.toHexString(label[i]&0xFF);
			}	
		}
		return resp;
	}
}