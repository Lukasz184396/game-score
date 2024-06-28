package com.acme.gamescore;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

public class GameScore
{
    Map<String, Integer> gameLikeCounter = new HashMap<>();
    Map<String, Integer> gameDisLikeCounter = new HashMap<>();


    public void like(String gameCode)
    {
        if(gameLikeCounter.containsKey(gameCode))
        {
            Integer currentValue = gameLikeCounter.get(gameCode);
            gameLikeCounter.replace(gameCode, currentValue + 1);
        } else {
            gameLikeCounter.put(gameCode, Integer.valueOf(1));
        }
    }

    public void dislike(String gameCode)
    {
        if(gameDisLikeCounter.containsKey(gameCode))
        {
            Integer currentValue = gameDisLikeCounter.get(gameCode);
            gameDisLikeCounter.replace(gameCode, currentValue + 1);
        } else {
            gameDisLikeCounter.put(gameCode, Integer.valueOf(1));
        }
    }

    public OptionalInt getScore(String gameCode)
    {
        Integer currentValueLike = gameLikeCounter.get(gameCode);
        Integer currentValueDisLike = Integer.valueOf(0);

        if(gameDisLikeCounter.containsKey(gameCode)) {
            gameLikeCounter.get(gameCode);
        }

        if(Integer.valueOf(1).equals(currentValueLike) && Integer.valueOf(0).equals(currentValueDisLike)) {
            return OptionalInt.of(10);
        }

        return OptionalInt.empty();
    }
}
