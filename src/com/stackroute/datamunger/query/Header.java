package com.stackroute.datamunger.query;

//header class
public class Header {
	String[] header;

	public Header(String[] header) {
		this.header = header;
	}

	/*
	 * this class should contain a member variable which is a String array, to hold
	 * the headers and should override toString() method.
	 */
	public String[] getHeaders() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}
}
