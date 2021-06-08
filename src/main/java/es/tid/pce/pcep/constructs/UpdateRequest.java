package es.tid.pce.pcep.constructs;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.LSP;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.SRP;


/**
 * Update Request Construct
 * {@code
 * <update-request>::=
 *      <SRP>
 *      <LSP>
 *      <path>
 * }
 * @author Fernando Munoz del Nuevo
 * @author ogondio
 *
 */

public class UpdateRequest extends PCEPConstruct
{
	private LSP lsp;
	private SRP srp;
	private Path path;
	
	public UpdateRequest(){
		super();
		
	}

	
	public UpdateRequest(byte []bytes, int offset)throws PCEPProtocolViolationException 
	{
		decode(bytes, offset);
	}
	
	
	
	public void encode()throws PCEPProtocolViolationException
	{
		int length=0;

		srp.encode();
		length += srp.getLength();
		
		lsp.encode();
		length += lsp.getLength();
		
		path.encode();
		length += path.getLength();
		
		this.setLength(length);
		this.bytes = new byte[length];
		int offset = 0;
		
		System.arraycopy(srp.getBytes(), 0, this.getBytes(), offset,srp.getLength());
		offset += srp.getLength();
		
		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset,lsp.getLength());		
		offset += lsp.getLength();

		System.arraycopy(path.getBytes(), 0, bytes, offset, path.getLength());
		offset = offset+path.getLength();
		
	}

	protected void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException
	{
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset)
		{
			
			throw new PCEPProtocolViolationException();
		}
		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_SRP)
		{
			try 
			{
				srp = new SRP(bytes,offset);
				
			} 
			catch (MalformedPCEPObjectException e) 
			{
				log.warn("Malformed SRP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+srp.getLength();
			len=len+srp.getLength();
		}
		else
		{
			log.warn("Malformed Update Request Construct. There must be at least one SRP object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
		
		
		oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_LSP)
		{
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
			len=len+lsp.getLength();
		}
		else
		{
			log.warn("Malformed Update Request Construct. There must be at least one LSP object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
		
		if (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_ERO)
		{
			path=new Path(bytes,offset);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}
		}
		else if (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_SR_ERO)
		{
			path=new Path(bytes,offset);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				this.setLength(len);
				return;
			}			
		}
		else
		{
			log.warn("Malformed Update Request Construct.");
			throw new PCEPProtocolViolationException();
		}
		this.setLength(len);
	}


	public LSP getLsp() {
		return lsp;
	}


	public void setLsp(LSP lsp) {
		this.lsp = lsp;
	}


	public SRP getSrp() {
		return srp;
	}


	public void setSrp(SRP srp) {
		this.srp = srp;
	}


	public Path getPath() {
		return path;
	}


	public void setPath(Path path) {
		this.path = path;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((lsp == null) ? 0 : lsp.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		UpdateRequest other = (UpdateRequest) obj;
		if (lsp == null) {
			if (other.lsp != null)
				return false;
		} else if (!lsp.equals(other.lsp))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (srp == null) {
			if (other.srp != null)
				return false;
		} else if (!srp.equals(other.srp))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "UpdateRequest [lsp=" + lsp + ", srp=" + srp + ", path=" + path + "]";
	}
	
	
	
	
	
}
