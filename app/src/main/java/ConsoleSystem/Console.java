
package ConsoleSystem;

import MethodsSystem.MethodString;

public class Console{
    
    public static ConsoleNonStatic tab(){
        System.out.print("\t");
        return new ConsoleNonStatic();
    }
    
    public static ConsoleNonStatic gap(){
        System.out.println();
        return new ConsoleNonStatic();
    }
    
    public static ConsoleNonStatic line(){
        Console.out("-----------------------------------------------------");
        return new ConsoleNonStatic();
    }
    
// System console output -------------------------------------------------------------------------------------
    
    public static <E> ConsoleNonStatic out(E[] arrayOutput){
        for(E report : arrayOutput) out((String)report, true);
        return new ConsoleNonStatic();
    }
    
    public static ConsoleNonStatic out(int output){
        return out(String.valueOf(output));
    }
    
    public static ConsoleNonStatic out(String output){
        System.out.println(output);
        return new ConsoleNonStatic();
    }
    
    public static ConsoleNonStatic out(int output, boolean line){
        return out(String.valueOf(output), line);
    }
    
    public static ConsoleNonStatic out(String output, boolean line){
        if(line){
            System.out.println(output);
        }else{
            System.out.print(output);
        }
        return new ConsoleNonStatic();
    }
    
// Error -----------------------------------------------------------------------------------------------------
    
    public static void errorOut(String errorMessage, Exception e){
        Console.line();
        Console.out("Class: ", false).out(Thread.currentThread().getStackTrace()[2].getClassName());
        Console.out("Method: ", false).out(Thread.currentThread().getStackTrace()[2].getMethodName());
        Console.out("Message: ", false).out(errorMessage);
        Console.out("Error: ", false).out(MethodString.removeBlankLines(e.getMessage()));
    }
    
}
