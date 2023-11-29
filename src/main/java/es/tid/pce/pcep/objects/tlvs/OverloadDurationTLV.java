package es.tid.pce.pcep.objects.tlvs;

import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.protocol.commons.ByteHandler;

/**
 * OVERLOAD-DURATION TLV (Type 2)
 * 
 *  The OVERLOAD-DURATION TLV may be included in
 *  the NOTIFICATION object that specifies the period of time
 *  during which no further request should be sent to the PCE.
 *  Once this period of time has elapsed, the PCE should no longer
 *  be considered in a congested state.
 * @author ogondio
 *
 */

public class OverloadDurationTLV extends PCEPTLV {
	
	/**
	 * The estimated PCE congestion duration in seconds
	 */
	public long overload_duration;
	
	public OverloadDurationTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_OVERLOAD_DURATION);
		
	}
	
	public OverloadDurationTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	
	public void encode() {
		this.setTLVValueLength(4);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		ByteHandler.encode4bytesLong(this.overload_duration,this.tlv_bytes,offset);

	}
	
	public void decode() {
		int offset=4;
		this.overload_duration=ByteHandler.decode4bytesLong(this.tlv_bytes,offset);
	}

	public long getOverload_duration() {
		return overload_duration;
	}

	public void setOverload_duration(long overload_duration) {
		this.overload_duration = overload_duration;
	}

	@Override
	public String toString() {
		return "OverloadDurationTLV [overload_duration=" + overload_duration + "]";
	}
	
	

}
