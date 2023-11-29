package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.Association;
import es.tid.pce.pcep.objects.AssociationIPv4;
import es.tid.pce.pcep.objects.AssociationIPv6;
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
 *      [<association-list>]
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
	
	/**
	 * Optional associationList
	 */
	private LinkedList<Association> associationList;
	
	public UpdateRequest(){
		super();
		associationList=new LinkedList<Association>();
		
	}

	
	public UpdateRequest(byte []bytes, int offset)throws PCEPProtocolViolationException 
	{
		associationList=new LinkedList<Association>();
		decode(bytes, offset);
	}
	
	
	
	public void encode()throws PCEPProtocolViolationException
	{
		int length=0;

		srp.encode();
		length += srp.getLength();
		
		if (lsp != null ) {
			lsp.encode();	
			length += lsp.getLength();
		}
		
		
		
		if (associationList != null) {
			for (int i = 0; i < associationList.size(); ++i) {
				(associationList.get(i)).encode();
				length = length + (associationList.get(i)).getLength();
			}
		}
		
		path.encode();
		length += path.getLength();
		
		this.setLength(length);
		this.bytes = new byte[length];
		int offset = 0;
		
		System.arraycopy(srp.getBytes(), 0, this.getBytes(), offset,srp.getLength());
		offset += srp.getLength();
		
		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset,lsp.getLength());		
		offset += lsp.getLength();
		
		for (int i = 0; i < associationList.size(); ++i) {
			System.arraycopy(associationList.get(i).getBytes(), 0, bytes, offset, associationList.get(i).getLength());
			offset = offset + associationList.get(i).getLength();
		}

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
		
		//Association
		
		oc = PCEPObject.getObjectClass(bytes, offset);
		int ot = PCEPObject.getObjectType(bytes, offset);
		log.debug("Checking ASSOCIATION");
		while (oc == ObjectParameters.PCEP_OBJECT_CLASS_ASSOCIATION) {
			Association aso=null;
			try {
				if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV4) {
					aso = new AssociationIPv4(bytes, offset);
				}else if(ot == ObjectParameters.PCEP_OBJECT_TYPE__ASSOCIATION_IPV6) {
					aso = new AssociationIPv6(bytes, offset);
				}
				
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed ASSOCIATION Object found");
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
			ot = PCEPObject.getObjectType(bytes, offset);
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


	public LinkedList<Association> getAssociationList() {
		return associationList;
	}


	public void setAssociationList(LinkedList<Association> associationList) {
		this.associationList = associationList;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((associationList == null) ? 0 : associationList.hashCode());
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
		if (associationList == null) {
			if (other.associationList != null)
				return false;
		} else if (!associationList.equals(other.associationList))
			return false;
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
		return "UpdateRequest [lsp=" + lsp + ", srp=" + srp + ", path=" + path + ", associationList=" + associationList
				+ "]";
	}


	
	
	
}
