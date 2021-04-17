package com.kyanite.chatsubadmin.repository;

import com.kyanite.chatsubadmin.domain.DdUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DdUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DdUserRepository extends JpaRepository<DdUser, Long> {}
