import bsh.EvalError;
import bsh.Interpreter;

import java.util.Arrays;

public class StringConverter {
    String stringLine, expression;
    int numberOfVars, rows = 1;
    int[][] table;
    public StringConverter(String str){
        this.stringLine = str;
        this.counterParameters();
        this.createTable();
        this.defaultFiller();
        this.converter();
        this.fillTableF();
    }
    private void counterParameters() {
        int counter = 0;
        while (stringLine.contains("x" + (counter + 1))) ++counter;
        this.numberOfVars = counter;
        for (int i = 0; i < numberOfVars; ++i) this.rows *= 2;
    }
    private void createTable() {
        this.table = new int[rows][numberOfVars + 1];
    }
    private void defaultFiller() {
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
    private void converter() {
        String expression = "if (";
//        for (int i = 0; i < stringLine.length(); i++) {
//
//        }
        expression += stringLine;
        expression += ") res = 1;";
        this.expression = expression;
    }
    private void fillTableF() {
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
    public void abbreviatedDNF() {
        switch (numberOfVars) {
            case 2:
                for (int i = 0; i < numberOfVars; ++i) {
                    int flag = 1, negFlag = 1;
                    for (int[] row : table) {
                        if (row[i] == 1) {
                            if (row[row.length - 1] == 0) {
                                flag *= 0;
                            }
                        } else {
                            if (row[row.length - 1] == 0) {
                                negFlag *= 0;
                            }
                        }
                    }
                    boolean booFlag = flag == 1;
                    boolean booNegFlag = negFlag == 1;
                    System.out.print((booFlag ? "x" + (i + 1) : "") + (booNegFlag ? "¬x" + (i + 1) : ""));
                }
            case 3:
                for (int j = 0; j < numberOfVars; ++j) {
                    for (int i = j + 1; i < numberOfVars; ++i) {
                        int flag1 = 1, flag2 = 1, flag3 = 1, flag4 = 1;
                        for (int[] row : table) {
                            if (row[i] == 1 && row[j] == 1) {
                                if (row[row.length - 1] == 0) {
                                    flag1 *= 0;
                                }
                            } else if (row[i] == 1 && row[j] == 0) {
                                if (row[row.length - 1] == 0) {
                                    flag2 *= 0;
                                }
                            } else if (row[i] == 0 && row[j] == 1) {
                                if (row[row.length - 1] == 0) {
                                    flag3 *= 0;
                                }
                            } else {
                                if (row[row.length - 1] == 0) {
                                    flag4 *= 0;
                                }
                            }
                        }
                        System.out.print((flag1==1?"(x" + (j+1) + " ^ x" + (i+1) + ")":"") +
                                            (flag2==1?"(¬x" + (j+1) + " ^ x" + (i+1) + ")":"") +
                                            (flag3==1?"(x" + (j+1) + " ^ ¬x" + (i+1) + ")":"") +
                                            (flag4==1?"(¬x" + (j+1) + " ^ ¬x" + (i+1) + ")":""));
                    }

                }


        }
    }
}
