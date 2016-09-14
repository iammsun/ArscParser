package com.iammsun.arsc.info;

import com.iammsun.arsc.Utils;

/**
 * struct ResTable_ref in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResTableRef implements Parseable{
	
	public int ident;
	
	public static int getSize(){
		return 4;
	}
	
	@Override
	public String toString(){
		return "ident:"+Utils.bytesToHexString(Utils.int2Byte(ident));
	}

	@Override
	public ResTableRef parse(byte[] data, int start) {
		byte[] identByte = Utils.copyByte(data, start, ResTableRef.getSize());
		ident = Utils.byte2int(identByte);
		return this;
	}

}
