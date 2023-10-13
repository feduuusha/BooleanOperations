import bsh.EvalError;
import bsh.Interpreter;

import java.util.*;

public class BoolOperator {
    String stringInput, expression;
    int numberOfVars, rows = 1;
    int[][] table;
    private final List<List<Integer>> abbrDNF = new ArrayList<>();
    private List<List<List<Integer>>> tupicDNF = new ArrayList<>();
    public BoolOperator(String str) {
        this.stringInput = str;
        this.counterParameters();
        this.createTable();
        this.defaultFiller();
        this.converter();
        this.fillTableF();
        this.fillListSDNF();
        this.abbreviatedDNF();
        this.engineOfTupicDNF();
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
        for (List<Integer> conjunction : abbrDNF) {
            System.out.print("(");
            for (int j = 0; j < conjunction.size(); ++j) {
                Integer el = conjunction.get(j);
                if (el != -1) System.out.print((el == 1 ? "x" + (j + 1) : "¬x" + (j + 1)) + " ∧ ");
            }
            System.out.print("\b\b\b) ");

        }
        System.out.println();
    }
    public List<List<Integer>> quineTable() {
        List<List<Integer>> qTable = new ArrayList<>();
        for (int i = 0; i < abbrDNF.size(); ++i) {
            qTable.add(new ArrayList<>());
            for (int[] row : table) {
                int res = 1;
                if (row[row.length-1] == 1) {
                    for (int k = 0; k < row.length-1; ++k)
                        if(!abbrDNF.get(i).get(k).equals(-1)) {
                            if (!abbrDNF.get(i).get(k).equals(row[k])) res *= 0;
                        }
                    qTable.get(i).add(res);
                }
            }
        }
        return qTable;
    }
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    private void engineOfTupicDNF() {
        List<List<List<Integer>>> tupic = new ArrayList<>();
        tupic.add(abbrDNF);
        int j = 0;
        while (j != tupic.size()) {
            List<List<Integer>> list = new ArrayList<>(tupic.get(j));
            for (int i = 0; i < list.size(); ++i) {
                List<List<Integer>> copy = new ArrayList<>(list);
                copy.remove(i);
                if (checker(copy)) tupic.add(copy);
            }
            ++j;
        }
        tupic = cleaner(tupic);
        this.tupicDNF = tupic;
    }
    private boolean checker(List<List<Integer>> list) {
        int res = 1;
        for (int[] row : table) {
            if (row[row.length-1] == 1) {
                int result = 0;
                for (List<Integer> integers : list) {
                    int res1 = 1;
                    for (int j = 0; j < integers.size(); ++j) {
                        if (integers.get(j) != -1) {
                            if (integers.get(j) != row[j]) res1 *= 0;
                        }
                    }
                    if (res1 != 0) ++result;
                }
                result = result > 0? 1 : 0;
                res *= result;
            }
        }
        return res==1;
    }
    private List<List<List<Integer>>> cleaner(List<List<List<Integer>>> list) {
        List<List<List<Integer>>> copy = new ArrayList<>(list);
        List<Integer> removable = new ArrayList<>();
        for (int i = list.size()-1; i > 0; --i) {
            for (int j = 0; j < i; j++) {
                List<List<Integer>> lst1 = new ArrayList<>(list.get(i));
                List<List<Integer>> lst2 = new ArrayList<>(list.get(j));
                boolean res = true;
                for (List<Integer> vars : lst1) {
                    if (!lst2.contains(vars)) {
                        res = false;
                        break;
                    }
                }
                if (res) {
                    removable.add(j);
                }
            }
        }
        Set<Integer> set = new HashSet<>(removable);
        removable.clear();
        removable.addAll(set);
        Collections.sort(removable);
        Collections.reverse(removable);
        for (Integer index : removable) copy.remove((int) index);
        return copy;
    }
    public void tupicDNF() {
        for (List<List<Integer>> dnf : tupicDNF) {
            for (List<Integer> conjunction : dnf ) {
                System.out.print("(");
                for (int i = 0; i < conjunction.size(); ++i) {
                    if (conjunction.get(i) != -1) System.out.print((conjunction.get(i)==1? "x" + (i+1) : "¬x" + (i+1)) + " ∧ ");
                }
                System.out.print("\b\b\b)");
            }
            System.out.println();
        }
    }
}
