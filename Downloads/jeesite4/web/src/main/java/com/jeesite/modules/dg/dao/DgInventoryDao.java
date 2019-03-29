/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.dg.entity.DgInventory;

/**
 * 库存管理DAO接口
 * @author eric
 * @version 2019-03-10
 */
@MyBatisDao
public interface DgInventoryDao extends CrudDao<DgInventory> {
	
}