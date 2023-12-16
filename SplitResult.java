public class SplitResult<T> {
        final T left;
        final T right;

        SplitResult(T left, T right) {
            this.left = left;
            this.right = right;
        }
    }