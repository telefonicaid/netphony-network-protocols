package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

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
 * Tests PCEP Protocol messages that can't be tested with the generic method
 * Tests all messages by doing enconding-decoding-encoding tests
 * @author ogondio
 *
 */
public class TestPCEP {


	@org.junit.Test
	public void testClose(){
		System.out.println("Testing Close Message");
		//Create message
		PCEPClose message = new PCEPClose();
		int[] reasons={0,4,Close.reason_MAX_VALUE};
		for (int i =0; i< reasons.length;++i){
			message.setReason(reasons[i]);
			System.out.println("PCEP Close test with reason "+reasons[i]);
			testPCEPMessage(message,PCEPClose.class,"PCEP Close test with reason "+reasons[i] );
		}

		//loopFields(message);


	}

	@org.junit.Test
	public void testPCEPError(){
		try {
		System.out.println("Testing PCEPError Message");
		PCEPError message = new PCEPError();
		//Test with an Error
		System.out.println("Test #1 of PCEPError with a single Error");
		ErrorConstruct error= new ErrorConstruct();
		LinkedList<ErrorConstruct> errorList= new LinkedList<ErrorConstruct>();
		//PCEPErrorObject e = new PCEPErrorObject();
		TestCommons.createAllFields(error,0);
		errorList.add(error);
		message.setErrorList(errorList);
		message.encode();
		PCEPMessage object2 = new PCEPError(message.getBytes());
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(message.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		System.out.println(message.toString());
		//Check if the fields are the same
		assertTrue("asserting Error message with single error object ",message.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message error",false);
    		
    	}

	}

	/**
	 * encode-decode-encode test.
	 * Encodes the message, then creates a new message with the bytes of the
	 * encoded message. Then, encodes this message. 
	 * It checks if the bytes and fields are the same in both.
	 * @param message
	 * @param messageClass
	 */
	public void testPCEPMessage(PCEPMessage message, Class messageClass, String testText){
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
