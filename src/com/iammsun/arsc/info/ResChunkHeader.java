package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResChunk_header in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResChunkHeader implements Parseable {

	public short type;
	public short headerSize;
	public int size;

	public static int getHeaderSize() {
		return 2 + 2 + 4;
	}

	@Override
	public String toString() {
		return "type:" + Utils.bytesToHexString(Utils.int2Byte(type)) + ",headerSize:" + headerSize + ",size:" + size;
	}

	public boolean hasChild(byte[] data, int offset) {
		int childOffset = offset + headerSize;
		ResChunkHeader childHeader = new ResChunkHeader().parse(data, childOffset);
		return ResourceType.isValid(childHeader.type) && childHeader.size > 0
				&& (childHeader.size + childOffset) <= size;
	}

	@Override
	public ResChunkHeader parse(byte[] data, int start) {
		// 解析头部类型
		byte[] typeByte = Utils.copyByte(data, start, 2);
		type = Utils.byte2Short(typeByte);

		// 解析头部大小
		byte[] headerSizeByte = Utils.copyByte(data, start + 2, 2);
		headerSize = Utils.byte2Short(headerSizeByte);

		// 解析整个Chunk的大小
		byte[] tableSizeByte = Utils.copyByte(data, start + 4, 4);
		size = Utils.byte2int(tableSizeByte);
		return this;
	}
}
