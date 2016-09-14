package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_header  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTable extends ResChunk {
	
	public static ResTable gloabl;

	public ResTable(ResChunkHeader header, ResChunk parent) {
		super(header, parent);
		gloabl = this;
	}

	public int packageCount;
	public ResStringPool stringPool;
	public ResTablePackage tablePackage;

	public int getHeaderSize() {
		return ResChunkHeader.getHeaderSize() + 4;
	}

	@Override
	public String toString() {
		return "header:" + header.toString() + "\n" + "packageCount:" + packageCount;
	}

	@Override
	public ResTable parse(byte[] data, int start) {
		// 解析PackageCount个数(一个apk可能包含多个Package资源)
		byte[] packageCountByte = Utils.copyByte(data, start + ResChunkHeader.getHeaderSize(), 4);
		packageCount = Utils.byte2int(packageCountByte);
		return this;
	}
}
