package sg.edu.rp.c346.id20037834.c346_sa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class ItemList extends AppCompatActivity {

    ArrayList<String> itemExpireArray;
    EditText etProductName;
    Button btnAdd;
    Button btnDelete;
    Button btnUpdate;
    ListView lvItemExpire;
    Spinner monthPicker;
    ArrayAdapter adapterProductList;
    DatePicker expiryDatePicker;
    int xMonth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        etProductName = findViewById(R.id.etProductName);
        lvItemExpire = findViewById(R.id.lvItemExpire);
        itemExpireArray = new ArrayList<String>();
        monthPicker = findViewById(R.id.spnMonthPicker);
        expiryDatePicker = findViewById(R.id.datePicker);


        itemExpireArray.add("Expires 2021-8-2 Vegetables");
        itemExpireArray.add("Expires 2021-8-6 Chicken");
        itemExpireArray.add("Expires 2021-10-18 Pizza");
        itemExpireArray.add("Expires 2021-10-20 Salmon");
        itemExpireArray.add("Expires 2022-1-2 Meat");

        adapterProductList = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,itemExpireArray);
        lvItemExpire.setAdapter(adapterProductList);
        sortList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                itemExpireArray.add(productName);
                etProductName.setText("");
                etProductName.setHint("Enter Product Name");
                sortList();
                adapterProductList.notifyDataSetChanged();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = etProductName.getText().toString();
                for(int i = 0; i < itemExpireArray.size(); i++){
                    if(itemExpireArray.get(i).contains(productName)){
                        itemExpireArray.remove(itemExpireArray.get(i));
                        dateFilter();
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productName = etProductName.getText().toString();
                for(int i = 0; i < itemExpireArray.size(); i++){
                    //To find the product name
                    if(itemExpireArray.get(i).contains(productName)){
                        //To get the date of the product from splitting it by using substring
                        String product = itemExpireArray.get(i).substring
                                (itemExpireArray.get(i).indexOf(" ",10)+1, itemExpireArray.get(i).length());

                        //To make use of the date picker
                        String newExpiryYear = "Expires " + expiryDatePicker.getYear() + "-" +
                                //To make the integer to string since adding + 1 would make it an integer
                                //instead of a string which would make it a int.
                                Integer.toString(expiryDatePicker.getMonth() + 1) + "-" +
                        expiryDatePicker.getDayOfMonth() + " ";
                        // i for the position and to set the product name and values.
                        itemExpireArray.set(i, newExpiryYear + product);
                        dateFilter();
                    }
                }
            }
        });

        monthPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        xMonth = 1;
                        break;
                    case 1:
                        xMonth = 3;
                        break;
                    case 2:
                        xMonth = 6;
                        break;
                }
                dateFilter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void sortList(){

        ArrayList<String> testing = new ArrayList<>();
        //Reorder the text so product is infront of Date & store the new format in the temporary array.
        for (int i = 0; i < itemExpireArray.size(); i++) {
            String product = itemExpireArray.get(i).substring(itemExpireArray.get(i).indexOf(" ",10)+1, itemExpireArray.get(i).length());

            String expireDate = " "+ itemExpireArray.get(i).substring(0, itemExpireArray.get(i).indexOf(" ",10));
            testing.add(product + expireDate);
        }

        //Sort temporary array and clear the original to set in the sorted array
        Collections.sort(testing);
        itemExpireArray.clear();

        //Re-order the temporary array so that it is back to the original format of expiry date first then product
        //Then add it back to original array
        for (int i = 0; i < testing.size(); i++) {

            String product = testing.get(i).substring(0, testing.get(i).indexOf(" "));
            String expireDate = testing.get(i).substring(testing.get(i).indexOf(" ")+1, testing.get(i).length());
            itemExpireArray.add(expireDate +" "+ product);
        }
    }

    public void dateFilter(){
        //To get current date of singapore since default is UTC
        Date _sortingDate = new Date(new Date().getTime()+28800000);
        ArrayList<String> filteredDateResult = new ArrayList<String>();
        //value of one month
        double oneMonth = 2629746000f;

        for (int i = 0; i < itemExpireArray.size(); i++) {
            //Get ONLY the expiry date as YYYY/MM/DD Format
            String productDate = itemExpireArray.get(i).substring(itemExpireArray.get(i).indexOf(" ")+1, itemExpireArray.get(i).indexOf(" ", 10));

            //This is to intialise the variable
            Date _productDate = null;
            //This is to try to catch any possible error when trying to parse a data.
            try {
                _productDate = new SimpleDateFormat("yyyy-MM-dd").parse(productDate); //Convert the date in string to date dataType
            }
            //This is to catch the possible error.
            catch (Exception e) {
                //The parsing as date got error.
            }

            //Filtering
            //If the expiry date is after the input's date
            if (_productDate.getTime() > _sortingDate.getTime() && _productDate.getTime() < _sortingDate.getTime()+(oneMonth*xMonth)) {
                filteredDateResult.add(itemExpireArray.get(i));
            }
        }
        //This is to show only the filtered list
        adapterProductList = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,filteredDateResult);
        lvItemExpire.setAdapter(adapterProductList);
        adapterProductList.notifyDataSetChanged();
    }
}