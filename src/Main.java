import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        /*executorService.execute(() -> {
            for (int i=1; i<=5000; i ++)
                System.out.println("Hello");
        });

        executorService.execute(() -> {
            for (int i=1; i<=5000; i ++)
                System.out.println("Hi");
        });

        executorService.shutdown();*/

        /*allable<String> stringCallable = () -> "Hello";

        Future<String> submit = executorService.submit(stringCallable);
        System.out.println("Hi");
        System.out.println(submit.get());
        System.out.println("Bye");
        executorService.shutdown();*/

        /*Supplier<String> stringSupplier = () -> {
            System.out.println("Hello");
            return "Hello";
        };
        Function<String, Integer> stringToIntegerFunction = s -> {
            System.out.println("Hi");
            return s.length();
        };

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(stringSupplier);
        CompletableFuture<Integer> integerCompletableFuture = stringCompletableFuture.thenApply(stringToIntegerFunction);
        System.out.println("Bye");
        Integer integer = integerCompletableFuture.get();
        System.out.println("Ok");
        System.out.println(integer);*/

        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("Hello");
        });
        for (int i=0; i<100; i++)
            System.out.println(completableFuture.isDone());*/


        /*List<CompletableFuture<Integer>> integerCompletableFuture = new ArrayList<>();
        for (int i=0; i<5; i++) {
            integerCompletableFuture.add(CompletableFuture.supplyAsync(() -> 5)
                    .thenApply(integer -> integer*5)
                    .thenApply(integer -> integer * 10));
        }
        int sum = integerCompletableFuture.stream().mapToInt(value -> {
            try {
                return value.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).sum();
        System.out.println(sum);*/

        //Find the maximum element from the below data
        List<Integer[]> matrix = new ArrayList<>(
                Arrays.asList(new Integer[]{5,3,65,89},
                        new Integer[]{12,35,43,67,95},
                        new Integer[]{13,52,61,94,42},
                        new Integer[]{26,98,28,73},
                        new Integer[]{19,43,55,33,99}
                ));

        //Solution 1
        System.out.println("Execution started for solution 1.");
        Optional<Integer> max1 = matrix.stream().map(integers -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return Arrays.stream(integers).max(Comparator.comparing(Integer::intValue)).orElse(null);
                        }
                ).filter(Objects::nonNull)
                .max(Comparator.comparing(Integer::intValue));
        System.out.println(max1.get());

        //Solution 2
        System.out.println("Execution started for solution 2.");
        Optional<Integer> max2 = matrix.stream().flatMap(Arrays::stream)
                .max(Comparator.comparing(Integer::intValue));
        System.out.println(max2.get());

        //Solution 3
        System.out.println("Execution started for solution 3.");
        List<CompletableFuture<Optional<Integer>>> completableFutures = matrix.stream().map(integers ->
                CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return Arrays.stream(integers).max(Comparator.comparing(Integer::intValue));
                        }
                )
        ).toList();

        Optional<Integer> max3 = completableFutures.stream()
                .map(optionalCompletableFuture -> {
                    try {
                        return optionalCompletableFuture.get().orElse(null);
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Integer::intValue));

        System.out.println(max3.orElse(-1));

    }

}
