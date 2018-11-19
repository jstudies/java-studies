package edu.javacourse.synch;

import java.util.ArrayList;
import java.util.List;

public class CommonClass
{
    private List<String> list = new ArrayList<>(20000);


    public synchronized void addItem(String str) {
      try {
          Thread.sleep(100);
          list.add(str);
      } catch(Exception e) {

      }
    }

    public synchronized int getSize() {
        return list.size();
    }
}
