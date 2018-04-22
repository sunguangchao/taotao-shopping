package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
/**
 * 图片上传服务
 * @author 11981
 *
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	//从web的resource文件夹下读取相关配置信息-resource.properties
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;

	@Override
	public Map uploadPicture(MultipartFile uploadFile){
		// TODO Auto-generated method stub
		Map resultMap = new HashMap<>();
		try {
			//生成一个新文件名
			//取原始文件名
			String oldName = uploadFile.getOriginalFilename();
//			UUID.randomUUID();
			String newName = IDUtils.getImageName();
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			//新文件名
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			//转存文件，上传到ftp服务器
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH, 
					imagePath, newName, uploadFile.getInputStream());
			//返回结果
			if (!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
			
		} catch (Exception e) {
			// TODO: handle exception
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}
}
