Detailed PCEP Implementation Support (v1.0.1)
=============================================

Messages
--------

### Standard (RFC & IETF draft) Messages
* Open Message
* KeepAlive
* PCReq Message
* PCRep Message
* PCNtf Message
* PCErr Message
* Close Message
* PCMonReq Message
* PCMonRep Message
* PCRpt Message
* PCUpd Message
* PCInitiate Message

### Experimental propietary messages

* PCEPTELinkSuggestion
* PCEPTELinkConfirmation
* PCEPTELinkTearDownSuggestion

Objects
-------

### Standard (RFC & IETF draft) Objects

* Bandwidth
* Close
* EndPoints
* ExcludeRouteObject
* ExplicitRouteObject
* IncludeRouteObject
* InterLayer
* LoadBalancing
* LSP
* LSPA
* Metric
* Monitoring
* NoPath
* Notification
* ObjectiveFunction
* ObjectParameters
* OPEN
* PccReqId
* PceId
* PCEPError Object
* ProcTime
* ReportedRouteObject
* ReqAdapCap
* RequestParameters
* ServerIndication
* SRP
* Svec
* SwitchLayer
* WavelengthAssignementObject

### Experimental propietary object

* AdvanceReservation Object
* BitmapLabelSet Object
* NetQuotationIPv4
* NetQuotationIPv6
* NetQuotationNSAP
* Reservation
* ReservationConf
* SuggestedLabel

TLVs
----

### Standard (RFC & IETF draft) TLVs

* DomainID TLV (with non-standard encoding)
* EndPointIPv4TLV
* GMPLSCapabilityTLV
* LabelRequestTLV (encoding TBD)
* LSPDatabaseVersion TLV
* LSPErrorCodeTLV
* LSPIdentifiersTLV
* NoPathTLV
* OF_LIST_TLV
* OverloadedDurationTLV
* PathReservationTLV
* PathSetupTLV
* PCE_ID_TLV
* PCE_Redundancy_Group_Identifier_TLV
* ReqMissingTLV
* RequestInfoTLV
* RSVPErrorSpecTLV
* SRCapabilityTLV
* StatefulCapabilityTLV
* SymbolicPathNameTLV
* TunnelIDTLV
* UnnumberedEndpointTLV


### Experimental propietary TLVs

* BandwidthTLV
* EndPointApplicationTLV
* EndPointServerTLV
* EndPointsNSAPTLV
* EndPointStorageTLV
* ITAdvertisementTLV
* MaxRequestTimeTLV
* OSPFTE_LSA_TLV
* ReachabilityTLV
* ReservationIDTLV
* ServerTLV
* StorageTLV
* XifiEndPointTLV