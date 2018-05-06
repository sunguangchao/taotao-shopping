package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;
@Service
public class ContentServiceImpl implements ContentService{

	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		// 补全POJO内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return TaotaoResult.ok();
	}

	@Override
	public EUDataGridResult getContentList(int page, int rows) {
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		PageHelper.startPage(page, rows);
		List<TbContent> lists = contentMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(lists);
		//获取记录总数
		PageInfo<TbContent> pageInfo = new PageInfo<>(lists);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult deleteContent(long ids) {
		// TODO Auto-generated method stub
		contentMapper.deleteByPrimaryKey(ids);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent content) {
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKey(content);
		// TODO Auto-generated method stub
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return TaotaoResult.ok();
	}
	

}
