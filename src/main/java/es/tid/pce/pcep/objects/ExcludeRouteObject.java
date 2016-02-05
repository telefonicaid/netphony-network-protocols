package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.subobjects.ASNumberXROSubobject;
import es.tid.pce.pcep.objects.subobjects.DataPathIDXROSubobject;
import es.tid.pce.pcep.objects.subobjects.IPv4PrefixXROSubobject;
import es.tid.pce.pcep.objects.subobjects.IPv6prefixXROSubobject;
import es.tid.pce.pcep.objects.subobjects.SRLGXROSubobject;
import es.tid.pce.pcep.objects.subobjects.UnnumberIfIDXROSubobject;
import es.tid.pce.pcep.objects.subobjects.XROSubObjectValues;
import es.tid.pce.pcep.objects.subobjects.XROSubobject;


/**
 * Exclude Route Object from RFC 5521
 * 
 * 2.1. Exclude Route Object (XRO)


2.1.1. Definition


   The XRO is OPTIONAL and MAY be carried within Path Computation
   Request (PCReq) and Path Computation Reply (PCRep) messages.

   When present in a PCReq message, the XRO provides a list of network
   resources that the PCE is requested to exclude from the path that it
   computes.  Flags associated with each list member instruct the PCE as
   to whether the network resources must be excluded from the computed
   path, or whether the PCE should make best efforts to exclude the
   resources from the computed path.

   The XRO MAY be used on a PCRep message that carries the NO-PATH
   object (i.e., one that reports a path computation failure) to
   indicate the set of elements of the original XRO that prevented the
   PCE from finding a path.

   The XRO MAY also be used on a PCRep message for a successful path
   computation when the PCE wishes to provide a set of exclusions to be
   signaled during LSP setup using the extensions to Resource
   Reservation Protocol (RSVP)-TE [RFC4874].

   The XRO Object-Class is 17.

   The XRO Object-Type is 1.

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |        Reserved               |   Flags                     |F|
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      //                        (Subobjects)                         //
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                          Figure 1: XRO Body Format

   Reserved: 16 bits - MUST be set to zero on transmission and SHOULD be
   ignored on receipt.

   Flags: 16 bits - The following flags are currently defined:

      F (Fail - 1 bit): when set, the requesting PCC requires the
      computation of a new path for an existing TE LSP that has failed.
      If the F bit is set, the path of the existing TE LSP MUST be
      provided in the PCReq message by means of a Record Route Object
      (RRO) defined in [RFC5440].  This allows the path computation to
      take into account the previous path and reserved resources to
      avoid double bandwidth booking should the Traffic Engineering
      Database (TED) have not yet been updated or the corresponding
      resources not be yet been released.  This will usually be used in
      conjunction with the exclusion from the path computation of the
      failed resource that caused the LSP to fail.

   Subobjects: The XRO is made up of one or more subobject(s).  An XRO
   with no subobjects MUST NOT be sent and SHOULD be ignored on receipt.

   In the following subobject definitions, a set of fields have
   consistent meaning as follows:

   X
      The X-bit indicates whether the exclusion is mandatory or desired.
      0 indicates that the resource specified MUST be excluded from the
      path computed by the PCE.  1 indicates that the resource specified
      SHOULD be excluded from the path computed by the PCE, but MAY be

      included subject to PCE policy and the absence of a viable path
      that meets the other constraints and excludes the resource.

   Type
      The type of the subobject.  The following subobject types are
      defined.

      Type           Subobject
      -------------+-------------------------------
      1              IPv4 prefix
      2              IPv6 prefix
      4              Unnumbered Interface ID
      32             Autonomous system number
      34             SRLG

   Length
      The length of the subobject including the Type and Length fields.

   Prefix Length
      Where present, this field can be used to indicate a set of
      addresses matching a prefix.  If the subobject indicates a single
      address, the prefix length MUST be set to the full length of the
      address.

   Attribute
      The Attribute field indicates how the exclusion subobject is to be
      interpreted.

   0 Interface
      The subobject is to be interpreted as an interface or set of
      interfaces.  All interfaces identified by the subobject are to be
      excluded from the computed path according to the setting of the
      X-bit.  This value is valid only for subobject types 1, 2, and 3.

   1 Node
      The subobject is to be interpreted as a node or set of nodes.  All
      nodes identified by the subobject are to be excluded from the
      computed path according to the setting of the X-bit.  This value
      is valid only for subobject types 1, 2, 3, and 4.

   2 SRLG
      The subobject identifies an SRLG explicitly or indicates all of
      the SRLGs associated with the resource or resources identified by
      the subobject.  Resources that share any SRLG with those
      identified are to be excluded from the computed path according to
      the setting of the X-bit.  This value is valid for all subobjects.

   Reserved
      Reserved fields within subobjects MUST be transmitted as zero and
      SHOULD be ignored on receipt.
 * 
 * @author ogondio
 *
 */

public class ExcludeRouteObject extends PCEPObject {
	
	/**
	 *  F (Fail - 1 bit): when set, the requesting PCC requires the
      computation of a new path for an existing TE LSP that has failed.
      If the F bit is set, the path of the existing TE LSP MUST be
      provided in the PCReq message by means of a Record Route Object
      (RRO) defined in [RFC5440].  This allows the path computation to
      take into account the previous path and reserved resources to
      avoid double bandwidth booking should the Traffic Engineering
      Database (TED) have not yet been updated or the corresponding
      resources not be yet been released.  This will usually be used in
      conjunction with the exclusion from the path computation of the
      failed resource that caused the LSP to fail.
	 */
	private boolean fail;
	
	/**
	 * List of ERO subobjetcts
	 */
	public LinkedList<XROSubobject> XROSubobjectList;

	public ExcludeRouteObject() {
		super();
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_XRO);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_XRO);
		XROSubobjectList=new LinkedList<XROSubobject>();
	}
	
	/**
	 * Constructs a new ERO (Explicit Route Object) from a sequence of bytes
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts
	 * @throws MalformedPCEPObjectException Thrown if the decoded object is not well formed
	 */
	public ExcludeRouteObject (byte []bytes, int offset)throws MalformedPCEPObjectException{
		super(bytes, offset);
		XROSubobjectList=new LinkedList<XROSubobject>();
		decode();
	}
	
	@Override
	public void encode() {
		int len=8;//The four bytes of the header
		for (int k=0; k<XROSubobjectList.size();k=k+1){
			XROSubobjectList.get(k).encode();			
			len=len+XROSubobjectList.get(k).getErosolength();
		}
		ObjectLength=len;
		this.object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=0;
		object_bytes[5]=0;
		object_bytes[6]=0;
		object_bytes[7]=(byte)((fail?1:0));
		int pos=8;
		for (int k=0 ; k<XROSubobjectList.size(); k=k+1) {					
			System.arraycopy(XROSubobjectList.get(k).getSubobject_bytes(),0, this.object_bytes, pos, XROSubobjectList.get(k).getErosolength());
			pos=pos+XROSubobjectList.get(k).getErosolength();
		}				

	}

	@Override
	public void decode() throws MalformedPCEPObjectException {
		boolean fin=false;
		int offset=8;//Position of the next subobject
		if (ObjectLength==8){
			fin=true;
		}
		
		fail=(object_bytes[7]&0x01)==0x01;
		while (!fin) {
			int subojectclass=XROSubobject.getType(this.getObject_bytes(), offset);
			int subojectlength=XROSubobject.getLength(this.getObject_bytes(), offset);
			switch(subojectclass) {
				case XROSubObjectValues.XRO_SUBOBJECT_IPV4PREFIX:
					IPv4PrefixXROSubobject sobjt4=new IPv4PrefixXROSubobject(this.getObject_bytes(), offset);
					this.addXROSubobject(sobjt4);
					break;
			
				case XROSubObjectValues.XRO_SUBOBJECT_IPV6PREFIX:
					IPv6prefixXROSubobject sobjt6=new IPv6prefixXROSubobject(this.getObject_bytes(), offset);
					addXROSubobject(sobjt6);
					break;		
				
				case XROSubObjectValues.XRO_SUBOBJECT_ASNUMBER:
					ASNumberXROSubobject sobjas=new ASNumberXROSubobject (this.getObject_bytes(), offset);
					addXROSubobject(sobjas);
					break;
				
				case XROSubObjectValues.XRO_SUBOBJECT_UNNUMBERED_IF_ID:
					UnnumberIfIDXROSubobject subun=new UnnumberIfIDXROSubobject(this.getObject_bytes(), offset);
					addXROSubobject(subun);
					break;
					
				case XROSubObjectValues.XRO_SUBOBJECT_DATAPATH_ID:
					DataPathIDXROSubobject subundpid=new DataPathIDXROSubobject(this.getObject_bytes(), offset);
					addXROSubobject(subundpid);
					break;	
					
				case XROSubObjectValues.XRO_SUBOBJECT_SRLG:
					SRLGXROSubobject subsrlg=new SRLGXROSubobject(this.getObject_bytes(), offset);
					addXROSubobject(subsrlg);
					break;					

				default:
					log.warn("XRO Subobject Unknown");
					fin=true;
					//FIXME What do we do??
					break;
			}
			offset=offset+subojectlength;
			if (offset>=ObjectLength){
				fin=true;
			}
		}

	}
	
	public void addXROSubobject(XROSubobject eroso){
		XROSubobjectList.add(eroso);
	}
	
	public void addEROSubobjectList(LinkedList<XROSubobject> erosovec){
		XROSubobjectList.addAll(erosovec);
	}

	public LinkedList<XROSubobject> getEROSubobjectList() {
		return XROSubobjectList;
	}

	public void setEROSubobjectList(LinkedList<XROSubobject> eROSubobjectList) {
		XROSubobjectList = eROSubobjectList;
	}
	

	public String toString(){
		StringBuffer sb=new StringBuffer(XROSubobjectList.size()*100);
		sb.append("<XRO: ");
		for (int i=0;i<XROSubobjectList.size();++i){
			sb.append(XROSubobjectList.get(i).toString());
		}
		sb.append(">");
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime
				* result
				+ ((XROSubobjectList == null) ? 0 : XROSubobjectList.hashCode());
		result = prime * result + (fail ? 1231 : 1237);
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
		ExcludeRouteObject other = (ExcludeRouteObject) obj;
		if (XROSubobjectList == null) {
			if (other.XROSubobjectList != null)
				return false;
		} else if (!XROSubobjectList.equals(other.XROSubobjectList))
			return false;
		if (fail != other.fail)
			return false;
		return true;
	}

	public boolean isfail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}
	
	

}
