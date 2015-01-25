package pickupsports2.ridgewell.pickupsports2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ridgewell.pickupsports2.common.*;

import static pickupsports2.ridgewell.pickupsports2.R.layout.view_event_screen;

/**
 * Created by cameronridgewell on 1/22/15.
 */
public class View_Event_Screen extends Activity {
    Event event;
    Context myContext;

    public View_Event_Screen() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event_screen);

        Bundle extras = getIntent().getExtras();
        event = extras.getParcelable("viewable_event");

        //String[] event_data = intent.getStringArrayExtra("viewable_event");

        TextView viewItemTextTitle = (TextView) findViewById(R.id.view_event_title);
        viewItemTextTitle.setText(event.getName());

        TextView viewItemTextCreator = (TextView) findViewById(R.id.view_event_creator);
        viewItemTextCreator.setText("Created by " + event.getCreator().getUsername());

        TextView viewItemTextSport = (TextView) findViewById(R.id.event_sport_heading);
        viewItemTextSport.setText(event.getSport().getSportName());

        TextView viewItemTextLocation = (TextView) findViewById(R.id.event_location_heading);
        viewItemTextLocation.setText(event.getLocation().getLocation());

        TextView viewItemTextTime = (TextView) findViewById(R.id.event_time_heading);
        viewItemTextTime.setText(event.getTime().toString() + " " + event.getDaysUntil());


    }
}
