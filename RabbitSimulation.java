/**
 * File: RabbitSimulation.java
 * author: Maggie Westerland
 * Date: Spring 2020
 * Homework00
 * Description: This program will simulate 10 years of rabbit production. It is the driver for the Rabbit class.
 */

//package homework.hw00;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RabbitSimulation {

    public static void main(String[] args) throws FileNotFoundException {
      // first check to see if the program was run with the command line argument
      if(args.length < 1) {
        System.out.println("Error: no input file");
	      System.exit(1);
      }

      File f = new File(args[0]);
      // attempt to read data from file output integer arraylist
      ArrayList<Integer[]> seed = readFile(f);

      if(seed == null){
        return;
      }
      // make an array of arraylists of rabbits
      ArrayList<Rabbit> rabbits = new ArrayList<Rabbit>(0);
      int[][] data = new int[10][3];

      for(int k = 0; k < seed.size(); k++){
        //print starting sentence
        System.out.println("Starting with " + seed.get(k)[0] + " doe(s) and " + seed.get(k)[1] + " buck(s):");

        //repeat trial 10 times
        for(int i = 0; i < 10; i++){
          //clear data and rabbits for reuse
          rabbits.clear();
          for(int j = 0; j < 10; j++){
            data[i][0] = 0;
            data[i][1] = 0;
            data[i][2] = 0;
          }
          // seed the rabbit array list with the correct amount of rabbits
          for(int j = 0; j < seed.get(k)[0]; j++){
            addFemale(rabbits, data[i]);
          }
          for(int j = 0; j < seed.get(k)[1]; j++){
            addMale(rabbits, data[i]);
          }
          //repeat simulateDay 365 times
          for(int j = 0; j < 365; j++){
            //update trials
            simulateDay(rabbits, data[i]);
          }
          System.out.print("Trial " + i + ": " + data[i][0] + " was the final population of rabbits; ");
          System.out.println(data[i][1] + " does, " + data[i][2] + " bucks.");
        }
        int[] avg = getAverage(data);
        int[] std = getStd(data);
        System.out.println("Average number of rabbits: " + avg[0] + " with standard deviation of " + std[0] + ".");
        System.out.println("Average number of female rabbits: " + avg[1] + " with standard deviation of " + std[1] + ".");
        System.out.println("Average number of male rabbits: " + avg[2] + " with standard deviation of " + std[2] + ".\n");
      }
    }//main

    /**
     * this method reads in a file and parses the data into femaleCount and maleCount of rabbits.
     * @param f
     * @throws FileNotFoundException
     */

    public static ArrayList<Integer[]> readFile(File f) throws FileNotFoundException {
      Scanner scanner = new Scanner(f);
      ArrayList<Integer[]> seed = new ArrayList<Integer[]>();
      while (scanner.hasNext()) {
          if (scanner.hasNextInt()) {
              Integer[] ints = new Integer[2];
              ints[0] = scanner.nextInt();
              if(scanner.hasNextInt()){
                ints[1] = scanner.nextInt();
                seed.add(ints);
              }
          } else {
              scanner.next();
          }
      }
      return(seed);
    }

    public static void simulateDay(ArrayList<Rabbit> rabbits, int[] data){
      for(int i = 0; i < rabbits.size(); i++){
        // work on one rabbit at a time
        Rabbit r = rabbits.get(i);
        r.updateAge();
        if(r.getSex() == 'f'){
          // female case
          if(r.isPregnant()){
            r.updateGestational();
          }

          if(r.isBreed()){
            r.setPregnant(true);
          } else {
            r.updateBreed();
          }

          if(r.isBirth()){
            r.setPregnant(false);
            r.setBirth(false);
            Integer baby = r.getLitterSize();
            for(int j = 0; j < baby; j++){
              addRabbit(rabbits, data);
            }
          }
        }
      }
    }

    public static void addFemale(ArrayList<Rabbit> rabbits, int[] data){
      Rabbit rabbit = new Rabbit('f');
      rabbits.add(rabbit);
      data[1] += 1;
      data[0] += 1;
    }

    public static void addMale(ArrayList<Rabbit> rabbits, int[] data){
      Rabbit rabbit = new Rabbit('m');
      rabbits.add(rabbit);
      data[2] += 1;
      data[0] += 1;
    }

    public static void addRabbit(ArrayList<Rabbit> rabbits, int[] data){
      Rabbit rabbit = new Rabbit();
      rabbits.add(rabbit);
      if(rabbit.getSex() == 'm'){
        data[2] += 1;
        data[0] += 1;
      } else {
        data[1] += 1;
        data[0] += 1;
      }
    }

    public static int[] getAverage(int[][] arr){
      Integer rSum = 0;
      Integer fSum = 0;
      Integer mSum = 0;
      for(int i = 0; i < 10; i++){
        rSum += arr[i][0];
        fSum += arr[i][1];
        mSum += arr[i][2];
      }
      rSum /= 10;
      fSum /= 10;
      mSum /= 10;
      int[] avg = {rSum, fSum, mSum};
      return(avg);
    }

    public static int[] getStd(int[][] arr){
      Integer rSum = 0;
      Integer fSum = 0;
      Integer mSum = 0;
      for(int i = 0; i < 10; i++){
        rSum += arr[i][0];
        fSum += arr[i][1];
        mSum += arr[i][2];
      }
      rSum /= 10;
      fSum /= 10;
      mSum /= 10;
      int[] avg = {rSum, fSum, mSum};
      return(avg);
    }
}
