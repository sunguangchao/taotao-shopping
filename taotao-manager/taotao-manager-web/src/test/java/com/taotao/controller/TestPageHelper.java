package com.taotao.controller;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
/**
 * 分页插件测试
 * @author 11981
 *
 */
public class TestPageHelper {
//	@Test
//	public void testPageHelper() {
//		//创建一个spring容器
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
//		//从spring容器中获得Mapper的代理对象
//		TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
//		TbItemExample example = new TbItemExample();
//		PageHelper.startPage(1, 10);
//		//执行查询并开始分页
//		List<TbItem> list = mapper.selectByExample(example);
//		//取商品列表
//		for(TbItem item : list) {
//			System.out.println(item.getTitle());
//		}
//		//取分页信息
//		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
//		long total = pageInfo.getTotal();
//		System.out.println("共有商品：" + total);
//	}
}
