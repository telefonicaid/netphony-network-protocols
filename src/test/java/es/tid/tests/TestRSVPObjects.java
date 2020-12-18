package es.tid.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
//import static org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import es.tid.pce.pcep.objects.PCEPObject;
import es.tid.protocol.commons.ByteHandler;
import es.tid.rsvp.objects.RSVPObject;

@RunWith(org.junit.runners.Parameterized.class)
public class TestRSVPObjects {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
    			{"es.tid.rsvp.objects.ERO"},
    			//{"es.tid.rsvp.objects.FlowSpec"},FIXME: Decode Not implemented!!
    			//{"es.tid.rsvp.objects.IntservSenderTSpec"}, FIXME: Decode Not implemented!!
    			{"es.tid.rsvp.objects.SessionAttributeWResourceAffinities"}, 
    			{"es.tid.rsvp.objects.ErrorSpecIPv4"},
    			{"es.tid.rsvp.objects.ErrorSpecIPv6"},
    			//{"es.tid.rsvp.objects.FilterSpecIPv4"},
    			//{"es.tid.rsvp.objects.FilterSpecIPv6"},
    			//{"es.tid.rsvp.objects.FilterSpecLSPTunnelIPv4"},
    			//{"es.tid.rsvp.objects.FilterSpecLSPTunnelIPv6"},
    			//{"es.tid.rsvp.objects.FlowLabelFilterSpecIPv6"},
    			{"es.tid.rsvp.objects.HelloACK"},
    			{"es.tid.rsvp.objects.HelloRequest"},
    			//{"es.tid.rsvp.objects.Integrity"},
    			{"es.tid.rsvp.objects.RRO"},
    			{"es.tid.rsvp.objects.SessionIPv6"},
				};
		return Arrays.asList(objects);
    }
	
    
    public TestRSVPObjects (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing RSVP Object "+object);
    	Class objectClass=Class.forName(object);
		RSVPObject object = (RSVPObject)objectClass.newInstance();
		TestCommons.createAllFields(object,true);
		object.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		RSVPObject object2 = (RSVPObject) ctor.newInstance(object.getBytes(),0);
		object2.encode();
		System.out.println(object.toString());
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		System.out.println("ok");
		//Check if the fields are the same
		assertTrue("testing RSVP object "+objectClass,object.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in object "+object,false);
    		
    	}
    }
 
    
}
