
public class Test {
    public static void main(String[] args) {
//        public void abbreviatedDNF() {
//            switch (numberOfVars) {
//                case 2:
//                    for (int i = 0; i < numberOfVars; ++i) {
//                        int flag = 1, negFlag = 1;
//                        for (int[] row : table) {
//                            if (row[i] == 1) {
//                                if (row[row.length - 1] == 0) {
//                                    flag *= 0;
//                                }
//                            } else {
//                                if (row[row.length - 1] == 0) {
//                                    negFlag *= 0;
//                                }
//                            }
//                        }
//                        boolean booFlag = flag == 1;
//                        boolean booNegFlag = negFlag == 1;
//                        System.out.print((booFlag ? "x" + (i + 1) : "") + (booNegFlag ? "¬x" + (i + 1) : ""));
//                    }
//                case 3:
//                    for (int j = 0; j < numberOfVars; ++j) {
//                        for (int i = j + 1; i < numberOfVars; ++i) {
//                            int flag1 = 1, flag2 = 1, flag3 = 1, flag4 = 1;
//                            for (int[] row : table) {
//                                if (row[i] == 1 && row[j] == 1) {
//                                    if (row[row.length - 1] == 0) {
//                                        flag1 *= 0;
//                                    }
//                                } else if (row[i] == 1 && row[j] == 0) {
//                                    if (row[row.length - 1] == 0) {
//                                        flag2 *= 0;
//                                    }
//                                } else if (row[i] == 0 && row[j] == 1) {
//                                    if (row[row.length - 1] == 0) {
//                                        flag3 *= 0;
//                                    }
//                                } else {
//                                    if (row[row.length - 1] == 0) {
//                                        flag4 *= 0;
//                                    }
//                                }
//                            }
//                            System.out.print((flag1 == 1 ? "(x" + (j + 1) + " ^ x" + (i + 1) + ")" : "") +
//                                    (flag2 == 1 ? "(¬x" + (j + 1) + " ^ x" + (i + 1) + ")" : "") +
//                                    (flag3 == 1 ? "(x" + (j + 1) + " ^ ¬x" + (i + 1) + ")" : "") +
//                                    (flag4 == 1 ? "(¬x" + (j + 1) + " ^ ¬x" + (i + 1) + ")" : ""));
//                        }
//
//                    }
//
//
//            }
//        }
//        private List<List<Integer>> cleaner(List<List<Integer>> a) {
//            List<List<Integer>> b = new ArrayList<>(a);
//            for (int i = 0; i < a.size(); ++i) {
//                for (int j = 0; j < a.get(i).size(); ++j) {
//                    if (a.get(i).get(j) == -1) b.get(i).remove(j);
//
//                }
//            }
//            return b;
//        }
    }
}
