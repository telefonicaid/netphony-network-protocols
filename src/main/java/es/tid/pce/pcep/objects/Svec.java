/*
 * PCEP SVEC object definition
 *
 * 7.13.2. SVEC Object


   Section 7.13.1 details the circumstances under which it may be
   desirable and/or required to synchronize a set of path computation
   requests.  The SVEC (Synchronization VECtor) object allows a PCC to
   request the synchronization of a set of dependent or independent path
   computation requests.  The SVEC object is optional and may be carried
   within a PCReq message.

   The aim of the SVEC object carried within a PCReq message is to
   request the synchronization of M path computation requests.  The SVEC
   object is a variable-length object that lists the set of M path
   computation requests that must be synchronized.  Each path
   computation request is uniquely identified by the Request-ID-number
   carried within the respective RP object.  The SVEC object also
   contains a set of flags that specify the synchronization type.

   SVEC Object-Class is 11.

   SVEC Object-Type is 1.





Vasseur & Le Roux           Standards Track                    [Page 44]

 
RFC 5440                          PCEP                        March 2009


   The format of the SVEC object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Reserved    |                   Flags                 |S|N|L|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Request-ID-number #1                      |
   //                                                             //
   |                     Request-ID-number #M                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 18: SVEC Body Object Format

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Flags (24 bits):  Defines the potential dependency between the set of
      path computation requests.

      *  L (Link diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT have any link in common.

      *  N (Node diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT have any node in common.

      *  S (SRLG diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT share any SRLG (Shared Risk Link
         Group).

      In case of a set of M synchronized independent path computation
      requests, the bits L, N, and S are cleared.

   Unassigned flags MUST be set to zero on transmission and MUST be
   ignored on receipt.

   The flags defined above are not exclusive.

7.13.3. Handling of the SVEC Object


   The SVEC object allows a PCC to specify a list of M path computation
   requests that MUST be synchronized along with a potential dependency.
   The set of M path computation requests may be sent within a single
   PCReq message or multiple PCReq messages.  In the latter case, it is
   RECOMMENDED for the PCE to implement a local timer (called the

   SyncTimer) activated upon the receipt of the first PCReq message that
   contains the SVEC object after the expiration of which, if all the M
   path computation requests have not been received, a protocol error is
   triggered.  When a PCE receives a path computation request that
   cannot be satisfied (for example, because the PCReq message contains
   an object with the P bit set that is not supported), the PCE sends a
   PCErr message for this request (see Section 7.2), the PCE MUST cancel
   the whole set of related path computation requests and MUST send a
   PCErr message with Error-Type="Synchronized path computation request
   missing".

   Note that such PCReq messages may also contain non-synchronized path
   computation requests.  For example, the PCReq message may comprise N
   synchronized path computation requests that are related to RP 1, ...,
   RP N and are listed in the SVEC object along with any other path
   computation requests that are processed as normal.
   
  
 * @author Oscar Gonzalez de Dios
 */

package es.tid.pce.pcep.objects;

import java.util.ArrayList;

/**
 * <p> Represents a SVEC Object as defined in RFC 5440</p>
 * <p> From RFC 5440, Section 7.13.2. SVEC Object</p>
 * <pre>
   Section 7.13.1 details the circumstances under which it may be
   desirable and/or required to synchronize a set of path computation
   requests.  The SVEC (Synchronization VECtor) object allows a PCC to
   request the synchronization of a set of dependent or independent path
   computation requests.  The SVEC object is optional and may be carried
   within a PCReq message.

   The aim of the SVEC object carried within a PCReq message is to
   request the synchronization of M path computation requests.  The SVEC
   object is a variable-length object that lists the set of M path
   computation requests that must be synchronized.  Each path
   computation request is uniquely identified by the Request-ID-number
   carried within the respective RP object.  The SVEC object also
   contains a set of flags that specify the synchronization type.

   SVEC Object-Class is 11.

   SVEC Object-Type is 1.

   The format of the SVEC object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Reserved    |                   Flags                 |S|N|L|
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                     Request-ID-number #1                      |
   //                                                             //
   |                     Request-ID-number #M                      |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                  Figure 18: SVEC Body Object Format

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Flags (24 bits):  Defines the potential dependency between the set of
      path computation requests.

      *  L (Link diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT have any link in common.

      *  N (Node diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT have any node in common.

      *  S (SRLG diverse) bit: when set, this indicates that the
         computed paths corresponding to the requests specified by the
         following RP objects MUST NOT share any SRLG (Shared Risk Link
         Group).

      In case of a set of M synchronized independent path computation
      requests, the bits L, N, and S are cleared.

   Unassigned flags MUST be set to zero on transmission and MUST be
   ignored on receipt.

   The flags defined above are not exclusive.
</pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public class Svec extends PCEPObject{
	
	 /**
	  * L (Link diverse) bit: when set, this indicates that the
	  * computed paths corresponding to the requests specified by the
      * following RP objects MUST NOT have any link in common.
	  */
     private boolean lDiverseBit;
     /**
      * N (Node diverse) bit: when set, this indicates that the
     computed paths corresponding to the requests specified by the
     following RP objects MUST NOT have any node in common. 
      */
     private boolean nDiverseBit;
     /**
      * S (SRLG diverse) bit: when set, this indicates that the
     computed paths corresponding to the requests specified by the
     following RP objects MUST NOT share any SRLG (Shared Risk Link
     Group). 
      */
     private boolean sRLGDiverseBit;
     /**
      * 
      */
     private ArrayList<Long> requestIDlist;
	
     /**
      * Construct new SVEC Object from scratch
      */
	public Svec(){
		this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_SVEC);
		this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_SVEC);
		requestIDlist=new ArrayList<Long>();
		
	}
	
	/**
	 * Constructs a SVEC object from a sequence of bytes 
	 * @param bytes Sequence of bytes where the object is present
	 * @param offset Position at which the object starts 
	 * @throws MalformedPCEPObjectException Exception when the object is malformed
	 */
	public Svec(byte[] bytes, int offset)throws MalformedPCEPObjectException{		
		super(bytes,offset);
		requestIDlist=new ArrayList<Long>();		
		decode();
		
	}
	/**
	 * Encode the PCEP SVEC Object
	 */
	public void encode() {
		ObjectLength=8+requestIDlist.size()*4;	
		object_bytes=new byte[ObjectLength];
		encode_header();
		object_bytes[4]=0x00;
		object_bytes[5]=0x00;
		object_bytes[6]=0x00;	
		object_bytes[7]=(byte)( ( (lDiverseBit?1:0) & 0x01) | ( ((nDiverseBit?1:0) <<1) & 0x02) | (((sRLGDiverseBit?1:0)<<2) & 0x04) );
		for (int k=0;k<requestIDlist.size();k++){
			object_bytes[8+k*4]=(byte)((requestIDlist.get(k)>>24) & 0xFF);
			object_bytes[9+k*4]=(byte)((requestIDlist.get(k)>>16) & 0xFF);
			object_bytes[10+k*4]=(byte)((requestIDlist.get(k)>>8) & 0xFF);
			object_bytes[11+k*4]=(byte)(requestIDlist.get(k) & 0xFF);
		}
	}
	
	/**
	 * Decode the PCEP SVEC Object
	 */
	public void decode() throws MalformedPCEPObjectException{
		lDiverseBit =(object_bytes[7]&0x01)==0x01;
		nDiverseBit=(object_bytes[7]&0x02)==0x02;
		sRLGDiverseBit =(object_bytes[7]&0x04)==0x04;
		boolean fin=false;
		int offset=8;
		if (offset>=ObjectLength){				
			fin=true;
		}	
		while (!fin){
			long requestID=( (((long)object_bytes[offset]&(long)0xFF)<<24) | (((long)object_bytes[offset+1]&(long)0xFF)<<16) |( ((long)object_bytes[offset+2]&(long)0xFF)<<8) |  ((long)object_bytes[offset+3]& (long)0xFF) );			
			requestIDlist.add(requestID);
			offset=offset+4;
			if (offset>=ObjectLength){				
				fin=true;
			}		
		}		
		
	}
	public boolean isLDiverseBit() {
		return lDiverseBit;
	}
	public void setLDiverseBit(boolean lDiverseBit) {
		this.lDiverseBit = lDiverseBit;
	}
	public boolean isNDiverseBit() {
		return nDiverseBit;
	}
	public void setNDiverseBit(boolean nDiverseBit) {
		this.nDiverseBit = nDiverseBit;
	}
	public boolean isSRLGDiverseBit() {
		return sRLGDiverseBit;
	}
	public void setSRLGDiverseBit(boolean sRLGDiverseBit) {
		this.sRLGDiverseBit = sRLGDiverseBit;
	}
	public ArrayList<Long> getRequestIDlist() {
		return requestIDlist;
	}
	public void setRequestIDlist(ArrayList<Long> requestIDlist) {
		this.requestIDlist = requestIDlist;
	}
	
	public String toString(){
		return "Link diverse: "+lDiverseBit+" Node diverse: "+nDiverseBit+" SRLG diverse "+sRLGDiverseBit+"Requests List"+requestIDlist.toString();
	}
	
	public void addRequestID(long reqID){
		this.requestIDlist.add(new Long(reqID));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (lDiverseBit ? 1231 : 1237);
		result = prime * result + (nDiverseBit ? 1231 : 1237);
		result = prime * result
				+ ((requestIDlist == null) ? 0 : requestIDlist.hashCode());
		result = prime * result + (sRLGDiverseBit ? 1231 : 1237);
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
		Svec other = (Svec) obj;
		if (lDiverseBit != other.lDiverseBit)
			return false;
		if (nDiverseBit != other.nDiverseBit)
			return false;
		if (requestIDlist == null) {
			if (other.requestIDlist != null)
				return false;
		} else if (!requestIDlist.equals(other.requestIDlist))
			return false;
		if (sRLGDiverseBit != other.sRLGDiverseBit)
			return false;
		return true;
	}
	
	
	
	
	
	
}
