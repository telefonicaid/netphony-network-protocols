package es.tid.pce.pcep.objects;


import es.tid.pce.pcep.PCEPProtocolViolationException;


/**
 * <p> Base class for representing Generalized EndPoints Object.</p>
 *
 *
 */
public abstract class GeneralizedEndPoints extends EndPoints{

	private int generalizedendpointType;

	public GeneralizedEndPoints(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS);
	}
	public GeneralizedEndPoints(byte[] bytes, int offset) throws MalformedPCEPObjectException, PCEPProtocolViolationException{
		super(bytes, offset);
		decode();
	}
	/**
	 * Encode Generalized EndPoint Object
	 */
	public void encodeEndpointHeader() {
		int offset=4;
		this.object_bytes[4]=0x00;
		this.object_bytes[5]=0x00;
		this.object_bytes[6]=(byte)((generalizedendpointType>>8)&0xFF);
		this.object_bytes[7]=(byte)((generalizedendpointType)&0xFF);
		
	
	}

	//Getters and Setters

	public int getGeneralizedEndPointsType() {
		return generalizedendpointType;
	}

	public void setGeneralizedEndPointsType(int generalizedendpointType) {
		this.generalizedendpointType = generalizedendpointType;
	}


	public static int getGeneralizedEndPointsType(byte[] bytes, int offset) {
		int type =bytes[offset+7]&0xFF;
		return type;
	}

}