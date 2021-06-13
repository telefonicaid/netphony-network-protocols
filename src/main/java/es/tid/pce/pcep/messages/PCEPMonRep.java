package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Request;
import es.tid.pce.pcep.constructs.SVECConstruct;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Monitoring;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.PccReqId;
import es.tid.pce.pcep.objects.PceId;
import es.tid.pce.pcep.objects.RequestParameters;

/**
 * Path Monitoring Reply (PCMonRep) Message (RFC 5886).
 * <p>The PCMonRep message is used to provide PCE state metrics back to the
   requester for out-of-band monitoring requests.  The Message-Type
   field of the PCEP common header for the PCMonRep message is set to 9.</p>
   <p>There is one mandatory object that MUST be included within a PCMonRep
   message: the MONITORING object.  If the MONITORING
   object is missing, the receiving PCE MUST send a PCErr message with
   Error-type=6 (Mandatory Object missing) and Error-value=4 (MONITORING
   object missing).</p>

   <p>   Format of a PCMonRep (out-of-band request):</p>
{@code
    <PCMonRep Message>::= <Common Header>
                         <MONITORING>
                         <PCC-ID-REQ>
                         [<RP>]
                         [<metric-pce-list>]

   where:

   <<metric-pce-list>::=<metric-pce>[<metric-pce-list>]

   <metric-pce>::=<PCE-ID>
                  [<PROC-TIME>]
                  [<OVERLOAD>]}


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

	public PCEPMonRep(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		decode();

	}

	@Override
	public void encode() throws PCEPProtocolViolationException {
		int len=4;
		monitoring.encode();
		len+=monitoring.getLength();
		pccReqId.encode();
		len+=pccReqId.getLength();
		if (this.RP!=null) {
			this.RP.encode();
			len+=RP.getLength();
		}
		this.setMessageLength(len);
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		int offset=4;
		System.arraycopy(monitoring.getBytes(), 0, messageBytes,offset, monitoring.getLength());
		offset+=monitoring.getLength();
		System.arraycopy(pccReqId.getBytes(), 0, messageBytes,offset, pccReqId.getLength());
		offset+=pccReqId.getLength();
		if (this.RP!=null) {
			System.arraycopy(RP.getBytes(), 0, messageBytes,offset, RP.getLength());
			offset+=RP.getLength();
		} 
	}

	public void decode() throws PCEPProtocolViolationException {
		try {
			int offset=4;//We start after the object header
			// Monitoring object
			int oc=PCEPObject.getObjectClass(this.getBytes(), offset);
			if (oc==ObjectParameters.PCEP_OBJECT_CLASS_MONITORING){
				monitoring=new Monitoring(this.getBytes(),offset);		
				offset=offset+monitoring.getLength();
			}

			//PCC Req Id
			oc=PCEPObject.getObjectClass(this.getBytes(), offset);
			if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCC_REQ_ID){
				pccReqId=new PccReqId(this.getBytes(),offset);
				offset=offset+pccReqId.getLength();
			}

			//RP
			oc=PCEPObject.getObjectClass(this.getBytes(), offset);
			if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
				this.RP=new RequestParameters(this.getBytes(),offset);
				offset=offset+RP.getLength();
			}

		} catch (Exception e) {
			log.warn("Malformed Monitoring Object found");
			throw new PCEPProtocolViolationException();
		}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((RP == null) ? 0 : RP.hashCode());
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
		PCEPMonRep other = (PCEPMonRep) obj;
		if (RP == null) {
			if (other.RP != null)
				return false;
		} else if (!RP.equals(other.RP))
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

	@Override
	public String toString() {
		return "PCEPMonRep [monitoring=" + monitoring + ", pccReqId=" + pccReqId + ", RP=" + RP + "]";
	}




}
