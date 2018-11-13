package org.fjpc.mafia;

public class Application {

    public static void main(String[] args) {
        MafiaRecruitmentService recruitmentService = new MafiaRecruitmentService();
        recruitmentService.sendToJail(1L);
        recruitmentService.sendToJail(2L);
        recruitmentService.sendToJail(9L);
        recruitmentService.releaseFromJail(1L);
        recruitmentService.releaseFromJail(9L);
        System.out.println();
    }

}
