package for_bilkent_students.librarynavigator;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends AppCompatActivity {
    private static EditText TitleInput;
    private static EditText AuthorInput;
    String title = "";
    String author = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TitleInput = (EditText) findViewById(R.id.searchForBook);
        AuthorInput = (EditText) findViewById(R.id.searchForAuthor);
       // Already made a onClick in layout file so no need for button
        // final Button button = (Button) findViewById(R.id.searchForBookbutton);

    }


    public void onClick(View view) {
        if (!isOnline()) { //Check internet
            Toast popup = Toast.makeText(SearchActivity.this, "No internet",
                    Toast.LENGTH_SHORT);
            popup.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
            popup.show();
            return;
        }
            Intent i = new Intent(this, SearchResults.class);
            author = AuthorInput.getText().toString();
            title = TitleInput.getText().toString();
            if ((title == null && author == null) || (title.equals("")&& author.equals(""))) {
                Toast popup = Toast.makeText(SearchActivity.this, "Please enter a string to at least one text space",
                        Toast.LENGTH_SHORT);
                popup.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
                popup.show();
            } else {
                i.putExtra("searchURL", makeURL());

                Toast popup2 = Toast.makeText(SearchActivity.this, "Done with URL making",
                        Toast.LENGTH_SHORT);
                popup2.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
                popup2.show();
                startActivity(i);
            }
        }


    public String makeURL() {
        String urlFirstPart = "http://librarycatalog.bilkent.edu.tr/client/university/search/results?qu=";
        String urlTitle = "";
        String urlAuthor = "";


        if(title != null) {
            urlTitle = "&qu=TITLE%3D";
            for(int i = 0; title.length() > i; i++ ) {
                if(title.charAt(i) == ' ')
                    urlTitle += "+";
                else
                    urlTitle += title.charAt(i);
            }
            urlTitle += "+";  //in the end of title there has to be this for URL to work
        }

        if(author != null) {
            urlAuthor = "&qu=AUTHOR%3D";
            for(int i = 0; author.length() > i; i++ ) {
                if(author.charAt(i) == ' ')
                    urlAuthor += "+";
                else
                    urlAuthor += author.charAt(i);
            }
            urlAuthor += "+";  //in the end of title there has to be this for URL to work
        }

        return urlFirstPart + urlTitle + urlAuthor +"&rw=0&lm=UNIVERSITY";
    }

    public boolean isOnline() { //Check if device is online requires access internet state
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
