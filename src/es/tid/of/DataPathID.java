package es.tid.of;

/**
 *
 * @author b.mvas
 *
 */

public class DataPathID {
	/**
	 * Source dataPath ID
	 */
	private String dataPathID;


	public DataPathID(){
		//System.out.println("* Me meto en DataPathID");
	}

	public static DataPathID getByName(String dataPathIDString) {
		DataPathID datapath = new DataPathID();
		datapath.setDataPathID(dataPathIDString);

		return datapath;
	}
	

	public static DataPathID getByNameBytes(byte[] id) {
		DataPathID datapath = new DataPathID();
	    String idString = new String(id);
		datapath.setDataPathID(idString);
		return datapath;
	}

	public String getDataPathID() {
		return dataPathID;
	}

	public void setDataPathID(String dataPathID) {
		this.dataPathID = dataPathID;
	}


	public String toString(){
		return dataPathID;
	}

	@Override
	public boolean equals(Object object){
		//System.out.println("* Me meto en el equals: "+this.getDataPathID()+" "+object.toString());
		if(object instanceof DataPathID && (((DataPathID)object).getDataPathID()).equals(this.getDataPathID())){
		//	System.out.println("* True"); 
			return true;
		} else {
		//	System.out.println("* False"); 
			return false;
		}
	}
	@Override
	public int hashCode(){
		//System.out.println("* Me meto en el hashCode: "+this.getDataPathID());
		return dataPathID.hashCode();
	}

}