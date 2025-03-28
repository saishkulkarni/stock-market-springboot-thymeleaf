package org.jsp.stocks.repository;

import org.jsp.stocks.dto.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {

}
