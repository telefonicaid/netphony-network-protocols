package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPErrorObject;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.RequestParameters;

/**
 *  <error>::=[<request-id-list>]
              <error-obj-list>
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
		//log.info("Path Length = "+len);
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
		log.finest("Decoding Error Construct");
		int len=0;	
		int max_offset=bytes.length;
		if (offset>=max_offset){
			log.warning("Empty Notify construct!!!");
			throw new PCEPProtocolViolationException();
		}
		int oc=PCEPObject.getObjectClass(bytes, offset);
		while (oc==ObjectParameters.PCEP_OBJECT_CLASS_RP){
			log.finest("RP Object found");
			RequestParameters rp;
			try {
				rp = new RequestParameters(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed METRIC Object found");
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
			log.finest("PCEP Error Object found");
			PCEPErrorObject perror;
			try {
				perror = new PCEPErrorObject(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warning("Malformed METRIC Object found");
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
	
	

}
