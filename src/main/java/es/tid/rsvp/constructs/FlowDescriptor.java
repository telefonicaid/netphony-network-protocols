package es.tid.rsvp.constructs;

import es.tid.rsvp.RSVPProtocolViolationException;


/**
 * <p>Represents a Flow Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <UL >
 * <LI>&lt;flow descriptor list&gt; ::=  &lt;empty&gt; | &lt;flow descriptor list&gt; &lt;flow descriptor&gt;
 * </UL>
 * 
 * <p>If the INTEGRITY object is present, it must immediately follow the
 * common header.  The STYLE object followed by the flow descriptor list
 * must occur at the end of the message, and objects within the flow
 * descriptor list must follow the BNF given below. There are no other
 * requirements on transmission order, although the above order is
 * recommended.</p>
 * 
 * <p>The NHOP (i.e., the RSVP_HOP) object contains the IP address of
 * the interface through which the Resv message was sent and the LIH for
 * the logical interface on which the reservation is required.</p>
 * 
 * <p>The appearance of a RESV_CONFIRM object signals a request for a
 * reservation confirmation and carries the IP address of the receiver
 * to which the ResvConf should be sent.  Any number of POLICY_DATA
 * objects may appear.</p>
 * 
 * <p>The BNF above defines a flow descriptor list as simply a list of
 * flow descriptors.  The following style-dependent rules specify in more
 * detail the composition of a valid flow descriptor list for each of
 * the reservation styles.</p>
 * 
 * <UL>
 * <LI>WF Style: &lt;flow descriptor list&gt; ::=  &lt;WF flow descriptor&gt;
 * &lt;WF flow descriptor&gt; ::= &lt;FLOWSPEC&gt;
 * <LI>FF style: &lt;flow descriptor list&gt; ::= &lt;FLOWSPEC&gt;  &lt;FILTER_SPEC&gt;  |
 * &lt;flow descriptor list&gt; &lt;FF flow descriptor&gt; &lt;FF flow descriptor&gt; ::=
 * [ &lt;FLOWSPEC&gt; ] &lt;FILTER_SPEC&gt;
 * <p>Each elementary FF style request is defined by a single
 * (FLOWSPEC, FILTER_SPEC) pair, and multiple such requests may be packed
 * into the flow descriptor list of a single Resv message. A FLOWSPEC
 * object can be omitted if it is identical to the most recent such
 * object that appeared in the list; the first FF flow descriptor must
 * contain a FLOWSPEC.</p>
 * 
 * <LI>SE style: &lt;flow descriptor list&gt; ::= &lt;SE flow descriptor&lt;
 * &lt;SE flow descriptor&gt; ::= &lt;FLOWSPEC&gt; &lt;filter spec list&gt;
 * &lt;filter spec list&gt; ::=  &lt;FILTER_SPEC&gt; | &lt;filter spec list&lt;
 * &lt;FILTER_SPEC&gt;
 * </UL>
 * 
 * @author fmn
 *
 */

public class FlowDescriptor extends RSVPConstruct {

	@Override
	public void encode() throws RSVPProtocolViolationException {

		
	}

	@Override
	public void decode(byte[] bytes, int offset)
			throws RSVPProtocolViolationException {

	}

	
	
}
