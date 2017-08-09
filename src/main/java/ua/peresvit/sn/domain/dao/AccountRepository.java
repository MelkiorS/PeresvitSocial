package ua.peresvit.sn.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ua.peresvit.sn.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findOneByEmail(String email);
}