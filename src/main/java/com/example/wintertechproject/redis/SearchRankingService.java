package com.example.wintertechproject.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchRankingService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String RANKING_KEY = "search:ranking";

    // 검색어 등록/카운트 증가
    public void incrementSearchCount(String keyword) {
        redisTemplate.opsForZSet().incrementScore(RANKING_KEY, keyword, 1);
    }

    // 상위 N개 검색어 조회
    public List<SearchRankingDto> getTopSearches(int limit) {
        Set<ZSetOperations.TypedTuple<String>> rankings =
                redisTemplate.opsForZSet().reverseRangeWithScores(RANKING_KEY, 0, limit - 1);

        List<SearchRankingDto> result = new ArrayList<>();
        int rank = 1;

        for (ZSetOperations.TypedTuple<String> tuple : rankings) {
            result.add(new SearchRankingDto(
                    rank++,
                    tuple.getValue(),
                    tuple.getScore().longValue()
            ));
        }

        return result;
    }
}
