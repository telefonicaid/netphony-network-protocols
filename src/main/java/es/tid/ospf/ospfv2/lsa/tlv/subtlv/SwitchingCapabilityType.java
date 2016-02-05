package es.tid.ospf.ospfv2.lsa.tlv.subtlv;

/**
 * Switching Capability Type.
 * 
 *    1     Packet-Switch Capable-1 (PSC-1)
      2     Packet-Switch Capable-2 (PSC-2)
      3     Packet-Switch Capable-3 (PSC-3)
      4     Packet-Switch Capable-4 (PSC-4)
      51    Layer-2 Switch Capable  (L2SC)
      100   Time-Division-Multiplex Capable (TDM)
      150   Lambda-Switch Capable   (LSC)
      200   Fiber-Switch Capable    (FSC)
 * 
 * @author ogondio
 *
 */
public class SwitchingCapabilityType {
	public static final int PACKET_SWITCH_CAPABLE_1=1;
	public static final int PACKET_SWITCH_CAPABLE_2=2;
	public static final int PACKET_SWITCH_CAPABLE_3=3;
	public static final int PACKET_SWITCH_CAPABLE_4=4;
	public static final int LAYER_2_SWITCH_CAPABLE=51;
	public static final int TIME_DIVISION_SWITCH_CAPABLE=100;
	public static final int LAMBDA_SWITCH_CAPABLE=150;
	public static final int FIBER_SWITCH_CAPABLE=200;

}
