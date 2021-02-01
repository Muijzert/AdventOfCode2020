import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_6_Custom_Customs {
    public static void main(String args[]) throws IOException {
        List<Set<String>> answers = questionReader("Day6.txt");

        // Part 1
        System.out.println("Part 1 -> total questions = " + calcAnswers(answers));

        // Part 2
        List<Set<String>> answers2 = questionReader2("Day6.txt");
        System.out.println("Part 1 -> total questions = " + calcAnswers(answers2));
    }
    public static int calcAnswers(List<Set<String>> answers){
        int total = 0;
        for (Set<String> answer : answers) {
            total += answer.size();
        }
        return total;
    }



    public static List<Set<String>> questionReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        String [] keyValue;
        List<Set<String>> answers = new ArrayList<>();
        Set<String> answer = new HashSet<>();

        // Reads text file line by line
        while ((line = reader.readLine()) != null) {
            // If new passport as shown by an empty line add previous passport to list and make blank passport
            if (line.equals("")){
                answers.add(answer);
                answer = new HashSet<>();
            }else{
                // Adds key and values from current line to passport
                String[] splitLine = line.split(" ");
                for (char questionID : line.toCharArray()) {
                    answer.add(Character.toString(questionID));
                }
            }
        }
        reader.close();
        // Add last passport
        answers.add(answer);
        return answers;
    }

    public static List<Set<String>> questionReader2(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        String [] keyValue;
        List<Set<String>> answers = new ArrayList<>();
        Set<String> tmpAnswer = new HashSet<>();
        HashMap<Character,Integer> answer = new HashMap<>();
        int groupSize = 0;
        Object[] keySet;

        // Reads text file line by line
        while ((line = reader.readLine()) != null) {
            // If new passport as shown by an empty line add previous passport to list and make blank passport
            if (line.equals("")){

                // Check how many questions everyone got
                keySet = answer.keySet().toArray();
                for (Object aKey : keySet) {
                    if (answer.get(aKey).equals(groupSize)) {
                        tmpAnswer.add(aKey.toString());
                    }
                }

                answers.add(tmpAnswer);
                answer = new HashMap<>();
                tmpAnswer = new HashSet<>();
                groupSize = 0;
            }else{
                // Adds key and values from current line to passport
                for (char questionID : line.toCharArray()) {

                    if(answer.containsKey(questionID)){
                        answer.replace(questionID, answer.get(questionID) + 1);
                    }else{
                        answer.put(questionID, 1);
                    }
                }
                groupSize++;
            }
        }
        reader.close();
        // Add last passport
        keySet = answer.keySet().toArray();
        for (Object aKey : keySet) {
            if (answer.get(aKey).equals(groupSize)) {
                tmpAnswer.add(aKey.toString());
            }
        }

        answers.add(tmpAnswer);


        return answers;
    }

}
