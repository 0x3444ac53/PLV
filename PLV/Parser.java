/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftinginterpreters.PLV;

import static com.craftinginterpreters.PLV.TokenType.*;
import java.util.List;

/**
 *
 * @author ellie
 */
public class Parser {

    private static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    Sentence parse() {
        try {
            return sentence();
        } catch(ParseError error){
            return null;
        }
    }

    private Sentence sentence(){
        return iff();
    }
    
    private Sentence iff() {
        Sentence sentence = disjunction();
        
        while (match(IF, IFF)) {
            Token operator =previous();
            Sentence right = disjunction();
            sentence = new Sentence.Binary(sentence, operator, right);
        }
        
        return sentence;
    }
    
    private Sentence disjunction(){
        Sentence sentence = conjunction();
        
        while (match(OR)){
            Token operator = previous();
            Sentence right = conjunction();
            sentence = new Sentence.Binary(sentence, operator, right);
        }
        
        return sentence;
    }
    
    private Sentence conjunction(){
        Sentence sentence = negation();
        
        while (match(AND)){
            Token operator = previous();
            Sentence right= negation();
            sentence = new Sentence.Binary(sentence, operator, right);
        }
        
        return sentence;
    }
    
    private Sentence negation(){
        if(match(NOT)){
            Token operator = previous();
            Sentence right = negation();
            return new Sentence.Unary(operator, right);
        }
        return atomic();
    }
    
    private Sentence atomic(){
        if (match(ATOMIC)){
            return new Sentence.Atomic(previous().literal);
        }
        if(match(LEFT_PAREN)){
            Sentence sentence = sentence();
            consume(RIGHT_PAREN, "Expect ')' after sentence.");
            return new Sentence.Complex(sentence);
        }
        return null;
    }
    
    private boolean match(TokenType... types) {
        for(TokenType type: types){
            if(check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }
    
    private Token consume(TokenType type, String message){
        if (check(type)) return advance();
        throw error(peek(), message);
    }
    
    
    
    private boolean check(TokenType type){
        if (isAtEnd()) return false;
        return peek().type == type;
    }
    
    private Token advance(){
        if (!isAtEnd()) current++;
        return previous();
    }
    
    private boolean isAtEnd() {
        return peek().type == EOF;
    }
    
    private Token peek(){
        return tokens.get(current);
    }
    
    private Token previous() {
        return tokens.get(current - 1);
    }
    
    private ParseError error(Token token, String message) {
        PLV.error(token, message);
        return new ParseError();
    }
}
