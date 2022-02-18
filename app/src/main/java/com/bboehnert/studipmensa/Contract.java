package com.bboehnert.studipmensa;

import com.bboehnert.studipmensa.entity.FoodItemDisplayable;

import java.io.Serializable;
import java.util.List;

public interface Contract {

    interface Presenter {
        void connect(String address, String username, String password);

        void storeCredentials(LocalStorage storage);
    }

    interface View {
        void displayFood(List<Model> foodLocationsList);

        void showNoMensaPlan(String message);

        void showJSONParsingError(String message);

        void showOnNetworkError(String message);

        void showProgressbar();

        void hideProgressbar();
    }

    interface Model extends Serializable {
        void setName(String name);

        String getName();

        List<FoodItemDisplayable> getAllFood();
    }
}
