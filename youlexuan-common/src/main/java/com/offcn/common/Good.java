package com.offcn.common;

import java.io.Serializable;

import com.offcn.pojo.TbGoods;
import com.offcn.pojo.TbGoodsDesc;

public class Good implements Serializable{

	private long id;
	private TbGoods tbGooods;
	private TbGoodsDesc tbGoodsDesc;
	public TbGoods getTbGooods() {
		return tbGooods;
	}
	public void setTbGooods(TbGoods tbGooods) {
		this.tbGooods = tbGooods;
	}
	public TbGoodsDesc getTbGoodsDesc() {
		return tbGoodsDesc;
	}
	public void setTbGoodsDesc(TbGoodsDesc tbGoodsDesc) {
		this.tbGoodsDesc = tbGoodsDesc;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
