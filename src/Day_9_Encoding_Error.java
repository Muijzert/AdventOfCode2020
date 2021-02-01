import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_9_Encoding_Error {

    public static void main(String args[]) throws IOException {

        List<Long> XMASpreamble = fileReader("Day9.txt");

        Preamble preamble = new Preamble(XMASpreamble, 25);
        System.out.println("Part 1 : " + preamble.findError());
        System.out.println("Part 2 : " + preamble.findWeakness());

    }

    public static class Preamble {
        private List<Long> preamble;
        private int index;
        private List<Long> previousPreamble;

        public Preamble(List<Long> preamble, int preambleSize){
            this.preamble = preamble;
            this.index = 0;
            previousPreamble = new ArrayList<>();

            for (int i = index; i < preambleSize + index; i++) {
                previousPreamble.add(preamble.get(i));
            }
            index += preambleSize;
        }

        private Long findError(){
            while((index) < preamble.size()) {
                if (!canSum(preamble.get(index))) {
                    return preamble.get(index);
                }
                IncrementPreviousPreamble();
            }
            return 0L;
        }
        private Long findWeakness(){
            Long numb = findError();

            for (int i = 0; i < preamble.size(); i++) {
                for (int j = 0; j < preamble.size(); j++) {
                    if(findSumOfMultiple(i,j) == numb && i != j){
                        return sumOfLargestAndSmallest(i,j);
                    }
                }
            }
            return 0L;
        }

        private boolean canSum(Long numb){

            for (int i = 0; i < previousPreamble.size(); i++) {
                for (int j = 0; j < previousPreamble.size(); j++) {
                    if(previousPreamble.get(i) + previousPreamble.get(j) == numb && i != j){
                        return true;
                    }
                }
            }

            return false;
        }

        public long sumOfLargestAndSmallest(int startInp, int endInp){
            long min = Long.MAX_VALUE;
            long max = 0;
            for (int i = startInp; i < endInp + 1; i++) {
                if(preamble.get(i) > max){
                    max = preamble.get(i);
                }
                if(preamble.get(i) < min){
                    min = preamble.get(i);
                }
            }
            return max + min;
        }

        private long findSumOfMultiple(int startInp, int endInp){
            long total = 0;
            for (int start = startInp; start < endInp + 1; start++) {
                total+= preamble.get(start);
            }
            return total;
        }

        private void IncrementPreviousPreamble(){
            previousPreamble.remove(0);
            previousPreamble.add(preamble.get(index));
            index += 1;
        }



    }



    private static List<Long> fileReader(String fileName) throws IOException {
        List<Long> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(Long.parseLong(line));
        }
        reader.close();
        return words;
    }

}
