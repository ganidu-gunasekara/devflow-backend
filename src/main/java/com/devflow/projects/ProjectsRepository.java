package com.devflow.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.company " +
            "WHERE p.isDeleted = :showDeleted " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:id = 0 OR :id = p.id)")
    List<Project> findProjectsWithCompanies(@Param("showDeleted") boolean showDeleted,
                                            @Param("keyword") String keyword,
                                            @Param("id") Long id

    );

    @Query("SELECT p FROM Project p " +
            "LEFT JOIN FETCH p.company " +
            "LEFT JOIN FETCH p.userProjects up " +
            "LEFT JOIN FETCH up.user u " +
            "LEFT JOIN FETCH u.company " +
            "WHERE p.isDeleted = false " +
            "AND (up.isDeleted = false OR up.isDeleted IS NULL) " +
            "AND (u.company.id = p.company.id OR u IS NULL) " +
            "AND (:id = 0 OR :id = p.id)")
    Optional<Project> findProject(@Param("id") Long id);

    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.company " +
            "JOIN p.userProjects up " +
            "WHERE p.isDeleted = :showDeleted " +
            "AND up.user.id = :userId " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:id = 0 OR :id = p.id)")
    List<Project> findProjectsByUserId(@Param("showDeleted") boolean showDeleted,
                                       @Param("keyword") String keyword,
                                       @Param("id") Long id,
                                       @Param("userId") Long userId);
}
