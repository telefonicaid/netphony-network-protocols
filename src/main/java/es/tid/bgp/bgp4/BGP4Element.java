package es.tid.bgp.bgp4;



public interface BGP4Element {
		
		
		public void encode(); //throws PCEPProtocolViolationException;
		
		
		
		public byte[] getBytes();
		
		public int getLength();

	}

