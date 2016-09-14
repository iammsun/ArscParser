package com.iammsun.arsc.info;

/**
 * create by sunmeng on 9/15/16
 */
public abstract class ResChunk implements Parseable{

	protected final ResChunkHeader header;
	protected final ResChunk parent;

	public ResChunk(ResChunkHeader header, ResChunk parent) {
		super();
		this.header = header;
		this.parent = parent;
	}

	public ResChunkHeader getHeader() {
		return header;
	}

	public ResChunk getParent() {
		return parent;
	}

	public ResTable getResTable() {
		if (parent == null && !(this instanceof ResTable)) {
			throw new RuntimeException("no parent found");
		}
		if (parent == null) {
			return ((ResTable) this);
		}
		ResChunk parentChunk = parent;
		while (!(parentChunk instanceof ResTable)) {
			parentChunk = parentChunk.parent;
		}
		return ((ResTable) parentChunk);
	}
}
