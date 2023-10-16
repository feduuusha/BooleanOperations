public class Main {
    public static void main(String[] args) {
        BoolOperator gen = new BoolOperator("0010 1011 1101 1111");
        BoolOperator gen1 = new BoolOperator("(!X1 & !X2 & !x3 & !x4) || (!x1 & !x2 & !x3 & x4) || (!x1 & !x2 & x3 & !x4) || (!x1 & !x2 & x3 & x4) || (!x1 & x2 & !x3 && !x4) || (x1 & !x2 & !x3 & x4) || (x1 & x2 & !x3 & !x4) || (x1 & x2 & !x3 & x4)");
        gen1.table();
        gen.table();
        System.out.println("СКНФ:");
        gen.scnf();
        System.out.println("СДНФ:");
        gen.sdnf();
        System.out.println("Сокращенная ДНФ:");
        gen.abbrDNF();
        System.out.println("Тупиковые ДНФ:");
        gen.tupicDNF();
        System.out.println("Таблица Квайна:");
        gen.quineTable();
        System.out.println("Минимальные ДНФ:");
        gen.minimumDNF();
    }
}