package es.tid.pce.pcep.messages;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Monitoring;
import es.tid.pce.pcep.objects.PccReqId;
import es.tid.pce.pcep.objects.RequestParameters;

/**
 * <h1> Path Monitoring Reply (PCMonRep) Message (RFC 5886). </h1>
 * <p>The PCMonRep message is used to provide PCE state metrics back to the
   requester for out-of-band monitoring requests.  The Message-Type
   field of the PCEP common header for the PCMonRep message is set to 9.</p>
   <p>There is one mandatory object that MUST be included within a PCMonRep
   message: the MONITORING object.  If the MONITORING
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=4 (MONITORING
   object missing).</p>

   <p>   Format of a PCMonRep (out-of-band request):</p>

    <PCMonRep Message>::= <Common Header>
                         <MONITORING>
                         <PCC-ID-REQ>
                         [<RP>]
                         [<metric-pce-list>]

   where:

   <<metric-pce-list>::=<metric-pce>[<metric-pce-list>]

   <metric-pce>::=<PCE-ID>
                  [<PROC-TIME>]
                  [<OVERLOAD>]


 *
 * @author Marta Cuaresma Saturio
 *
 */
public class PCEPMonRep  extends PCEPMessage {
	private Monitoring monitoring;
	private PccReqId pccReqId;
	private RequestParameters RP;
	//private LinkedList<> metricPceList;

	/**
	 * Construct new PCEP PCMonReq
	 */
	public PCEPMonRep () {
		super();
		this.setMessageType(PCEPMessageTypes.MESSAGE_PCMONREP);
		monitoring=new Monitoring();
		pccReqId=new PccReqId();
	}
	@Override
	public void encode() throws PCEPProtocolViolationException {
		monitoring.encode();
		pccReqId.encode();
		this.setMessageLength(4+monitoring.getLength()+pccReqId.getLength());
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		System.arraycopy(monitoring.getBytes(), 0, messageBytes, 4, monitoring.getLength());
		
	}
	public Monitoring getMonitoring() {
		return monitoring;
	}
	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}
	public PccReqId getPccReqId() {
		return pccReqId;
	}
	public void setPccReqId(PccReqId pccReqId) {
		this.pccReqId = pccReqId;
	}
	public RequestParameters getRP() {
		return RP;
	}
	public void setRP(RequestParameters rP) {
		RP = rP;
	}
	
	
	
	
}
