package com.craftinginterpreters.PLV;

import java.util.List;

abstract class Sentence {
  interface Visitor<R> {
    R visitComplexSentence(Complex sentence);
    R visitAtomicSentence(Atomic sentence);
    R visitBinarySentence(Binary sentence);
    R visitUnarySentence(Unary sentence);
  }
  static class Complex extends Sentence {
    Complex(Sentence sentence) {
      this.sentence = sentence;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitComplexSentence(this);
    }

    final Sentence sentence;
  }
  static class Atomic extends Sentence {
    Atomic(Object value) {
      this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitAtomicSentence(this);
    }

    final Object value;
  }
  static class Binary extends Sentence {
    Binary(Sentence left, Token operator, Sentence right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinarySentence(this);
    }

    final Sentence left;
    final Token operator;
    final Sentence right;
  }
  static class Unary extends Sentence {
    Unary(Token operator, Sentence right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnarySentence(this);
    }

    final Token operator;
    final Sentence right;
  }

  abstract <R> R accept(Visitor<R> visitor);
}
