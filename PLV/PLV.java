package com.craftinginterpreters.PLV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 * @author ellie
 */
public class PLV {

    static boolean hadError = false;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        for (String a : args) {
            System.out.println(a);
        }
        if (args.length != 2) {
            System.out.println("Usage: PLV [-c expression] ");
            System.exit(64);
        } else if (args.length == 2) {
            runLine(args[1]);
        } else {
            runPrompt();
        }
    }

    /**
     *
     * @param sentence
     */
    public static void runLine(String sentence) {
        run(sentence);
        if (hadError) {
            System.exit(65);
        }
    }

    /**
     *
     * @throws IOException
     */
    public static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("PL Check>> ");
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            run(line);
        }
    }

    /**
     *
     * @param source
     */
    public static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);
        Sentence sentence = parser.parse();
        if (hadError) {
            return;
        }
        System.out.println(new AstPrinter().print(sentence));
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }
}
