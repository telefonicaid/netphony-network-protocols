package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Request;
import es.tid.pce.pcep.constructs.SVECConstruct;
import es.tid.pce.pcep.objects.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
 *{@code
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
 *}
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
	private static final Logger log= LoggerFactory.getLogger("PCEPParser");

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
		decode();

	}

	/**
	 * Encodes the PCEP Request message
	 */
	public void encode() throws PCEPProtocolViolationException {
		//Encoding PCEP Request Message
		if (RequestList.size()==0){
			log.warn("There should be at least one request in a PCEP Request message");
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
	 * @throws PCEPProtocolViolationException Exception when the message is malformed 
	 */
	public void decode() throws PCEPProtocolViolationException{
		//Current implementation is strict, does not accept unknown objects 
		//Decoding PCEP Request Message
		byte[] bytes=this.messageBytes;
		int offset=4;//We start after the object header
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_MONITORING){
			try {
				monitoring=new Monitoring(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed Monitoring Object found");
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
				log.warn("Malformed PccReqId Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+pccReqId.getLength();
			//len=len+pccReqId.getLength();
		}
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SVEC){
			SVECConstruct c_svec;
			try {
				c_svec = new SVECConstruct(bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				log.warn("Malformed SVEC Construct");
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((RequestList == null) ? 0 : RequestList.hashCode());
		result = prime * result
				+ ((SvecList == null) ? 0 : SvecList.hashCode());
		result = prime * result
				+ ((monitoring == null) ? 0 : monitoring.hashCode());
		result = prime * result
				+ ((pccReqId == null) ? 0 : pccReqId.hashCode());
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
		PCEPRequest other = (PCEPRequest) obj;
		if (RequestList == null) {
			if (other.RequestList != null)
				return false;
		} else if (!RequestList.equals(other.RequestList))
			return false;
		if (SvecList == null) {
			if (other.SvecList != null)
				return false;
		} else if (!SvecList.equals(other.SvecList))
			return false;
		if (monitoring == null) {
			if (other.monitoring != null)
				return false;
		} else if (!monitoring.equals(other.monitoring))
			return false;
		if (pccReqId == null) {
			if (other.pccReqId != null)
				return false;
		} else if (!pccReqId.equals(other.pccReqId))
			return false;
		return true;
	}
	
	

}