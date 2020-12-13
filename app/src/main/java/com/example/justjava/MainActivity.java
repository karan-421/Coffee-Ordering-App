package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    //This App dispalys the order form for coffe order
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This function is called when button is clicked
    public void submitOrder(View view) {

        //  displayPrice(numberOfCoffees * 5);
        CheckBox whippedCreamCheckBox = findViewById(R.id.Whipped_Cream_CheckedBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_CheckedBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        EditText nameEditText = findViewById(R.id.name_field);
        String name = nameEditText.getText().toString();

        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for "+ name);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }




    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice += 1;
        }
        //if user want whppedCream topping
        if (hasChocolate) {
            basePrice += 2;
        }
        int price = quantity * basePrice;
        return price;
    }


    /**
     * @param name            of customer
     *                        order sumaary
     * @param price           of order
     * @param addWhippedCream whether the user want topping or not
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean hasChocolate, String name) {

        String priceMessage =getString(R.string.order_summary_name , name);
        priceMessage = priceMessage + "\n" +getString(R.string.order_summary_whipped_cream ,addWhippedCream );
        priceMessage = priceMessage + "\n" +getString(R.string.order_summary_chocolate , hasChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity ,quantity);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_price ,NumberFormat.getCurrencyInstance().format(price));
        priceMessage = priceMessage +"\n" +getString(R.string.thank_you);
        return priceMessage;
    }


    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }


    public void increament(View view) {
        if (quantity == 100) {
            //Show the error message as toast
            Toast.makeText(this, "You cant have more than 100", Toast.LENGTH_SHORT).show();
            //Return from this nothing has been left
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }


    public void decreament(View view) {
        if (quantity == 1) {
            //Show the error message as toast
            Toast.makeText(this, "You cant order less than 1", Toast.LENGTH_SHORT).show();
            //Return from this nothing has been lef
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }



}
//ctrl + f for search