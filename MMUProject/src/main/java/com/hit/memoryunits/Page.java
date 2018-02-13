package com.hit.memoryunits;

public class Page<T> extends java.lang.Object implements java.io.Serializable {
	private java.lang.Long Id;
	private T content;

	public Page(java.lang.Long id, T content) {
		this.content = content;
		this.Id = id;
	}

	// getPageId
	public java.lang.Long getPageId() {
		return this.Id;

	}

	// setPageId
	public void setPageId(java.lang.Long pageId) {
		this.Id = pageId;
	}
	// getContent

	public T getContent() {
		return this.content;
	}
	// setContent

	public void setContent(T content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Id.hashCode();
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		return this.hashCode() == obj.hashCode();

	}

	// toString
	public java.lang.String toString() {
		return Id.toString();
	}

}
