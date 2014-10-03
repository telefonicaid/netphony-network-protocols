package tid.pce.pcep.objects;



/**


 * @author Oscar Gonzalez de Dios
 */

public abstract class LabelSet extends PCEPObject{


	//Constructors
	
	public LabelSet(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_LABEL_SET);
	}

	public LabelSet(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
	}
	
}
