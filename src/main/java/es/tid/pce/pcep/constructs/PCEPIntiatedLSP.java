package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Bandwidth;
import es.tid.pce.pcep.objects.BandwidthExistingLSP;
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
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.P2MPEndPointsDataPathID;
import es.tid.pce.pcep.objects.P2MPEndPointsIPv4;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.SRP;
import es.tid.pce.pcep.objects.XifiEndPoints;
import es.tid.pce.pcep.objects.XifiUniCastEndPoints;

/**
 *   <PCE-initiated-lsp-request> ::= <SRP>
                                   <LSP>
                                   <END-POINTS>
                                   <ERO>
                                   [<attribute-list>]
 * @author ogondio
 *
 */
public class PCEPIntiatedLSP extends PCEPConstruct
{

	private SRP srp;
	private LSP lsp;
	private ExplicitRouteObject ero;

	private EndPoints endPoint;
	private Bandwidth bandwidth;


	public Bandwidth getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Bandwidth bandwidth) {
		this.bandwidth = bandwidth;
	}

	public PCEPIntiatedLSP() 
	{
	}

	public PCEPIntiatedLSP(byte []bytes, int offset)throws PCEPProtocolViolationException 
	{
		decode(bytes, offset);
	}

	public void encode() throws PCEPProtocolViolationException 
	{

		int len = 0;

		srp.encode();
		lsp.encode();
		len += srp.getLength();
		len += lsp.getLength();


		if (endPoint!=null) {
			endPoint.encode();
			len += endPoint.getLength();
		}


		if (ero!=null)
		{	
			ero.encode();
			len += ero.getLength();

		}
		
		else
		{
			log.warn("NO ERO ...");
			// This is not a mistake. A PCE can receive an empty PCEPInitiate and that means it has to find
			// a path between the endpoints
			//throw new PCEPProtocolViolationException();
		}

		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}		
		this.setLength(len);

		this.bytes = new byte[len];
		int offset=0;

		System.arraycopy(srp.getBytes(), 0, this.getBytes(), offset, srp.getLength());
		offset=offset + srp.getLength();


		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset, lsp.getLength());
		offset=offset + lsp.getLength();

		if (endPoint!=null)
		{
			System.arraycopy(endPoint.getBytes(), 0, this.getBytes(), offset, endPoint.getLength());
			offset=offset + endPoint.getLength();
		}
		
		if (ero!=null)
		{
			System.arraycopy(ero.getBytes(), 0, this.getBytes(), offset, ero.getLength());
			offset=offset + ero.getLength();
		}
	
		if (bandwidth!=null)
		{
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
	}

	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException
	{
		int len=0;
		//Current implementation is strict, does not accept unknown objects 
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warn("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}
		//No LSP object. Malformed Update Request. PCERR mesage should be sent!
		if (PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_SRP) {
			log.info("There should be at least one SRP Object");
			throw new PCEPProtocolViolationException();
		} else {
			try 
			{
				srp = new SRP(bytes,offset);

			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warn("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+srp.getLength();
			len += srp.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				log.warn("Just one SRP object found, no more");
				throw new PCEPProtocolViolationException();
			}
		}

		if (PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_LSP) {
			log.warn("There should be at least one LSP Object");
			throw new PCEPProtocolViolationException();
		} else {
			try 
			{
				lsp = new LSP(bytes,offset);

			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warn("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lsp.getLength();
			len += lsp.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				log.warn("Just one SRP and one LSP object found, no more");
				log.warn("TEMPORAL FIX");
				return;
				//throw new PCEPProtocolViolationException();
			}
		}

		if (PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS) {
			log.warn("There should be at least one EndPoint Object. Should throw Ex");
			//throw new PCEPProtocolViolationException(); //Optional
		} else {
			try 
			{
				int ot=PCEPObject.getObjectType(bytes, offset);

				if (ot==ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_DATAPATHID){
					try {
						endPoint=new P2MPEndPointsDataPathID(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed P2MP ENDPOINTS DataPathID Object found");
						throw new PCEPProtocolViolationException();
					}
				}else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_P2MP_ENDPOINTS_IPV4){
					try {
						endPoint=new P2MPEndPointsIPv4(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed P2MP ENDPOINTS IPV4 Object found");
						throw new PCEPProtocolViolationException();
					}
				}	
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
					try {
						endPoint=new EndPointsIPv4(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTS IPV4 Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED){
					try {
						endPoint=new EndPointsUnnumberedIntf(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTS Unnumbered Interface Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC){
					try {
						endPoint=new XifiUniCastEndPoints(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed EndPointsMAC Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC_NOT_UNICAST){
					try {
						endPoint=new XifiEndPoints(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed EndPointsMAC Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
					try {
						endPoint=new EndPointsIPv6(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed ENDPOINTSIPV6 Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS){
					try {
						endPoint=new GeneralizedEndPoints(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed GENERALIZED END POINTS Object found");
						throw new PCEPProtocolViolationException();
					}
				}

				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_DATAPATH_ID){
					try {
						endPoint=new EndPointDataPathID(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warn("Malformed GENERALIZED END POINTS Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else {
					log.warn("BANDWIDTH TYPE NOT SUPPORTED");
					throw new PCEPProtocolViolationException();
				}
			} 
			catch (Exception e) 
			{
				log.warn("Malformed EndPoint Object found");
				//throw new PCEPProtocolViolationException();
			}
			if (endPoint!=null)
			{
				offset = offset + endPoint.getLength();
				len += endPoint.getLength();
				if (offset>=max_offset){
					this.setLength(len);
					//In draft, ERO is mandatory... here we relax the draft
					log.warn("Just one SRP, one LSP and one END-POINTS, object found, no more");
					//throw new PCEPProtocolViolationException();
				}
			}
		}

		if(PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_ERO)
		{
			try 
			{
				ero = new ExplicitRouteObject(bytes,offset);

			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warn("Malformed ERO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + ero.getLength();
			len += ero.getLength();
			
		}
		
	

		int oc=PCEPObject.getObjectClass(bytes, offset);
		int ot=PCEPObject.getObjectType(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_REQUEST){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}			
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthExistingLSP(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_REQUEST){
				try {
					bandwidth=new BandwidthRequestedGeneralizedBandwidth(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP){
				try {
					bandwidth=new BandwidthRequested(bytes, offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Malformed BANDWIDTH Object found");
					throw new PCEPProtocolViolationException();
				}		
				
			} else {
				log.warn("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
		}
		this.setLength(len);
	}

	//FIXME: REMOVE
	public SRP getRsp() 
	{
		return srp;
	}

	//FIXME: REMOVE
	public void setRsp(SRP rsp) 
	{
		this.srp = rsp;
	}
	
	public SRP getSrp() {
		return srp;
	}

	public void setSrp(SRP srp) {
		this.srp = srp;
	}

	public LSP getLsp() 
	{
		return lsp;
	}

	public void setLsp(LSP lsp)
	{
		this.lsp = lsp;
	}

	public ExplicitRouteObject getEro()
	{
		return ero;
	}

	public void setEro(ExplicitRouteObject ero) 
	{
		this.ero = ero;
	}

	public EndPoints getEndPoint() 
	{
		return endPoint;
	}

	public void setEndPoint(EndPoints endPoint) 
	{
		this.endPoint = endPoint;
	}

	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("Initiated LSP: ");
		if (srp!=null){
			sb.append(srp.toString());
		}
		if (lsp!=null){
			sb.append(lsp.toString());
		}
		if (ero!=null){
			sb.append(ero.toString());
		}
		if (endPoint!=null){
			sb.append(endPoint.toString());
		}
		
	
		
		if (bandwidth!=null){
			sb.append(bandwidth.toString());
		}		
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((bandwidth == null) ? 0 : bandwidth.hashCode());
		result = prime * result
				+ ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((ero == null) ? 0 : ero.hashCode());
		result = prime * result + ((lsp == null) ? 0 : lsp.hashCode());
		result = prime * result + ((srp == null) ? 0 : srp.hashCode());
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
		if (bandwidth == null) {
			if (other.bandwidth != null)
				return false;
		} else if (!bandwidth.equals(other.bandwidth))
			return false;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (ero == null) {
			if (other.ero != null)
				return false;
		} else if (!ero.equals(other.ero))
			return false;
		if (lsp == null) {
			if (other.lsp != null)
				return false;
		} else if (!lsp.equals(other.lsp))
			return false;
	
		if (srp == null) {
			if (other.srp != null)
				return false;
		} else if (!srp.equals(other.srp))
			return false;
		return true;
	}
	
	

}
