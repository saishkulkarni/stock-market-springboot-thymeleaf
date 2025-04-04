package org.jsp.stocks.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AdminData {
	@Id
	private final int id=1;
	private double totalPlatformFee;
	private double platformFeePercentage;
	private double totalStocksBought;
	private double totalStocksSold;
	private double totalTransaction;
}
