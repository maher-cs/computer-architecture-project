import java.util.*;
import javax.swing.JOptionPane;

public class MIPS_ISA_ToMachine {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the instruction:");
        System.out.println(decodeInst(in.nextLine()));
    }

    public static String decodeInst(String instruction) {
        instruction = formatInst(instruction);
        int opcode;
        String output;
        try {
            opcode = Integer.parseInt(instruction.substring(0, 6), 2);

        } catch (Exception e) {
            System.out.println("error");
            opcode = 1;
            System.exit(0);
        }
        if (opcode == 0) {
            output = decodeRType(instruction);
        } else if (opcode == 2 || opcode == 3) {
            output = decodeJType(instruction);
        } else {
            output = decodeIType(instruction);
        }

        return output;
    }

    public static String formatInst(String binary) {
        String output = "";

        output = binary.replaceAll(" ", "");
        output = output.replaceAll(",", "");

        return output;
    }

    public static String decodeRType(String instruction) {
        String output = "";

        String func = selectFunc(instruction.substring(26));
        output += func;
        output += "   ";
        output += selectRegister(instruction.substring(16, 21));
        output += ", ";
        if (func == null) {
            output = "Function isn't Correct";
        } else {
            if (func.equals("sll") || func.equals("srl")) {
                output += selectRegister(instruction.substring(11, 16));
                output += ", ";
                output += Integer.parseInt(instruction.substring(21, 26), 2);
            } else if (func.equals("jr")) {
                output = func + "   " + selectRegister(instruction.substring(6, 11));
            } else {
                output += selectRegister(instruction.substring(6, 11));
                output += ", ";
                output += selectRegister(instruction.substring(11, 16));
            }
        }
        return output;
    }

    public static String decodeIType(String instruction) {
        String output = "";
        String op = selectOpcode(instruction.substring(0, 6));

        if (containsMemInst(op)) {
            output += op;
            output += "   ";
            output += selectRegister(instruction.substring(16, 21));
            output += ", ";
            output += Integer.parseInt(instruction.substring(16), 2);
            output += "(" + selectRegister(instruction.substring(11, 16)) + ")";
        } else {
            output += op;
            output += "   ";
            output += selectRegister(instruction.substring(11, 16));
            output += ", ";
            output += selectRegister(instruction.substring(6, 11));
            output += ", ";
            output += Integer.parseInt(instruction.substring(16), 2);
        }

        return output;
    }

    public static String decodeJType(String instruction) {
        String output = "";
        String ops = selectOpcode(instruction.substring(0, 6));
        if (containsJumpInst(ops)) {
            output += ops;
            output += "   ";
            output += "Label " + "( Target Adress: " + (Integer.parseInt(instruction.substring(6), 2) * 2)
                    + "  " + "Program Counter: " + (Integer.parseInt(instruction.substring(6), 2) * 2 + 4) + " )";
        } else {
            output = "false";

        }

        return output;
    }

    public static String selectOpcode(String opco) {
        String[] opCodes = {"000000", null, "j", "jal", "beq", "bne", "blez", "bgtz", "addi", "addiu",
            "slti", "sltiu", "andi", "ori", "xori", "lui", null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null,
            "lb", "lh", "lwl", "lw", "lbu", "lhu", "lwr", null, "sb", "sh", "swl",
            "sw", null, null, "swr", "cache", "li", "lwc1", "lwc2", "pref", null,
            "ldc1", "ldc2", null, "sc", "swc1", "swc2", null, null, "sdc1", "sdc2", null};

        return opCodes[Integer.parseInt(opco, 2)];
    }

    public static String selectRegister(String binary) {
        String[] registers = {"zero", "at", "v0", "v1", "a0", "a1", "a2", "a3", "t0",
            "t1", "t2", "t3", "t4", "t5", "t6", "t7", "s0", "s1", "s2",
            "s3", "s4", "s5", "s6", "s7", "t8", "t9", "k0", "k1", "gb",
            "sp", "fp", "ra"};

        return "$" + registers[Integer.parseInt(binary, 2)];
    }

    public static String selectFunc(String func) {
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

        return funcCodes[Integer.parseInt(func, 2)];
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

    public static boolean containsJumpInst(String instr) {
        String[] jumpInstrs = {"j", "jal"};
        for (String str : jumpInstrs) {
            if (str.equals(instr)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsNonInst(String instr) {
        String[] nonInstrs = {null};
        for (String str : nonInstrs) {
            if (str.equals(instr)) {
                return true;
            }
        }
        return false;
    }

}
