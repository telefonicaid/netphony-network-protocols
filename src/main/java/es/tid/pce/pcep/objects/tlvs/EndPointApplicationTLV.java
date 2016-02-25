package es.tid.pce.pcep.objects.tlvs;


import es.tid.pce.pcep.objects.MalformedPCEPObjectException;
import es.tid.pce.pcep.objects.ObjectParameters;
import es.tid.pce.pcep.objects.tlvs.subtlvs.ApplicationSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.OperativeSystemSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLV;
import es.tid.pce.pcep.objects.tlvs.subtlvs.PCEPSubTLVTypes;


/**
  * Application END-POINT TLV, Experimental & Propietary from GEYSERS.
  * Enconding: propietary based on GEYSERS project interoperability.
  * TLV Typer: 1002, non-standard
  * 
	Application description � in this case the End-point description field includes the
	description of the characteristics of the requested Application and is structured in
	a set of TLVs.


   The format of the Application END-POINT TLV object for is as follows:

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |         Type                  |  Length                       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          			                         |
     |							SubTLVs								 |
     |																 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 
 * @author Alejandro Tovar de Due�as
 *
 */
public class EndPointApplicationTLV extends PCEPTLV {
	
	
	public OperativeSystemSubTLV operativeSystem;
	public ApplicationSubTLV application;
	
	
	public EndPointApplicationTLV(){
		this.setTLVType(ObjectParameters.PCEP_TLV_TYPE_ENDPOINTS_APPLICATION);
		
	}
	
	public EndPointApplicationTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode
	 */
	public void encode() {
		
		int length=0;
		
		if (operativeSystem!=null){
			operativeSystem.encode();
			length=length+operativeSystem.getTotalSubTLVLength();
		}
		
		if (application!=null){
			application.encode();
			length=length+application.getTotalSubTLVLength();
		}
				
		this.setTLVValueLength(length);
		this.tlv_bytes=new byte[this.getTotalTLVLength()];
		this.encodeHeader();
		int offset=4;
		
		if (operativeSystem!=null){
			System.arraycopy(operativeSystem.getSubTLV_bytes(),0,this.tlv_bytes,offset,operativeSystem.getTotalSubTLVLength());
			offset=offset+operativeSystem.getTotalSubTLVLength();
		}
		
		if (application!=null){
			System.arraycopy(application.getSubTLV_bytes(),0,this.tlv_bytes,offset,application.getTotalSubTLVLength());
			offset=offset+application.getTotalSubTLVLength();
		}
		
	}

	
	public void decode() throws MalformedPCEPObjectException{
		boolean fin=false;
		int offset=4;//Position of the next subobject
		if (this.getTLVValueLength()==0){
			throw new MalformedPCEPObjectException();
		}
		while (!fin) {
			int subTLVType=PCEPSubTLV.getType(this.getTlv_bytes(), offset);
			int subTLVLength=PCEPSubTLV.getTotalSubTLVLength(this.getTlv_bytes(), offset);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_OS:
				this.operativeSystem = new OperativeSystemSubTLV(this.getTlv_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_APPLICATION:
				this.application = new ApplicationSubTLV(this.getTlv_bytes(), offset);
				break;
		
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getTLVValueLength()+4)){
				fin=true;
			}

		}
	}

	
	public OperativeSystemSubTLV getOperativeSystemSubTLV() {
		return operativeSystem;
	}

	public void setOperativeSystem (OperativeSystemSubTLV OperativeSystemSubTLV) {
		this.operativeSystem = OperativeSystemSubTLV;
	}

	public ApplicationSubTLV getApplicationSubTLV() {
		return application;
	}

	public void setApplicationSubTLV(ApplicationSubTLV ApplicationSubTLV) {
		this.application = ApplicationSubTLV;
	}

	public ApplicationSubTLV getApplication() {
		return application;
	}

	public void setApplication(ApplicationSubTLV application) {
		this.application = application;
	}

	public OperativeSystemSubTLV getOperativeSystem() {
		return operativeSystem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime * result
				+ ((operativeSystem == null) ? 0 : operativeSystem.hashCode());
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
		EndPointApplicationTLV other = (EndPointApplicationTLV) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (operativeSystem == null) {
			if (other.operativeSystem != null)
				return false;
		} else if (!operativeSystem.equals(other.operativeSystem))
			return false;
		return true;
	}
	
	
	
}