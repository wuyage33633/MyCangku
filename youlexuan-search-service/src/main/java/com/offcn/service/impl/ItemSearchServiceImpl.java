package com.offcn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.offcn.pojo.TbItem;
import com.offcn.service.ItemSearchService;

@Service
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;
	
	
	@Override
	public List<TbItem> search(String title) {
//		Query query = new SimpleQuery();
//		Criteria criteria = new Criteria("title").is(title);
//		query.addCriteria(criteria);
//		ScoredPage<TbItem> res = solrTemplate.queryForPage(query, TbItem.class);
//		List<TbItem> list = res.getContent();
		
		
		HighlightQuery query = new SimpleHighlightQuery();

		// 加入需要显示的高亮字段
		HighlightOptions highlightOptions = new HighlightOptions().addField("title");
		// 设置高亮前缀
		highlightOptions.setSimplePrefix("<em style='color:red'>");
		// 设置高亮后缀
		highlightOptions.setSimplePostfix("</em>");
		// 设置高亮
		query.setHighlightOptions(highlightOptions);
		
		// 根据条件查询数据
		Criteria criteria = new Criteria("title").is(title);
		query.addCriteria(criteria);
		
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
		for(HighlightEntry<TbItem> h : page.getHighlighted()){
			// 获取item对象
			TbItem tbItem = h.getEntity();
			if(h.getHighlights().size() > 0 && h.getHighlights().get(0).getSnipplets().size() > 0){
				// 设置高亮属性
				tbItem.setTitle(h.getHighlights().get(0).getSnipplets().get(0));
			}
		}
		List<TbItem> list = page.getContent();
		return list;
	}

}
