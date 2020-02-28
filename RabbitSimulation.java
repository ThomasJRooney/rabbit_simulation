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
import java.util.Random;
import static java.lang.Math.sqrt;

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
          // repeat simulateDay 365 times
          for(int j = 0; j < 365; j++){
            //update trials
            Integer size = rabbits.size();
            for(int l = 0; l < size; l++){
              // work on one rabbit at a time
              Rabbit r = rabbits.get(l);
              // update every rabbits age
              r.updateAge();

              if(r.getSex() == 'f'){
                if(r.getAge() >= 100){
                  // female case
                  if(r.isBirth()){
                    // have birth
                    Integer baby = r.getLitterSize();
                    Random rand = new Random();
                    for(int p = 0; p < baby; p++){
                      int rand_i = rand.nextInt(2);
                      if(rand_i == 1){
                        addMale(rabbits, data[i]);
                      } else {
                        addFemale(rabbits, data[i]);
                      }
                    }
                    // update rabbits data
                    r.setPregnant(false);
                    r.setBirth(false);
                    r.setBreed(false);
                  }

                  if(r.isPregnant()){
                    r.updateGestationDay();
                    if(r.getGestationDay() == r.getGestationalPeriod()){
                      r.setBirth(true);
                      r.setPregnant(false);
                      r.setBreed(false);
                    }
                  }

                  if(r.isBreed()){
                    r.setPregnant(true);
                    r.setGestational();
                    r.resetGestationDay();
                    r.setBreed(false);
                    r.setBirth(false);
                  } else {
                    if(r.getAge() == 100){
                      r.isBreed();
                    }
                    if(!r.isPregnant() && !r.isBirth()){
                      r.updateNonBreedDay();
                    }
                  }
                }
              }
            }
          }
          System.out.print("Trial " + i + ": " + data[i][0] + " was the final population of rabbits; ");
          System.out.println(data[i][1] + " does, " + data[i][2] + " bucks.");
        }
        double[] avg = getAverage(data);
        double[] std = getStd(data);
        System.out.println("Average number of rabbits: " + String.format("%.3f", avg[0]) + " with standard deviation of " + String.format("%.3f", std[0]) + ".");
        System.out.println("Average number of female rabbits: " + String.format("%.3f", avg[1]) + " with standard deviation of " + String.format("%.3f", std[1]) + ".");
        System.out.println("Average number of male rabbits: " + String.format("%.3f", avg[2]) + " with standard deviation of " + String.format("%.3f", std[2]) + ".\n");
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

    public static double[] getAverage(int[][] arr){
      double rSum = 0;
      double fSum = 0;
      double mSum = 0;
      for(int i = 0; i < 10; i++){
        rSum += arr[i][0];
        fSum += arr[i][1];
        mSum += arr[i][2];
      }
      rSum /= 10;
      fSum /= 10;
      mSum /= 10;
      double[] avg = {rSum, fSum, mSum};
      return(avg);
    }

    public static double[] getStd(int[][] arr){
      double[] avg = getAverage(arr);

      double rStd = 0;
      double fStd = 0;
      double mStd = 0;
      for(int i = 0; i < 10; i++){
        rStd += ((arr[i][0] - avg[0])*(arr[i][0] - avg[0])) / 10;
        fStd += ((arr[i][1] - avg[1])*(arr[i][1] - avg[1])) / 10;
        mStd += ((arr[i][2] - avg[2])*(arr[i][2] - avg[2])) / 10;
      }
      rStd = Math.sqrt(rStd);
      fStd = Math.sqrt(fStd);
      mStd = Math.sqrt(mStd);
      double[] std = {rStd, fStd, mStd};
      return(std);
    }
}
