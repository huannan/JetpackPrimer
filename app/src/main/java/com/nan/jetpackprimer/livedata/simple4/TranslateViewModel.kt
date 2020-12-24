package com.nan.jetpackprimer.livedata.simple4

import androidx.lifecycle.*

class TranslateViewModel : ViewModel() {

    val inputLiveData: MutableLiveData<String> = MutableLiveData()

    val translateResult: LiveData<Result<BaseResult<String>>> = inputLiveData.switchMap { input ->
        liveData {
            val result = try {
                Result.success(TranslateService.getApi().requestTranslateResult(input))
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }
    }

}