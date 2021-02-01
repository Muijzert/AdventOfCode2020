import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_5_Binary_Boarding {


    public static void main(String args[]) throws IOException {
        List<String> boardingPasses = fileReader("Day5.txt");
        System.out.println("Part 1 -> highest seat ID = " + highestSeatID(boardingPasses));
        System.out.println("Part 2 -> highest seat ID = " + mySeatID(boardingPasses));
    }

    public static int highestSeatID(List<String> boardingPasses){
        int minRows;
        int maxRows;
        int minColumn;
        int maxColumn;
        int tmp = 0;
        int boardingID;
        for (String boardingPass : boardingPasses) {
            minRows = 0;
            maxRows = 127;
            minColumn = 0;
            maxColumn = 7;
            for(int i = 0; i < 10; i++){
                if(boardingPass.charAt(i) == 'F'){
                    maxRows = maxRows - (maxRows - minRows)/2 - 1;
                }else if (boardingPass.charAt(i) == 'B'){
                    minRows = minRows + (maxRows - minRows)/2 + 1;
                }else if (boardingPass.charAt(i) == 'L'){
                    maxColumn = maxColumn - (maxColumn - minColumn)/2 - 1;
                }else if (boardingPass.charAt(i) == 'R'){
                    minColumn = minColumn + (maxColumn - minColumn)/2 + 1;
                }
            }
//            System.out.println("minRow = " + minRows + "   maxRow = " + maxRows + "   minColumn = " + minColumn + "   maxColumn = " + maxColumn);
            boardingID = minRows * 8 + minColumn;
            if(boardingID > tmp) {
                tmp = boardingID;
            }
        }
        return tmp;
    }

    public static int mySeatID(List<String> boardingPasses){
        int minRows;
        int maxRows;
        int minColumn;
        int maxColumn;
        int tmp = 0;
        int boardingID;
        Set<Integer> IDs = new HashSet<>();
        for (String boardingPass : boardingPasses) {
            minRows = 0;
            maxRows = 127;
            minColumn = 0;
            maxColumn = 7;
            for(int i = 0; i < 10; i++){
                if(boardingPass.charAt(i) == 'F'){
                    maxRows = maxRows - (maxRows - minRows)/2 - 1;
                }else if (boardingPass.charAt(i) == 'B'){
                    minRows = minRows + (maxRows - minRows)/2 + 1;
                }else if (boardingPass.charAt(i) == 'L'){
                    maxColumn = maxColumn - (maxColumn - minColumn)/2 - 1;
                }else if (boardingPass.charAt(i) == 'R'){
                    minColumn = minColumn + (maxColumn - minColumn)/2 + 1;
                }
            }
//            System.out.println("minRow = " + minRows + "   maxRow = " + maxRows + "   minColumn = " + minColumn + "   maxColumn = " + maxColumn);
            boardingID = minRows * 8 + minColumn;

            IDs.add(boardingID);
            if(boardingID > tmp) {
                tmp = boardingID;
            }
        }
        boolean leftTmp = false;
        boolean centreTmp = false;
        boolean rightTmp = false;
        int myID = 0;

        for(int j = 0; j < tmp + 1; j++){
            switch(j%3){
                case 0:
                    leftTmp = IDs.contains(j);
                    break;
                case 1:
                    centreTmp = IDs.contains(j);
                    break;
                case 2:
                    rightTmp = IDs.contains(j);
                    break;
            }
            if (leftTmp && !centreTmp && rightTmp && 2 == j%3){
                myID = j -  1;
            }
        }

        return myID;
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
