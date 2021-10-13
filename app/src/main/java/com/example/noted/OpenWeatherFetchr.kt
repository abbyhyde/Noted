
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noted.LocationData
import com.example.noted.LocationResponse
import com.example.noted.api.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NotedApp"

class OpenWeatherFetchr {
    private val retrofitApi: RetrofitApi
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitApi = retrofit.create(RetrofitApi::class.java)
    }

    fun getLocation(lat: String, lon: String): LiveData<LocationData> {
        val responseLiveData: MutableLiveData<LocationData> = MutableLiveData()
        val openWeatherRequest: Call<LocationResponse> = retrofitApi.fetchWeather(lat, lon, "9f9afc03c8b10d743f06b6f2b835f935")
        openWeatherRequest.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                val locationResponse: LocationResponse? = response.body()
                var name : String = locationResponse?.name ?: ""
                var data = LocationData(name)
                responseLiveData.value = data
            }
        })
        return responseLiveData
    }
}