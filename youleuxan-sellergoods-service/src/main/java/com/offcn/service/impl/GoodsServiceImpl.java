package com.offcn.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.Good;
import com.offcn.common.PageResult;
import com.offcn.common.Result;
import com.offcn.dao.TbGoodsDescMapper;
import com.offcn.dao.TbGoodsMapper;
import com.offcn.dao.TbItemCatMapper;
import com.offcn.pojo.TbGoods;
import com.offcn.pojo.TbGoodsDesc;
import com.offcn.pojo.TbGoodsExample;
import com.offcn.pojo.TbItemExample;
import com.offcn.pojo.TbGoodsExample.Criteria;
import com.offcn.pojo.TbItemCat;
import com.offcn.pojo.TbItemCatExample;
import com.offcn.service.GoodsService;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbGoods goods) {
		goodsMapper.insert(goods);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Good good){
		TbGoods goods = good.getTbGooods();
		goodsMapper.updateByPrimaryKey(goods);
		TbGoodsDesc goodsDesc = good.getTbGoodsDesc();
		goodsDescMapper.updateByPrimaryKey(goodsDesc);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Good findOne(Long id){
		Good good = new Good();
		// 查good表，然后回显，修改
		TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		good.setTbGooods(tbGoods);
		TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
		good.setTbGoodsDesc(tbGoodsDesc);
		return  good;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			TbGoods goods = new TbGoods();
			goods.setId(id);
			// 0  没有删除    1是删除的
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKeySelective(goods);
		}		
	}
	
	// 修改审批以后数据的状态
	@Override
	public void produceShenPi(Long[] ids, String status) {
		for(Long id:ids){
			TbGoods goods = new TbGoods();
			goods.setId(id);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKeySelective(goods);
		}	
		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andSellerIdEqualTo(goods.getSellerId());
		// 查询过滤，是没有删除的商品
		criteria.andIsDeleteEqualTo("0");
		
		if(goods!=null){			
						if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);
		List<TbGoods> list = page.getResult();
		for(TbGoods good : list){
			List<Long> ids = new ArrayList<Long>();
			Long category1Id = good.getCategory1Id();
			Long category2Id = good.getCategory2Id();
			Long category3Id = good.getCategory3Id();
			ids.add(category1Id);
			ids.add(category2Id);
			ids.add(category3Id);
			TbItemCatExample example2 = new TbItemCatExample();
			com.offcn.pojo.TbItemCatExample.Criteria createCriteria = example2.createCriteria();
			createCriteria.andIdIn(ids);
			List<TbItemCat> selectByExample = itemCatMapper.selectByExample(example2);
			good.setItemCatName1(selectByExample.get(0).getName());
			good.setItemCatName2(selectByExample.get(1).getName());
			good.setItemCatName3(selectByExample.get(2).getName());
		}
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public void addGood(Good good) {
			// 商品添加的时候默认状态是0  没有审核
			good.getTbGooods().setAuditStatus("0");
			// 默认添加的商品是没有删除的  0 是没有删除   1 是删除
			good.getTbGooods().setIsDelete("0");
			// 获取当前添加goods商品的id
			goodsMapper.insert(good.getTbGooods());
			Long id = good.getTbGooods().getId();
			// 把商品的id设置给商品描述的goodsid
			good.getTbGoodsDesc().setGoodsId(id);
			goodsDescMapper.insert(good.getTbGoodsDesc());
		}

		@Override
		public void shenheProduct(long[] ids) {
			for(Long id:ids){
				TbGoods goods = new TbGoods();
				goods.setId(id);
				// 0  没有删除    1是删除的
				goods.setAuditStatus("1");
				goodsMapper.updateByPrimaryKeySelective(goods);
			}		
			
		}
		
		
		// 查询待审的商品，准备审核
		@Override
		public PageResult searchPage(TbGoods goods, int pageNum, int pageSize) {
			PageHelper.startPage(pageNum, pageSize);
			
			TbGoodsExample example=new TbGoodsExample();
			Criteria criteria = example.createCriteria();
			// 查询没有删除的数据   0 没删    1 删除
			criteria.andIsDeleteEqualTo("0");
			
			// 设置查询的状态（未申请状态）
			criteria.andAuditStatusNotEqualTo("0");
			
			
			if(goods!=null){			
							if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
					criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
				}			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
					criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
				}			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
					criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
				}			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
					criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
				}			if(goods.getCaption()!=null && goods.getCaption().length()>0){
					criteria.andCaptionLike("%"+goods.getCaption()+"%");
				}			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
					criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
				}			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
					criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
				}			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
					criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
				}	
			}
			Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);
			return  new PageResult(page.getTotal(), page.getResult());
		}

}
