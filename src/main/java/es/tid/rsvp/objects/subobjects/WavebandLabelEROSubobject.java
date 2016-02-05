package es.tid.rsvp.objects.subobjects;

public class WavebandLabelEROSubobject extends LabelEROSubobject {

public WavebandLabelEROSubobject(){
	super();
	this.setCtype(SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL);
}
		
public WavebandLabelEROSubobject(byte []bytes, int offset){
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
