package com.acme.gamescore;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameScore
{
    private final ConcurrentHashMap<String, AtomicInteger> gameLikeCounter = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicInteger> gameDislikeCounter = new ConcurrentHashMap<>();

    public void like(String gameCode)
    {
        gameLikeCounter.computeIfAbsent(gameCode, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public void dislike(String gameCode)
    {
        gameDislikeCounter.computeIfAbsent(gameCode, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public OptionalInt getScore(String gameCode)
    {
        int likeCount = gameLikeCounter.getOrDefault(gameCode, new AtomicInteger(0)).get();
        int dislikeCount = gameDislikeCounter.getOrDefault(gameCode, new AtomicInteger(0)).get();

        if (likeCount == 0 && dislikeCount == 0) {
            return OptionalInt.empty();
        }

        if (likeCount > 0 && dislikeCount == 0) {
            return OptionalInt.of(10);
        }

        if (likeCount == 0 && dislikeCount > 0) {
            return OptionalInt.of(0);
        }

        double ratio = (double) likeCount / (likeCount + dislikeCount);
        int score = (int) Math.ceil(ratio * 10); // Use Math.ceil to ensure rounding up

        return OptionalInt.of(score);
    }
}
