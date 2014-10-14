package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.pce.pcep.objects.IncludeRouteObject;
import es.tid.pce.pcep.objects.LSPA;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Metric;
import es.tid.pce.pcep.objects.Monitoring;
import es.tid.pce.pcep.objects.NoPath;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.PccReqId;
import es.tid.pce.pcep.objects.RequestParameters;
import es.tid.pce.pcep.objects.ReservationConf;

/**
 * <p> Represents a PCEP Response. </p>  
 * <p> It is a collection of PCEP Objects. </p>
 * <p> From <a href="http://tools.ietf.org/search/rfc5440#page-19">RFC 5440 Section 6.5 </a> :<p>
 * <p> 
 *  <response>::=<RP>
                  [<NO-PATH>]
                  [<attribute-list>]
                  [<path-list>]

      <path-list>::=<path>[<path-list>]

      <path>::= <ERO><attribute-list>

   where:

    <attribute-list>::=[<LSPA>]
                       [<BANDWIDTH>]
                       [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                       [<metric-list>]
                       [<IRO>]

    <metric-list>::=<METRIC>[<metric-list>]


The PCRep message MUST contain at least one RP object.  For each
   reply that is bundled into a single PCReq message, an RP object MUST
   be included that contains a Request-ID-number identical to the one
   specified in the RP object carried in the corresponding PCReq message
   (see Section 7.4 for the definition of the RP object).

 * </p>
 * 
 * With monitoring data:
 * <response>::=<RP>
                   <MONITORING>
                   <PCC-ID-REQ>
                  [<NO-PATH>]
                  [<attribute-list>]
                  [<path-list>]
                  [<metric-pce-list>]
 * 
 * @author Oscar Gonzalez de Dios
 *
 */
public class Response extends PCEPConstruct{

	/**
	 * 
	 */
	private RequestParameters requestParameters;

	/**
	 * Monitoring object
	 */
	private Monitoring monitoring;

	/**
	 * 
	 */
	private PccReqId pccIdreq;

	/**
	 * 
	 */
	private NoPath noPath;

	/**
	 * 
	 */
	private LSPA lSPA;

	/**
	 * 
	 */
	private Bandwidth bandwidth;

	/**
	 * 
	 */
	private BandwidthRequestedGeneralizedBandwidth generalizedbandwidth;

	/**
	 * 
	 */
	private LinkedList<Metric> metricList;

	/**
	 * 
	 */
	private IncludeRouteObject iRO;

	private ReservationConf resConf;

	/**
	 * 
	 */
	private LinkedList<Path> pathList;

	/**
	 * 
	 */
	private LinkedList<MetricPCE> metricPCEList;

	public Response() {
		metricList=new LinkedList<Metric>();
		pathList=new LinkedList<Path>();
		metricPCEList=new LinkedList<MetricPCE>();
	}

	public Response(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		metricList=new LinkedList<Metric>();
		pathList=new LinkedList<Path>();
		metricPCEList=new LinkedList<MetricPCE>();
	}

	public RequestParameters getRequestParameters() {
		return requestParameters;
	}
	public void setRequestParameters(RequestParameters requestParameters) {
		this.requestParameters = requestParameters;
	}
	public NoPath getNoPath() {
		return noPath;
	}
	public void setNoPath(NoPath noPath) {
		this.noPath = noPath;
	}
	public LSPA getlSPA() {
		return lSPA;
	}
	public void setlSPA(LSPA lSPA) {
		this.lSPA = lSPA;
	}
	public Bandwidth getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}
	public LinkedList<Metric> getMetricList() {
		return metricList;
	}
	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}
	public IncludeRouteObject getiRO() {
		return iRO;
	}
	public void setiRO(IncludeRouteObject iRO) {
		this.iRO = iRO;
	}

	public LinkedList<Path> getPathList() {
		return pathList;
	}

	public void addPath(Path path){
		this.pathList.add(path);
	}

	public Path getPath(int index){
		return this.pathList.get(index);
	}


	public void setPathList(LinkedList<Path> pathList) {
		this.pathList = pathList;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public PccReqId getPccIdreq() {
		return pccIdreq;
	}

	public void setPccIdreq(PccReqId pccIdreq) {
		this.pccIdreq = pccIdreq;
	}

	public LinkedList<MetricPCE> getMetricPCEList() {
		return metricPCEList;
	}

	public void setMetricPCEList(LinkedList<MetricPCE> metricPCEList) {
		this.metricPCEList = metricPCEList;
	}

	public ReservationConf getResConf() {
		return resConf;
	}

	public void setResConf(ReservationConf resConf) {
		this.resConf = resConf;
	}


	public BandwidthRequestedGeneralizedBandwidth getGeneralizedbandwidth() {
		return generalizedbandwidth;
	}

	public void setGeneralizedbandwidth(BandwidthRequestedGeneralizedBandwidth generalizedbandwidth) {
		this.generalizedbandwidth = generalizedbandwidth;
	}
	
	/**
	 * 
	 */
	public void encode() throws PCEPProtocolViolationException {		
		int len=0;
		if (requestParameters!=null){
			requestParameters.encode();
			len=len+requestParameters.getLength();

		}
		else {
			log.warning("requestParameters is compulsory in response");
			throw new PCEPProtocolViolationException();
		}
		if (monitoring!=null){
			monitoring.encode();
			len=len+monitoring.getLength();
		}
		if (pccIdreq!=null){
			pccIdreq.encode();
			len=len+pccIdreq.getLength();
		}
		if (noPath!=null){
			noPath.encode();
			len=len+noPath.getLength();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		if (generalizedbandwidth!=null){
			generalizedbandwidth.encode();
			len=len+generalizedbandwidth.getLength();
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				(metricList.get(i)).encode();
				len=len+(metricList.get(i)).getLength();
			}
		}
		if (iRO!=null){
			iRO.encode();
			len=len+iRO.getLength();
		}
		if (resConf!=null){
			resConf.encode();
			len=len+resConf.getLength();
		}
		if (pathList!=null){
			for (int i=0;i<pathList.size();++i){
				(pathList.get(i)).encode();
				len=len+(pathList.get(i)).getLength();
			}
		}
		if (metricPCEList!=null){
			for (int i=0;i<metricPCEList.size();++i){
				(metricPCEList.get(i)).encode();
				len=len+(metricPCEList.get(i)).getLength();
			}
		}
		
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(requestParameters.getBytes(), 0, bytes, offset, requestParameters.getLength());
		offset=offset+requestParameters.getLength();
		if (monitoring!=null){
			System.arraycopy(monitoring.getBytes(), 0, bytes, offset, monitoring.getLength());
			offset=offset+monitoring.getLength();
		}
		if (pccIdreq!=null){
			System.arraycopy(pccIdreq.getBytes(), 0, bytes, offset, pccIdreq.getLength());
			offset=offset+pccIdreq.getLength();
		}		
		if (noPath!=null){
			System.arraycopy(noPath.getBytes(), 0, bytes, offset, noPath.getLength());
			offset=offset+noPath.getLength();
		}
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();
		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		if (generalizedbandwidth!=null){
			System.arraycopy(generalizedbandwidth.getBytes(), 0, bytes, offset, generalizedbandwidth.getLength());
			offset=offset+generalizedbandwidth.getLength();
		}
		for (int i=0;i<metricList.size();++i){
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset=offset+metricList.get(i).getLength();
		}
		if (iRO!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, iRO.getLength());
			offset=offset+iRO.getLength();
		}
		if (resConf!=null){
			System.arraycopy(resConf.getBytes(), 0, bytes, offset, resConf.getLength());
			offset=offset+resConf.getLength();
		}
		for (int i=0;i<pathList.size();++i){
			System.arraycopy(pathList.get(i).getBytes(), 0, bytes, offset, pathList.get(i).getLength());
			offset=offset+pathList.get(i).getLength();
		}
		for (int i=0;i<metricPCEList.size();++i){
			System.arraycopy(metricPCEList.get(i).getBytes(), 0, bytes, offset, metricPCEList.get(i).getLength());
			offset=offset+metricPCEList.get(i).getLength();
		}	
		
	}

	public void decode(byte[] bytes, int offset)
			throws PCEPProtocolViolationException {
		//Decoding Response Rule
		int len=0;
		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		//IF UNKNOWN OBJECTS ARE PRESENT, EXCEPTION IS THROWN...
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			try {
				requestParameters=new RequestParameters(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed RP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+requestParameters.getLength();
			len=len+requestParameters.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		else {
			log.warning("Request must start with RP object");
			throw new PCEPProtocolViolationException();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_MONITORING){
			try {
				monitoring=new Monitoring(bytes, offset);							
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed Monitoring Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+monitoring.getLength();
			len=len+monitoring.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCC_REQ_ID){
			try {
				pccIdreq=new PccReqId(bytes, offset);							
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed PCC ID REQ Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+pccIdreq.getLength();
			len=len+pccIdreq.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_NOPATH){
			try {
				noPath=new NoPath(bytes, offset);							
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed NOPATH Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+noPath.getLength();
			len=len+noPath.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			try {
				lSPA=new LSPA(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lSPA.getLength();
			len=len+lSPA.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}			
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthExistingLSP(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST){
				try {
					bandwidth=new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else {
				throw new PCEPProtocolViolationException();
			}
			
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
	
		oc=PCEPObject.getObjectClass(bytes, offset);

		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_METRIC){
			Metric metric;
			try {
				metric = new Metric(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset=offset+metric.getLength();
			len=len+metric.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_IRO){
			try {
				iRO=new IncludeRouteObject(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed IRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+iRO.getLength();
			len=len+iRO.getLength();
			if ((offset+len)>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RESERVATION_CONF){
			try {
				resConf=new ReservationConf(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed RESERVATION CONF  Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+resConf.getLength();
			len=len+resConf.getLength();
			if ((offset+len)>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_ERO){
			Path path=new Path(bytes,offset);
			pathList.add(path);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO){
			Path path=new Path(bytes,offset);
			pathList.add(path);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}		


		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCE_ID){
			MetricPCE metricPCE=new MetricPCE(bytes,offset);
			metricPCEList.add(metricPCE);
			offset=offset+metricPCE.getLength();
			len=len+metricPCE.getLength();
			if ((offset+len)>=bytes.length){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		this.setLength(len);

	}

	public String toString(){
		String ret="";
		if (requestParameters!=null){
			ret=ret+requestParameters.toString();
		}
		if (noPath!=null){
			ret=ret+"<NOPATH>";
		}
		if (lSPA!=null){
			ret=ret+"<LSPA>";
		}
		if (bandwidth!=null){
			ret=ret+"<BW>";
		}
		if (generalizedbandwidth!=null){
			ret=ret+"<GBW>";
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				ret=ret+metricList.get(i).toString();
			}
		}
		if (iRO!=null){
			ret=ret+"<IRO>";
		}
		if (pathList!=null){
			for (int i=0;i<pathList.size();++i){
				ret=ret+pathList.get(i).toString();
			}
		}
		if (metricPCEList!=null){
			for (int i=0;i<metricPCEList.size();++i){
				ret=ret+metricPCEList.get(i).toString();
			}
		}

		return ret;
	}


}