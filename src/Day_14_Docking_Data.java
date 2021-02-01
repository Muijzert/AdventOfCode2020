import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_14_Docking_Data {


    public static void main(String args[]) throws IOException {
        List<String> program = fileReader("Day14.txt");

        BitMask bitMask = new BitMask(program);

        System.out.println("Part 1 : " + bitMask.partOne());
        System.out.println("Part 2 : " + bitMask.partTwo());
    }

    public static class BitMask{
        private List<String> program;
        private HashMap<Integer, Long> memory;
        private HashMap<Long, Long> memory2;

        public BitMask(List<String> program){
            this.program = program;
            memory = new HashMap<>();
            memory2 = new HashMap<>();


        }

        private long partOne(){
            String[] split;
            String currentMask =  program.get(0).split("=")[1].trim();
            int currentNumb;
            int memoryKey;
            for (String line : program) {
                // Split string up
                split = line.split("=");

                if(split[0].contains("mask")){
                    // Set new mask
                    currentMask = split[1].trim();
                }else{
                    currentNumb = Integer.parseInt(split[1].trim());
                    memoryKey = Integer.parseInt(split[0].substring(4,split[0].length() - 2));
                    // Applies binary mask and save to hash map
                    memory.put(memoryKey,applyMask(currentNumb, currentMask));
                }
            }
            // Calculate sum of values in hashmap
            Set<Integer> keySet = memory.keySet();
            long sum = 0;

            for (int key : keySet){
                sum+= memory.get(key);
            }
            return sum;
        }

        private long partTwo(){
            String[] split;
            String currentMask =  program.get(0).split("=")[1].trim();
            Long currentNumb;
            int memoryKey;
            char[] maskedMemory;
            List<char[]> charList;
            List<Long> memoryList;
            for (String line : program) {
                // Split string up
                split = line.split("=");

                if(split[0].contains("mask")){
                    // Set new mask
                    currentMask = split[1].trim();
                }else{
                    currentNumb = Long.parseLong(split[1].trim());
                    memoryKey = Integer.parseInt(split[0].substring(4,split[0].length() - 2));

                    // String which applies mask
                    maskedMemory = applyMaskTwo(memoryKey, currentMask);

                    // Finds combinations of masked numb List<char[]>
                    charList = getAddresses(maskedMemory, 0);

                    // Converts List<char[]> to List<Long>
                    memoryList = convertBinaryToLong(charList);

                    for (int i = 0; i < memoryList.size(); i++) {
                        System.out.println(memoryList.get(i) + "  =  "  + String.valueOf(charList.get(i)));
                    }

                    // Adds values HashMap
                    for (long mem : memoryList) {
                        memory2.put(mem, currentNumb);
                    }
                }
            }
            // Calculate sum of values in hashmap

            long sum = 0;
            for (long key : memory2.values()){
                sum+= key;
            }
            return sum;
        }

        private List<Long> convertBinaryToLong(List<char[]> charList){
            List<Long> memoryList = new ArrayList<>();
            for (char[] binary : charList) {
                memoryList.add(Long.parseLong(String.valueOf(binary),2));
            }
            return memoryList;
        }

        private List<char[]> getAddresses(char[] maskedNumb, int index){
            List<char[]> memoryAddresses = new ArrayList<>();
            if(maskedNumb.length == index){
                memoryAddresses.add(maskedNumb);
                return memoryAddresses;

            }else if(maskedNumb[index] == 'X'){
                char[] zeroNumb;
                zeroNumb = maskedNumb.clone();
                zeroNumb[index] = '0';
                memoryAddresses.addAll(getAddresses(zeroNumb,index+1));
                char[] oneNumb;
                oneNumb = maskedNumb.clone();
                oneNumb[index] = '1';
                memoryAddresses.addAll(getAddresses(oneNumb,index+1));
                return memoryAddresses;
            }else{
                memoryAddresses.addAll(getAddresses(maskedNumb, index + 1));
                return memoryAddresses;
            }



        }

        private long applyMask(Integer numb, String mask){
            // Turn numb into binary number in char[] form
            String binaryNumb = Integer.toBinaryString(numb);
            String filledBinaryNumb = String.join("", Collections.nCopies(mask.length() - binaryNumb.length(), "0")) + binaryNumb;
            char[] filledBinaryNumbChar = filledBinaryNumb.toCharArray();

            // Use mask
            for (int i = 0; i < filledBinaryNumbChar.length; i++) {
                if(mask.charAt(i) != 'X'){
                    filledBinaryNumbChar[i] = mask.charAt(i);
                }
            }
            // return masked numb
            return Long.parseLong(String.valueOf(filledBinaryNumbChar),2);
        }

        private char[] applyMaskTwo(Integer numb, String mask){
            // Turn numb into binary number in char[] form
            String binaryNumb = Integer.toBinaryString(numb);
            String filledBinaryNumb = String.join("", Collections.nCopies(mask.length() - binaryNumb.length(), "0")) + binaryNumb;
            char[] filledBinaryNumbChar = filledBinaryNumb.toCharArray();

            // Use mask
            for (int i = 0; i < filledBinaryNumbChar.length; i++) {
                if(mask.charAt(i) != '0'){
                    filledBinaryNumbChar[i] = mask.charAt(i);
                }
            }
            // return masked numb
            return filledBinaryNumbChar;

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
