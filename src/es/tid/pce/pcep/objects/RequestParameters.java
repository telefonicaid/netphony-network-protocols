package es.tid.pce.pcep.objects;

import es.tid.pce.pcep.objects.tlvs.MaxRequestTimeTLV;
import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.PathSetupTLV;
import es.tid.protocol.commons.ByteHandler;

/**
 * Request Parameters Object.
 * * <p>Represents a Request Parameters Object, as described in RFC 5440.</p>
 * <p>One propietary optional TLVs in current version</p>
 * <p>From RFC 5440, Section 7.4 RP Object</p>
 * 
 * @author ogondio
 *
 */
public class RequestParameters extends PCEPObject{
	
	/**
	 *  Pri (Priority - 3 bits): the Priority field may be used by the
      requesting PCC to specify to the PCE the request's priority from 1
      to 7.  The decision of which priority should be used for a
      specific request is a local matter; it MUST be set to 0 when
      unused.  Furthermore, the use of the path computation request
      priority by the PCE's scheduler is implementation specific and out
      of the scope of this document.  Note that it is not required for a
      PCE to support the priority field: in this case, it is RECOMMENDED
      that the PCC set the priority field to 0 in the RP object.  If the
      PCE does not take into account the request priority, it is
      RECOMMENDED to set the priority field to 0 in the RP object
      carried within the corresponding PCRep message, regardless of the
      priority value contained in the RP object carried within the
      corresponding PCReq message.  A higher numerical value of the
      priority field reflects a higher priority.  Note that it is the
      responsibility of the network administrator to make use of the
      priority values in a consistent manner across the various PCCs.
      The ability of a PCE to support request prioritization MAY be
      dynamically discovered by the PCCs by means of PCE capability
      discovery.  If not advertised by the PCE, a PCC may decide to set
      the request priority and will learn the ability of the PCE to
      support request prioritization by observing the Priority field of
      the RP object received in the PCRep message.  If the value of the
      Pri field is set to 0, this means that the PCE does not support
      the handling of request priorities: in other words, the path
      computation request has been honored but without taking the
      request priority into account.

	 */
	private int prio;
	/**
	 * R (Reoptimization - 1 bit): when set, the requesting PCC specifies
      that the PCReq message relates to the reoptimization of an
      existing TE LSP.  For all TE LSPs except zero-bandwidth LSPs, when
      the R bit is set, an RRO (see Section 7.10) MUST be included in
      the PCReq message to show the path of the existing TE LSP.  Also,
      for all TE LSPs except zero-bandwidth LSPs, when the R bit is set,
      the existing bandwidth of the TE LSP to be reoptimized MUST be
      supplied in a BANDWIDTH object (see Section 7.7).  This BANDWIDTH
      object is in addition to the instance of that object used to
      describe the desired bandwidth of the reoptimized LSP.  For zero-
      bandwidth LSPs, the RRO and BANDWIDTH objects that report the
      characteristics of the existing TE LSP are optional.
	 */
	private boolean reopt;
	/**
	 *  B (Bi-directional - 1 bit): when set, the PCC specifies that the
      path computation request relates to a bi-directional TE LSP that
      has the same traffic engineering requirements including fate
      sharing, protection and restoration, LSRs, TE links, and resource
      requirements (e.g., latency and jitter) in each direction.  When
      cleared, the TE LSP is unidirectional.
	 */
	private boolean bidirect;
	/**
	 * O (strict/loose - 1 bit): when set, in a PCReq message, this
      indicates that a loose path is acceptable.  Otherwise, when
      cleared, this indicates to the PCE that a path exclusively made of
      strict hops is required.  In a PCRep message, when the O bit is
      set this indicates that the returned path is a loose path;
      otherwise (when the O bit is cleared), the returned path is made
      of strict hops.
	 */
	private boolean loose;
	
	 /**F (Supply OF - 1 bit):  The Supply OF on response flag 
     (bit number 24). When set in a PCReq
	   message, this indicates that the PCE MUST provide the applied
	   objective function (should a path satisfying the constraints be
	   found) in the PCRep message.  When set in a PCRep message, this
	   indicates that the objective function that was used during path
	   computation is included.*/
	private boolean supplyOF;
	
	/**
	 * Request-ID-number (32 bits):  The Request-ID-number value combined
      with the source IP address of the PCC and the PCE address uniquely
      identify the path computation request context.  The Request-ID-
      number is used for disambiguation between pending requests, and
      thus it MUST be changed (such as by incrementing it) each time a
      new request is sent to the PCE, and may wrap.

      The value 0x00000000 is considered invalid.

      If no path computation reply is received from the PCE (e.g., the
      request is dropped by the PCE because of memory overflow), and the
      PCC wishes to resend its request, the same Request-ID-number MUST
      be used.  Upon receiving a path computation request from a PCC
      with the same Request-ID-number, the PCE SHOULD treat the request
      as a new request.  An implementation MAY choose to cache path
      computation replies in order to quickly handle retransmission
      without having to process a path computation request twice (in the
      case that the first request was dropped or lost).  Upon receiving
      a path computation reply from a PCE with the same Request-ID-
      number, the PCC SHOULD silently discard the path computation
      reply.

      Conversely, different Request-ID-numbers MUST be used for
      different requests sent to a PCE.

      The same Request-ID-number MAY be used for path computation
      requests sent to different PCEs.  The path computation reply is
      unambiguously identified by the IP source address of the replying
      PCE.
	 */
	public long requestID;
	
	//FOR TESTING PURPOSES ONLY, NO STANDARD!!!
	private boolean retry;
	
	//FOR TESTING PURPOSES ONLY, NO STANDARD!!!
	MaxRequestTimeTLV maxRequestTimeTLV;
	
	private PathSetupTLV pathSetupTLV;
	
	
	/**
	 *    As described in Section 3.3.1, three new RP Object Flags have been
   defined.  IANA has made the following allocations from the PCEP "RP
   Object Flag Field" sub-registry:

      Bit      Description                         Reference

      18       Fragmentation (F-bit)               RFC 6006
      19       P2MP (N-bit)                        RFC 6006
      20       ERO-compression (E-bit)             RFC 6006
	 */
	
	/**
	 *  The F-bit is added to the flag bits of the RP object to indicate to
   the receiver that the request is part of a fragmented request, or is
   not a fragmented request.

   o  F (RP fragmentation bit - 1 bit):

      0: This indicates that the RP is not fragmented or it is the last
         piece of the fragmented RP.

      1: This indicates that the RP is fragmented and this is not the
         last piece of the fragmented RP.  The receiver needs to wait
         for additional fragments until it receives an RP with the same
         RP-ID and with the F-bit set to 0.
	 */
	
	private boolean Fbit = false;
	
	/**
	 *  The N-bit is added in the flag bits field of the RP object to signal
   the receiver of the message that the request/reply is for P2MP or is
   not for P2MP.

   o  N (P2MP bit - 1 bit):

      0: This indicates that this is not a PCReq or PCRep message for
         P2MP.

      1: This indicates that this is a PCReq or PCRep message for P2MP.
	 */
	
	private boolean Nbit = false;
	
	/**
	 *  The E-bit is added in the flag bits field of the RP object to signal
   the receiver of the message that the route is in the compressed
   format or is not in the compressed format.  By default, the path
   returned by the PCE SHOULD use the compressed format.

   o  E (ERO-compression bit - 1 bit):

      0: This indicates that the route is not in the compressed format.

      1: This indicates that the route is in the compressed format.

	 */
	
	private boolean Ebit = false;

	
//	private byte[] header;
	/** Use this constructor create new RequestParameters object from scratch
	 * 
	 */
	public RequestParameters() {
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_RP);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_RP);
		/*VALORES POR DEFECTO*/
		prio = 0x0;
		reopt = false;
		bidirect = false;
		loose = false;				
		retry=false;
		
		
	}

	/**
	 * Constructs a Request Parameters (RP) object from a sequence of bytes 
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 */
	public RequestParameters(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * 
	 */
	public void encode() {
	/*Por ahora no hay flags opcionales*/
		ObjectLength=12;
		if (maxRequestTimeTLV!=null){
			try {
				maxRequestTimeTLV.encode();
				this.ObjectLength+=maxRequestTimeTLV.getTotalTLVLength();	

			}catch (Exception e){
				log.warning(e.getMessage());
			}
			
		}
		if (pathSetupTLV!=null){
			try {
				pathSetupTLV.encode();
				this.ObjectLength+=pathSetupTLV.getTotalTLVLength();	

			}catch (Exception e){
				log.warning(e.getMessage());
			}
						
		}
		object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=(byte)((retry?1:0) <<7);
		object_bytes[5]=0x00;
		object_bytes[6]=(byte)( ( ((Fbit?1:0) <<5) & 0x20) | ( ((Nbit?1:0) <<4) & 0x10) | (((Ebit?1:0)<<3) & 0x08));
		object_bytes[7]=(byte)( ( ((loose?1:0) <<5) & 0x20) | ( ((bidirect?1:0) <<4) & 0x10) | (((reopt?1:0)<<3) & 0x08) | (prio & 0x07) | (  ( (supplyOF?1:0)<<6   ) ) ) ;
		object_bytes[8]=(byte)((requestID>>24) & 0xFF);
		object_bytes[9]=(byte)((requestID>>16) & 0xFF);
		object_bytes[10]=(byte)((requestID>>8) & 0xFF);
		object_bytes[11]=(byte)(requestID & 0xFF);
//		ByteHandler.BoolToBuffer(16 + 17, Fbit,object_bytes);
//		ByteHandler.BoolToBuffer(16 + 18, Nbit,object_bytes);
//		ByteHandler.BoolToBuffer(16 + 19, Ebit,object_bytes);
		int offset = 12;

		if (maxRequestTimeTLV!=null){
			System.arraycopy(maxRequestTimeTLV.getTlv_bytes(), 0, object_bytes, offset, maxRequestTimeTLV.getTotalTLVLength());
			offset+=maxRequestTimeTLV.getTotalTLVLength();
		}
		if (pathSetupTLV!=null){
			System.arraycopy(pathSetupTLV.getTlv_bytes(), 0, object_bytes, offset, pathSetupTLV.getTotalTLVLength());
		}

		
	}
	/**
	 * Decode Request Parameters Object
	 */
	public void decode() throws MalformedPCEPObjectException {
		retry=(object_bytes[4]&0x80)==0x80;
		loose =(object_bytes[7]&0x20)==0x20;
		bidirect=(object_bytes[7]&0x10)==0x10;
		reopt =(object_bytes[7]&0x08)==0x08;
		prio = object_bytes[7]&0x07;
		supplyOF=(object_bytes[7]&0x40)==0x40;
		requestID=( (((long)object_bytes[8]&(long)0xFF)<<24) | (((long)object_bytes[9]&(long)0xFF)<<16) |( ((long)object_bytes[10]&(long)0xFF)<<8) |  ((long)object_bytes[11]& (long)0xFF) );
		
		Fbit = (ByteHandler.easyCopy(1,1,object_bytes[4]) == 1) ? true : false ;
		Nbit = (ByteHandler.easyCopy(2,2,object_bytes[4]) == 1) ? true : false ;
		Ebit = (ByteHandler.easyCopy(3,3,object_bytes[4]) == 1) ? true : false ;
		
		boolean endObject=false;
		if (this.ObjectLength<12){
			throw new MalformedPCEPObjectException();
		}
		if (this.ObjectLength==12){
			endObject=true;
		}
		int offset=12;
		while (!endObject) {
			int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
			int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
			switch (tlvtype){
			case ObjectParameters.PCEP_TLV_TYPE_MAX_REQ_TIME:
				maxRequestTimeTLV=new MaxRequestTimeTLV(this.getObject_bytes(), offset);				
				break;
			case ObjectParameters.PCEP_TLV_PATH_SETUP:
				pathSetupTLV=new PathSetupTLV(this.getObject_bytes(), offset);				
				break;					
			default:
				//Unknown or unexpected TLV found");
				//UnknownTLV unknownTLV = new UnknownTLV();
				//tLVList.add(unknownTLV);
				//FIXME: Que hacemos con los desconocidos????
				break;
			}
			offset=offset+tlvlength;
			if (offset>=ObjectLength){
				endObject=true;
			}
		}
	}
	
	//Getters and Setters
	
	public boolean isReopt() {
		return reopt;
	}

	public void setReopt(boolean reopt) {
		this.reopt = reopt;
	}

	public boolean isBidirect() {
		return bidirect;
	}

	public void setBidirect(boolean bidirect) {
		this.bidirect = bidirect;
	}

	public boolean isLoose() {
		return loose;
	}

	public void setLoose(boolean loose) {
		this.loose = loose;
	}

	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

	public long getRequestID() {
		return requestID;
	}
	
	public boolean isFbit() {
		return Fbit;
	}

	public void setFbit(boolean fbit) {
		Fbit = fbit;
	}

	public boolean isNbit() {
		return Nbit;
	}

	public void setNbit(boolean nbit) {
		Nbit = nbit;
	}

	public boolean isEbit() {
		return Ebit;
	}

	public void setEbit(boolean ebit) {
		Ebit = ebit;
	}

	public boolean isSupplyOF() {
		return supplyOF;
	}

	public void setSupplyOF(boolean supplyOF) {
		this.supplyOF = supplyOF;
	}
	
	

	public boolean isRetry() {
		return retry;
	}

	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	
	public MaxRequestTimeTLV getMaxRequestTimeTLV() {
		return maxRequestTimeTLV;
	}
	
	public PathSetupTLV getPathSetupTLV() {
		return pathSetupTLV;
	}
	
	public void setPathSetupTLV(PathSetupTLV pathSetupTLV) {
		this.pathSetupTLV = pathSetupTLV;
	}


	public void setMaxRequestTimeTLV(MaxRequestTimeTLV maxRequestTimeTLV) {
		this.maxRequestTimeTLV = maxRequestTimeTLV;
	}

	public String toString(){
		String str =  "<RP"+" ReqID: "+requestID+" Prio: "+prio+" Reopt: "+(reopt?1:0)+" Bid: "+(bidirect?1:0)+" Loose: "+(loose?1:0)+" SupOF: "+supplyOF+" retry "+retry;
		if (this.pathSetupTLV != null)
		{
			str += "<PathSetupTLV "+this.pathSetupTLV.toString()+">";
		}
		str+=">";
		return str;
	}

	
}