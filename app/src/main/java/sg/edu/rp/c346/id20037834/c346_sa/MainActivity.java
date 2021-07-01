package sg.edu.rp.c346.id20037834.c346_sa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button toGitHub;
    Button toItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toGitHub = findViewById(R.id.btnToGitHub);
        toItemList = findViewById(R.id.btnToItemList);


        toGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myGitHubWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KyanTay/C346_SA"));
                startActivity(myGitHubWebsite);
            }
        });

        toItemList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itemList = new Intent(MainActivity.this, ItemList.class);
                startActivity(itemList);
            }
        });
    }
}