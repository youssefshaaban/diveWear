package com.smartzone.diva_wear.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.data.pojo.Category
import com.smartzone.diva_wear.data.pojo.Slider
import com.smartzone.diva_wear.data.repositery.HomeRepository
import com.smartzone.diva_wear.data.utils.Result
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class HomeViewModel(private val homeRepository: HomeRepository,private val schedulerProvider: SchedulerProvider) : BaseViewModel() {
    val categories=MutableLiveData<List<Category>>()
    val slider=MutableLiveData<List<Slider>>()
    fun loadData(){
        setLoading(true)
        add {
            homeRepository.getCategories().with(schedulerProvider)
                .subscribe({
                    if (it is Result.Success){
                        categories.value=it.data
                    }else if (it is Result.Failure){
                        setErrorMessage(it.exception)
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

    fun getSlider(){
        add {
            homeRepository.getSlider().with(schedulerProvider)
                .subscribe({
                    if (it.status){
                        slider.value=it.sliders
                    }else{
                        slider.value= ArrayList()
                    }
                },{
                    setErrorNetwork(it)
                })
        }
    }
}