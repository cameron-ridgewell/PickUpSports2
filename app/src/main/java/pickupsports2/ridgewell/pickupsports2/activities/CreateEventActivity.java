package pickupsports2.ridgewell.pickupsports2.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.joda.time.MutableDateTime;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.ServerRequest;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.Event;
import ridgewell.pickupsports2.common.LocationProperties;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 1/21/15.
 */
public class CreateEventActivity extends ActionBarActivity {
    final int SUCCESS_CODE = 1;
    private Button post;
    private EditText event_name;

    private TextView displayDate;
    private TextView displayTime;
    private Button editDate;
    private Button editTime;
    private Spinner sportsSpinner;
    private Spinner costSpinner;
    private Spinner privacySpinner;
    private EditText location;
    private EditText maxAttendance;
    private EditText notes;

    private LocationProperties event_loc = null;

    private MutableDateTime date;

    private ServerRequest svreq = ServerRequest.getInstance();

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 888;

    public CreateEventActivity() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_create_event_screen);

        setTitle("Create an Event!");

        event_name = (EditText) findViewById(R.id.eventNameInput);

        sportsSpinner = (Spinner) findViewById(R.id.sport_spinner);
        ArrayAdapter<CharSequence> sportsAdapter = ArrayAdapter.createFromResource(this,
                R.array.sports, android.R.layout.simple_spinner_item);
        sportsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sportsSpinner.setAdapter(sportsAdapter);

        costSpinner = (Spinner) findViewById(R.id.cost_spinner);
        ArrayAdapter<CharSequence> costAdapter = ArrayAdapter.createFromResource(this,
                R.array.cost_options, android.R.layout.simple_spinner_item);
        costAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        costSpinner.setAdapter(costAdapter);

        privacySpinner = (Spinner) findViewById(R.id.privacy_spinner);
        ArrayAdapter<CharSequence> privacyAdapter = ArrayAdapter.createFromResource(this,
                R.array.privacy_options, android.R.layout.simple_spinner_item);
        privacyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        privacySpinner.setAdapter(privacyAdapter);

        //location = (EditText) findViewById(R.id.eventLocationInput);

        maxAttendance = (EditText) findViewById(R.id.eventAttendanceInput);

        displayDate = (TextView) findViewById(R.id.selected_date);

        displayTime = (TextView) findViewById(R.id.selected_time);

        EditText maxAttendance = (EditText) findViewById(R.id.eventAttendanceInput);

        notes = (EditText) findViewById(R.id.eventNotesInput);

        post = (Button) findViewById(R.id.create_event_post_button);

        addCreateEventButtonListener();

        setCurrentDateOnView();
        addEditTimesListener();
        addSetLocationListener();
    }

    // display current date
    public void setCurrentDateOnView() {
        displayDate = (TextView) findViewById(R.id.selected_date);

        date = MutableDateTime.now();

        // set current date into textview
        displayDate.setText(date.toString("MMMM d, yyyy"));

        displayTime.setText(date.toString("h:mm a"));
    }

    public void addEditTimesListener() {

        editDate = (Button) findViewById(R.id.edit_date_button);

        editDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        editTime = (Button) findViewById(R.id.edit_time_button);

        editTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }

    public void addCreateEventButtonListener() {
        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO grab creating user
                //TODO Check for empty fields + Toast
                User user = UserData.getInstance().getThisUser(CreateEventActivity.this);
                try {
                    if (event_loc == null) {
                        throw new NullPointerException("Cannot have an event without location");
                    }
                    Event created_event = new Event(event_name.getText().toString(),
                            sportsSpinner.getSelectedItem().toString(),
                            date.toDateTime(),
                            event_loc,
                            costSpinner.getSelectedItemPosition(),
                            notes.getText().toString(),
                            privacySpinner.getSelectedItemPosition() == 1,
                            Integer.parseInt(maxAttendance.getText().toString()),
                            user.get_id());
                    svreq.addEvent(created_event, CreateEventActivity.this);

                    //TODO this is an empty intent, surely it doesn't need to be passed
                    setResult(SUCCESS_CODE, new Intent());
                    finish();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "One or more of your fields is incomplete", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
    }

    private void addSetLocationListener() {
        Button set_location = (Button) findViewById(R.id.locationInputButton);
        set_location
                .setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            IntentProtocol.launchLocationPicker(CreateEventActivity.this, "");
                                        }
                                    });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
            case TIME_DIALOG_ID:
                // set time picker as current date
                return new TimePickerDialog(this, timePickerListener,
                        date.getHourOfDay(),date.getMinuteOfHour(), false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            date.setYear(selectedYear);
            date.setMonthOfYear(selectedMonth + 1);
            date.setDayOfMonth(selectedDay);

            // set selected date into textview
            displayDate.setText(date.toString("MMMM d, yyyy"));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        // when dialog box is closed, below method will be called.
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            date.setHourOfDay(selectedHour);
            date.setMinuteOfHour(selectedMinute);

            // set selected date into textview
            displayTime.setText(date.toString("h:mm a"));
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.event_loc = IntentProtocol.getLocationPickerResult(requestCode, resultCode, data);
    }
}