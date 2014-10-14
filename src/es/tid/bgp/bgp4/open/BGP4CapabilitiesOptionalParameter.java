package es.tid.bgp.bgp4.open;

import java.util.LinkedList;
import java.util.logging.Logger;


/**
 * 
 * <a href="http://tools.ietf.org/html/rfc3392">RFC 3392</a>.
 * 
 *  This is an Optional Parameter that is used by a BGP speaker to convey
   to its BGP peer the list of capabilities supported by the speaker.

   The parameter contains one or more triples <Capability Code,
   Capability Length, Capability Value>, where each triple is encoded as
   shown below:
 * <pre>
       +------------------------------+
       | Capability Code (1 octet)    |
       +------------------------------+
       | Capability Length (1 octet)  |
       +------------------------------+
       | Capability Value (variable)  |
       +------------------------------+
 * </pre>
   The use and meaning of these fields are as follows:

      Capability Code:

         Capability Code is a one octet field that unambiguously
         identifies individual capabilities.

      Capability Length:

         Capability Length is a one octet field that contains the length
         of the Capability Value field in octets.

      Capability Value:

         Capability Value is a variable length field that is interpreted
         according to the value of the Capability Code field.


 * @author mcs
 *
 */
public class BGP4CapabilitiesOptionalParameter extends BGP4OptionalParameter{

	LinkedList<BGP4Capability> capabilityList;

	public BGP4CapabilitiesOptionalParameter(){
		log=Logger.getLogger("BGP4Parser");	
		this.type = BGP4OptionalParametersTypes.CAPABILITY_OPTIONAL_PARAMETER;
		capabilityList = new LinkedList<BGP4Capability>();
	}


	public BGP4CapabilitiesOptionalParameter(byte []bytes, int offset) {
		super(bytes, offset);
		capabilityList = new LinkedList<BGP4Capability>();
		decode();
	}

	public void encode() {
		this.parameterLength=0;
		for (int k=0; k<capabilityList.size();++k){
			capabilityList.get(k).encode();
			this.parameterLength+=capabilityList.get(k).getLength();
		}
		this.length=parameterLength+2;
		this.bytes=new byte[length];
		encodeOptionalParameterHeader();
		int offset=2;//2 is the header of the Optional Parameter in BGP4
		for (int k=0; k<capabilityList.size();++k){
			System.arraycopy(capabilityList.get(k).getBytes(),0,this.bytes,offset,capabilityList.get(k).getLength());
			offset=offset+capabilityList.get(k).getLength();
		}
	}

	public void decode() {
		int offset=2;
		while (offset<=this.getLength()) {
			int capabilityCode = BGP4Capability.getCapalitityCode(this.bytes, offset);
			if (capabilityCode == BGP4OptionalParametersTypes.CAPABILITY_CODE_MULTIPROTOCOLEXTENSION)
			{
				capabilityList.add(new  MultiprotocolExtensionCapabilityAdvertisement(this.bytes, offset));
			}
			else 
				log.finest("Unknown Capability Code");
		}


	}


	public LinkedList<BGP4Capability> getCapabilityList() {
		return capabilityList;
	}


	public void setCapabilityList(LinkedList<BGP4Capability> capabilityList) {
		this.capabilityList = capabilityList;
	}


}
