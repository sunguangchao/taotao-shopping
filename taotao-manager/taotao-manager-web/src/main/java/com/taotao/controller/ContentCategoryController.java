package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * jsp页面：content-category
 * @author 11981
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService categoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") Long parentId){
		List<EUTreeNode> list = categoryService.getCategoryList(parentId);
		return list;
	}

	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result =  categoryService.insertContentCategory(parentId, name);
		return result;
	}
	/**
	 * @param parentId-没有送过来
	 * @param id
	 * @return
	 * parentId:node.parentId,
	 * 这里我修改了下：
	 * 页面中node.parentId为null
	 * 所以把方法参数中的parentId去掉了
	 * 注意：
	 * 这里id要和js中的id对应，否则报空指针异常
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id) {
		TaotaoResult result = categoryService.deleteContentCategory(id);
		return result;
	}
	
	/**
	 * 目前前端的页面是有问题的
	 * 根据nodeId 修改节点名称
	 * @param id
	 * @param text
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String text) {
		TaotaoResult result = categoryService.updateContentCategory(id, text);
		return result;
	}
}
