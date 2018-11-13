package org.fjpc.mafia;

import java.util.Date;

public class Gangster {

    private Long id;

    private String name;

    private Integer grade;

    private Date experience;

    private Gangster boss;

    private GangsterList subordinates;

    public Gangster(Long id, String name, Integer grade, Date experience, Gangster boss, GangsterList subordinates) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.experience = experience;
        this.boss = boss;
        this.subordinates = subordinates;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getExperience() {
        return experience;
    }

    public Gangster getBoss() {
        return boss;
    }

    public void setBoss(Gangster boss) {
        this.boss = boss;
    }

    public GangsterList getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(GangsterList subordinates) {
        this.subordinates = subordinates;
    }

}
