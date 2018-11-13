package org.fjpc.mafia;

import java.util.ArrayList;
import java.util.Comparator;

public class GangsterList extends ArrayList<Gangster> {

    public Gangster findById(Long id) {
        GangsterList results = new GangsterList();
        this.stream()
                .filter(gangster -> gangster.getId().equals(id))
                .forEach(results::add);
        return (results.size() > 0) ? results.get(0) : null;
    }

    public GangsterList findAllByName(String name) {
        GangsterList results = new GangsterList();
        this.stream()
                .filter(gangster -> gangster.getName().equals(name))
                .forEach(results::add);
        return results;
    }

    public GangsterList findAllByGrade(Integer grade) {
        GangsterList results = new GangsterList();
        this.stream()
                .filter(gangster -> gangster.getGrade().equals(grade))
                .forEach(results::add);
        return results;
    }

    public GangsterList orderedByExperience() {
        GangsterList results = new GangsterList();
        this.stream()
                .sorted(Comparator.comparing(Gangster::getExperience))
                .forEach(results::add);
        return results;
    }

    public GangsterList findByBoss(Gangster boss) {
        GangsterList results = new GangsterList();
        this.stream()
                .filter(gangster -> gangster.equals(boss))
                .forEach(results::add);
        return results;
    }

}
