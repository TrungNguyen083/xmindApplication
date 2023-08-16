package org.example;

import java.util.UUID;

public class Relationship {
    private UUID ID;
    private String title;
    private UUID end1ID; //Tail
    private UUID end2ID; //Head

    private Point controlPoint;
    private Point lineEndPoint;




    public Relationship(UUID end1ID, UUID end2ID) {
        ID = UUID.randomUUID();
        this.end1ID = end1ID;
        this.end2ID = end2ID;
    }

    public UUID getID() {
        return ID;
    }

    public UUID getEnd1ID() {
        return end1ID;
    }

    public UUID getEnd2ID() {
        return end2ID;
    }

    public void setEnd1ID(UUID end1ID) {
        this.end1ID = end1ID;
    }

    public void setEnd2ID(UUID end2ID) {
        this.end2ID = end2ID;
    }
}
