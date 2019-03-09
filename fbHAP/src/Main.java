import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    private static Map<String, List<Integer>> timestampMap = new HashMap<>();

    private static Map<String, Integer> map = new HashMap<>();
    private static int counter = 0;
    private static int maxFreq = 0;
    private static String hashtag = "";

    private static int currTimestamp = 0;

    public static void main(String[] args) {
        String fileName = "./test.txt"; //inputA.txt


        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                parseLine(line);
                counter++;
                counter = counter % 3;
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" +
                    fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '"
                    + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        for (Map.Entry<String, List<Integer>> entry : timestampMap.entrySet()) {
          calcFreTimeFrame(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          if (entry.getValue() > maxFreq) {
            maxFreq = entry.getValue();
            hashtag = entry.getKey();
          }
        }

      System.out.println(timestampMap);

      System.out.println(hashtag);
      System.out.println(maxFreq);
    }


    private static void parseLine(String line) {
        switch (counter) {
          case 2:    //empty string
            break;
          case 0:    //timestamp
            currTimestamp = Integer.valueOf(line);
          case 1:    //post
//            parsePostEasy(line);
//            parsePostMedium(line);
            parsePostHard(line, currTimestamp);
            break;
        }
    }

    private static void parsePostEasy(String post) {
      for (String s : post.split("\\s+")) {
        if (!s.isEmpty() && s.charAt(0) == '#') {
          if (!map.containsKey(s)) {
            map.put(s, 1);
          } else {
            int count = map.get(s);
            map.put(s, count+1);
          }
        }
      }

    }

  private static void parsePostMedium(String post) {
    Set<String> set = new HashSet<>();
    for (String s : post.split("\\s+")) {
      if (!s.isEmpty() && s.charAt(0) == '#') {
        set.add(s);
      }
    }
    for (String str : set) {
      if (!map.containsKey(str)) {
        map.put(str, 1);
      } else {
        int count = map.get(str);
        map.put(str, count + 1);
      }
    }
  }

  private static void parsePostHard(String post, int timestamp){

    for (String s : post.split("\\s+")) {
      if (!s.isEmpty() && s.charAt(0) == '#') {
        if (!timestampMap.containsKey(s)) {
          List<Integer> list = new ArrayList<>();
          list.add(timestamp);
          timestampMap.put(s, list);
        } else {
          List<Integer> list = timestampMap.get(s);
          list.add(timestamp);
          timestampMap.put(s, list);
        }
      }
    }

  }

  private static void calcFreTimeFrame(String string, List<Integer> timestamps) {
      //go through list of timestamps and calc freq for the intervals
  }
}
