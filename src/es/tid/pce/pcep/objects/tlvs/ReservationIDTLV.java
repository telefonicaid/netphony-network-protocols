package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;


/**
 *  The TLV indicates the reservation ID (Type TBA by IANA).

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                       Reservation ID                          |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author ogondio
 *
 */
public class ReservationIDTLV extends PCEPTLV{

	private long reservationID;
	
	public ReservationIDTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_RESERVATION_ID);
	}
	
	public ReservationIDTLV(byte[] bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}
	
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.TotalTLVLength];
		encodeHeader();
		this.tlv_bytes[4]=(byte)(reservationID >>> 24);
		this.tlv_bytes[5]=(byte)(reservationID >> 16 );
		this.tlv_bytes[6]=(byte)(reservationID >> 8 );
		this.tlv_bytes[7]=(byte)reservationID;		
	}
	
	public void decode() throws MalformedPCEPObjectException{
		if (this.TLVValueLength!=4){
			throw new MalformedPCEPObjectException();
		}
		log.finest("Decoding Domain ID TLV");
		reservationID = 0;		
		for (int k = 0; k < 4; k++) {
			reservationID = (reservationID << 8) | (this.tlv_bytes[k+4] & 0xff);
		}
	}

	public long getReservationID() {
		return reservationID;
	}

	public void setReservationID(long reservationID) {
		this.reservationID = reservationID;
	}
	
	
}
