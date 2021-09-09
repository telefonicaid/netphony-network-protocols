package es.tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.Objects;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.*;
import jdk.internal.org.jline.utils.Log;

/**
 * Request Object. 
 * 
 * Is a collection of objects (thus, It is not an object itself); RFC 5440
 * ADDED SUPPORT FOR OF from RFC 5541  AND XRO from RFC 5521
 * ADDED INTERLAYER-SWITCH-LAYER SUPPORT
 * <p> From RFC 5440, Section 6.4
 * {@code
 *  <request>::= <RP>
                   <END-POINTS>
                   [<LSPA>]
                   [<BANDWIDTH>]
                   
                   AQUIIIIIIIIIIII
                   [<metric-list>]
                   [<OF>]
                   [<RRO>[<BANDWIDTH>]]
                   [<IRO>]
                   [<LOAD-BALANCING>]
                   [<XRO>] 
                   [<INTER-LAYER> [<SWITCH-LAYER>]]
                   [<REQ-ADAP-CAP>]
}
 There are two mandatory objects that MUST be included within a PCReq
   message: the RP and the END-POINTS objects (see Section 7).  If one
   or both of these objects is missing, the receiving PCE MUST send an
   error message to the requesting PCC.  Other objects are optional.
 * 
 * </p>
 * @author ogondio
 *
 */
public class Request extends PCEPConstruct{

	private RequestParameters requestParameters;//COMPULSORY!!
	private EndPoints endPoints;//COMPULSORY!!!
	private LSPA lSPA;
	private Bandwidth bandwidth;
	private LinkedList<BandwidthUtilization> buList;
	private LinkedList<Metric> metricList;
	private RROBandwidth rROBandwidth;
	private IncludeRouteObject iRO;
	private LoadBalancing loadBalancing;
	private ObjectiveFunction objectiveFunction;
	private ExcludeRouteObject xro;
	private InterLayer interLayer;
	private SwitchLayer switchLayer;
	private ReqAdapCap reqAdapCap;
	
	/**
	 * LSP Object: According to RFC 8231, the LSP object can be included.
	 */
	private LSP lsp;
	
	/**
	 * Reservation Object //OPTIONAL AND TEMPORAL!!
	 */
	private Reservation reservation;

	
	/**
	 * Default constructor. 
	 * Use this method to create a new Request from scratch
	 */
	public Request(){
		metricList=new LinkedList<Metric>();
		buList = new LinkedList<BandwidthUtilization>();
		
	}
	
	/**
	 * 
	 * Use this method to create a new Request from a sequence of bytes	 
	 * @param bytes bytes
	 * @param offset bytes 
	 * @throws PCEPProtocolViolationException Exception when the bytes do not lead to a valid PCEP Request Construct 
	 */
	public Request(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		buList = new LinkedList<BandwidthUtilization>();
		decode(bytes,offset);
	}

	public void encode() throws PCEPProtocolViolationException{
		//Encoding Request Construct
		int len=0;
		if (requestParameters!=null){
			requestParameters.encode();
			len=len+requestParameters.getLength();
		}
		else {
			log.warn("Request Parameters not found! They are compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (endPoints!=null){
			endPoints.encode();
			len=len+endPoints.getLength();
		}
		else {
			log.warn("EndPoints not found! They are compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (lsp!=null) {
			lsp.encode();
			len=len+lsp.getLength();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}
		
		if (buList!=null){
			for (int i=0;i<buList.size();++i){
				(buList.get(i)).encode();
				len=len+(buList.get(i)).getLength();
			}	
		}
		
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				(metricList.get(i)).encode();
				len=len+(metricList.get(i)).getLength();
			}
		}
		if (objectiveFunction!=null){
			objectiveFunction.encode();
			len=len+objectiveFunction.getLength();
		}
		if (reservation!=null){
			reservation.encode();
			len=len+reservation.getLength();
		}
		if (rROBandwidth!=null){
			rROBandwidth.encode();
			len=len+rROBandwidth.getLength();
		}
		if (iRO!=null){
			iRO.encode();
			len=len+iRO.getLength();
		}
		if (loadBalancing!=null){
			loadBalancing.encode();
			len=len+loadBalancing.getLength();
		}
		if (xro!=null){
			xro.encode();
			len=len+xro.getLength();
		}
		if (interLayer!=null){
			interLayer.encode();
			len=len+interLayer.getLength();
		}
		if (switchLayer!=null){
			switchLayer.encode();
			len=len+switchLayer.getLength();
		}
		if (reqAdapCap!=null){
			reqAdapCap.encode();
			len=len+reqAdapCap.getLength();
		}
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		System.arraycopy(requestParameters.getBytes(), 0, bytes, offset, requestParameters.getLength());
		offset=offset+requestParameters.getLength();
		System.arraycopy(endPoints.getBytes(), 0, bytes, offset, endPoints.getLength());
		offset=offset+endPoints.getLength();
		if (lsp!=null) {
			System.arraycopy(lsp.getBytes(), 0, bytes, offset, lsp.getLength());
			offset=offset+lsp.getLength();			
		}
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();

		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
		
		if(buList!=null) {
			for (int i=0;i<buList.size();++i){
				System.arraycopy(buList.get(i).getBytes(), 0, bytes, offset, buList.get(i).getLength());
				offset=offset+buList.get(i).getLength();
			}
		}

		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
				offset=offset+metricList.get(i).getLength();
			}
		}
		if (objectiveFunction!=null){
			System.arraycopy(objectiveFunction.getBytes(), 0, bytes, offset, objectiveFunction.getLength());
			offset=offset+objectiveFunction.getLength();
		}
		if (reservation!=null){
			System.arraycopy(reservation.getBytes(), 0, bytes, offset, reservation.getLength());
			offset=offset+reservation.getLength();
		}
		if (rROBandwidth!=null){
			System.arraycopy(rROBandwidth.getBytes(), 0, bytes, offset, rROBandwidth.getLength());
			offset=offset+rROBandwidth.getLength();
		}
		if (iRO!=null){
			System.arraycopy(iRO.getBytes(), 0, bytes, offset, iRO.getLength());
			offset=offset+iRO.getLength();
		}
		if (loadBalancing!=null){
			System.arraycopy(loadBalancing.getBytes(), 0, bytes, offset, loadBalancing.getLength());
			offset=offset+loadBalancing.getLength();
		}			
		if (xro!=null){
			System.arraycopy(xro.getBytes(), 0, bytes, offset, xro.getLength());
			offset=offset+xro.getLength();
		}			
		if (interLayer!=null){
			System.arraycopy(interLayer.getBytes(), 0, bytes, offset, interLayer.getLength());
			offset=offset+interLayer.getLength();
		}			
		if (switchLayer!=null){
			System.arraycopy(switchLayer.getBytes(), 0, bytes, offset, switchLayer.getLength());
			offset=offset+switchLayer.getLength();
		}	
		if (reqAdapCap!=null){
			System.arraycopy(reqAdapCap.getBytes(), 0, bytes, offset, reqAdapCap.getLength());
			offset=offset+reqAdapCap.getLength();
		}	

	}

	/**
	 * Decode a Request rule;
	 * @param bytes bytes
	 * @param offset offset
	 * @throws PCEPProtocolViolationException Exception when the bytes do not lead to a valid PCEP Request Construct 
	 */
	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		//Decoding Request Rule
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warn("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}

		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		//EMPEZAMOS HACIENDO IMPLEMENTACION ESTRICTA
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			try {
				requestParameters=new RequestParameters(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed RP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+requestParameters.getLength();
			len=len+requestParameters.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		else {
			log.warn("Request must start with RP object");
			throw new PCEPProtocolViolationException();
		}

		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS){
			ot=PCEPObject.getObjectType(bytes, offset);
			log.debug("Request: ot = "+ot);
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS){
				try {
					int endPointType=GeneralizedEndPoints.getGeneralizedEndPointsType(bytes,offset);
					if (endPointType==1) {
						endPoints=new P2PGeneralizedEndPoints(bytes,offset);	
					}
					
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed GENERALIZED END POINTS Object found");
					throw new PCEPProtocolViolationException();
				}
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_IPV4){
				try {
					endPoints=new P2MPEndPointsIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed P2MP ENDPOINTS DataPathID Object found");
					throw new PCEPProtocolViolationException();
				}
			}						
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
				try {
					endPoints=new EndPointsIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed ENDPOINTS IPV4 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED){
				try {
					endPoints=new EndPointsUnnumberedIntf(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed ENDPOINTS Unnumbered Interface Object found");
					throw new PCEPProtocolViolationException();
				}
			}

			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
				try {
					endPoints=new EndPointsIPv6(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed ENDPOINTSIPV6 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else {
				log.warn("END POINT TYPE NOT SUPPORTED");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+endPoints.getLength();
			len=len+endPoints.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		else {
			log.warn("ENDPOINTS COMPULSORY AFTER  RP object");
			throw new PCEPProtocolViolationException();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSP){
			try {
				lsp=new LSP(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lsp.getLength();
			len=len+lsp.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			try {
				lSPA=new LSPA(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lSPA.getLength();
			len=len+lSPA.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}

		// Bandwidth
		try {
			oc = PCEPObject.getObjectClass(bytes, offset);
			ot = PCEPObject.getObjectType(bytes, offset);
			if (oc == ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH) {
				if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST) {
					bandwidth = new BandwidthRequested(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP) {
					bandwidth = new BandwidthExistingLSP(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST) {
					bandwidth = new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP) {
					bandwidth = new BandwidthExistingLSPGeneralizedBandwidth(bytes, offset);
				} else {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}

				offset = offset + bandwidth.getLength();
				len = len + bandwidth.getLength();
				if (offset >= bytes.length) {
					this.setLength(len);
					return;
				}
			}
		} catch (MalformedPCEPObjectException e) {
			log.warn("Malformed BANDWIDTH Object found");
			throw new PCEPProtocolViolationException();
		}
		
		//BU Objects
	
		oc = PCEPObject.getObjectClass(bytes, offset);
		while(oc==ObjectParameters.PCEP_OBJECT_CLASS_BU) {
			BandwidthUtilization bu;
			try {
				bu=new BandwidthUtilization(bytes,offset);
			}catch (MalformedPCEPObjectException e) {
				log.warn("Malformed BU Object found");
				throw new PCEPProtocolViolationException();
			}
			buList.add(bu);
			offset=offset+bu.getLength();
			len=len+bu.getLength();
			if (offset >= bytes.length) {
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
			
		}
		
		
		
		//Metric
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_METRIC){
			Metric metric;
			try {
				metric = new Metric(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset=offset+metric.getLength();
			len=len+metric.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION){
			try {
				objectiveFunction=new ObjectiveFunction(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed OBJECTIVE FUNCTION Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+objectiveFunction.getLength();
			len=len+objectiveFunction.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RESERVATION){
			try {
				reservation=new Reservation(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed OBJECTIVE FUNCTION Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+reservation.getLength();
			len=len+reservation.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}

		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RRO){
			rROBandwidth=new RROBandwidth(bytes, offset);
			offset=offset+rROBandwidth.getLength();
			len=len+rROBandwidth.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}

		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_IRO){
			try {
				iRO=new IncludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed IRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+iRO.getLength();
			len=len+iRO.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LOADBALANCING){
			try {

				loadBalancing=new LoadBalancing(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LOADBALANCING Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+loadBalancing.getLength();
			len=len+loadBalancing.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_XRO){
			try {
				xro=new ExcludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed XRO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+xro.getLength();
			len=len+xro.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_INTER_LAYER){
			try {
				interLayer=new InterLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed INTER_LAYER Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+interLayer.getLength();
			len=len+interLayer.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SWITCH_LAYER){
			try {
				switchLayer=new SwitchLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed SWITCH_LAYER Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+switchLayer.getLength();
			len=len+switchLayer.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_REQ_ADAP_CAP){
			try {
				reqAdapCap=new ReqAdapCap(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed ReqAdapCap Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+reqAdapCap.getLength();
			len=len+reqAdapCap.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		this.setLength(len);
		
	}

		
	public RequestParameters getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(RequestParameters requestParameters) {
		this.requestParameters = requestParameters;
	}
	public EndPoints getEndPoints() {
		return endPoints;
	}

	public void setEndPoints(EndPoints endPoints) {
		this.endPoints = endPoints;
	}

	public LSPA getLSPA() {
		return lSPA;
	}

	public void setLSPA(LSPA lSPA) {
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

	public RROBandwidth getRROBandwidth() {
		return rROBandwidth;
	}

	
	public void setRROBandwidth(RROBandwidth rROBandwidth) {
		this.rROBandwidth = rROBandwidth;
	}

	public IncludeRouteObject getIRO() {
		return iRO;
	}

	public void setIRO(IncludeRouteObject iRO) {
		this.iRO = iRO;
	}

	public LoadBalancing getLoadBalancing() {
		return loadBalancing;
	}

	public void setLoadBalancing(LoadBalancing loadBalancing) {
		this.loadBalancing = loadBalancing;
	}
	
	public ObjectiveFunction getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(ObjectiveFunction objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}
	

	public ExcludeRouteObject getXro() {
		return xro;
	}

	public void setXro(ExcludeRouteObject xro) {
		this.xro = xro;
	}
	

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	

	public InterLayer getInterLayer() {
		return interLayer;
	}

	public void setInterLayer(InterLayer interLayer) {
		this.interLayer = interLayer;
	}

	public SwitchLayer getSwitchLayer() {
		return switchLayer;
	}

	public void setSwitchLayer(SwitchLayer switchLayer) {
		this.switchLayer = switchLayer;
	}

	public ReqAdapCap getReqAdapCap() {
		return reqAdapCap;
	}

	public void setReqAdapCap(ReqAdapCap reqAdapCap) {
		this.reqAdapCap = reqAdapCap;
	}

	
	public LinkedList<BandwidthUtilization> getBuList() {
		return buList;
	}

	public void setBuList(LinkedList<BandwidthUtilization> buList) {
		this.buList = buList;
	}
	
	public Request duplicate(){
		Request req=new Request();
		req.setRequestParameters(this.requestParameters);
		req.setEndPoints(this.endPoints);
		
		req.setLSPA(this.lSPA);
		req.setBandwidth(this.bandwidth);
		req.setMetricList(this.metricList);
		req.setRROBandwidth(this.rROBandwidth);
		req.setIRO(this.iRO);
		req.setLoadBalancing(this.loadBalancing);
		req.setObjectiveFunction(this.objectiveFunction);
		req.setXro(this.xro);
		req.setInterLayer(this.interLayer);
		req.setSwitchLayer(this.switchLayer);
		req.setReservation(this.reservation);
		return req;
	}



	public LSP getLsp() {
		return lsp;
	}

	public void setLsp(LSP lsp) {
		this.lsp = lsp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(bandwidth, buList, endPoints, iRO, interLayer, lSPA, loadBalancing, lsp, metricList,
						objectiveFunction, rROBandwidth, reqAdapCap, requestParameters, reservation, switchLayer, xro);
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
		Request other = (Request) obj;
		return Objects.equals(bandwidth, other.bandwidth) && Objects.equals(buList, other.buList)
				&& Objects.equals(endPoints, other.endPoints) && Objects.equals(iRO, other.iRO)
				&& Objects.equals(interLayer, other.interLayer) && Objects.equals(lSPA, other.lSPA)
				&& Objects.equals(loadBalancing, other.loadBalancing) && Objects.equals(lsp, other.lsp)
				&& Objects.equals(metricList, other.metricList)
				&& Objects.equals(objectiveFunction, other.objectiveFunction)
				&& Objects.equals(rROBandwidth, other.rROBandwidth) && Objects.equals(reqAdapCap, other.reqAdapCap)
				&& Objects.equals(requestParameters, other.requestParameters)
				&& Objects.equals(reservation, other.reservation) && Objects.equals(switchLayer, other.switchLayer)
				&& Objects.equals(xro, other.xro);
	}

	@Override
	public String toString() {
		return "Request [requestParameters=" + requestParameters + ", endPoints=" + endPoints + ", lSPA=" + lSPA
				+ ", bandwidth=" + bandwidth + ", buList=" + buList + ", metricList=" + metricList + ", rROBandwidth="
				+ rROBandwidth + ", iRO=" + iRO + ", loadBalancing=" + loadBalancing + ", objectiveFunction="
				+ objectiveFunction + ", xro=" + xro + ", interLayer=" + interLayer + ", switchLayer=" + switchLayer
				+ ", reqAdapCap=" + reqAdapCap + ", lsp=" + lsp + ", reservation=" + reservation + "]";
	}

	
	
	
	
	
	
	
}
