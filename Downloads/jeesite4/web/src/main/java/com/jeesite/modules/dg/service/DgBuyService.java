/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.web.http.ServletUtils;
import com.jeesite.modules.dg.entity.DgBuy;
import com.jeesite.modules.dg.dao.DgBuyDao;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileEntityService;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.file.service.support.FileUploadServiceSupport;
import com.jeesite.modules.file.utils.FileUploadUtils;

import javassist.tools.framedump;

/**
 * dg_buyService
 * @author eric
 * @version 2019-02-26
 */
@Service
@Transactional(readOnly=true)
public class DgBuyService extends CrudService<DgBuyDao, DgBuy> {
	
	
	@Autowired
	private FileEntityService fileEntityService;
	@Autowired
	private FileUploadService fileUploadService;
	
	/**
	 * 获取单条数据
	 * @param dgBuy
	 * @return
	 */
	@Override
	public DgBuy get(DgBuy dgBuy) {
		return super.get(dgBuy);
	}
	
	/**
	 * 查询分页数据
	 * @param dgBuy 查询条件
	 * @param dgBuy.page 分页对象
	 * @return
	 */
	@Override
	public Page<DgBuy> findPage(DgBuy dgBuy) {
		return super.findPage(dgBuy);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param dgBuy
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DgBuy dgBuy) {
		super.save(dgBuy);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(dgBuy.getId(), "dgBuy_image");
		
		
		
		List<FileUpload> list = FileUploadUtils.findFileUpload(dgBuy.getId(), "dgBuy_image");
		StringBuilder sBuilder = new StringBuilder();
		
		if (list != null) {		
			for (FileUpload fileUpload : list) {
				sBuilder.append(fileUpload.getFileEntity().getFilePath() ).append(fileUpload.getFileEntity().getFileId()).append(".").append(fileUpload.getFileEntity().getFileExtension()).append(",");
			}
			String picString = sBuilder.toString();
			if (picString.endsWith(",")) {
				picString = picString.substring(0, picString.length() - 1);
			}
			dgBuy.setPic(sBuilder.toString());
			super.update(dgBuy);
		}
		
//		if (StringUtils.hasLength(bizKeyString)) {
//			List<FileUpload> list = new ArrayList<FileUpload>();
//			String[] bizKeyArray = bizKeyString.split(",");
//			for (int i = 0; i < bizKeyArray.length; i++) {
//				FileUpload fileUpload = new FileUpload();
//				fileUpload.setId(bizKeyArray[i]);
//				FileUpload fileUpload2 = fileUploadService.get(fileUpload);
//				if (fileUpload2 != null) {
//					System.out.println(fileUpload2.getFileUrl());
//				}
//			}
//		}
	}
	
	/**
	 * 更新状态
	 * @param dgBuy
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DgBuy dgBuy) {
		super.updateStatus(dgBuy);
	}
	
	/**
	 * 删除数据
	 * @param dgBuy
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DgBuy dgBuy) {
		super.delete(dgBuy);
	}
	
}