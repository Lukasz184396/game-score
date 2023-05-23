# Introduction
We are going to implement a game scoring system (the `com.acme.gamescore.GameScore` class).
Every community member can like (the `GameScore.like` method) or dislike any game represented by its code.

## Technical details
It's a maven based project, so it should be easy to import it in your favourite IDE.
There are some Junit 5 test cases. They can be executed from the IDE or from the command line by running:
```shell
mvn test
```
We use latest LTS Java version (17). For your convenience we don't use any Java 17 specific features, so you could change it if needed (Java 11 is the baseline here).
Feel free to use any Java version (11 or above) which works for you. You can change the java version in the [pom.xml](./pom.xml) file by editing following line:
```xml
<java.version>17</java.version>
```

# Tasks
There are 3 tasks. They depend on each other so start with [Task 1](#Task-1) then continue with [Task 2](#Task-2) and finish the whole assignment with the [Task-3](#Task-3).

## Task 1
In this task you are asked to implement the `GameScore.like` and the `GameScore.dislike` methods. Both methods accept a `String` argument representing a game code.

In addition to these methods you also need to implement the `GameScore.getScore` method which also accepts a game code, but it returns a score for a given game calculated in a following way:
percentage of likes for a given game scaled to the values from 0-10 (both inclusively). Values should be rounded **up**. Some examples:

| likes | dislikes |   score |
|------:|---------:|--------:|
|     0 |        0 | `empty` |
|     0 |        1 |       0 |
|     1 |        0 |      10 |
|     1 |        1 |       5 |
|     1 |        2 |       4 |
|     2 |        1 |       7 |

To focus on the code a test case for this scenario is provided in the `com.acme.gamescore.GameScoreScenario1Test` class.

As a result of this task the test should pass successfully. Feel free to add any additional tests you like.
If your IDE doesn't allow you to run the test directly you can run it from a command line by executing:
```shell
mvn test "-Dtest=GameScoreScenario1Test"
```


## Task 2
Our community is growing we have more and more requests. Let's make our `GameScore` thread safe (**Eventually Consistent** solution is fine).

The same approach is used. Focus on the code. Use the `com.acme.gamescore.GameScoreScenario2Test` to verify your solution.

## Task 3
We are getting more and more popular. Some well established figures in the gaming industry want to use our product. Unfortunately they don't want to use the likes/dislikes, they want to score the games directly.

Please add a `GameScore.scoreGame` method accepting a game code and a score (`int` 0-10). The final score (returned by the `GameScore.getScore`) should be an average of the community score and all additional scores rounded **up**.

Please base your solution on the result of a [Task 2](#Task-2).
