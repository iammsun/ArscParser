package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_entry  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableEntry implements Parseable {

	public final static int FLAG_COMPLEX = 0x0001;
	public final static int FLAG_PUBLIC = 0x0002;

	public short size;
	public short flags;

	public ResStringPoolRef key;

	public ResTableEntry() {
		key = new ResStringPoolRef();
	}

	public static int getSize() {
		return 2 + 2 + ResStringPoolRef.getSize();
	}

	@Override
	public String toString() {
		return "size:" + size + ",flags:" + flags + ",key:" + key.toString() + ",str:"
				+ ResTable.gloabl.stringPool.getString(key.index);
	}

	@Override
	public ResTableEntry parse(byte[] data, int start) {

		byte[] sizeByte = Utils.copyByte(data, start, 2);
		size = Utils.byte2Short(sizeByte);

		byte[] flagByte = Utils.copyByte(data, start + 2, 2);
		flags = Utils.byte2Short(flagByte);

		key.parse(data, start + 4);
		return this;
	}

}
