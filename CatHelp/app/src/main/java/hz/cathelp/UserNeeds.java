package hz.cathelp;

/**
 * Created by leo on 17/09/16.
 * "medic", "shelter", "food", "water"
 */
enum UserNeeds {
    MEDIC("medic"),
    SHELTER("shelter"),
    FOOD("food"),
    WATER("water");

    private String name;

    UserNeeds(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
