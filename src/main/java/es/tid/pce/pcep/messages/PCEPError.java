package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.ErrorConstruct;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.OPEN;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPErrorObject;
import es.tid.pce.pcep.objects.PCEPObject;

/**
 * <h1> PCEP Error Message (RFC 5440). </h1>
 * <p>From RFC 5440 Section 6.7. Error (PCErr) Message</p>
   <p>The PCEP Error message (also referred to as a PCErr message) is sent
   in several situations: when a protocol error condition is met or when
   the request is not compliant with the PCEP specification (e.g.,
   reception of a malformed message, reception of a message with a
   mandatory missing object, policy violation, unexpected message,
   unknown request reference).  The Message-Type field of the PCEP
   common header for the PCErr message is set to 6.</p>

   <p>The PCErr message is sent by a PCC or a PCE in response to a request
   or in an unsolicited manner.  If the PCErr message is sent in
   response to a request, the PCErr message MUST include the set of RP
   objects related to the pending path computation requests that
   triggered the error condition.  In the latter case (unsolicited), no
   RP object is inserted in the PCErr message.  For example, no RP
   object is inserted in a PCErr when the error condition occurred
   during the initialization phase.  A PCErr message MUST contain a
   PCEP-ERROR object specifying the PCEP error condition.  The PCEP-
   ERROR object is defined in Section 7.15.</p>

   <p>The format of a PCErr message is as follows:</p>

   <PCErr Message> ::= <Common Header>
                       ( <error-obj-list> [<Open>] ) | <error>
                       [<error-list>]

   <error-obj-list>::=<PCEP-ERROR>[<error-obj-list>]

   <error>::=[<request-id-list>]
              <error-obj-list>

   <request-id-list>::=<RP>[<request-id-list>]
   <error-list>::=<error>[<error-list>] 
 *
 * @author Oscar Gonzalez de Dios
 *
 */
//
public class PCEPError extends PCEPMessage {
	
	/**
	 * 
	 */
	private LinkedList<PCEPErrorObject> errorObjList;
	/**
	 * 
	 */
	private OPEN open;
	/*
	 * 
	 */
	private ErrorConstruct error;
	/**
	 * 
	 */
	private LinkedList<ErrorConstruct> errorList;
	
	
	
	
	
	public PCEPError(){
		this.setMessageType(PCEPMessageTypes.MESSAGE_ERROR);
		errorObjList=new LinkedList<PCEPErrorObject>();
		errorList=new LinkedList<ErrorConstruct>();
	}
	
	public PCEPError(byte[] bytes) throws PCEPProtocolViolationException {
		super(bytes);
		errorObjList=new LinkedList<PCEPErrorObject>();
		errorList=new LinkedList<ErrorConstruct>();
		this.decode();
	}
	/**
	 * Encode the PCEP Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		if  ((errorObjList.size()==0)&&(error==null)){
			/*
			 * <PCErr Message> ::= <Common Header>
                       ( <error-obj-list> [<Open>] ) | <error>
                       [<error-list>]
			 */
			
			throw new PCEPProtocolViolationException();
		}
		int len=4;
		if (error!=null){
			error.encode();
			len=len+error.getLength();
		}
		else {
			//Either error... or the errorobjlist and an open. BOTH is not possible
			for (int i=0;i<errorObjList.size();++i){
				(errorObjList.get(i)).encode();
				len=len+(errorObjList.get(i)).getLength();
			}
			if (open!=null){
				open.encode();
				len=len+open.getLength();
			}
		}
		
		for (int i=0;i<errorList.size();++i){
			(errorList.get(i)).encode();
			len=len+(errorList.get(i)).getLength();
		}
		this.setMessageLength(len);
		this.messageBytes=new byte[this.getLength()];
		encodeHeader();
		int offset=4;
		if (error!=null){
			System.arraycopy(error.getBytes(), 0, this.messageBytes, offset, error.getLength());
			offset=offset+error.getLength();
		}
		for (int i=0;i<errorObjList.size();++i){
			System.arraycopy(errorObjList.get(i).getBytes(), 0, this.messageBytes, offset, errorObjList.get(i).getLength());
			offset=offset+errorObjList.get(i).getLength();
		}
		if (errorObjList.size()>0){
			if (open!=null){
				System.arraycopy(open.getBytes(), 0, this.messageBytes, offset, open.getLength());
				offset=offset+open.getLength();
			}
		}		
	}

	
	private void decode() throws PCEPProtocolViolationException {
		//Decoding PCEP Error Message"		
		int offset=4;//We start after the object header
		if (offset>=this.getLength()){
			log.warn("Empty Error message");
			throw new PCEPProtocolViolationException();
		}
		int oc=PCEPObject.getObjectClass(this.messageBytes, 4);
		if (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			//If the first thing is an error construct, then, we have to fill the Error list
			while ((oc==ObjectParameters.PCEP_OBJECT_CLASS_RP)||(oc==ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR))  {
				ErrorConstruct errc;
				try {
					errc = new ErrorConstruct(this.messageBytes,offset);
				} catch (PCEPProtocolViolationException e) {
					log.warn("Problem Decoding Error Construct");
					throw new PCEPProtocolViolationException();
				}
				errorList.add(errc);
				offset=offset+errc.getLength();
				if (offset>=this.getLength()){
					return;
				}				
				oc=PCEPObject.getObjectClass(this.messageBytes, offset);
			}
		}
		else if (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR){
			while (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR){
				PCEPErrorObject perrobj;
				try {
					perrobj=new PCEPErrorObject(this.messageBytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Problem Decoding Error Object");
					e.printStackTrace();
					throw new PCEPProtocolViolationException();
				}
				errorObjList.add(perrobj);
				offset=offset+perrobj.getLength();
				if (offset>=this.getLength()){
					return;
				}				
				oc=PCEPObject.getObjectClass(this.messageBytes, offset);
			}			
			if (oc==ObjectParameters.PCEP_OBJECT_CLASS_OPEN){				
				try {
					open=new OPEN(this.messageBytes,offset);
				} catch (MalformedPCEPObjectException e) {
					log.warn("Problem Decoding Error Object");
					e.printStackTrace();
					throw new PCEPProtocolViolationException();
				}
				offset=offset+open.getLength();
				if (offset>=this.getLength()){
					return;
				}				
				oc=PCEPObject.getObjectClass(this.messageBytes, offset);
			}
			while ((oc==ObjectParameters.PCEP_OBJECT_CLASS_RP)||(oc==ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR))  {
					ErrorConstruct errc;
					try {
						errc = new ErrorConstruct(this.messageBytes,offset);
					} catch (PCEPProtocolViolationException e) {
						log.warn("Problem Decoding Error Construct");
						throw new PCEPProtocolViolationException();
					}
					errorList.add(errc);
					offset=offset+errc.getLength();
					if (offset>=this.getLength()){
						return;
					}				
					oc=PCEPObject.getObjectClass(this.messageBytes, offset);
				}
			}
	}

	public LinkedList<PCEPErrorObject> getErrorObjList() {
		return errorObjList;
	}

	public void setErrorObjList(LinkedList<PCEPErrorObject> errorObjList) {
		this.errorObjList = errorObjList;
	}

	public OPEN getOpen() {
		return open;
	}

	public void setOpen(OPEN open) {
		this.open = open;
	}

	public LinkedList<ErrorConstruct> getErrorList() {
		return errorList;
	}

	public void setErrorList(LinkedList<ErrorConstruct> errorList) {
		this.errorList = errorList;
	}

	public ErrorConstruct getError() {
		return error;
	}

	public void setError(ErrorConstruct error) {
		this.error = error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result
				+ ((errorList == null) ? 0 : errorList.hashCode());
		result = prime * result
				+ ((errorObjList == null) ? 0 : errorObjList.hashCode());
		result = prime * result + ((open == null) ? 0 : open.hashCode());
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
		PCEPError other = (PCEPError) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (errorList == null) {
			if (other.errorList != null)
				return false;
		} else if (!errorList.equals(other.errorList))
			return false;
		if (errorObjList == null) {
			if (other.errorObjList != null)
				return false;
		} else if (!errorObjList.equals(other.errorObjList))
			return false;
		if (open == null) {
			if (other.open != null)
				return false;
		} else if (!open.equals(other.open))
			return false;
		return true;
	}
	
	
}
