package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;
import java.util.StringJoiner;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String concatStringList(List<String> inputList) {
        String concattedList = inputList.get(0);
        for (int i = 1; i < inputList.size(); i++) {
            concattedList.concat(", " + inputList.get(i));
        }
        return concattedList;
    }

    private void populateUI(Sandwich inputSandwich) {
        mBinding.mainNameTv.setText(inputSandwich.getMainName());
        mBinding.originTv.setText(inputSandwich.getPlaceOfOrigin());
        mBinding.descriptionTv.setText(inputSandwich.getDescription());

        Uri imageUri = Uri.parse(inputSandwich.getImage());
        mBinding.imageIv.setImageURI(imageUri);

        String concattedListOfAkaNames = concatStringList(inputSandwich.getAlsoKnownAs());
        mBinding.alsoKnownTv.setText(concattedListOfAkaNames);

        String concattedListOfIngredients = concatStringList(inputSandwich.getIngredients());
        mBinding.ingredientsTv.setText(concattedListOfIngredients);
    }
}
