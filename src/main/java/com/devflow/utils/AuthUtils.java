package com.devflow.utils;

import com.devflow.company.Company;
import com.devflow.users.User;
import com.devflow.users.enums.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal(); // 👈 now this works
    }

    public boolean isAdminOrSuperAdmin() {
        UserType type = getCurrentUser().getType();
        return type == UserType.SUPER_ADMIN || type == UserType.ADMIN;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public Company getCurrentUserCompany() {
        return getCurrentUser().getCompany();
    }
}
