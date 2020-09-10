package test.app.nuegelb.ui.splash

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import test.app.nuegelb.data.repository.ConfigRepository
import test.app.nuegelb.ui.ResultData

class SplashViewModel(private val configRepository: ConfigRepository) : ViewModel() {


    private val shouldRetry = MutableLiveData<Boolean>()

    val fetchConfig = Transformations.switchMap(shouldRetry) {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {

            //Loading
            emit(ResultData.Loading<String>(true))

            try {

                configRepository.getAppConfig()
                emit(ResultData.Success())

            } catch (e: Exception) {
                emit(ResultData.Error<String>(message = e.message ?: ""))
            }
        }
    }

    fun retryData(retry: Boolean) {
        shouldRetry.value = retry
    }
}