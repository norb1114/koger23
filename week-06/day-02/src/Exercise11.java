import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Exercise11 {
  /*
   * Find a random Wikipedia article and copy the contents to a txt file.
   * Create a Stream expression which reads all text from this file, and prints the 100 most common words in descending
   * order. Keep in mind that the text contains punctuation characters which should be ignored. The result should be
   * something like this:
   * */
  public static void main(String[] args) {
    try {
      Files.readAllLines(Paths.get("assets/wiki.txt")).stream()
              .flatMap(charSequence -> Pattern.compile(" ").splitAsStream(charSequence))
              .flatMap(charSequence -> Pattern.compile("\\[").splitAsStream(charSequence))
              .flatMap(charSequence -> Pattern.compile("\\[").splitAsStream(charSequence))
              .flatMap(charSequence -> Pattern.compile("]").splitAsStream(charSequence))
              .collect(Collectors.toMap(k -> {
                        if (k.endsWith(". ") || k.endsWith(", ") || k.endsWith(".\n") || k.endsWith(",\n")) {
                          return k.substring(0, k.length() - 2);
                        } else if (k.endsWith("?") || k.endsWith(",") || k.endsWith(".") || k.endsWith("!")|| k.endsWith(":")) {
                          return k.substring(0, k.length() - 1);
                        } else {
                          return k;
                        }
                      },
                      v -> 1,
                      (i, i1) -> Integer.sum(i, i1)))
              .entrySet()
              .stream()
              .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
              .limit(100)
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
              .entrySet()
              .stream()
              .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
