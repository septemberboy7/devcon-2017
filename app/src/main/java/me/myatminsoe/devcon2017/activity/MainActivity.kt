package me.myatminsoe.devcon2017.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import me.myatminsoe.devcon2017.R
import me.myatminsoe.devcon2017.adapter.TopicAdapter
import me.myatminsoe.devcon2017.mdetect.getText
import me.myatminsoe.devcon2017.model.Time
import me.myatminsoe.devcon2017.model.Topic
import org.json.JSONArray


class MainActivity : AppCompatActivity() {

    private val day1Topics = "[{\"time\":\"9:00 - 9:45 AM\",\"topics\":[{\"type\":\"panel\",\"title\":\"Opening Ceremony\",\"room\":\"Conference Hall\",\"speaker\":\"\"}]},{\"time\":\"10:00 - 11:00 AM\",\"topics\":[{\"type\":\"panel\",\"title\":\"Panel Discussion (Bridging Academic with industry)\",\"room\":\"Conference Hall\",\"speaker\":\"Dr. Mie Mie Thet Thwin, Dr. Saw Sandar Aye, Daw Aye Aye Thwin (Moderator U Ravi)\"}]},{\"time\":\"11:15 - 12:00 PM\",\"topics\":[{\"type\":\"panel\",\"title\":\"Essential Soft Skills for IT professionals\",\"room\":\"Conference Hall\",\"speaker\":\"Dr. Aung Tun Thet\"}]},{\"time\":\"12:00 - 1:00 PM\",\"topics\":[{\"type\":\"lunch\",\"title\":\"Lunch\",\"speaker\":\"\",\"room\":\"\"}]},{\"time\":\"1:00 - 2:30 PM\",\"topics\":[{\"type\":\"panel\",\"title\":\"Panel Discussion (နည်းပညာအပြောင်းအလဲနှင့် အချိုးအကွေ့များ)\",\"room\":\"Conference Hall\",\"speaker\":\"U Ravi, U Htoo Myint Naung, U Thar Htet (Moderator U Thaung Su Nyein)\"}]},{\"time\":\"2:35 - 3:20 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"React & Redux: Fundamental Concepts\",\"room\":\"Conference Hall\",\"speaker\":\"Ei Maung\"},{\"type\":\"conference\",\"title\":\"Build a Cloud application with AWS from scratch (Live Coding)\",\"room\":\"205\",\"speaker\":\"Phyo Pyae\"},{\"type\":\"conference\",\"title\":\"How to be an ICT Professionals!\",\"room\":\"203\",\"speaker\":\"Phyo Pyae\"}]},{\"time\":\"3:25 - 4:10 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Code less\",\"room\":\"Conference Hall\",\"speaker\":\"Thar Htet\"},{\"type\":\"conference\",\"title\":\"IoT with Juju and Kubernetes\",\"room\":\"205\",\"speaker\":\"Ko Ko Ye\"},{\"type\":\"conference\",\"title\":\"How I bootstrapped a profitable Micro SaaS startup\",\"room\":\"203\",\"speaker\":\"Swan Htet Aung\"}]}]"
    private val day2Topics = "[{\"time\":\"9:30 - 10:15 AM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Room Persistence Library for Android\",\"speaker\":\"Soe Thiha Naung\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"Importance of data access in modern projects\",\"speaker\":\"Tin Maung Maung Yin\",\"room\":\"205\"},{\"type\":\"conference\",\"title\":\"How to cut the downtime for releasing to ensure your business never stops.\",\"speaker\":\"Pin Maing Soe\",\"room\":\"203\"}]},{\"time\":\"10:20 - 11:05 AM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Buses you don't know\",\"speaker\":\"Htoo Myint Naung\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"Beyond the rich of information\",\"speaker\":\"Aung Soe Moe\",\"room\":\"205\"},{\"type\":\"conference\",\"title\":\"Game Development and Animation Basic\",\"speaker\":\"Swan Thu\",\"room\":\"203\"}]},{\"time\":\"11:10 - 12:00 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Database Management with ArcGIS\",\"speaker\":\"Thandar Phyu\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"Programmer to Software Engineer\",\"speaker\":\"Zeyar Wint\",\"room\":\"205\"},{\"type\":\"conference\",\"title\":\"NodeJs Tips and Tricks\",\"speaker\":\"Thura Myo Nyunt\",\"room\":\"203\"}]},{\"time\":\"12:00 - 1:00 PM\",\"topics\":[{\"type\":\"lunch\",\"title\":\"Lunch\",\"speaker\":\"\",\"room\":\"\"}]},{\"time\":\"1:00 - 1:45 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"End to End Process in making a (digital) Solution\",\"speaker\":\"Aung Pyae Phyo\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"Designing a Microservices\",\"speaker\":\"Khant Naing Set\",\"room\":\"205\"},{\"type\":\"conference\",\"title\":\"Competitive career development\",\"speaker\":\"Kyi Thant Han\",\"room\":\"203\"}]},{\"time\":\"1:50 - 2:15 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Now you see Kotlin\",\"speaker\":\"Myat Min Soe\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"Functional Reactive Programming using RxJs\",\"speaker\":\"Moe Hein Kyaw, Phone Pyae Oo\",\"room\":\"205\"}]},{\"time\":\"2:40 - 3:25 PM\",\"topics\":[{\"type\":\"conference\",\"title\":\"Do's and Don't at your work place \\\"Professional Focus\\\"\",\"speaker\":\"Nyein Oo\",\"room\":\"Conference Hall\"},{\"type\":\"conference\",\"title\":\"The Do & Don't of Android\",\"speaker\":\"Aung Kyaw Paing\",\"room\":\"205\"},{\"type\":\"conference\",\"title\":\"Project development experiences with VR cardboard and 360 photo experience ofMyanmar Culture Heritage App\",\"speaker\":\"Khin Sandar\",\"room\":\"203\"}]},{\"time\":\"3:30 - 5:00 PM\",\"topics\":[{\"type\":\"panel\",\"title\":\"Townhall Meeting & Lucky draw\",\"room\":\"Conference Hall\",\"speaker\":\"\"}]}]"

    private val items = ArrayList<Any>()
    private lateinit var rv: RecyclerView
    private lateinit var adapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById(R.id.rv)
        adapter = TopicAdapter(items)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        parse(0)

        findViewById<View>(R.id.tvDayOne).setOnClickListener({
            set(0)
        })

        findViewById<View>(R.id.tvDayTwo).setOnClickListener({
            set(1)
        })
    }

    private fun set(day: Int) {
        if (day == 0) {
            tvDayOne.setBackgroundResource(R.drawable.background_left_pressed)
            tvDayOne.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            tvDayTwo.setBackgroundResource(R.drawable.background_right)
            tvDayTwo.setTextColor(ContextCompat.getColor(this, R.color.white))
            parse(0)
        } else {
            tvDayTwo.setBackgroundResource(R.drawable.background_right_pressed)
            tvDayTwo.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            tvDayOne.setBackgroundResource(R.drawable.background_left)
            tvDayOne.setTextColor(ContextCompat.getColor(this, R.color.white))
            parse(1)
        }
    }

    private fun parse(day: Int) {
        try {
            items.clear()
            val jsonArray = JSONArray(if (day == 0) day1Topics.getText() else day2Topics.getText())
            for (i in 0 until jsonArray.length()) {
                val jsonObj = jsonArray.getJSONObject(i)
                val jsonTopics = jsonObj.getJSONArray("topics")
                items.add(Time(jsonObj.getString("time")))
                for (ii in 0 until jsonTopics.length()) {
                    val jsonTopic = jsonTopics.getJSONObject(ii)
                    items.add(Topic(jsonTopic.getString("title"), jsonTopic.getString("speaker"), jsonTopic.getString("room"), jsonTopic.getString("type")))
                }
            }
            adapter.notifyDataSetChanged()
            rv.scrollToPosition(0)

        } catch (e: Throwable) {
            Log.e("Error", e.toString())
        }
    }

    public fun github(v: View) {
        val webpage = Uri.parse("https://github.com/septemberboy7/devcon-2017")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intent)
        }
    }
}
