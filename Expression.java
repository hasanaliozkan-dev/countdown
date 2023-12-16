public abstract class Expression{
    
    // Evaluate the expression
    abstract int eval();
    
    // Get the values of the expression
    @Override
    public String toString() {
        return Integer.toString(eval());
    }
}
