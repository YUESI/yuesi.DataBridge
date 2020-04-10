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
@Table(name="tb_tradedate", catalog="market")
public class TradeDate {

	@Id
	@Column(name="tradedate", columnDefinition = "date")
	private Date tradeDate;
	@Column(name="isopen")
	private Boolean isOpen;

	@Override
	public String toString() {
		return "TradeDate{" + "tradeDate=" + tradeDate + ", isOpen=" + isOpen + '}';
	}
}
