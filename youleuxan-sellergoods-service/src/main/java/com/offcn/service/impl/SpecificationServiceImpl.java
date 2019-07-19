package com.offcn.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.Op.Create;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.common.PageResult;
import com.offcn.common.Specification;
import com.offcn.dao.TbSpecificationMapper;
import com.offcn.dao.TbSpecificationOptionMapper;
import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationExample;
import com.offcn.pojo.TbSpecificationExample.Criteria;
import com.offcn.pojo.TbSpecificationOption;
import com.offcn.pojo.TbSpecificationOptionExample;
import com.offcn.service.SpecificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper speoptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Specification specification) {
		// 添加规格数据
		specificationMapper.insert(specification.getTbSpecification());	
		
		// 添加规格参数数据
		List<TbSpecificationOption> list = specification.getSepOptions();
		for(TbSpecificationOption option : list){
			// 还要添加id
			Long id = specification.getTbSpecification().getId();
			option.setSpecId(id);
			speoptionMapper.insert(option);
		}
		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Specification specification){
		specificationMapper.updateByPrimaryKey(specification.getTbSpecification());
		List<TbSpecificationOption> list = specification.getSepOptions();
		for(TbSpecificationOption opt : list){
			speoptionMapper.updateByPrimaryKeySelective(opt);
		}
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		 TbSpecification tbspec = specificationMapper.selectByPrimaryKey(id);
		 TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		 com.offcn.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		 criteria.andSpecIdEqualTo(id);
		 List<TbSpecificationOption> list = speoptionMapper.selectByExample(example);
		 
		 Specification s = new Specification();
		 s.setSepOptions(list);
		 s.setTbSpecification(tbspec);
		 return s;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			// 删除规格表的数据
			specificationMapper.deleteByPrimaryKey(id);
			// 再删除规格项表数据
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.offcn.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			speoptionMapper.deleteByExample(example);
		}		
	}
	
	
	// 模糊查询
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null ){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public List<Map> findAllSpec() {
			return specificationMapper.findaAllSpec();
		}
	
}
