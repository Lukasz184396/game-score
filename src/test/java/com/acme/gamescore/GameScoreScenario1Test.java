package com.acme.gamescore;

import static java.util.stream.IntStream.range;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameScoreScenario1Test
{
    private static final String UNKNOWN_GAME = "UNKNOWN";
    private static final String GTA6 = "GTA6";

    @Test
    public void shouldReturnEmptyScoreForNotScoredGame()
    {
        final var gameScore =  new GameScore();

        final var score = gameScore.getScore(UNKNOWN_GAME);

        assertThat(score).isNotNull()
                         .isEmpty();
    }

    @Test
    public void shouldScore10WhenThereAreNoDislikes()
    {
        final var gameScore =  new GameScore();

        gameScore.like(GTA6);
        var score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(10);

        gameScore.like(GTA6);
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(10);
    }

    @Test
    public void shouldScore0WhenThereAreNoLikes()
    {
        final var gameScore =  new GameScore();

        gameScore.dislike(GTA6);
        var score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(0);

        gameScore.dislike(GTA6);
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(0);
    }

    @Test
    public void shouldRoundUpTheScore()
    {
        final var gameScore = new GameScore();

        gameScore.dislike(GTA6);
        gameScore.like(GTA6);
        var score = gameScore.getScore("GTA6");
        assertThat(score).isNotNull()
                         .hasValue(5);

        gameScore.like(GTA6);
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(7);

        range(0, 2).forEach(i -> gameScore.like(GTA6));
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(8);

        range(0, 5).forEach(i -> gameScore.like(GTA6));
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(9);

        gameScore.like(GTA6);
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(10);

        range(0, 10_000).forEach(i -> gameScore.like(GTA6));
        score = gameScore.getScore(GTA6);
        assertThat(score).isNotNull()
                         .hasValue(10);
    }
}