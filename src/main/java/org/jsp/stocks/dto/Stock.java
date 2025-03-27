package org.jsp.stocks.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Stock {
	private String ticker;
	private String companyName;
	private double price;
}
