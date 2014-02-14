package tid.bgp.bgp4.update.fields;
/**
 *  OSPFv2 Multi-Instance Extensions
 * 
 * <a href="http://tools.ietf.org/html/rfc6549#page-4">RFC 6549</a>.
 * 
 *  The following OSPFv2 Instance IDs have been defined:

   0      Base IPv4 Instance - This is the default IPv4 routing instance
          corresponding to default IPv4 unicast routing and the
          attendant IPv4 routing table.  Use of this Instance ID
          provides backward compatibility with the base OSPF
          specification [OSPFV2].

   1      Base IPv4 Multicast Instance - This IPv4 instance corresponds
          to the separate IPv4 routing table used for the Reverse Path
          Forwarding (RPF) checking performed on IPv4 multicast traffic.

   2      Base IPv4 In-band Management Instance - This IPv4 instance
          corresponds to a separate IPv4 routing table used for network
          management applications.

   3-127  Private Use - These Instance IDs are reserved for definition
          and semantics defined by the local network administrator.  For
          example, separate Interface Instance IDs and their
          corresponding OSPFv2 instances could be used to support
          independent non-congruent topologies for different classes of
          IPv4 unicast traffic.  The details of such deployments are
          beyond the scope of this document.
          
 * @author mcs
 *
 */
public class InstanceIDTypes {
	public static final int Base_IPv4_Instance = 0;
	public static final int Base_IPv4_MulticasInstance= 1;
	public static final int Base_IPv4_InBandManagementInstance = 2;

}
