/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.dg.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * dg_buyEntity
 * @author eric
 * @version 2019-02-26
 */
@Table(name="dg_buy", alias="a", columns={
		@Column(name="ids", attrName="ids", label="ids", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="wechat", attrName="wechat", label="wechat"),
		@Column(name="address", attrName="address", label="address"),
		@Column(name="pricecn", attrName="pricecn", label="pricecn"),
		@Column(name="description", attrName="description", label="description"),
		@Column(name="pic", attrName="pic", label="pic"),
		@Column(name="purchasedate", attrName="purchasedate", label="purchasedate"),
		@Column(name="ispay", attrName="ispay", label="ispay"),
		@Column(name="isbuy", attrName="isbuy", label="isbuy"),
		@Column(name="issend", attrName="issend", label="issend"),
		@Column(name="client", attrName="client", label="国内接收点，拼邮到国内后，再分别邮寄给客户"),
		@Column(name="deliverfee", attrName="deliverfee", label="deliverfee"),
		@Column(name="iscontainfee", attrName="iscontainfee", label="iscontainfee"),
		@Column(name="deliverdate", attrName="deliverdate", label="deliverdate"),
		@Column(name="ispaydeliverfee", attrName="ispaydeliverfee", label="ispaydeliverfee"),
		@Column(name="version", attrName="version", label="version"),
		@Column(name="delivermethod", attrName="delivermethod", label="delivermethod"),
		@Column(name="tracknum", attrName="tracknum", label="tracknum"),
		@Column(name="remark1", attrName="remark1", label="remark1"),
		@Column(name="remark2", attrName="remark2", label="remark2"),
		@Column(name="userid", attrName="userid", label="userid"),
	}, orderBy="a.ids DESC"
)
public class DgBuy extends DataEntity<DgBuy> {
	
	private static final long serialVersionUID = 1L;
	private String ids;		// ids
	private String name;		// name
	private String wechat;		// wechat
	private String address;		// address
	private Double pricecn;		// pricecn
	private String description;		// description
	private String pic;		// pic
	private Date purchasedate;		// purchasedate
	private Long ispay;		// ispay
	private Integer isbuy;		// isbuy
	private Long issend;		// issend
	private String client;		// 国内接收点，拼邮到国内后，再分别邮寄给客户
	private Double deliverfee;		// deliverfee
	private Long iscontainfee;		// iscontainfee
	private Date deliverdate;		// deliverdate
	private Long ispaydeliverfee;		// ispaydeliverfee
	private Long version;		// version
	private String delivermethod;		// delivermethod
	private String tracknum;		// tracknum
	private String remark1;		// remark1
	private String remark2;		// remark2
	private String userid;		// userid
	private String startDate;
	private String endDate;
	
	public DgBuy() {
		this(null);
	}

	public DgBuy(String id){
		super(id);
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Length(min=0, max=32, message="name长度不能超过 32 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="wechat长度不能超过 32 个字符")
	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	
	@Length(min=0, max=200, message="address长度不能超过 200 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getPricecn() {
		return pricecn;
	}

	public void setPricecn(Double pricecn) {
		this.pricecn = pricecn;
	}
	
	@Length(min=0, max=200, message="description长度不能超过 200 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=200, message="pic长度不能超过 200 个字符")
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(Date purchasedate) {
		this.purchasedate = purchasedate;
	}
	
	public Long getIspay() {
		return ispay;
	}

	public void setIspay(Long ispay) {
		this.ispay = ispay;
	}
	
	public Integer getIsbuy() {
		return isbuy;
	}

	public void setIsbuy(Integer isbuy) {
		this.isbuy = isbuy;
	}
	
	public Long getIssend() {
		return issend;
	}

	public void setIssend(Long issend) {
		this.issend = issend;
	}
	
	@Length(min=0, max=20, message="国内接收点，拼邮到国内后，再分别邮寄给客户长度不能超过 20 个字符")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public Double getDeliverfee() {
		return deliverfee;
	}

	public void setDeliverfee(Double deliverfee) {
		this.deliverfee = deliverfee;
	}
	
	public Long getIscontainfee() {
		return iscontainfee;
	}

	public void setIscontainfee(Long iscontainfee) {
		this.iscontainfee = iscontainfee;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeliverdate() {
		return deliverdate;
	}

	public void setDeliverdate(Date deliverdate) {
		this.deliverdate = deliverdate;
	}
	
	public Long getIspaydeliverfee() {
		return ispaydeliverfee;
	}

	public void setIspaydeliverfee(Long ispaydeliverfee) {
		this.ispaydeliverfee = ispaydeliverfee;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Length(min=0, max=100, message="delivermethod长度不能超过 100 个字符")
	public String getDelivermethod() {
		return delivermethod;
	}

	public void setDelivermethod(String delivermethod) {
		this.delivermethod = delivermethod;
	}
	
	@Length(min=0, max=100, message="tracknum长度不能超过 100 个字符")
	public String getTracknum() {
		return tracknum;
	}

	public void setTracknum(String tracknum) {
		this.tracknum = tracknum;
	}
	
	@Length(min=0, max=255, message="remark1长度不能超过 255 个字符")
	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
	@Length(min=0, max=255, message="remark2长度不能超过 255 个字符")
	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	@Length(min=0, max=32, message="userid长度不能超过 32 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}