Detailed BGP-4 & BGP-LS Implementation Support (v1.3.0) (to be completed)
=======================================================

The BGP-4 and BGP-LS elements shown below are implemented in current version, unless **Not Implemented** is mentioned.  

BGP-4 Messages
--------------
* BGP KeepAlive Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.4 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Keepalive Message")
* BGP Notification **Not Implemented**
* BGP Open Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.2 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Open Message")
* BGP Update Message [RFC 4271](http://tools.ietf.org/html/rfc4271#section-4.3 "RFC 4271: A Border Gateway Protocol 4 (BGP-4) Update Message")

Optional Parameters in OPEN
---------------------------
* Capabilities Optional Parameter (Parameter Type 2) [RFC 3392](http://tools.ietf.org/html/rfc3392#section-4 "RFC 3392 Capabilities Advertisement with BGP-4")

### Capabilities
* Multiprotocol Extensions capability (Capability Code 1) [RFC 4760](http://tools.ietf.org/html/rfc4760#section-8 "RFC 4760 Multiprotocol Extensions for BGP-4")

Fields in UPDATE Message
------------------------
* Withdrawn Routes **Not Implemented**
* Path Attribute
* Network Layer Reachability Information **Not Implemented**

### Path Attributes
* Origin Attribute (Type Code 1): rfc 4271
* AS Path Attribute (Type Code 2): rfc 4271
* Next Hop (Type Code 3): rfc 4271  **Not Implemented**
* Multi Exit Disc (Type Code 4) **Not Implemented**
* Local Pref (Type Code 5) **Not Implemented**
* Atomic Aggregate (Type Code 6) **Not Implemented**
* Aggregator (Type Code 7) **Not Implemented**
* MP Reach (Type Code 14)
* MP Unreach (Type Code 15)

### NLRI Types

| **Type**  | **NLRI Type**   |
|:---:|:---:|
|  1   | Node NLRI                 |
|  2   | Link NLRI                 |
|  3   | IPv4 Topology Prefix NLRI |
|  4   | IPv6 Topology Prefix NLRI (**Not Implemented**)|

###TLVs
----
* Local Node Descriptors TLV (Type 256)
* Remote Node Descriptors TLV (Type 257)

 ### Node Descriptor Sub-TLVs
 * Autonomous System
 * BGP-LS Identifier 
 * OSPF Area-ID   
 * IGP Router-ID

###Link Descriptors TLVs
----------------------
*  258   Link Local/Remote Identifiers
*  259    IPv4 interface  address   
*  260    | IPv4 neighbor    address 
*  261    | IPv6 interface address  
*  262    | IPv6 neighbor address 
*  263    | Multi-Topology Identifier     
     

## BGP- Link State Attribute  TLVs

### Node Attribute TLVs

| **TLV Code points**  | **Name**   |
|:---:|:---:| 
|     263     | Multi-Topology   Identifier   (**Not Implemented**)  | 
|     1024    | Node Flag Bits       | 
|     1025    | Opaque Node    Attribute    (**Not Implemented**)    | 
|     1026    | Node Name            | 
|     1027    | IS-IS Area    Identifier        | 
|     1028    | IPv4 Router-ID of  Local Node  |
|     1029    | IPv6 Router-ID of Local Node     |
  
  ### Link Attribute TLVs
  
| **TLV Code points**  | **Name**   |
|:---:|:---:| 
   |    1028   | IPv4 Router-ID of   |   134/---    | [RFC5305]/4.3    |
   |           | Local Node          |              |                  |
   |    1029   | IPv6 Router-ID of   |   140/---    | [RFC6119]/4.1    |
   |           | Local Node          |              |                  |
   |    1030   | IPv4 Router-ID of   |   134/---    | [RFC5305]/4.3    |
   |           | Remote Node         |              |                  |
   |    1031   | IPv6 Router-ID of   |   140/---    | [RFC6119]/4.1    |
   |           | Remote Node         |              |                  |
   |    1088   | Administrative      |     22/3     | [RFC5305]/3.1    |
   |           | group (color)       |              |                  |
   |    1089   | Maximum link        |     22/9     | [RFC5305]/3.4    |
   |           | bandwidth           |              |                  |
   |    1090   | Max. reservable     |    22/10     | [RFC5305]/3.5    |
   |           | link bandwidth      |              |                  |
   |    1091   | Unreserved          |    22/11     | [RFC5305]/3.6    |
   |           | bandwidth           |              |                  |
   |    1092   | TE Default Metric   |    22/18     | Section 3.3.2.3  |
   |    1093   | Link Protection     |    22/20     | [RFC5307]/1.2    |
   |           | Type                |              |                  |
   |    1094   | MPLS Protocol Mask  |     ---      | Section 3.3.2.2  |
   |    1095   | IGP Metric          |     ---      | Section 3.3.2.4  |
   |    1096   | Shared Risk Link    |     ---      | Section 3.3.2.5  |
   |           | Group               |              |                  |
   |    1097   | Opaque Link         |     ---      | Section 3.3.2.6  |
   |           | Attribute           |              |                  |
   |    1098   | Link Name           |     ---      | Section 3.3.2.7  |
   +-----------+---------------------+--------------+------------------+

-------------------------
* Administrative Group
* Link Local/Remote 
* IPv4 interface
* IPv4 neighbor


