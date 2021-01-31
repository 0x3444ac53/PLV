/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftinginterpreters.PLV;

import static com.craftinginterpreters.PLV.TokenType.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ellie
 */
public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0, current = 0, line = 1;
    //private static final Map<String, TokenType> keywords;

    /*
    static {
        keywords = new HashMap<>();
        keywords.put("∨", OR);
        keywords.put("&", AND);
        keywords.put("→", IF);
        keywords.put("~", NOT);
        keywords.put("↔", IFF);
    }
    */
    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            //we are at the beggining of the next lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
                // Start copy below this for table gen
            case '&':
            case '∧':
            case '·':
                addToken(AND);
                break;
            case '↔':
            case '⇔':
            case '≡':
            case '!':
                addToken(IFF);
                break;
            case '~':
            case '∼':
            case '¬':
                addToken(NOT);
                break;
            case '→':
            case '⇒':
            case '⊃':
                addToken(IF);
                break;
            case '∨':
            case '+':
            case '∥':
                addToken(OR);
                break;
            // end copy for tablr here
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            default:
                if (isAlpha(c)) {
                    addToken(ATOMIC, c);
                } else {
                    PLV.error(line, "Unexpected charecter");
                }
        }
    }

    private boolean match(char expected) {
        if (isAtEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'A' && c <= 'Z');
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
