package es.tid.pce.pcep.constructs;

import java.util.LinkedList;
import java.util.Objects;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Association;
import es.tid.pce.pcep.objects.AssociationIPv4;
import es.tid.pce.pcep.objects.AssociationIPv6;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
import es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth;
import es.tid.pce.pcep.objects.EndPointDataPathID;
import es.tid.pce.pcep.objects.EndPoints;
import es.tid.pce.pcep.objects.EndPointsIPv4;
import es.tid.pce.pcep.objects.EndPointsIPv6;
import es.tid.pce.pcep.objects.EndPointsUnnumberedIntf;
import es.tid.pce.pcep.objects.ExplicitRouteObject;
import es.tid.pce.pcep.objects.GeneralizedEndPoints;
import es.tid.pce.pcep.objects.LSP;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Metric;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.P2MPEndPointsIPv4;
import es.tid.pce.pcep.objects.P2PGeneralizedEndPoints;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.SRP;

/**
 * PCEP Initiated LSP. {@code
 *   <PCE-initiated-lsp-request> ::= <SRP>
                                   <LSP>
                                   <END-POINTS>
                                   <ERO>
                                   [<association-list>]
                                   [<attribute-list>]
                                   
<association-list> ::= <ASSOCIATION> [<association-list>]

<attribute-list>::=[<LSPA>]
                       [<BANDWIDTH>]
                       [<metric-list>]
                       [<IRO>]                                   
                                   
                                   }
 * 
 * @author ogondio
 *
 */
public class PCEPIntiatedLSP extends PCEPConstruct {

	private SRP srp;
	private LSP lsp;
	
	private ExplicitRouteObject ero;

	private EndPoints endPoint;
	
	private LinkedList<Association> associationList;

	/**
	 * Metric List
	 */
	private LinkedList<Metric> metricList;

	private Bandwidth bandwidth;

	public Bandwidth getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}

	public PCEPIntiatedLSP() {
		metricList=new LinkedList<Metric>();
		associationList=new LinkedList<Association>();
	}

	public PCEPIntiatedLSP(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		metricList=new LinkedList<Metric>();
		associationList=new LinkedList<Association>();
		decode(bytes, offset);
	}

	public void encode() throws PCEPProtocolViolationException {

		int len = 0;

		srp.encode();
		lsp.encode();
		len += srp.getLength();
		len += lsp.getLength();

		if (endPoint != null) {
			endPoint.encode();
			len += endPoint.getLength();
		}

		if (ero != null) {
			ero.encode();
			len += ero.getLength();

		}

		else {
			log.warn("NO ERO ...");
			// This is not a mistake. A PCE can receive an empty PCEPInitiate and that means
			// it has to find
			// a path between the endpoints
			// throw new PCEPProtocolViolationException();
		}
		if (associationList != null) {
			for (int i = 0; i < associationList.size(); ++i) {
				(associationList.get(i)).encode();
				len = len + (associationList.get(i)).getLength();
			}
		}

		if (bandwidth != null) {
			bandwidth.encode();
			len = len + bandwidth.getLength();
		}
		if (metricList != null) {
			for (int i = 0; i < metricList.size(); ++i) {
				(metricList.get(i)).encode();
				len = len + (metricList.get(i)).getLength();
			}
		}

		this.setLength(len);

		this.bytes = new byte[len];
		int offset = 0;
		System.arraycopy(srp.getBytes(), 0, this.getBytes(), offset, srp.getLength());
		offset = offset + srp.getLength();
		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset, lsp.getLength());
		offset = offset + lsp.getLength();
		if (endPoint != null) {
			System.arraycopy(endPoint.getBytes(), 0, this.getBytes(), offset, endPoint.getLength());
			offset = offset + endPoint.getLength();
		}
		System.out.println("offset "+offset);
		if (ero != null) {
			System.arraycopy(ero.getBytes(), 0, this.getBytes(), offset, ero.getLength());
			offset = offset + ero.getLength();
		}
		for (int i = 0; i < associationList.size(); ++i) {
			System.arraycopy(associationList.get(i).getBytes(), 0, bytes, offset, associationList.get(i).getLength());
			offset = offset + associationList.get(i).getLength();
		}
		if (bandwidth != null) {
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset = offset + bandwidth.getLength();
		}
		for (int i = 0; i < metricList.size(); ++i) {
			System.arraycopy(metricList.get(i).getBytes(), 0, bytes, offset, metricList.get(i).getLength());
			offset = offset + metricList.get(i).getLength();
		}

	}

	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException {
		int len = 0;
		// Current implementation is strict, does not accept unknown objects
		int max_offset = bytes.length;
		if (offset >= max_offset) {
			log.warn("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}
		// SRP
		// No SRP object. Malformed Update Request. PCERR mesage should be sent!
		int oc = PCEPObject.getObjectClass(bytes, offset);
		int ot = PCEPObject.getObjectType(bytes, offset);
		if (oc != ObjectParameters.PCEP_OBJECT_CLASS_SRP) {
			log.info("There should be at least one SRP Object");
			throw new PCEPProtocolViolationException();
		} else {
			try {
				srp = new SRP(bytes, offset);

			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + srp.getLength();
			len += srp.getLength();
			if (offset >= max_offset) {
				this.setLength(len);
				log.warn("Just one SRP object found, no more");
				throw new PCEPProtocolViolationException();
			}
		}
		oc = PCEPObject.getObjectClass(bytes, offset);
		ot = PCEPObject.getObjectType(bytes, offset);
		if (oc != ObjectParameters.PCEP_OBJECT_CLASS_LSP) {
			log.warn("There should be at least one LSP Object");
			throw new PCEPProtocolViolationException();
		} else {
			try {
				lsp = new LSP(bytes, offset);

			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + lsp.getLength();
			len += lsp.getLength();
			if (offset >= max_offset) {
				this.setLength(len);
				log.warn("Just one SRP and one LSP object found, no more");
				log.warn("TEMPORAL FIX");
				return;
				// throw new PCEPProtocolViolationException();
			}
		}
		oc = PCEPObject.getObjectClass(bytes, offset);
		ot = PCEPObject.getObjectType(bytes, offset);
		if (oc != ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS) {
			log.warn("There should be at least one EndPoint Object. Should throw Ex");
			// throw new PCEPProtocolViolationException(); //Optional
		} else {
			try {
				if (ot == ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_IPV4) {
					try {
						endPoint = new P2MPEndPointsIPv4(bytes, offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed P2MP ENDPOINTS IPV4 Object found");
						throw new PCEPProtocolViolationException();
					}
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4) {
					try {
						endPoint = new EndPointsIPv4(bytes, offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTS IPV4 Object found");
						throw new PCEPProtocolViolationException();
					}
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED) {
					try {
						endPoint = new EndPointsUnnumberedIntf(bytes, offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTS Unnumbered Interface Object found");
						throw new PCEPProtocolViolationException();
					}
				}

				else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6) {
					try {
						endPoint = new EndPointsIPv6(bytes, offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTSIPV6 Object found");
						throw new PCEPProtocolViolationException();
					}
				} else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS) {
					try {
						int endPointType = GeneralizedEndPoints.getGeneralizedEndPointsType(bytes, offset);
						if (endPointType == 1) {
							endPoint = new P2PGeneralizedEndPoints(bytes, offset);
						}

					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed GENERALIZED END POINTS Object found");
						throw new PCEPProtocolViolationException();
					}
				}

				else if (ot == ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_DATAPATH_ID) {
					try {
						endPoint = new EndPointDataPathID(bytes, offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed GENERALIZED END POINTS Object found");
						throw new PCEPProtocolViolationException();
					}
				} else {
					log.warn("BANDWIDTH TYPE NOT SUPPORTED");
					throw new PCEPProtocolViolationException();
				}
			} catch (Exception e) {
				log.warn("Malformed EndPoint Object found");
				// throw new PCEPProtocolViolationException();
			}
			if (endPoint != null) {
				offset = offset + endPoint.getLength();
				len += endPoint.getLength();
				if (offset >= max_offset) {
					this.setLength(len);
					// In draft, ERO is mandatory... here we relax the draft
					log.warn("Just one SRP, one LSP and one END-POINTS, object found, no more");
					// throw new PCEPProtocolViolationException();
				}
			}
		}

		if (PCEPObject.getObjectClass(bytes, offset) == ObjectParameters.PCEP_OBJECT_CLASS_ERO) {
			try {
				ero = new ExplicitRouteObject(bytes, offset);

			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed ERO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + ero.getLength();
			len += ero.getLength();

		}
		//Association
		oc = PCEPObject.getObjectClass(bytes, offset);
		ot = PCEPObject.getObjectType(bytes, offset);
		while (oc == ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION) {
			Association aso=null;
			try {
				if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV4) {
					aso = new AssociationIPv4(bytes, offset);
				}else if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV6) {
					aso = new AssociationIPv6(bytes, offset);
				}
				
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			associationList.add(aso);
			offset = offset + aso.getLength();
			len = len + aso.getLength();
			if (offset >= bytes.length) {
				this.setLength(len);
				return;
			}
			oc = PCEPObject.getObjectClass(bytes, offset);
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
		// Metric
		oc = PCEPObject.getObjectClass(bytes, offset);

		while (oc == ObjectParameters.PCEP_OBJECT_CLASS_METRIC) {
			Metric metric;
			try {
				metric = new Metric(bytes, offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			metricList.add(metric);
			offset = offset + metric.getLength();
			len = len + metric.getLength();
			if (offset >= bytes.length) {
				this.setLength(len);
				return;
			}
			oc = PCEPObject.getObjectClass(bytes, offset);
		}
		this.setLength(len);
	}

	// FIXME: REMOVE
	public SRP getRsp() {
		return srp;
	}

	// FIXME: REMOVE
	public void setRsp(SRP rsp) {
		this.srp = rsp;
	}

	public SRP getSrp() {
		return srp;
	}

	public void setSrp(SRP srp) {
		this.srp = srp;
	}

	public LSP getLsp() {
		return lsp;
	}

	public void setLsp(LSP lsp) {
		this.lsp = lsp;
	}

	public ExplicitRouteObject getEro() {
		return ero;
	}

	public void setEro(ExplicitRouteObject ero) {
		this.ero = ero;
	}

	public EndPoints getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(EndPoints endPoint) {
		this.endPoint = endPoint;
	}

	public LinkedList<Association> getAssociationList() {
		return associationList;
	}

	public void setAssociationList(LinkedList<Association> associationList) {
		this.associationList = associationList;
	}

	public LinkedList<Metric> getMetricList() {
		return metricList;
	}

	public void setMetricList(LinkedList<Metric> metricList) {
		this.metricList = metricList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(associationList, bandwidth, endPoint, ero, lsp, metricList, srp);
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
		PCEPIntiatedLSP other = (PCEPIntiatedLSP) obj;
		return Objects.equals(associationList, other.associationList) && Objects.equals(bandwidth, other.bandwidth)
				&& Objects.equals(endPoint, other.endPoint) && Objects.equals(ero, other.ero)
				&& Objects.equals(lsp, other.lsp) && Objects.equals(metricList, other.metricList)
				&& Objects.equals(srp, other.srp);
	}

	@Override
	public String toString() {
		return "PCEPIntiatedLSP [srp=" + srp + ", lsp=" + lsp + ", ero=" + ero + ", endPoint=" + endPoint
				+ ", associationList=" + associationList + ", metricList=" + metricList + ", bandwidth=" + bandwidth
				+ "]";
	}

	

}
