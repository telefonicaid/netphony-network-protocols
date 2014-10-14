package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.RequestInfoTLV;

/**
 * PCEP Monitoring Object (described in RFC 5886).
 * 4.1. MONITORING Object


   The MONITORING object MUST be present within PCMonReq and PCMonRep
   messages (out-of-band monitoring requests) and MAY be carried within
   PCRep and PCReq messages (in-band monitoring requests).  There SHOULD
   NOT be more than one instance of the MONITORING object in a PCMonReq
   or PCMonRep message: if more than one instance of the MONITORING
   object is present, the recipient MUST process the first instance and
   MUST ignore other instances.

   The MONITORING object is used to specify the set of requested PCE
   state metrics.

   The MONITORING Object-Class (19) has been assigned by IANA.

   The MONITORING Object-Type (1) has been assigned by IANA.

   The format of the MONITORING object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |    Reserved   |                  Flags              |I|C|P|G|L|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Monitoring-id-number                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                      Optional TLV(s)                        //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   Flags: 24 bits

   The following flags are currently defined:

   L (Liveness) - 1 bit: when set, this indicates that the state metric
   of interest is the PCE's liveness and thus the PCE MUST include a
   PCE-ID object in the corresponding reply.  The L bit MUST always be
   ignored in a PCMonRep or PCRep message.

   G (General) - 1 bit: when set, this indicates that the monitoring
   request is a general monitoring request.  When the requested
   performance metric is specific, the G bit MUST be cleared.  The G bit
   MUST always be ignored in a PCMonRep or PCRep message.

   P (Processing Time) - 1 bit: the P bit of the MONITORING object
   carried in a PCMonReq or a PCReq message is set to indicate that the
   processing times is a metric of interest.  If allowed by policy, a
   PROC-TIME object MUST be inserted in the corresponding PCMonRep or
   PCRep message.  The P bit MUST always be ignored in a PCMonRep or
   PCRep message.

   C (Overload) - 1 bit: The C bit of the MONITORING object carried in a
   PCMonReq or a PCReq message is set to indicate that the overload
   status is a metric of interest, in which case an OVERLOAD object MUST
   be inserted in the corresponding PCMonRep or PCRep message.  The C
   bit MUST always be ignored in a PCMonRep or PCRep message.

   I (Incomplete) - 1 bit: If a PCE supports a received PCMonReq message
   and that message does not trigger any policy violation, but the PCE
   cannot provide any of the set of requested performance metrics for
   unspecified reasons, the PCE MUST set the I bit.  The I bit has no
   meaning in a request and SHOULD be ignored on receipt.

   Monitoring-id-number (32 bits): The monitoring-id-number value
   combined with the PCC-REQ-ID identifying the requesting PCC uniquely
   identifies the monitoring request context.  The monitoring-id-number
   MUST start at a non-zero value and MUST be incremented each time a
   new monitoring request is sent to a PCE.  Each increment SHOULD have
   a value of 1 and may cause a wrap back to zero.  If no reply to a
   monitoring request is received from the PCE, and the PCC wishes to
   resend its path computation monitoring request, the same monitoring-
   id-number MUST be used.  Conversely, a different monitoring-id-number
   MUST be used for different requests sent to a PCE.  A PCEP
   implementation SHOULD checkpoint the Monitoring-id-number of pending
   monitoring requests in case of restart thus avoiding the reuse of a
   Monitoring-id-number of an in-process monitoring request.

   Unassigned bits are considered as reserved and MUST be set to zero on
   transmission and ignored on reception.

   No optional TLVs are currently defined.
 
 * @author ogondio
 *
 */
public class Monitoring extends PCEPObject {

	private boolean livenessBit;
	private boolean generalBit;
	private boolean processingTimeBit;
	private boolean overloadBit;
	private boolean incompleteBit;
	private long monitoringIdNumber;
	//Optional TLV's
	private RequestInfoTLV requestInfoTLV;
	
	public Monitoring(){
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_MONITORING);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_MONITORING);
	}
	
	public Monitoring(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);			
		decode();
		
	}
	
	
	public void encode() {
		ObjectLength=12;
		object_bytes=new byte[ObjectLength];
		encode_header();
		int offset = 7;
		object_bytes[offset]=(byte)( ( (livenessBit?1:0) & 0x01) | ( ((generalBit?1:0) <<1) & 0x02) | (((processingTimeBit?1:0)<<3) & 0x04) | (((overloadBit?1:0)<<4) & 0x08) | (((incompleteBit?1:0)<<5) & 0x10));
		object_bytes[offset+1]=(byte)((monitoringIdNumber>>24) & 0xFF);
		object_bytes[offset+2]=(byte)((monitoringIdNumber>>16) & 0xFF);
		object_bytes[offset+3]=(byte)((monitoringIdNumber>>8) & 0xFF);
		object_bytes[offset+4]=(byte)(monitoringIdNumber & 0xFF);
		offset=offset+5;
		if (requestInfoTLV!=null){
			System.arraycopy(requestInfoTLV.getTlv_bytes(),0,this.object_bytes,offset,requestInfoTLV.getTotalTLVLength());
			offset=offset+requestInfoTLV.getTotalTLVLength();
			
		}
		
		
	}

	@Override
	public void decode() throws MalformedPCEPObjectException {		
		livenessBit =(object_bytes[7]&0x01)==0x01;
		generalBit =(object_bytes[7]&0x02)==0x02;
		processingTimeBit =(object_bytes[7]&0x04)==0x04;
		overloadBit =(object_bytes[7]&0x08)==0x08;
		incompleteBit =(object_bytes[7]&0x10)==0x10;
		monitoringIdNumber  = ((long) (  ((int)object_bytes[8] << 24) 
	 			  | ((int)object_bytes[9] << 16)
	 			  | ((int)object_bytes[10] << 8)
	 			  | ((int)object_bytes[11])    ))& 0xFFFFFFFFL;		
		
		boolean fin=false;
		int offset=12;
		if (ObjectLength==12){
			fin=true;
		}
		while (!fin) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_REQUEST_INFO:
				requestInfoTLV=new RequestInfoTLV(this.getObject_bytes(), offset);
				break;
			
			default:
				log.finest("UNKNOWN TLV found");
				//UnknownTLV unknownTLV = new UnknownTLV();			
				//FIXME: Que hacemos con los desconocidos????
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				fin=true;
			}
			
		}
	}

	public boolean isLivenessBit() {
		return livenessBit;
	}

	public void setLivenessBit(boolean livenessBit) {
		this.livenessBit = livenessBit;
	}

	public boolean isGeneralBit() {
		return generalBit;
	}

	public void setGeneralBit(boolean generalBit) {
		this.generalBit = generalBit;
	}

	public boolean isProcessingTimeBit() {
		return processingTimeBit;
	}

	public void setProcessingTimeBit(boolean processingTimeBit) {
		this.processingTimeBit = processingTimeBit;
	}

	public boolean isOverloadBit() {
		return overloadBit;
	}

	public void setOverloadBit(boolean overloadBit) {
		this.overloadBit = overloadBit;
	}

	public boolean isIncompleteBit() {
		return incompleteBit;
	}

	public void setIncompleteBit(boolean incompleteBit) {
		this.incompleteBit = incompleteBit;
	}

	public long getMonitoringIdNumber() {
		return monitoringIdNumber;
	}

	public void setMonitoringIdNumber(long monitoringIdNumber) {
		this.monitoringIdNumber = monitoringIdNumber;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("Monitoring: ");
		sb.append(object_bytes[8]);
		sb.append(object_bytes[9]);
		sb.append(object_bytes[10]);
		sb.append(object_bytes[11]);

		return sb.toString();
	}
	
}
