public class HazardDetection {

    public static void main(String[] args) {
    
        String[][] strmatrix = { {"lw", "s0", "t1", "t0"} ,
                                 {"add", "t3", "s0", "t8"} ,
                                 {"sw", "t4", "s5", "s4"} };
        
        String[] ins = { "lw s0 t1 t0" ,
                           "add t3 s0 t8",
                           "sw t4 s5 s4" };
        for( String s : insertStall(ins, detectDependency(strmatrix) ) ) {
            System.out.println(s);
        }
    }

    public static int[] detectDependency(String[][] instMatrix) {
        
        int[] dependency = new int[instMatrix.length];
        
        for(int i = 1; i < instMatrix.length; i++) {
            for(int j = (i == 1? 1: 2); j > 0; j--) {
                if(instMatrix[i][2] == instMatrix[i-j][1] || instMatrix[i][3] == instMatrix[i-j][1]) {
                    dependency[i] = i-j;
                } else {
                    dependency[i] = i;
                }
            }
        }
        
        return dependency;
    }
    
    public static ArrayList<String> insertStall(String[] instructions, int[] dependency) {
        
        final int MAX_STALL = 3;
        String stall = "nop";
        ArrayList<String> output = new ArrayList<String>();
        for(int i = 0; i < instructions.length; i++) {
            int dif = i - dependency[i];
            for(in j = MAX_STALL - dif; (dif != 0) && (j > 0); j--) {
                output.add(stall);
            }
            output.add(instructions[i]);
        }
        
        return output;
    
    }
}
