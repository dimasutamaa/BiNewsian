package com.binewsian.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityType {
    WEBINAR("Webinar"),
    SEMINAR("Seminar"),
    WORKSHOP("Workshop"),
    CERTIFICATION("Certification"),
    COMMUNITY_SERVICE("Community Service");

    private final String displayName;
}
