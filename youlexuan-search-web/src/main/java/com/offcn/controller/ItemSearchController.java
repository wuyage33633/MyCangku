package com.offcn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.offcn.pojo.TbItem;
import com.offcn.service.ItemSearchService;

@RestController
@RequestMapping("/itemsearch")
public class ItemSearchController {

	@Autowired
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public List<TbItem> search(String keys){
		List<TbItem> list = itemSearchService.search(keys);
		return list;
	}
}
