package org.yuesi.databridge.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Data;

@Data
@DynamicInsert
@Entity
@Table(name = "tb_stockbasics", catalog = "market")
public class StockBasics {

	@Id
	@Column(name = "code")
	private String code; // 代码
	@Column(name = "name")
	private String name; // 名称
	@Column(name = "industry")
	private String industry; // 细分行业
	@Column(name = "area")
	private String area; // 地区
	@Column(name = "tradedate", nullable = true)
	private Date timeToMarket; // 上市日期

	@Override
	public String toString() {
		return "StockBasics{" + "code=" + code + ", name=" + name + ", industry=" + industry + ", area=" + area + "}";
	}
}
