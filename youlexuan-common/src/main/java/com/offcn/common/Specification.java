package com.offcn.common;

import java.io.Serializable;
import java.util.List;

import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationOption;

/**
 * Specification里面包含了规格需要的两张表
 * @author luoyanpeng
 *
 */
public class Specification implements Serializable{

	private TbSpecification tbSpecification;
	private List<TbSpecificationOption> sepOptions;
	
	public TbSpecification getTbSpecification() {
		return tbSpecification;
	}
	public void setTbSpecification(TbSpecification tbSpecification) {
		this.tbSpecification = tbSpecification;
	}
	public List<TbSpecificationOption> getSepOptions() {
		return sepOptions;
	}
	public void setSepOptions(List<TbSpecificationOption> sepOptions) {
		this.sepOptions = sepOptions;
	}
}
