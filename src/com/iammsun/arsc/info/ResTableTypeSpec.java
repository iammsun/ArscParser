package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_typeSpec  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableTypeSpec extends ResChunk {

	public ResTableTypeSpec(ResChunkHeader header, ResChunk parent) {
		super(header, parent);
	}

	public final static int SPEC_PUBLIC = 0x40000000;

	public byte id;
	public byte res0;
	public short res1;
	public int entryCount;
	public int[] idEntries;
	public String specName;

	@Override
	public String toString() {
		String result = header.toString() + "\nid:" + id + ", res0:" + res0 + ", res1:" + res1
				+ ", entryCount:" + entryCount + ", specName:" + specName;
//		if (entryCount > 0) {
//			for (int id : idEntries) {
//				result += "\n" + Utils.bytesToHexString(Utils.int2Byte(id));
//			}
//		}
		return result;
	}

	@Override
	public ResTableTypeSpec parse(byte[] data, int start) {
		int offset = start + ResChunkHeader.getHeaderSize();

		// 解析id类型
		byte[] idByte = Utils.copyByte(data, offset, 1);
		id = (byte) (idByte[0] & 0xFF);
		specName = ((ResTablePackage) parent).typeStringPool.stringList.get(id - 1);

		// 解析res0字段,这个字段是备用的，始终是0
		byte[] res0Byte = Utils.copyByte(data, offset + 1, 1);
		res0 = (byte) (res0Byte[0] & 0xFF);

		// 解析res1字段，这个字段是备用的，始终是0
		byte[] res1Byte = Utils.copyByte(data, offset + 2, 2);
		res1 = Utils.byte2Short(res1Byte);

		// entry的总个数
		byte[] entryCountByte = Utils.copyByte(data, offset + 4, 4);
		entryCount = Utils.byte2int(entryCountByte);

		idEntries = new int[entryCount];
		int intAryOffset = start + header.headerSize;
		for (int i = 0; i < entryCount; i++) {
			int element = Utils.byte2int(Utils.copyByte(data, intAryOffset + i * 4, 4));
			idEntries[i] = element;
		}
		return this;
	}

}
