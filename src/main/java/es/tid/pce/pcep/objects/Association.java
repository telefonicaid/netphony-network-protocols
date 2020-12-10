package es.tid.pce.pcep.objects;

/**
 * Base abstract class for representing Association.
 * @author Oscar Gonzalez de Dios 
 *
 */
public abstract class Association extends PCEPObject{
	
	public Association(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION);
	}
	
	public Association(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
	}
		
}
