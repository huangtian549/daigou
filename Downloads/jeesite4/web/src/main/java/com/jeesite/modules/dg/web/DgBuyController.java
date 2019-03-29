/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tools.ant.util.DateUtils;
import org.jsoup.helper.StringUtil;
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
import com.jeesite.modules.dg.entity.DgBuy;
import com.jeesite.modules.dg.service.DgBuyService;

/**
 * dg_buyController
 * @author eric
 * @version 2019-02-26
 */
@Controller
@RequestMapping(value = "${adminPath}/dg/dgBuy")
public class DgBuyController extends BaseController {

	@Autowired
	private DgBuyService dgBuyService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DgBuy get(String ids, boolean isNewRecord) {
		return dgBuyService.get(ids, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("dg:dgBuy:view")
	@RequestMapping(value = {"list", ""})
	public String list(DgBuy dgBuy, Model model) {
		model.addAttribute("dgBuy", dgBuy);
		return "modules/dg/dgBuyList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("dg:dgBuy:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<DgBuy> listData(DgBuy dgBuy, HttpServletRequest request, HttpServletResponse response) {
		dgBuy.setPage(new Page<>(request, response));
		Page<DgBuy> page = dgBuyService.findPage(dgBuy);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("dg:dgBuy:view")
	@RequestMapping(value = "form")
	public String form(DgBuy dgBuy, Model model) {
		model.addAttribute("dgBuy", dgBuy);
		return "modules/dg/dgBuyForm";
	}

	/**
	 * 保存dg_buy
	 */
	@RequiresPermissions("dg:dgBuy:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated DgBuy dgBuy) {
		if(StringUtil.isBlank(dgBuy.getIds())) {
			dgBuy.setPurchasedate(new java.sql.Date(new java.util.Date().getTime()));
		}
		dgBuyService.save(dgBuy);
		return renderResult(Global.TRUE, text("保存购买记录成功！"));
	}
	
	/**
	 * 删除dg_buy
	 */
	@RequiresPermissions("dg:dgBuy:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DgBuy dgBuy) {
		dgBuyService.delete(dgBuy);
		return renderResult(Global.TRUE, text("删除购买记录成功！"));
	}
	
}