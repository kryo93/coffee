package org.example;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App
{
    public static void main( String[] args )
    {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        RunAllMachine runAllMachine =  new RunAllMachine();
        executorService.submit(runAllMachine::makeBlackTea);
        executorService.submit(runAllMachine::makeCoffee);
        executorService.submit(runAllMachine::makeGreenTea);
        System.out.println( "Making Drinks!" );
    }
}
