package com.binewsian.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NewsStatus {
    DRAFT("Draft", "status-draft"),
    PUBLISHED("Published", "status-published");

    private final String displayName;
    private final String cssClass;
}
