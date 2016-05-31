netphony-network-protocols v1.3.1
=================================

Repository branch build status:

| **Master**  | **Develop**   |
|:---:|:---:|
| [![Build Status](https://travis-ci.org/telefonicaid/netphony-network-protocols.svg?branch=master)](https://travis-ci.org/telefonicaid/netphony-network-protocols) | [![Build Status](https://travis-ci.org/telefonicaid/netphony-network-protocols.svg?branch=develop)](https://travis-ci.org/telefonicaid/netphony-network-protocols) |

Latest Maven Central Release: 

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/es.tid.netphony/network-protocols/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/es.tid.netphony/network-protocols/)

The netphony-network-protocols library is an implementation of four networking protocols: 
* **PCEP protocol**: Path Computation Element Protocol (PCEP) (RFC 5440 https://tools.ietf.org/html/rfc5440 ). 
* **RSVP-TE protocol**: Resource reservation protocol (RSVP) with Traffic Engineering extensions.
* **OSPF-TE protocol**: OSPF Version 2 protocol with Traffic Engineering extensions.
* **BGP-LS protocol**: North-Bound Distribution of Link-State and Traffic Engineering Information using BGP  https://tools.ietf.org/html/rfc7752. Support of  BGP-LS Traffic Engineering (TE) Metric Extensions https://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-01 

The protocol library can be easily integrated in any software that needs to interact with other software/devices using these protoocols. The library provides the encoding and decoding from java objects to bit-level. Note that, the state machine and set up of sessions is provided by other components, also available in github. 

The PCEP implemnentation has been tested against other PCEP implementations (Telecom Italia Implementation, CTTC Implementation and CNIT Implementation) . 

The Netphony BGP-LS implementation is known to interoperate with Telecom Italia Implementation, CTTC Implementation and CNIT Implementation. It is listed in the IETF BGP-LS implemenation report https://tools.ietf.org/html/draft-ietf-idr-ls-distribution-impl-04 

The library is maintained to be up-to-date to the latest version of the internet-drafts/RFCs. **Contributions are highly welcomed.**

Detailed CHANGELOG [click here](CHANGELOG)

## **Latest news**
- License is now Apache 2.0 
- BPG-LS Tested with Juniper MX routers
- Various bugfixes in BGP-LS

## Compilation and use

The library can be built using the maven tool. There is a set of Junit tests included that check the enconding/decoding process . Contributions on expanding the test suite are welcomed.
To build the .jar file and run the tests, clone the repository, go to the main directory and run:
 ```bash
    cd netphony-network-protocols
    mvn package
    mvn install
 ```
 
 To use the library in your application, add the dependency in your pom.xml file:
  ```xml
    <dependency>
      <groupId>es.tid.netphony</groupId>
      <artifactId>network-protocols</artifactId>
      <version>1.3.1</version>
    </dependency>
 ```
 Authors keep also a copy of the artifact in maven central to facilitate the deployment. (*) In process

## How to use the code:

### ENCODING

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
     Inet4Address ipp = (Inet4Address)Inet4Address.getByName("172.16.101.101");
     ep.setSourceIP(ipp);
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
## PCEP Protocol and Support

The Path Computation Element Protocol (PCEP) is used for communications between a PCC and a PCE, or between two PCEs, in compliance with RFC4657.  Such interactions include path computation requests, path computation replies as well as notifications of specific states related to the use of a PCE in the context of MPLS and GMPLS Traffic Engineering. Recent extensions. A good source of PCE material can be found in http://ict-one.eu/pace/public_wiki/mediawiki-1.19.7/index.php?title=Tools

The detail of the messages, objects and TLVs can be found in [click here](doc/PCEP_Support.md)

* RFC 5440: Full compliance
* RFC 5521: Path-key not supported
* RFC 5886: Full compliance
* RFC 6006: Only P2MP END-POINTS Object for IPv4
* draft-ietf-pce-gmpls-pcep-extensions-10 (partial)
* draft-ietf-pce-inter-layer-ext-05 (partial)
* draft-ietf-pce-hierarchy-extensions-02
* draft-ietf-pce-stateful-pce-05
* draft-ietf-pce-pcep-stateful-pce-gmpls-00
* draft-ietf-pce-pce-initiated-lsp-00:
 
## RSVP-TE Support

Detailed RSVP-TE Support [click here](doc/RSVP-TE_Support.md)


## OSPF-TE 

Detailed OSPF-TE Support [click here](doc/OSPF-TE_Support.md)

OSPF-TE v2 LSA from RFC3630
Inter-AS-TE-v2 LSA from RFC5392 http://tools.ietf.org/html/rfc5392

## BGP-LS Support

 It is used to exchange TE information between BGP-LS speakers.

Detailed BGP4 & BGP-LS Support [click here](doc/BGP-LS_Support.md)

* North-Bound Distribution of Link-State and Traffic Engineering Information using BGP  https://tools.ietf.org/html/rfc7752. 

* BGP-LS Traffic Engineering (TE) Metric Extensions https://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-01


(*) The BGLP-LS Speaker is available in https://github.com/telefonicaid/netphony-topology

