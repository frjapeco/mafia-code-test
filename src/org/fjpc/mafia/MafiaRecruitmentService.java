package org.fjpc.mafia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MafiaRecruitmentService {

    private GangsterList actives;

    private GangsterList jailed;

    public MafiaRecruitmentService() {
        actives = new GangsterList();
        jailed = new GangsterList();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            actives.add(new Gangster(1L, "A", 1,dateFormat.parse("01-01-1970"), null, new GangsterList()));
            actives.add(new Gangster(2L, "B", 2,dateFormat.parse("01-01-1971"), null, new GangsterList()));
            actives.add(new Gangster(3L, "C", 2,dateFormat.parse("01-01-1972"), null, new GangsterList()));
            actives.add(new Gangster(4L, "D", 3,dateFormat.parse("01-01-1973"), null, new GangsterList()));
            actives.add(new Gangster(5L, "E", 3,dateFormat.parse("01-01-1974"), null, new GangsterList()));
            actives.add(new Gangster(6L, "F", 3,dateFormat.parse("01-01-1975"), null, new GangsterList()));
            actives.add(new Gangster(7L, "G", 3,dateFormat.parse("01-01-1976"), null, new GangsterList()));
            actives.add(new Gangster(8L, "H", 3,dateFormat.parse("01-01-1977"), null, new GangsterList()));
            actives.add(new Gangster(9L, "I", 4,dateFormat.parse("01-01-1978"), null, new GangsterList()));
            actives.add(new Gangster(10L, "J", 5,dateFormat.parse("01-01-1979"), null, new GangsterList()));

            actives.findById(2L).setBoss(actives.findById(1L));
            actives.findById(3L).setBoss(actives.findById(1L));
            actives.findById(4L).setBoss(actives.findById(2L));
            actives.findById(5L).setBoss(actives.findById(2L));
            actives.findById(6L).setBoss(actives.findById(2L));
            actives.findById(7L).setBoss(actives.findById(3L));
            actives.findById(8L).setBoss(actives.findById(3L));
            actives.findById(9L).setBoss(actives.findById(8L));
            actives.findById(10L).setBoss(actives.findById(9L));

            actives.findById(1L).getSubordinates().add(actives.findById(2L));
            actives.findById(1L).getSubordinates().add(actives.findById(3L));
            actives.findById(2L).getSubordinates().add(actives.findById(4L));
            actives.findById(2L).getSubordinates().add(actives.findById(5L));
            actives.findById(2L).getSubordinates().add(actives.findById(6L));
            actives.findById(3L).getSubordinates().add(actives.findById(7L));
            actives.findById(3L).getSubordinates().add(actives.findById(8L));
            actives.findById(8L).getSubordinates().add(actives.findById(9L));
            actives.findById(9L).getSubordinates().add(actives.findById(10L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GangsterList getActives() {
        return actives;
    }

    public GangsterList getJailed() {
        return jailed;
    }

    public void sendToJail(Long gangsterId) {
        Gangster gangster = actives.findById(gangsterId);
        if (gangster == null) {
            return;
        }
        actives.remove(gangster);
        jailed.add(gangster);
        if (actives.size() > 1 && gangster.getSubordinates().size() > 0) {
            GangsterList remainingBosses = actives.findAllByGrade(gangster.getGrade());
            if (remainingBosses.size() > 0) {
                Gangster newBoss = remainingBosses.orderedByExperience().get(0);
                newBoss.getSubordinates().addAll(gangster.getSubordinates());
                gangster.getSubordinates().forEach(subordinate -> subordinate.setBoss(newBoss));
            } else {
                Gangster newBoss = gangster.getSubordinates().orderedByExperience().get(0);
                newBoss.setBoss(gangster.getBoss());
                newBoss.setGrade(gangster.getGrade());
                if (newBoss.getGrade() <= 1) {
                    newBoss.setBoss(null);
                }
                gangster.getSubordinates().remove(newBoss);
                newBoss.getSubordinates().addAll(gangster.getSubordinates());
                gangster.getSubordinates().forEach(subordinate -> subordinate.setBoss(newBoss));
                gangster.getSubordinates().add(newBoss);
            }
        }
    }

    public void releaseFromJail(Long gangsterId) {
        Gangster gangster = jailed.findById(gangsterId);
        jailed.remove(gangster);
        gangster.getSubordinates().forEach(subordinate -> {
            if (subordinate.getBoss() != null) {
                subordinate.getBoss().getSubordinates().remove(subordinate);
            }
            subordinate.setBoss(gangster);
            subordinate.setGrade(gangster.getGrade() + 1);
        });
        if (gangster.getBoss() != null) {
            Long bossId = gangster.getBoss().getId();
            if (actives.findById(bossId) != null) {
                actives.findById(bossId)
                        .getSubordinates()
                        .add(gangster);
            } else {
                Integer grade = gangster.getGrade() - 1;
                while (actives.findAllByGrade(grade).size() == 0 && grade > 0) {
                    grade--;
                }
                Gangster newBoss = null;
                if (grade > 0) {
                    newBoss = actives.findAllByGrade(grade).orderedByExperience().get(0);
                }
                gangster.setBoss(newBoss);
            }
        }
        actives.add(gangster);
    }

}
