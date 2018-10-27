package com.community.jboss.visitingcard.VisitingCard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.community.jboss.visitingcard.R;

public class ViewVisitingCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visiting_card);

        // TODO: Align FAB to Bottom Right and replace it's icon with a SAVE icon
        // TODO: On Click on FAB should give out an implicit intent to Contacts app and the intent should contain Name, Email and Phone Number
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "And that wraps-up the flow.!", Snackbar.LENGTH_LONG).show();
            }
        });

        //TODO: Add ImageView and a number of TextViews to display Selected Visiting Card Information.
    }

    public void onClick(View view) {
        String text = ((TextView) view).getText().toString().trim();
        String url;
        switch (view.getId()){
            case R.id.tvGithub:
                url = getString(R.string.viewcard_github) + text;
                break;
            case R.id.tvLinkedin:
                url = getString(R.string.viewcard_linkedin) + text;
                break;
            case R.id.tvTwitter:
                url = getString(R.string.viewcard_twitter) + text;
                break;
            default:
                return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
