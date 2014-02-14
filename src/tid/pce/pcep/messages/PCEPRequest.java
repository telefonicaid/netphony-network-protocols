package tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.Request;
import tid.pce.pcep.constructs.Response;
import tid.pce.pcep.constructs.SVECConstruct;
import tid.pce.pcep.objects.*;


/**
 * PCEP Request Message (RFC 5440, RFC 5541, RFC 5886, and RFC 5521).
 * 
 * <p>From RFC 5440 Section 6.4:
 * 6.4. Path Computation Request (PCReq) Message
 *
 *
 *  A Path Computation Request message (also referred to as a PCReq
 *  message) is a PCEP message sent by a PCC to a PCE to request a path
 *  computation.  A PCReq message may carry more than one path
 *  computation request.  The Message-Type field of the PCEP common
 *  header for the PCReq message is set to 3.
 *  </p>
 *  <p>There are two mandatory objects that MUST be included within a PCReq
 *  message: the RP and the END-POINTS objects (see Section 7).  If one
 *  or both of these objects is missing, the receiving PCE MUST send an
 *  error message to the requesting PCC.  Other objects are optional.
 *  </p>
 *  The format of a PCReq message is as follows:
 *
 *  <PCReq Message>::= <Common Header>
 *  					[<MONITORING>]
 *                      [<PCC-ID-REQ>]
 *                      [<svec-list>]
 *                      <request-list>
 *
 *  where:
 *
 *     <svec-list>::= <SVEC>
                         [<OF>]
                         [<metric-list>]
                         [<svec-list>]

 *     <request-list>::=<request>[<request-list>]
 *
 *      <request>::= <RP>
 *                  <END-POINTS>
 *                  [<LSPA>]
 *                  [<BANDWIDTH>]
 *                  [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
 *                  [<metric-list>]
 *                  [<RRO>[<BANDWIDTH>]]
 *                  [<IRO>]
 *                  [<LOAD-BALANCING>]
 *                  [<XRO>]
 *
 *  where:
 *
 *  <metric-list>::=<METRIC>[<metric-list>]
 *
 *  The SVEC, RP, END-POINTS, LSPA, BANDWIDTH, METRIC, RRO, IRO, and
 *  LOAD-BALANCING objects are defined in Section 7.  The special case of
 *  two BANDWIDTH objects is discussed in detail in Section 7.7.
 *
 *  A PCEP implementation is free to process received requests in any
 *  order.  For example, the requests may be processed in the order they
 *  are received, reordered and assigned priority according to local
 *  policy, reordered according to the priority encoded in the RP object
 *  (Section 7.4.1), or processed in parallel.
 *
 * @author Oscar Gonzalez de Dios
 * @version 0.1
 * 
**/
public class PCEPRequest extends PCEPMessage {
	
	/**
	 * List of optional SVEC Objects
	 */
	private LinkedList<SVECConstruct> SvecList; //SVEC List is OPTIONAL. Si est� a null, es que no hay
	/**
	 * List of requests. At least one request is compulsory!!
	 */
	private LinkedList<Request> RequestList;
	
	/**
	 * Monitoring object
	 */
	private Monitoring monitoring;
	
	/**
	 * PCC_REQ_ID
	 */
	
	private PccReqId pccReqId;
	
	/**
	 * Logger
	 */
	private Logger log=Logger.getLogger("PCEPParser");
	
	/**
	 * Construct new PCEP Request from scratch
	 */
	public PCEPRequest(){		
		SvecList=new LinkedList<SVECConstruct>();
		RequestList=new LinkedList<Request>();
		this.setMessageType(PCEPMessageTypes.MESSAGE_PCREQ);
	}
	
	public PCEPRequest(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		SvecList=new LinkedList<SVECConstruct>();
		RequestList=new LinkedList<Request>();
		decode(bytes);
		
	}
	
	/**
	 * Encodes the PCEP Request message
	 */
	public void encode() throws PCEPProtocolViolationException {
		log.finest("Encoding PCEP Request Message");
		if (RequestList.size()==0){
			log.warning("There should be at least one request in a PCEP Request message");
			throw new PCEPProtocolViolationException();
		}
		int len=4;
		if (monitoring!=null){
			monitoring.encode();
			len=len+monitoring.getLength();
		}
		if (pccReqId!=null){
			pccReqId.encode();
			len=len+pccReqId.getLength();
		}
		for (int i=0;i<SvecList.size();++i){
			SvecList.get(i).encode();
			len=len+SvecList.get(i).getLength();
		}
		for (int i=0;i<RequestList.size();++i){
			RequestList.get(i).encode();
			len=len+RequestList.get(i).getLength();
		}
		this.setMessageLength(len);		
		messageBytes=new byte[len];
		encodeHeader();
		int offset=4;
		if (monitoring!=null){
			System.arraycopy(monitoring.getBytes(),0,messageBytes,offset,monitoring.getLength());
			offset=offset+monitoring.getLength();
		}
		if (pccReqId!=null){
			System.arraycopy(pccReqId.getBytes(),0,messageBytes,offset,pccReqId.getLength());
			offset=offset+pccReqId.getLength();
		}
		for (int i=0;i<SvecList.size();++i){
			System.arraycopy(SvecList.get(i).getBytes(), 0, messageBytes, offset, SvecList.get(i).getLength());
			offset=offset+SvecList.get(i).getLength();
		}
		for (int i=0;i<RequestList.size();++i){
			System.arraycopy(RequestList.get(i).getBytes(), 0, messageBytes, offset, RequestList.get(i).getLength());
			offset=offset+RequestList.get(i).getLength();		
		}
		
	}
	
	/**
	 * Decodes a PCEP Request following RFC 5440, RFC 5541, RFC 5886 and RFC 5521 
	 */
	public void decode(byte[] bytes) throws PCEPProtocolViolationException{
		//Current implementation is strict, does not accept unknown objects 
		log.finest("Decoding PCEP Request Message");
		log.finest("Length in bytes: "+bytes.length);
		this.messageBytes=new byte[bytes.length];
		System.arraycopy(bytes, 0, this.messageBytes, 0, bytes.length);
		int offset=4;//We start after the object header
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_MONITORING){
			try {
				monitoring=new Monitoring(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed Monitoring Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+monitoring.getLength();
			//len=len+monitoring.getLength();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCC_REQ_ID){
			try {
				pccReqId=new PccReqId(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed PccReqId Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+pccReqId.getLength();
			//len=len+pccReqId.getLength();
		}
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SVEC){
			log.info("Svec construct");
			SVECConstruct c_svec;
			try {
				c_svec = new SVECConstruct(bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				log.warning("Malformed SVEC Construct");
				throw new PCEPProtocolViolationException();
			}
			SvecList.add(c_svec);
			offset=offset+c_svec.getLength();
		}
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			Request req=new Request(bytes, offset);
			RequestList.add(req);
			offset=offset+req.getLength();
			if (offset>=bytes.length){
				return;
			}
		}
		if (RequestList.size()==0){
			throw new PCEPProtocolViolationException();
		}
	}
	
	
	public void addRequest(Request request){		
		this.RequestList.add(request);		
	}
	
	public Request getRequest(int index){
		return this.RequestList.get(index);	
	}

	public LinkedList<Request> getRequestList() {
		return RequestList;
	}

	public void setRequestList(LinkedList<Request> requestList) {
		RequestList = requestList;
	}

	public LinkedList<SVECConstruct> getSvecList() {
		return SvecList;
	}

	public void setSvecList(LinkedList<SVECConstruct> svecList) {
		SvecList = svecList;
	}
	
	public void addSvec(SVECConstruct svec){		
		this.SvecList.add(svec);
	}

	
	public Monitoring getMonitoring() {
		return monitoring;
	}

	public PccReqId getPccReqId() {
		return pccReqId;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public void setPccReqId(PccReqId pccReqId) {
		this.pccReqId = pccReqId;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer(RequestList.size()*100);
		sb.append("REQ MESSAGE: ");
		if (monitoring!=null){
			sb.append("<MON>");
		}
		for (int i=0;i<RequestList.size();++i){
			sb.append(RequestList.get(i).toString());
		}
		for (int i=0;i<SvecList.size();++i){
			sb.append(SvecList.get(i).toString());
		}
		return sb.toString();
	}

}