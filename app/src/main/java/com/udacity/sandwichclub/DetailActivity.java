package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get the intent
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        //check the position of the sandwich item from the value that was passed
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        //get the sandwich json string in the position and parse it to a Sandwich object
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //populate the UI using the Sandwich object
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(binding.imageIv);

        setTitle(sandwich.getMainName());
    }


    /**
     * Finish the activity if there is an error in getting Sandwich data
     **/
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate the UI from the parsed JSON string
     *
     * @param sandwich An object of Sandwich class that holds the Sandwich data needed to fill
     *                 in the UI
     **/
    private void populateUI(Sandwich sandwich) {
        //set the main name
        binding.mainNameTv.setText(sandwich.getMainName());

        StringBuilder stringBuilder = new StringBuilder();
        // set the also known as name if it isn't empty else remove them from the view
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            for (String name : sandwich.getAlsoKnownAs()) {
                stringBuilder.append(name).append(", ");
            }
            binding.alsoKnownTv.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
        } else {
            binding.alsoKnownTv.setVisibility(View.GONE);
            binding.labelAlsoKnownAs.setVisibility(View.GONE);
        }

        //set the place of origin if it isn't an empty string else remove the views
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            binding.originTv.setText(sandwich.getPlaceOfOrigin());
        } else {
            binding.labelPlaceOfOrigin.setVisibility(View.GONE);
            binding.originTv.setVisibility(View.GONE);
        }

        //set the decription of the sandwich
        binding.descriptionTv.setText(sandwich.getDescription());

        stringBuilder = new StringBuilder();
        //set the ingredients of the sandwich
        for (String ingredient : sandwich.getIngredients()) {
            stringBuilder.append(ingredient).append(", ");
        }
        binding.ingredientsTv.setText(stringBuilder.substring(0, stringBuilder.length() - 2));
    }
}
