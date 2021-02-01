import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_10_Adapter_Array {

    public static void main(String args[]) throws IOException {

        TreeSet<Integer> adapters = fileReader("Day10.txt");

        AdapterArray adapterArray = new AdapterArray(adapters);

        System.out.println("Part 1 : " + adapterArray.solvePartOne());
        System.out.println("Part 2 : " + adapterArray.solvePartTwoTwo());
//        System.out.println("Part 2 : " + adapterArray.solvePartTwo(0));

//        adapterArray.printTree();
    }
    public static class AdapterArray {
        private TreeSet<Integer> adapters;
        private HashMap<Integer, Long> possibilitiesForAdapter;

        public AdapterArray(TreeSet<Integer> adapters) {
            int last = adapters.last() + 3;
            adapters.add(last);
            adapters.add(0);
            this.adapters = adapters;
            possibilitiesForAdapter = new HashMap<Integer, Long>();
        }

        private int solvePartOne() {
            int oneJolt = 0;
            int threeJolt = 0;
            int previousJolt = 0;

            for (Integer adapter : adapters) {
                if (previousJolt == adapter - 1) {
                    oneJolt++;
                    previousJolt = adapter;
                } else if (previousJolt == adapter - 3) {
                    threeJolt++;
                    previousJolt = adapter;
                }
            }
            return oneJolt * threeJolt;
        }

        private int solvePartTwo(int jolt) {
            int possibilities = 0;
            if (jolt == adapters.last()) {
                return 1;
            } else {
                if (adapters.contains(jolt + 1)) {
                    possibilities += solvePartTwo(jolt + 1);
                }
                if (adapters.contains(jolt + 2)) {
                    possibilities += solvePartTwo(jolt + 2);
                }
                if (adapters.contains(jolt + 3)) {
                    possibilities += solvePartTwo(jolt + 3);
                }
            }
            return possibilities;
        }

        private Long solvePartTwoTwo() {
            for (Integer adapter : adapters) {
                possibilitiesForAdapter.put(adapter, findPossibilities(adapter));
            }

            return possibilitiesForAdapter.get(adapters.last());
        }

        private Long findPossibilities(int adapter) {
            long possibilities = 0;
            if(adapter == 0){
                return 1L;
            }else{
                for (int i = adapter - 3; i < adapter; i++) {
                    if (adapters.contains(i)) {
                        possibilities += possibilitiesForAdapter.get(i);
                    }
                }
                return possibilities;
            }
        }
    }

    public static TreeSet<Integer> fileReader(String fileName) throws IOException {
        TreeSet<Integer> words = new TreeSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(Integer.parseInt(line));
        }
        reader.close();
        return words;
    }
}
