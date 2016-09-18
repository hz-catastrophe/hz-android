package hz.cathelp;

/**
 * Created by leo on 17/09/16.
 */
enum UserNeedsStatus {
    OPEN("open"),
    PROCESSING("processing"),
    DONE("done");

    private String name;

    UserNeedsStatus(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
