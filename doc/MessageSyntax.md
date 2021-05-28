Implemented PCEP messages syntax
====================================

This file shows the implemented syntax.
Beware that it may not represent or be fully updated to the latest version of a draft

Syntax generated with jPCEPGrammar

Open Message
------------
```
Open Message::=
<Common Header><OPEN>
```

KeepAlive Message
-----------------
```
Keepalive Message::=<Common Header>
```

Path Computation Request (PCReq) Message
-------------

```
PCReq Message::=<Common Header>[<svec-tuple-list>]<request-list>

<svec-tuple-list>::=<svec-tuple>[<svec-tuple-list>]

<svec-tuple>::=	<SVEC>[<OF>][<metric-list>][<GC>][<XRO>]

<metric-list>::=<METRIC>[<metric-list>]

<request-list>::=<request>[<request-list>]

<request>::=<RP><segment-computation>|<path-key-expansion>

<segment-computation>::=<END-POINTS>[<CLASSTYPE>][<LSPA>][<BANDWIDTH>][<metric-list>][<OF>][<rro-bw-pair>][<IRO>][<LOAD-BALANCING>][<XRO>]

<rro-bw-pair>::=<RRO>[<BANDWIDTH>]

<path-key-expansion>::=<PATH-KEY>
     
```

Path Computation Reply (PCRep) Message
-------------

```
<CRep Message::=<Common Header>
                <response-list>

<response-list>::=<response>[<response-list>]

<response>::=<RP>[<NO-PATH>][<attribute-list>][<path-list>]

<attribute-list>::=[<OF>][<LSPA>][<BANDWIDTH>][<metric-list>][<IRO>][<XRO>]

<metric-list>::=<METRIC>[<metric-list>]

<path-list>::=<path>[<path-list>]

<path>::=<ERO>

<attribute-list>
```
Notification (PCNtf) Message
-------------

```
PCNtf Message::=<Common Header> <notify-list>

<notify-list>::=<notify>[<notify-list>]

<notify>::=[<request-id-list>]<notification-list>

<request-id-list>::=<RP>[<request-id-list>]

<notification-list>::=<NOTIFICATION>[<notification-list>]
```

Error (PCErr) Message
-------------

```
PCErr Message::=<Common Header><err-open>|<error>[<error-list>]

<err-open>::=<error-obj-list>[<OPEN>]

<error-obj-list>::=<PCEP-ERROR>[<error-obj-list>]

<error>::=[<request-id-list>]<error-obj-list>

<request-id-list>::=<RP>[<request-id-list>]

<error-list>::=<error>[<error-list>]
```

Close Message
-------------

```
Close Message::=<Common Header><CLOSE>
```

Path Computation Monitoring Request (PCMonReq) Message
-------------

```
PCMonReq Message::=<Common Header><MONITORING><PCC-ID-REQ>[<pce-list>][<svec-list>][<request-list>]
<pce-list>::=
<PCE-ID>
[<pce-list>]<svec-list>::=
<SVEC>
[<svec-list>]<request-list>::=
<request>
[<request-list>]<request>::=
<RP>
<segment-computation>|<path-key-expansion>
<segment-computation>::=
[<vendor-info-list>]
<END-POINTS>
[<LSP>]
[<CLASSTYPE>]
[<LSPA>]
[<BANDWIDTH>]
[<metric-list>]
[<OF>]
[<rro-bw-pair>]
[<IRO>]
[<LOAD-BALANCING>]
[<XRO>]
<vendor-info-list>::=
<VENDOR-INFORMATION>
[<vendor-info-list>]<metric-list>::=
<METRIC>
[<metric-list>]<rro-bw-pair>::=
<RRO>
[<BANDWIDTH>]
<path-key-expansion>::=
<PATH-KEY>
```

Path Computation Monitoring Reply (PCMonRep) Message
-------------

```
PCMonRep Message::=<Common Header><MONITORING><PCC-ID-REQ>[<RP>][<metric-pce-list>]

<metric-pce-list>::=<metric-pce>[<metric-pce-list>]

<metric-pce>::=<PCE-ID>[<PROC-TIME>][<OVERLOAD>]
```

Report (PCRpt) Message
-------------

```
PCRpt Message::=<Common Header><state-report-list>

<state-report-list>::=<state-report>[<state-report-list>]

<state-report>::=<LSP><path>

<path>::=<ERO><attribute-list>

<attribute-list>::=[<OF>][<LSPA>][<BANDWIDTH>][<metric-list>][<IRO>][<XRO>]

<metric-list>::=<METRIC>[<metric-list>]
```

Update (PCUpd) Message
-------------
```
PCUpd Message::=<Common Header><update-request-list>

<update-request-list>::=<update-request>[<update-request-list>]

<update-request>::=<LSP><path>

<path>::=<ERO><attribute-list>

<attribute-list>::=[<OF>][<LSPA>][<BANDWIDTH>][<metric-list>][<IRO>][<XRO>]

<metric-list>::=<METRIC>[<metric-list>]
```
LSP Initiate (PCInitiate) Request Message
-------------
```
PCInitiate Message::=<Common Header><PCE-initiated-lsp-list>

<PCE-initiated-lsp-list>::=<PCE-initiated-lsp-instantiation>|<PCE-initiated-lsp-deletion>[<PCE-initiated-lsp-list>]
<PCE-initiated-lsp-instantiation>::=<SRP><END-POINTS><ERO>[<attribute-list>]

<attribute-list>::=[<OF>][<LSPA>][<BANDWIDTH>][<metric-list>][<IRO>][<XRO>]

<metric-list>::=<METRIC>[<metric-list>]

<PCE-initiated-lsp-deletion>::=<SRP>
```
StartTLS Message
-------------
```
 <StartTLS Message>::= <Common Header>
```

### Experimental proprietary messages

* PCEPTELinkSuggestion
* PCEPTELinkConfirmation
* PCEPTELinkTearDownSuggestion



