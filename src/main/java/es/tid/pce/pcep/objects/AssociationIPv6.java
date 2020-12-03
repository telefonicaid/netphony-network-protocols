package es.tid.pce.pcep.objects;

public class AssociationIPv6 extends PCEPObject {

	public AssociationIPv6() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV6);
	}

	public AssociationIPv6(byte[] bytes, int offset) throws MalformedPCEPObjectException {
		super(bytes, offset);

	}

	@Override
	public void encode() {

	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		// TODO Auto-generated method stub

	}

}
