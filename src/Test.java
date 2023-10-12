import bsh.EvalError;
import bsh.Interpreter;

public class Test {
    public static void main(String[] args) {
        Interpreter inter = new Interpreter();
        try {
            inter.set("res", 0);
            inter.set("x1", true);
            inter.set("x2", true);
            inter.eval("if (x1 || x2) res = 1");
            int res = (int) inter.get("res");
            System.out.println(res);
        } catch (EvalError ex) {
            System.out.println(ex.getMessage());
        }

    }
}
