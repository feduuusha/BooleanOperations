import bsh.EvalError;
import bsh.Interpreter;

import java.util.*;

public class StringConverter {
    String stringInput, expression;
    int numberOfVars, rows = 1;
    int[][] table;
    private final List<List<Integer>> abbrDNF = new ArrayList<>();
    public StringConverter(String str) {
        this.stringInput = str;
        this.counterParameters();
        this.createTable();
        this.defaultFiller();
        this.converter();
        this.fillTableF();
    }
    private void counterParameters() {
        int counter = 0;
        while (stringInput.contains("x" + (counter + 1))) ++counter;
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
                if (i % x == 0) res = !res;
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
        expression += stringInput;
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
                table[i][(table[i].length) - 1] = (int) inter.get("res");
            } catch (EvalError ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private void fillListSDNF() {
        for (int[] row : table) {
            if (row[row.length - 1] == 1) {
                abbrDNF.add(new ArrayList<>());
                for (int j = 0; j < row.length - 1; ++j) {
                    abbrDNF.get(abbrDNF.size() - 1).add(row[j]);
                }
            }
        }
    }
    public void table() {
        for (int i = 0; i < numberOfVars; ++i) System.out.print("x" + (i + 1) + " ");
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
    private void abbreviatedDNF() {
        int counter;
        do {
            List<List<Integer>> clone = new ArrayList<>(abbrDNF);
            List<Integer> removable = new ArrayList<>();
            int count = 0;
            for (int k = 0; k < numberOfVars; ++k) {
                for (int j = 0; j < abbrDNF.size(); ++j) {
                    for (int i = j + 1; i < abbrDNF.size(); ++i) {
                        if (abbrDNF.get(j).size() == abbrDNF.get(i).size()) {
                            if (k < abbrDNF.get(j).size()) {
                                if (!abbrDNF.get(j).get(k).equals(abbrDNF.get(i).get(k))) {
                                    List<Integer> lst1 = new ArrayList<>(abbrDNF.get(j));
                                    List<Integer> lst2 = new ArrayList<>(abbrDNF.get(i));
                                    lst1.set(k, -1);
                                    lst2.set(k, -1);
                                    if (lst1.equals(lst2)) {
                                        clone.add(lst1);
                                        count++;
                                        removable.add(i);
                                        removable.add(j);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Set<Integer> set = new HashSet<>(removable);
            removable.clear();
            removable.addAll(set);
            Collections.sort(removable);
            Collections.reverse(removable);
            for (int var : removable) clone.remove(var);
            abbrDNF.clear();
            abbrDNF.addAll(clone);
            counter = count;
        }
        while (counter != 0);
    }
    public void abbrDNF() {
        this.fillListSDNF();
        abbreviatedDNF();
        for (List<Integer> conjunction : abbrDNF) {
            System.out.print("(");
            for (int j = 0; j < conjunction.size(); ++j) {
                Integer el = conjunction.get(j);
                if (el != -1) System.out.print((el == 1 ? "x" + (j + 1) : "¬x" + (j + 1)) + " ^ ");
            }
            System.out.print("\b\b\b) ");

        }
    }
}
