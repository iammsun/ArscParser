package com.iammsun.arsc.info;

/**
 * struct ResStringPool_span in ResourceTypes.h 
 * 
 * create by sunmeng on 9/15/16
 */
public class ResStringPoolSpan {
	
	public final static int END = 0xFFFFFFFF;
	
	public ResStringPoolRef name;
	public int firstChar;
	public int lastChar;
	
	@Override
	public String toString(){
		return "name:"+name.toString()+",firstChar:"+firstChar+",lastChar:"+lastChar;
	}

}
