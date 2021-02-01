import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day_13_Shuttle_Search {

    public static void main(String args[]) throws IOException {

        List<String> buses = fileReader("Day13.txt");

        BusTimes busTimes = new BusTimes(buses);

        System.out.println("Part 1 : " + busTimes.partOne());
        System.out.println("Part 2 : " + busTimes.partTwo());

    }

    public static class BusTimes{

        private long earliest;
        private String[] buses;
        private List<Integer> busesInService;

        public BusTimes(List<String> busTimes){
            earliest = Integer.parseInt(busTimes.get(0));
            buses = busTimes.get(1).split(",");

            busesInService = new ArrayList<>();

            for (String bus: buses) {
                if(!bus.equals("x"))
                    busesInService.add(Integer.parseInt(bus));
            }
        }

        private long partOne(){
            long time = earliest;
            boolean found = false;
            long output = 0;

            while(!found){
                for (int bus : busesInService) {
                    if(time % bus == 0) {
                        output = bus * (time - earliest);
                        found = true;
                    }
                }
                time++;
            }
            return output;
        }

        public long partTwo() {
            ArrayList<Integer> arrayB = new ArrayList<>();
            ArrayList<Long> arrayN = new ArrayList<>();
            ArrayList<Integer> arrayX = new ArrayList<>();

            long productN = 1;
            for (int i = 0; i < buses.length; i++) {
                if (!buses[i].equals("x")) {
                    productN *= Integer.parseInt(buses[i]);
                    arrayB.add(Integer.parseInt(buses[i]) + (-1 * i) % Integer.parseInt(buses[i]));
                }
            }

            for (int i = 0; i < busesInService.size(); i++) {
                arrayN.add(productN / busesInService.get(i));
                arrayX.add(inverse(arrayN.get(i), busesInService.get(i)));
            }

            long total = 0;
            for (int i = 0; i < busesInService.size(); i++) {
                total += arrayB.get(i) * arrayN.get(i) * arrayX.get(i);
            }
            total = total % productN;
            return total;
        }

        private static int inverse(long Ni, int ni) {
            int xi = 1;
            while ((Ni * xi) % ni != 1) {
                xi++;
            }
            return xi;
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
