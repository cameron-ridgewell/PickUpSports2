package ridgewell.pickupsports2.common;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cameronridgewell on 1/16/15.
 */
public class Event implements Parcelable{
    private int event_id;
    private String name;
    private String sport;
    private String timeString;
    private String location;
    private int cost;
    private String notes;
    private boolean isPublic;
    //private List<User> attendees;
    private int maxAttendance;
    //private List<User> waitlist; //queue
    private String creator;

    //largest number of users allowed on the waitlist
    public final int WAITLISTMAX = 10;

    public Event() {}

    public Event(String name, String sport, DateTime time, String location, int cost,
                 String notes, boolean isPublic, int maxAttendance, String creator) {
        this.name = name;
        this.sport = sport;
        this.timeString = time.toString();
        this.location = location;
        this.cost = cost;
        this.notes = notes;
        this.isPublic = isPublic;
        this.maxAttendance = maxAttendance;
        //this.attendees = new ArrayList<User>();
        //this.waitlist = new ArrayList<User>();
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getTime() {
        return new DateTime(timeString);
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTime(DateTime time) {
        this.timeString = time.toString();
    }

    public void setTimeString(String time) {
        this.timeString = time;
    }

    public String getDaysUntil() {
        DateTime now = new DateTime();
        int days = Days.daysBetween(now.withTimeAtStartOfDay(),
                this.getTime().withTimeAtStartOfDay()).getDays();

        if (days == 0) {
            return "(today)";
        } else if (days == 1) {
            return "(tomorrow)";
        } else if (days == -1) {
            return "(yesterday)";
        } else if (days < 0) {
            return "(" + (-1 * days) + " days ago)";
        } else {
            return "(" + days + " days)";
        }
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
/*
    public List<User> getAttendees() {
        return attendees;
    }

    public int getAttendeeCount() {
        return attendees.size();
    }

    public void addAttendee(User user) {
        this.attendees.add(user);
    }

    public void removeAttendee(User user) {
        this.attendees.remove(user);
    }
*/
    public int getMaxAttendance() {
        return maxAttendance;
    }

    public void setMaxAttendance(int maxAttendance) {
        this.maxAttendance = maxAttendance;
    }
/*
    public List<User> getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(List<User> waitlist) {
        this.waitlist = waitlist;
    }

    public User waitlistTop() {
        return waitlist.get(0);
    }

    //no checking for empty waitlist
    public User waitlistDequeue() {
        return waitlist.remove(0);
    }

    public boolean waitlistEnqueue(User user) {
        if (this.waitlistSize() < WAITLISTMAX) {
            waitlist.add(user);
            return true;
        }
        return false;
    }

    public boolean waitlistIsEmpty() {
        return waitlist.size() == 0;
    }

    public int waitlistSize() {
        return waitlist.size();
    }
*/
    public String getCreator() {
        return creator;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(sport);
        out.writeString(timeString);
        out.writeString(location);
        out.writeInt(cost);
        out.writeString(notes);
        out.writeInt(isPublic ? 1 : 0);
        //out.writeTypedList(attendees);
        out.writeInt(maxAttendance);
        //out.writeTypedList(waitlist);
        out.writeString(creator);
    }

    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in) {
        name = in.readString();
        sport = in.readString();
        timeString = in.readString();
        location = in.readString();
        cost = in.readInt();
        notes = in.readString();
        isPublic = in.readInt() == 1;
        //attendees = in.createTypedArrayList(User.CREATOR);
        maxAttendance = in.readInt();
        //waitlist = in.createTypedArrayList(User.CREATOR);
        creator = in.readString();
    }
}
