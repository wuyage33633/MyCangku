package com.offcn.service.impl;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.PageResult;
import com.offcn.common.Result;
import com.offcn.dao.TbBrandMapper;
import com.offcn.dao.TbItemMapper;
import com.offcn.pojo.TbBrand;
import com.offcn.pojo.TbBrandExample;
import com.offcn.pojo.TbBrandExample.Criteria;
import com.offcn.pojo.TbItem;
import com.offcn.pojo.TbItemExample;
import com.offcn.service.BrandService;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbBrand> findAll() {
		return brandMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbBrand> page=   (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public Result add(TbBrand brand) {
		TbBrandExample example = new TbBrandExample();
		Criteria criteria = example.createCriteria();
		criteria.andNameEqualTo(brand.getName());
		List<TbBrand> list = brandMapper.selectByExample(example);
		if(list.size() > 0){
			return new Result(false, "该品牌已经存在");
		}else{
			brandMapper.insert(brand);
			return new Result(true, "添加成功");
		}
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbBrand brand){
		brandMapper.updateByPrimaryKey(brand);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbBrand findOne(Long id){
		return brandMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			brandMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbBrandExample example=new TbBrandExample();
		Criteria criteria = example.createCriteria();
		
		if(brand!=null){			
						if(brand.getName()!=null && brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
			}			if(brand.getFirstChar()!=null && brand.getFirstChar().length()>0){
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}	
		}
		
		Page<TbBrand> page= (Page<TbBrand>)brandMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> findAllBrands() {
			return brandMapper.findAllBrands();
		}

		@Override
		public void importsolrdata() {
			// 查询数据库的tb_item表的所有数据
			List<TbItem> list = itemMapper.selectByExample(new TbItemExample());
			solrTemplate.saveBeans(list);
			solrTemplate.commit();
			
		}
	
}
