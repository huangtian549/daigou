/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 库存管理Entity
 * @author eric
 * @version 2019-03-10
 */
@Table(name="dg_inventory", alias="a", columns={
		@Column(name="ids", attrName="ids", label="ids", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="description", attrName="description", label="description", queryType=QueryType.LIKE),
		@Column(name="count_all", attrName="countAll", label="count_all"),
		@Column(name="count", attrName="count", label="count"),
		@Column(name="daigou_price", attrName="daigouPrice", label="daigou_price"),
		@Column(name="daili_price", attrName="dailiPrice", label="daili_price"),
		@Column(name="customer_price", attrName="customerPrice", label="customer_price"),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="pic", attrName="pic", label="pic"),
		@Column(name="category", attrName="category", label="category"),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="status", attrName="status", label="status", isUpdate=false),
	}, orderBy="a.ids DESC"
)
public class DgInventory extends DataEntity<DgInventory> {
	
	private static final long serialVersionUID = 1L;
	private Integer ids;		// ids
	private String name;		// name
	private String description;		// description
	private Integer countAll;		// count_all
	private Integer count;		// count
	private Integer daigouPrice;		// daigou_price
	private Integer dailiPrice;		// daili_price
	private Integer customerPrice;		// customer_price
	private String userid;		// userid
	private String pic;		// pic
	private Integer category;		// category
	private Integer type;		// type
	
	public DgInventory() {
		this(null);
	}

	public DgInventory(String id){
		super(id);
	}
	
	public Integer getIds() {
		return ids;
	}

	public void setIds(Integer ids) {
		this.ids = ids;
	}
	
	@Length(min=0, max=50, message="name长度不能超过 50 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="description长度不能超过 500 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getCountAll() {
		return countAll;
	}

	public void setCountAll(Integer countAll) {
		this.countAll = countAll;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Integer getDaigouPrice() {
		return daigouPrice;
	}

	public void setDaigouPrice(Integer daigouPrice) {
		this.daigouPrice = daigouPrice;
	}
	
	public Integer getDailiPrice() {
		return dailiPrice;
	}

	public void setDailiPrice(Integer dailiPrice) {
		this.dailiPrice = dailiPrice;
	}
	
	public Integer getCustomerPrice() {
		return customerPrice;
	}

	public void setCustomerPrice(Integer customerPrice) {
		this.customerPrice = customerPrice;
	}
	
	@Length(min=0, max=50, message="userid长度不能超过 50 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=200, message="pic长度不能超过 200 个字符")
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}