    package com.app.glideadvanced

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

val b = booleanArrayOf(
    false, false, false, false,
    false, false, false, false,
    false, false, false, false
)


val rowCount = 3
val columnCount = 4
var count = 0
var imageRowColumn = arrayOf(
    intArrayOf(R.id.img_column00, R.id.img_column01, R.id.img_column02, R.id.img_column03),
    intArrayOf(R.id.img_column10, R.id.img_column11, R.id.img_column12, R.id.img_column13),
    intArrayOf(R.id.img_column20, R.id.img_column21, R.id.img_column22, R.id.img_column23)
)


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prepareData()

    }


    fun displayData(link: String?) {

        val gridImgDownloaded = findViewById<View>(R.id.downloadedImageLayout)
        gridImgDownloaded.visibility = View.VISIBLE

        var row = 0
        var column = 0


        val index: Int = Random.nextInt(rowCount * columnCount)
        if (b[index] == false || (count >= rowCount * columnCount)) {
            count++
            b[index] = true

            when {
                index in 0.. 3 -> {
                    row = 0
                    column = 3 - index
                }
                index in 4..7 -> {
                    row = 1
                    column = 7 - index
                }
                index >= 8 -> {
                    row = 2
                    column = 11 - index
                }
            }

                 Glide.with(MainActivity@ this).load(link)
                      .into((gridImgDownloaded.findViewById(imageRowColumn[row][column]) as ImageView))



        }

    }

    private fun prepareData(){
        var apiInterface = MyApplication.RetrofitInstance.getInstance().create(ApiInterface::class.java)

        val call = apiInterface.getData()
        call?.enqueue(object : Callback<MutableList<String>> {
            override fun onResponse(call: Call<MutableList<String>>, response: Response<MutableList<String>>) {

                if (response.isSuccessful && response.body() != null) {
                    for (link in response.body() as MutableList<String>)
                    {

                        Log.d("link ",link)
                         displayData(link)
                    }

                }
            }
            override fun onFailure(call: Call<MutableList<String>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MainActivity ",t.message.toString())
            }

        })
    }
}
