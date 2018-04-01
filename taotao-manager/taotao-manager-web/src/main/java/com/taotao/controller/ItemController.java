package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;


/**
 * 商品管理Controller
 * @author 11981
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
//		System.out.println(item);
		return item;
		
	}
	/**
	 * 获取商品列表
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	/**
	 * 新增商品
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/item/save", method=RequestMethod.POST)
	@ResponseBody
	private TaotaoResult createItem(TbItem item,String desc, String itemParams) throws Exception{
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		return result;
	}
	
	/**
	 * 修改商品
	 * @param item
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	@RequestMapping(value="/item/update", method=RequestMethod.POST)
	private TaotaoResult updateItem(TbItem item, String desc, String itemParams) {
		TaotaoResult result = itemService.updateItem(item);
		return result;
	}
	@RequestMapping(value="/item/query/item/desc/{itemId}", method=RequestMethod.GET)
	@ResponseBody
	private TaotaoResult queryItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDescById(itemId);
		return result;
	}
	
	@RequestMapping("/item/delete/{itemId}")
	@ResponseBody
	private TaotaoResult deleteItem(@PathVariable Long itemId) {
		TaotaoResult result = itemService.delItemById(itemId);
		return result;
	}

	
}
