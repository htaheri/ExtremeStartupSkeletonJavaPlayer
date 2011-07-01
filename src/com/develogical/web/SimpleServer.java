package com.develogical.web;

import bsh.EvalError;
import bsh.Interpreter;
import org.junit.runner.Result;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SimpleServer implements Container {

    public void handle(Request request, Response response) {
        boolean hadException = false;

        try {
            ProcessRequest(request, response);
        } catch (Exception e) {
            hadException = true;
            System.out.println("An exception occurred: " + e);
        }

        if (hadException) {
            try {
                PrintStream body = response.getPrintStream();
                body.println("Unknown");
                body.close();
            } catch (Exception e) {
                System.out.println("Failed writing response: " + e);
            }
        }

    }

    private void ProcessRequest(Request request, Response response) throws IOException {
        String newRequest = request.getParameter("q");
        System.out.println("Got a new request: " + newRequest);

        if (newRequest.contains("what is your name")) {
            WhatIsYourName(response);

        } else if (newRequest.contains("plus") || newRequest.contains("multiplied") || newRequest.contains("minus")) {
            MathFormula(response, newRequest);
        } else if (newRequest.contains("which of the following numbers is the largest")) {
            System.out.println("which of the followi request ");
            WhatIslargestnumber(response, newRequest);
        } else if (newRequest.contains("which of the following numbers are primes")) {
            WhatIsPrime(response, newRequest);
        } else {
            Unknown(response, newRequest);
        }

    }

    private void Unknown(Response response, String newRequest) throws IOException {
        System.out.println("Q: " + newRequest);
        PrintStream body = response.getPrintStream();
        body.println("Unknown");
        body.close();
    }

    private void WhatIsYourName(Response response) throws IOException {
        PrintStream body = response.getPrintStream();
        body.println("Logical");
        body.close();
    }

    public String ProcessPlus(String question) {
        Integer result = 0;
        String numString = question.substring(18);
        String[] nums = numString.split(" plus ");
        for (int i = 0; i < nums.length; i++) {
            String num = nums[i];
            result = result + Integer.parseInt(num);
        }

        return result.toString();
    }

    private void MathFormula(Response response, String newRequest) throws IOException {
        String result = calculateRequest(newRequest);

        //String result = ProcessPlus(newRequest);

        PrintStream body = response.getPrintStream();
        body.println(result);
        body.close();
    }

    public String calculateRequest(String newRequest) {
        String formula = TranslateFormula(newRequest);
        return parsFunction(formula);
    }

    public String TranslateFormula(String newRequest) {
        String temp = "291e6400: what is";

        String formula = newRequest.substring(temp.length());
        formula = formula.trim();
        //formula.replaceAll("+","plus");
        formula = formula.replace("plus","+");
        
        formula = formula.replace("multiplied by", "*");
        formula = formula.replace("minus", "-");
        return formula;
    }

    public String LargestNumber(Integer[] theNums) {
        Integer largestNum = 0;
        for (int i = 0; i < theNums.length; i++) {
            if (largestNum < theNums[i]) {
                largestNum = theNums[i];
            }
        }
        return largestNum.toString();
    }

    private void WhatIslargestnumber(Response response, String newRequest) throws IOException {
        System.out.println(newRequest);

        try {
            Integer[] theInts = getNumberList(newRequest, "bf4e7f90: which of the following numbers is the largest: ");

            String answer = LargestNumber(theInts);
            System.out.println("Answer: " + answer);

            PrintStream body = response.getPrintStream();
            body.println(answer);
            body.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    private Integer[] getNumberList(String newRequest, String prompt) {
        Integer[] theInts = null;
        try {
            String Numebrs = newRequest.substring(prompt.length());
            System.out.println(Numebrs);
            String[] theNums = Numebrs.split(",");
            theInts = new Integer[theNums.length];

            for (int i = 0; i < theNums.length; i++) {
                theInts[i] = Integer.parseInt(theNums[i].trim());
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return theInts;
    }

    public boolean isPrime(int num) {
        boolean prime = true;
        int limit = (int) Math.sqrt(num);

        for (int i = 2; i <= limit; i++) {
            if (num % i == 0) {
                prime = false;
                break;
            }
        }

        return prime;
    }

    private void WhatIsPrime(Response response, String newRequest) throws IOException {
        Integer[] theInts = getNumberList(newRequest, "0a5df660: which of the following numbers are primes: ");

        for (int i = 0; i < theInts.length; i++) {
            if (isPrime(theInts[i])) {
                PrintStream body = response.getPrintStream();
                body.println(theInts[i]);
                body.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Container container = new SimpleServer();
        Connection connection = new SocketConnection(container);
        SocketAddress address = new InetSocketAddress(8080);
        connection.connect(address);
    }

    public String parsFunction(String formula) {
        String result = "";
        try {
            Interpreter interpreter = new Interpreter();
            result = interpreter.eval("result = " + formula).toString();
        } catch (Exception e) {
            result = "Error";
        }

        return result;
        //System.out.println(interpreter.get("result"));

    }

    public boolean isSquareNumber(int number)
    {
        double temp = Math.pow(number, 0.5);
         int intTemp = (int)temp;


        return intTemp * intTemp == number;

    }

    public boolean isCubeNumber(int number)
    {
        double temp = Math.pow(number, 1.0/3);
        int intTemp = (int)temp;
        return intTemp * intTemp * intTemp == number;

    }
}
