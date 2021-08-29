package com.bboehnert.studipmensa.entity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class MensaHelper {

    public final static List<com.bboehnert.studipmensa.entity.FoodLocation> getFoodList(JSONObject json) throws JSONException {

        // Menu als Top-Layer entfernen
        JSONObject menu = json.getJSONObject("menu");
        Gson gson = new Gson();
        List<com.bboehnert.studipmensa.entity.FoodLocation> list = new ArrayList<>();

        Iterator<String> it = menu.keys();
        while (it.hasNext()) {

            String key = it.next();
            menu.getJSONObject(key);
            com.bboehnert.studipmensa.entity.FoodLocation location = gson.fromJson(
                    menu.getJSONObject(key).toString(),
                    com.bboehnert.studipmensa.entity.FoodLocation.class);

            location.setName(getMensaIds().get(key));
            list.add(location);
        }
        return list;
    }

    private static Map<String, String> getMensaIds() {
        Map<String, String> mensaIds = new HashMap<>();

        mensaIds.put("1", "Uhlhornsweg - Pasta & Veggie/Vegan");
        mensaIds.put("2", "Uhlhornsweg - Classic");
        mensaIds.put("3", "Uhlhornsweg - Culinarium");
        mensaIds.put("4", "Wechloy");
        return mensaIds;
    }

    // Notwendig, weil GSON nicht mit Leerzeichen umgehen kann
    public static String parseKeyNames(String result) {

        result = result.replace("Hauptgericht", "mainDish");
        result = result.replace("Beilagen", "extras");
        result = result.replace("Gemüse", "vegetables");
        result = result.replace("Salat", "salad");
        result = result.replace("Dessert", "dessert");
        result = result.replace("Veggie & Vegan", "veggie");

        return result;
    }

    // Wenn die Mensa kein Angebot bietet für den Tag zeigt die API "{"menu":false}"
    public static boolean hasMensaPlan(String result) {
        return !result.equals("{\"menu\":false}");
    }
}
