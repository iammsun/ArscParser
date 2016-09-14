package com.iammsun.arsc;

import com.iammsun.arsc.info.ResChunk;
import com.iammsun.arsc.info.ResChunkHeader;
import com.iammsun.arsc.info.ResStringPool;
import com.iammsun.arsc.info.ResTable;
import com.iammsun.arsc.info.ResTablePackage;
import com.iammsun.arsc.info.ResTableType;
import com.iammsun.arsc.info.ResTableTypeSpec;
import com.iammsun.arsc.info.ResourceType;

/**
 * create by sunmeng on 9/15/16
 */
public class Parser {

	private static final String ARG_LIST_TYPE = "--list-type";
	private static final String ARG_LIST_SPEC = "--list-spec";
	private static final String ARG_LIST_STRING = "--list-string";
	private static final String ARG_LIST_KEY = "--list-key";
	private static final String ARG_LIST = "-list";

	public static void printHelp() {
		System.out.println(String.format("java -jar arsc-parser.jar filePath <%s> <%s> <%s> <%s> <%s specName>",
				ARG_LIST_TYPE, ARG_LIST_SPEC, ARG_LIST_STRING, ARG_LIST_KEY, ARG_LIST));
	}

	public static void main(String[] args) {

		boolean listType = false;
		boolean listSpec = false;
		boolean listString = false;
		boolean listKey = false;
		boolean isList = false;
		String specType = null;
		String filePath = null;

		if (args != null) {
			for (String arg : args) {

				if (ARG_LIST_TYPE.equals(arg)) {
					listType = true;
					isList = false;
					continue;
				}

				if (ARG_LIST_SPEC.equals(arg)) {
					listSpec = true;
					isList = false;
					continue;
				}

				if (ARG_LIST_STRING.equals(arg)) {
					listString = true;
					isList = false;
					continue;
				}

				if (ARG_LIST_KEY.equals(arg)) {
					listKey = true;
					isList = false;
					continue;
				}

				if (ARG_LIST.equals(arg)) {
					isList = true;
					continue;
				}

				if (isList) {
					isList = false;
					specType = arg;
					continue;
				}

				if (filePath != null) {
					printHelp();
					return;
				}

				filePath = arg;
			}
		}

		byte[] srcByte = Utils.read(filePath);

		if (srcByte == null) {
			printHelp();
			return;
		}
		ResChunk typeSpec = null;

		int offset = 0;
		while (offset < srcByte.length) {
			ResChunkHeader header = new ResChunkHeader();
			header.parse(srcByte, offset);
			ResChunk chunk = null;

			switch (header.type) {
			case ResourceType.RES_TABLE_TYPE:
				chunk = new ResTable(header, null).parse(srcByte, offset);
				break;
			case ResourceType.RES_TABLE_PACKAGE_TYPE:
				chunk = new ResTablePackage(header, ResTable.gloabl).parse(srcByte, offset);
				ResTable.gloabl.tablePackage = (ResTablePackage) chunk;
				break;
			case ResourceType.RES_STRING_POOL_TYPE:
				if (ResTable.gloabl.stringPool == null) {
					chunk = new ResStringPool(header, ResTable.gloabl).parse(srcByte, offset);
					ResTable.gloabl.stringPool = (ResStringPool) chunk;
					if (listString) {
						System.out.println(chunk);
					}
				} else if (offset == ResTable.gloabl.tablePackage.typeStrings + ResTable.gloabl.getHeader().headerSize
						+ ResTable.gloabl.stringPool.getHeader().size) {
					chunk = new ResStringPool(header, ResTable.gloabl.tablePackage).parse(srcByte, offset);
					ResTable.gloabl.tablePackage.typeStringPool = (ResStringPool) chunk;
					if (listType) {
						System.out.println(chunk);
					}
				} else if (offset == ResTable.gloabl.tablePackage.keyStrings + ResTable.gloabl.getHeader().headerSize
						+ ResTable.gloabl.stringPool.getHeader().size) {
					chunk = new ResStringPool(header, ResTable.gloabl.tablePackage).parse(srcByte, offset);
					ResTable.gloabl.tablePackage.keyStringPool = (ResStringPool) chunk;
					if (listKey) {
						System.out.println(chunk);
					}
				}
				break;
			case ResourceType.RES_TABLE_TYPE_SPEC_TYPE:
				chunk = new ResTableTypeSpec(header, ResTable.gloabl.tablePackage).parse(srcByte, offset);
				typeSpec = chunk;
				if (listSpec) {
					System.out.println();
					System.out.println(typeSpec);
				}
				break;
			case ResourceType.RES_TABLE_TYPE_TYPE:
				chunk = new ResTableType(header, typeSpec).parse(srcByte, offset);
				if ((((ResTableTypeSpec) typeSpec).specName).equals(specType)) {
					System.out.println(chunk);
				} else if (listSpec) {
					System.out.println(((ResTableType) chunk).resConfig);
				}
				break;
			default:
				chunk = new ResTable(header, ResTable.gloabl).parse(srcByte, offset);
			}
			offset += header.hasChild(srcByte, offset) ? header.headerSize : header.size;
		}

	}
}
