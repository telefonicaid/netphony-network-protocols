package es.tid.bgp.bgp4.messages;
/**
 * BGP Notification Message Format (RFC 4271).
 * <p>From RFC 4271, Section 4.5</p>
 * <a href="https://tools.ietf.org/html/rfc4271">RFC 4271</a>.
 * 4.5. NOTIFICATION Message Format

   A NOTIFICATION message is sent when an error condition is detected.
   The BGP connection is closed immediately after it is sent.

   In addition to the fixed-size BGP header, the NOTIFICATION message
   contains the following fields:
<pre>
      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      | Error code    | Error subcode |   Data (variable)             |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
</pre>
      Error Code:

         This 1-octet unsigned integer indicates the type of
         NOTIFICATION.  The following Error Codes have been defined:

            Error Code       Symbolic Name               Reference

              1         Message Header Error             Section 6.1

              2         OPEN Message Error               Section 6.2

              3         UPDATE Message Error             Section 6.3

              4         Hold Timer Expired               Section 6.5

              5         Finite State Machine Error       Section 6.6

              6         Cease                            Section 6.7

      Error subcode:

         This 1-octet unsigned integer provides more specific
         information about the nature of the reported error.  Each Error
         Code may have one or more Error Subcodes associated with it.
         If no appropriate Error Subcode is defined, then a zero
         (Unspecific) value is used for the Error Subcode field.

      Message Header Error subcodes:

               1 - Connection Not Synchronized.
               2 - Bad Message Length.
               3 - Bad Message Type.

      OPEN Message Error subcodes:

               1 - Unsupported Version Number.
               2 - Bad Peer AS.
               3 - Bad BGP Identifier.
               4 - Unsupported Optional Parameter.
               5 - [Deprecated - see Appendix A].
               6 - Unacceptable Hold Time.

      UPDATE Message Error subcodes:

               1 - Malformed Attribute List.
               2 - Unrecognized Well-known Attribute.
               3 - Missing Well-known Attribute.
               4 - Attribute Flags Error.
               5 - Attribute Length Error.
               6 - Invalid ORIGIN Attribute.
               7 - [Deprecated - see Appendix A].
               8 - Invalid NEXT_HOP Attribute.
               9 - Optional Attribute Error.
              10 - Invalid Network Field.
              11 - Malformed AS_PATH.

      Data:

         This variable-length field is used to diagnose the reason for
         the NOTIFICATION.  The contents of the Data field depend upon
         the Error Code and Error Subcode.  See Section 6 for more
         details.

         Note that the length of the Data field can be determined from
         the message Length field by the formula:

                  Message Length = 21 + Data Length

   The minimum length of the NOTIFICATION message is 21 octets
   (including message header).
 * @author mcs
 *
 */
public class BGP4Notification extends BGP4Message {

	@Override
	public void encode() {
		// TODO Auto-generated method stub
		
	}

}
