package com.iammsun.arsc.info;

import java.util.ArrayList;
import java.util.List;

import com.iammsun.arsc.Utils;

/**
 * struct ResStringPool_header in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResStringPool extends ResChunk {

	public ResStringPool(ResChunkHeader header, ResChunk parent) {
		super(header, parent);
	}

	@Override
	public ResStringPool parse(byte[] data, int start) {
		int offset = start + ResChunkHeader.getHeaderSize();

		// 获取字符串的个数
		byte[] stringCountByte = Utils.copyByte(data, offset, 4);
		stringCount = Utils.byte2int(stringCountByte);

		// 解析样式的个数
		byte[] styleCountByte = Utils.copyByte(data, offset + 4, 4);
		styleCount = Utils.byte2int(styleCountByte);

		// 这里表示字符串的格式:UTF-8/UTF-16
		byte[] flagByte = Utils.copyByte(data, offset + 8, 4);
		flags = Utils.byte2int(flagByte);

		// 字符串内容的开始位置
		byte[] stringStartByte = Utils.copyByte(data, offset + 12, 4);
		stringsStart = Utils.byte2int(stringStartByte);

		// 样式内容的开始位置
		byte[] sytleStartByte = Utils.copyByte(data, offset + 16, 4);
		stylesStart = Utils.byte2int(sytleStartByte);

		// 获取字符串内容的索引数组和样式内容的索引数组
		int[] stringIndexAry = new int[stringCount];
		int[] styleIndexAry = new int[styleCount];

		int stringIndex = offset + 20;
		for (int i = 0; i < stringCount; i++) {
			stringIndexAry[i] = Utils.byte2int(Utils.copyByte(data, stringIndex + i * 4, 4));
		}

		int styleIndex = stringIndex + 4 * stringCount;
		for (int i = 0; i < styleCount; i++) {
			styleIndexAry[i] = Utils.byte2int(Utils.copyByte(data, styleIndex + i * 4, 4));
		}

		// 每个字符串的头两个字节的最后一个字节是字符串的长度
		// 这里获取所有字符串的内容
		int stringContentIndex = styleIndex + styleCount * 4;
		int index = 0;
		while (index < stringCount) {
			byte[] stringSizeByte = Utils.copyByte(data, stringContentIndex, 2);
			int stringSize = (stringSizeByte[1] & 0x7F);
			String val = "";
			if (stringSize != 0) {
				try {
					val = new String(Utils.copyByte(data, stringContentIndex + 2, stringSize), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stringList == null) {
				stringList = new ArrayList<String>();
			}
			stringList.add(val);
			stringContentIndex += (stringSize + 3);
			index++;
		}
		return this;
	}

	public int stringCount;
	public int styleCount;

	public final static int SORTED_FLAG = 1;
	public final static int UTF8_FLAG = (1 << 8);

	public int flags;
	public int stringsStart;
	public int stylesStart;

	public List<String> stringList;
	public List<String> styleList;

	public int getHeaderSize() {
		return ResChunkHeader.getHeaderSize() + 4 + 4 + 4 + 4 + 4;
	}

	@Override
	public String toString() {
		String result = "header:" + header.toString() + "\n" + "stringCount:" + stringCount + ",styleCount:"
				+ styleCount + ",flags:" + flags + ",stringStart:" + stringsStart + ",stylesStart:" + stylesStart;
		if (stringList != null) {
			for (String string : stringList) {
				result += "\n" + string;
			}
		}
		return result;
	}

	/**
	 * index from 1
	 * 
	 * @param index
	 * @return
	 */
	public String getString(int index) {
		return stringList.get(index);
	}

	public boolean isUTF8() {
		return (flags & UTF8_FLAG) != 0;
	}

}
