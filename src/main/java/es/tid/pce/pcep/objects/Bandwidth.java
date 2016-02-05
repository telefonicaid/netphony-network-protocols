package es.tid.pce.pcep.objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> Base abstract class for representing Bandwidth Object.</p>
 * <p> </p>
 * <pre>
 
 * </pre>  
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public abstract class Bandwidth extends PCEPObject{
	protected static final Logger log = LoggerFactory.getLogger("PCEPParser");
	
	public Bandwidth(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH);
	}
	public Bandwidth(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
	}
		
	public abstract Bandwidth duplicate();
}
