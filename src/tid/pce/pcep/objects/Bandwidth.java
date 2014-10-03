package tid.pce.pcep.objects;

/**
 * <p> Base abstract class for representing Bandwidth Object.</p>
 * <p> </p>
 * <pre>
 
 * </pre>  
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
