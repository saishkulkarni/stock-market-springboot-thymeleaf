package org.jsp.stocks.service;

import org.jsp.stocks.dto.UserStocksTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStocksTransactionRepository extends JpaRepository<UserStocksTransaction, Integer> {

}
