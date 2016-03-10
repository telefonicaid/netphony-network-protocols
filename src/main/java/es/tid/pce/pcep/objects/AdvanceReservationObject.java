package es.tid.pce.pcep.objects;

import java.util.LinkedList;
import java.util.Vector;

/**
 * <h1> Advance Reservation Object as described in GEYSERS D4.1</h1>
 * 
 * <p> From GEYSERS D4.1 Section 6.3.1.3 Advance Reservation</p>

   The path computation for an advance reservation is requested
   including a new object, called ADVANCE-RESERVATION, in the PCReq
   message. This object is optional and is used when the path
   computation must be performed for a time slot in the future. For
   unicast and anycast requests the object specifies the start-time
   and the duration of the service. In case of assisted unicast
   requests multiple pairs of such values can be provided and the path
   computation reply should return a quotation in terms of network cost
   for each time slot.
   Object-Class and Object-Type for the ADVANCE-RESERVATION object are
   to be defined. The P flag in the header must be set. The format of
   the object is the following, where:
   	Start time is a 32-bit unsigned number specifying the time when
        the resources reserved for the service have to be committed.
   	Duration is a 32-bit unsigned number specifying the service 
        lifetime in units of seconds. 
   0                   1                   2                   3
   0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ 
   |                         Start time                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                          Duration                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                             ...                             //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                         Start time                            |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                          Duration                             |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   AR Object-Class is 16.

   AR Object-Type is 1.
   
 * @author Alejandro Tovar de Due�as
 *
 */
public class AdvanceReservationObject extends PCEPObject{

	private LinkedList<AdvanceReservation> ARList;
	/**
	 * 
	 */
	public AdvanceReservationObject(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_ADVANCE_RESERVATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_ADVANCE_RESERVATION);
		ARList = new LinkedList<AdvanceReservation>();
	}
	
	/**
	 * 
	 */
	public AdvanceReservationObject(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		ARList = new LinkedList<AdvanceReservation>();
		decode();
	}

	/**
	 * Encode Advance Reservation Object
	 */
	public void encode() {
		int len=4;//The four bytes of the header
		ObjectLength=len+ARList.size()*8; //AR length = 8
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;
		for (int k=0 ; k<ARList.size(); k=k+1) {					
			System.arraycopy(ARList.get(k), 0, this.object_bytes, pos, 8);//AR length = 8
			pos=pos+8;//AR length = 8
		}				
	}
	
	/**
	 * Decodes Advance Reservation Object
	 * @throws MalformedPCEPObjectException Exception when the object is malformed
	 */
	public void decode() throws MalformedPCEPObjectException{
		boolean fin=false;
		AdvanceReservation advancereservation = null;
		int i=0;
		int offset=4;//Position of the next subobject
		if (ObjectLength==4){
			fin=true;
		}
		while (!fin) {
			System.arraycopy(this.object_bytes[i],8, advancereservation, 0, 8);
			ARList.add(advancereservation);
			offset=offset+8;
			if (offset>=ObjectLength){
				//No more Advance Reservations
				fin=true;
			}
			i=i+1;
		}
		
	}

	public void addAR(AdvanceReservation arelem){
		ARList.add(arelem);
	}
	
	
	public void addARVector(Vector<AdvanceReservation> arvec){
		ARList.addAll(arvec);		
	}

	public LinkedList<AdvanceReservation> getARList() {
		return ARList;
	}

	public void setARList(LinkedList<AdvanceReservation> aRList) {		
		ARList = aRList;
	}
			

	public abstract class AdvanceReservation{
		
		protected byte [] AR_bytes;
		public int Start_time;
		public int Duration;
		
	public AdvanceReservation(){
			
		}
		
		public AdvanceReservation(byte[] bytes){
			Start_time=((bytes[0]<<8)& 0xFF00) |  (bytes[3] & 0xFF);//�qu� va en vez de las 0xFF00 y 0xFF?
			Duration=((bytes[4]<<8)& 0xFF00) |  (bytes[7] & 0xFF);
			System.arraycopy(bytes, 0, AR_bytes, 0, 8);

		}
		
		public int getStart_time() {
			return Start_time;
		}
		
		public int getDuration() {
			return Duration;
		}
			
		
		public byte[] getAR_bytes() {
			return AR_bytes;
		}
		
		public void setStart_time(int Startime) {
			this.Start_time = Startime;
		}
		
		public void setDuration(int duration) {
			this.Duration = duration;
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ARList == null) ? 0 : ARList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdvanceReservationObject other = (AdvanceReservationObject) obj;
		if (ARList == null) {
			if (other.ARList != null)
				return false;
		} else if (!ARList.equals(other.ARList))
			return false;
		return true;
	}
	
	

}
