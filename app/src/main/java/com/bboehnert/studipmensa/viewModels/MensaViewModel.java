package com.bboehnert.studipmensa.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.bboehnert.studipmensa.MensaAction;
import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.repositories.MensaRepository;
import com.bboehnert.studipmensa.responses.MensaResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MensaViewModel extends ViewModel {

    private final MensaRepository repository;
    private final List<FoodGroupDisplayable> list;
    private final Calendar calendar;
    private MutableLiveData<Calendar> mutuableDate;
    private MutableLiveData<MensaAction> mutuableAction;

    @Inject
    public MensaViewModel(MensaRepository repository) {
        this.repository = repository;
        this.list = new ArrayList<>();
        this.mutuableDate = new MutableLiveData<>();
        this.mutuableAction = new MutableLiveData<>();
        this.calendar = Calendar.getInstance();
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

    public void setCalendar(int add) {
        calendar.add(Calendar.DATE, add);
        mutuableDate.setValue(calendar);
    }

    public LiveData<Calendar> getCalender() {
        return mutuableDate;
    }

    public void setAction(MensaAction action) {
        mutuableAction.setValue(action);
    }

    public LiveData<MensaAction> getAction() {
        return mutuableAction;
    }

    private long getMensaPlanDay(Date date) {
        return date.getTime() / 1000;
    }
}
