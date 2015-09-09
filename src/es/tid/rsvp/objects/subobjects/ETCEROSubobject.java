package es.tid.rsvp.objects.subobjects;

public class ETCEROSubobject extends EROSubobject {

	public ETCEROSubobject(){
		super();
		this.setType(SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX);
	}
	
	public ETCEROSubobject (byte [] bytes, int offset){
		super(bytes, offset);
		decode();
	}
	
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decode() {
		// TODO Auto-generated method stub
		
	}

}
