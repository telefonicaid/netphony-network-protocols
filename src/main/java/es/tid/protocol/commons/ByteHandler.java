package es.tid.protocol.commons;

import java.nio.ByteBuffer;
import java.util.BitSet;



public class ByteHandler {

	/*
	 * 
	 */
	
	/**
	 * Returns an int from the bytes between begin and end. 
	 * and end position too.
	 * @param begin position of the start 
	 * @param end position of the end 
	 * @param bytes Bytes to copy
	 * @return int number
	 */
	public static int easyCopy(int begin,int end,byte ... bytes)
	{
		int dest = 0;

		if ((begin<0)||(begin>end)||(bytes.length*8<=end))
		{
			//throw new Exception();
		}

		byte[] bCleared = new byte[bytes.length];

		for (int i=0;i<bytes.length;i++)
		{
			if ((i<begin/8)||(i>end/8))
			{
				bCleared[i]=0;
			}
			else
			{
				bCleared[i]=bytes[i];
			}
		}

		byte forClearing = 0;

		if (begin/8 == end/8)
		{
			for (int i = 0; i < end-begin+1;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | 0x01) ;
			}
			forClearing = (byte)((0xFF & forClearing) << (8-(end%8)-1));
			bCleared[end/8] = (byte)((0xFF & bCleared[end/8]) & (0xFF & forClearing));
		}
		else
		{

			for (int i = 0; i < 8-begin%8;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | 0x01) ;
			}

			bCleared[begin/8] = (byte)((0xFF & bCleared[begin/8]) & (0xFF & forClearing));

			forClearing = 0;
			for (int i = 0; i < end%8+1;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | (byte)0x01) ;
			}
			forClearing = (byte)(forClearing << (8-end%8-1));
			bCleared[end/8] = (byte) (0xFF & ((0xFF & (bCleared[end/8]) & (0xFF & forClearing))));
		}

		if(begin/8 == end/8)
		{
			dest = (0xFF & ((0xFF & bCleared[end/8]) >> (8 - end%8 -1)));
		}
		else
		{
			dest = bCleared[begin/8] & 0xFF;

			for(int i = begin/8+1;i < end/8;i++)
			{
				dest = dest << 8 | (0xFF & bCleared[i]);
			}
			dest = dest << (end%8+1) | (0xFF & ((0xFF & bCleared[end/8]) >> (8 - end%8-1)));
		}

		return dest;
	}

	/*
	 * 
	 */
/**
 * Returns a long from the bytes between begin and end. 
 * @param begin position of the start 
 * @param end position of the end 
 * @param bytes Bytes to take the number
 * @return long number 
 */
	public static long easyCopyL(int begin,int end,byte ... bytes) //throws Exception
	{
		long dest = 0;

		if ((begin<0)||(begin>end)||(bytes.length*8<=end)||(end-begin>63))
		{
			//throw new Exception();
			//Throw excepcion in futuru, -1 is a valid return value!!
			return -1;
		}

		byte[] bCleared = new byte[bytes.length];

		for (int i=0;i<bytes.length;i++)
		{
			if ((i<begin/8)||(i>end/8))
			{
				bCleared[i]=0;
			}
			else
			{
				bCleared[i]=bytes[i];
			}
		}

		byte forClearing = 0;

		if (begin/8 == end/8)
		{
			for (int i = 0; i < end-begin+1;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | 0x01) ;
			}
			forClearing = (byte)((0xFF & forClearing) << (8-end-1));
			bCleared[end/8] = (byte)((0xFF & bCleared[end/8]) & (0xFF & forClearing));
		}
		else
		{

			for (int i = 0; i < 8-begin%8;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | 0x01) ;
			}

			bCleared[begin/8] = (byte)((0xFF & bCleared[begin/8]) & (0xFF & forClearing));

			forClearing = 0;
			for (int i = 0; i < end%8+1;i++)
			{
				forClearing = (byte)(((0xFF & forClearing) << 1) | (byte)0x01) ;
			}
			forClearing = (byte)(forClearing << (8-end%8-1));
			bCleared[end/8] = (byte) (0xFF & ((0xFF & (bCleared[end/8]) & (0xFF & forClearing))));
		}

		if(begin/8 == end/8)
		{
			dest = (0xFF & ((0xFF & bCleared[end/8]) >> (8 - end%8 -1)));
		}
		else
		{
			dest = bCleared[begin/8] & 0xFF;

			for(int i = begin/8+1;i < end/8;i++)
			{
				dest = dest << 8 | (0xFF & bCleared[i]);
			}
			dest = dest << (end%8+1) | (0xFF & ((0xFF & bCleared[end/8]) >> (8 - end%8-1)));
		}

		return dest;
	}

	public static byte[] toByteArray(BitSet bits, int length) {
		byte[] bytes = new byte[(length + 7) / 8];
		for (int i=0; i<length; i++) {
			if (bits.get(i)) {
				bytes[i/8] |= 1<<(i%8);
			}
		}
		return bytes;
	}

	/** Substitute of Bitset.valueOf();
	 * 
	 * This function is available in SE7(My PC) but not in SE6(simpsongsw).
	 * So this function tries to emulate it
	 * 
	 *  Returns a new bit set containing all the bits in the given byte array.
		More precisely, 
		BitSet.valueOf(bytes).get(n) == ((bytes[n/8] & (1<<(n%8))) != 0) 
		for all n < 8 * bytes.length.

		This method is equivalent to BitSet.valueOf(ByteBuffer.wrap(bytes)).
	 * @param bs bytes 
	 * @return BitSet 
	 */
	private static BitSet myBitSetValueOf(byte[] bs)
	{
		BitSet bS = new BitSet();
		for (int n = 0; n < 8 * bs.length; n++)
		{
			bS.set(n ,((bs[n/8] & (1<<(n%8))) != 0));
		}
		return bS;
	}

	public static void bufferToBuffer(int begS, byte[] source,int begD, byte[] dest, int length)
	{
		BitSet sourceSet = myBitSetValueOf(source);
		BitSet destSet = myBitSetValueOf(dest);       

		for (int i=begS,j = begD;i<(begS+length);i++,j++)
		{
			if (sourceSet.get(i - (i%8) +(8-(i%8)-1)))
				destSet.set(j - (j%8) +(8-(j%8)-1));
			else
				destSet.clear(j - (j%8) +(8-(j%8)-1));
		}

		byte[] intSourceSet = toByteArray(sourceSet,source.length*8);
		byte[] intDestSet = toByteArray(destSet,dest.length*8);

		for (int i = 0;i < source.length;i++)
			source[i] = intSourceSet[i];
		for (int i = 0;i < dest.length;i++)
			dest[i] = intDestSet[i];        
	}

	public static void IntToBuffer(int begS, int begD, int length,int value , byte[] dest)
	{
		bufferToBuffer (begS, ByteBuffer.allocate(4).putInt(value).array(), begD, dest, length);
	}

	public static void LongToBuffer(int begS, int begD,int length,long value ,byte[] dest)
	{
		bufferToBuffer (begS, ByteBuffer.allocate(8).putLong(value).array(), begD, dest, length);
	}

	public static void BoolToBuffer(int bitPos,boolean value ,byte[] dest)
	{
		if (value)
		{
			IntToBuffer(0, bitPos, 1,-1 , dest);
		}
		else
		{
			IntToBuffer(0, bitPos, 1,0 , dest);
		}
	}

	public static String ByteMACToString(byte[] mac)
	{
		StringBuilder sb = new StringBuilder(mac.length*3);
		for (byte b : mac) {
			if (sb.length() > 0)
				sb.append(':');
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	public static String ByteDataPathToString(byte[] mac)
	{
		StringBuilder sb = new StringBuilder(16);
		for (byte b : mac) {
			if (sb.length() > 0)
				sb.append(':');
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	/**
	 * macAddress = "AA:BB:CC:DD:EE:FF";
	 * @param macAddress MAC address in String format
	 * @return byte array
	 */
	public static byte[] MACFormatStringtoByteArray(String macAddress)
	{
		String[] macAddressParts = macAddress.split(":");

		// convert hex string to byte values
		byte[] macAddressBytes = new byte[macAddressParts.length];
		for(int i=0; i<macAddressParts.length; i++)
		{
			Integer hex = Integer.parseInt(macAddressParts[i], 16);
			macAddressBytes[i] = hex.byteValue();
		}
		return macAddressBytes;
	}

	public static byte[] MACStringtoByteArray(String macAddress)
	{
		String[] macAddressParts = macAddress.split(":");

		// convert hex string to byte values
		byte[] macAddressBytes = new byte[6];
		for(int i=0; i<6; i++){
			Integer hex = Integer.parseInt(macAddressParts[i], 16);
			macAddressBytes[i] = hex.byteValue();
		}
		return macAddressBytes;
	}

	/**
	 * DataPath = "AA:BB:CC:DD:EE:FF:GG:HH"; 
	 * @param DataPath Data Path in String format "AA:BB:CC:DD:EE:FF:GG:HH"
	 * @return byte array
	 */
	public static byte[] DataPathFormatStringtoByteArray(String DataPath)
	{
		String[] DataPathParts = DataPath.split(":");

		// convert hex string to byte values
		byte[] macAddressBytes = new byte[DataPathParts.length];
		for(int i=0; i<DataPathParts.length; i++)
		{
			Integer hex = Integer.parseInt(DataPathParts[i], 16);
			macAddressBytes[i] = hex.byteValue();
		}
		return macAddressBytes;
	}

	public static byte[] DataPathStringtoByteArray(String DataPath)
	{
		String[] DataPathParts = DataPath.split(":");

		// convert hex string to byte values
		byte[] DataPathBytes = new byte[6];
		for(int i=0; i<8; i++){
			Integer hex = Integer.parseInt(DataPathParts[i], 16);
			DataPathBytes[i] = hex.byteValue();
		}
		return DataPathBytes;
	}


	public static void encode4bytesLong(long number, byte[] bytes, int offset ){
		bytes[offset]=(byte)((number>>24) & 0xFF);
		bytes[offset+1]=(byte)((number>>16) & 0xFF);
		bytes[offset+2]=(byte)((number>>8) & 0xFF);
		bytes[offset+3]=(byte)(number & 0xFF);
	}

	public static long decode4bytesLong( byte[] bytes, int offset) {
		long number=0;
		number=( (((long)bytes[offset]&(long)0xFF)<<24) | (((long)bytes[offset+1]&(long)0xFF)<<16) |( ((long)bytes[offset+2]&(long)0xFF)<<8) |  ((long)bytes[offset+3]& (long)0xFF) );
		return number;
	}
}
