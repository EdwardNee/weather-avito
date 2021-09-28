package com.nieduard.weather_avito

import com.nieduard.weather_avito.model.Lst
import com.nieduard.weather_avito.service.WeatherAPI
import com.nieduard.weather_avito.views.WeatherFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: WeatherFragment)
//    val weatherModel: WeatherModel
//    val coords: Coords
}
//Можно в каждом из классов сделать Inject и тогда не писать Provide

@Module(includes = [NetworkModule::class])
object AppModule {

    @Provides
    fun provideString() = String()

    @Provides
    fun provideInt() = 0

    @Provides
    fun provideDouble() = 0.0

    @Provides
    fun provideList() = ArrayList<Lst>()

//    @Provides
//    fun provideWeatherModel(
//        city: CityX,
//        cnt: Int,
//        cod: String,
//        list: ArrayList<Lst>,
//        message: Double
//    ) = WeatherModel(city, cnt, cod, list, message)

//    @Provides
//    fun provideCityX(
//        coord: Coord,
//        country: String,
//        id: Int,
//        name: String,
//        population: Int,
//        timezone: Int
//    ): CityX = CityX(coord, country, id, name, population, timezone)
//
//    @Provides
//    fun provideCoord(
//        lat: Double,
//        lon: Double
//    ) = Coord(lat, lon)
//
//    @Provides
//    fun provideLst(
//        clouds: Int,
//        deg: Int,
//        dt: Int,
//        feels_like: FeelsLike,
//        gust: Double,
//        humidity: Int,
//        pop: Double,
//        pressure: Int,
//        speed: Double,
//        sunrise: Int,
//        sunset: Int,
//        temp: Temp,
//        weather: List<Weather>
//    ) = Lst(
//        clouds,
//        deg,
//        dt,
//        feels_like,
//        gust,
//        humidity,
//        pop,
//        pressure,
//        speed,
//        sunrise,
//        sunset,
//        temp,
//        weather
//    )
//
//    @Provides
//    fun provideFeelsLike(
//        day: Double,
//        eve: Double,
//        morn: Double,
//        night: Double
//    ) = FeelsLike(day, eve, morn, night)
//
//    @Provides
//    fun provideTemp(
//        day: Double,
//        eve: Double,
//        max: Double,
//        min: Double,
//        morn: Double,
//        night: Double
//    ) = Temp(day, eve, max, min, morn, night)
//
//    @Provides
//    fun provideWeather(
//        description: String,
//        icon: String,
//        id: Int,
//        main: String
//    ) = Weather(description, icon, id, main)
//
//    @Provides
//    fun provideCoords(
//        lat: Double,
//        lon: Double
//    ) = Coords(lat, lon)

}

@Module
object NetworkModule {

    //Must not be in public access. Added to check the task.
    const val API_KEY = "0971c9f9ed8206a186292d41b88e5961"
    const val BASE_URL =
        "https://api.openweathermap.org/data/2.5/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(NetworkModule.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun weatherApi(): WeatherAPI = retrofit.create()
}