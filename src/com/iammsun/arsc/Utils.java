package com.iammsun.arsc;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * create by sunmeng on 9/15/16
 */
public class Utils {

	public static void closeQuiet(Closeable closeable) {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}

	public static byte[] read(String filePath) {
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(filePath);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeQuiet(fis);
			closeQuiet(bos);
		}
		return null;
	}

	public static int byte2int(byte[] res) {
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) | ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}

	public static byte[] int2Byte(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	public static short byte2Short(byte[] b) {
		short s = 0;
		short s0 = (short) (b[0] & 0xff);
		short s1 = (short) (b[1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("0x");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] copyByte(byte[] src, int start, int len) {
		if (src == null) {
			return null;
		}
		if (start > src.length) {
			return null;
		}
		if ((start + len) > src.length) {
			return null;
		}
		if (start < 0) {
			return null;
		}
		if (len <= 0) {
			return null;
		}
		byte[] resultByte = new byte[len];
		for (int i = 0; i < len; i++) {
			resultByte[i] = src[i + start];
		}
		return resultByte;
	}

	public static byte[] reverseBytes(byte[] bytess) {
		byte[] bytes = new byte[bytess.length];
		for (int i = 0; i < bytess.length; i++) {
			bytes[i] = bytess[i];
		}
		if (bytes == null || (bytes.length % 2) != 0) {
			return bytes;
		}
		int i = 0, len = bytes.length;
		while (i < (len / 2)) {
			byte tmp = bytes[i];
			bytes[i] = bytes[len - i - 1];
			bytes[len - i - 1] = tmp;
			i++;
		}
		return bytes;
	}

	public static String filterStringNull(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		byte[] strByte = str.getBytes();
		ArrayList<Byte> newByte = new ArrayList<Byte>();
		for (int i = 0; i < strByte.length; i++) {
			if (strByte[i] != 0) {
				newByte.add(strByte[i]);
			}
		}
		byte[] newByteAry = new byte[newByte.size()];
		for (int i = 0; i < newByteAry.length; i++) {
			newByteAry[i] = newByte.get(i);
		}
		return new String(newByteAry);
	}

}
