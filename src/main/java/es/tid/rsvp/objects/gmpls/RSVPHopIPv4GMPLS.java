package es.tid.rsvp.objects.gmpls;

import java.util.LinkedList;

import es.tid.rsvp.objects.RSVPHopIPv4;

public class RSVPHopIPv4GMPLS extends RSVPHopIPv4 {

	private LinkedList<IfIdTLV> tlvs;
	
	public RSVPHopIPv4GMPLS(){
		
		this.length = 12;
		super.cType = 3;
		this.tlvs = new LinkedList<IfIdTLV>();
		
	}
	
	public void addTLV(IfIdTLV tlv){
		
		tlvs.add(tlv);
		this.length = this.length + tlv.getLength();
		
	}

	public LinkedList<IfIdTLV> getTlvs() {
		return tlvs;
	}

	public void setTlvs(LinkedList<IfIdTLV> tlvs) {
		this.tlvs = tlvs;
	}

	@Override
	public void encode() {
		
		this.length = 12;	// Cabecera 
		
		int tlvsNumber = tlvs.size();
		for(int i = 0; i < tlvsNumber; i++){
			
			IfIdTLV ifId = tlvs.get(i);
			this.length = this.length + ifId.getLength();
			
			
		}
		
		this.bytes = new byte[this.length];
		
		encodeHeader();
		
		byte[] addr = next_previousHopAddress.getAddress();
		
		System.arraycopy(addr,0, getBytes(), 4, addr.length);
		
		int offset = 8; 
		
		this.bytes[offset] = (byte)(((long)this.logicalInterfaceHandle & 0xFF000000L) >> 24);
		this.bytes[offset+1] = (byte)(((long)this.logicalInterfaceHandle & 0x00FF0000L) >> 16);
		this.bytes[offset+2] = (byte)(((long)this.logicalInterfaceHandle & 0x0000FF00L) >> 8);
		this.bytes[offset+3] = (byte)((long)this.logicalInterfaceHandle & 0x000000FFL);
				
		int currentIndex = 12;

		// Se codifica cada uno de los subobjetos
		
		for(int j = 0; j < tlvsNumber; j++){
			
			IfIdTLV ifId = tlvs.get(j);
			ifId.encode();
			System.arraycopy(ifId.getBytes(), 0, this.bytes, currentIndex, ifId.getLength());
			currentIndex = currentIndex + ifId.getLength();
			
		}
		
	}
	
	
	
}
