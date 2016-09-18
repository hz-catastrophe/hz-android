package hz.cathelp;

/**
 * Created by leo on 17/09/16.
 */
enum UserSource {
    IOS("ios"),
    Phone("phone"),
    SMS("sms");

    private String name;

    UserSource(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
