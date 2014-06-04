package tid.pce.pcep.objects;

public abstract class GeneralizedBandwidth extends PCEPObject{
	
	protected int TrafficSpecLength;
	
	protected int  TSpecType;
	
	public GeneralizedBandwidth(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_GENERALIZED_BANDWIDTH);
	}
	public GeneralizedBandwidth (byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
	}
}
