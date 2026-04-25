package com.devflow.user_project;

import com.devflow.exception.AppException;
import com.devflow.projects.Project;
import com.devflow.users.User;
import com.devflow.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProjectService {
    private final UserProjectRepository userProjectRepository;
    private final UsersRepository usersRepository;
    public void assignUsersForProject(List<Long> userIds, Project project) {
        List<Long> savedIds = new ArrayList<>();

        for (Long userId : userIds) {
            Optional<UserProject> existingUserProject = userProjectRepository.findByUserIdAndProjectId(userId, project.getId());

            UserProject userProject = existingUserProject.orElse(new UserProject());

            User user = usersRepository.findById(userId)
                    .orElseThrow(() -> new AppException.NotFoundException("User not found"));

            userProject.setProject(project);
            userProject.setUser(user);
            userProject.setDeleted(false);

            userProjectRepository.save(userProject);
            savedIds.add(userProject.getId());
        }

        List<UserProject> toDelete = userProjectRepository.findByProjectId(project.getId());
        toDelete.forEach(userProject -> {
            if (!savedIds.contains(userProject.getId())) {
                userProject.setDeleted(true);
                userProjectRepository.save(userProject);
            }
        });
    }
}
