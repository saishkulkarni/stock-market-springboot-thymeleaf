package org.jsp.stocks.repository;



import org.jsp.stocks.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

    Object findByEmail(String email);

    void findAllByEmail(String email);
	

}
