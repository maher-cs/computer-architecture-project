public class AssemblyToMachineLogic {
    
    public static void main(String[] args) {
        System.out.println(decodeJType("j", "16"));
        
    }
    
    public static String decodeRType(String func, String rd, String rs, String rt) {
        
        if(selectFunc(func) == 64)
            return "Error";
        
        String output = "";
        output += "000000";
        
        if( ( func.equals("sll") || func.equals("srl") ) ) {
            output += "00000";
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rs)) );
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rd)) );
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rt)) ); //shamt
        } else {
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rs)) );
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rt)) );
            output += String.format( "%5s", Integer.toBinaryString(selectRegister(rd)) );
            output += "00000";
        }
        
        output += String.format( "%5s", Integer.toBinaryString(selectFunc(func)) );
        
        output = output.replaceAll(" ", "0");
        
        return output;
    }
    
    public static String decodeIType(String op, String rt, String rs, String imValue) {
        String output = "";
        output += String.format( "%6s", Integer.toBinaryString(selectOpcode(op)) );
        output += String.format( "%5s", Integer.toBinaryString(selectRegister(rs)) );
        output += String.format( "%5s", Integer.toBinaryString(selectRegister(rt)) );
        
        output += String.format( "%16s", Integer.toBinaryString(Integer.parseInt(imValue)) );
        
        output = output.replaceAll(" ", "0");
        
        return output;
    }
    
    public static String decodeJType(String op, String cons) {
        String output = "";
        output += String.format( "%6s", Integer.toBinaryString(selectOpcode(op)) );
        output += String.format( "%26s", Integer.toBinaryString(Integer.parseInt(cons)) );
        
        output = output.replaceAll(" ", "0");
        return output;
    }
    
    public static int selectFunc(String func) {
        String[] funcCodes = {"sll", null, "srl", "sra", "sllv", null,
            "srlv", "srav", "jr", "jalr", "movz",
            "movn", "syscall", "break", null,
            "sync", "mfhi", "mthi", "mflo", "mtlo",
            null, null, null, null, "mult", "multo",
            "div", "divu", null, null, null, null,
            "add", "addu", "sub", "subu", "and",
            "or", "xor", "nor", null, null, "slt",
            "sltu", null, null, null, null, "tqe",
            "tqeu", "tlt", "tltu", "teq", null, "tne", null,
            null, null, null, null, null, null, null, null};
        
        for(int i = 0; i < funcCodes.length; i++) {
            if(func.equals(funcCodes[i]))
                return i;
        }

        return 64;
    }
    
    public static int selectOpcode(String opco) {
        
        
        String[] opCodes = {"", null, "j", "jal", "beq", "bne", "blez", "bgtz", "addi", "addiu",
            "slti", "sltiu", "andi", "ori", "xori", "lui", null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,//32
            "lb", "lh", "lwl", "lw", "lbu", "lhu", "lwr", null, "sb", "sh", "swl",
            "sw", null, null, "swr", "cache", "li", "lwc1", "lwc2", "pref", null,
            "ldc1", "ldc2", null, "sc", "swc1", "swc2", null, null, "sdc1", "sdc2", null};

        for(int i = 0; i < opCodes.length; i++) {
            if(opco.equals(opCodes[i]))
                return i;
        }

        return 64;
    }
    
    public static int selectRegister(String reg) {
        String[] registers = {"zero", "at", "v0", "v1", "a0", "a1", "a2", "a3", "t0",
            "t1", "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
            "s3", "s4", "s5", "s6", "s7", "t8", "t9", "k0", "k1", "gb",
            "sp", "fp", "ra"};

        for(int i = 0; i < registers.length; i++) {
            if(reg.equals("$" + registers[i]))
                return i;
        }
        
        return 32;
    }

    public static boolean containsMemInst(String instr) {
        String[] memInstrs = {"lb", "lh", "lwl", "lw", "lbu", "lhu", "lwr", "sb", "sh", "swl",
            "sw", "swr"};
        for (String str : memInstrs) {
            if (str.equals(instr)) {
                return true;
            }
        }
        return false;
    }
    
    
    
}
