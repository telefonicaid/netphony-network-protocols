
//************************* RUBEN ***********************************


package es.tid.bgp.bgp4.update.tlv.linkstate_attribute_tlvs;

import es.tid.bgp.bgp4.update.tlv.BGP4TLVFormat;


public class MF_OTPAttribTLV extends BGP4TLVFormat{

	private int Type;				//16
	private int Length;				//16
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
	
	// Aggregated Optical Spectrum for subTx
  /*private int subTxLabelTypeTLV;	//16
    private int subTxLength1;		//16
	private int subTxPriority;		//8
	private int subTxReserved;		//24
	private int subTxAct;			//4
	private int subTxNumLabels;		//12
	private int subTxLength2;		//16
	private int subTxGrid;			//3
	private int subTxCS;			//4
	private int subTxIdentifier;	//9
	private int subTxn;				//16
	private int subTxBitMap; 		//32
  */
	
	
	
	// Aggregated Optical Spectrum for subTx
	
	
	
	
	public MF_OTPAttribTLV(){
		super();
		this.setTLVType(LinkStateAttributeTLVTypes.LINK_ATTRIBUTE_TLV_TYPE_MF_OTP);
	}
	
	public MF_OTPAttribTLV(byte[] bytes, int offset){
		super(bytes,offset);
		decode();
	}

	@Override
	public void encode() {
		/*int offset = 1;
		
	    tlv_bytes[offset]=(byte)((trans_app_code>>24) & 0xFF);
	    tlv_bytes[offset+1]=(byte)((trans_app_code>>16) & 0xFF);
		tlv_bytes[offset+2]=(byte)((trans_app_code>>8) & 0xFF);
		tlv_bytes[offset+3]=(byte)(trans_app_code & 0xFF);	
		
		tlv_bytes[offset+4]=(byte)((trans_class>>24) & 0xFF);
	    tlv_bytes[offset+5]=(byte)((trans_class>>16) & 0xFF);
		tlv_bytes[offset+6]=(byte)((trans_class>>8) & 0xFF);
		tlv_bytes[offset+7]=(byte)(trans_class & 0xFF);	
		 */ 
	}
	
	protected void decode(){
		/*int offset = 1;
		
		trans_app_code = ( (((int)tlv_bytes[offset]&(int)0xFF)<<24) | (((int)tlv_bytes[offset+1]&(int)0xFF)<<16) | (((int)tlv_bytes[offset+2]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+3]&(int)0xFF) );
		trans_class = ( (((int)tlv_bytes[offset+4]&(int)0xFF)<<24) | (((int)tlv_bytes[offset+5]&(int)0xFF)<<16) | (((int)tlv_bytes[offset+6]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+7]&(int)0xFF) );
		*/
		
		int offset = 4;
				
		log.info("******************* Decodificando MF OTP *****************");
		
		Length = (this.getTLVValueLength()/4);
		log.info("Longitud MF OTP: "+Length+".");
	   	
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
		
		Reserved = (((int)tlv_bytes[offset]&(int)0xFF));
		log.info("Valor de Reserved del MF_OTP: "+Reserved+".");
		
		offset = offset + 1;
		
		MinWidth = (((int)tlv_bytes[offset]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+1]&(int)0xFF);
		log.info("Valor de MinWidth del MF_OTP: "+MinWidth+".");
		
		offset = offset + 1;
		
		MaxWidth = (((int)tlv_bytes[offset]&(int)0xFF)<<8) | ((int)tlv_bytes[offset+1]&(int)0xFF);
		log.info("Valor de MaxWidth del MF_OTP: "+MaxWidth+".");
		
		log.info("***************** FIN Decodificando MF OTP ***************");
	}

/*
	public float getTransAppCode() {
		return trans_app_code;
	}

	public void getTransAppCode(int trans_app_code) {
		this.trans_app_code = trans_app_code;
	}

	public float getTransClass() {
		return trans_class;
	}

	public void getTransClass(int trans_class) {
		this.trans_class = trans_class;
	}	
	
	public String toString(){
		String str =  "<RP" + " Transceiver Application Code: " + trans_app_code + " Transceiver Class: " + trans_class;
		str+=">";
		return str;
	}
	
	*/
	
	public String toString(){
		String str =  "<MFOTP>";
		return str;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getLength() {
		return Length;
	}

	public void setLength(int length) {
		Length = length;
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
	
	public MF_OTPAttribTLV duplicate(){
		MF_OTPAttribTLV mm=new MF_OTPAttribTLV();
		mm.AvailRxTrnsp=this.AvailRxTrnsp;
		mm.AvailTxSTrnsp=this.AvailTxSTrnsp;
		mm.CFG=this.CFG;
		mm.Encoding=this.Encoding;
		return mm;
		
	}

}
