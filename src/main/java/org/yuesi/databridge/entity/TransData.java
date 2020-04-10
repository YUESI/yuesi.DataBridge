package org.yuesi.databridge.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.Data;

@Data
@DynamicInsert
@Entity
@IdClass(CodeDateKey.class)
@Table(name="tb_transdata", catalog="market")
public class TransData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552859018672303710L;

	@Id
	@Column(name="code", columnDefinition = "vachar(12)")
	private String code; //代码
	
	@Id
	@Column(name="tradedate", columnDefinition = "date")
	private Date tradeDate; // 交易日

	@Column(name = "open", columnDefinition = "decimal(19,4)")
	private BigDecimal open;

	@Column(name = "high", columnDefinition = "decimal(19,4)")
	private BigDecimal high;

	@Column(name = "low", columnDefinition = "decimal(19,4)")
	private BigDecimal low;

	@Column(name = "close", columnDefinition = "decimal(19,4)")
	private BigDecimal close;

	@Column(name = "volume", columnDefinition = "decimal(19,4)")
	private BigDecimal volume;

	@Column(name = "amount", columnDefinition = "decimal(19,4)")
	private BigDecimal amount;

	@Column(name = "`change`", columnDefinition = "decimal(19,4)")
    private BigDecimal change;

	@Column(name = "pChange", columnDefinition = "decimal(19,4)")
    private BigDecimal pChange;
	
}
