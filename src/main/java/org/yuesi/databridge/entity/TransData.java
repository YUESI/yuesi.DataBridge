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
import org.yuesi.databridge.common.DateUtil;

import lombok.Data;

@Data
@DynamicInsert
@Entity
@IdClass(CodeDateKey.class)
@Table(name = "tb_transdata", catalog = "market")
public class TransData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552859018672303710L;

	@Id
	@Column(name = "code", columnDefinition = "vachar(12)")
	private String code; // 代码

	@Id
	@Column(name = "tradedate", columnDefinition = "date")
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

	@Override
	public String toString() {
		return String.format(
				"TransData{tradeDate=%s, open=%s, high=%s, low=%s, close=%s, volume=%s, amount=%s, change=%s, pChange=%s",
				DateUtil.sdf.format(tradeDate), open == null ? "" : String.format("%.4f", open),
				high == null ? "" : String.format("%.4f", high), low == null ? "" : String.format("%.4f", low),
				close == null ? "" : String.format("%.4f", close), volume == null ? "" : String.format("%.4f", volume),
				amount == null ? "" : String.format("%.4f", amount),
				change == null ? "" : String.format("%.4f", change),
				pChange == null ? "" : String.format("%.4f", pChange));
	}

}
