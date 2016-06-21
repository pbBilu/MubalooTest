package com.example.pabilicki.mubalootest.Loader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pabilicki.mubalootest.R;

import java.io.InputStream;

/**
 * Created by piotr on 20.06.2016.
 */
public class TeamMemberDetailActivity extends Activity {

    private TextView tvCeo, tvTeamName, tvTeamMemberName, tvTeamMemberRole, tvTeamMemberDescription;
    private ImageView imgTeamLogo, imgProfileImg, imgCptArmband;
    private String ceo, teamName, teamMemberName, teamMemberRole, teamMemberURL, teamMemberDescription;
    private String TAG = "pbBilu.TeamMemberDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_member_details);

        tvCeo = (TextView) findViewById(R.id.tv_ceo_name_details);
        tvTeamName = (TextView) findViewById(R.id.tv_team_name_details);
        tvTeamMemberName = (TextView) findViewById(R.id.tv_team_member_name_details);
        tvTeamMemberRole = (TextView) findViewById(R.id.tv_team_member_role_details);
        tvTeamMemberDescription = (TextView) findViewById(R.id.tv_team_member_description_details);

        imgTeamLogo = (ImageView) findViewById(R.id.img_team_logo_details);
        imgProfileImg = (ImageView) findViewById(R.id.img_profile_details);
        imgCptArmband = (ImageView) findViewById(R.id.img_cpt_armband_details);

        ceo = getIntent().getStringExtra("ceo");
        teamName = getIntent().getStringExtra("teamName");
        teamMemberName = getIntent().getStringExtra("Name");
        teamMemberRole = getIntent().getStringExtra("Role");
//        teamMemberURL = getIntent().getStringExtra("ProfileImageURL");
        teamMemberURL = "http://iconshow.me/media/images/ui/ios7-icons/png/512/social-android-outline.png";
        teamMemberDescription = getIntent().getStringExtra("Description");
        tvCeo.setText(ceo);
        tvTeamName.setText(teamName);
        tvTeamMemberName.setText(teamMemberName);
        tvTeamMemberRole.setText(teamMemberRole);
        tvTeamMemberDescription.setText(teamMemberDescription);

        if (teamName.equals("iOS")) {
            imgTeamLogo.setImageResource(R.drawable.logo_ios);
        } else if (teamName.equals("Android")) {
            imgTeamLogo.setImageResource(R.drawable.logo_android);
        } else if (teamName.equals("Web")) {
            imgTeamLogo.setImageResource(R.drawable.logo_web);
        } else if (teamName.equals("Design")) {
            imgTeamLogo.setImageResource(R.drawable.logo_design);
        } else {
            imgTeamLogo.setImageResource(R.drawable.logo_placeholder);
        }

        if (teamMemberRole.contains("Team Lead")) {
            imgCptArmband.setVisibility(View.VISIBLE);
        }

        if (teamMemberURL.equals("http://developers.mub.lu/resources/profilePlaceholder.png")) {
            imgProfileImg.setImageResource(R.drawable.profile_placeholder);
        } else {
            // TODO: 21.06.2016 downloading a resource
            new DownloadImageTask((ImageView) findViewById(R.id.img_profile_details))
                    .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
        }
    }


    protected class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imgToUpdate;

        public DownloadImageTask(ImageView img) {
            this.imgToUpdate = img;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imgToUpdate.setImageBitmap(result);
        }
    }
}
