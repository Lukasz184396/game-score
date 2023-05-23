package com.acme.gamescore;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class GameScoreScenario2Test
{
    private static final String UNKNOWN_GAME = "UNKNOWN";
    private static final String[] AVAILABLE_GAMES = IntStream.range(0, 100_000)
                                                             .mapToObj(i -> "G" + i)
                                                             .toArray(String[]::new);
    private static final long NUMBER_OF_THREADS = 10;

    @Test
    public void shouldScoreTheGamesInThreadSafeManner() throws InterruptedException
    {
        assertSingleThreadResultIsTheSameAsMultithreadedOne(10);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(100);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(1000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(10_000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(100_000);
        assertSingleThreadResultIsTheSameAsMultithreadedOne(1_000_000);
    }

    private void assertSingleThreadResultIsTheSameAsMultithreadedOne(int numberOfTasks) throws InterruptedException
    {
        Collection<Consumer<GameScore>> allTasks = generateTasks(numberOfTasks);

        var singleThreadResult = getScores(Executors::newSingleThreadExecutor, allTasks);
        var multiThreadedResult = getScores(() -> Executors.newFixedThreadPool(10), allTasks);

        assertThat(singleThreadResult).isEqualTo(multiThreadedResult);
    }

    private static Map<String, OptionalInt> getScores(Supplier<ExecutorService> executorSupplier, Collection<Consumer<GameScore>> tasks) throws InterruptedException
    {
        final ExecutorService executor = executorSupplier.get();
        final GameScore gameScore = new GameScore();

        List<Future<?>> futures = tasks.stream()
                                       .map(t -> (Runnable) () -> t.accept(gameScore))
                                       .map(executor::submit)
                                       .collect(toUnmodifiableList());

        executor.shutdown();
        boolean terminated = executor.awaitTermination(NUMBER_OF_THREADS, TimeUnit.SECONDS);
        assertThat(terminated).withFailMessage("Scoring hasn't finished in 10 seconds.")
                              .isTrue();

        futures.forEach(f -> assertThatNoException().isThrownBy(f::get));

        return Stream.concat(Stream.of(AVAILABLE_GAMES), Stream.of(UNKNOWN_GAME))
                     .collect(Collectors.toMap(Function.identity(), gameScore::getScore));
    }

    private static Collection<Consumer<GameScore>> generateTasks(int numberOfTasks)
    {
        final Random rnd = new Random();
        return LongStream.range(0, numberOfTasks)
                  .mapToObj(i -> AVAILABLE_GAMES[rnd.nextInt(AVAILABLE_GAMES.length)])
                .map(g -> Map.entry(g, rnd.nextBoolean()))
                .map(e -> e.getValue() ? like(e.getKey()) : dislike(e.getKey()))
                .collect(toList());
    }

    private static Consumer<GameScore> like(String gameCode)
    {
        return gameScore -> gameScore.like(gameCode);
    }

    private static Consumer<GameScore> dislike(String gameCode)
    {
        return gameScore -> gameScore.dislike(gameCode);
    }
}
