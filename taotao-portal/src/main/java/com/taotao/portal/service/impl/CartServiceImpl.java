package com.taotao.portal.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.utils.HttpClientUtil;
@Service
public class CartServiceImpl implements CartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	/*
	 * /(non-Javadoc)
	 * @see com.taotao.portal.service.CartService#addCartItem(long, int, javax.servlet.http.HttpServletRequest, 
	 * javax.servlet.http.HttpServletResponse)
	 * 基本思路：
	 * 接收一个商品id，数量（默认为1），根据商品id查询商品信息。
	 * 调用taotao-rest的服务。把商品添加到购物车，先把购物车商品列表取出来，判断列表中是否有此商品，如果有就增加数量就可以了。
	 * 如果没有把此商品添加到商品列表。返回添加成功Taotaoresult。
	 */
	@Override
	public TaotaoResult addCartItem(long itemId, int num, 
			HttpServletRequest request, HttpServletResponse response) {
		//取商品信息
		CartItem cartItem = null;
		//取购物车商品列表
		List<CartItem> list = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for(CartItem citem : list) {
			//如果列表中存在此商品
			if (citem.getId() == itemId) {
				//增加商品数量
				citem.setNum(citem.getNum() + num);
				cartItem = citem;//?
				break;
			}
		}
		//如果从cookie中取出的itemlist没有此cartItem
		if (cartItem == null) {
			cartItem = new CartItem();
			//根据商品id查询商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			//把json转换成java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem item = (TbItem) taotaoResult.getData();
				cartItem.setId(item.getId());
				cartItem.setTitle(item.getTitle());
				cartItem.setImage(item.getImage() == null ? "":item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
			}
			list.add(cartItem);
		}
		//将购物车列表重新写回cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), true);	
		return TaotaoResult.ok();
	}

	/* 从cookie中取商品列表
	 * 
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中取商品列表
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cartJson == null) {
			return new ArrayList<>();
		}
		//把json转换成商品列表
		try {
			List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	
	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	/**
	 * 在购物车页面中点击删除连接，接收要删除的商品id，从cookie中把商品找到删除，重新写入cookie，重新展示购物车页面。
	 */
	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		//从cookie中取购物车商品列表
		List<CartItem> itemList = getCartItemList(request);
		//从列表中找到此商品s
		for(CartItem item : itemList) {
			if (item.getId() == itemId) {
				itemList.remove(item);
				break;
			}
		}
		//把购物车列表重新写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		return TaotaoResult.ok();
	}

	/**
	 * 修改购物车数量
	 */
	@Override
	public TaotaoResult updateCartItemNum(long itemId, Integer itemNum, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		//取商品信息
		CartItem cartItem = null;
		//取购物车商品列表
		List<CartItem> list = getCartItemList(request);
		//判断购物车商品列表中是否存在此商品
		for(CartItem citem : list) {
			//如果列表中存在此商品
			if (citem.getId() == itemId) {
				//增加商品数量
				citem.setNum(itemNum);
				cartItem = citem;//?
				break;
			}
		}
		return TaotaoResult.ok();
	}

}
