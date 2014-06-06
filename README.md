cne-network-protocols
=====================

Java Library of Networking Protocols: PCEP, RSVP, OSPF, BGP-LS

Provides encoding and decoding of the mentioned protocols.

###Usage:

ENCODING

1-> Create a new instance of the desired message
 ```java
     PCEPRequest message = new PCEPRequest();
 ```
2-> Create instances of the desired constructs or objects and add them to the message
 ```java
     Request req = new Request();
     //RequestParameters
     RequestParameters rp= new RequestParameters();
     rp.setPbit(true);				
     rp.setRequestID(123);		
     rp.setPrio(1);		
     rp.setReopt(false);	
     rp.setBidirect(false);
     rp.setLoose(false);
     req.setRequestParameters(rp);
     //EndPoints
     EndPointsIPv4 ep=new EndPointsIPv4();				
     req.setEndPoints(ep);
     ep..setEndPointIPv4TLV((Inet4Address)Inet4Address.getByName ("192.168.1.3");
	.....
    message.addRequest(req); 	
 ```
3-> Call encode()
```java
   message.encode();
```
4-> Get bytes and send them!
```java
   out.write(message.getBytes());
   out.flush();
```
# PCEP Support
RFC 5440 
# RSVP-TE Support

# OSPF-TE Support
OSPF-TE v2 LSA from RFC3630
Inter-AS-TE-v2 LSA from RFC5392 http://tools.ietf.org/html/rfc5392
# BGP-LS Support
http://tools.ietf.org/html/draft-ietf-idr-ls-distribution-03

