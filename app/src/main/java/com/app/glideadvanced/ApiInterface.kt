package com.app.glideadvanced

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("94b8db3cb5045318c0cbc9d053cdaaf5/raw/f96409866c51d9c5794393bcd597d9fa3bdd4072/images_data")
     fun getData(): Call<MutableList<String>>?

}