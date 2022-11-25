package com.example.myapplicationnetworks

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class APIService {
    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    private fun getContent(url: String,
                           timeout: Int = 10000): String? {
        var connect: HttpURLConnection? = null
        try{
            val u = URL(url)
            connect = u.openConnection() as HttpURLConnection
            Log.d("check11","4")
            connect.setRequestMethod("GET")
            Log.d("check11","5")
            connect.setRequestProperty("Content-length", "0")
            Log.d("check11","6")
            connect.setUseCaches(false)
            Log.d("check11","7")
            connect.setAllowUserInteraction(false)
            Log.d("check11","8")
            connect.setConnectTimeout(timeout)
            Log.d("check11","9")
            connect.setReadTimeout(timeout)
            Log.d("check11","10")
            connect.connect()
            Log.d("check11","11.1")
            val status: Int = connect.getResponseCode()
            Log.d("check11","$status")
            when(status) {
                200, 201 -> {
                    Log.d("check11","11")
                    val streamReader = InputStreamReader(connect.inputStream)
                    var text = ""
                    streamReader.use {
                        text = it.readText()
                    }
                    return text
                }
            }

        } catch (ex: MalformedURLException){
            Log.d("check12","12")
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Log.d("check12","13")
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } finally {
            if(connect != null){
                try{
                    connect.disconnect()
                } catch(ex: Exception) {
                    Log.d("check12","14")
                    Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
                }
            }
        }
        return null
    }

    fun getPostById(id: Int): Post?{
        Log.d("check1", "${BASE_URL}posts/$id")
        val response = getContent(
            "${BASE_URL}posts/$id",
            1000
        )
        var jsonObject: JSONObject? = null
        try{
            jsonObject = JSONObject(response)

            return Post(
                jsonObject.getInt("id"),
                jsonObject.getInt("userId"),
                jsonObject.getString("title"),
                jsonObject.getString("body")
            )

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}