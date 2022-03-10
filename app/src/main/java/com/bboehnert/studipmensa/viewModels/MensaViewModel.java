package com.bboehnert.studipmensa.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.repositories.MensaRepository;
import com.bboehnert.studipmensa.responses.MensaResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MensaViewModel extends ViewModel {

    private final MensaRepository repository;
    private final List<FoodGroupDisplayable> list;
    private final Calendar calendar = Calendar.getInstance();
    private MutableLiveData<Calendar> mutuableDate;

    @Inject
    public MensaViewModel(MensaRepository repository) {
        this.repository = repository;
        list = new ArrayList<>();
    }

    public LiveData<List<FoodGroupDisplayable>> getMensaMenu() {
        long day = getMensaPlanDay(calendar.getTime());
        LiveData<MensaResponse> mutableLiveData = repository.getMensaOfDay(day);
        return Transformations.map(mutableLiveData, input -> {
            if (input == null) {
                return null;
            } else {
                list.clear();
                list.add(input.getResponse().getWechloy());
                list.add(input.getResponse().getUhlhornweg());
                return list;
            }
        });
    }

    public LiveData<Calendar> setCalendar(int add) {
        if(mutuableDate == null){
            mutuableDate = new MutableLiveData<>();
        }
        calendar.add(Calendar.DATE, add);
        mutuableDate.setValue(calendar);
        return mutuableDate;
    }

    private long getMensaPlanDay(Date date) {
        return date.getTime() / 1000;
    }
}
