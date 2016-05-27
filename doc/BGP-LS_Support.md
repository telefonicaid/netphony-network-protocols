Detailed BGP-4 & BGP-LS Implementation Support (v1.3.0) 
=======================================================

The BGP-4 and BGP-LS elements shown below are implemented in current version, unless **Not Implemented** is mentioned.  

RFCs:
* [RFC 3392](http://tools.ietf.org/html/rfc3392)
* [RFC 4271](http://tools.ietf.org/html/rfc4271)
* [RFC 4760](http://tools.ietf.org/html/rfc4760)
* [RFC 7752](http://tools.ietf.org/html/rfc7752)
* [draft-previdi-idr-bgpls-te-metric-extensions-00](http://tools.ietf.org/html/draft-previdi-idr-bgpls-te-metric-extensions-00)

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

| **Type**  | **NLRI Type**   | Class in the implementation |
|:---:|:---:|:---:|
|  1   | Node NLRI                 | http://telefonicaid.github.io/netphony-network-protocols/api/es/tid/bgp/bgp4/update/fields/LinkNLRI.html |
|  2   | Link NLRI                 |http://telefonicaid.github.io/netphony-network-protocols/api/es/tid/bgp/bgp4/update/fields/NodeNLRI.html |
|  3   | IPv4 Topology Prefix NLRI | http://telefonicaid.github.io/netphony-network-protocols/api/es/tid/bgp/bgp4/update/fields/PrefixNLRI.html | 
|  4   | IPv6 Topology Prefix NLRI | (**Not Implemented**)|

### Link State NLRI TLVs 
| **Type**  | **NLRI Type**   |
|:---:|:---:|
| (Type 256) | Local Node Descriptors TLV |
| (Type 257) | Remote Node Descriptors TLV |

 ### Node Descriptor Sub-TLVs

| **Type**  | **NLRI Type**   |
|:---:|:---:| 
| 512 | Autonomous System|
| 513 |BGP-LS Identifier |
| 514 |OSPF Area-ID   |
| 515 |IGP Router-ID|

### Link Descriptors TLVs

| **TLV Code points**  | **Name**   |
|:---:|:---:| 
|  258  | Link Local/Remote Identifiers |
|  259   | IPv4 interface  address   |
|  260    | IPv4 neighbor    address |
|  261    | IPv6 interface address  |
| 262    | IPv6 neighbor address |
|  263    | Multi-Topology Identifier |    

## BGP-LS Attribute TLVs

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
|    1028   | IPv4 Router-ID of Local Node  | 
|    1029   | IPv6 Router-ID of Local Node (**Not Implemented**)  | 
|    1030   | IPv4 Router-ID of Remote Node  |  
|    1031   | IPv6 Router-ID of Remote Node  (**Not Implemented**)  | 
|    1088   | Administrative group (color)     |   
|    1089   | Maximum link   bandwidth      |   
|    1090   | Max. reservable  link bandwidth   |   
|    1091   | Unreserved  bandwidth        |  
|    1092   | TE Default Metric   | 
|    1093   | Link Protection   Type  |
|    1094   | MPLS Protocol Mask (**Not Implemented**)  |  
|    1095   | IGP Metric          |  
|    1096   | Shared Risk Link   Group |
|    1097   | Opaque Link    Attribute   (**Not Implemented**)   | 
|    1098   | Link Name      (**Not Implemented**)      | 
|    1094   | MPLS Protocol Mask (**Not Implemented**)  |  
|    1095   | IGP Metric          |  
|    1096   | Shared Risk Link   Group |
|    1097   | Opaque Link    Attribute   (**Not Implemented**)   | 
|    1098   | Link Name      (**Not Implemented**)      | 
|    1104     | Unidirectional Delay            |
|    1105     | Unidirectional Delay            |
|    1106     | Unidirectional Min-Max Delay       | 
|    1107     | Unidirectional Residual Bandwidth   |
|    1109     | Unidirectional Available Bandwidth        |
|    1110     | Unidirectional Utilized Bandwidth    |

### Prefix Attribute TLVs
| **TLV Code points**  | **Name**   |
|:---:|:---:| 
|      1152     | IGP Flags            |
|      1153     | IGP Route Tag    (**Not Implemented**)       | 
|      1154     | IGP Extended Route Tag   (**Not Implemented**)   |
|      1155     | Prefix Metric        |
|      1156     | OSPF Forwarding  Address    |
|      1157     | Opaque Prefix Attribute  (**Not Implemented**)   |    

 
