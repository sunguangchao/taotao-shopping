package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	private static Logger LOGGER = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for(TbContentCategory content : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(content.getId());
			node.setText(content.getName());
			node.setState(content.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}
		return resultList;
	}

	/**
	 * 新增子节点
	 */
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		// 添加一个新节点，创建一个节点对象
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true,如果不是true改为true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok(contentCategory);
	}

	/**
	 * 传入parentId, nodeid
	 */
	@Override
	public TaotaoResult deleteContentCategory(long nodeId) {
		
		//根据nodeId查询出parentId
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(nodeId);
		Long parentIdM = category.getParentId();
		LOGGER.info("parentId:" + parentIdM);
		//根据nodeId删除节点信息
		contentCategoryMapper.deleteByPrimaryKey(nodeId);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentIdM);
		//查询该parentId下是否还有子目录
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			//如果没有，则将parentId对应的nodeId的isParent字段置为false
			TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentIdM);
			parentCat.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		return TaotaoResult.ok();
	}
	
	public TaotaoResult updateContentCategory(long id, String text) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		if (category != null) {
			category.setName(text);
			contentCategoryMapper.updateByPrimaryKey(category);
		}
		return TaotaoResult.ok();
		
	}

}
