package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResStringPool_ref in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResStringPoolRef implements Parseable {

	public int index;
	
	public static int getSize(){
		return 4;
	}
	
	@Override
	public String toString(){
		return "index:"+index;
	}

	@Override
	public ResStringPoolRef parse(byte[] data, int start) {
		index = Utils.byte2int(Utils.copyByte(data, start, 4));
		return this;
	}
	
}
