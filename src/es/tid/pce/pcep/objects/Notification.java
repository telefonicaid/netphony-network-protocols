package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.tlvs.*;


/**
 * <p> Represents a PCEP NOTIFICATION Object, as defined in RFC 5440.</p>
 * 
 * <p>From RFC 5440 Section 7.14. NOTIFICATION Object</p>
<pre>
   The NOTIFICATION object is exclusively carried within a PCNtf message
   and can either be used in a message sent by a PCC to a PCE or by a
   PCE to a PCC so as to notify of an event.

   NOTIFICATION Object-Class is 12.

   NOTIFICATION Object-Type is 1.

   The format of the NOTIFICATION body object is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Reserved    |     Flags     |      NT       |     NV        |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                      Optional TLVs                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 19: NOTIFICATION Body Object Format

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Flags (8 bits):  No flags are currently defined.  Unassigned flags
      MUST be set to zero on transmission and MUST be ignored on
      receipt.

   NT (Notification Type - 8 bits):  The Notification-type specifies the
      class of notification.

   NV (Notification Value - 8 bits):  The Notification-value provides
      addition information related to the nature of the notification.

   Both the Notification-type and Notification-value are managed by
   IANA.

   The following Notification-type and Notification-value values are
   currently defined:

   o  Notification-type=1: Pending Request cancelled

      *  Notification-value=1: PCC cancels a set of pending requests.  A
         Notification-type=1, Notification-value=1 indicates that the
         PCC wants to inform a PCE of the cancellation of a set of
         pending requests.  Such an event could be triggered because of
         external conditions such as the receipt of a positive reply
         from another PCE (should the PCC have sent multiple requests to
         a set of PCEs for the same path computation request), a network
         event such as a network failure rendering the request obsolete,
         or any other events local to the PCC.  A NOTIFICATION object
         with Notification-type=1, Notification-value=1 is carried
         within a PCNtf message sent by the PCC to the PCE.  The RP
         object corresponding to the cancelled request MUST also be
         present in the PCNtf message.  Multiple RP objects may be
         carried within the PCNtf message; in which case, the
         notification applies to all of them.  If such a notification is
         received by a PCC from a PCE, the PCC MUST silently ignore the
         notification and no errors should be generated.

      *  Notification-value=2: PCE cancels a set of pending requests.  A
         Notification-type=1, Notification-value=2 indicates that the
         PCE wants to inform a PCC of the cancellation of a set of
         pending requests.  A NOTIFICATION object with Notification-
         type=1, Notification-value=2 is carried within a PCNtf message
         sent by a PCE to a PCC.  The RP object corresponding to the
         cancelled request MUST also be present in the PCNtf message.
         Multiple RP objects may be carried within the PCNtf message; in
         which case, the notification applies to all of them.  If such
         notification is received by a PCE from a PCC, the PCE MUST
         silently ignore the notification and no errors should be
         generated.

   o  Notification-type=2: Overloaded PCE

      *  Notification-value=1: A Notification-type=2, Notification-
         value=1 indicates to the PCC that the PCE is currently in an
         overloaded state.  If no RP objects are included in the PCNtf
         message, this indicates that no other requests SHOULD be sent
         to that PCE until the overloaded state is cleared: the pending
         requests are not affected and will be served.  If some pending
         requests cannot be served due to the overloaded state, the PCE
         MUST also include a set of RP objects that identifies the set
         of pending requests that are cancelled by the PCE and will not
         be honored.  In this case, the PCE does not have to send an
         additional PCNtf message with Notification-type=1 and
         Notification-value=2 since the list of cancelled requests is
         specified by including the corresponding set of RP objects.  If
         such notification is received by a PCE from a PCC, the PCE MUST
         silently ignore the notification and no errors should be
         generated.

      *  A PCE implementation SHOULD use a dual-threshold mechanism used
         to determine whether it is in a congestion state with regards
         to specific resource monitoring (e.g.  CPU, memory).  The use
         of such thresholds is to avoid oscillations between overloaded/
         non-overloaded state that may result in oscillations of request
         targets by the PCCs.

      *  Optionally, a TLV named OVERLOADED-DURATION may be included in
         the NOTIFICATION object that specifies the period of time
         during which no further request should be sent to the PCE.
         Once this period of time has elapsed, the PCE should no longer
         be considered in a congested state.

         The OVERLOADED-DURATION TLV is compliant with the PCEP TLV
         format defined in Section 7.1 and is comprised of 2 bytes for
         the type, 2 bytes specifying the TLV length (length of the
         value portion in bytes), followed by a fixed-length value field
         of a 32-bit flags field.

         Type:   2
         Length: 4 bytes
         Value:  32-bit flags field indicates the estimated PCE
                 congestion duration in seconds.

      *  Notification-value=2: A Notification-type=2, Notification-
         value=2 indicates that the PCE is no longer in an overloaded
         state and is available to process new path computation
         requests.  An implementation SHOULD make sure that a PCE sends
         such notification to every PCC to which a Notification message
         (with Notification-type=2, Notification-value=1) has been sent
         unless an OVERLOADED-DURATION TLV has been included in the
         corresponding message and the PCE wishes to wait for the
         expiration of that period of time before receiving new
         requests.  If such notification is received by a PCE from a
         PCC, the PCE MUST silently ignore the notification and no
         errors should be generated.  It is RECOMMENDED to support some
         dampening notification procedure on the PCE so as to avoid too
         frequent congestion state and congestion state release
         notifications.  For example, an implementation could make use
         of an hysteresis approach using a dual-threshold mechanism that
         triggers the sending of congestion state notifications.
         Furthermore, in case of high instabilities of the PCE
         resources, an additional dampening mechanism SHOULD be used
         (linear or exponential) to pace the notification frequency and
         avoid oscillation of path computation requests.

   When a PCC receives an overload indication from a PCE, it should
   consider the impact on the entire network.  It must be remembered
   that other PCCs may also receive the notification, and so many path
   computation requests could be redirected to other PCEs.  This may, in
   turn, cause further overloading at PCEs in the network.  Therefore,
   an application at a PCC receiving an overload notification should
   consider applying some form of back-off (e.g., exponential) to the
   rate at which it generates path computation requests into the
   network.  This is especially the case as the number of PCEs reporting
   overload grows.
</pre>
 * @author ogondio
 *
 */
public class Notification extends PCEPObject{
	
	/**
	 * NT (Notification Type - 8 bits):  The Notification-type specifies the
      class of notification.
	 */
	 private int notificationType;
	 /**
	  * NV (Notification Value - 8 bits):  The Notification-value provides
      addition information related to the nature of the notification.
	  */
	 private int notificationValue;
	 /**
	  * List of optional TLVs
	  */
	 //private LinkedList<PCEPTLV> tLVList;
	 
	 /**
	  * Optional Overloaded Duration TLV;
	  */
	 private OverloadedDurationTLV odtlv;
	 
	 /**
	  * Optional List of Reachability TLVs
	  */
	 private LinkedList<ReachabilityTLV>  reachabilityTLVList;
	 
	 /**
	  * Optional List of OSPFTE_LSA TLVs;
	  */
	 private LinkedList<OSPFTE_LSA_TLV> LSATLVList;
	 
	 /**
	  * GEYSERS: Optional IT_advertisement TLV;
	  */
	 private ITAdvertisementTLV ITadvTLV;
	 
	 /**
	  * GEYSERS: Optional Storage TLV;
	  */
	 private StorageTLV StorageTLV;
	 
	 /**
	  * GEYSERS: Optional Server TLV;
	  */
	 private ServerTLV ServerTLV;
	 
	 private ReservationIDTLV reservationIDTLV;
	 
	 /**
	  * STRONGEST: collaborative PCEs
	  */
	 private PathReservationTLV notificationTLV;
	 
	public Notification(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_NOTIFICATION);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_NOTIFICATION);
	}
	
	
	
	/**
	 * Constructs a new NOTIFICATION Object from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public Notification(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		//tLVList=new LinkedList<PCEPTLV> ();
		decode();
	}

	/**
	 * Encode Notification Object
	 */
	public void encode() {
		int len=4+4;//The four bytes of the header plus the 4 first bytes
		
		if (odtlv!=null){
			odtlv.encode();
			len=len+odtlv.getTotalTLVLength();
		}
		
		if (reachabilityTLVList!=null){
			for (int i=0;i<reachabilityTLVList.size();++i){
				(reachabilityTLVList.get(i)).encode();
				len=len+(reachabilityTLVList.get(i)).getTotalTLVLength();
			}
		}
			
		if (LSATLVList!=null){
			for (int i=0;i<LSATLVList.size();++i){
				(LSATLVList.get(i)).encode();
				len=len+(LSATLVList.get(i)).getTotalTLVLength();
			}
		}
		
		// Aquí hay q añadir las TLVs del GEYSERS
		
		if (ITadvTLV!=null){
			ITadvTLV.encode();
			len=len+ITadvTLV.getTotalTLVLength();
			
			if (StorageTLV!=null){
				StorageTLV.encode();
				len=len+StorageTLV.getTotalTLVLength();
			}
			
			if (ServerTLV!=null){
				ServerTLV.encode();
				len=len+ServerTLV.getTotalTLVLength();
			}
			
		}
		if (reservationIDTLV!=null){
			reservationIDTLV.encode();
			len=len+reservationIDTLV.getTotalTLVLength();
		}
		if (notificationTLV!=null){
			notificationTLV.encode();
			len=len+notificationTLV.getTotalTLVLength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		this.object_bytes[4]=0x00;
		this.object_bytes[5]=0x00;
		this.object_bytes[6]=(byte)notificationType;
		this.object_bytes[7]=(byte)notificationValue;
		int offset=8;
		
		if (odtlv!=null){
			System.arraycopy(odtlv.getTlv_bytes(), 0, this.object_bytes, offset, odtlv.getTotalTLVLength());
			offset=offset+odtlv.getTotalTLVLength();
		}
		
		if (reachabilityTLVList!=null){
			for (int i=0;i<reachabilityTLVList.size();++i){
				System.arraycopy(reachabilityTLVList.get(i).getTlv_bytes(), 0, this.object_bytes, offset, reachabilityTLVList.get(i).getTotalTLVLength());
				offset=offset+reachabilityTLVList.get(i).getTotalTLVLength();
			}
		}
		
		if (LSATLVList!=null){
			for (int i=0;i<LSATLVList.size();++i){
				System.arraycopy(LSATLVList.get(i).getTlv_bytes(), 0, this.object_bytes, offset, LSATLVList.get(i).getTotalTLVLength());
				offset=offset+LSATLVList.get(i).getTotalTLVLength();
			}
		}
		
// Aquí hay q añadir las TLVs del GEYSERS
		
		if (ITadvTLV!=null){
			System.arraycopy(ITadvTLV.getTlv_bytes(), 0, this.object_bytes, offset, ITadvTLV.getTotalTLVLength());
			offset=offset+ITadvTLV.getTotalTLVLength();
			
			if (StorageTLV!=null){
				System.arraycopy(StorageTLV.getTlv_bytes(), 0, this.object_bytes, offset, StorageTLV.getTotalTLVLength());
				offset=offset+StorageTLV.getTotalTLVLength();
			}
			
			if (ServerTLV!=null){
				System.arraycopy(ServerTLV.getTlv_bytes(), 0, this.object_bytes, offset, ServerTLV.getTotalTLVLength());
				offset=offset+ServerTLV.getTotalTLVLength();
			}
			
		}
		if (reservationIDTLV!=null){
			System.arraycopy(reservationIDTLV.getTlv_bytes(), 0, this.object_bytes, offset, reservationIDTLV.getTotalTLVLength());
			offset=offset+reservationIDTLV.getTotalTLVLength();
		}
		if (notificationTLV!=null){
			System.arraycopy(notificationTLV.getTlv_bytes(), 0, this.object_bytes, offset, notificationTLV.getTotalTLVLength());
			offset=offset+notificationTLV.getTotalTLVLength();
		}
		
		
//		for (int k=0; k<tLVList.size();k=k+1){
//			tLVList.get(k).encode();			
//			len=len+tLVList.get(k).getTotalTLVLength();
//		}
//		ObjectLength=len;
//		this.object_bytes=new byte[ObjectLength];
//		encode_header();
//		this.object_bytes[4]=0x00;
//		this.object_bytes[5]=0x00;
//		this.object_bytes[6]=(byte)notificationType;
//		this.object_bytes[7]=(byte)notificationValue;						
//		int pos=8;
//		for (int k=0 ; k<tLVList.size(); k=k+1) {					
//			System.arraycopy(tLVList.get(k).getTlv_bytes(),0, this.object_bytes, pos, tLVList.get(k).getTotalTLVLength());
//			pos=pos+tLVList.get(k).getTotalTLVLength();
//		}						
	}

	/**
	 * Decode notification object
	 */
	public void decode() throws MalformedPCEPObjectException {
		notificationType=this.object_bytes[6]&0xFF;
		notificationValue=this.object_bytes[7]&0xFF;
		boolean fin=false;
		int offset=8;
		if (ObjectLength==8){
			fin=true;
		}
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_OVERLOADED_DURATION:
				odtlv=new OverloadedDurationTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_REACHABILITY_TLV:
				if (reachabilityTLVList==null){
					reachabilityTLVList=new LinkedList<ReachabilityTLV>();
				}
				ReachabilityTLV rtlv=new ReachabilityTLV(this.getObject_bytes(), offset);
				reachabilityTLVList.add(rtlv);
				break;
			case ObjectParameters.PCEP_TLV_OSPFTE_LSA_TLV:
				if (LSATLVList==null){
					LSATLVList=new LinkedList<OSPFTE_LSA_TLV>();
				}
				OSPFTE_LSA_TLV lsa_tlv=new OSPFTE_LSA_TLV(this.getObject_bytes(), offset);
				LSATLVList.add(lsa_tlv);
				break;
			case ObjectParameters.PCEP_TLV_DOMAIN_ID_TLV:
				//domain_id_tlv=new DomainIDTLV(this.getObject_bytes(), offset);
				break;
			case ObjectParameters.PCEP_TLV_PCE_ID_TLV:
				//pce_id_tlv=new PCE_ID_TLV(this.getObject_bytes(), offset);
				break;
			case ObjectParameters.PCEP_TLV_TYPE_IT_ADV:
				ITadvTLV=new ITAdvertisementTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_TYPE_STORAGE:
				StorageTLV=new StorageTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_TYPE_SERVER:
				ServerTLV=new ServerTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_TYPE_RESERVATION_ID:
				reservationIDTLV=new ReservationIDTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_TYPE_PATH_RESERVATION:
				notificationTLV=new PathReservationTLV(this.getObject_bytes(), offset);				
				break;
			default:
				log.warning("Unknown or unexpected TLV found");
				//UnknownTLV unknownTLV = new UnknownTLV();
				//tLVList.add(unknownTLV);
				//FIXME: Que hacemos con los desconocidos????
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}
		
	}
	
	//Getters and Setters

	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}

	public int getNotificationValue() {
		return notificationValue;
	}

	public void setNotificationValue(int notificationValue) {
		this.notificationValue = notificationValue;
	}

//	public LinkedList<PCEPTLV> gettLVList() {
//		return tLVList;
//	}
//
//	public void settLVList(LinkedList<PCEPTLV> tLVList) {
//		this.tLVList = tLVList;
//	}



	public OverloadedDurationTLV getOdtlv() {
		return odtlv;
	}



	public void setOdtlv(OverloadedDurationTLV odtlv) {
		this.odtlv = odtlv;
	}



	public LinkedList<ReachabilityTLV> getReachabilityTLVList() {
		return reachabilityTLVList;
	}



	public void setReachabilityTLVList(
			LinkedList<ReachabilityTLV> reachabilityTLVList) {
		this.reachabilityTLVList = reachabilityTLVList;
	}



	public LinkedList<OSPFTE_LSA_TLV> getLSATLVList() {
		return LSATLVList;
	}



	public void setLSATLVList(LinkedList<OSPFTE_LSA_TLV> lSATLVList) {
		LSATLVList = lSATLVList;
	}	
	
	public void addOSPFTE_LSA_TLV(OSPFTE_LSA_TLV ospfte_lsa_tlv){
		if (LSATLVList==null){
			LSATLVList=new LinkedList<OSPFTE_LSA_TLV>();
		}
		LSATLVList.add(ospfte_lsa_tlv);
	}
	
	public void addReachabilityTLV(ReachabilityTLV reachabilityTLV){
		if (reachabilityTLVList==null){
			reachabilityTLVList=new LinkedList<ReachabilityTLV>();
		}
		reachabilityTLVList.add(reachabilityTLV);
	}

	public ITAdvertisementTLV getITadvtlv() {
		return ITadvTLV;
	}

	public void setITadvtlv(ITAdvertisementTLV ITadvtlv) {
		this.ITadvTLV = ITadvtlv;
	}
	
	public StorageTLV getStoragetlv() {
		return StorageTLV;
	}

	public void setStoragetlv(StorageTLV Storagetlv) {
		this.StorageTLV = Storagetlv;
	}

	
	public ServerTLV getServertlv() {
		return ServerTLV;
	}

	public void setServertlv(ServerTLV Servertlv) {
		this.ServerTLV = Servertlv;
	}



	public ReservationIDTLV getReservationIDTLV() {
		return reservationIDTLV;
	}



	public void setReservationIDTLV(ReservationIDTLV reservationIDTLV) {
		this.reservationIDTLV = reservationIDTLV;
	}
	
	public PathReservationTLV getNotificationTLV() {
		return notificationTLV;
	}



	public void setNotificationTLV(PathReservationTLV notificationTLV) {
		this.notificationTLV = notificationTLV;
	}


}