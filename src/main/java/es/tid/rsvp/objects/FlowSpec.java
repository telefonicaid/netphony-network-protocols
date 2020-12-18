package es.tid.rsvp.objects;

/*
 * 

   RFC 2205                          RSVP                    September 1997


   A.8 FLOWSPEC Class

      FLOWSPEC class = 9.

      o    Reserved (obsolete) flowspec object: Class = 9, C-Type = 1

      o    Inv-serv Flowspec object: Class = 9, C-Type = 2

           The contents and encoding rules for this object are specified
           in documents prepared by the int-serv working group [RFC
           2210].

3.2.1 FLOWSPEC object when requesting Controlled-Load service

   The format of an RSVP FLOWSPEC object originating at a receiver
   requesting Controlled-Load service is shown below. Each of the TSpec
   fields is represented using the preferred concrete representation
   specified in the 'Invocation Information' section of [RFC 2211]. The
   value of 5 in the per-service header (field (c), below) indicates
   that Controlled-Load service is being requested.










Wroclawski                  Standards Track                    [Page 12]

RFC 2210                   RSVP with INTSERV              September 1997


        31           24 23           16 15            8 7             0
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   1   | 0 (a) |    reserved           |             7 (b)             |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   2   |    5  (c)     |0| reserved    |             6 (d)             |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   3   |   127 (e)     |    0 (f)      |             5 (g)             |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   4   |  Token Bucket Rate [r] (32-bit IEEE floating point number)    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   5   |  Token Bucket Size [b] (32-bit IEEE floating point number)    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   6   |  Peak Data Rate [p] (32-bit IEEE floating point number)       |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   7   |  Minimum Policed Unit [m] (32-bit integer)                    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   8   |  Maximum Packet Size [M]  (32-bit integer)                    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

     (a) - Message format version number (0)
     (b) - Overall length (7 words not including header)
     (c) - Service header, service number 5 (Controlled-Load)
     (d) - Length of controlled-load data, 6 words not including
           per-service header
     (e) - Parameter ID, parameter 127 (Token Bucket TSpec)
     (f) - Parameter 127 flags (none set)
     (g) - Parameter 127 length, 5 words not including per-service
           header


   In this object, the TSpec parameters [r], [b], and [p] are set to
   reflect the traffic parameters of the receiver's desired reservation
   (the Reservation TSpec). The meaning of these fields is discussed
   fully in [RFC 2211]. Note that it is unlikely to make sense for the
   [p] term to be smaller than the [r] term.

   The maximum packet size parameter [M] should be set to the value of
   the smallest path MTU, which the receiver learns from information in
   arriving RSVP ADSPEC objects.  Alternatively, if the receiving
   application has built-in knowledge of the maximum packet size in use
   within the RSVP session, and this value is smaller than the smallest
   path MTU, [M] may be set to this value.  Note that requesting a value
   of [M] larger than the service modules along the data path can
   support will cause the reservation to fail. See section 2.3.2 for
   further discussion of the MTU value.

Wroclawski                  Standards Track                    [Page 13]

RFC 2210                   RSVP with INTSERV              September 1997


   The value of [m] can be chosen in several ways. Recall that when a
   resource reservation is installed at each intermediate node, the
   value used for [m] is the smaller of the receiver's request and the
   values in each sender's SENDER_TSPEC.

   If the application has a fixed, known minimum packet size, than that
   value should be used for [m]. This is the most desirable case.

   For a shared reservation style, the receiver may choose between two
   options, or pick some intermediate point between them.

      - if the receiver chooses a large value for [m], then the
      reservation will allocate less overhead for link-level headers.
      However, if a new sender with a smaller SENDER_TSPEC [m] joins the
      session later, an already-installed reservation may fail at that
      time.

      - if the receiver chooses a value of [m] equal to the smallest
      value which might be used by any sender, then the reservation will
      be forced to allocate more overhead for link-level headers.
      However it will not fail later if a new sender with a smaller
      SENDER_TSPEC [m] joins the session.

   For a FF reservation style, if no application-specific value is known
   the receiver should simply use the value of [m] arriving in each
   sender's SENDER_TSPEC for its reservation request to that sender.



 * 
 */

/**
 * @author Fernando Munoz del Nuevo, fernandomunoz1985@gmail.com
 */

public class FlowSpec extends RSVPObject{

	private int formatVersionNumber;
	
	private int overallLength;
	
	private int serviceHeader;
	
	private int lengthOfControlledLoadData;
	
	private int parameterID;
	
	private int parameter127Flags;
	
	private int parameter127length;
	
	private float tokenBucketRate;
	
	private float tokenBucketSize;
	
	private float peakDataRate;
	
	private long minimumPoliciedUnit;
	
	private long maximumPacketSize;
	
	
//	public FlowSpec(){
//		
//		classNum = 9;
//		cType = 2;
//		length = 4;
//		bytes = new byte[length];
//		
//	}
	public FlowSpec(byte [] bytes, int offset) {
		super(bytes,offset);
		decode();
		
	}
	
	public FlowSpec () {
		super();
		this.setClassNum(RSVPObjectParameters.RSVP_OBJECT_CLASS_FLOW_SPEC);
		this.setcType(2);
	}
	
	/*
	
	      0             1              2             3
    +-------------+-------------+-------------+-------------+
    |       Length (bytes)      |  Class-Num  |   C-Type    |
    +-------------+-------------+-------------+-------------+
    |                                                       |
    //                  (Object contents)                   //
    |                                                       |
    +-------------+-------------+-------------+-------------+	
    
    */
	
	//FIXME: Buscar en la RFC el resto del objeto
	
	public FlowSpec(int formatVersionNumber, int overallLength, int serviceHeader,
			int lengthOfControlledLoadData, int parameterID,
			int parameter127Flags, int parameter127length,
			float tokenBucketRate, float tokenBucketSize, float peakDataRate,
			long minimumPoliciedUnit, long maximumPacketSize) {
		super();
		
		classNum = 9;
		cType = 2;
		this.formatVersionNumber = formatVersionNumber;
		this.overallLength = overallLength;
		this.serviceHeader = serviceHeader;
		this.lengthOfControlledLoadData = lengthOfControlledLoadData;
		this.parameterID = parameterID;
		this.parameter127Flags = parameter127Flags;
		this.parameter127length = parameter127length;
		this.tokenBucketRate = tokenBucketRate;
		this.tokenBucketSize = tokenBucketSize;
		this.peakDataRate = peakDataRate;
		this.minimumPoliciedUnit = minimumPoliciedUnit;
		this.maximumPacketSize = maximumPacketSize;

	}



	@Override
	public void encode() {
		this.setLength(36);
		bytes = new byte[this.getLength()];
		encodeHeader();
		
		int offset = 4;
		
		this.bytes[offset] = (byte)(this.formatVersionNumber >> 4 & 0xF0);
		this.bytes[offset+1] = (byte)(0 & 0xff);
		this.bytes[offset+2] = (byte)(this.overallLength >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.overallLength & 0xFF);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.serviceHeader & 0xFF);
		this.bytes[offset+1] = (byte)(0 & 0xff);
		this.bytes[offset+2] = (byte)(this.lengthOfControlledLoadData >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.lengthOfControlledLoadData & 0xFF);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.parameterID & 0xFF);
		this.bytes[offset+1] = (byte)(this.parameter127Flags & 0xff);
		this.bytes[offset+2] = (byte)(this.parameter127length >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.parameter127length & 0xFF);
		
		offset = offset + 4;
		
		int tbr = Float.floatToIntBits(this.tokenBucketRate);
		
		this.bytes[offset] = (byte)(tbr >>> 24);
		this.bytes[offset+1] = (byte)(tbr >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(tbr >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(tbr & 0xff);
		
		offset = offset + 4;
		
		int tbs = Float.floatToIntBits(this.tokenBucketSize);
		
		this.bytes[offset] = (byte)(tbs >>> 24);
		this.bytes[offset+1] = (byte)(tbs >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(tbs >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(tbs & 0xff);

		offset = offset + 4;
		
		int pdr = Float.floatToIntBits(this.peakDataRate);
		
		this.bytes[offset] = (byte)(pdr >>> 24);
		this.bytes[offset+1] = (byte)(pdr >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(pdr >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(pdr & 0xff);
		
		offset = offset + 4;
		
		this.bytes[offset] = (byte)(this.minimumPoliciedUnit >>> 24);
		this.bytes[offset+1] = (byte)(this.minimumPoliciedUnit >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(this.minimumPoliciedUnit >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(this.minimumPoliciedUnit & 0xff);
		
		offset = offset + 4;
				
		this.bytes[offset] = (byte)(this.maximumPacketSize >>> 24);
		this.bytes[offset+1] = (byte)(this.maximumPacketSize >> 16 & 0xff);
		this.bytes[offset+2] = (byte)(this.maximumPacketSize >> 8 & 0xff);
		this.bytes[offset+3] = (byte)(this.maximumPacketSize & 0xff);
		
	}

	
	public void decode() {
		//FALTA POR IMPLEMENTAR		
	}

	public int getFormatVersionNumber() {
		return formatVersionNumber;
	}

	public void setFormatVersionNumber(int formatVersionNumber) {
		this.formatVersionNumber = formatVersionNumber;
	}

	public int getOverallLength() {
		return overallLength;
	}

	public void setOverallLength(int overallLength) {
		this.overallLength = overallLength;
	}

	public int getServiceHeader() {
		return serviceHeader;
	}

	public void setServiceHeader(int serviceHeader) {
		this.serviceHeader = serviceHeader;
	}

	public int getLengthOfControlledLoadData() {
		return lengthOfControlledLoadData;
	}

	public void setLengthOfControlledLoadData(int lengthOfControlledLoadData) {
		this.lengthOfControlledLoadData = lengthOfControlledLoadData;
	}

	public int getParameterID() {
		return parameterID;
	}

	public void setParameterID(int parameterID) {
		this.parameterID = parameterID;
	}

	public int getParameter127Flags() {
		return parameter127Flags;
	}

	public void setParameter127Flags(int parameter127Flags) {
		this.parameter127Flags = parameter127Flags;
	}

	public int getParameter127length() {
		return parameter127length;
	}

	public void setParameter127length(int parameter127length) {
		this.parameter127length = parameter127length;
	}

	public float getTokenBucketRate() {
		return tokenBucketRate;
	}

	public void setTokenBucketRate(float tokenBucketRate) {
		this.tokenBucketRate = tokenBucketRate;
	}

	public float getTokenBucketSize() {
		return tokenBucketSize;
	}

	public void setTokenBucketSize(float tokenBucketSize) {
		this.tokenBucketSize = tokenBucketSize;
	}

	public float getPeakDataRate() {
		return peakDataRate;
	}

	public void setPeakDataRate(float peakDataRate) {
		this.peakDataRate = peakDataRate;
	}

	public long getMinimumPoliciedUnit() {
		return minimumPoliciedUnit;
	}

	public void setMinimumPoliciedUnit(long minimumPoliciedUnit) {
		this.minimumPoliciedUnit = minimumPoliciedUnit;
	}

	public long getMaximumPacketSize() {
		return maximumPacketSize;
	}

	public void setMaximumPacketSize(long maximumPacketSize) {
		this.maximumPacketSize = maximumPacketSize;
	}
}

