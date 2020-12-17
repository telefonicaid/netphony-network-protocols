package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Request;
import es.tid.pce.pcep.constructs.SVECConstruct;
import es.tid.pce.pcep.objects.*;


/**
 * Path Computation Monitoring Request (PCMonReq) Message (RFC 5886).
 * <p>The Message-Type field of the PCEP common header for the PCMonReq
   message is set to 8.</p>
   <p>There is one mandatory object that MUST be included within a PCMonReq
   message: the MONITORING object.  If the MONITORING
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=4 (MONITORING
   object missing).  Other objects are optional.</p>

   <p>Format of a PCMonReq message (out-of-band request):</p>
{@code
    <PCMonReq Message>::= <Common Header>
                         <MONITORING>
                         <PCC-ID-REQ>
                         [<pce-list>]
                         [<svec-list>]
                         [<request-list>]
                          where:

   <pce-list>::=<PCE-ID>[<pce-list>]

   <svec-list>::=<SVEC>
                 [<OF>]
                 [<svec-list>]

   <request-list>::=<request>[<request-list>]

   <request>::= <RP>
                <END-POINTS>
                [<LSPA>]
                [<BANDWIDTH>]
                [<metric-list>]
                [<RRO>]
                [<IRO>]
                [<LOAD-BALANCING>]
                [<XRO>]

   <metric-list>::=<METRIC>[<metric-list>]}
   
   <p> The SVEC, RP, END-POINTS, LSPA, BANDWIDTH, METRIC, RRO, IRO, and
   LOAD-BALANCING objects are defined in [RFC5440].  The XRO object is
   defined in [RFC5521] and the OF object is defined in [RFC5541].  The
   PCC-ID-REQ object is defined in Section 4.2.</p>

   <p>The PCMonReq message is used to gather various PCE state metrics
   along a path computation chain.  The path computation chain may be
   determined by the PCC (in the form of a series of a series of PCE-ID
   objects defined in Section 4.3) according to policy specified on the
   PCC or alternatively may be determined by the path computation
   procedure.  For example, if the BRPC procedure ([RFC5441]) is used to
   compute an inter-domain TE LSP, the path computation chain may be
   determined dynamically.  In that case, the PCC sends a PCMonReq
   message that contains the PCEP objects that characterize the TE LSP
   attributes along with the MONITORING object that
   lists the set of metrics of interest.  If a list of PCEs is present
   in the monitoring request, it takes precedence over mechanisms used
   to dynamically determine the path computation chain.  If a PCE
   receives a monitoring request that specifies a next-hop PCE in the
   PCE list that is unreachable, the request MUST be silently discarded.</p>
 *
 * @author Marta Cuaresma Saturio
 *
 */

public class PCEPMonReq  extends PCEPMessage {
	private Monitoring monitoring;
	private PccReqId pccReqId;
	private LinkedList<PceId> pceList;
	private LinkedList<SVECConstruct> svecList;
	private LinkedList<Request> requestList;

	/**
	 * Construct new PCEP PCMonReq
	 */
	
	public PCEPMonReq () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_PCMONREQ);
		monitoring=new Monitoring();
		pccReqId=new PccReqId();
		pceList = new LinkedList<PceId>();
		svecList = new LinkedList<SVECConstruct>();
		requestList = new LinkedList<Request>();
		
	}
	
	public PCEPMonReq(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		pceList = new LinkedList<PceId>();
		svecList = new LinkedList<SVECConstruct>();
		requestList = new LinkedList<Request>();
		
		decode();

	}
	
	@Override
	public void encode() throws PCEPProtocolViolationException {
		//Encoding PCEP Monitoring Request Message

		if ((monitoring==null)||(pccReqId==null)){
			log.warn("There should be at least one request in a PCEP Request message");
			throw new PCEPProtocolViolationException();
		}
		int len = 4;
		monitoring.encode();
		len=len+monitoring.getLength();
		pccReqId.encode();
		len=len+pccReqId.getLength();

		for (int i=0;i<svecList.size();++i){
			svecList.get(i).encode();
			len=len+svecList.get(i).getLength();
		}
		for (int i=0;i<requestList.size();++i){
			requestList.get(i).encode();
			len=len+requestList.get(i).getLength();
		}
		for (int i=0;i<pceList.size();++i){
			pceList.get(i).encode();
			len=len+pceList.get(i).getLength();
		}
		this.setMessageLength(len);		
		messageBytes=new byte[len];
		encodeHeader();
		int offset=4;
		//MONITORING
		System.arraycopy(monitoring.getBytes(),0,messageBytes,offset,monitoring.getLength());
		offset=offset+monitoring.getLength();
		
		//PCC-ID-REQ
		System.arraycopy(pccReqId.getBytes(),0,messageBytes,offset,pccReqId.getLength());
		offset=offset+pccReqId.getLength();
		//PCE-LIST
		for (int i=0;i<pceList.size();++i){
			System.arraycopy(pceList.get(i).getBytes(), 0, messageBytes, offset, pceList.get(i).getLength());
			offset=offset+pceList.get(i).getLength();
		}
		//SVEC-LIST
		for (int i=0;i<svecList.size();++i){
			System.arraycopy(svecList.get(i).getBytes(), 0, messageBytes, offset, svecList.get(i).getLength());
			offset=offset+svecList.get(i).getLength();
		}
		//REQUEST-LIST
		for (int i=0;i<requestList.size();++i){
			System.arraycopy(requestList.get(i).getBytes(), 0, messageBytes, offset, requestList.get(i).getLength());
			offset=offset+requestList.get(i).getLength();		
		}
	}
	
	/**
	 * Decodes a PCEP Mon Request following RFC 5440, RFC 5541, RFC 5886 and RFC 5521 
	 * @param bytes bytes
	 * @throws PCEPProtocolViolationException Exception when the message is malformed 
	 */
	public void decode() throws PCEPProtocolViolationException{
		//Current implementation is strict, does not accept unknown objects 
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
		else{
			log.warn("Malformed PCEP Mon Request");
			throw new PCEPProtocolViolationException();
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
		else{
			log.warn("Malformed PCEP Mon Request");
			throw new PCEPProtocolViolationException();
		}
		while (PCEPObject.getObjectClass(bytes, offset) == ObjectParameters.PCEP_OBJECT_CLASS_PCE_ID){
			PceId pceId;
			try {
				pceId = new PceIdIPv4(bytes,offset);
				pceList.add(pceId);
				offset=offset+pceId.getLength();
			} catch (MalformedPCEPObjectException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SVEC){
			SVECConstruct c_svec;
			try {
				c_svec = new SVECConstruct(bytes,offset);
			} catch (PCEPProtocolViolationException e) {
				log.warn("Malformed SVEC Construct");
				throw new PCEPProtocolViolationException();
			}
			svecList.add(c_svec);
			offset=offset+c_svec.getLength();
		}
		while (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			Request req=new Request(bytes, offset);
			requestList.add(req);
			offset=offset+req.getLength();
		}
		
	
	}
	

	
	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}
	public void setPccReqId(PccReqId pccReqId) {
		this.pccReqId = pccReqId;
	}
	public void addRequest(Request request){		
		this.requestList.add(request);
	}
	
	public void addSvec(SVECConstruct svec){		
		this.svecList.add(svec);
	}
	public void addPceId(PceId pce){		
		this.pceList.add(pce);
	}
	public Request getRequest(int index){
		return this.requestList.get(index);	
	}
	public Monitoring getMonitoring() {
		return monitoring;
	}
	public PccReqId getPccReqId() {
		return pccReqId;
	}
	public LinkedList<PceId> getPceList() {
		return pceList;
	}
	public void setPceList(LinkedList<PceId> pceList) {
		this.pceList = pceList;
	}
	public LinkedList<SVECConstruct> getSvecList() {
		return svecList;
	}
	public void setSvecList(LinkedList<SVECConstruct> svecList) {
		this.svecList = svecList;
	}
	public LinkedList<Request> getRequestList() {
		return requestList;
	}
	public void setRequestList(LinkedList<Request> requestList) {
		this.requestList = requestList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((monitoring == null) ? 0 : monitoring.hashCode());
		result = prime * result
				+ ((pccReqId == null) ? 0 : pccReqId.hashCode());
		result = prime * result + ((pceList == null) ? 0 : pceList.hashCode());
		result = prime * result
				+ ((requestList == null) ? 0 : requestList.hashCode());
		result = prime * result
				+ ((svecList == null) ? 0 : svecList.hashCode());
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
		PCEPMonReq other = (PCEPMonReq) obj;
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
		if (pceList == null) {
			if (other.pceList != null)
				return false;
		} else if (!pceList.equals(other.pceList))
			return false;
		if (requestList == null) {
			if (other.requestList != null)
				return false;
		} else if (!requestList.equals(other.requestList))
			return false;
		if (svecList == null) {
			if (other.svecList != null)
				return false;
		} else if (!svecList.equals(other.svecList))
			return false;
		return true;
	}
	

}
