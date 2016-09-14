package com.iammsun.arsc.info;

/**
 * create by sunmeng on 9/15/16
 */
public class ResourceType {

	public final static int RES_NULL_TYPE = 0x0000;
	public final static int RES_STRING_POOL_TYPE = 0x0001;
	public final static int RES_TABLE_TYPE = 0x0002;
	public final static int RES_XML_TYPE = 0x0003;
	public final static int RES_XML_FIRST_CHUNK_TYPE = 0x0100;
	public final static int RES_XML_START_NAMESPACE_TYPE = 0x0100;
	public final static int RES_XML_END_NAMESPACE_TYPE = 0x0101;
	public final static int RES_XML_START_ELEMENT_TYPE = 0x0102;
	public final static int RES_XML_END_ELEMENT_TYPE = 0x0103;
	public final static int RES_XML_CDATA_TYPE = 0x0104;
	public final static int RES_XML_LAST_CHUNK_TYPE = 0x017f;
	public final static int RES_XML_RESOURCE_MAP_TYPE = 0x0180;
	public final static int RES_TABLE_PACKAGE_TYPE = 0x0200;
	public final static int RES_TABLE_TYPE_TYPE = 0x0201;
	public final static int RES_TABLE_TYPE_SPEC_TYPE = 0x0202;

	public static boolean isValid(short type) {
		switch (type) {
		case RES_STRING_POOL_TYPE:
		case RES_TABLE_TYPE:
		case RES_XML_TYPE:
		case RES_XML_RESOURCE_MAP_TYPE:
		case RES_TABLE_PACKAGE_TYPE:
		case RES_TABLE_TYPE_TYPE:
		case RES_TABLE_TYPE_SPEC_TYPE:
			return true;
		default:
			return type >= RES_XML_FIRST_CHUNK_TYPE && type <= RES_XML_LAST_CHUNK_TYPE;
		}
	}
}
