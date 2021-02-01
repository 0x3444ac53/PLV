/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.craftinginterpreters.PLV;

/**
 *
 * @author ellie
 */
public class AstPrinter implements Sentence.Visitor<String> {

    String print(Sentence expr) {
        return expr.accept(this);
    }

    /**
     *
     * @param expr
     * @return
     */
    @Override
    public String visitBinarySentence(Sentence.Binary expr) {
        return parenthesize(expr.operator.lexeme,
                expr.left, expr.right);
    }

    /**
     *
     * @param expr
     * @return
     */
    @Override
    public String visitComplexSentence(Sentence.Complex expr) {
        return parenthesize("group", expr.sentence);
    }

    /**
     *
     * @param expr
     * @return
     */
    @Override
    public String visitAtomicSentence(Sentence.Atomic expr) {
        if (expr.value == null) {
            return "nil";
        }
        return expr.value.toString();
    }

    /**
     *
     * @param expr
     * @return
     */
    @Override
    public String visitUnarySentence(Sentence.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }
    
    private String parenthesize(String name, Sentence... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Sentence expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");

    return builder.toString();
  }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        Sentence sentence = new Sentence.Binary(
                new Sentence.Unary(
                        new Token(TokenType.NOT, "~", null, 1), 
                        new Sentence.Atomic("A")),
                new Token(TokenType.AND, "&", null, 1),
                    new Sentence.Atomic("B"));
        System.out.println(new AstPrinter().print(sentence));
    }
}
