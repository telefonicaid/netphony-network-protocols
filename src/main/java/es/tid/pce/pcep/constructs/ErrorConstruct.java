package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPErrorObject;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.RequestParameters;

/**
 * Error Construct
 * {@code 
 *  <error>::=[<request-id-list>]
              <error-obj-list>}
 */
public class ErrorConstruct extends PCEPConstruct {
	
	/**
	 * Optional Request Id List
	 */
	private LinkedList<RequestParameters> requestIdList;
	
	/**
	 * Error Object List (compulsory)
	 */
	private LinkedList<PCEPErrorObject> errorObjList;
	
	/**
	 * Default constructor
	 */
	public ErrorConstruct(){
		requestIdList=new LinkedList<RequestParameters> ();
		errorObjList=new LinkedList<PCEPErrorObject>();
	}
	
	public ErrorConstruct(byte []bytes, int offset)throws PCEPProtocolViolationException{
		requestIdList=new LinkedList<RequestParameters> ();
		errorObjList=new LinkedList<PCEPErrorObject>();
		decode(bytes,offset);
	}	
	
	/**
	 * Encode the Error Construct
	 */
	public void encode() throws PCEPProtocolViolationException {
		int len=0;
		
		for (int i=0;i<requestIdList.size();++i){
			(requestIdList.get(i)).encode();
			len=len+(requestIdList.get(i)).getLength();
		}
		for (int i=0;i<errorObjList.size();++i){
			(errorObjList.get(i)).encode();
			len=len+(errorObjList.get(i)).getLength();
		}
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		for (int i=0;i<requestIdList.size();++i){
			System.arraycopy(requestIdList.get(i).getBytes(), 0, bytes, offset, requestIdList.get(i).getLength());
			offset=offset+requestIdList.get(i).getLength();
		}
		for (int i=0;i<errorObjList.size();++i){
			System.arraycopy(errorObjList.get(i).getBytes(), 0, bytes, offset, errorObjList.get(i).getLength());
			offset=offset+errorObjList.get(i).getLength();
		}
	}

	/**
	 * Decode the error construct
	 */
	private void decode(byte[] bytes, int offset)
			throws PCEPProtocolViolationException {
		int len=0;	
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warn("Empty Notify construct!!!");
			throw new PCEPProtocolViolationException();
		}
		int oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			RequestParameters rp;
			try {
				rp = new RequestParameters(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			requestIdList.add(rp);
			offset=offset+rp.getLength();
			len=len+rp.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR){
			PCEPErrorObject perror;
			try {
				perror = new PCEPErrorObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed METRIC Object found");
				throw new PCEPProtocolViolationException();
			}
			errorObjList.add(perror);
			offset=offset+perror.getLength();
			len=len+perror.getLength();
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
			oc=PCEPObject.getObjectClass(bytes, offset);
		}
		this.setLength(len);
		
	}

	public LinkedList<RequestParameters> getRequestIdList() {
		return requestIdList;
	}

	public void setRequestIdList(LinkedList<RequestParameters> requestIdList) {
		this.requestIdList = requestIdList;
	}

	public LinkedList<PCEPErrorObject> getErrorObjList() {
		return errorObjList;
	}

	public void setErrorObjList(LinkedList<PCEPErrorObject> errorObjList) {
		this.errorObjList = errorObjList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((errorObjList == null) ? 0 : errorObjList.hashCode());
		result = prime * result
				+ ((requestIdList == null) ? 0 : requestIdList.hashCode());
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
		ErrorConstruct other = (ErrorConstruct) obj;
		if (errorObjList == null) {
			if (other.errorObjList != null)
				return false;
		} else if (!errorObjList.equals(other.errorObjList))
			return false;
		if (requestIdList == null) {
			if (other.requestIdList != null)
				return false;
		} else if (!requestIdList.equals(other.requestIdList))
			return false;
		return true;
	}
	
	
	

}
