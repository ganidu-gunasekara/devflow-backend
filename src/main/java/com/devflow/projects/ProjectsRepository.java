package com.devflow.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByIsDeleted(int isDeleted);
}
