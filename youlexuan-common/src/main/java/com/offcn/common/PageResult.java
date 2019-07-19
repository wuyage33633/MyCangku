package com.offcn.common;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable{
	
	/**
	 * ["total":123,rows[{},{},{}]]
	 */

	private long total;
	
	private List<?> rows;
	
	

	public PageResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
