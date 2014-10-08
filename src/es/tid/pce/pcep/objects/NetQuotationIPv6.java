package es.tid.pce.pcep.objects;


import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.LinkedList;

import es.tid.pce.pcep.objects.EndPoints;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;


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
    //                     Quotation 1    	                    //
    |                     											 |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                          								 	 |
    //                          ...                                //
    |                     											 |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    |                          									 |
    //                     Quotation N    	                    //
    |                     										 |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+


where a single Quotation block has the following format:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   |                Source IPv6 address (16 bytes)                 |
   |                                                               |
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   |              Destination IPv6 address (16 bytes)              |
   |                                                               |
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     	 Cost								   |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

					Quotation block Format for IPv6



* </pre>
* @author Alejandro Tovar de Dueñas
*
*/
public class NetQuotationIPv6 extends EndPoints {
	
	private LinkedList<EPQuotationIPv6> EPQuotationList;
	
	
	public NetQuotationIPv6(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_NET_QUOTATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_IP6);
		EPQuotationList=new LinkedList<EPQuotationIPv6> ();
	}
	
	public NetQuotationIPv6(byte []bytes, int offset)throws MalformedPCEPObjectException {
		super(bytes,offset);
		EPQuotationList=new LinkedList<EPQuotationIPv6> ();
		decode();
	}
	
	@Override
	public void encode() {
		int len=0;
		for (int i=0;i<EPQuotationList.size();++i){
			(EPQuotationList.get(i)).encode();
			len=len+(EPQuotationList.get(i)).getEPQuotationlength();
		}
		//log.info("Path Length = "+len);
		this.ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		int pos=4;	
		for (int i=0;i<EPQuotationList.size();++i){
			System.arraycopy(EPQuotationList.get(i).getEPQuotation_bytes(), 0, this.object_bytes, pos, EPQuotationList.get(i).getEPQuotationlength());
			pos=pos+EPQuotationList.get(i).getEPQuotationlength();
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
			EPQuotationIPv6 epquotation=new EPQuotationIPv6();
			byte[] ip6add=new byte[16]; 
			System.arraycopy(this.getObject_bytes(),offset, ip6add, 0, 16);
			try {
				epquotation.sourceIP6=(Inet6Address)Inet6Address.getByAddress(ip6add);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			System.arraycopy(this.getObject_bytes(),offset+16, ip6add, 0, 16);
			try {
				epquotation.destIP6=(Inet6Address)Inet6Address.getByAddress(ip6add);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.arraycopy(this.getObject_bytes(),offset+4+4, epquotation.cost, 0, 4);
			EPQuotationList.add(epquotation);
			offset=offset+16+16;
			if (offset>=ObjectLength){
				//No more End Points
				fin=true;
			}
		}
	}
			
	
	public static class EPQuotationIPv6{
		protected int EPQuotationlength;
		//protected byte [] EndPointPair_Addresses;
		protected byte [] EPQuotation_bytes;
		
		public Inet6Address sourceIP6;//Source IPv6 address
		public Inet6Address destIP6;//Destination IPv6 address
		public byte [] cost;//Pair Cost
		
        public EPQuotationIPv6(){
        	super ();
        }
        
        public EPQuotationIPv6(byte[] bytes, int offset) throws MalformedPCEPObjectException {
    		decode();
    	}
        
        public void encode() {
    		this.EPQuotationlength=32+4;
    		this.EPQuotation_bytes=new byte[EPQuotationlength];
    		System.arraycopy(sourceIP6.getAddress(), 0, this.EPQuotation_bytes, 0, 16);
    		System.arraycopy(destIP6.getAddress(), 0, this.EPQuotation_bytes, 16, 16);
    		System.arraycopy(cost, 0, this.EPQuotation_bytes, 16, 4);
		}
        
        public void decode() throws MalformedPCEPObjectException {
    		if (EPQuotationlength!=32){
    			throw new MalformedPCEPObjectException();
    		}
    		byte[] ip=new byte[16]; 
    		System.arraycopy(this.EPQuotation_bytes,0, ip, 0, 16);
    		try {
    			sourceIP6=(Inet6Address)Inet6Address.getByAddress(ip);
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		System.arraycopy(this.EPQuotation_bytes,16, ip, 0, 16);
    		try {
    			destIP6=(Inet6Address)Inet6Address.getByAddress(ip);
    		} catch (UnknownHostException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 	 
    		System.arraycopy(this.EPQuotation_bytes,32, cost, 0, 4);    		
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
