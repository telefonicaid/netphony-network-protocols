package tid.pce.pcep.messages;

import java.util.LinkedList;
import java.util.logging.Logger;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.PCEPIntiatedLSP;
import tid.pce.pcep.objects.ObjectParameters;
import tid.pce.pcep.objects.PCEPObject;

/**
 * <PCInitiate Message> ::= <Common Header>
                            <PCE-initiated-lsp-list>
Where:

   <PCE-initiated-lsp-list> ::= <PCE-initiated-lsp-request>[<PCE-initiated-lsp-list>]

   <PCE-initiated-lsp-request> ::= <SRP>
                                   <LSP>
                                   <END-POINTS>
                                   <ERO>
                                   [<attribute-list>]
 * 
 * @author jaume
 *
 */

public class PCEPInitiate extends PCEPMessage 
{

	protected LinkedList<PCEPIntiatedLSP> pcepIntiatedLSPList; 
	private Logger log = Logger.getLogger("PCEPParser");

	public PCEPInitiate()
	{
		this.setMessageType(PCEPMessageTypes.MESSAGE_INTIATE);
		pcepIntiatedLSPList = new LinkedList<PCEPIntiatedLSP>();
	}
	public PCEPInitiate(byte [] bytes)  throws PCEPProtocolViolationException
	{
		super(bytes);
		pcepIntiatedLSPList = new LinkedList<PCEPIntiatedLSP>();
		decode();

	}

	@Override
	public void encode() throws PCEPProtocolViolationException 
	{
		log.info("Inicio encode");
		int len = 4;
		int index = 0;

        while(index < pcepIntiatedLSPList.size())
        {
        	pcepIntiatedLSPList.get(index).encode();		
			len += pcepIntiatedLSPList.get(index).getLength();
			index++;			
        }
        
		if (pcepIntiatedLSPList.size() == 0)
		{
			log.warning("There should be at least one update request in a PCEP update Request message");
			throw new PCEPProtocolViolationException();
		}

		this.setMessageLength(len);
		messageBytes = new byte[len];
		log.info("Inicio encodeHeader");
		this.encodeHeader();
		int offset = 4;		//Header
		index=0;
		log.info("Inicio array copy");

		while(index < pcepIntiatedLSPList.size())
		{
			System.arraycopy(pcepIntiatedLSPList.get(index).getBytes(), 0, this.messageBytes, offset, pcepIntiatedLSPList.get(index).getLength());
			offset = offset + pcepIntiatedLSPList.get(index).getLength();
			index++;						
		}	
	}


	public void decode() throws PCEPProtocolViolationException
	{
		//Current implementation is strict, does not accept unknown objects 
		int offset = 4;//We start after the object header
		boolean atLeastOne = false;
		PCEPIntiatedLSP sr;
		log.info("Decoding PCEP Intiate!!:: len :: "+this.getBytes().length);

		//No LSP object. Malformed Update Request. PCERR mesage should be sent!
		log.info("Object Type::"+PCEPObject.getObjectClass(this.getBytes(), offset));
		if(PCEPObject.getObjectClass(this.getBytes(), offset)!=ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			log.info("There should be at least one RSP Object");
			throw new PCEPProtocolViolationException();
		}
		//It has to be at least one!
		while (PCEPObject.getObjectClass(this.getBytes(), offset)==ObjectParameters.PCEP_OBJECT_CLASS_RSP)
		{
			try
			{
				sr = new PCEPIntiatedLSP(this.getBytes(),offset);
			}
			catch(PCEPProtocolViolationException e)
			{
				log.warning("Malformed UpdateRequest Construct");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + sr.getLength();
			pcepIntiatedLSPList.add(sr);
			atLeastOne = true;
		}

		if (!atLeastOne)
		{
			log.warning("Malformed Report Message. There must be at least one state-report object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
		log.info("PCEP Report decoded!!");
	}
	public LinkedList<PCEPIntiatedLSP> getPcepIntiatedLSPList() 
	{
		return pcepIntiatedLSPList;
	}
	public void setPcepIntiatedLSPList(
			LinkedList<PCEPIntiatedLSP> pcepIntiatedLSPList) 
	{
		this.pcepIntiatedLSPList = pcepIntiatedLSPList;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer(pcepIntiatedLSPList.size()*100);
		sb.append("INITIATE MESSAGE: ");

		for (int i=0;i<pcepIntiatedLSPList.size();++i){
			sb.append(pcepIntiatedLSPList.get(i).toString());
		}
	
		return sb.toString();
	}


}