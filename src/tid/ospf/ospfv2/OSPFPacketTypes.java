package tid.ospf.ospfv2;

/**
 *               Type   Description
                          ________________________________
                          1      Hello
                          2      Database Description
                          3      Link State Request
                          4      Link State Update
                          5      Link State Acknowledgment
 * @author ogondio
 *
 */
public class OSPFPacketTypes {
	public static final int OSPFv2_HELLO_PACKET=1;
	public static final int OSPFv2_DATABASE_DESCRIPTION=2;
	public static final int OSPFv2_LINK_STATE_REQUEST=3;
	public static final int OSPFv2_LINK_STATE_UPDATE=4;
	public static final int OSPFv2_LINK_STATE_ACKNOWLEDGEMENT=5;
}
