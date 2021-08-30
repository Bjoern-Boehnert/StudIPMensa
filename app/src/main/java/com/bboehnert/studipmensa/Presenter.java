package com.bboehnert.studipmensa;

import com.bboehnert.studipmensa.entity.MensaHelper;
import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.bboehnert.studipmensa.network.OnDataFetched;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Presenter implements Contract.Presenter, OnDataFetched {

    private Contract.View view;
    private String seminarToken;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void connect(String address, String seminarToken) {
        //Todo: Prüfen auf kein Internet?

        this.seminarToken = seminarToken;

        ConnectionHelper.downloadJsonContent(address,
                seminarToken,
                this);
    }

    // Parsen des JSON-Strings
    private List<Contract.Model> getFoodList(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(MensaHelper.parseKeyNames(result));
        return MensaHelper.getFoodList(jsonObject);
    }

    @Override
    public void showProgress() {
        view.showProgressbar();
    }

    @Override
    public void hideProgress() {
        view.hideProgressbar();
    }

    @Override
    public void setData(String result) {
        if (result == null) {
            // Connection Fehler oder Falsche Route
            view.showOnNetworkError("Seminar Cookie ist falsch! Bitte erneut eingeben");
            return;

        } else if (!MensaHelper.hasMensaPlan(result)) {
            view.showNoMensaPlan("Kein Mensaplan für heute!");
            return;
        }

        try {
            List<Contract.Model> foodLocationsList = getFoodList(result);
            view.displayFood(foodLocationsList);

        } catch (JSONException e) {
            view.showJSONParsingError(e.getMessage());
            e.printStackTrace();
        }

        view.saveSeminarCookie(seminarToken);
    }


}
