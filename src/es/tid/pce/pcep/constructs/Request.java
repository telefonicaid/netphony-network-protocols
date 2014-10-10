package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.*;

/**
 * Request Object. 
 * 
 * Is a collection of objects (thus, It is not an object itself); RFC 5440
 * ADDED SUPPORT FOR OF from RFC 5541  AND XRO from RFC 5521
 * ADDED INTERLAYER-SWITCH-LAYER SUPPORT
 * <p> From RFC 5440, Section 6.4
 *  <request>::= <RP>
                   <END-POINTS>
                   [<LSPA>]
                   [<BANDWIDTH>]
                   [<GENERALIZED-BANDWIDTH>] --ESTADO DRAFT
                   [<metric-list>]
                   [<OF>]
                   [<RRO>[<BANDWIDTH>]]
                   [<IRO>]
                   [<LOAD-BALANCING>]
                   [<XRO>] 
                   [<INTER-LAYER> [<SWITCH-LAYER>]]
                   [<REQ-ADAP-CAP>]

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
	 * Reservation Object //OPTIONAL AND TEMPORAL!!
	 */
	private Reservation reservation;

	
	/**
	 * Default constructor. 
	 * Use this method to create a new Request from scratch
	 */
	public Request(){
		metricList=new LinkedList<Metric>();
		
	}
	
	/**
	 * 
	 * Use this method to create a new Request from a sequence of bytes	 
	 * @param bytes
	 * @param offset
	 * @throws PCEPProtocolViolationException
	 */
	public Request(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		metricList=new LinkedList<Metric>();
		decode(bytes,offset);
	}

	public void encode() throws PCEPProtocolViolationException{
		log.finest("Encoding Request Construct");
		int len=0;
		if (requestParameters!=null){
			requestParameters.encode();
			len=len+requestParameters.getLength();
		}
		else {
			log.warning("Request Parameters not found! They are compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (endPoints!=null){
			endPoints.encode();
			len=len+endPoints.getLength();
		}
		else {
			log.warning("EndPoints not found! They are compulsory");
			throw new PCEPProtocolViolationException();
		}
		if (lSPA!=null){
			lSPA.encode();
			len=len+lSPA.getLength();
		}
		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
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
		if (lSPA!=null){
			System.arraycopy(lSPA.getBytes(), 0, bytes, offset, lSPA.getLength());
			offset=offset+lSPA.getLength();

		}
		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
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
	 */
	private void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		log.finest("Decoding Request Rule");
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warning("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}

		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		//EMPEZAMOS HACIENDO IMPLEMENTACION ESTRICTA
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			try {
				requestParameters=new RequestParameters(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed RP Object found");
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
			log.warning("Request must start with RP object");
			throw new PCEPProtocolViolationException();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS){
			ot=PCEPObject.getObjectType(bytes, offset);
			log.info("PCEPObject.getObjectType(bytes, offset):"+PCEPObject.getObjectType(bytes, offset));
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_DATAPATHID){
				try {
					endPoints=new P2MPEndPointsDataPathID(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed P2MP ENDPOINTS DataPathID Object found");
					throw new PCEPProtocolViolationException();
				}
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_IPV4){
				try {
					endPoints=new P2MPEndPointsIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed P2MP ENDPOINTS DataPathID Object found");
					throw new PCEPProtocolViolationException();
				}
			}						
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
				try {
					endPoints=new EndPointsIPv4(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTS IPV4 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED){
				try {
					endPoints=new EndPointsUnnumberedIntf(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTS Unnumbered Interface Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC){
				try {
					endPoints=new XifiUniCastEndPoints(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed EndPointsMAC Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
				try {
					endPoints=new EndPointsIPv6(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed ENDPOINTSIPV6 Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS){
				try {
					endPoints=new GeneralizedEndPoints(bytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed GENERALIZED END POINTS Object found");
					throw new PCEPProtocolViolationException();
				}
			}
			else {
				log.warning("END POINT TYPE NOT SUPPORTED");
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
			log.warning("ENDPOINTS COMPULSORY AFTER  RP object");
			throw new PCEPProtocolViolationException();
		}
		oc=PCEPObject.getObjectClass(bytes, offset);		
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSPA){
			log.finest("LSPA Object found");
			try {
				lSPA=new LSPA(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed LSPA Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lSPA.getLength();
			len=len+lSPA.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST){
				log.finest("BANDWIDTH Request Object found");
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}			
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP){
				log.finest("BANDWIDTH Existing TE LSP Object found");
				try {
					bandwidth=new BandwidthExistingLSP(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST){
				log.finest("BANDWIDTH GENERALIZED BANDWIDTH Request Object found");
				try {
					bandwidth=new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP){
				log.finest("BANDWIDTH GENERALIZED BANDWIDTH Existing TE LSP Object found");
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warning("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else {
				log.warning("Malformed BANDWIDTH Object found");
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
			log.finest("METRIC Object found");
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
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OBJECTIVE_FUNCTION){
			log.finest("OBJECTIVE FUNCTION Object found");
			try {
				objectiveFunction=new ObjectiveFunction(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed OBJECTIVE FUNCTION Object found");
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
			log.finest("RESERVATION Object found");
			try {
				reservation=new Reservation(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed OBJECTIVE FUNCTION Object found");
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
			log.finest("RRO Object found");
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
			log.finest("IRO Object found");
			try {
				iRO=new IncludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed IRO Object found");
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
			log.finest("LOADBALANCING Object found");
			try {
				loadBalancing=new LoadBalancing(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed LOADBALANCING Object found");
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
			log.finest("XRO Object found");
			try {
				xro=new ExcludeRouteObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed XRO Object found");
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
			log.finest("INTER_LAYER Object found");
			try {
				interLayer=new InterLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed INTER_LAYER Object found");
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
			log.finest("INTER_LAYER Object found");
			try {
				switchLayer=new SwitchLayer(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed SWITCH_LAYER Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+switchLayer.getLength();
			len=len+switchLayer.getLength();
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

	public RROBandwidth getrROBandwidth() {
		return rROBandwidth;
	}

	public void setrROBandwidth(RROBandwidth rROBandwidth) {
		this.rROBandwidth = rROBandwidth;
	}

	public IncludeRouteObject getiRO() {
		return iRO;
	}

	public void setiRO(IncludeRouteObject iRO) {
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

	public String toString(){
		StringBuffer sb=new StringBuffer();
		if (requestParameters!=null){
			sb.append(requestParameters.toString());
		}
		if (endPoints!=null){
			sb.append(endPoints);
		}
		if (lSPA!=null){
			sb.append(lSPA);
		}
		if (bandwidth!=null){
			sb.append(bandwidth.toString());
		}
		if (metricList!=null){
			for (int i=0;i<metricList.size();++i){
				sb.append(metricList.get(i).toString());
			}
		}
		if (objectiveFunction!=null){
			sb.append(objectiveFunction.toString());
		}
		if (reservation!=null){
			sb.append(reservation.toString());
		}
		if (rROBandwidth!=null){
			sb.append(rROBandwidth.toString());
		}
		if (iRO!=null){
			sb.append(iRO.toString());
		}
		if (loadBalancing!=null){
			sb.append(loadBalancing.toString());
		}
		if (xro!=null){
			sb.append(xro.toString());
		}
		if (interLayer!=null){
			sb.append(interLayer.toString());
		}
		if (switchLayer!=null){
			sb.append(switchLayer.toString());
		}
		return sb.toString();
	}
	
	public Request duplicate(){
		Request req=new Request();
		req.setRequestParameters(this.requestParameters);
		req.setEndPoints(this.endPoints);
		req.setlSPA(this.lSPA);
		req.setBandwidth(this.bandwidth);
		req.setMetricList(this.metricList);
		req.setrROBandwidth(this.rROBandwidth);
		req.setiRO(this.iRO);
		req.setLoadBalancing(this.loadBalancing);
		req.setObjectiveFunction(this.objectiveFunction);
		req.setXro(this.xro);
		req.setInterLayer(this.interLayer);
		req.setSwitchLayer(this.switchLayer);
		req.setReservation(this.reservation);
		return req;
	}
	
	
	
}
