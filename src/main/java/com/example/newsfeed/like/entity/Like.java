package com.example.newsfeed.like.entity;

import com.example.newsfeed.feed.entity.Feed;
import com.example.newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","feed_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id",nullable = false)
    private Feed feed;

    @Builder
    public Like(User user, Feed feed) {
        this.user = user;
        this.feed = feed;
    }
}

/**
 * 비관적 락은 데이터의 일관성이 매우 중요하고, -> 은행?, 주식? 금융권??
 * 충돌이 자주 발생할 것으로 예상되는 경우에 적합합니다.
 * 왜냐하면 이 경우 데이터의 일관성과 무결성을
 * 보장하는 것이 더 중요하기 때문입니다.
 *
 * 반면, 낙관적 락은 동시성이 높은 환경에서 -> 여기선 ..
 * 성능을 최적화하고자 할 때 유리합니다.
 * 왜냐하면 이 경우 충돌이 드물게 발생하고,
 * 시스템의 성능을 최대한 활용하는 것이 더 중요하기 때문입니다.
 *
 * */

// 좋아요 동시성 제어
// 좋아요 실행/취소 시 dto에 true/false 값을 바디에..
// dto에 true/false를 만들 것.
// 히스토리성 테이블은 생성시점을 남기는것이 좋다..
// 어떤 시점에 피드에 좋아요가 하나가..있었다면
// 좋아요한 카운트는 피드에 뷰 카운트를 넣는다..
// A피드를 조회중이다.. Like Count가1일때
// 다른 두 사람이 접속한다면 동시에 Like를 클릭하는 순간이 오면
// 그 시점에 select로 count하는 순간 1로 조회됨