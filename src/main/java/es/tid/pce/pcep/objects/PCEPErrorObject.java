package es.tid.pce.pcep.objects;

import java.util.LinkedList;

import es.tid.pce.pcep.objects.tlvs.PCEPTLV;
import es.tid.pce.pcep.objects.tlvs.ReqMissingTLV;

/** 
 * <p> Represents a PCEP Error Object, as defined in RFC 5440</p>
 * <p> From RFC 5440 Section 7.15. PCEP-ERROR Object</p>
<pre>
   The PCEP-ERROR object is exclusively carried within a PCErr message
   to notify of a PCEP error.

   PCEP-ERROR Object-Class is 13.

   PCEP-ERROR Object-Type is 1.

   The format of the PCEP-ERROR object body is as follows:

    0                   1                   2                   3
    0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |   Reserved    |      Flags    |   Error-Type  |  Error-value  |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
   |                                                               |
   //                     Optional TLVs                           //
   |                                                               |
   +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

               Figure 20: PCEP-ERROR Object Body Format

   A PCEP-ERROR object is used to report a PCEP error and is
   characterized by an Error-Type that specifies the type of error and
   an Error-value that provides additional information about the error
   type.  Both the Error-Type and the Error-value are managed by IANA
   (see the IANA section).

   Reserved (8 bits):  This field MUST be set to zero on transmission
      and MUST be ignored on receipt.

   Flags (8 bits):  no flag is currently defined.  This flag MUST be set
      to zero on transmission and MUST be ignored on receipt.

   Error-Type (8 bits):  defines the class of error.

   Error-value (8 bits):  provides additional details about the error.

   Optionally, the PCEP-ERROR object may contain additional TLVs so as
   to provide further information about the encountered error.

   A single PCErr message may contain multiple PCEP-ERROR objects.

   For each PCEP error, an Error-Type and an Error-value are defined.

   Error-Type    Meaning
      1          PCEP session establishment failure
                 Error-value=1: reception of an invalid Open message or
                                a non Open message.
                 Error-value=2: no Open message received before the
                                expiration of the OpenWait timer
                 Error-value=3: unacceptable and non-negotiable session
                                characteristics
                 Error-value=4: unacceptable but negotiable session
                                characteristics
                 Error-value=5: reception of a second Open message with
                                still unacceptable session
                                characteristics
                 Error-value=6: reception of a PCErr message proposing
                                unacceptable session characteristics
                 Error-value=7: No Keepalive or PCErr message received
                                before the expiration of the KeepWait
                                timer
      2          Capability not supported
      3          Unknown Object
                  Error-value=1: Unrecognized object class
                  Error-value=2: Unrecognized object Type
      4          Not supported object
                  Error-value=1: Not supported object class
                  Error-value=2: Not supported object Type
      5          Policy violation
                  Error-value=1: C bit of the METRIC object set
                                 (request rejected)
                  Error-value=2: O bit of the RP object set
                                 (request rejected)
      6          Mandatory Object missing
                  Error-value=1: RP object missing
                  Error-value=2: RRO object missing for a reoptimization
                                 request (R bit of the RP object set)
                                 when bandwidth is not equal to 0.
                  Error-value=3: END-POINTS object missing
      7          Synchronized path computation request missing
      8          Unknown request reference
      9          Attempt to establish a second PCEP session
      10         Reception of an invalid object
                  Error-value=1: reception of an object with P flag not
                  set although the P flag must be set according to this
                  specification.

   The error types listed above are described below.

   Error-Type=1: PCEP session establishment failure.

      If a malformed message is received, the receiving PCEP peer MUST
      send a PCErr message with Error-Type=1, Error-value=1.

      If no Open message is received before the expiration of the
      OpenWait timer, the receiving PCEP peer MUST send a PCErr message
      with Error-Type=1, Error-value=2 (see Appendix A for details).

      If one or more PCEP session characteristics are unacceptable by
      the receiving peer and are not negotiable, it MUST send a PCErr
      message with Error-Type=1, Error-value=3.

      If an Open message is received with unacceptable session
      characteristics but these characteristics are negotiable, the
      receiving PCEP peer MUST send a PCErr message with Error-Type-1,
      Error-value=4 (see Section 6.2 for details).

      If a second Open message is received during the PCEP session
      establishment phase and the session characteristics are still
      unacceptable, the receiving PCEP peer MUST send a PCErr message
      with Error-Type-1, Error-value=5 (see Section 6.2 for details).

      If a PCErr message is received during the PCEP session
      establishment phase that contains an Open message proposing
      unacceptable session characteristics, the receiving PCEP peer MUST
      send a PCErr message with Error-Type=1, Error-value=6.

      If neither a Keepalive message nor a PCErr message is received
      before the expiration of the KeepWait timer during the PCEP
      session establishment phase, the receiving PCEP peer MUST send a
      PCErr message with Error-Type=1, Error-value=7.

   Error-Type=2:  the PCE indicates that the path computation request
      cannot be honored because it does not support one or more required
      capability.  The corresponding path computation request MUST be
      cancelled.

   Error-Type=3 or Error-Type=4:  if a PCEP message is received that
      carries a PCEP object (with the P flag set) not recognized by the
      PCE or recognized but not supported, then the PCE MUST send a
      PCErr message with a PCEP-ERROR object (Error-Type=3 and 4,
      respectively).  In addition, the PCE MAY include in the PCErr
      message the unknown or not supported object.  The corresponding
      path computation request MUST be cancelled by the PCE without
      further notification.

   Error-Type=5:  if a path computation request is received that is not
      compliant with an agreed policy between the PCC and the PCE, the
      PCE MUST send a PCErr message with a PCEP-ERROR object (Error-
      Type=5).  The corresponding path computation MUST be cancelled.
      Policy-specific TLVs carried within the PCEP-ERROR object may be
      defined in other documents to specify the nature of the policy
      violation.

   Error-Type=6:  if a path computation request is received that does
      not contain a mandatory object, the PCE MUST send a PCErr message
      with a PCEP-ERROR object (Error-Type=6).  If there are multiple
      mandatory objects missing, the PCErr message MUST contain one
      PCEP-ERROR object per missing object.  The corresponding path
      computation MUST be cancelled.

   Error-Type=7:  if a PCC sends a synchronized path computation request
      to a PCE and the PCE does not receive all the synchronized path
      computation requests listed within the corresponding SVEC object
      after the expiration of the timer SyncTimer defined in
      Section 7.13.3, the PCE MUST send a PCErr message with a PCEP-
      ERROR object (Error-Type=7).  The corresponding synchronized path
      computation MUST be cancelled.  It is RECOMMENDED for the PCE to
      include the REQ-MISSING TLVs (defined below) that identify the
      missing requests.

      The REQ-MISSING TLV is compliant with the PCEP TLV format defined
      in section 7.1 and is comprised of 2 bytes for the type, 2 bytes
      specifying the TLV length (length of the value portion in bytes),
      followed by a fixed-length value field of 4 bytes.

         Type:   3
         Length: 4 bytes
         Value:  4 bytes that indicate the Request-ID-number that
                 corresponds to the missing request.

   Error-Type=8:  if a PCC receives a PCRep message related to an
      unknown path computation request, the PCC MUST send a PCErr
      message with a PCEP-ERROR object (Error-Type=8).  In addition, the
      PCC MUST include in the PCErr message the unknown RP object.

   Error-Type=9:  if a PCEP peer detects an attempt from another PCEP
      peer to establish a second PCEP session, it MUST send a PCErr
      message with Error-Type=9, Error-value=1.  The existing PCEP
      session MUST be preserved and all subsequent messages related to
      the tentative establishment of the second PCEP session MUST be
      silently ignored.

   Error-Type=10:  if a PCEP peers receives an object with the P flag
      not set although the P flag must be set according to this
      specification, it MUST send a PCErr message with Error-Type=10,
      Error-value=1.
</pre>
 * @author Oscar Gonzalez de Dios (ogondio@tid.es)
 *
 */
public class PCEPErrorObject extends PCEPObject{

		/**
		 * Error-Type (8 bits):  defines the class of error.
		 */
		private int errorType;
		/**
		 * Error-value (8 bits):  provides additional details about the error.
		 */
		private int errorValue;
		
		//private LinkedList<PCEPTLV> optionalTLVs;
		
		/**
		 * Optional REQ MISSING TLV
		 */
		private ReqMissingTLV reqMissing;
		//Constructors
		
		public PCEPErrorObject(){
			this.setObjectClass(ObjectParameters.PCEP_OBJECT_CLASS_PCEPERROR);
			this.setOT(ObjectParameters.PCEP_OBJECT_TYPE_PCEPERROR);
			//optionalTLVs=new LinkedList<PCEPTLV>();
			
		}
		
		public PCEPErrorObject(byte[] bytes,int offset) throws MalformedPCEPObjectException{
			super(bytes, offset);
			//optionalTLVs=new LinkedList<PCEPTLV>();
			decode();
		}
		
		//Encode and decode
		
		/**
		 * Encode PCEP Error Object
		 */
		public void encode() {
			int len=8;//The four bytes of the header and four bytes of the body
//			for (int k=0; k<optionalTLVs.size();k=k+1){
//				optionalTLVs.get(k).encode();			
//				len=len+optionalTLVs.get(k).getTotalTLVLength();
//			}
			if (reqMissing!=null){
				reqMissing.encode();
				len+=reqMissing.getTotalTLVLength();
			}
			this.ObjectLength=len;			
			this.object_bytes=new byte[ObjectLength];
			encode_header();
			this.object_bytes[4]=0x00;
			this.object_bytes[5]=0x00;
			this.object_bytes[6]=(byte)errorType;
			this.object_bytes[7]=(byte)errorValue;
			int pos=8;
//			for (int k=0 ; k<optionalTLVs.size(); k=k+1) {					
//				System.arraycopy(optionalTLVs.get(k).getTlv_bytes(),0, this.object_bytes, pos, optionalTLVs.get(k).getTotalTLVLength());
//				pos=pos+optionalTLVs.get(k).getTotalTLVLength();
//			}	
			if (reqMissing!=null){
				System.arraycopy(reqMissing.getTlv_bytes(),0, this.object_bytes, pos, reqMissing.getTotalTLVLength());
	
			}
		}

		/**
		 * Decode PCEP Error Object
		 */	
		public void decode() throws MalformedPCEPObjectException {
			if (ObjectLength<8){
				throw new MalformedPCEPObjectException();
			}
		 	errorType=this.object_bytes[6];
		 	errorValue=this.object_bytes[7];
		 	if (ObjectLength>8){
		 		//There are optional TLVs
		 		boolean fin=false;
		 		int offset=8;
		 		while (!fin) {
					int tlvtype=PCEPTLV.getType(this.getObject_bytes(), offset);
					int tlvlength=PCEPTLV.getTotalTLVLength(this.getObject_bytes(), offset);
					if (tlvtype==ObjectParameters.PCEP_TLV_REQ_MISSING_TLV){
						reqMissing = new ReqMissingTLV(this.getObject_bytes(), offset);
					}
					offset=offset+tlvlength;
					if (offset>=ObjectLength){
						//No more TLVs in Error
						fin=true;
					}
		 		}
		 	}
		}

		//Getters and Setters

		public int getErrorType() {
			return errorType;
		}

		public void setErrorType(int errorType) {
			this.errorType = errorType;
		}

		public int getErrorValue() {
			return errorValue;
		}

		public void setErrorValue(int errorValue) {
			this.errorValue = errorValue;
		}

		public ReqMissingTLV getReqMissing() {
			return reqMissing;
		}

		public void setReqMissing(ReqMissingTLV reqMissing) {
			this.reqMissing = reqMissing;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + errorType;
			result = prime * result + errorValue;
			result = prime * result
					+ ((reqMissing == null) ? 0 : reqMissing.hashCode());
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
			PCEPErrorObject other = (PCEPErrorObject) obj;
			if (errorType != other.errorType)
				return false;
			if (errorValue != other.errorValue)
				return false;
			if (reqMissing == null) {
				if (other.reqMissing != null)
					return false;
			} else if (!reqMissing.equals(other.reqMissing))
				return false;
			return true;
		}
		
		
}
