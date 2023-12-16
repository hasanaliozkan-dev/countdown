public class Application extends Expression {

        final Operation op; // The operation
        final Expression left; // The left operand
        final Expression right; // The right operand

        Application(Operation op, Expression left, Expression right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }

  
        @Override
        public String toString() {
            return "(" + left + op + right + ")";
        }

        @Override
        public int eval() {

        switch (this.op) {
            case ADD:
                return left.eval() + right.eval();
            case SUB:
                return left.eval() - right.eval();
            case MUL:
                return left.eval() * right.eval();
            case DIV:
                if (right.eval() != 0) {
                    return left.eval() / right.eval();
                } else {
                    throw new ArithmeticException("Division by zero");
                }
            default:
                throw new UnsupportedOperationException("Unsupported operation: " + op);
        }
    }
}
