package es.tid.bgp.bgp4.update.fields;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*
The format of the Link-State NLRI is shown in the following figure.

0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|            NLRI Type          |     Total NLRI Length         |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                                                               |
//                  Link-State NLRI (variable)                 //
|                                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

       Figure 5: Link-State AFI 16388 / SAFI 71 NLRI Format




 */
public abstract class LinkStateNLRI extends NLRI {
	protected int NLRIType;
	
	protected int TotalNLRILength;
	

	
	protected static final Logger log = LoggerFactory.getLogger("BGP4Parser");
	
	public LinkStateNLRI(){
	}
	
	
	public LinkStateNLRI(byte []bytes, int offset) {
		this.NLRIType=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		int valueNLRILength=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		this.TotalNLRILength=valueNLRILength+4;
		this.bytes=new byte[TotalNLRILength];
		this.length=this.TotalNLRILength;
		System.arraycopy(bytes, offset, this.bytes, 0, TotalNLRILength);
	}
	
	protected void encodeHeader(){
		int nlriValueLength = TotalNLRILength-4;//ñapa para que quede bien el mensaje
		this.bytes[0]=(byte)(NLRIType>>>8 & 0xFF);
		this.bytes[1]=(byte)(NLRIType & 0xFF);
		this.bytes[2]=(byte)(nlriValueLength>>>8 & 0xFF);
		this.bytes[3]=(byte)(nlriValueLength & 0xFF);
	}
	

	
	public static int getTotalTLVLength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF)+4;
		if ((len%4)!=0){
			//Padding must be done!!
			len=len+4-(len%4);
		}		
		return len;
	}
	
	public static int getNLRILength(byte []bytes, int offset) {
		int len=((((int)bytes[offset+2]&0xFF)<<8)& 0xFF00) |  ((int)bytes[offset+3] & 0xFF);
		return len;
	}
	
	
	public static int getType(byte []bytes, int offset) {
		int typ=((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);
		return typ;
	}


	public int getNLRIType() {
		return NLRIType;
	}


	protected void setNLRIType(int nLRIType) {
		NLRIType = nLRIType;
	}


	public int getTotalNLRILength() {
		return TotalNLRILength;
	}


	protected void setTotalNLRILength(int totalNLRILength) {
		TotalNLRILength = totalNLRILength;
	}



	public static int getNLRIType(byte[] bytes, int offset){
		try {
			int obc= ((  ((int)bytes[offset]&0xFF)   <<8)& 0xFF00) |  ((int)bytes[offset+1] & 0xFF);//(int)(bytes[offset]&0xFF);
			return obc;
		}
		catch (ArrayIndexOutOfBoundsException e){
			return 0;
		}
	}



	public abstract void encode();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + NLRIType;
		result = prime * result + TotalNLRILength;
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
		LinkStateNLRI other = (LinkStateNLRI) obj;
		if (NLRIType != other.NLRIType)
			return false;
		if (TotalNLRILength != other.TotalNLRILength)
			return false;
		return true;
	}
	
	

}
