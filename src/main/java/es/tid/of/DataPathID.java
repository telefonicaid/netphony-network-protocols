package es.tid.of;

/**
 *
 * @author b.mvas , b.jmgj
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
		String idString = "";
		for (int i = 0; i<id.length ;i++ ){
			if (((int)id[i] & 0xff) <= 15){
				idString = idString +"0";
			}
			idString = idString + Integer.toHexString(id[i] & 0xff);
			if (i != (id.length - 1)){
				idString = idString + ":";
			}
		}
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataPathID other = (DataPathID) obj;
		if (dataPathID == null) {
			if (other.dataPathID != null)
				return false;
		} else if (!dataPathID.equalsIgnoreCase(other.dataPathID))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataPathID == null) ? 0 : dataPathID.hashCode());
		return result;
	}

}