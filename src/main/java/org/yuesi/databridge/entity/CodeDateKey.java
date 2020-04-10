package org.yuesi.databridge.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CodeDateKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3011087291369227690L;

	@Column(name="code", columnDefinition = "varchar(12)")
	private String code;

	@Column(name="tradedate", columnDefinition = "date")
	private Date tradeDate;
	// 省略setter,getter方法

	@Override
	public String toString() {
		return "CodeDateKey [code=" + code + ", tradedate=" + tradeDate + "]";
	}
}
