package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_type  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableType extends ResChunk {

	public ResTableType(ResChunkHeader header, ResChunk parent) {
		super(header, parent);
	}

	public final static int NO_ENTRY = 0xFFFFFFFF;

	public byte id;
	public byte res0;
	public short res1;
	public int entryCount;
	public int entriesStart;
	public String typeName;
	public int[] offsetEntries;
	public ResTableMapEntry[] resTableEntries;
	public ResValue[] resValueAry;

	public ResTableConfig resConfig;

	public static int getSize() {
		return ResChunkHeader.getHeaderSize() + 1 + 1 + 2 + 4 + 4;
	}

	@Override
	public String toString() {
		String result = header.toString() + "\n\tid:" + id + ",res0:" + res0 + ",res1:" + res1 + ",entryCount:"
				+ entryCount + ",entriesStart:" + entriesStart + ",typeName:" + typeName + "\n\tconfig:" + resConfig;
		if (entryCount > 0) {
			for (int index = 0; index < entryCount; index++) {
				result += "\n\t\tResourceId:" + Utils.bytesToHexString(Utils.int2Byte(getResId(index)));
				ResTableMap[] maps = resTableEntries[index] == null ? null : resTableEntries[index].tableMaps;
				ResValue value = resValueAry[index];
				if (maps != null) {
					for (ResTableMap tableMap : maps) {
						result += "\n\t\t\t" + tableMap;
					}
				}
				if (value != null && value.dataType != ResValue.TYPE_NULL)
					result += "\n\t\t\t" + value;
			}
		}
		return result;
	}

	@Override
	public ResTableType parse(byte[] data, int start) {
		int offset = (start + ResChunkHeader.getHeaderSize());

		// 解析type的id值
		byte[] idByte = Utils.copyByte(data, offset, 1);
		id = (byte) (idByte[0] & 0xFF);
		typeName = ResTable.gloabl.tablePackage.typeStringPool.getString(id - 1);

		// 解析res0字段的值，备用字段，始终是0
		byte[] res0 = Utils.copyByte(data, offset + 1, 1);
		this.res0 = (byte) (res0[0] & 0xFF);

		// 解析res1字段的值，备用字段，始终是0
		byte[] res1 = Utils.copyByte(data, offset + 2, 2);
		this.res1 = Utils.byte2Short(res1);

		byte[] entryCountByte = Utils.copyByte(data, offset + 4, 4);
		entryCount = Utils.byte2int(entryCountByte);

		byte[] entriesStartByte = Utils.copyByte(data, offset + 8, 4);
		entriesStart = Utils.byte2int(entriesStartByte);

		resConfig = new ResTableConfig().parse(data, offset + 12);

		// 先获取entryCount个int数组
		offsetEntries = new int[entryCount];
		for (int i = 0; i < entryCount; i++) {
			int element = Utils.byte2int(Utils.copyByte(data, start + header.headerSize + i * 4, 4));
			offsetEntries[i] = element;
		}

		resTableEntries = new ResTableMapEntry[entryCount];
		resValueAry = new ResValue[entryCount];

		for (int i = 0; i < entryCount; i++) {
			int valueOffset = start + entriesStart + offsetEntries[i];
			ResTableEntry tableEntry = new ResTableEntry().parse(data, valueOffset);
			// 这里需要注意的是，先判断entry的flag变量是否为1,如果为1的话，那就ResTable_map_entry
			if (tableEntry.flags == ResTableEntry.FLAG_COMPLEX) {
				resTableEntries[i] = new ResTableMapEntry().parse(data, valueOffset);
			} else {
				// 这里是简单的类型的value
				resValueAry[i] = new ResValue().parse(data, valueOffset + ResTableEntry.getSize());
			}

		}
		return this;
	}

	public int getResId(int entryId) {
		return ((ResTable.gloabl.tablePackage.id << 24) | ((id & 0xFF) << 16) | (entryId & 0xFFFF));
	}
}
