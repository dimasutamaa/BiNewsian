package com.binewsian.dto;

import com.binewsian.enums.VoteType;

public record ForumVoteResponse(long upvotes, long downvotes, VoteType userVote) {
}
