package com.example.wintertechproject.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchRankingController {
    private final SearchRankingService searchRankingService;

    // 검색어 등록
    @PostMapping("/record")
    public ResponseEntity<Void> recordSearch(@RequestParam String keyword) {
        searchRankingService.incrementSearchCount(keyword);
        return ResponseEntity.ok().build();
    }

    // 실시간 검색어 순위 조회
    @GetMapping("/ranking")
    public ResponseEntity<List<SearchRankingDto>> getSearchRanking(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(searchRankingService.getTopSearches(limit));
    }
}