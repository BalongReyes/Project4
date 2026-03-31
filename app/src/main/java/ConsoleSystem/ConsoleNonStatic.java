
package ConsoleSystem;

public class ConsoleNonStatic {

    public ConsoleNonStatic tab() {
        return Console.tab();
    }

    public ConsoleNonStatic gap() {
        return Console.gap();
    }

    public ConsoleNonStatic line() {
        return Console.line();
    }

    // System console output
    // -------------------------------------------------------------------------------------

    public <E> ConsoleNonStatic out(E[] arrayOutput) {
        return Console.out(arrayOutput);
    }

    public ConsoleNonStatic out(String output) {
        return Console.out(output);
    }

    public ConsoleNonStatic out(String output, boolean line) {
        return Console.out(output, line);
    }

}
