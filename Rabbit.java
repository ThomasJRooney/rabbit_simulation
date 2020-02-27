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
    private int gestation;
    private boolean pregnant;
    private int breedPeriod;
    private boolean breed;
    private boolean birth;
    private char sex;
    private int breedCount;

    //method to generate array
    public Rabbit() {
      this.age = 0;
      this.gestationalPeriod = 0;
      this.gestation = 0;
      this.pregnant = false;
      this.breedPeriod = 0;
      this.breed = false;
      this.birth = false;
      Random r = new Random();
      Integer i = r.nextInt(2);
      if(i == 1){
        this.sex = 'm';
      } else {
        this.sex = 'f';
      }
    }

    public Rabbit(char gender) {
      this.age = 0;
      this.gestationalPeriod = 0;
      this.gestation = 0;
      this.pregnant = false;
      this.breedPeriod = 0;
      this.breed = false;
      this.birth = false;
      if(gender == 'm'){
        this.sex = gender;
      } else {
        this.sex = gender;
      }
    }

    public void updateBirth(){
      this.birth = false;
      if(this.gestation == this.gestationalPeriod){
        this.birth = true;
        this.gestation = 0;
      }
    }

    public void updateBreed(){
      this.breedPeriod += 1;
      if(this.breedPeriod == 7){
        this.breed = true;
        this.breedPeriod = 0;
      }
    }

    public void updateGestational(){
      this.gestation += 1;
      if(this.gestation == this.gestationalPeriod){
        this.birth = true;
        this.gestation = 0;
      }
    }

    public char getSex() {
        return sex;
    }

    private void setSex() {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void updateAge() {
        this.age += 1;
    }

    public boolean isPregnant() {
        return pregnant;
    }

    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
        this.gestationalPeriod = getGestational();
        this.gestation = 0;
    }

    public boolean isBreed() {
        return breed;
    }

    public void setBreed(boolean breed) {
        this.breed = breed;
    }

    public boolean isBirth() {
        return birth;
    }

    public void setBirth(boolean birth) {
        this.birth = birth;
    }

    public static int getGestational() {
        Random r = new Random();
        return r.nextInt(32 - 28) + 28;
    }

    public static int getLitterSize() {
        Random r = new Random();
        return r.nextInt(8 - 3) + 3;
    }

}
