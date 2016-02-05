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

@RunWith(org.junit.runners.Parameterized.class)
public class TestPCEPObjects {

	String object;
	//private String[] objects;
	
	
	//@Parameters
	@Parameters(name="{0}")
    public static Collection configs() {
    	Object[][] objects={
				//"es.tid.pce.pcep.objects.AdvanceReservationObject",
    			{"es.tid.pce.pcep.objects.BandwidthExistingLSP"},
    			//{"es.tid.pce.pcep.objects.BandwidthExistingLSPGeneralizedBandwidth"},
    			{"es.tid.pce.pcep.objects.BandwidthRequested"},
				//"es.tid.pce.pcep.objects.BandwidthRequestedGeneralizedBandwidth",
				//"es.tid.pce.pcep.objects.BitmapLabelSet",
    			{"es.tid.pce.pcep.objects.Close"},
				//"es.tid.pce.pcep.objects.EndPointDataPathID",
    			{"es.tid.pce.pcep.objects.EndPointsIPv4"},
				//"es.tid.pce.pcep.objects.EndPointsIPv6",
    			{"es.tid.pce.pcep.objects.EndPointsUnnumberedIntf"},
    			//"es.tid.pce.pcep.objects.ExcludeRouteObject",
    			{"es.tid.pce.pcep.objects.ExplicitRouteObject"},
				//"es.tid.pce.pcep.objects.GeneralizedEndPoints",
    			{"es.tid.pce.pcep.objects.IncludeRouteObject"},
    			{"es.tid.pce.pcep.objects.InterLayer"},
    			//"es.tid.pce.pcep.objects.LSP",
    			{"es.tid.pce.pcep.objects.LSPA"},
				//"es.tid.pce.pcep.objects.LabelSet",
				//"es.tid.pce.pcep.objects.LabelSetInclusiveList",
				//"es.tid.pce.pcep.objects.LoadBalancing",
    			{"es.tid.pce.pcep.objects.Metric"},
				//"es.tid.pce.pcep.objects.Monitoring",
				//"es.tid.pce.pcep.objects.NetQuotationIPv4",
				//"es.tid.pce.pcep.objects.NetQuotationIPv6",
				//"es.tid.pce.pcep.objects.NetQuotationNSAP",
				//"es.tid.pce.pcep.objects.NoPath",
				//"es.tid.pce.pcep.objects.Notification",
				//"es.tid.pce.pcep.objects.OPEN",
				//"es.tid.pce.pcep.objects.ObjectiveFunction",
				//"es.tid.pce.pcep.objects.P2MPEndPointsDataPathID",
				//"es.tid.pce.pcep.objects.P2MPEndPointsIPv4",
				//"es.tid.pce.pcep.objects.PCEPErrorObject",
    			{"es.tid.pce.pcep.objects.PccReqId"},
				//"es.tid.pce.pcep.objects.PceId",
    			{"es.tid.pce.pcep.objects.PceIdIPv4"},
				//"es.tid.pce.pcep.objects.ProcTime",
				//"es.tid.pce.pcep.objects.ReportedRouteObject",
				//"es.tid.pce.pcep.objects.ReqAdapCap",
				//"es.tid.pce.pcep.objects.RequestParameters",
				//"es.tid.pce.pcep.objects.Reservation",
				//"es.tid.pce.pcep.objects.ReservationConf",
    			{"es.tid.pce.pcep.objects.SERO"},
				//"es.tid.pce.pcep.objects.SRERO",
    			{"es.tid.pce.pcep.objects.SRP"},
    			{"es.tid.pce.pcep.objects.ServerIndication"},
				//"es.tid.pce.pcep.objects.SuggestedLabel",
				//"es.tid.pce.pcep.objects.Svec",
				//"es.tid.pce.pcep.objects.SwitchLayer",
				//"es.tid.pce.pcep.objects.WavelengthAssignementObject",
				//"es.tid.pce.pcep.objects.XifiEndPoints",
				//"es.tid.pce.pcep.objects.XifiUniCastEndPoints"
				};
		//Object[][] objects=  {
    	//		{"hola"},{"bola"}};
    	
    	//return Arrays.asList(new Object[][] {
    	//		{"hola"},{"bola"}});
		return Arrays.asList(objects);
    }
	
    
    public TestPCEPObjects (String object) {
    	this.object=object;
    }
    
    @Test
    public void test (){
    	try {
    	System.out.println("Testing PCEP Object "+object);
    	Class objectClass=Class.forName(object);
		PCEPObject object = (PCEPObject)objectClass.newInstance();
		TestPCEPCommons.createAllFields(object);
		object.encode();
		Constructor ctor = objectClass.getConstructor(byte[].class,int.class);
		PCEPObject object2 = (PCEPObject) ctor.newInstance(object.getBytes(),0);
		object2.encode();
		System.out.println(ByteHandler.ByteMACToString(object.getBytes()));
		System.out.println(ByteHandler.ByteMACToString(object2.getBytes()));

		//Check if the fields are the same
		assertTrue("testing object "+object,object.equals(object2));
    	} catch(Exception e){
    		e.printStackTrace();
    		assertTrue("Exception in object "+object,false);
    		
    	}
    }
 
    
}
