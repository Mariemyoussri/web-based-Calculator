package com.calculator.backend;

public class Calculator {

    public String addition(double first, double second){
        return String.valueOf((first + second));
    }

    public String subtraction(double first, double second){
        return Double.toString((first - second));
    }

    public String multiply(double first, double second){
        return Double.toString((first * second));
    }
    public String division(double first, double second){
        if(second == 0){
            return "Error";
        }
        return Double.toString((first / second));
    }
    public String square(double first){
        return Double.toString((first * first));
    }

    public String squareRoot(double first){
        if(first < 0){
            return "Error";
        }
        return Double.toString(Math.sqrt(first));
    }

    public String inverse(double first){
        if(first == 0){
            return "Error";
        }
        return Double.toString((1/first));
    }
    
    public String percent(double first){
       return Double.toString((first/100));
    }

}
