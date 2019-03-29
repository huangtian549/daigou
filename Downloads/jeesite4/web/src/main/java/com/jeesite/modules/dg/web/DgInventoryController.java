/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.dg.entity.DgInventory;
import com.jeesite.modules.dg.service.DgInventoryService;

/**
 * 库存管理Controller
 * @author eric
 * @version 2019-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/dg/dgInventory")
public class DgInventoryController extends BaseController {

	@Autowired
	private DgInventoryService dgInventoryService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DgInventory get(String ids, boolean isNewRecord) {
		return dgInventoryService.get(ids, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("dg:dgInventory:view")
	@RequestMapping(value = {"list", ""})
	public String list(DgInventory dgInventory, Model model) {
		model.addAttribute("dgInventory", dgInventory);
		return "modules/dg/dgInventoryList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("dg:dgInventory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DgInventory> listData(DgInventory dgInventory, HttpServletRequest request, HttpServletResponse response) {
		dgInventory.setPage(new Page<>(request, response));
		if (dgInventory.getStatus() == null || dgInventory.getStatus().length() < 1) {
			dgInventory.setStatus("0");
		}
		Page<DgInventory> page = dgInventoryService.findPage(dgInventory);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("dg:dgInventory:view")
	@RequestMapping(value = "form")
	public String form(DgInventory dgInventory, Model model) {
		model.addAttribute("dgInventory", dgInventory);
		return "modules/dg/dgInventoryForm";
	}

	/**
	 * 保存dg_inventory
	 */
	@RequiresPermissions("dg:dgInventory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DgInventory dgInventory) {
		dgInventoryService.save(dgInventory);
		return renderResult(Global.TRUE, text("保存dg_inventory成功！"));
	}
	
	/**
	 * 保存dg_inventory
	 */
	@RequiresPermissions("dg:dgInventory:edit")
	@PostMapping(value = "updateCount")
	@ResponseBody
	public String updateCount(@Validated DgInventory dgInventory) {
		dgInventoryService.update(dgInventory);
		return renderResult(Global.TRUE, text("保存dg_inventory成功！"));
	}
	
	/**
	 * 停用dg_inventory
	 */
	@RequiresPermissions("dg:dgInventory:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(DgInventory dgInventory) {
		dgInventory.setStatus(DgInventory.STATUS_DISABLE);
		dgInventoryService.updateStatus(dgInventory);
		return renderResult(Global.TRUE, text("停用dg_inventory成功"));
	}
	
	/**
	 * 启用dg_inventory
	 */
	@RequiresPermissions("dg:dgInventory:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(DgInventory dgInventory) {
		dgInventory.setStatus(DgInventory.STATUS_NORMAL);
		dgInventoryService.updateStatus(dgInventory);
		return renderResult(Global.TRUE, text("启用dg_inventory成功"));
	}
	
	/**
	 * 删除dg_inventory
	 */
	@RequiresPermissions("dg:dgInventory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DgInventory dgInventory) {
		dgInventoryService.delete(dgInventory);
		return renderResult(Global.TRUE, text("删除dg_inventory成功！"));
	}
	
}