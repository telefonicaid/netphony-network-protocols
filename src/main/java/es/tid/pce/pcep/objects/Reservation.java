package es.tid.pce.pcep.objects;

/**
 * Reservation Object from draft-gonzalezdedios-pce-resv-res-context-state.
 * 
 *    RESERVATION Object-Class is to be assigned by IANA.

   RESERVATION Object-Type is to be assigned by IANA (recommended
   value=1)

   The RESERVATION object indicates the intention of the PCC to set up
   the requested path and request the PCE to reserve the resources of
   the computed path to avoid being used by other requests.

        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                            Timer                              |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |S N L|                  Resource Type                          |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                       Optional TLVs                           |
       ...
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


   o  Timer is the value in ms of the time that the resources should be
      blocked, encoded as a 32 bit unsigned integer.

   o  Resource Type indicates the type of resource to be reserved.  A
      value of 0 means the default resource type:

      *  Bandwidth (PSC, L2SC, ...)

      *  Time Slot (Sonet/SDH TDM)

      *  Tributary Slot (G709 OTN ODU-k TDM)

      *  Wavelength (G709 OTN OCh or WSON LSC)

   o  Bit L: if set, TE Links should be part of the reservation, and
      excluded from subsequent request.

   o  Bit N: if set, Nodes should be part of the reservation.

   o  Bit S: if set, the set of SRLG (Shared Risk Link Group) deduced
      from the associated resources (i.e., the union of SRLGs of the
      links) should be part of the reservation.

   Currently no TLVs are defined.
 * @author ogondio
 *
 */

public class Reservation extends PCEPObject {
	
	public Reservation(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_RESERVATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_RESERVATION);
	}
	
	public Reservation (byte []bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	/**
	 *  Timer is the value in ms of the time that the resources should be
      blocked, encoded as a 32 bit unsigned integer.
	 */
	private long timer;

	/**
	 * Encode the reservation object
	 */
	public void encode() {
		this.setObjectLength(12);
		this.object_bytes=new byte[this.getLength()];
		encode_header();
		this.object_bytes[4]=(byte)(timer >>> 24);
		this.object_bytes[5]=(byte)(timer >>> 16);
		this.object_bytes[6]=(byte)(timer >>> 8);
		this.object_bytes[7]=(byte)timer ;

	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		timer= 0;		
		for (int k = 0; k < 4; k++) {
			timer = (timer << 8) | (object_bytes[k+4] & 0xff);
		}
	}

	public long getTimer() {
		return timer;
	}

	public void setTimer(long timer) {
		this.timer = timer;
	}
	
	public String toString(){
		String ret="<RV= "+timer+"ms>";
		return ret;
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (timer ^ (timer >>> 32));
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
		Reservation other = (Reservation) obj;
		if (timer != other.timer)
			return false;
		return true;
	}
	
	

}
