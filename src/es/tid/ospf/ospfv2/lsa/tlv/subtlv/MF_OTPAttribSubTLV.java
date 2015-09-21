
//************************* RUBEN ***********************************


package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;
import es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs.LinkStateAttributeTLVTypes;


public class MF_OTPAttribSubTLV extends OSPFSubTLV{

	private int MatrixID;			//8
	private int RstType;			//8
	private int Swcap;				//8
	private int Encoding;			//8	
	private int TxSubTrnsp;			//8
	private int RxSubTrnsp;			//8
	private int AvailTxSTrnsp;		//8
	private int AvailRxTrnsp;		//8
	private int CFG;				//8
	private int SWG;				//8
	private int Reserved;			//16
	private int MinWidth;			//16
	private int MaxWidth;			//16
	
	private LabelTypeTLV txAggregatedOpticalSpec;
	
	private LabelTypeTLV rxAggregatedOpticalSpec;
		
	
	public MF_OTPAttribSubTLV(){
		super();
		this.setTLVType(26);
	}
	
	public MF_OTPAttribSubTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		
		
		int valueLength = 16;
		
		log.info("******************* Codificando MF OTP *****************");
		
		
		
		if (txAggregatedOpticalSpec != null){
			
			txAggregatedOpticalSpec.encode();
			valueLength += txAggregatedOpticalSpec.getTotalTLVLength();
		}
		
		if (rxAggregatedOpticalSpec != null){
			
			rxAggregatedOpticalSpec.encode();
			valueLength += rxAggregatedOpticalSpec.getTotalTLVLength();
		}
		
		this.setTLVValueLength(valueLength);
		
		this.tlv_bytes = new byte[this.getTotalTLVLength()];
		
		this.encodeHeader();
		int offset = 4;
		tlv_bytes[offset]=(byte)((MatrixID) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((RstType) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((Swcap) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((Encoding) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((TxSubTrnsp) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((RxSubTrnsp) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((AvailTxSTrnsp) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((AvailRxTrnsp) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((CFG) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((SWG) & 0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((Reserved>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(Reserved&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((MinWidth>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(MinWidth&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)((MaxWidth>>8)&0xFF);
		
		offset = offset + 1;
		
		tlv_bytes[offset]=(byte)(MaxWidth&0xFF);
		
		offset = offset + 1;
		
		if (txAggregatedOpticalSpec != null){
			
			System.arraycopy(this.txAggregatedOpticalSpec.getTlv_bytes(),0 , this.tlv_bytes,offset,txAggregatedOpticalSpec.getTotalTLVLength() );	
		}
		offset= offset+txAggregatedOpticalSpec.getTotalTLVLength() ;
		if (rxAggregatedOpticalSpec != null){
			
			System.arraycopy(this.rxAggregatedOpticalSpec.getTlv_bytes(),0 , this.tlv_bytes,offset,rxAggregatedOpticalSpec.getTotalTLVLength() );
		}
		
		log.info("***************** FIN Codificando MF OTP ***************");
		
	}
	
	protected void decode(){
		
		int offset = 4;
				
		log.info("******************* Decodificando MF OTP *****************");
	   	
	   	MatrixID = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de MatrixID del MF_OTP: "+MatrixID+".");
				
		offset = offset + 1;
			
		RstType = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de RstType del MF_OTP: "+RstType+".");
		
		offset = offset + 1;
				
		Swcap = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de Swcap del MF_OTP: "+Swcap+".");
		
		offset = offset + 1;
				
		Encoding = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de Encoding del MF_OTP: "+Encoding+".");
		
		offset = offset + 1;
		
		TxSubTrnsp = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de TxSubTrnsp del MF_OTP: "+TxSubTrnsp+".");
		
		offset = offset + 1;
		
		RxSubTrnsp = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de RxSubTrnsp del MF_OTP: "+RxSubTrnsp+".");
		
		offset = offset + 1;
		
		AvailTxSTrnsp = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de AvailTxSTrnsp del MF_OTP: "+AvailTxSTrnsp+".");
		
		offset = offset + 1;
				
		AvailRxTrnsp = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de AvailRxTrnsp del MF_OTP: "+AvailRxTrnsp+".");
		
		offset = offset + 1;
		
		CFG = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de CFG del MF_OTP: "+CFG+".");
		
		offset = offset + 1;
		
		SWG = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de SWG del MF_OTP: "+SWG+".");
		
		offset = offset + 1;
		
		Reserved = (((int)tlv_bytes[offset]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+1]&(int)0xFF);
		log.info("Valor de Reserved del MF_OTP: "+Reserved+".");
		
		offset = offset +1 + 1;
		
		MinWidth = (((int)tlv_bytes[offset]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+1]&(int)0xFF);
		log.info("Valor de MinWidth del MF_OTP: "+MinWidth+".");
		
		offset = offset + 1 + 1;
		
		MaxWidth = (((int)tlv_bytes[offset]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+1]&(int)0xFF);
		log.info("Valor de MaxWidth del MF_OTP: "+MaxWidth+".");
		
		offset = offset + 1 + 1;
		
		log.info("Lenght: "+this.getTotalTLVLength());
		log.info("offset: "+offset);
		
		txAggregatedOpticalSpec = new LabelTypeTLV(tlv_bytes,offset);
		
		offset += txAggregatedOpticalSpec.getTotalTLVLength();
		
		rxAggregatedOpticalSpec = new LabelTypeTLV(tlv_bytes,offset);
		
		log.info("***************** FIN Decodificando MF OTP ***************");
	}


	public LabelTypeTLV getTxAggregatedOpticalSpec() {
		return txAggregatedOpticalSpec;
	}

	public void setTxAggregatedOpticalSpec(LabelTypeTLV txAggregatedOpticalSpec) {
		this.txAggregatedOpticalSpec = txAggregatedOpticalSpec;
	}

	public LabelTypeTLV getRxAggregatedOpticalSpec() {
		return rxAggregatedOpticalSpec;
	}

	public void setRxAggregatedOpticalSpec(LabelTypeTLV rxAggregatedOpticalSpec) {
		this.rxAggregatedOpticalSpec = rxAggregatedOpticalSpec;
	}

	public String toString(){
		String str =  "<MFOTP" + " MatrixID: " + MatrixID + "\n RstType: " + RstType + "\n Swcap " + Swcap + "\n Encoding: " + Encoding + "\n TxSubTrnsp: " + TxSubTrnsp + "\n RxSubTrnsp: " + RxSubTrnsp + "\n AvailTxSTrnsp: " + AvailTxSTrnsp + "\n AvailRxTrnsp: " + AvailRxTrnsp + "\n CFG: " + CFG + "\n SWG: " + SWG + "\n Reserved: " + Reserved + "\n MinWidth: " + MinWidth + "\n MaxWidth: " + MaxWidth + "\n TxAggregatedOpticalSpec: " + txAggregatedOpticalSpec.toString() + "\n RxAggregatedOpticalSpec: " + rxAggregatedOpticalSpec.toString();
		str+=">";
		return str;
	}


	public int getMatrixID() {
		return MatrixID;
	}

	public void setMatrixID(int matrixID) {
		MatrixID = matrixID;
	}

	public int getRstType() {
		return RstType;
	}

	public void setRstType(int rstType) {
		RstType = rstType;
	}

	public int getSwcap() {
		return Swcap;
	}

	public void setSwcap(int swcap) {
		Swcap = swcap;
	}

	public int getEncoding() {
		return Encoding;
	}

	public void setEncoding(int encoding) {
		Encoding = encoding;
	}

	public int getTxSubTrnsp() {
		return TxSubTrnsp;
	}

	public void setTxSubTrnsp(int txSubTrnsp) {
		TxSubTrnsp = txSubTrnsp;
	}

	public int getRxSubTrnsp() {
		return RxSubTrnsp;
	}

	public void setRxSubTrnsp(int rxSubTrnsp) {
		RxSubTrnsp = rxSubTrnsp;
	}

	public int getAvailTxSTrnsp() {
		return AvailTxSTrnsp;
	}

	public void setAvailTxSTrnsp(int availTxSTrnsp) {
		AvailTxSTrnsp = availTxSTrnsp;
	}

	public int getAvailRxTrnsp() {
		return AvailRxTrnsp;
	}

	public void setAvailRxTrnsp(int availRxTrnsp) {
		AvailRxTrnsp = availRxTrnsp;
	}

	public int getCFG() {
		return CFG;
	}

	public void setCFG(int cFG) {
		CFG = cFG;
	}

	public int getSWG() {
		return SWG;
	}

	public void setSWG(int sWG) {
		SWG = sWG;
	}

	public int getReserved() {
		return Reserved;
	}

	public void setReserved(int reserved) {
		Reserved = reserved;
	}

	public int getMinWidth() {
		return MinWidth;
	}

	public void setMinWidth(int minWidth) {
		MinWidth = minWidth;
	}

	public int getMaxWidth() {
		return MaxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		MaxWidth = maxWidth;
	}
	
		
	public MF_OTPAttribSubTLV duplicate(){
		MF_OTPAttribSubTLV mm=new MF_OTPAttribSubTLV();
		  mm.AvailRxTrnsp=this.AvailRxTrnsp;
		  mm.AvailTxSTrnsp=this.AvailTxSTrnsp;
		  mm.CFG=this.CFG;
		  mm.Encoding=this.Encoding;
		  mm.MatrixID=this.MatrixID;
		  mm.MaxWidth=this.MaxWidth;
		  mm.MinWidth=this.MinWidth;
		  mm.Reserved=this.Reserved;
		  mm.RstType=this.RstType;
		  mm.SWG=this.SWG;
		  mm.Swcap=this.Swcap;
		  mm.TxSubTrnsp=this.TxSubTrnsp;
		  mm.RxSubTrnsp=this.RxSubTrnsp;
		  mm.txAggregatedOpticalSpec = this.txAggregatedOpticalSpec.duplicate();
		  mm.rxAggregatedOpticalSpec = this.rxAggregatedOpticalSpec.duplicate();
		  
		  return mm;
		
	}

}
