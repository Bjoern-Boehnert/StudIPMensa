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
    private final Calendar calendar;
    private final LiveData<List<FoodGroupDisplayable>> mutuableFood;
    private final MutableLiveData<Calendar> mutuableDate;
    private final MutableLiveData<MensaAction> mutuableAction;

    @Inject
    public MensaViewModel(MensaRepository repository) {
        this.repository = repository;
        this.mutuableDate = new MutableLiveData<>();
        this.mutuableAction = new MutableLiveData<>();
        this.calendar = Calendar.getInstance();

        this.mutuableFood = repository.getMensaOfDay();
    }

    public void setMensaMenu() {
        long day = getMensaPlanDay(calendar.getTime());
        repository.setMensaOfDay(day);
    }

    public LiveData<List<FoodGroupDisplayable>> getMensaMenu() {
        return mutuableFood;
    }

    private void setCalendar(int add) {
        calendar.add(Calendar.DATE, add);
        mutuableDate.setValue(calendar);
    }

    public LiveData<Calendar> getCalender() {
        return mutuableDate;
    }

    public void setAction(MensaAction action) {

        switch (action) {
            case GET_NEXT_DAY:
                setCalendar(1);
                break;
            case GET_CURRENT_DAY:
                setCalendar(0);
                break;
            case GET_PREVIOUS_DAY:
                setCalendar(-1);
                break;
            default:
                mutuableAction.setValue(action);
        }

    }

    public LiveData<MensaAction> getAction() {
        return mutuableAction;
    }

    private long getMensaPlanDay(Date date) {
        return date.getTime() / 1000;
    }
}
