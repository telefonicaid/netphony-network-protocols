package es.tid.pce.pcep.objects.tlvs.subtlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
 All PCEP TLVs have the following format:

   Type:   2 bytes
   Length: 2 bytes
   Value:  variable

   A PCEP object TLV is comprised of 2 bytes for the type, 2 bytes
   specifying the TLV length, and a value field.

   The Length field defines the length of the value portion in bytes.
   The TLV is padded to 4-bytes alignment; padding is not included in
   the Length field (so a 3-byte value would have a length of 3, but the
   total size of the TLV would be 8 bytes).

   Unrecognized TLVs MUST be ignored.

   IANA management of the PCEP Object TLV type identifier codespace is
   described in Section 9.

In GEYSERS, 
The Power sub-TLV is further structured in the sub-TLVs shown in Table 6.7.

Sub-TLV type	Sub-TLV name			Description	Occur

TBD				Power Info				Contains a set of details on the power sources
 										and efficiency	0..1
TBD				Maximum Consumption		Amount of power the resource consumes when 
										working at maximum load	0..1
TBD				Idle Consumption		Amount of power the resource consumes when in
 										idle (waiting for work) mode	0..1
TBD				Sleep Consumption		Amount of power the resource consumes when in
 										sleep (waiting for wake-up call) mode	0..1
TBD				Inter State Latencies	Contains the latencies required to move from 
										a state to another	0..1
TBD				State					Contains the current status of the device	0..1

 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class PowerSubTLV extends PCEPSubTLV {
	
	private PowerInfoSubTLV powerInfo;
	private MaximumConsumptionSubTLV maximumConsumption;
	private IdleConsumptionSubTLV idleConsumption;
	private SleepConsumptionSubTLV sleepConsumption;
	private InterStateLatenciesSubTLV interStateLatencies;
	private PowerStateSubTLV state;
	
	public PowerSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER);
		
	}
	
	public PowerSubTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode Power SubTLV
	 */
	public void encode() {
		
		int len=0;
		
		if (powerInfo!=null){
			powerInfo.encode();
			len=len+powerInfo.getTotalSubTLVLength();
		}
		
		if (maximumConsumption!=null){
			maximumConsumption.encode();
			len=len+maximumConsumption.getTotalSubTLVLength();
		}
		
		if (idleConsumption!=null){
			idleConsumption.encode();
			len=len+idleConsumption.getTotalSubTLVLength();
		}
		
		if (sleepConsumption!=null){
			sleepConsumption.encode();
			len=len+sleepConsumption.getTotalSubTLVLength();
		}
		
		if (interStateLatencies!=null){
			interStateLatencies.encode();
			len=len+interStateLatencies.getTotalSubTLVLength();
		}
		
		if (state!=null){
			state.encode();
			len=len+state.getTotalSubTLVLength();
		}
		
		this.setSubTLVValueLength(len);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		int offset = 4;
		
		if (powerInfo!=null){
			System.arraycopy(powerInfo.getSubTLV_bytes(),0,this.subtlv_bytes,offset,powerInfo.getTotalSubTLVLength());
			offset=offset+powerInfo.getTotalSubTLVLength();
		}
		
		if (maximumConsumption!=null){
			System.arraycopy(maximumConsumption.getSubTLV_bytes(),0,this.subtlv_bytes,offset,maximumConsumption.getTotalSubTLVLength());
			offset=offset+maximumConsumption.getTotalSubTLVLength();
		}
		
		if (idleConsumption!=null){
			System.arraycopy(idleConsumption.getSubTLV_bytes(),0,this.subtlv_bytes,offset,idleConsumption.getTotalSubTLVLength());
			offset=offset+idleConsumption.getTotalSubTLVLength();
		}
		
		if (sleepConsumption!=null){
			System.arraycopy(sleepConsumption.getSubTLV_bytes(),0,this.subtlv_bytes,offset,sleepConsumption.getTotalSubTLVLength());
			offset=offset+sleepConsumption.getTotalSubTLVLength();
		}
		
		if (interStateLatencies!=null){
			System.arraycopy(interStateLatencies.getSubTLV_bytes(),0,this.subtlv_bytes,offset,interStateLatencies.getTotalSubTLVLength());
			offset=offset+interStateLatencies.getTotalSubTLVLength();
		}
		
		if (state!=null){
			System.arraycopy(state.getSubTLV_bytes(),0,this.subtlv_bytes,offset,state.getTotalSubTLVLength());
			offset=offset+state.getTotalSubTLVLength();
		}
	}
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getSubTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getSubTLV_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getSubTLV_bytes(), offset);
			log.info("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER_INFO:
				log.info("Power Info found");
				this.powerInfo=new PowerInfoSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MAXIMUM_CONSUMPTION:
				log.info("Maximum Consumption found");
				this.maximumConsumption=new MaximumConsumptionSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_IDLE_CONSUMPTION:
				log.info("Idle Consumption found");
				this.idleConsumption=new IdleConsumptionSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_SLEEP_CONSUMPTION:
				log.info("Sleep Consumption found");
				this.sleepConsumption=new SleepConsumptionSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_INTER_STATE_LATENCIES:
				log.info("Inter State Latencies found");
				this.interStateLatencies=new InterStateLatenciesSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_POWER_STATE:
				log.info("Power State found");
				this.state=new PowerStateSubTLV(this.getSubTLV_bytes(), offset);
				break;
			
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getSubTLVValueLength()+4)){
				log.info("No more SubTLVs in Power Sub-TLV");
				fin=true;
			}

		}
	}
	
	
	
	public void setPowerInfo(PowerInfoSubTLV powerInfo) {
		this.powerInfo = powerInfo;
	}
	
	public PowerInfoSubTLV getPowerInfo() {
		return powerInfo;
	}

	public void setMaximumConsumption(MaximumConsumptionSubTLV maximumConsumption) {
		this.maximumConsumption = maximumConsumption;
	}
	
	public MaximumConsumptionSubTLV getMaximumConsumptionSubTLV() {
		return maximumConsumption;
	}
	
	public void setIdleConsumption(IdleConsumptionSubTLV idleConsumption) {
		this.idleConsumption = idleConsumption;
	}
	
	public IdleConsumptionSubTLV getIdleConsumption() {
		return idleConsumption;
	}
	
	public void setSleepConsumption(SleepConsumptionSubTLV sleepConsumption) {
		this.sleepConsumption = sleepConsumption;
	}
	
	public SleepConsumptionSubTLV getSleepConsumption() {
		return sleepConsumption;
	}
	
	
	public void setInterStateLatencies(InterStateLatenciesSubTLV interStateLatencies) {
		this.interStateLatencies = interStateLatencies;
	}
	
	public InterStateLatenciesSubTLV getInterStateLatencies() {
		return interStateLatencies;
	}
	
	public void setPowerState(PowerStateSubTLV state) {
		this.state = state;
	}
	
	public PowerStateSubTLV getPowerState() {
		return state;
	}
	
}