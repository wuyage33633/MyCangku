package com.offcn.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.common.Good;
import com.offcn.common.PageResult;
import com.offcn.common.Result;
import com.offcn.pojo.TbGoods;
import com.offcn.service.GoodsService;
/**
 * controller
 * @author Administrator
 *
 */
@RestController  // Controller   +  ResponseBody
@RequestMapping("/goods")
public class GoodsController {


	@Autowired
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbGoods goods){
		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Good good){
		try {
			goodsService.update(good);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Good findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	@RequestMapping("/shenhe")
	public Result shenheProduct(long [] ids){
		try {
			goodsService.shenheProduct(ids);
			return new Result(true, "提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "提交失败");
		}
	}
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		// 获取当前用户，根据用户查询商品
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		goods.setSellerId(name);
		return goodsService.findPage(goods, page, rows);		
	}
	
	@RequestMapping("/addGood")
	public Result addGood(@RequestBody Good good){
		// 获取当前用户
		String seller_id = SecurityContextHolder.getContext().getAuthentication().getName();
		good.getTbGooods().setSellerId(seller_id);
		goodsService.addGood(good);
		return new Result(true, "添加成功");
	}
	
}
