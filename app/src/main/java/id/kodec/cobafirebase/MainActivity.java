package id.kodec.cobafirebase;

import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText MovieTitle,SearchTitle;
    Button buttonAdd,buttonSearch;
    Spinner spinnerRatings;
    TextView title, rating;
    private static final String TAG = "MainActivity";

    DatabaseReference databaseMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseMovies = FirebaseDatabase.getInstance().getReference("Movies");

        MovieTitle = (EditText) findViewById(R.id.MovieTitle);
        buttonAdd = (Button) findViewById(R.id.addMovie);
        buttonSearch = (Button) findViewById(R.id.searchButton);
        spinnerRatings = (Spinner) findViewById(R.id.rating);
        SearchTitle = (EditText)findViewById(R.id.searchMovie);
        title = (TextView)findViewById(R.id.movTitle);
        rating = (TextView)findViewById(R.id.movRating);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovie();
            }
        });
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Query query = databaseMovies.orderByChild("movieName").equalTo(SearchTitle.getText().toString());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                            Movies value = userSnapshot.getValue(Movies.class);
                            rating.setText("Rating \t\t\t\t : " + value.getMovieRating() + " of 5 Stars");
                            String title1 = (String) userSnapshot.child("movieName").getValue();
                            title.setText("Movie Title \t: "+title1);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

        //databaseMovies.addValueEventListener(valueEventListener);




    }

    private void addMovie(){
        String title = MovieTitle.getText().toString().trim();
        String ratings = spinnerRatings.getSelectedItem().toString();

        if(!TextUtils.isEmpty(title)){
            String id = databaseMovies.push().getKey();

            Movies movie = new Movies(id, title, ratings);

            databaseMovies.child(id).setValue(movie);

            Toast.makeText(this, "Movie Title added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "You should enter Movie Title", Toast.LENGTH_LONG).show();
        }
    }


}
