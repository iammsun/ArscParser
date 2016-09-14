package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_map_entry  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableMapEntry extends ResTableEntry {

	public ResTableRef parent = new ResTableRef();
	public int count;
	public ResTableMap[] tableMaps;

	public static int getSize() {
		return ResTableEntry.getSize() + ResTableRef.getSize() + 4;
	}

	@Override
	public String toString() {
		String result = super.toString() + ",parent:" + parent.toString() + ",count:" + count;
		if (tableMaps != null) {
			for (ResTableMap map : tableMaps) {
				result += "\n\t" + map;
			}
		}
		return result;
	}

	@Override
	public ResTableMapEntry parse(byte[] data, int start) {
		super.parse(data, start);
		parent.parse(data, start + ResTableEntry.getSize());
		byte[] countByte = Utils.copyByte(data, start + ResTableEntry.getSize() + 4, 4);
		count = Utils.byte2int(countByte);
		tableMaps = new ResTableMap[count];
		for (int index = 0; index < count; index++) {
			tableMaps[index] = new ResTableMap().parse(data,
					start + ResTableMapEntry.getSize() + ResTableMap.getSize() * index);
		}
		return this;
	}

}
