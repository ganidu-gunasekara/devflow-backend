package com.devflow.users;

import lombok.Data;

@Data
public class UserFilterRequest {
    private boolean showDeleted = false;
    String keyword = null;
    int page = 0;
    int size = 100;
    long company_id = 0;
}
