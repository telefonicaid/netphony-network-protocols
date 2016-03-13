package es.tid.ospf.ospfv2.lsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Base class for OSPF v2 LSA (Link State Advertisement) messages.
 * <p>
 * See <a href="http://www.ietf.org/rfc/rfc2328"> RFC 2328</a>.
 
    All LSAs begin with a common 20 byte header.  This header contains
    enough information to uniquely identify the LSA (LS type, Link State
    ID, and Advertising Router).  Multiple instances of the LSA may
    exist in the routing domain at the same time.  It is then necessary
    to determine which instance is more recent.  This is accomplished by
    examining the LS age, LS sequence number and LS checksum fields that
    are also contained in the LSA header.
</p>
<pre>
        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |            LS age             |    Options    |    LS type    |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                        Link State ID                          |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                     Advertising Router                        |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |                     LS sequence number                        |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
       |         LS checksum           |             length            |
       +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
</pre>
 * @author ogondio
 *
 */
public abstract class LSA {
	
	/**
	 * LS age: 
	 */
	private int LSage;
	
	/**
	 * Options
	 */
	private int options;
	
	/**
	 * LS type
	 */
	private int LStype;
	
	/**
	 * Link State ID 
	 */
	private Inet4Address LinkStateId;
	
	/**
	 * Advertising Router
	 */
	private Inet4Address AdvertisingRouter;
	
	/**
	 * LS sequence number 
	 */
	private int  LSsequenceNumber;
	
	/**
	 * LS checksum
	 */
	private int checksum;
	
	/**
	 * length
	 */
	private int length;
	
	/**
	 * Bytes of the LSA
	 */
	protected byte[] LSAbytes;
	
	/**
	 * The logger
	 */
	protected static final Logger log = LoggerFactory.getLogger("OSPFParser");
	
	/**
	 * Default constructor
	 */
	public LSA(){	
	}
	
	/**
	 * Decodes the header of a LSA
	 * @param bytes bytes
	 * @param offset offset
	 * @throws MalformedOSPFLSAException Malformed OSPF LSA Exception
	 */
	public LSA(byte[] bytes, int offset) throws MalformedOSPFLSAException{
		this.LSage= ((bytes[offset]&0xFF)<<8) |  (bytes[offset+1] & 0xFF);
		this.options=bytes[offset+2]&0xFF;
		this.LStype=bytes[offset+3]&0xFF;	
		byte[] ip=new byte[4]; 
		System.arraycopy(bytes,offset+4, ip, 0, 4);		
		try {
			this.LinkStateId=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warn("ERROR IN Link State ID: "+e.toString());
		}
		System.arraycopy(bytes,offset+8, ip, 0, 4);
		try {
			this.AdvertisingRouter=(Inet4Address)Inet4Address.getByAddress(ip);
		} catch (UnknownHostException e) {
			log.warn("ERROR in Advertising Router: "+e.toString());
		}
		//LSequenceNumber is a 32bit SIGNED int
		this.LSsequenceNumber=(((bytes[offset+12]&0xFF)<<24) | (((bytes[offset+13]&0xFF)<<16)) |(((bytes[offset+14]&0xFF)<<8)) |  (bytes[offset+15]&0xFF) );
		//length is 2 bytes
		this.length=((bytes[offset+18]&0xFF)<<8) |  (bytes[offset+19]&0xFF);
		this.LSAbytes=new byte[this.length];
		//Copy the LSA bytes
		try {
			System.arraycopy(bytes, offset, this.LSAbytes, 0, this.length);	
		}catch (Exception e){
			log.warn("ERROR tam de bytes es "+bytes.length);
			throw new MalformedOSPFLSAException();
		}
	}
	
	/**
	 * 
	 */
	public void encodeLSAHeader(){
		this.LSAbytes[0]=(byte)(this.LSage>>>8);
		this.LSAbytes[1]=(byte)this.LSage;
		this.LSAbytes[2]=(byte)this.options;
		this.LSAbytes[3]=(byte)this.LStype;
		if (this.LinkStateId!=null){
			System.arraycopy(this.LinkStateId.getAddress(),0, this.LSAbytes, 4, 4);	
		}
		if (this.AdvertisingRouter!=null){
			System.arraycopy(this.AdvertisingRouter.getAddress(),0,this.LSAbytes, 8, 4);	
		}else {
			this.LSAbytes[8]=0;
			this.LSAbytes[9]=0;
			this.LSAbytes[10]=0;
			this.LSAbytes[11]=0;
		}
		
		this.LSAbytes[12]=(byte)(this.LSsequenceNumber>>>24);
		this.LSAbytes[13]=(byte)(this.LSsequenceNumber>>>16);
		this.LSAbytes[14]=(byte)(this.LSsequenceNumber>>>8);
		this.LSAbytes[15]=(byte)this.LSsequenceNumber;		
		//FIXME: POR AHORA NO SE CALCULA EL CHECKSUM;
		this.LSAbytes[16]=0;
		this.LSAbytes[17]=0;
		this.LSAbytes[18]=(byte)(this.length>>>8);
		this.LSAbytes[19]=(byte)this.length;		
	}

	public int getLSage() {
		return LSage;
	}

	public void setLSage(int lSage) {
		LSage = lSage;
	}

	public int getOptions() {
		return options;
	}

	public void setOptions(int options) {
		this.options = options;
	}

	public int getLStype() {
		return LStype;
	}

	public void setLStype(int lStype) {
		LStype = lStype;
	}


	public Inet4Address getLinkStateId() {
		return LinkStateId;
	}

	public void setLinkStateId(Inet4Address linkStateId) {
		LinkStateId = linkStateId;
	}

	public Inet4Address getAdvertisingRouter() {
		return AdvertisingRouter;
	}

	public void setAdvertisingRouter(Inet4Address advertisingRouter) {
		AdvertisingRouter = advertisingRouter;
	}
	

	public int getLSsequenceNumber() {
		return LSsequenceNumber;
	}

	public void setLSsequenceNumber(int lSsequenceNumber) {
		LSsequenceNumber = lSsequenceNumber;
	}

	public int getLength() {
		return length;
	}

	protected void setLength(int length) {
		this.length = length;
	}
	
	public static int getLStype(byte[] bytes, int offset){
		return bytes[offset+3]&0xFF;
	}
	public static int getLSlength(byte[] bytes, int offset){
		return ((bytes[offset+18]&0xFF)<<8) |  (bytes[offset+19]&0xFF);
	}

	public byte[] getLSAbytes() {
		return LSAbytes;
	}
	
	
	public int getChecksum() {
		return checksum;
	}

	public abstract void encode();
	
	/**
	 * Compare LSA by LStype 
	 */
	public boolean equals(Object LSAToCompare){
		 if (LSAToCompare == null) {
		        return false;
		    }
		 	try {
			    final LSA other = (LSA) LSAToCompare;
				if (other.getLStype()!=this.getLStype()){
					return false;
				}
				if (!(other.getLinkStateId().equals(this.getLinkStateId()))){
					return false;
				}
				if (!(other.getAdvertisingRouter().equals(this.getAdvertisingRouter()))){
					return false;
				}
		 		
		 	}catch (Exception e){
		 		return false;
		 	}
		return true;
		
	}
	
	public String printHeader(){
		return "LSType: "+this.getLStype()+" LinkStateId(): "+this.getLinkStateId()+" AdvertisingRouter: "+this.getAdvertisingRouter();
	}
	
	

	
	
	
}
