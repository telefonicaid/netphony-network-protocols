package es.tid.pce.pcep.objects;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.LinkedList;


/**
  * <p>Represents a PCEP NET-QUOTATION object as defined in GEYSERS D4.1</p>
  * <p>From GEYSERS D4.1 Section 6.3.2.1 Assisted unicast replies</p>
<pre>

A PCRep for assisted unicast connections provides a network
quotation for each pair of end points or each time-slot (used
in case of advance reservation service) included in the PCReq.
This information is included in the NET-QUOTATION object 
(Object-Class and Object-Type to be defined). It should be noted 
that the path-list object is not included in assisted unicast 
replies, and no ERO is returned.
The format of a NET-QUOTATION-ENDPOINTS-IPV4 object is the following:

      0                   1                   2                   3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          									 |
     //                     Quotation 1                        //
     |                     											 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          								 	 |
     //                          ...                                //
     |                     											 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          									 |
     //                     Quotation N                        //
     |                     											 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+



That way, each End-point pair block is reduced to 
     0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                     Source IPv4 address                       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                  Destination IPv4 address                     |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                     	 Cost									|
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
					Quotation block Format for IPv4

 * </pre>
 * @author Alejandro Tovar de Dueñas
 *
 */
public abstract class NetQuotationIPv4 extends PCEPObject{
	
	private LinkedList<EPQuotationIPv4> EPQuotationList;
		
	public NetQuotationIPv4(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_NET_QUOTATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_IP4);
		EPQuotationList=new LinkedList<EPQuotationIPv4> ();
	}
	
	public NetQuotationIPv4(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}

	public void encode(){
		int len=4;//The four bytes of the header
		int pos=0;
		for (int k=0; k<EPQuotationList.size();k=k+1){
			EPQuotationList.get(k).encode();
			len=len+EPQuotationList.get(k).getEPQuotationlength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		pos=4;
		for (int k=0 ; k<EPQuotationList.size(); k=k+1) {					
			System.arraycopy(EPQuotationList.get(k).getEPQuotation_bytes(),0, this.object_bytes, pos, EPQuotationList.get(k).getEPQuotationlength());
			pos=pos+EPQuotationList.get(k).getEPQuotationlength();
		}
	}


	@Override
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=4;//Position of the next Address
		if (ObjectLength==4){
			fin=true;
		}
		while (!fin) {
			EPQuotationIPv4 epquotation=new EPQuotationIPv4();
			byte[] ip4add=new byte[4]; 
			System.arraycopy(this.getObject_bytes(),offset, ip4add, 0, 4);
			try {
				epquotation.sourceIP4=(Inet4Address)Inet4Address.getByAddress(ip4add);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			System.arraycopy(this.getObject_bytes(),offset+4, ip4add, 0, 4);
			try {
				epquotation.destIP4=(Inet4Address)Inet4Address.getByAddress(ip4add);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.arraycopy(this.getObject_bytes(),offset+4+4, epquotation.cost, 0, 4);
			EPQuotationList.add(epquotation);
			offset=offset+4+4+4;
			if (offset>=ObjectLength){
				//No more End Points
				fin=true;
			}
		}
	}
			
	
	public static class EPQuotationIPv4{
		protected int EPQuotationlength;
		//protected byte [] EndPointPair_Addresses;
		protected byte [] EPQuotation_bytes;
		
		public Inet4Address sourceIP4;//Source IPv4 address
		public Inet4Address destIP4;//Destination IPv4 address
		public byte [] cost;//Pair Cost
		
        public EPQuotationIPv4(){
        	super ();
        }
        
        public EPQuotationIPv4(byte[] bytes, int offset) throws MalformedPCEPObjectException {
    		decode();
    	}
        
        public void encode() {
    		this.EPQuotationlength=12;
    		this.EPQuotation_bytes=new byte[EPQuotationlength];
    		System.arraycopy(sourceIP4.getAddress(), 0, this.EPQuotation_bytes, 0, 4);
    		System.arraycopy(destIP4.getAddress(), 0, this.EPQuotation_bytes, 4, 4);
    		System.arraycopy(cost, 0, this.EPQuotation_bytes, 8, 4);
		}
        
        public void decode() throws MalformedPCEPObjectException {
    		if (EPQuotationlength!=12){
    			throw new MalformedPCEPObjectException();
    		}
    		byte[] ip=new byte[4]; 
    		System.arraycopy(this.EPQuotation_bytes,0, ip, 0, 4);
    		try {
    			sourceIP4=(Inet4Address)Inet4Address.getByAddress(ip);
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		System.arraycopy(this.EPQuotation_bytes,4, ip, 0, 4);
    		try {
    			destIP4=(Inet4Address)Inet4Address.getByAddress(ip);
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 	 
    		System.arraycopy(this.EPQuotation_bytes,8, cost, 0, 4);
    	}
    	
    	public int getEPQuotationlength() {
    		return EPQuotationlength;
    	}

    	public byte[] getEPQuotation_bytes() {
    		return EPQuotation_bytes;
    	}

    	public void setEPQuotation_bytes(byte[] EPQuotation_bytes) {
    		this.EPQuotation_bytes = EPQuotation_bytes;
    	}
    	
	}
}