package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
//import static org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.tid.bgp.bgp4.messages.BGP4Message;
import es.tid.pce.pcep.PCEPProtocolViolationException;
import es.tid.pce.pcep.messages.PCEPMessage;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.messages.RSVPMessage;

@RunWith(org.junit.runners.Parameterized.class)
public class TestPCEPMessages {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={    			
    			{"es.tid.pce.pcep.messages.PCEPClose"},
    			//{"es.tid.pce.pcep.messages.PCEPError"},
    			{"es.tid.pce.pcep.messages.PCEPInitiate"},
    			{"es.tid.pce.pcep.messages.PCEPKeepalive"},
    			{"es.tid.pce.pcep.messages.PCEPMonRep"},
    			{"es.tid.pce.pcep.messages.PCEPMonReq"},
    			{"es.tid.pce.pcep.messages.PCEPNotification"},
    			{"es.tid.pce.pcep.messages.PCEPOpen"},
    			{"es.tid.pce.pcep.messages.PCEPReport"},
    			{"es.tid.pce.pcep.messages.PCEPRequest"},
    			{"es.tid.pce.pcep.messages.PCEPResponse"},
    			{"es.tid.pce.pcep.messages.PCEPUpdate"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestPCEPMessages (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing PCEP Message "+object);
    	Class objectClass=Class.forName(object);
    	PCEPMessage object = (PCEPMessage)objectClass.newInstance();
		TestCommons.createAllFields(object,0);
		object.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class);
		PCEPMessage object2 = (PCEPMessage) ctor.newInstance(object.getBytes());
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		System.out.println(object.toString());
		//Check if the fields are the same
		assertTrue("asserting PCEP message "+objectClass,object.equals(object2));
		//check hashcode
		object.hashCode();
		//check equals
		
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message "+object,false);
    		
    	}
    }
    
  
    
}
