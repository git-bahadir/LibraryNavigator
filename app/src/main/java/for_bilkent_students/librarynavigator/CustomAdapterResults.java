package for_bilkent_students.librarynavigator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo PC on 9/23/2016.
 */
public class CustomAdapterResults extends ArrayAdapter<Book> {
    private Context context;
    private ArrayList<Book> results;

    CustomAdapterResults(Context context, ArrayList<Book> results){
        super(context,R.layout.custom_row, results);
        this.context = context;
        this.results = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if(convertView ==null) {
            LayoutInflater bahadirsInflater = LayoutInflater.from(getContext());
            convertView = bahadirsInflater.inflate(R.layout.custom_row, parent, false);
        }
        row = convertView;

        TextView titleText = (TextView) row.findViewById(R.id.titleText);
        TextView authorText = (TextView) row.findViewById(R.id.authorText);
        TextView callNumText = (TextView) row.findViewById(R.id.callText);

        //Set Text
        titleText.setText(results.get(position).getTitle());
        authorText.setText(results.get(position).getAuthor());
        callNumText.setText(results.get(position).getCallNumber());

        return row;
    }
}
