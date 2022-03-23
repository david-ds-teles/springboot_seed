package com.david.ds.teles.springboot.seed.dao;

import com.david.ds.teles.springboot.seed.core.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, Long> {}
