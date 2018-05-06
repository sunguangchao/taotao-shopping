package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/**
	 * 接收页面传递过来的商品id，调用Service查询商品基本信息。
	 * 传递给jsp页面。返回逻辑视图，展示商品详情页面。
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("/item/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ItemInfo item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		return "item";
	}
	
	@RequestMapping(value="/item/desc/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId) {
		String result = itemService.getItemDescById(itemId);
		return result;
	}
	
	/**
	 * 页面的ajax请求Controller，请求的url://item/param/{itemId}.html
	 * 响应一个字符串。规格参数的片段。
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String result = itemService.getItemParam(itemId);
		return result;
	}
	
	

}
