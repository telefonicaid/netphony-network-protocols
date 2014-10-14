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

The NetworkSpec sub-TLV is further structured in the sub-TLVs:

Sub-TLV type	Sub-TLV name	Description	Occur

TBD				EP Address		The address of the IT resource. It is a string in
 								the format <protocol>: <address>:<port>	1
TBD				TNA				Transport Network Assigned name, compliant with
 								[OIF-UNI-2.0] specification	0..1
TBD				MTU				Maximum amount of data that can be processed per
 								frame in bytes	0..1
TBD				Max Speed		Maximum throughput that can be sustained	0..1
TBD				Network Adapter	Type of adapter for network connectivity	0..1
 
 * 
 * 
 * @author Alejandro Tovar de Dueñas
 *
 */
public class NetworkSpecSubTLV extends PCEPSubTLV {
	
	private EPaddressSubTLV epaddress;
	private TNAIPv4SubTLV tnaIPv4;
	private TNAIPv6SubTLV tnaIPv6;
	private TNANSAPSubTLV tnaNSAP;
	private MTUSubTLV mtu;
	private MaxSpeedSubTLV maxSpeed;
	private NetworkAdapterSubTLV networkAdapter;
	
	public NetworkSpecSubTLV(){
		this.setSubTLVType(PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_NETWORK_SPEC);
		
	}
	
	public NetworkSpecSubTLV(byte[] bytes, int offset) throws MalformedPCEPObjectException{
		super(bytes,offset);
		decode();
	}

	/**
	 * Encode RequestedCPUs TLV
	 */
	public void encode() {
		
		int len=0;
		
		if (epaddress!=null){
			epaddress.encode();
			len=len+epaddress.getTotalSubTLVLength();
		}else {
			//FIXME: THROW EXCEPTION??
		}
		
		if (tnaIPv4!=null){
			tnaIPv4.encode();
			len=len+tnaIPv4.getTotalSubTLVLength();
		}
		
		if (tnaIPv6!=null){
			tnaIPv6.encode();
			len=len+tnaIPv6.getTotalSubTLVLength();
		}
		
		if (tnaNSAP!=null){
			tnaNSAP.encode();
			len=len+tnaNSAP.getTotalSubTLVLength();
		}
		
		
		if (mtu!=null){
			mtu.encode();
			len=len+mtu.getTotalSubTLVLength();
		}
		
		if (maxSpeed!=null){
			maxSpeed.encode();
			len=len+maxSpeed.getTotalSubTLVLength();
		}
		
		if (networkAdapter!=null){
			networkAdapter.encode();
			len=len+networkAdapter.getTotalSubTLVLength();
		}
		
		
		this.setSubTLVValueLength(len);
		this.subtlv_bytes=new byte[this.getTotalSubTLVLength()];
		this.encodeHeader();
		int offset = 4;
		
		if (epaddress!=null){
			System.arraycopy(epaddress.getSubTLV_bytes(),0,this.subtlv_bytes,offset,epaddress.getTotalSubTLVLength());
			offset=offset+epaddress.getTotalSubTLVLength();
		}
		
		if (tnaIPv4!=null){
			System.arraycopy(tnaIPv4.getSubTLV_bytes(),0,this.subtlv_bytes,offset,tnaIPv4.getTotalSubTLVLength());
			offset=offset+tnaIPv4.getTotalSubTLVLength();
		}
		
		if (tnaIPv6!=null){
			System.arraycopy(tnaIPv6.getSubTLV_bytes(),0,this.subtlv_bytes,offset,tnaIPv6.getTotalSubTLVLength());
			offset=offset+tnaIPv6.getTotalSubTLVLength();
		}
		
		if (tnaNSAP!=null){
			System.arraycopy(tnaNSAP.getSubTLV_bytes(),0,this.subtlv_bytes,offset,tnaNSAP.getTotalSubTLVLength());
			offset=offset+tnaNSAP.getTotalSubTLVLength();
		}
		
		if (mtu!=null){
			System.arraycopy(mtu.getSubTLV_bytes(),0,this.subtlv_bytes,offset,mtu.getTotalSubTLVLength());
			offset=offset+mtu.getTotalSubTLVLength();
		}
		
		if (maxSpeed!=null){
			System.arraycopy(maxSpeed.getSubTLV_bytes(),0,this.subtlv_bytes,offset,maxSpeed.getTotalSubTLVLength());
			offset=offset+maxSpeed.getTotalSubTLVLength();
		}
		
		if (networkAdapter!=null){
			System.arraycopy(networkAdapter.getSubTLV_bytes(),0,this.subtlv_bytes,offset,networkAdapter.getTotalSubTLVLength());
			offset=offset+networkAdapter.getTotalSubTLVLength();
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
			log.finest("subTLVType: "+subTLVType+" subTLVLength: "+subTLVLength);
			switch (subTLVType){
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_EP_ADDRESS:
				log.finest("EP addres");
				this.epaddress=new EPaddressSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv4:
				log.finest("TNA IPv4 found");
				this.tnaIPv4=new TNAIPv4SubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_IPv6:
				log.finest("TNA IPv6 found");
				this.tnaIPv6=new TNAIPv6SubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_TNA_NSAP:
				log.finest("TNA NSAP found");
				this.tnaNSAP=new TNANSAPSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MTU:
				log.finest("MTU found");
				this.mtu=new MTUSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_MAX_SPEED:
				log.finest("Max Speed found");
				this.maxSpeed=new MaxSpeedSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			case PCEPSubTLVTypes.PCEP_SUBTLV_TYPE_NETWORK_ADAPTER:
				log.finest("Network adapter found");
				this.networkAdapter=new NetworkAdapterSubTLV(this.getSubTLV_bytes(), offset);
				break;
				
			
			}
			offset=offset+subTLVLength;
			if (offset>=(this.getSubTLVValueLength()+4)){
				log.finest("No more SubTLVs in Network Spec SubTLV");
				fin=true;
			}

		}
	}
	
	
	public void setEPaddress(EPaddressSubTLV epAddress) {
		this.epaddress = epAddress;
	}
	
	public EPaddressSubTLV getEPaddress() {
		return epaddress;
	}

	public void setTNAIPv4(TNAIPv4SubTLV tna) {
		this.tnaIPv4 = tna;
	}
	
	public TNAIPv4SubTLV getTNAIPv4() {
		return tnaIPv4;
	}
	
	public void setTNAIPv6(TNAIPv6SubTLV tna) {
		this.tnaIPv6 = tna;
	}
	
	public TNAIPv6SubTLV getTNAIPv6() {
		return tnaIPv6;
	}
	
	public void setTNANSAP(TNANSAPSubTLV tna) {
		this.tnaNSAP = tna;
	}
	
	public TNANSAPSubTLV getTNANSAP() {
		return tnaNSAP;
	}
	
	public void setMTU(MTUSubTLV mtu) {
		this.mtu = mtu;
	}
	
	public MTUSubTLV getMTU() {
		return mtu;
	}
	
	public MaxSpeedSubTLV getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(MaxSpeedSubTLV maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	public NetworkAdapterSubTLV getNetworkAdapter() {
		return networkAdapter;
	}
	
	public void setNetworkAdapter(NetworkAdapterSubTLV networkAdapter) {
		this.networkAdapter = networkAdapter;
	}
	
}