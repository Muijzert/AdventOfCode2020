import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day_15_Rambunctious_Recitation {
    public static void main(String args[]) throws IOException {
        List<String> lines = fileReader("Day15.txt");

        Game game = new Game(lines.get(0));
        Game game2 = new Game(lines.get(0));
        System.out.println("Part 1 : " + game.findSpokenNumber(2020));
        System.out.println("Part 2 : " + game2.findSpokenNumber(30000000));
    }

    public static class Game{
        private List<Integer> startingNumbs;
        private HashMap<Integer, List<Integer>> spoken;

        public Game(String line){
            startingNumbs = new ArrayList<>();
            spoken = new HashMap<>();
            List<Integer> tmpList = new ArrayList<>();
            String[] split = line.split(",");
            for (int i = 0; i < split.length; i++) {
                startingNumbs.add(Integer.parseInt(split[i]));
                tmpList.add(i);
                spoken.put(Integer.parseInt(split[i]), tmpList);
                tmpList = new ArrayList<>();
            }
        }

        private int findSpokenNumber(int numberSpoken){
            List<Integer> tmpList;
            int currentNumb = 0;
            for (int i = startingNumbs.size(); i < numberSpoken; i++) {
                tmpList = new ArrayList<>();
                currentNumb = round(currentNumb, i);
                if (!spoken.containsKey(currentNumb)){
                    // new key
                    tmpList.add(i);
                    spoken.put(currentNumb, tmpList);
                }else{
                    // Already inside list
                    tmpList = spoken.get(currentNumb);
                    tmpList.add(i);
                    spoken.put(currentNumb, tmpList);
                }
            }
            return currentNumb;
        }

        private int round(int currentNumb, int turnIndex){
            List<Integer> countContain = spoken.get(currentNumb);

            if(countContain.size() == 1){
                return 0;
            }else{
                return turnIndex - 1 - countContain.get(countContain.size() - 2);
            }
        }
    }

    public static List<String> fileReader(String fileName) throws IOException {
        List<String> words = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        return words;
    }
}
