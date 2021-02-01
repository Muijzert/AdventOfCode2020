import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day_8_Handheld_Halting {

    public static void main(String args[]) throws IOException {

        List<String> instructionList = fileReader("Day8.txt");

        GameConsole gameConsole = new GameConsole(instructionList);
        System.out.println("Part 1 : " + gameConsole.findAccumulatorBeforeInfLoop());
        System.out.println("Part 2 : " + gameConsole.findAccumulatorWithoutCorruption());
    }

    public static class GameConsole{
        private List<String> instructionList;
        private int accumulator;
        private int instructionIndex;
        private Boolean[] instructionRun;
        int corruptIndex;
        List<String> masterInstructionList;

        private GameConsole(List<String> instructionList){
            this.instructionList = instructionList;
            this.accumulator = 0;
            this.instructionIndex = 0;
            this.corruptIndex = -1;
            instructionRun = new Boolean[instructionList.size()];
            Arrays.fill(instructionRun, false);
            masterInstructionList = new ArrayList<>();
            masterInstructionList.addAll(instructionList);
        }

        private int findAccumulatorBeforeInfLoop(){
            while(!instructionRun[instructionIndex]) {
                instructionRun[instructionIndex] = true;
                readCommands();
            }
            return accumulator;
        }

        private int findAccumulatorWithoutCorruption(){
            while(instructionIndex < instructionList.size()) {
                changeInstructionList();
                instructionIndex = 0;
                this.accumulator = 0;
                Arrays.fill(instructionRun, false);
                while (instructionIndex < instructionList.size() && !instructionRun[instructionIndex]) {
                    instructionRun[instructionIndex] = true;
                    readCommands();
                }
            }
            return accumulator;
        }

        private void changeInstructionList(){
            instructionList = masterInstructionList;
            String[] OperArg;

            // Change back previous value
            if(corruptIndex != -1){
                OperArg = instructionList.get(corruptIndex).split(" ");
                if(OperArg[0].equals("nop")){
                    instructionList.set(corruptIndex, "jmp " + OperArg[1]);

                }else if(OperArg[0].equals("jmp") && Integer.parseInt(OperArg[1]) != 0){
                    instructionList.set(corruptIndex, "nop " + OperArg[1]);
                }
            }

            for (int i = corruptIndex + 1; i < instructionList.size(); i++) {
                OperArg = instructionList.get(i).split(" ");
                if(OperArg[0].equals("nop")){
                    instructionList.set(i, "jmp " + OperArg[1]);
                    corruptIndex = i;
                    break;
                }else if(OperArg[0].equals("jmp") && Integer.parseInt(OperArg[1]) != 0){
                    instructionList.set(i, "nop " + OperArg[1]);
                    corruptIndex = i;
                    break;
                }
            }
        }

        private void readCommands(){
            String[] OperArg = instructionList.get(instructionIndex).split(" ");

            switch(OperArg[0]){
                case "acc":
                    accumulator+= Integer.parseInt(OperArg[1]);
                    instructionIndex++;
                    break;

                case "jmp":
                    instructionIndex+= Integer.parseInt(OperArg[1]);
                    break;

                case "nop":
                    instructionIndex++;
                    break;
            }
        }
    }

    private static List<String> fileReader(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            words.add(line);
        }
        reader.close();
        return words;
    }

}
