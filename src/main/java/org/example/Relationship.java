package org.example;

import java.util.UUID;

public class Relationship{
    private UUID uuid;
    private String title;
    private UUID end1ID;
    private UUID end2ID;


    public Relationship(UUID end1ID, UUID end2ID) {
        uuid = UUID.randomUUID();
        this.end1ID = end1ID;
        this.end2ID = end2ID;
    }
    public Relationship(UUID end1ID, UUID end2ID, String title) {
        uuid = UUID.randomUUID();
        this.title = title;
        this.end1ID = end1ID;
        this.end2ID = end2ID;
    }

    public UUID getUuid() {
        return uuid;
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
