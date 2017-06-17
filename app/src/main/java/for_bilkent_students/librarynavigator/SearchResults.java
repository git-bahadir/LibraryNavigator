package for_bilkent_students.librarynavigator;

/* PRoblems:
1) Need to say searching
2) second page not showing (... Results found source code, shows 12 results per page
3) Need threading too much time lost
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.R.attr.author;


public class SearchResults extends Activity {
    String searchURL;
    static int row = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Button nextPage = (Button) findViewById(R.id.nextbutton);


        Button prevPage = (Button) findViewById(R.id.previousbutton);
     /*   prevPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tv.setText(months[rand.nextInt(12)]);
                tv.setTextColor(Color.rgb(rand.nextInt(255)+1, rand.nextInt(255)+1, rand.nextInt(255)+1));
            }
        });
        */

        Bundle searchData = getIntent().getExtras();

        Toast popup = Toast.makeText(SearchResults.this, "Started REsults",
                Toast.LENGTH_SHORT);
        popup.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0 , 0);
        popup.show();

        searchURL = searchData.getString("searchURL");
        final HTMLReader results = new HTMLReader(searchURL);

         for(int i = 0; i < results.getTitles().size(); i++)
            results.books.add(new Book(results.getTitles().get(i), results.getAuthor().get(i),results.getShelfNum().get(i)));
        // results.getAuthor().get(i), )

        if(results.getTitles().size() == 0) {
            Toast.makeText(SearchResults.this,"Searched book does not exist in library" , Toast.LENGTH_LONG).show();
        }

         ListView resultsView = (ListView) findViewById(R.id.listViewForResults);
        ListAdapter bahadirsAdapter = new CustomAdapterResults(this,results.books);
        resultsView.setAdapter(bahadirsAdapter);

        nextPage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   Bundle bundle = new Bundle();
                bundle.putString("searchURL", makeURL(row));
                Toast.makeText(SearchResults.this,makeURL(row) , Toast.LENGTH_LONG).show();
                row+=12;
                onCreate(bundle);
                /*
                HTMLReader newPage = new HTMLReader(searchURL);
                for(int i = 0; i < newPage.getTitles().size(); i++)
                    newPage.books.add(new Book(newPage.getTitles().get(i), newPage.getAuthor().get(i),newPage.getShelfNum().get(i)));
                ListAdapter newAdapter = new CustomAdapterResults(SearchResults.class,newPage.books);
                */
            }
        });


        resultsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // diğer sayfaya geçmesiçin ayarla
               // String food = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(SearchResults.this,results.books.get(position).getCallNumber() +"sdfsdfsdfsdf" , Toast.LENGTH_SHORT).show(); //implement indent
                Intent i = new Intent(getBaseContext(), FloorView.class);
                i.putExtra("callNumber", results.books.get(position).getCallNumber());
                startActivity(i);

            }
        });
        Toast.makeText(SearchResults.this,results.getShelfNum().size() + " title count    " + results.getTitles().size() + " shelf " + results.getTitles().get(results.getTitles().size() -1), Toast.LENGTH_LONG).show();
    }
    public String makeURL(int row) {
        int index = searchURL.length() -15; // part of the link where row starts
        while(searchURL.charAt(index) == '&') {
            index--;
        }
        String newURL = searchURL.substring(0,index) + "rw=" + row + searchURL.substring(searchURL.length() - 14);
        return newURL;
    }

    public class HTMLReader {

        // PROPERTIES
        private String input = "";
        private String pageContents;
        private ArrayList<String> authors;
        private ArrayList<String> shelves;
        private ArrayList<String> titles;
        private ArrayList<String> imageURLs;
        ArrayList<Book> books = new ArrayList<>();

        public HTMLReader(String input) {
        this.input = input;
        pageContents = getWebsite(input);
        authors = new ArrayList<>();
        shelves = new ArrayList<>();
        titles = new ArrayList<>();
        imageURLs = new ArrayList<>();
        getEverything();

    }

    public String getURL() {
        return input;
    }

    public void getEverything() {
        String titleCall;
        String result = "";
        String temp = "k0\" title=\"";
        int bookCount = 0;
         String eachBook = "results_bio";
        final String RESULT_COUNT = "Toolbar_num_results\">";
        final String NO_RESULT = "No results";
        int countTitles = 0;
        int pageNumbers = 0;
        int authorCount = 0;
        int callCount = 0;
        String pageNumbersTemp = "";
        final String PAGE_NUMBER = "\"LastPagetop\" title=\"";
        for (int i = 0; i + 25 < pageContents.length(); i++) {
            if(pageContents.substring(i, i + eachBook.length() + String.valueOf(bookCount).length()).equals(eachBook + bookCount)) {
                authors.add(bookCount, "");
                shelves.add(bookCount, "");
                titles.add(bookCount, "");
                ++bookCount;

            }
            if( pageNumbers == 0 && pageContents.substring(i, i+ PAGE_NUMBER.length()).equals(PAGE_NUMBER)) {
                i = i + PAGE_NUMBER.length() + 1;
                while (pageContents.charAt(i) != '"') {
                    pageNumbersTemp += pageContents.charAt(i);
                    i++;
                }

                try {
                    pageNumbers = Integer.parseInt(pageNumbersTemp);
                } catch(NumberFormatException nfe) {
                    // Handle parse error. Add log
                }
            }

            if(pageContents.substring(i,i+ 10).equals(NO_RESULT)) {
                return;
            }
            if (pageContents.substring(i, i + 24).equals("Me INITIAL_AUTHOR_SRCH\">")) {
                i = i + 25;
                while (pageContents.charAt(i) != '<') {
                    result += pageContents.charAt(i);
                    i++;
                }
                authors.set(bookCount -1, result);
             //   authors.set(0, "I love trains");
                result = "";
            }
            if (pageContents.substring(i, i + 25).equals("Me PREFERRED_CALLNUMBER\">")) {
                i = i + 26;
                while (pageContents.charAt(i) != '<') {
                    result += pageContents.charAt(i);
                    i++;
                }
                shelves.set(bookCount -1,result);
                result = "";
            }
            if (pageContents.substring(i, i + temp.length()).equals(temp)) { //only prnts english characters problemssss her şeyi 11 yap düzelmezse
               // Toast.makeText(SearchResults.this,"result sonuçlarsdsds", Toast.LENGTH_SHORT).show();
                i = i + temp.length();


                while (pageContents.charAt(i) != '"') {
                    result += pageContents.charAt(i++);
                }
                titles.set(countTitles,result);
                result = "";
                ++countTitles;
                titleCall = "k" + countTitles;
                titleCall += "\" title=\"";
                temp = titleCall;

            }

            if (pageContents.substring(i, i + 21).equals(RESULT_COUNT)) {
              i += 22 ;
                while(pageContents.charAt(i) != ' ') {
                    result += pageContents.charAt(i);
                    i++;
                }
                TextView pageCount = (TextView) findViewById(R.id.textView2);
                pageCount.setText(result + " Results");
               // Toast.makeText(SearchResults.this,pageContents.length()  + "sonuçlar", Toast.LENGTH_LONG).show();
                result = "";
            }

        }
// Toast.makeText(SearchResults.this,shelves.get(shelves.size() -1) + "title count", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<String> getAuthor() { // returns all the authors listed in a page
        return authors;
    }

    public ArrayList<String> getShelfNum() {
        return shelves;
    }

    public String getSearchebleShelfNum() { // will return the shelf number for the pathfinder
        String result = "";
        return result;

    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public String getWebsite(String website) {
        String resString = "";
        // StrictMode.ThreadPolicy policy = new StrictMode().ThreadPolicy.Builder().permitAll().build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(website);
        try {
            HttpResponse response;
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            resString = sb.toString();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(SearchResults.this, "Eroor", Toast.LENGTH_SHORT).show();
        }
        return resString;

    }
}

}


