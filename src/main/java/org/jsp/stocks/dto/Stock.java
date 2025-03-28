package org.jsp.stocks.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
public class Stock {
	@Id
	private String ticker;
	private String companyName;
	private double price;
	private double changes;
	private double quantity;
}