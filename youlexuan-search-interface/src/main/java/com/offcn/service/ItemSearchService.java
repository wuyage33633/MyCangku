package com.offcn.service;

import java.util.List;

import com.offcn.pojo.TbItem;

public interface ItemSearchService {

	public List<TbItem> search(String title);
}
