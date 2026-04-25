package com.devflow.projects;

import lombok.Data;

@Data
public class ProjectFilterRequest {
    private long id = 0;
    private boolean showDeleted = false;
    private String keyword = null;
    private int page = 0;
    private int size = 100;
}
