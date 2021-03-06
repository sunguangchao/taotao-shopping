package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	@Autowired
	private ItemService itemService;
	
	/**
	 * 接收cid参数。调用Service查询规格参数模板。返回TaotaoResult。返回json数据。
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}
	
	/**
	 * 接收cid、规格参数模板。创建一TbItemParam对象。
	 * 调用Service返回TaotaoResult。返回json数据。
	 * @param cid
	 * @param paramData
	 * @return
	 */
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		//创建pojo对象
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		TaotaoResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}
	/**
	 * 
	 * @param itemId
	 * @return
	 * 加载商品规格
	 */
	@RequestMapping("/query/item/{itemId}")
	@ResponseBody
	public TaotaoResult queryItemParam(@PathVariable Long itemId){
		TaotaoResult result = itemService.getItemParamById(itemId);
		return result;

	}
}
