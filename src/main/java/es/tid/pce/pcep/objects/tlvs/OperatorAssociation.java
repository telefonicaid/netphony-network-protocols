package es.tid.pce.pcep.objects.tlvs;

public class OperatorAssociation {
	
	private int assocType=0;
	
	private int startAssocID=0;
	
	private int range=0;

	public OperatorAssociation() {
		// TODO Auto-generated constructor stub
	}

	public int getAssocType() {
		return assocType;
	}

	public void setAssocType(int assocType) {
		this.assocType = assocType;
	}

	public int getStartAssocID() {
		return startAssocID;
	}

	public void setStartAssocID(int startAssocID) {
		this.startAssocID = startAssocID;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
