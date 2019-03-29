/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.dg.entity.DgInventory;
import com.jeesite.modules.dg.dao.DgInventoryDao;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 库存管理Service
 * @author eric
 * @version 2019-03-10
 */
@Service
@Transactional(readOnly=true)
public class DgInventoryService extends CrudService<DgInventoryDao, DgInventory> {
	
	/**
	 * 获取单条数据
	 * @param dgInventory
	 * @return
	 */
	@Override
	public DgInventory get(DgInventory dgInventory) {
		return super.get(dgInventory);
	}
	
	/**
	 * 查询分页数据
	 * @param dgInventory 查询条件
	 * @param dgInventory.page 分页对象
	 * @return
	 */
	@Override
	public Page<DgInventory> findPage(DgInventory dgInventory) {
		return super.findPage(dgInventory);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param dgInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DgInventory dgInventory) {
		super.save(dgInventory);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(dgInventory.getId(), "dgInventory_image");
		
		List<FileUpload> list = FileUploadUtils.findFileUpload(dgInventory.getId(), "dgInventory_image");
		StringBuilder sBuilder = new StringBuilder();
		
		if (list != null) {		
			for (FileUpload fileUpload : list) {
				sBuilder.append(fileUpload.getFileEntity().getFilePath() ).append(fileUpload.getFileEntity().getFileId()).append(".").append(fileUpload.getFileEntity().getFileExtension()).append(",");
			}
			String picString = sBuilder.toString();
			if (picString.endsWith(",")) {
				picString = picString.substring(0, picString.length() - 1);
			}
			dgInventory.setPic(sBuilder.toString());
			super.update(dgInventory);
		}
	}
	
	/**
	 * 更新状态
	 * @param dgInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DgInventory dgInventory) {
		super.updateStatus(dgInventory);
	}
	
	/**
	 * 删除数据
	 * @param dgInventory
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DgInventory dgInventory) {
		super.delete(dgInventory);
	}
	
}