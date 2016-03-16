package es.tid.bgp.bgp4.update.tlv.complexFields;

import java.net.Inet4Address;
import java.util.LinkedList;


/**

Bernstein and Lee     Expires November 25, 2011                [Page 7]

Internet-Draft  General Network Element Constraint Encoding    May 2011

2.2. Label Set Field

 {@code
   Label Set Field is used within the <AvailableLabels> sub-TLV or the
   <SharedBackupLabels> sub-TLV, which is defined in Section 2.3.  and
   2.4. , respectively.}

   The general format for a label set is given below. This format uses
   the Action concept from [RFC3471] with an additional Action to define
   a "bit map" type of label set. The second 32 bit field is a base
   label used as a starting point in many of the specific formats.

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | Action|    Num Labels         |          Length               |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                          Base Label                           |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |     Additional fields as necessary per action                 |
     |


   Action:

         0 - Inclusive List

         1 - Exclusive List

         2 - Inclusive Range

         3 - Exclusive Range

         4 - Bitmap Set

   Num Labels is only meaningful for Action value of 4 (Bitmap Set). It
   indicates the number of labels represented by the bit map. See more
   detail in section 3.2.3.

   Length is the length in bytes of the entire field.

 * 
 * @author ogondio
 * @author Fernando Munoz del Nuevo
 */

public abstract class LabelSetField{
	
	protected int action;
	protected int numLabels;
	protected int length;
	protected LinkedList<Inet4Address> labels;
	protected byte[] bytes;
	
	
/*	public LabelSetField(){		
		this.labels = new LinkedList<Inet4Address>();
		
	}

	public LabelSetField(byte[] bytes, int offset) throws MalformedOSPFSubTLVException {
	
		this.length = (int) (((bytes[offset+2]<<8)& 0xFF00) |  (bytes[offset+3] & 0xFF));
		this.bytes = new byte[this.length];
		System.arraycopy(bytes, offset, this.bytes, 0, this.length);
		decodeHeader();

	}*/



	public void encodeHeader(){		
		int offset = 0;		
		this.bytes[offset]=(byte)((action << 4) | ((numLabels >> 8) & 0x0F));
		this.bytes[offset+1]=(byte)(numLabels & 0xff);
		this.bytes[offset+2]=(byte)((length >> 8) & 0xff);
		this.bytes[offset+3]=(byte)(length & 0xff);

		
	}

	
	public void decodeHeader(){
		int offset=0;
		this.action = (int) ((this.bytes[offset] & 0xF0) >> 4);
		this.numLabels = (int) (((this.bytes[offset]& 0x0F)<< 8)  | (this.bytes[offset+1] & 0xFF));		
	}
	public abstract void encode();
	
	public abstract void decode();

	public Inet4Address getBaseLabel(){
		
		return labels.getFirst();
				
	}
	
	public Inet4Address getLastLabel(){
		
		return labels.getLast();
		
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getNumLabels() {
		return numLabels;
	}

	public void setNumLabels(int numLabels) {
		this.numLabels = numLabels;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public LinkedList<Inet4Address> getLabels() {
		return labels;
	}

	public void setLabels(LinkedList<Inet4Address> labels) {
		this.labels = labels;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	@Override
	public String toString() {
		String ret="";
		ret=ret+"Action: "+ String.valueOf(action)+"\t";
		ret=ret+"Number of Labels: "+ String.valueOf(numLabels)+"\r\n";
		return ret;
	}

	
	
	
}
