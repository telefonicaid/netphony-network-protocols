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
    			{"es.tid.rsvp.objects.ErrorSpecIPv4"},
    			{"es.tid.rsvp.objects.ErrorSpecIPv6"},
    			{"es.tid.rsvp.objects.FilterSpecIPv4"},
    			{"es.tid.rsvp.objects.FilterSpecIPv6"},
    			{"es.tid.rsvp.objects.FilterSpecLSPTunnelIPv4"},
    			{"es.tid.rsvp.objects.FilterSpecLSPTunnelIPv6"},
    			{"es.tid.rsvp.objects.FlowLabelFilterSpecIPv6"},
    			//{"es.tid.rsvp.objects.FlowLabelSenderTemplateIPv6"}, FIXME: Not implemented
    			//{"es.tid.rsvp.objects.FlowSpec"},FIXME: Decode Not implemented!! 
    			{"es.tid.rsvp.objects.HelloACK"},
    			{"es.tid.rsvp.objects.HelloRequest"},
    			//{"es.tid.rsvp.objects.Integrity"},
    			//{"es.tid.rsvp.objects.IntservADSPEC"},FIXME: Decode Not implemented!! 
    			//{"es.tid.rsvp.objects.IntservSenderTSpec"},FIXME: Decode Not implemented!! 
    			{"es.tid.rsvp.objects.Label"},
    			//{"es.tid.rsvp.objects.LabelRequest"},FIXME: NOT IMPLEMENTED
    			//LabelRequestWATMLabelRange FIXME: Decode Not implemented!! 
    			//LabelRequestWFrameRelayLabelRange FIXME: Decode Not implemented!! 
    			//{"es.tid.rsvp.objects.PolicyData"}, FIXME: NOT IMPLEMENTED
    			{"es.tid.rsvp.objects.ResvConfirmIPv4"},
    			{"es.tid.rsvp.objects.ResvConfirmIPv6"},
    			{"es.tid.rsvp.objects.RRO"},
    			{"es.tid.rsvp.objects.RSVPHopIPv4"},
    			{"es.tid.rsvp.objects.RSVPHopIPv6"},
    			{"es.tid.rsvp.objects.ScopeIPv4"},
    			{"es.tid.rsvp.objects.ScopeIPv6"},
    			{"es.tid.rsvp.objects.SessionAttributeWResourceAffinities"}, 
    			{"es.tid.rsvp.objects.SessionIPv6"},
    			{"es.tid.rsvp.objects.SessionLSPTunnelIPv4"},
    			{"es.tid.rsvp.objects.SessionLSPTunnelIPv6"},
    			{"es.tid.rsvp.objects.SSONSenderTSpec"},
    			{"es.tid.rsvp.objects.Style"},
    			{"es.tid.rsvp.objects.SuggestedLabel"},
    			{"es.tid.rsvp.objects.TimeValues"},
    			{"es.tid.rsvp.objects.UpstreamLabel"},
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
		RSVPObject object1 = (RSVPObject)objectClass.newInstance();
		TestCommons.createAllFields(object1,true);
		object1.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		RSVPObject object2 = (RSVPObject) ctor.newInstance(object1.getBytes(),0);
		object2.encode();
		System.out.println(object1.toString());
		System.out.println(ByteHandler.ByteMACToString(object1.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));
		assertTrue("testing RSVP object "+objectClass,object1.equals(object2));
		System.out.println("RSVP Object "+object+" Bytes match!");
		//Check toString output
		object1.toString();
		//Check all the gets
		TestCommons.testGets(object1);
		//Check if the fields are the same
		//Check bad objects
		try {
			byte[] bitos = new byte[4];
			bitos[3]=0x04;
			RSVPObject object3 = (RSVPObject) ctor.newInstance(bitos,0);
			
		} catch (Exception e) {
			System.out.println("Testing malformed object ok");
		}
		//Check empty object
		try {
			
			RSVPObject object4 = (RSVPObject)objectClass.newInstance();
			object4.encode();
			
		} catch (Exception e) {
			System.out.println("Testing empty object");
		}
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in object "+object,false);
    		
    	}
    }
 
    
}
