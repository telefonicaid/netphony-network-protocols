package es.tid.pce.pcep.objects;


/**
 * 3.5.  RESERVATION_CONF object format

   The RESERVATION_CONF object is optional.  The RESERVATION_CONF object
   indicates that the PCE has reserved the resources of computed path to
   avoid being used by other requests.  The RESERVATION_CONF object is
   sent in the PCRep.

   The RESERVATION_CONF Object-Class is to be assigned by IANA.

   The RESERVATION_CONF Object-Type is to be assigned by IANA
   (recommended value=1)

   The format of the RESERVATION_CONF object body is:

        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                     Reservation ID                            |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                     Reservation timer                         |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |S N L|             Reservation Type                            |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * @author ogondio
 *
 */
public class ReservationConf extends PCEPObject {

	private long reservationID;
	
	public ReservationConf(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_RESERVATION_CONF);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_RESERVATION_CONF);
	}
	
	public ReservationConf(byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	public void encode() {
		this.setObjectLength(16);
		this.object_bytes=new byte[this.getLength()];
		encode_header();
		this.object_bytes[4]=(byte)(reservationID >>> 24);
		this.object_bytes[5]=(byte)(reservationID >>> 16);
		this.object_bytes[6]=(byte)(reservationID >>> 8);
		this.object_bytes[7]=(byte)reservationID ;

	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		reservationID= 0;		
		for (int k = 0; k < 4; k++) {
			reservationID = (reservationID << 8) | (object_bytes[k+4] & 0xff);
		}
	}

	public long getReservationID() {
		return reservationID;
	}

	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}
	
	

}
