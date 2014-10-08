package es.tid.rsvp.objects;

/*
 * 

   A.12 ADSPEC Class

      ADSPEC class = 13.

      o    Intserv ADSPEC object: Class = 13, C-Type = 2

           The contents and format for this object are specified in
           documents prepared by the int-serv working group.

31           24 23            16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| 0 (a) |      reserved         |  Msg length - 1 (b)           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    Default General Parameters fragment (Service 1)  (c)       |
|    (Always Present)                                           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    Guaranteed Service Fragment (Service 2)    (d)             |
|    (Present if application might use Guaranteed Service)      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    Controlled-Load Service Fragment (Service 5)  (e)          |
|    (Present if application might use Controlled-Load Service) |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(a)
    Message format version number (0). 
(b)
    Overall message length not including the header word. 
(c, d, e)
    Data fragments.

Default General Characterisation Parameters Data Fragment for ADSPEC Objects

All RSVP ADSPEC objects must contain the general characterisation parameters defined in [SW96b].

31            24 23           16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    1  (c)     |x| reserved    |           8 (d)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    4 (e)      |    (f)        |           1 (g)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|       IS hop count (32-bit unsigned integer)                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    6 (h)      |    (i)        |           1 (j)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|  Path b/w estimate  (32-bit IEEE floating point number)       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     8 (k)     |    (l)        |           1 (m)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|        Minimum path latency (32-bit integer)                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     10 (n)    |      (o)      |           1 (p)               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|      Composed MTU (32-bit unsigned integer)                   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(c)
    Per-Service header, service number 1 (Default General Parameters). 
(d)
    Global Break bit ([SW96b], Parameter 2) (marked x) and length of General Parameters data block. 
(e)
    Parameter ID, parameter 4 (Number-of-IS-hops parameter). 
(f)
    Parameter 4 flag byte. 
(g)
    Parameter 4 length, 1 word not including the header. 
(h)
    Parameter ID, parameter 6 (Path-BW parameter). 
(i)
    Parameter 6 flag byte. 
(j)
    Parameter 6 length, 1 word not including the header. 
(k)
    Parameter ID, parameter 8 (minimum path latency). 
(l)
    Parameter 8 flag byte. 
(m)
    Parameter 8 length, 1 word not including the header. 
(n)
    Parameter ID, parameter 10 (composed path MTU). 
(o)
    Parameter 10 flag byte. 
(p)
    Parameter 10 length, 1 word not including the header.

Controlled-Load Service ADSPEC Data Fragment

The Controlled-Load service does not require any extra ADSPEC data; the only service-specific ADSPEC data is the Controlled-Load break bit.

 31           24 23           16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     5 (a)     |x|  (b)        |            N-1 (c)            |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
. Service-specific general parameter headers/values, if present .
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(a)
    Per-Service header, service number 5 (Controlled-Load). 
(b)
    Break bit. 
(c)
    Length of per-service data in 32 bit words not including the header word.

Guaranteed Service ADSPEC Data Fragment

31            24 23           16 15            8 7             0
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     2 (a)     |x|  reserved   |             N-1 (b)           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|    133 (c)    |     0 (d)     |             1 (e)             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|   End-to-end composed value for C [Ctot] (32-bit integer)     |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     134 (f)   |       (g)     |             1 (h)             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|   End-to-end composed value for D [Dtot] (32-bit integer)     |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     135 (i)   |       (j)     |             1 (k)             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Since-last-reshaping point composed C [Csum] (32-bit integer) |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     136 (l)   |       (m)     |             1 (n)             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Since-last-reshaping point composed D [Dsum] (32-bit integer) |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
. Service-specific general parameter headers/values, if present .
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

(a)
    Per-Service header, service number 2 (Guaranteed). 
(b)
    Break bit and Length of per-service data in 32-bit words not including the header word. 
(c)
    Parameter ID, parameter 133 (Composed Ctot). 
(d)
    Parameter 133 flag byte. 
(e)
    Parameter 133 length, 1 word not including the header. 
(f)
    Parameter ID, parameter 134 (Composed Dtot). 
(g)
    Parameter 134 flag byte. 
(h)
    Parameter 134 length, 1 word not including the header. 
(i)
    Parameter ID, parameter 135 (Composed Csum). 
(j)
    Parameter 135 flag byte. 
(k)
    Parameter 135 length, 1 word not including the header. 
(l)
    Parameter ID, parameter 136 (Composed Dsum). 
(m)
    Parameter 136 flag byte. 
(n)
    Parameter 136 length, 1 word not including the header.




 * 
 */

public class IntservADSPEC extends RSVPObject{

	private int formatVersionNumber;
	
	private int overallLength;
	
	private DefaultGeneralParameters dgp;
	
	private ControlledLoadService cls;
	
	public IntservADSPEC(){
		
		classNum = 13;
		cType = 2;
		
	}
	
	// FIXME: Buscar Intserv SENDER_TSPEC object en int-serv working group
	
	public IntservADSPEC(DefaultGeneralParameters dgp, ControlledLoadService cls){
		
		classNum = 13;
		cType = 2;
		
		this.formatVersionNumber = 1;
		this.overallLength = 10;
		
		this.dgp = dgp;
		this.cls = cls;
		
		length = 8 + this.dgp.getLength() + this.cls.getLength();
		bytes = new byte[length];
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
	
	@Override
	public void encode() {
		// TODO Auto-generated method stub
		encodeHeader();
		
		int offset = 4;
		
		this.bytes[offset] = (byte)(this.formatVersionNumber >> 4 & 0xFF);
		this.bytes[offset+1] = (byte)(0 & 0xff);
		this.bytes[offset+2] = (byte)(this.overallLength >> 8 & 0xFF);
		this.bytes[offset+3] = (byte)(this.overallLength & 0xFF);
		
		offset = offset + 4;
		
		this.dgp.encode();
		System.arraycopy(this.dgp.getBytes(),0,this.getBytes(),offset,this.dgp.getLength());
		
		offset = offset + this.dgp.getLength();

		this.cls.encode();
		System.arraycopy(this.cls.getBytes(),0,this.getBytes(),offset,this.cls.getLength());
				
	}

	@Override
	public void decode(byte[] bytes, int offset) {
		// FIXME: Codificarlo cuando se indague en la RFC de intserv
	}
}
