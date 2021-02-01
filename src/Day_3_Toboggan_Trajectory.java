import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Day_3_Toboggan_Trajectory{

    public static void main(String[] args) throws IOException {
        List<String> map = fileReader("Day3.txt");
        System.out.println(partOne(map));
        System.out.println(partTwo(map));
    }

    public static int partOne(List<String> map){
        int column = 0;
        int rowLength = map.get(0).length();
        int treeCount = 0;
        // loops through each row in the map
        for (int r = 1; r < map.size(); r++){
            // Increment the columns by 3
            column = column + 3;
            // If column is larger than row length then bring columns back to start
            if (column >= rowLength){
                column = column - rowLength;
            }
            // If new location is a tree add to treeCount
            if (map.get(r).charAt(column) == '#'){
                treeCount++;
            }
        }
        return treeCount;
    }

    public static long partTwo(List<String> map){
        int column = 0;
        int rowLength = map.get(0).length();
        int treeCount;
        long treeMultipled = 1;
        int[] rightArray = {1,3,5,7,1};
        int[] downArray =  {1,1,1,1,2};
        int down;
        int right;


        for (int slope = 0; slope < rightArray.length; slope++) {
            treeCount = 0;
            column = 0;
            down = downArray[slope];
            right = rightArray[slope];

            // loops through each row in the map
            for (int r = 0; r < map.size(); r+=down){
                // If location is a tree add to treeCount
                if (map.get(r).charAt(column) == '#'){
                    treeCount++;
                }
                // Increment the columns
                column+= right;
                // If column is larger than row length then bring columns back to start
                if (column >= rowLength){
                    column = column - rowLength;
                }
            }
            treeMultipled = treeMultipled * treeCount;
        }
        return treeMultipled;
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
