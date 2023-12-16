enum Operation {
    ADD("+"), SUB("-"), MUL("*"), DIV("/");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
