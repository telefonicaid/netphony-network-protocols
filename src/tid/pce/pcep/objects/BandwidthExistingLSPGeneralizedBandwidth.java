package tid.pce.pcep.objects;

import java.util.LinkedList;

import tid.pce.pcep.PCEPProtocolViolationException;
import tid.pce.pcep.constructs.GeneralizedBandwidth;
import tid.pce.pcep.constructs.GeneralizedBandwidthSSON;
import tid.pce.pcep.objects.tlvs.PCEPTLV;


/**
 *  *    0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |    Bandwidth Spec Length      | Rev. Bandwidth Spec Length    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      | Bw Spec Type  |   Reserved                                    |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                     generalized bandwidth                     ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~          Optional : reverse generalized bandwidth             ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      ~                       Optional TLVs                           ~
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * @author ogondio
 *
 */
public class BandwidthExistingLSPGeneralizedBandwidth extends Bandwidth{
	
	protected int bwSpecLength = 0;
	protected int revBwSpecLength =0;
	protected int bwSpecType;
	
	protected GeneralizedBandwidth generalizedBandwidth;
	protected GeneralizedBandwidth reverseGeneralizedBandwidth;
		
	private LinkedList<PCEPTLV> optionalTLVs;
	
	public BandwidthExistingLSPGeneralizedBandwidth(){
		super();
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_BANDWIDTH_GEN_BW_EXISTING_TE_LSP);
	}
	public BandwidthExistingLSPGeneralizedBandwidth (byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes, offset);
		decode();
	}
	
	public void encode() {
		int length=4;
		if (generalizedBandwidth!=null) {
			try {
				generalizedBandwidth.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bwSpecLength=generalizedBandwidth.getLength();
			length =length+generalizedBandwidth.getLength();
			
		}
		if (reverseGeneralizedBandwidth!=null) {
			try {
				reverseGeneralizedBandwidth.encode();
			} catch (PCEPProtocolViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			revBwSpecLength=reverseGeneralizedBandwidth.getLength();
			length =length+reverseGeneralizedBandwidth.getLength();
		}
		
		for (int k=0; k<optionalTLVs.size();k=k+1){
			optionalTLVs.get(k).encode();			
			length=length+optionalTLVs.get(k).getTotalTLVLength();
		}
			
		this.setObjectLength(length);
		object_bytes=new byte[ObjectLength];
		encode_header();			
		int offset=4;
		this.getBytes()[offset]=(byte) ((bwSpecLength>>8)&0xFF);
		this.getBytes()[offset+1]=(byte) (bwSpecLength&0xFF);
		this.getBytes()[offset+2]=(byte) ((revBwSpecLength>>8)&0xFF);
		this.getBytes()[offset+3]=(byte) (revBwSpecLength&0xFF);
		this.getBytes()[offset+4]=(byte)bwSpecType;
		if (generalizedBandwidth!=null) {
			System.arraycopy(generalizedBandwidth.getBytes(), 0, this.getBytes(), offset, generalizedBandwidth.getLength());
			offset=offset+generalizedBandwidth.getLength();
			
		}
		if (reverseGeneralizedBandwidth!=null) {
			System.arraycopy(reverseGeneralizedBandwidth.getBytes(), 0, this.getBytes(), offset, reverseGeneralizedBandwidth.getLength());
			offset=offset+reverseGeneralizedBandwidth.getLength();
			
		}
		if (optionalTLVs!=null){
			for (int k=0 ; k<optionalTLVs.size(); k=k+1) {					
				System.arraycopy(optionalTLVs.get(k).getTlv_bytes(),0, this.object_bytes, offset, optionalTLVs.get(k).getTotalTLVLength());
				offset=offset+optionalTLVs.get(k).getTotalTLVLength();
			}
		}
	}
	@Override
	public void decode() throws MalformedPCEPObjectException {
		int offset=4;	
		bwSpecLength=0;
		for (int k = 0; k < 2; k++) {
			bwSpecLength = (bwSpecLength << 8) | (object_bytes[offset+k] & 0xff);
		} 
		offset = 6;
		revBwSpecLength=0;
		for (int k = 0; k < 2; k++) {
			revBwSpecLength = (revBwSpecLength << 8) | (object_bytes[offset+k] & 0xff);
		} 
		offset = 8;
		this.bwSpecType = object_bytes[offset]&0xFF;
		offset = 12;
		if (bwSpecLength==0){
			throw new MalformedPCEPObjectException();
		}else {
			if (this.bwSpecType == ObjectParameters.PCEP_GMPLS_GEN_BANDWIDTH_SSON){
				generalizedBandwidth = new GeneralizedBandwidthSSON ();
				generalizedBandwidth.decode(this.getBytes(),offset);
			}
			
		}
		offset = 12+bwSpecLength;
		if (revBwSpecLength!=0){

		}
		
	}
	public GeneralizedBandwidth getGeneralizedBandwidth() {
		return generalizedBandwidth;
	}
	public void setGeneralizedBandwidth(GeneralizedBandwidth generalizedBandwidth) {
		this.generalizedBandwidth = generalizedBandwidth;
		this.bwSpecType= generalizedBandwidth.getBwSpecType();
	}
	public GeneralizedBandwidth getReverseGeneralizedBandwidth() {
		return reverseGeneralizedBandwidth;
	}
	public void setReverseGeneralizedBandwidth(
			GeneralizedBandwidth reverseGeneralizedBandwidth) {
		this.reverseGeneralizedBandwidth = reverseGeneralizedBandwidth;
	}
	
	public Bandwidth duplicate(){
		Bandwidth bw= new BandwidthExistingLSPGeneralizedBandwidth();
		return bw;
	}
	
	
}
