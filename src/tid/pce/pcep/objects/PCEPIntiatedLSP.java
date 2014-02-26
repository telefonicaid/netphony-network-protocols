package tid.pce.pcep.objects;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.PCEPConstruct;
import tid.util.UtilsFunctions;

public class PCEPIntiatedLSP extends PCEPConstruct
{

	private SRP rsp;
	private LSP lsp;
	private ExplicitRouteObject ero;
	private SRERO srero;

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
		//super(bytes, offset);
		decode(bytes, offset);
	}

	@Override
	public void encode() throws PCEPProtocolViolationException 
	{
//		log.finest("Encoding PCEP Response Message");
//		int len = 4;
		int len = 0;
		
		rsp.encode();
		lsp.encode();
		len += rsp.getLength();
		len += lsp.getLength();

		if (ero!=null)
		{	
			ero.encode();
			len += ero.getLength();

			endPoint.encode();
			len += endPoint.getLength();
		}
		else if (srero!=null)
		{
			srero.encode();
			len += srero.getLength();
		}
		else
		{
			log.info("NO ERO or SRERO, finishing...");
			throw new PCEPProtocolViolationException();
		}

		if (bandwidth!=null){
			bandwidth.encode();
			len=len+bandwidth.getLength();
		}		
		this.setLength(len);

		this.bytes = new byte[len];

		log.info("Leeeength:::"+len);
		int offset=0;

		System.arraycopy(rsp.getBytes(), 0, this.getBytes(), offset, rsp.getLength());
		offset=offset + rsp.getLength();


		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset, lsp.getLength());
		offset=offset + lsp.getLength();

		if (ero!=null)
		{
			System.arraycopy(ero.getBytes(), 0, this.getBytes(), offset, ero.getLength());
			offset=offset + ero.getLength();


			System.arraycopy(endPoint.getBytes(), 0, this.getBytes(), offset, endPoint.getLength());
			offset=offset + endPoint.getLength();
		}
		else if (srero!=null)
		{
			System.arraycopy(srero.getBytes(), 0, this.getBytes(), offset, srero.getLength());
			log.info("SRERO leength:: "+srero.getLength());
			offset=offset + srero.getLength();			
		}


		if (bandwidth!=null){
			System.arraycopy(bandwidth.getBytes(), 0, bytes, offset, bandwidth.getLength());
			offset=offset+bandwidth.getLength();
		}
	}

	public void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException
	{
		int len=0;
		//Current implementation is strict, does not accept unknown objects 
		log.info("Decoding PCEP Intiate!!offset: "+offset+",");
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warning("Empty Request construct!!!");
			throw new PCEPProtocolViolationException();
		}
		//No LSP object. Malformed Update Request. PCERR mesage should be sent!
		log.info("Object Type::"+PCEPObject.getObjectClass(bytes, offset));
		if(PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			log.info("There should be at least one RSP Object");
			throw new PCEPProtocolViolationException();
		}

		try 
		{
			rsp = new SRP(bytes,offset);

		} 
		catch (MalformedPCEPObjectException e) 
		{
			log.warning("Malformed LSP Object found");
			throw new PCEPProtocolViolationException();
		}
		offset=offset+rsp.getLength();
		len += rsp.getLength();


		if(PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_LSP)
		{
			log.info("There should be at least one LSP Object");
			throw new PCEPProtocolViolationException();
		}

		try 
		{
			lsp = new LSP(bytes,offset);

		} 
		catch (MalformedPCEPObjectException e) 
		{
			log.warning("Malformed LSP Object found");
			throw new PCEPProtocolViolationException();
		}
		offset=offset+lsp.getLength();
		len += lsp.getLength();


		if(PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_ERO)
		{
			try 
			{
				ero = new ExplicitRouteObject(bytes,offset);

			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warning("Malformed ERO Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + ero.getLength();
			len += ero.getLength();


			if(PCEPObject.getObjectClass(bytes, offset)!=ObjectParameters.PCEP_OBJECT_CLASS_ENDPOINTS)
			{
				log.info("There should be at least one EndPoint Object");
				throw new PCEPProtocolViolationException();
			}

			try 
			{
				int ot=PCEPObject.getObjectType(bytes, offset);
				if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV4){
					try {
						endPoint=new EndPointsIPv4(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warning("Malformed ENDPOINTS IPV4 Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_UNNUMBERED){
					try {
						endPoint=new EndPointsUnnumberedIntf(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warning("Malformed ENDPOINTS Unnumbered Interface Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_MAC){
					try {
						endPoint=new XifiEndPoints(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warning("Malformed EndPointsMAC Object found");
						log.warning(UtilsFunctions.exceptionToString(e));
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_ENDPOINTS_IPV6){
					try {
						endPoint=new EndPointsIPv6(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warning("Malformed ENDPOINTSIPV6 Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else if (ot==ObjectParameters.PCEP_OBJECT_TYPE_GENERALIZED_ENDPOINTS){
					try {
						endPoint=new GeneralizedEndPoints(bytes,offset);
					} catch (MalformedPCEPObjectException e) {
						log.warning("Malformed GENERALIZED END POINTS Object found");
						throw new PCEPProtocolViolationException();
					}
				}
				else {
					log.warning("BANDWIDTH TYPE NOT SUPPORTED");
					throw new PCEPProtocolViolationException();
				}

			} 
			catch (Exception e) 
			{
				log.warning("Malformed EndPoint Object found");
				throw new PCEPProtocolViolationException();
			}

			offset = offset + endPoint.getLength();
			len += endPoint.getLength();
		}//ERO
		else if(PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO)
		{
			try 
			{
				srero = new SRERO(bytes,offset);

			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warning("Malformed srero Object found");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + srero.getLength();
			len += srero.getLength();						
		}
		else
		{
			log.info("There should be at least one ERO or SRERO Object");
			throw new PCEPProtocolViolationException();						
		}
	
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_BANDWIDTH){
			log.finest("BANDWIDTH Object found");
			try {
				bandwidth=new Bandwidth(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed BANDWIDTH Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+bandwidth.getLength();
			len=len+bandwidth.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}

		this.setLength(len);
	}

	public SRP getRsp() 
	{
		return rsp;
	}

	public void setRsp(SRP rsp) 
	{
		this.rsp = rsp;
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

	public SRERO getSrero() {
		return srero;
	}

	public void setSrero(SRERO srero) {
		this.srero = srero;
	}


}