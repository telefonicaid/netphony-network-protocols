package tid.pce.pcep.constructs;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.objects.LSP;
import tid.pce.pcep.objects.MalformedPCEPObjectException;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;
import tid.pce.pcep.objects.SRP;

/*
 * Both PCRpt and PCUpd use a construct of the type 
 * <LSP>[<path-list>], <state-report> and <update-request>
 * Just to keep names correcty two classes that are completely equal but with
 * different names are created.
 * 
 *  <update-request> ::= <LSP>
                           [<path-list>]

    Where:

      <path-list>::=<path>[<path-list>]
    Where:
      <path> is defined in technology-specific documents per LSP type
      
      
      --------------------------------------------------------------------
      
      
      <state-report> ::= <LSP>
                      [<path-list>]
	
	Where:
	
	   <path-list>::=<path>[<path-list>]
	
	Where:
	     <path-list> is defined in [RFC5440] and extended by PCEP extensions.
	 * 
 */

public class LSPplusPath  extends PCEPConstruct
{

	//protected LinkedList<LSP> LSPList;
	LSP lsp;
	SRP rsp;
	protected Path path;
	
	public LSPplusPath()
	{
	}
	
	public LSPplusPath(byte []bytes, int offset)throws PCEPProtocolViolationException 
	{
		decode(bytes, offset);
	}
	
	
	
	public void encode()throws PCEPProtocolViolationException
	{
		log.info("en encode de LSPplusPath Construct");
		int length=0;

		rsp.encode();
		length += rsp.getLength();
		
		lsp.encode();
		length += lsp.getLength();
		
		path.encode();
		length += path.getLength();
		
		this.setLength(length);
		this.bytes = new byte[length];
		int offset = 0;
		
		System.arraycopy(rsp.getBytes(), 0, this.getBytes(), offset,rsp.getLength());
		offset += rsp.getLength();
		
		System.arraycopy(lsp.getBytes(), 0, this.getBytes(), offset,lsp.getLength());		
		offset += lsp.getLength();

		System.arraycopy(path.getBytes(), 0, bytes, offset, path.getLength());
		offset = offset+path.getLength();
		
		log.info("LSPplusPath Construct encoded!!");
	}

	protected void decode(byte[] bytes, int offset) throws PCEPProtocolViolationException
	{
		log.info("en decode de LSPplusPath Construct");
		int len=0;		
		int max_offset=bytes.length;
		if (offset>=max_offset)
		{
			
			throw new PCEPProtocolViolationException();
		}
		
		int oc=PCEPObject.getObjectClass(bytes, offset);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
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
			len=len+rsp.getLength();
		}
		else
		{
			log.warning("Malformed Report Message. There must be at least one RSP object. Exception will be throwed");
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
				log.warning("Malformed LSP Object found");
				throw new PCEPProtocolViolationException();
			}
			offset=offset+lsp.getLength();
			len=len+lsp.getLength();
		}
		else
		{
			log.warning("Malformed Report Message. There must be at least one LSP object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
		
		if (PCEPObject.getObjectClass(bytes, offset)==ObjectParameters.PCEP_OBJECT_CLASS_ERO)
		{
			log.finest("ERO Object found, New Path Construct found");
			path=new Path(bytes,offset);
			offset=offset+path.getLength();
			len=len+path.getLength();
			if (offset>=bytes.length){
				log.finest("No more bytes");
				this.setLength(len);
				return;
			}
		}
		else
		{
			log.warning("Malformed Report Message. There must be at least one ERO message!");
			throw new PCEPProtocolViolationException();
		}
	}
	

	public LSP getLSP() 
	{
		return lsp;
	}
	public void setLSP(LSP lsp) 
	{
		this.lsp = lsp;
	}
	
	public void setPath(Path path)
	{
		this.path = path;
	}
	public Path getPath()
	{
		return path;
	}
	public SRP getRSP() 
	{
		return rsp;
	}
	public void setRSP(SRP rsp)
	{
		this.rsp = rsp;
	}
	
}
