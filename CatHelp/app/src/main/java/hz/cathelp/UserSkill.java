package hz.cathelp;

/**
 * Created by leo on 17/09/16.
 */
enum UserSkill {
//    medic", "food", "water"
    MEDIC("medic"),
    FOOD("food"),
    WATER("water");

    private String name;

    UserSkill(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
