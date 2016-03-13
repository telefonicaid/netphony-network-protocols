package es.tid.rsvp.objects;

import java.util.LinkedList;
import org.slf4j.Logger;

import es.tid.rsvp.RSVPProtocolViolationException;
import es.tid.rsvp.objects.subobjects.*;
import org.slf4j.LoggerFactory;

/*

RFC 3209     RSVP-TE

  4.3. Explicit Route Object

   Explicit routes are specified via the EXPLICIT_ROUTE object (ERO).
   The Explicit Route Class is 20.  Currently one C_Type is defined,
   Type 1 Explicit Route.  The EXPLICIT_ROUTE object has the following
   format:

   Class = 20, C_Type = 1

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                        (Subobjects)                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

   Subobjects

   The contents of an EXPLICIT_ROUTE object are a series of variable-
   length data items called subobjects.  The subobjects are defined in
   section 4.3.3 below.

   If a Path message contains multiple EXPLICIT_ROUTE objects, only the
   first object is meaningful.  Subsequent EXPLICIT_ROUTE objects MAY be
   ignored and SHOULD NOT be propagated.


 */

public class ERO extends RSVPObject{

	/**
	 * List of Explicit Route Objects contained in an ERO Object.
	 */
	
	private LinkedList<EROSubobject> eroSubobjects;
	
	/**
	 * Log
	 */
		
	private static final Logger log = LoggerFactory.getLogger("ROADM");
	
	/**
	 * Constructor to be used when a new ERO Object wanted to be attached to a new message
	 */
	
	public ERO(){
		
		classNum = 20;
		cType = 1;
		length = 4;
		eroSubobjects = new LinkedList<EROSubobject>();
		
		log.debug("ERO Object Created");
	}
	
	/**
	 * Constructor to be used when a new ERO Object wanted to be decoded from a received
	 * message.
	 * @param bytes bytes
	 * @param offset offset
	 */
	
	public ERO(byte[] bytes, int offset){
		this.decodeHeader(bytes,offset);
		this.bytes = new byte[this.getLength()];
		System.arraycopy(bytes, offset, this.bytes, 0, this.getLength());
		eroSubobjects = new LinkedList<EROSubobject>();
		

		log.debug("ERO Object Created");
		try {
			decode(bytes,offset);
		} catch (RSVPProtocolViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to add a new ERO subobject to the ERO object.
	 * @param eroSO ERO Subobject
	 */
	
	public void addEROSubobject(EROSubobject eroSO){
		eroSubobjects.add(eroSO);
	//	this.length = this.length + eroSO.getErosolength();
	}
		
	/*

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                        (Subobjects)                          //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	 */
	
	@Override
	public void encode() throws RSVPProtocolViolationException{
		// Encontramos la longitud del objeto ERO
		this.length = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;	// Cabecera 
		
		int subObjectsNumber = eroSubobjects.size();
		for(int j = 0; j < subObjectsNumber; j++){
			EROSubobject eroSO = eroSubobjects.get(j);
			this.length = this.length + eroSO.getErosolength();
		}
		
		// Una vez se tiene la longitud completa, se codifica la cabecera
		
		this.bytes = new byte[this.length];
		encodeHeader();
		int currentIndex = RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;

		// Se codifica cada uno de los subobjetos
		
		for(int i = 0; i < subObjectsNumber; i++){
			EROSubobject eroSO = eroSubobjects.get(i);
			eroSO.encode();
			System.arraycopy(eroSO.getSubobject_bytes(), 0, this.bytes, currentIndex, eroSO.getErosolength());
			currentIndex = currentIndex + eroSO.getErosolength();
		}
	}
		
	public void decode(byte[] bytes, int offset) throws RSVPProtocolViolationException{

		int unprocessedBytes = length - RSVPObjectParameters.RSVP_OBJECT_COMMON_HEADER_SIZE;
		offset = offset + 4;  //Aumentar 4 bytes de cabecera
		while (unprocessedBytes > 0) {
			int subojectclass=EROSubobject.getType(bytes, offset);
			int subojectlength=EROSubobject.getLength(bytes, offset);
			
			switch(subojectclass) {
				case SubObjectValues.ERO_SUBOBJECT_IPV4PREFIX:
					IPv4prefixEROSubobject sobjt4=new IPv4prefixEROSubobject(bytes, offset);
					this.addEROSubobject(sobjt4);
					break;
			
				case SubObjectValues.ERO_SUBOBJECT_IPV6PREFIX:
					IPv6prefixEROSubobject sobjt6=new IPv6prefixEROSubobject(bytes, offset);
					addEROSubobject(sobjt6);
					break;		
				
				case SubObjectValues.ERO_SUBOBJECT_ASNUMBER:
					ASNumberEROSubobject sobjas=new ASNumberEROSubobject (bytes, offset);
					addEROSubobject(sobjas);
					break;
				case SubObjectValues.ERO_SUBOBJECT_UNNUMBERED_IF_ID:
					UnnumberIfIDEROSubobject subun=new UnnumberIfIDEROSubobject(bytes, offset);
					addEROSubobject(subun);
					break;
					
				case SubObjectValues.ERO_SUBOBJECT_LABEL:
					int ctype=LabelEROSubobject.getCType(bytes, offset);
					switch (ctype){
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_GENERALIZED_LABEL:
							GeneralizedLabelEROSubobject subgl=new GeneralizedLabelEROSubobject(bytes, offset);
							addEROSubobject(subgl);
							break;
						case SubObjectValues.ERO_SUBOBJECT_LABEL_CTYPE_WAVEBAND_LABEL:
							WavebandLabelEROSubobject subwl=new WavebandLabelEROSubobject(bytes, offset);
							addEROSubobject(subwl);
							break;
						default:
							break;							
					}
					break;
				default:
					log.error("ERO Subobject Unknown");
					//FIXME What do we do??
					break;
			}
			unprocessedBytes = unprocessedBytes - subojectlength;
			offset=offset+subojectlength;
		}
	}
	// Getters & Setters

	public LinkedList<EROSubobject> getEroSubobjects() {
		return eroSubobjects;
	}

	public void setEroSubobjects(LinkedList<EROSubobject> eroSubobjects) {
		this.eroSubobjects = eroSubobjects;
	}
	
}
