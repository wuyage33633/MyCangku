package com.offcn.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.PageResult;
import com.offcn.dao.TbItemCatMapper;
import com.offcn.pojo.TbItemCat;
import com.offcn.pojo.TbItemCatExample;
import com.offcn.pojo.TbItemCatExample.Criteria;
import com.offcn.service.ItemCatService;

/**
 * 商品类目服务实现层
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper item_catMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbItemCat> findAll() {
		return item_catMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbItemCat> page=   (Page<TbItemCat>) item_catMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbItemCat item_cat) {
		item_catMapper.insert(item_cat);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbItemCat item_cat){
		item_catMapper.updateByPrimaryKey(item_cat);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbItemCat findOne(Long id){
		return item_catMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
//	@Override
//	public void delete(Long[] ids) {
//		for(Long id:ids){
//			item_catMapper.deleteByPrimaryKey(id);
//		}		
//	}
	
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			List<Long> ids2 = item_catMapper.selectIdsById(id);
			TbItemCatExample example = new TbItemCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(ids2);
			item_catMapper.deleteByExample(example);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbItemCat item_cat, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbItemCatExample example=new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		if(item_cat!=null){			
						if(item_cat.getName()!=null && item_cat.getName().length()>0){
				criteria.andNameLike("%"+item_cat.getName()+"%");
			}	
		}
		
		Page<TbItemCat> page= (Page<TbItemCat>)item_catMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<TbItemCat> findItemCatByParentId(long parentId) {
			TbItemCatExample example = new TbItemCatExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parentId);
			List<TbItemCat> list = item_catMapper.selectByExample(example);
				if(list.size() > 0){
					return list;
				}else{
					return null;
				}
		 }

		@Override
		public List<Map> selectOptionList() {
			List<Map> list = item_catMapper.selectOptionList();
			return list;
		}
	
}
