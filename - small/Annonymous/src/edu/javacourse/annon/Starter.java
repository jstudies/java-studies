package edu.javacourse.annon;

// comparing lists using annonymous/inline class

import java.util.*;

public class Starter {
    public static void main(String[] args) {
        List<MyClass> list= new ArrayList<>();

        list.add(new MyClass("1"));
        list.add(new MyClass("5"));
        list.add(new MyClass("3"));
        list.add(new MyClass("4"));
        list.add(new MyClass("12"));
        list.add(new MyClass("56"));
        list.add(new MyClass("3"));
        list.add(new MyClass("4"));




        Collections.sort(list, new Comparator<MyClass>(){
            @Override
                    public int compare(MyClass o1, MyClass o2){
                return o1.getName().compareTo(o2.getName());
            }
        });

        for (MyClass mc: list){
            System.out.println(mc.getName());
        }

        Collections.sort(list, new Comparator<MyClass>() {
            @Override
            public int compare(MyClass o1, MyClass o2) {
                return -o1.getName().compareTo(o2.getName()); // large to small compared list
            }
        });

        for (MyClass mc: list){
            System.out.println(mc.getName());
        }

    }

}
