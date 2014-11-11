package es.tid.pce.pcep.constructs;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.Notification;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.pce.pcep.objects.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Notify Construct RFC 5440
 * From RFC 5440 Section 6.6
 *  <notify>::= [<request-id-list>]
                <notification-list>

   <request-id-list>::=<RP>[<request-id-list>]

   <notification-list>::=<NOTIFICATION>[<notification-list>]


 * @author ogondio
 *
 */
public class Notify extends PCEPConstruct {
	
	private LinkedList<RequestParameters> requestIdList;
	private LinkedList<Notification> notificationList;
	
	private static final Logger log= LoggerFactory.getLogger("PCEPParser");
	
	public Notify() {
		requestIdList=new LinkedList<RequestParameters> ();
		notificationList=new LinkedList<Notification>();
	}
	public Notify(byte[] bytes, int offset) throws PCEPProtocolViolationException{
		requestIdList=new LinkedList<RequestParameters> ();
		notificationList=new LinkedList<Notification>();
		decode(bytes,offset);
	}
	
	/**
	 * Encode Notify Construct
	 */
	public void encode() throws PCEPProtocolViolationException {
		if (notificationList.size()==0){
			log.warn("Notify must have at least a Nofitication object");
			throw new PCEPProtocolViolationException();
		}
		int len=0;
		for (int i=0;i<requestIdList.size();++i){
			(requestIdList.get(i)).encode();
			len=len+(requestIdList.get(i)).getLength();
		}
		for (int i=0;i<notificationList.size();++i){
			(notificationList.get(i)).encode();
			len=len+(notificationList.get(i)).getLength();
		}
		this.setLength(len);
		bytes=new byte[len];
		int offset=0;
		
		for (int i=0;i<requestIdList.size();++i){
			System.arraycopy(requestIdList.get(i).getBytes(), 0, bytes, offset, requestIdList.get(i).getLength());
			offset=offset+requestIdList.get(i).getLength();
		}
		for (int i=0;i<notificationList.size();++i){
			System.arraycopy(notificationList.get(i).getBytes(), 0, bytes, offset, notificationList.get(i).getLength());
			offset=offset+notificationList.get(i).getLength();
		}
	}

	
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
				log.warn("Malformed RP Object found");
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
		//while ((oc==ObjectParameters.PCEP_OBJECT_CLASS_NOTIFICATION)&&(len<this.getLength())){
		while ((oc==ObjectParameters.PCEP_OBJECT_CLASS_NOTIFICATION)){
			Notification notif;
			try {
				notif = new Notification(bytes,offset);
			} catch (MalformedPCEPObjectException e) {
				log.warn("Malformed NOTIFICATION Object found");
				throw new PCEPProtocolViolationException();
			}
			notificationList.add(notif);
			offset=offset+notif.getLength();			
			len=len+notif.getLength();
			if (len<this.getLength()){
				oc=PCEPObject.getObjectClass(bytes, offset);	
			}
			if (offset>=max_offset){
				this.setLength(len);
				return;
			}
		}
		this.setLength(len);

	}

	public LinkedList<RequestParameters> getRequestIdList() {
		return requestIdList;
	}

	public void setRequestIdList(LinkedList<RequestParameters> requestIdList) {
		this.requestIdList = requestIdList;
	}

	public LinkedList<Notification> getNotificationList() {
		return notificationList;
	}

	public void setNotificationList(LinkedList<Notification> notificationList) {
		this.notificationList = notificationList;
	}
	
	

}
