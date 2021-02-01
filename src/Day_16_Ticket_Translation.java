import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_16_Ticket_Translation {
    public static void main(String args[]) throws IOException {
        List<String> ticketData = fileReader("Day16.txt");

        TicketReader ticketReader = new TicketReader(ticketData);

        System.out.println("Part 1 : " +  ticketReader.errorRate());
        System.out.println("Part 2 : " +  ticketReader.findDepartmentValue());
    }

    public static class TicketReader{
        HashMap<String, int[]> rules;
        List<Integer> yourTicket;
        List<List<Integer>> nearbyTickets;

        public TicketReader(List<String> lines){
            rules = new HashMap<>();
            yourTicket = new ArrayList<>();
            nearbyTickets = new ArrayList<>();
            //## Break up ##

            int tmp = 0;
            String[] split;

            // Rules
            for (int i = 0; i < lines.size(); i++) {
                if(lines.get(i).equals("")){
                    tmp = i + 2;
                    break;
                }
                split = lines.get(i).split(":");
                rules.put(split[0], getRange(split[1]));
            }

            // Your ticket
            for (int i = tmp; i < lines.size(); i++) {
                if(lines.get(i).equals("")){
                    tmp = i + 2;
                    break;
                }
                split = lines.get(i).split(",");
                addYourTicket(split);
            }

            // Other tickets
            for (int i = tmp; i < lines.size(); i++) {
                split = lines.get(i).split(",");
                nearbyTickets.add(getNearbyTicket(split));
            }

        }

        private int[] getRange(String ranges){
            ranges = ranges.trim();
            String[] rangeArray = ranges.split(" or ");

            String[] range1 = rangeArray[0].split("-");
            String[] range2 = rangeArray[1].split("-");

            return new int[] {Integer.parseInt(range1[0]), Integer.parseInt(range1[1]), Integer.parseInt(range2[0]), Integer.parseInt(range2[1])};

        }
        private void addYourTicket(String[] strings){
            for (String string :strings){
                yourTicket.add(Integer.parseInt(string));
            }
        }
        private List<Integer> getNearbyTicket(String[] strings){
            List<Integer> tmp = new ArrayList<>();
            for (String string : strings) {
                tmp.add(Integer.parseInt(string));
            }

            return tmp;
        }
        private long findDepartmentValue() {
            int ticketSize = nearbyTickets.get(0).size();
            int[] ruleNames = new int[ticketSize];
            int[] currentRange;
            boolean[][] ticketIndexForRule = new boolean[ticketSize][ticketSize];
            for (int i = 0; i < ticketSize; i++) {
                Arrays.fill(ticketIndexForRule[i], true);
            }
            Object[] rulesKeySet = rules.keySet().toArray();
            String[] indexRules = new String[ticketSize];


            // Loops through each rule
            for (int i = 0; i < ticketSize; i++) {
//                for (int x = 0; x < ticketSize; x++) {
//                    Arrays.fill(ticketIndexForRule[x], true);
//                }
                currentRange = rules.get(rulesKeySet[i]);
                // Loops through each nearby ticket
                for (List<Integer> ticket : nearbyTickets) {
                    for (int j = 0; j < ticketSize; j++) {

                        int tmpTicket = ticket.get(j);
                        if(!(ticket.get(j) >= currentRange[0] && ticket.get(j) <= currentRange[1] || ticket.get(j) >= currentRange[2] && ticket.get(j) <= currentRange[3])){
                            ticketIndexForRule[i][j] = false;
                        }
                    }
                }
            }

            int possibilities;
            int tmpIndex = 0;
            boolean loop = true;
            while(loop){
                for (int i = 0; i < ticketIndexForRule.length; i++) {
                    possibilities = 0;
                    tmpIndex = -1;
                    for (int j = 0; j < ticketIndexForRule.length; j++) {
                        if (ticketIndexForRule[i][j]) {
                            possibilities++;
                            tmpIndex = j;
                        }
                    }
                    if (possibilities == 1) {
                        indexRules[tmpIndex] = (String) rulesKeySet[tmpIndex];
                        for (int j = 0; j < ticketIndexForRule.length; j++) {
                            ticketIndexForRule[j][tmpIndex] = false;
                        }
                    }
                }
                loop = false;
                for (int i = 0; i < ticketIndexForRule.length; i++) {
                    if(indexRules[i] == null){
                        loop = true;
                    }
                }
            }

            long sum = 1;
            for (int i = 0; i < ticketIndexForRule.length; i++) {
                if(indexRules[i].contains("departure")){
                    System.out.println(i);
                    sum*= yourTicket.get(i);
                }
            }




            return sum;
        }


        // Remove invalid tickets which are tickets which aren't valid for any of the fields
        private int errorRate(){
            int error = 0;
            for (int i = 0; i < nearbyTickets.size(); i++) {
                for (Integer value : nearbyTickets.get(i)){
                    // Can value be used for any rule
                    if(!valid(value)){
                        error+= value;
                        nearbyTickets.remove(i);
                        i--;
                        break;
                    }
                }

            }
            return error;
        }
        private boolean valid (int value){
            int[] currentRange;

            for (String rule : rules.keySet()) {
                currentRange = rules.get(rule);

                if(value >= currentRange[0] && value <= currentRange[1] || value >= currentRange[2] && value <= currentRange[3]){
                    return true;
                }
            }
            return false;
        }
    }

    public static List<String> fileReader(String fileName) throws IOException {
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
