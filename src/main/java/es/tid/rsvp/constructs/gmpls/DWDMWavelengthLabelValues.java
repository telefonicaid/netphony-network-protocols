package es.tid.rsvp.constructs.gmpls;

public class DWDMWavelengthLabelValues {
	
	/**
	 *  (1) Grid: 3 bits

   The value for Grid is set to 1 for the ITU-T DWDM grid as defined in
   [G.694.1].

   +----------+---------+
   |   Grid   |  Value  |
   +----------+---------+
   | Reserved |    0    |
   +----------+---------+
   |ITU-T DWDM|    1    |
   +----------+---------+
   |ITU-T CWDM|    2    |
   +----------+---------+
   |ITU-T Flex|    3    |
   +----------+---------+
   |Future use|  4 - 7  |
   +----------+---------+
	 */
	
	/**
	 * ITU-T DWDM Grid
	 */
	public static final int ITU_T_DWDM_GRID=1;

	/**
	 * ITU-T CWDM Grid
	 */
	public static final int ITU_T_CWDM_GRID=2;
	
	/**
	 * ITU-T CWDM Grid
	 */
	public static final int ITU_T_FLEX=3;
	
	/**
	 * DWDM channel spacing is defined as follows.

   +----------+---------+
   |C.S. (GHz)|  Value  |
   +----------+---------+
   | Reserved |    0    |
   +----------+---------+
   |    100   |    1    |
   +----------+---------+
   |    50    |    2    |
   +----------+---------+
   |    25    |    3    |
   +----------+---------+
   |    12.5  |    4    |
   +----------+---------+
   |    6.25  |    5    |
   +----------+---------+
   |Future use|  5 - 15 |
   +----------+---------+
	 */
	
	/**
	 * 100 GHz DWDM Channel Spacing
	 */
	public static final int DWDM_CHANNEL_SPACING_100_GHZ=1;
	
	/**
	 * 50 GHz DWDM Channel Spacing
	 */
	public static final int DWDM_CHANNEL_SPACING_50_GHZ=2;
	
	/**
	 * 25 GHz DWDM Channel Spacing
	 */
	public static final int DWDM_CHANNEL_SPACING_25_GHZ=3;
	
	/**
	 * 12.5 GHz DWDM Channel Spacing
	 */
	public static final int DWDM_CHANNEL_SPACING_12_5_GHZ=4;
	
	/**
	 * 6.25 GHz SSON Flexible grid
	 */
	public static final int DWDM_CHANNEL_SPACING_6_25_GHZ=5;

}
