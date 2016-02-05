package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.SwitchEncodingType;

/**
 * <p>Represents a SWITCH-LAYER Object, as defined in http://tools.ietf.org/id/draft-ietf-pce-inter-layer-ext-04.txt</p>
 * <p> From draft-ietf-pce-inter-layer-ext-04.txt, section  3.2. SWITCH-LAYER Object </p>
 * The SWITCH-LAYER object is optional on a PCReq message and 
    specifies switching layers in which a path MUST, or MUST NOT, be 
    established. A switching layer is expressed as a switching type and 
    encoding type. The SWITCH-LAYER object MUST NOT be used on a PCReq 
    unless an INTER-LAYER object is also present on the PCReq message. 
     
    The SWITCH-LAYER object is optional on a PCRep message, where it is 
    used with the NO-PATH object in the case of unsuccessful path 
    computation to indicate the set of constraints that could not be 
    satisfied. 
     
    SWITCH-LAYER Object-Class is to be assigned by IANA (recommended 
    value=19) 
     
    SWITCH-LAYER Object-Type is to be assigned by IANA (recommended 
    value=1) 
     
    The format of the SWITCH-LAYER object body is as follows: 
      
      0                   1                   2                   3 
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
    | LSP Enc. Type |Switching Type | Reserved                    |I| 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
    |                               .                               | 
    //                              .                              // 
    |                               .                               | 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
    | LSP Enc. Type |Switching Type | Reserved                    |I| 
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
     
    Each row indicates a switching type and encoding type that must or 
    must not be used for specified layer(s) in the computed path.  
     
    The format is based on [RFC3471], and has equivalent semantics. 
     
    LSP Encoding Type (8 bits): see [RFC3471] for a description of 
    parameters. 
     
    Switching Type (8 bits): see [RFC3471] for a description of 
    parameters. 
     
    I flag (1 bit): the I flag indicates whether a layer with the 
    specified switching type and encoding type must or must not be used 
    by the computed path. When the I flag is set (one), the computed 
    path MUST traverse a layer with the specified switching type and 
    encoding type. When the I flag is clear (zero), the computed path 
     
    MUST NOT enter or traverse any layer with the specified switching 
    type and encoding type. 
     
    When a combination of switching type and encoding type is not 
    included in SWITCH-LAYER object, the computed path MAY traverse a 
    layer with that combination of switching type and encoding type. 
     
    A PCC may want to specify only a Switching Type and not an LSP 
    Encoding Type. In this case, the LSP Encoding Type is set to zero. 

 * @author ogondio
 *
 */
public class SwitchLayer extends PCEPObject {
	
	private LinkedList<SwitchEncodingType> switchLayers;

	public SwitchLayer(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_SWITCH_LAYER);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_SWITCH_LAYER);
		switchLayers=new LinkedList<SwitchEncodingType>();
	}

	public SwitchLayer(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		switchLayers=new LinkedList<SwitchEncodingType>();
		decode();
	}
	/**
	 * Encode SwitchLayer PCEP Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		for (int k=0; k<switchLayers.size();k=k+1){
			try {
				switchLayers.get(k).encode();
			} catch (PCEPProtocolViolationException e) {
				e.printStackTrace();
			}			
			len=len+switchLayers.get(k).getLength();
		}
		this.setObjectLength(len);
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<switchLayers.size(); k=k+1) {					
			System.arraycopy(switchLayers.get(k).getBytes(),0, this.object_bytes, pos, switchLayers.get(k).getLength());
			pos=pos+switchLayers.get(k).getLength();
		}				
	}
	
	
	/**
	 * Decode SwitchLayer PCEP Object
	 */
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (ObjectLength==4){
			fin=true;
		}
		while (!fin) {
			SwitchEncodingType set;
			try {
				set = new SwitchEncodingType(this.getBytes(),offset);
				switchLayers.add(set);
			} catch (PCEPProtocolViolationException e) {
				throw new MalformedPCEPObjectException();
			}			
			offset=offset+set.getLength();
			if (offset>=ObjectLength){
				fin=true;
			}
		}

	}

	public LinkedList<SwitchEncodingType> getSwitchLayers() {
		return switchLayers;
	}

	public void setSwitchLayers(LinkedList<SwitchEncodingType> switchLayers) {
		this.switchLayers = switchLayers;
	}
	
	

}
