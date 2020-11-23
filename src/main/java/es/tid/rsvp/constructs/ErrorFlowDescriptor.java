package es.tid.rsvp.constructs;

import es.tid.rsvp.RSVPProtocolViolationException;


/**
 * 
 * <p>Represents a Error Flow Descriptor construct, as defined in RFC 2205.</p>
 * 
 * <p>The following style-dependent rules define the composition of a
 * valid error flow descriptor; the object order requirements are as given
 * earlier for flow descriptor.</p>
 * 
 * <UL>
 * <LI>WF Style: &lt;error flow descriptor&gt; ::= &lt;WF flow descriptor&gt;
 * <LI>FF style: &lt;error flow descriptor&gt; ::= &lt;FF flow descriptor&gt;
 * <p>Each flow descriptor in a FF-style Resv message must be processed
 * independently, and a separate ResvErr message must be generated
 * for each one that is in error.</p>
 * <LI>SE style: &lt;error flow descriptor&gt; ::= &lt;SE flow descriptor&gt;
 * <p>An SE-style ResvErr message may list the subset of the filter specs
 * in the corresponding Resv message to which the error applies.</p>
 * </UL>
 * <p>Note that a ResvErr message contains only one flow descriptor.
 * Therefore, a Resv message that contains N &gt; 1 flow descriptors (FF style)
 * may create up to N separate ResvErr messages.</p>
 * 
 * @author Fernando Munoz del Nuevo fmn@tid.es
 * @version 0.1
 */

public class ErrorFlowDescriptor extends RSVPConstruct {

	@Override
	public void encode() throws RSVPProtocolViolationException {

		
	}

	@Override
	public void decode(byte[] bytes, int offset)
			throws RSVPProtocolViolationException {

	}

	
	
}
