import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Countdown {

    public static List<Integer> evaluate(Expression expr) {
        if (expr instanceof Value) {
            int n = ((Value) expr).value;
            return (n > 0) ? Arrays.asList(n) : new ArrayList<>();
        } else if (expr instanceof Application) {
            Application app = (Application) expr;
            List<Integer> result = new ArrayList<>();
            List<Integer> evalLeft = evaluate(app.left);
            List<Integer> evalRight = evaluate(app.right);
            for (int x : evalLeft) {
                for (int y : evalRight) {
                    if (isvalid(app.op, x, y)) {
                        result.add(apply(app.op, x, y));
                    }
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Unknown expression type: " + expr.getClass());
        }
    }

    public static List<List<Integer>> subsetschoices(List<Integer> xs) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> s : subsets(xs)) {
            result.addAll(permutations(s));
        }
        return result;
    }

    private static List<List<Integer>> subsets(List<Integer> xs) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        for (int x : xs) {
            List<List<Integer>> ys = new ArrayList<>();
            for (List<Integer> sublist : result) {
                List<Integer> copy = new ArrayList<>(sublist);
                ys.add(copy);
                copy.add(x);
            }
            result.addAll(ys);
        }
        return result;
    }

    private static List<List<Integer>> permutations(List<Integer> xs) {
        List<List<Integer>> result = new ArrayList<>();
        if (xs.isEmpty()) {
            result.add(new ArrayList<>());
        } else {
            int x = xs.get(0);
            List<Integer> ys = xs.subList(1, xs.size());
            for (List<Integer> perm : permutations(ys)) {
                //System.out.println(interleave(x, perm));
                result.addAll(interleave(x, perm));
            }
        }
        return result;
    }

    private static List<List<Integer>> interleave(int x, List<Integer> ys) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= ys.size(); i++) {
            List<Integer> copy = new ArrayList<>(ys);
            copy.add(i, x);
            result.add(copy);
        }
        return result;
    }

    /*
    private static boolean solution(Expression expr, List<Integer> ns, int n) {
        return subsetschoices(ns).contains(values(expr)) && evaluate(expr).contains(n);
    }
 */
    private static List<Pair<List<Integer>, List<Integer>>> split(List<Integer> xs) {
        List<Pair<List<Integer>, List<Integer>>> result = new ArrayList<>();
        for (SplitResult<List<Integer>> splitResult : splitHelper(xs)) {          
            result.add(new Pair<>(splitResult.left, splitResult.right));
        }
        return result;
    }

    private static List<SplitResult<List<Integer>>> splitHelper(List<Integer> xs) {
        List<SplitResult<List<Integer>>> result = new ArrayList<>();
        if (xs.size() > 1) {
            result.add(new SplitResult<>(Arrays.asList(xs.get(0)), xs.subList(1, xs.size())));
            for (SplitResult<List<Integer>> splitResult : splitHelper(xs.subList(1, xs.size()))) {
                List<Integer> left = new ArrayList<>(Arrays.asList(xs.get(0)));
                left.addAll(splitResult.left);
                result.add(new SplitResult<>(left, splitResult.right));
            }
        }
        return result;
    }

    private static List<Expression> createexpression(List<Integer> ns) {
        List<Expression> result = new ArrayList<>();
        if (ns.size() == 1) {
            result.add(new Value(ns.get(0)));
        } else {
            for (Pair<List<Integer>, List<Integer>> split : split(ns)) {
                for (Expression l : createexpression(split.fst)) {
                    for (Expression r : createexpression(split.snd)) {
                        result.addAll(combine(l, r));
                    }
                }
            }
        }
        //System.out.println(result);
        return result;
    }

    private static List<Expression> combine(Expression l, Expression r) {
        List<Expression> result = new ArrayList<>();
        for (Operation op : Operation.values()) {
            result.add(new Application(op, l, r));
        }
        return result;
    }

    private static boolean isvalid(Operation op, int x, int y) {
        switch (op) {
            case ADD:
                return true;
            case SUB:
                return x > y;
            case MUL:
                return true;
            case DIV:
                return x % y == 0;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private static int apply(Operation op, int x, int y) {
        switch (op) {
            case ADD:
                return x + y;
            case SUB:
                return x - y;
            case MUL:
                return x * y;
            case DIV:
                return x / y;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private static List<Expression> solutions(List<Integer> ns, int n) {
        List<Expression> result = new ArrayList<>();
        for (List<Integer> nsPrime : subsetschoices(ns)) {
            for (Expression e : createexpression(nsPrime)) {
                
                if (evaluate(e).contains(n)) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

        int n = 3;  // Change this to your desired value
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        
        List<Expression> play = solutions(numbers , 6);
        for (Expression e : play) {
            System.out.println(e);
        }
        
        
        
    }
}
