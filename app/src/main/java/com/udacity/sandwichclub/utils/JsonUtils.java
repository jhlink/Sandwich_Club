package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String NAME_OBJECT = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_URL = "image";
    private static final String INGREDIENT_LIST = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwichFromJson = new Sandwich();

        try {
            JSONObject rootSandwichJson = (JSONObject) new JSONTokener(json).nextValue();

            // Set sandwich MAIN_NAME and ALSO_KNOWN_AS properties of sandwich object.
            JSONObject sandwichNameJsonObj = rootSandwichJson.getJSONObject(NAME_OBJECT);
            String sandwichMainName = extractStringFromJSONObject(sandwichNameJsonObj, MAIN_NAME);
            sandwichFromJson.setMainName(sandwichMainName);

            List<String> sandwichAlsoKnownAs  = extractStringListFromJSONObject(sandwichNameJsonObj,
                    ALSO_KNOWN_AS);
            sandwichFromJson.setAlsoKnownAs(sandwichAlsoKnownAs);

            // Set sandwich PLACE_OF_ORIGIN property of sandwich object.
            String sandwichPlaceOfOrigin = extractStringFromJSONObject(rootSandwichJson,
                    PLACE_OF_ORIGIN);
            sandwichFromJson.setPlaceOfOrigin(sandwichPlaceOfOrigin);

            // Set sandwich DESCRIPTION property of sandwich object.
            String sandwichDescription = extractStringFromJSONObject(rootSandwichJson,
                    DESCRIPTION);
            sandwichFromJson.setDescription(sandwichDescription);

            // Set sandwich IMAGE_URL property of sandwich object.
            String sandwichImageURL = extractStringFromJSONObject(rootSandwichJson, IMAGE_URL);
            sandwichFromJson.setImage(sandwichImageURL);

            // Set sandwich INGREDIENT_LIST property of sandwich object.
            List<String> sandwichIngredientList = extractStringListFromJSONObject
                    (rootSandwichJson, INGREDIENT_LIST);
            sandwichFromJson.setIngredients(sandwichIngredientList);

            Log.d(TAG, ": JSON Content " + rootSandwichJson.toString());
        } catch (JSONException e) {
            Log.e(TAG + ": Constructor ", e.getMessage());
            e.printStackTrace();
        }

        Log.d(TAG + ": Parsed Result ", sandwichFromJson.toString());
        return sandwichFromJson;
    }

    private static String extractStringFromJSONObject(JSONObject obj, String targetJSONProperty) {
        String jsonPropertyString = "";
        try {
            jsonPropertyString = obj.getString(targetJSONProperty);
        } catch (JSONException e) {
            Log.e(TAG + ": Helpers ", e.getMessage());
            e.printStackTrace();
        }
        return jsonPropertyString;
    }

    private static List<String> extractStringListFromJSONObject(JSONObject obj, String
            targetJSONProperty) {
        List<String> ingredientList = new ArrayList<String>();
        try {
            JSONArray jsonIngredientArray = obj.getJSONArray(targetJSONProperty);

            for (int i = 0; i < jsonIngredientArray.length(); i++) {
                ingredientList.add(jsonIngredientArray.getString(i));
            }
        } catch (JSONException e) {
            Log.e(TAG + ": Helpers ", e.getMessage());
            e.printStackTrace();
        }
        return ingredientList;
    }
}
