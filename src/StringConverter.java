import bsh.EvalError;
import bsh.Interpreter;

import java.util.Arrays;

public class StringConverter {
    String stringLine, expression;
    int numberOfVars, rows = 1;
    int[][] table;
    public StringConverter(String str){
        this.stringLine = str;
        this.counterParametrs();
        this.createTable();
        this.defaultFiller();
        this.converter();
        this.fillTableF();
    }
    public void counterParametrs() {
        int counter = 0;
        while (stringLine.contains("x" + (counter + 1))) ++counter;
        this.numberOfVars = counter;
        for (int i = 0; i < numberOfVars; ++i) this.rows *= 2;
    }
    public void createTable() {
        this.table = new int[rows][numberOfVars + 1];
    }
    public void defaultFiller() {
        int x = rows, col = 0;
        while (x != 1) {
            x /= 2;
            boolean res = true;
            for (int i = 0; i < table.length; ++i) {
                if (i % x == 0 ) res = !res;
                table[i][col] = res ? 1 : 0;
            }
            col += 1;


        }
    }
    public void converter() {
        String expression = "if (";
//        for (int i = 0; i < stringLine.length(); i++) {
//
//        }
        expression += stringLine;
        expression += ") res = 1;";
        this.expression = expression;
    }
    public void fillTableF() {
        for (int i = 0; i < table.length; ++i) {
            Interpreter inter = new Interpreter();
            for (int j = 1; j <= table[0].length; ++j) {
                try {
                    inter.set("x" + j, table[i][j - 1] == 1);
                } catch (EvalError ex) {
                    System.out.println(ex.getMessage());
                }
            }
            try {
                inter.set("res", 0);
                inter.eval(expression);
                table[i][(table[i].length)-1] = (int) inter.get("res");
            } catch (EvalError ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
    public void table() {
        for (int i = 0; i < numberOfVars; ++i) System.out.print("x" + (i+1) + " ");
        System.out.print(" F\n");
        for (int[] ints : table) System.out.println(Arrays.toString(ints));
    }
    public void scnf() {
        char neg = '¬';
        for (int[] variable : table) {
            if (variable[variable.length - 1] == 0) {
                System.out.print("(");
                for (int j = 0; j < variable.length - 1; ++j) {
                    boolean item = variable[j] == 1;
                    System.out.print((item ? neg + "x" + (j + 1) : "x" + (j + 1)) + " ∧ ");
                }
                System.out.print("\b\b\b)∨");
            }
        }
        System.out.println("\b");
    }
    public void sdnf() {
        char neg = '¬';
        for (int[] variable : table) {
            if (variable[variable.length - 1] == 1) {
                System.out.print("(");
                for (int j = 0; j < variable.length - 1; ++j) {
                    boolean item = variable[j] == 1;
                    System.out.print((item ? "x" + (j + 1) : neg + "x" + (j + 1)) + " ∨ ");
                }
                System.out.print("\b\b\b)∧");
            }
        }
        System.out.println("\b");
    }
}
