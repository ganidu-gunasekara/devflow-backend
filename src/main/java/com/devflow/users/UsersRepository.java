package com.devflow.users;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.company " +
            "WHERE u.isDeleted = :showDeleted " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:companyId = 0 OR u.company.id = :companyId) " +
            "ORDER BY u.id ASC")
    Page<User> findAllUsers(@Param("showDeleted") boolean showDeleted,
                            @Param("keyword") String keyword,
                            @Param("companyId") long companyId,
                            Pageable pageable);
    @Query("SELECT u from User as u LEFT JOIN fetch u.company LEFT JOIN fetch u.selectedProject where u.isDeleted =  false and u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);
}
