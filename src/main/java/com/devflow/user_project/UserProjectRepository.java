package com.devflow.user_project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProjectRepository extends JpaRepository<UserProject,Long> {
    Optional<UserProject> findByUserIdAndProjectId(Long userId, Long projectId);

    List<UserProject> findByProjectId(Long projectId);
}

