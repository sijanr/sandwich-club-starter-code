package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * Parse the JSON string into a Sandwich object
     * @param json JSON string to be parsed.
     * @return Sandwich object created using parsing the json string or null if there's an error
     *         parsing the string.
     * **/
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        if (json != null && json.length() > 0) {
            try {
                //get the top JSON
                JSONObject topObject = new JSONObject(json);

                //get the names
                JSONObject name = topObject.getJSONObject("name");
                String mainName = name.getString("mainName");
                List<String> alsoKnownAs = new ArrayList<>();

                JSONArray namesArray = name.getJSONArray("alsoKnownAs");
                for (int i = 0; i < namesArray.length(); i++) {
                    alsoKnownAs.add(namesArray.getString(i));
                }

                //get the place of origin of the sandwich
                String placeOfOrigin = topObject.getString("placeOfOrigin");

                //get the description
                String description = topObject.getString("description");

                //get the image URL
                String image = topObject.getString("image");

                //get the ingredients of the sandwich
                JSONArray ingredientsArray = topObject.getJSONArray("ingredients");
                List<String> ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    ingredients.add(ingredientsArray.getString(i));
                }

                sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                        description, image, ingredients);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sandwich;
    }
}
