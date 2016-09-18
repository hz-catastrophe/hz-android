package hz.cathelp;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by leo on 17/09/16.
 */
/*
{
  id: 123, // Can also be null
  name: "Max Muster",
  source: "ios|phone|sms",
  number: "+41791231234", // can also be null
  status: "ok|injured|heavily_injured",
  location: {
    lat: 12.000,
    lng: 13.000
  },
  needs: ["medic", "shelter", "food", "water"],
  needs_status: "open|processing|done",
  skills: ["medic", "food", "water"],
  photos: ["base64 of first photo", "base64 of second photo"]
}
 */
public class User {
    private int id;
    private String name;
    private UserSource source;
    private String number;
    private UserState state;
    private LatLng position;
    private UserNeeds needs;
    private UserNeedsStatus needSatus;
    private List<UserSkill> skills;
    private List<Integer> images;

    public User(int id, String name, UserSource source, String number, UserState state, LatLng position, UserNeeds needs, UserNeedsStatus needSatus, List<UserSkill> skills, List<Integer> images) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.number = number;
        this.state = state;
        this.position = position;
        this.needs = needs;
        this.needSatus = needSatus;
        this.skills = skills;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserSource getSource() {
        return source;
    }

    public String getNumber() {
        return number;
    }

    public UserState getState() {
        return state;
    }

    public LatLng getPosition() {
        return position;
    }

    public UserNeeds getNeeds() {
        return needs;
    }

    public UserNeedsStatus getNeedSatus() {
        return needSatus;
    }

    public List<UserSkill> getSkills() {
        return skills;
    }

    public List<Integer> getImages() {
        return images;
    }

    public BitmapDescriptor getMarkerColor(){
        switch(this.state){
            case HEAVILY_INJURED:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            case INJURED:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            case OK:
                return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            default:
                throw new RuntimeException("Not a valid State");
        }
    }
}
