package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_package  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTablePackage extends ResChunk {

	public ResTablePackage(ResChunkHeader header, ResChunk parent) {
		super(header, parent);
	}

	public int id;
	// public char[] name = new char[128];
	public String name; // new char[128]
	public int typeStrings;
	public int lastPublicType;
	public int keyStrings;
	public int lastPublicKey;
	public int typeIdOffset;
	public ResStringPool keyStringPool;
	public ResStringPool typeStringPool;

	@Override
	public String toString() {
		return "header:" + header + "\nid=" + id + ",name:" + name + ",typeStrings:" + typeStrings + ",lastPublicType:"
				+ lastPublicType + ",keyStrings:" + keyStrings + ",lastPublicKey:" + lastPublicKey;
	}

	@Override
	public ResTablePackage parse(byte[] data, int start) {
		int offset = start + ResChunkHeader.getHeaderSize();

		// 解析packId
		byte[] idByte = Utils.copyByte(data, offset, 4);
		id = Utils.byte2int(idByte);

		// 解析包名
		byte[] nameByte = Utils.copyByte(data, offset + 4, 128 * 2);// 这里的128是这个字段的大小，可以查看类型说明，是char类型的，所以要乘以2
		String packageName = new String(nameByte);
		name = Utils.filterStringNull(packageName);

		// 解析类型字符串的偏移值
		byte[] typeStrings = Utils.copyByte(data, offset + 4 + 128 * 2, 4);
		this.typeStrings = Utils.byte2int(typeStrings);

		// 解析lastPublicType字段
		byte[] lastPublicType = Utils.copyByte(data, offset + 8 + 128 * 2, 4);
		this.lastPublicType = Utils.byte2int(lastPublicType);

		// 解析keyString字符串的偏移值
		byte[] keyStrings = Utils.copyByte(data, offset + 12 + 128 * 2, 4);
		this.keyStrings = Utils.byte2int(keyStrings);

		// 解析lastPublicKey
		byte[] lastPublicKey = Utils.copyByte(data, offset + 16 + 128 * 2, 4);
		this.lastPublicKey = Utils.byte2int(lastPublicKey);

		// 解析typeIdOffset
		byte[] type = Utils.copyByte(data, offset + 20 + 128 * 2, 4);
		this.typeIdOffset = Utils.byte2int(type);
		return this;
	}
}
