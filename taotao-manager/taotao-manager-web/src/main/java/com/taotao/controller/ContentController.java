package com.taotao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
/**
 * 内容管理Controller
 * @author 11981
 * 对应的jsp页面：content.jsp
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	private static Logger LOGGER = LoggerFactory.getLogger(ContentController.class);
	@Autowired
	private ContentService contentService;
	
	/**
	 * 新增内容
	 * @param content
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result =  contentService.insertContent(content);
		return result;
	}
	/**
	 * 获取内容列表-ok
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(Integer page, Integer rows) {
		EUDataGridResult result = contentService.getContentList(page, rows);
		return result;
	}
	
	/**
	 * 删除内容-ok
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContent(Long ids) {
		LOGGER.info("ids:" + ids);
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
	}
	@RequestMapping("/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content) {
		TaotaoResult result = contentService.updateContent(content);
		return result;
	}
	
}
