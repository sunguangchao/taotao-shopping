package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	
	EUDataGridResult getItemList(int page, int rows);
	
	TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception;
	
	TaotaoResult updateItem(TbItem item);
	
	TaotaoResult getItemDescById(long itemId);
	
	TaotaoResult getItemParamById(long itemId);
	
	TaotaoResult delItemById(long itemId);
	

}
