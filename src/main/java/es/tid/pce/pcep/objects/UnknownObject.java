package es.tid.pce.pcep.objects;

/**
 * Placeholder to store unknow objects.
 * 
 * Only the bytes are stored
 * @author ogondio
 *
 */
public class UnknownObject extends PCEPObject{
	
	public UnknownObject () {
		super();
	}
	
	public UnknownObject (byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}

	public void encode(){
	}

	@Override
	public void decode() {
		// TODO Auto-generated method stub
		
	}
	
}
