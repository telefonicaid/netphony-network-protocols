package tid.pce.pcep.objects;

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


where a single Quotation block has the following format:
       0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          									 |
     |                     Source NSAP Address                       |
     |                     											 |
     |                     											 |
     |                     											 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          									 |
     |                     											 |
     |                          									 |
     |                     Dest NSAP Address                         | 
     |                     											 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 * </pre>
 * @author Alejandro Tovar de Dueñas
 *
 */
public abstract class NetQuotationNSAP extends PCEPObject{
	
	private LinkedList<EPQuotationNSAP> EPQuotationList;
		
	public NetQuotationNSAP(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_NET_QUOTATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_NET_QUOTATION_ENDPOINTS_IP4);
		EPQuotationList=new LinkedList<EPQuotationNSAP> ();
	}
	
	public NetQuotationNSAP(byte[] bytes, int offset) throws MalformedPCEPObjectException{
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
			EPQuotationNSAP epquotation=new EPQuotationNSAP();
			System.arraycopy(this.getObject_bytes(),offset, epquotation.sourceNSAP, 0, 20);
			System.arraycopy(this.getObject_bytes(),offset+20, epquotation.destNSAP, 0, 20);
			System.arraycopy(this.getObject_bytes(),offset+20+20, epquotation.cost, 0, 4);
			EPQuotationList.add(epquotation);
			offset=offset+20+20;
			if (offset>=ObjectLength){
				System.out.println("No more End Points");
				fin=true;
			}
		}
	}
			
	
	public static class EPQuotationNSAP{
		protected int EPQuotationlength;
		//protected byte [] EndPointPair_Addresses;
		protected byte [] EPQuotation_bytes;
		
		public byte [] sourceNSAP;//Source NSAP address
		public byte [] destNSAP;//Destination IPv6 address
		public byte [] cost;//Pair Cost
		
        public EPQuotationNSAP(){
        	super ();
        }
        
        public EPQuotationNSAP(byte[] bytes, int offset) throws MalformedPCEPObjectException {
    		decode();
    	}
        
        public void encode() {
    		this.EPQuotationlength=44;
    		this.EPQuotation_bytes=new byte[EPQuotationlength];
    		System.arraycopy(sourceNSAP, 0, this.EPQuotation_bytes, 0, 20);
    		System.arraycopy(destNSAP, 0, this.EPQuotation_bytes, 20, 20);
    		System.arraycopy(cost, 0, this.EPQuotation_bytes, 40, 4);
		}
        
        public void decode() throws MalformedPCEPObjectException {
    		if (EPQuotationlength!=44){
    			throw new MalformedPCEPObjectException();
    		}
    		System.out.println("Decoding NSAP Addreess"); 
    		System.arraycopy(this.EPQuotation_bytes,0, sourceNSAP, 0, 20);
    		System.arraycopy(this.EPQuotation_bytes,20, destNSAP, 0, 20);    		
    		System.arraycopy(this.EPQuotation_bytes,40, cost, 0, 4);
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