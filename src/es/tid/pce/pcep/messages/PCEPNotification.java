package es.tid.pce.pcep.messages;

import java.util.LinkedList;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.Notify;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.PCEPObject;

/**
 * PCEP Notification Message (RFC 5440).
 * 
 * The PCEP Notification message (also referred to as the PCNtf message)
   can be sent either by a PCE to a PCC, or by a PCC to a PCE, to notify
   of a specific event.  The Message-Type field of the PCEP common
   header for the PCNtf message is set to 5.

   The PCNtf message MUST carry at least one NOTIFICATION object and MAY
   contain several NOTIFICATION objects should the PCE or the PCC intend
   to notify of multiple events.  The NOTIFICATION object is defined in
   Section 7.14.  The PCNtf message MAY also contain RP objects (see
   Section 7.4) when the notification refers to particular path
   computation requests.

   The PCNtf message may be sent by a PCC or a PCE in response to a
   request or in an unsolicited manner.
   The format of a PCNtf message is as follows:

   <PCNtf Message>::=<Common Header>
                     <notify-list>

   <notify-list>::=<notify> [<notify-list>]

   <notify>::= [<request-id-list>]
                <notification-list>

   <request-id-list>::=<RP>[<request-id-list>]

   <notification-list>::=<NOTIFICATION>[<notification-list>]

 * @author ogondio
 *
 */
public class PCEPNotification extends PCEPMessage {

	/**
	 * List of notify constructs
	 */
	private LinkedList<Notify> notifyList;
	
	/**
	 * Construct new PCEP Notification Message
	 */
	public PCEPNotification(){
		this.setMessageType(PCEPMessageTypes.MESSAGE_NOTIFY);
		notifyList=new LinkedList<Notify> ();

	}
	
	/**
	 * Create a new PCEP Notification Message from a byte array
	 * @param bytes
	 * @throws PCEPProtocolViolationException
	 */
	public PCEPNotification(byte[] bytes)throws PCEPProtocolViolationException {
		super(bytes);
		notifyList=new LinkedList<Notify>();
		decode();
	}
	
	/**
	 * Encode PCEP Notification Message
	 */
	public void encode() throws PCEPProtocolViolationException {
		if (notifyList.size()==0){
			log.warning("There should be at least one notification in a PCEP Notification message");
			throw new PCEPProtocolViolationException();
		}
		int len=4;
		for (int i=0;i<notifyList.size();++i){
			notifyList.get(i).encode();
			len=len+notifyList.get(i).getLength();
		}
		this.setMessageLength(len);
		messageBytes=new byte[len];
		encodeHeader();
		int offset=4;
		for (int i=0;i<notifyList.size();++i){
			System.arraycopy(notifyList.get(i).getBytes(), 0, messageBytes, offset, notifyList.get(i).getLength());
			offset=offset+notifyList.get(i).getLength();
		}

	}

	/**
	 * Decode the PCEP Notification Message
	 */
	public void decode() throws PCEPProtocolViolationException {
		//Decoding PCEP Notification Message
		int offset=4;//We start after the object header
		if (offset>=this.getLength()){
			log.warning("Empty notification message");
			throw new PCEPProtocolViolationException();
		}
		int oc=PCEPObject.getObjectClass(this.messageBytes, offset);
		while (((oc==ObjectParameters.PCEP_OBJECT_CLASS_RP)||(oc==ObjectParameters.PCEP_OBJECT_CLASS_NOTIFICATION))) {
			Notify notf=new Notify(this.messageBytes, offset);
			notifyList.add(notf);
			offset=offset+notf.getLength();
			if (offset>=this.getLength()){
				return;
			}
			oc=PCEPObject.getObjectClass(this.messageBytes, offset);				
		}
	}

	public Notify getNotify(int index){
		return notifyList.get(index);
	}
	
	public void addNotify (Notify notify){
		notifyList.add(notify);
	}
	
	
	public LinkedList<Notify> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(LinkedList<Notify> notifyList) {
		this.notifyList = notifyList;
	}


}
