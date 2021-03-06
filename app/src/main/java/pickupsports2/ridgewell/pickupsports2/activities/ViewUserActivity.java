package pickupsports2.ridgewell.pickupsports2.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import pickupsports2.ridgewell.pickupsports2.R;
import pickupsports2.ridgewell.pickupsports2.elements.AttendedEventsDialog;
import pickupsports2.ridgewell.pickupsports2.elements.MultiSelectSpinner;
import pickupsports2.ridgewell.pickupsports2.intents.IntentProtocol;
import pickupsports2.ridgewell.pickupsports2.utilities.UserData;
import ridgewell.pickupsports2.common.User;

/**
 * Created by cameronridgewell on 1/26/15.
 */
public class ViewUserActivity extends ActionBarActivity {
    User user;
    Button invite_button = null;
    AttendedEventsDialog attendedEventsDialog = null;

    public ViewUserActivity() {}

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        this.user = IntentProtocol.getUser(this);

        setTitle(user.getFirstname() + " " + user.getLastname());

        TextView username = (TextView) findViewById(R.id.user_name);
        username.setText(user.getFirstname() + " " + user.getLastname());

        TextView joindate = (TextView) findViewById(R.id.user_join_date);
        joindate.setText("Joined: " + user.getJoinTime().toString("MMMM d, yyyy"));

        TextView user_favorite_sports = (TextView) findViewById(R.id.user_favorite_sports);
        List<String> favorites = user.getFavoriteSports();
        if (favorites.size() > 0) {
            String favorite_sports_list = "| ";
            for (String sport : user.getFavoriteSports()) {
                favorite_sports_list = favorite_sports_list + " " + sport + " |";
            }
            user_favorite_sports.setText(favorite_sports_list);
        } else {
            user_favorite_sports.setText(user.getFirstname() + " doesn't have any favorite sports!");
        }

        TextView user_location = (TextView) findViewById(R.id.user_location);
        user_location.setText(user.getLocationProperties().toString());

        invite_button = (Button) findViewById(R.id.invite_button);
        if (user.get_id().equals(UserData.getInstance().getThisUser(this).get_id())) {
            invite_button.setVisibility(View.GONE);
        }

        setInviteListener();
    }

    private void setInviteListener() {
        invite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendedEventsDialog = new AttendedEventsDialog();
                Bundle bundle = new Bundle();
                bundle.putParcelable("invitee",user);
                attendedEventsDialog.setArguments(bundle);
                attendedEventsDialog.show(getFragmentManager(), "launch attended events");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
