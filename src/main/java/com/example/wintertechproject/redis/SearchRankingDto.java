package com.example.wintertechproject.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRankingDto {
    private int rank;
    private String keyword;
    private long count;
}
