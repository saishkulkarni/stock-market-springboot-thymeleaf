package org.jsp.stocks.repository;

import java.util.Optional;

import org.jsp.stocks.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	Optional<User> findByEmail(String email);

}
