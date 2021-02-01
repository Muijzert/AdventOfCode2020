import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day_17_Conway_Cubes {
    public static void main(String args[]) throws IOException {
        List<String> lines = fileReader("Day17.txt");
        ThreeD partOne = new ThreeD(lines);
        System.out.println(partOne.partOne(6));
        FourD partTwo = new FourD(lines);
        System.out.println(partTwo.partTwo(6));
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

    public static class ThreeD {
        HashMap<XYZ, Boolean> pocketDimension;
        int xMin, xMax, yMin, yMax, zMin, zMax;

        public ThreeD(List<String> inputCubes){
            pocketDimension = new HashMap<>();
            // x
            xMin = 0;
            xMax = inputCubes.get(0).length() - 1;
            // y
            yMin = 0;
            yMax = inputCubes.size() - 1;
            // z
            zMin = 0;
            zMax = 0;
            //loops x and y axis
            for (int y = 0; y < inputCubes.size(); y++) {
                for (int x = 0; x < inputCubes.get(y).length(); x++) {
                    if(inputCubes.get(y).charAt(x) == '.'){
                        pocketDimension.put(new XYZ(x,y,0), false);
                    }else{
                        pocketDimension.put(new XYZ(x,y,0), true);
                    }
                }
            }
        }

        private int partOne(int cycleLength){
            for (int i = 0; i < cycleLength; i++) {
                cycle(new HashMap<>(pocketDimension));
                System.out.println("Cycle " + (i+1) + " " + countActive());
            }
            return countActive();
        }

        private void cycle(HashMap<XYZ, Boolean> PreviousPocketDimension){
            int neighbourCubeActives;
            int tmp_x_Min = xMin;
            int tmp_x_Max = xMax;
            int tmp_y_Min = yMin;
            int tmp_y_Max = yMax;
            int tmp_z_Min = zMin;
            int tmp_z_Max = zMax;
            XYZ tmpCubeKey;
            for (int x = tmp_x_Min - 1; x <= tmp_x_Max + 1; x++) {
                for (int y = tmp_y_Min - 1; y <= tmp_y_Max + 1; y++) {
                    for (int z = tmp_z_Min - 1; z <= tmp_z_Max + 1; z++) {
                        neighbourCubeActives = countNeighbours(PreviousPocketDimension, x,y,z);
                        tmpCubeKey = new XYZ(x,y,z);

                        // if cube exists
                        if(PreviousPocketDimension.containsKey(tmpCubeKey)){

                            //exists
                            if(PreviousPocketDimension.get(tmpCubeKey)){
                                //active cube
                                if(!(neighbourCubeActives == 3 || neighbourCubeActives == 2)){
//                                    if under 2 or above 3 neighbours are active then make false
                                    pocketDimension.replace(tmpCubeKey, false);
                                }
                            }else{
                                //inactive cube
                                if(neighbourCubeActives == 3){
                                    pocketDimension.replace(tmpCubeKey, true);
                                }
                            }
                        }else{

                            if(neighbourCubeActives == 3){
                                pocketDimension.put(tmpCubeKey, true);
                                checkRange(x,y,z);
                            }

                            //doesn't exist

                            // if new cube is active and outside one of the dimension max range then increase dimension range
                        }

                    }
                }
            }
        }

        private void checkRange(int x, int y, int z){
            if(x > xMax){
                xMax++;
            }
            if(x < xMin){
                xMin--;
            }
            if(y > yMax){
                yMax++;
            }
            if(y < yMin){
                yMin--;
            }
            if(z > zMax){
                zMax++;
            }
            if(z < zMin){
                zMin--;
            }
        }

        private int countNeighbours(HashMap<XYZ, Boolean> PreviousPocketDimension, int x, int y, int z){
            int neighbourCubeActives = 0;
            for (int Nx = x - 1; Nx <= x + 1; Nx++) {
                for (int Ny = y - 1; Ny <= y + 1; Ny++) {
                    for (int Nz = z - 1; Nz <= z + 1; Nz++) {
                        if(PreviousPocketDimension.containsKey(new XYZ(Nx,Ny,Nz))){
                            if (!(x == Nx && y == Ny && z == Nz)){
                                if(PreviousPocketDimension.get(new XYZ(Nx,Ny,Nz)))
                                    neighbourCubeActives++;
                            }

                        }
                    }
                }
            }
            return neighbourCubeActives;
        }

        private int countActive(){
            Collection<Boolean>  cubeStatusList = pocketDimension.values();
            int active = 0;
            for (boolean cube : cubeStatusList) {
                if(cube){
                    active++;
                }
            }
            return active;
        }

    }

    private static class XYZ {
        public int x;
        public int y;
        public int z;

        public XYZ(int x, int y, int z){
            this.x = x;
            this.y = y;
            this.z = z;
        }


        @Override
        public int hashCode(){
            int hash = 1;
            hash = 43 * hash + this.x;
            hash = 43 * hash + this.y;
            hash = 43 * hash + this.z;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof XYZ){
                return this.x == ((XYZ) obj).x && this.y == ((XYZ) obj).y && this.z == ((XYZ) obj).z;
            }
            return false;
        }

    }

    public static class FourD {
        HashMap<XYZW, Boolean> pocketDimension;
        int xMin, xMax, yMin, yMax, zMin, zMax, wMin, wMax;

        public FourD(List<String> inputCubes){
            pocketDimension = new HashMap<>();
            // x
            xMin = 0;
            xMax = inputCubes.get(0).length() - 1;
            // y
            yMin = 0;
            yMax = inputCubes.size() - 1;
            // z
            zMin = 0;
            zMax = 0;
            // w
            // z
            wMin = 0;
            wMax = 0;
            //loops x and y axis
            for (int y = 0; y < inputCubes.size(); y++) {
                for (int x = 0; x < inputCubes.get(y).length(); x++) {
                    if(inputCubes.get(y).charAt(x) == '.'){
                        pocketDimension.put(new XYZW(x,y,0,0), false);
                    }else{
                        pocketDimension.put(new XYZW(x,y,0,0), true);
                    }
                }
            }
        }

        private int partTwo(int cycleLength){
            for (int i = 0; i < cycleLength; i++) {
                cycle(new HashMap<>(pocketDimension));
                System.out.println("Cycle " + (i+1) + " " + countActive());
            }
            return countActive();
        }

        private void cycle(HashMap<XYZW, Boolean> PreviousPocketDimension){
            int neighbourCubeActives;
            int tmp_x_Min = xMin;
            int tmp_x_Max = xMax;
            int tmp_y_Min = yMin;
            int tmp_y_Max = yMax;
            int tmp_z_Min = zMin;
            int tmp_z_Max = zMax;
            int tmp_w_Min = wMin;
            int tmp_w_Max = wMax;
            XYZW tmpCubeKey;
            for (int x = tmp_x_Min - 1; x <= tmp_x_Max + 1; x++) {
                for (int y = tmp_y_Min - 1; y <= tmp_y_Max + 1; y++) {
                    for (int z = tmp_z_Min - 1; z <= tmp_z_Max + 1; z++) {
                        for (int w = tmp_w_Min - 1; w <= tmp_w_Max + 1; w++) {
                            neighbourCubeActives = countNeighbours(PreviousPocketDimension, x, y, z, w);
                            tmpCubeKey = new XYZW(x, y, z, w);
                            // if cube exists
                            if (PreviousPocketDimension.containsKey(tmpCubeKey)) {

                                //exists
                                if (PreviousPocketDimension.get(tmpCubeKey)) {
                                    //active cube
                                    if (!(neighbourCubeActives == 3 || neighbourCubeActives == 2)) {
//                                    if under 2 or above 3 neighbours are active then make false
                                        pocketDimension.replace(tmpCubeKey, false);
                                    }
                                } else {
                                    //inactive cube
                                    if (neighbourCubeActives == 3) {
                                        pocketDimension.replace(tmpCubeKey, true);
                                    }
                                }
                            } else {

                                if (neighbourCubeActives == 3) {
                                    pocketDimension.put(tmpCubeKey, true);
                                    checkRange(x, y, z, w);
                                }

                                //doesn't exist

                                // if new cube is active and outside one of the dimension max range then increase dimension range
                            }
                        }
                    }
                }
            }
        }

        private void checkRange(int x, int y, int z, int w){
            // x
            if(x > xMax){
                xMax++;
            }
            if(x < xMin){
                xMin--;
            }
            // y
            if(y > yMax){
                yMax++;
            }
            if(y < yMin){
                yMin--;
            }
            // z
            if(z > zMax){
                zMax++;
            }
            if(z < zMin){
                zMin--;
            }
            // w
            if(w > wMax){
                wMax++;
            }
            if(w < wMin){
                wMin--;
            }
        }

        private int countNeighbours(HashMap<XYZW, Boolean> PreviousPocketDimension, int x, int y, int z, int w ){
            int neighbourCubeActives = 0;
            for (int Nx = x - 1; Nx <= x + 1; Nx++) {
                for (int Ny = y - 1; Ny <= y + 1; Ny++) {
                    for (int Nz = z - 1; Nz <= z + 1; Nz++) {
                        for (int Nw = w - 1; Nw <= w + 1; Nw++) {
                            if (PreviousPocketDimension.containsKey(new XYZW(Nx, Ny, Nz, Nw))) {
                                if (!(x == Nx && y == Ny && z == Nz && w == Nw)) {
                                    if (PreviousPocketDimension.get(new XYZW(Nx, Ny, Nz, Nw)))
                                        neighbourCubeActives++;
                                }
                            }
                        }
                    }
                }
            }
            return neighbourCubeActives;
        }

        private int countActive(){
            Collection<Boolean>  cubeStatusList = pocketDimension.values();
            int active = 0;
            for (boolean cube : cubeStatusList) {
                if(cube){
                    active++;
                }
            }
            return active;
        }

    }

    private static class XYZW {
        public int x;
        public int y;
        public int z;
        public int w;

        public XYZW(int x, int y, int z, int w){
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }


        @Override
        public int hashCode(){
            int hash = 1;
            hash = 43 * hash + this.x;
            hash = 43 * hash + this.y;
            hash = 43 * hash + this.z;
            hash = 43 * hash + this.w;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof XYZW){
                return this.x == ((XYZW) obj).x && this.y == ((XYZW) obj).y && this.z == ((XYZW) obj).z && this.w == ((XYZW) obj).w;
            }
            return false;
        }

    }
}
