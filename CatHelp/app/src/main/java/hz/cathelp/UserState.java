package hz.cathelp;

/**
 * Created by leo on 17/09/16.
 *   status: "ok|injured|heavily_injured",
 */
enum UserState {
    OK("ok"),
    INJURED("injured"),
    HEAVILY_INJURED("heavily_injured");

    private String name;

    UserState(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
