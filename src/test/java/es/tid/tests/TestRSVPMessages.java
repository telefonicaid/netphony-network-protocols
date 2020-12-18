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
public class TestRSVPMessages {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.rsvp.messages.RSVPPathMessage"},
    			{"es.tid.rsvp.messages.RSVPPathTearMessage"},
    			{"es.tid.rsvp.messages.RSVPPathErrMessage"},
    			{"es.tid.rsvp.messages.RSVPResvConfMessage"},
    			{"es.tid.rsvp.messages.RSVPResvErrMessage"},
    			{"es.tid.rsvp.messages.RSVPResvMessage"},
    			{"es.tid.rsvp.messages.RSVPResvTearMessage"},

    			
				};
		return Arrays.asList(objects);
    }
	
    
    public TestRSVPMessages (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing RSVP Message "+object);
    	Class objectClass=Class.forName(object);
    	RSVPMessage object = (RSVPMessage)objectClass.newInstance();
		TestCommons.createAllFields(object,true);
		object.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		RSVPMessage object2 = (RSVPMessage) ctor.newInstance(object.getBytes(),object.getBytes().length);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		//System.out.println(object2.toString());
		//Check if the fields are the same
		assertTrue("asserting RSVP message "+objectClass,object.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in message "+object,false);
    		
    	}
    }
    
  
    
}
