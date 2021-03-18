package com.calculator.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping
public class BackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
    Calculator calculator;

    public BackendApplication(){
        calculator = new Calculator();
    }


    @GetMapping("/calculate")
    public String calculateExpression(@RequestParam(name="firstOp") String firstOp, @RequestParam(name = "secondOp") String secondOp, @RequestParam(name = "operator") String operator){
        double first = Double.valueOf(firstOp);
        double second = Double.valueOf(secondOp);
        String result = "";
        if(operator.equals("+")){
            result = calculator.addition(first, second);
        }else if(operator.equals("-")){
            result = calculator.subtraction(first, second);
        }else if(operator.equals("x")){
            result = calculator.multiply(first, second);
        }else if(operator.equals("÷")){
            result = calculator.division(first, second);
        }
        return result;
    }

    @GetMapping("/unaryOp")
    public String unaryOperation(@RequestParam(name="firstOp") String firstOp, @RequestParam(name = "operator") String operator){
        double first = Double.valueOf(firstOp);
        String result = "";
        if(operator.equals("square")){
            result = calculator.square(first);
        }else if(operator.equals("root")){
            result = calculator.squareRoot(first);
        }else if(operator.equals("inverse")){
            result = calculator.inverse(first);
        }else if(operator.equals("percent")){
            result = calculator.percent(first);
        }
        return result;
    }
}
