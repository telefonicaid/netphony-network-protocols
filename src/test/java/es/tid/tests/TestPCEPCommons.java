package es.tid.tests;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.tid.pce.pcep.constructs.GeneralizedBandwidthSSON;
import es.tid.pce.pcep.objects.BandwidthRequested;
import es.tid.pce.pcep.objects.BitmapLabelSet;
import es.tid.pce.pcep.objects.EndPointsIPv4;
import es.tid.pce.pcep.objects.PceIdIPv4;

public class TestPCEPCommons {
	public static void createAllFields(Object object){
		try {
			//System.out.println("Looking at "+object.getClass().getName() );
			List<Field> fieldListNS = new ArrayList<Field>();
			List<Field> fieldList= Arrays.asList(object.getClass().getDeclaredFields());
			//System.out.println("XXXX "+fieldList.size());
			for (Field field : fieldList) {
				
				if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
					fieldListNS.add(field);
					Type ty=field.getGenericType();
					//System.out.println("Type: "+ty);

					if (ty instanceof Class){
						Object o=null;
						Class c =(Class)ty;
						Method method = object.getClass().getMethod("set"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()),field.getType());
						//System.out.println("mi "+method.getName());
						if (c.isPrimitive()){
								fillPrimitive(object,method,ty);
						} else if (c.isArray()){
							
							Class c2=c.getComponentType();
							//System.out.println("c2: "+c2.getName()); 
							o = Array.newInstance(c2, 5);
							
							method.invoke(object, o);
							//System.out.println("FIXME:  ES UN ARRAY OJO ");
						}
						
						else {
						
							//System.out.println("me "+method.getName());
							 if (c.getName().equals("String")) {
									//System.out.println("FIXME:  String");
									o="TEST";
							 } else if  (c.getName().equals("java.net.Inet4Address")) {
									o=Inet4Address.getByName("1.1.1.1");
							 }else if  (c.getName().equals("java.net.Inet6Address")) {
									o=Inet6Address.getByName("1080:0:0:0:8:800:200C:417A");
							 }
							 else if  (c.getName().equals("es.tid.pce.pcep.objects.EndPoints")) {
									o = new EndPointsIPv4();
									createAllFields(o);
							 }else if  (c.getName().equals("es.tid.pce.pcep.objects.Bandwidth")) {
								 o= new BandwidthRequested();
								 createAllFields(o);
								}else if  (c.getName().equals("es.tid.pce.pcep.constructs.GeneralizedBandwidth")) {
									 o= new GeneralizedBandwidthSSON();
									 createAllFields(o);
									}
							 else if  (c.getName().equals("es.tid.pce.pcep.objects.LabelSet")) {
								 o= new BitmapLabelSet();
								 createAllFields(o);
								}
							 else if (c.getName().equals("es.tid.pce.pcep.objects.PceId")){
								 o= new PceIdIPv4();
								 createAllFields(o);
							 }
							 
							 
							 else {
								 //System.out.println("yyyy "+c.getName());
									o = ((Class)ty).newInstance();	
									createAllFields(o);
							 }
							
							method.invoke(object, o);

						}
					}else if (ty instanceof ParameterizedType){
						ParameterizedType pt=(ParameterizedType)ty;
						Type rt=pt.getRawType();
						Type at=pt.getActualTypeArguments()[0];

						if (rt instanceof Class){
							Class ca=(Class)rt;
							
							if (ca.getName().equals("java.util.LinkedList")){
								String name="get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
								Method method = object.getClass().getMethod("get"+field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase()));
								Object res=method.invoke(object);
								Method[] methods =res.getClass().getDeclaredMethods();	
								if  (((Class)at).getName().equals("es.tid.rsvp.objects.subobjects.EROSubobject")) {
									System.out.println("FIXME: es.tid.rsvp.objects.subobjects.EROSubobject");
								} else if  (((Class)at).getName().equals("es.tid.rsvp.objects.subobjects.RROSubobject")) {
									System.out.println("FIXME: es.tid.rsvp.objects.subobjects.RROSubobject");
								}else if  (((Class)at).getName().equals("es.tid.pce.pcep.objects.subobjects.XROSubobject")) {
									System.out.println("FIXME: es.tid.pce.pcep.objects.subobjects.XROSubobject");
								}else if  (((Class)at).getName().equals("es.tid.pce.pcep.tlvs.PCEPTLV")) {
									System.out.println("FIXME: es.tid.pce.pcep.tlvs.PCEPTLV");
								}else if (((Class) at).isPrimitive()){
									System.out.println("FIXME: PRIMITIVE "+ ((Class)at).getName());

								}
								else {
									if  (((Class)at).getName().equals("java.lang.Integer")) {
										Integer in=new Integer(3);
										methods[0].invoke(res, in);
									}else if  (((Class)at).getName().equals("java.lang.Long")) {
										Long in=new Long(5);
										methods[0].invoke(res, in);
									}else if  (((Class)at).getName().equals("java.net.Inet4Address")) {
										Inet4Address in=(Inet4Address) Inet4Address.getByName("1.1.1.1");
										methods[0].invoke(res, in);
									}else {
										System.out.println("Creating "+((Class)at).getName());
										Object o = ((Class)at).newInstance();
										createAllFields(o);
										methods[0].invoke(res, o);}
								}
								
	
							}
			
						}
						

					}

				}		    			    	
			}	
		}
		catch (Exception e) {
			e.printStackTrace();
		} 			
	}

	public static void fillPrimitive(Object object, Method method,Type tyy) {
		try {
			Class ty=(Class)tyy;
			if (ty.getName().equals("int")){

				method.invoke(object, 0);

			}else if (ty.getName().equals("boolean")){

				method.invoke(object,true);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
