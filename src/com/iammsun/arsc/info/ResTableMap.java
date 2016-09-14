package com.iammsun.arsc.info;

/**
 * struct ResTable_map  in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableMap implements Parseable {

	public ResTableRef name;
	public ResValue value;

	public ResTableMap() {
		name = new ResTableRef();
		value = new ResValue();
	}

	public static int getSize() {
		return ResTableRef.getSize() + ResValue.getSize();
	}

	@Override
	public String toString() {
		return name.toString() + ",value:" + value.toString();
	}

	@Override
	public ResTableMap parse(byte[] data, int start) {
		name.parse(data, start);
		value.parse(data, start + ResTableRef.getSize());
		return this;
	}

}
