/**
 * File: Rabbit.java
 * author: Maggie Westerland
 * Date: Spring 2020
 * Homework00
 * Description: This program will simulate 10 years of rabbit production.
 */

//package homework.hw00;

import java.util.Random;

public class Rabbit {
    private int age;
    private int gestationalPeriod;
    private int gestationDay;
    private int nonBreedDay;
    private boolean pregnant;
    private boolean breed;
    private boolean birth;
    private char sex;

    public Rabbit(char gender) {
      this.age = 0;
      this.gestationalPeriod = 0;
      this.gestationDay = 0;
      this.nonBreedDay = 0;
      this.pregnant = false;
      this.breed = false;
      this.birth = false;
      if(gender == 'm'){
        this.sex = gender;
      } else {
        this.sex = gender;
      }
    }

    public int getAge(){
      return this.age;
    }

    public char getSex() {
        return this.sex;
    }

    public void updateAge() {
        this.age += 1;
    }

    public boolean isPregnant() {
        return this.pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
    }

    public void updateNonBreedDay(){
      this.nonBreedDay += 1;
      if(this.nonBreedDay == 7){
        this.setBreed(true);
        this.nonBreedDay = 0;
      }
    }

    public boolean isBreed() {
        return this.breed;
    }

    public void setBreed(boolean breed) {
        this.breed = breed;
    }

    public boolean isBirth() {
        return this.birth;
    }

    public void setBirth(boolean birth) {
        this.birth = birth;
    }

    public void updateGestationDay(){
      this.gestationDay += 1;
    }

    public int getGestationDay() {
      return this.gestationDay;
    }

    public void resetGestationDay() {
      this.gestationDay = 0;
    }

    public static int getGestational() {
        Random r = new Random();
        return r.nextInt(32 - 28) + 28;
    }

    public int getGestationalPeriod() {
      return this.gestationalPeriod;
    }

    public void setGestational() {
      this.gestationalPeriod = this.getGestational();
    }

    public static int getLitterSize() {
        Random r = new Random();
        return r.nextInt(8 - 3) + 3;
    }

}
