/*
 * Created by Dmitry Melnikov 2018.
 */

/*
 * Created by Dmitry Melnikov 2018.
 */

package edu.javacourse.stp.domain;

import java.io.Serializable;
import java.util.List;

public class StudentOrder implements Serializable {
    private PersonAdult husband;
    private PersonAdult wife;
    private List<PersonChild> children;
    private int id;

    public StudentOrder(PersonAdult husband, PersonAdult wife, List<PersonChild> children,int id) {
        this.husband = husband;
        this.wife = wife;
        this.children = children;
        this.id=id;
    }

    public PersonAdult getHusband() {
        return husband;
    }

    public PersonAdult getWife() {
        return wife;
    }

    public List<PersonChild> getChildren() {
        return children;
    }

    public int getId() {
        return id;
    }
}
