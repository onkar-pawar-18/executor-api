import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WordCount {

    public static void main(String[] args) {
        Map<String, Integer> wordMap = new HashMap<>();
        File folder = new File("D:\\IDE\\SpringMvcPractice\\GitProjects\\executor-api\\files");
        if (folder.isDirectory()) {
            List<File> files = Arrays.stream(folder.listFiles()).toList();

            List<CompletableFuture<Void>> completableFutures = files.stream().map(file ->
                    CompletableFuture.runAsync(() -> {
                        if (file.isFile()) {
                            try (Scanner fileReader = new Scanner(file)) {
                                while (fileReader.hasNext()) {
                                    String[] words = fileReader.nextLine().split(" ");
                                    for (String word : words) {
                                        updateMap(wordMap, word);
                                    }
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    })
            ).toList();

            System.out.println("=================================================");

            completableFutures.forEach(voidCompletableFuture -> {
                try {
                    voidCompletableFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });

            wordMap.forEach((word, count) -> System.out.println(word+" "+count));

        }

        /*for (File file : files) {
            if (file.isFile()) {
                try (Scanner fileReader = new Scanner(file)) {
                    while (fileReader.hasNext()) {
                        String[] words = fileReader.nextLine().split(" ");
                        for (String word: words){
                            wordMap.merge(word, 1, Integer::sum);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
    }

    private static synchronized void updateMap(Map<String, Integer> wordMap, String word) {
        wordMap.merge(word, 1, Integer::sum);
    }

}
