package edu.asupoly.ser422.calc.controller;

@SuppressWarnings("serial")
class CalcException extends Exception {
	CalcException() {};
	CalcException(String msg) { super(msg); };
	CalcException(Throwable t) { super(t); };
}