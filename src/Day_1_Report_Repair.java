import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day_1_Report_Repair {

    public static void main(String[] args) throws IOException {
        Set<Integer> fileMap = fileReaderSet("Day1.txt");

        System.out.println("Part 1 : " + findEntry(fileMap));
        System.out.println("Part 2 : " + findSecondEntry(fileMap));
    }

    public static int findSecondEntry(Set<Integer> fileMap){
        int searchedNumber;
        int outputNumb;
        for (Integer key : fileMap) {
            for(Integer secondkey : fileMap){
                searchedNumber =  2020 - secondkey - key;
                if (fileMap.contains(searchedNumber)) {
                    return searchedNumber * key * secondkey;

                }
            }
        }
        return 0;
    }

    public static int findEntry(Set<Integer> fileMap){
        int searchedNumber;
        int outputNumb;
        for (Integer key : fileMap) {
            searchedNumber = 2020 - key;
            if (fileMap.contains(searchedNumber)) {
                outputNumb = searchedNumber * key;
                return outputNumb;
            }
        }
        return 0;
    }

    public static Set<Integer> fileReaderSet(String fileName) throws IOException {
        Set<Integer> words = new HashSet<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(Integer.parseInt(line));
        }
        reader.close();
        return words;
    }
}
