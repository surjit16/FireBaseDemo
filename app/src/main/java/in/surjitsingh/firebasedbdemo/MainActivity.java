package in.surjitsingh.firebasedbdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

    static int count = 0;
    private ListView listView;
    private List<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);
    }


    public void submit(View view) {
        String name = ((TextView) findViewById(R.id.name)).getText().toString();
        String uid = ((TextView) findViewById(R.id.uid)).getText().toString();
        String email = ((TextView) findViewById(R.id.email)).getText().toString();

        reference.getRoot().child("user" + count).setValue(new Person(uid, name, email));

        count++;
    }

    public void show(View view) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (childSnapshot.hasChildren()) {
                        Person p = childSnapshot.getValue(Person.class);
                        list.add(p.getEmail() + " " + p.getId() + " " + p.getName() + "\n");
                    } else {
                        list.add(childSnapshot.getValue().toString() + "\n");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void show2(View view) {
        list.clear();
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.hasChildren()) {
                    Person p = dataSnapshot.getValue(Person.class);
                    list.add(p.getEmail() + " " + p.getId() + " " + p.getName() + "\n");
                } else {
                    list.add(dataSnapshot.getValue().toString() + "\n");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Person p = dataSnapshot.getValue(Person.class);
                    list.remove(p.getEmail() + " " + p.getId() + " " + p.getName() + "\n");
                } else {
                    list.remove(dataSnapshot.getValue().toString() + "\n");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
