package es.tid.pce.pcep.objects;

/**
 * Base abstract class for representing Bandwidth Object.
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public abstract class Bandwidth extends PCEPObject{
	
	public Bandwidth(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH);
	}
	
	public Bandwidth(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
	}
		
	public abstract Bandwidth duplicate();
}
