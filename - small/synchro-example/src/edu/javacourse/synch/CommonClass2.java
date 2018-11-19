package edu.javacourse.synch;

import java.util.ArrayList;
import java.util.List;

public class CommonClass2
{
    private List<String> list1 = new ArrayList<>();
    private List<String> list2 = new ArrayList<>();

    public void addItem(int i, String str) {
        if (i == 1) {
            synchronized (list1){
            list1.add(str);}
        } else {
            synchronized (list2){
            list2.add(str);}
        }
    }
    public  int getSize(int i) {
        if (i == 1) {
            synchronized (list1){
            return list1.size();}
        } else {
                synchronized (list2){
            return list2.size();}
        }

    }

}
