package sg.edu.rp.c346.id20037834.c346_sa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    ArrayList<String> itemExpireArray;
    EditText etProductName;
    Button btnAdd;
    ListView lvItemExpire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        btnAdd = findViewById(R.id.btnAdd);
        etProductName = findViewById(R.id.etProductName);
        lvItemExpire = findViewById(R.id.lvItemExpire);
        itemExpireArray = new ArrayList<String>();

        itemExpireArray.add("Expires 2021-8-2 Frozen Vegetables");
        itemExpireArray.add("Expires 2021-8-6 Chicken");
        itemExpireArray.add("Expires 2021-10-18 Frozen Pizza");
        itemExpireArray.add("Expires 2021-10-20 Frozen Salmon");
        itemExpireArray.add("Expires 2022-1-2 Frozen Meat");

        ArrayAdapter adapterProductList = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemExpireArray);
        lvItemExpire.setAdapter(adapterProductList);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                itemExpireArray.add(productName);
                etProductName.setText("");
                etProductName.setHint("Enter Product Name");
                adapterProductList.notifyDataSetChanged();

            }
        });
    }
}