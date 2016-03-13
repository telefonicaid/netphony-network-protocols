package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import org.slf4j.Logger;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.PCEPIntiatedLSP;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

import org.slf4j.LoggerFactory;

/**
 * {@code
 * <PCInitiate Message> ::= <Common Header>
                            <PCE-initiated-lsp-list>
Where:

   <PCE-initiated-lsp-list> ::= <PCE-initiated-lsp-request>[<PCE-initiated-lsp-list>]

   <PCE-initiated-lsp-request> ::= <SRP>
                                   <LSP>
                                   <END-POINTS>
                                   <ERO>
                                   [<attribute-list>]
                                   }
 * 
 * @author jaume
 *
 */

public class PCEPInitiate extends PCEPMessage 
{

	protected LinkedList<PCEPIntiatedLSP> pcepIntiatedLSPList; 
	private static final Logger log = LoggerFactory.getLogger("PCEPParser");

	public PCEPInitiate()
	{
		this.setMessageType(PCEPMessageTypes.MESSAGE_INITIATE);
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
			log.warn("There should be at least one update request in a PCEP update Request message");
			throw new PCEPProtocolViolationException();
		}

		this.setMessageLength(len);
		messageBytes = new byte[len];
		this.encodeHeader();
		int offset = 4;		//Header
		index=0;

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
		if(PCEPObject.getObjectClass(this.getBytes(), offset)!=ObjectParameters.PCEP_OBJECT_CLASS_SRP)
		{
			log.warn("There should be at least one RSP Object");
			throw new PCEPProtocolViolationException();
		}
		//It has to be at least one!
		while (PCEPObject.getObjectClass(this.getBytes(), offset)==ObjectParameters.PCEP_OBJECT_CLASS_SRP)
		{
			try
			{
				log.info("this.getBytes(): "+this.getBytes());
				log.info("offset: "+offset);
				sr = new PCEPIntiatedLSP(this.getBytes(),offset);
				
			}
			catch(PCEPProtocolViolationException e)
			{
				log.warn("Malformed UpdateRequest Construct");
				throw new PCEPProtocolViolationException();
			}
			offset = offset + sr.getLength();
			pcepIntiatedLSPList.add(sr);
			atLeastOne = true;
		}

		if (!atLeastOne)
		{
			log.warn("Malformed Report Message. There must be at least one state-report object. Exception will be throwed");
			throw new PCEPProtocolViolationException();
		}
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((pcepIntiatedLSPList == null) ? 0 : pcepIntiatedLSPList
						.hashCode());
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
		PCEPInitiate other = (PCEPInitiate) obj;
		if (pcepIntiatedLSPList == null) {
			if (other.pcepIntiatedLSPList != null)
				return false;
		} else if (!pcepIntiatedLSPList.equals(other.pcepIntiatedLSPList))
			return false;
		return true;
	}

	

}