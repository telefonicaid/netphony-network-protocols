package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;

import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.constructs.ErrorConstruct;
import es.tid.pce.pcep.messages.PCEPClose;
import es.tid.pce.pcep.messages.PCEPError;
import es.tid.pce.pcep.messages.PCEPInitiate;
import es.tid.pce.pcep.messages.PCEPKeepalive;
import es.tid.pce.pcep.messages.PCEPMessage;
import es.tid.pce.pcep.objects.Close;
import es.tid.pce.pcep.objects.PCEPErrorObject;
import es.tid.protocol.commons.ByteHandler;

/**
 * Tests BGP4 Protocol
 * Tests all messages by doing enconding-decoding-encoding tests
 * @author ogondio
 *
 */
public class TestBGP4 {

	@org.junit.Test
	public void testBGP4KeepAlive() {
		System.out.println("Testing BGP4 Keepalive Message");
		PCEPKeepalive message = new PCEPKeepalive();
		testBGP4Message(message,PCEPKeepalive.class,"Test KeepAlive" );
	}



	/**
	 * encode-decode-encode test.
	 * Encodes the message, then creates a new message with the bytes of the
	 * encoded message. Then, encodes this message. 
	 * It checks if the bytes and fields are the same in both.
	 * @param message
	 * @param messageClass
	 */
	public void testBGP4Message(PCEPMessage message, Class messageClass, String testText){
		try {
			message.encode();
			try {
				Constructor ctor = messageClass.getConstructor(byte[].class);
				PCEPMessage message2 = (PCEPMessage) ctor.newInstance(message.getBytes());
				message2.encode();
				//assertArrayEquals(message.getBytes(), message2.getBytes());
				System.out.println(ByteHandler.ByteMACToString(message.getBytes()));
				System.out.println(ByteHandler.ByteMACToString(message2.getBytes()));

				//Check if the fields are the same
				assertTrue(testText, message.equals(message2));

			} catch (Exception e) {
				e.printStackTrace();
				assertTrue(testText, false);
			} 
		}catch (PCEPProtocolViolationException e) {
			assertTrue(testText, false);
		} 


	}
	


}
