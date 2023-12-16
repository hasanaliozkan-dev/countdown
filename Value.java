    public class Value extends Expression {
    public int value;

    public Value(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}
