package org.example;

import java.util.UUID;

public class Relationship {
    private String ID;
    private String title;
    private String end1ID; //Tail
    private String end2ID; //Head

    private Point controlPoint;
    private Point lineEndPoint;




    public Relationship(String end1ID, String end2ID) {
        ID = UUID.randomUUID().toString();
        this.end1ID = end1ID;
        this.end2ID = end2ID;
    }

    public String getID() {
        return ID;
    }

    public String getEnd1ID() {
        return end1ID;
    }

    public String getEnd2ID() {
        return end2ID;
    }

    public void setEnd1ID(String end1ID) {
        this.end1ID = end1ID;
    }

    public void setEnd2ID(String end2ID) {
        this.end2ID = end2ID;
    }
}
