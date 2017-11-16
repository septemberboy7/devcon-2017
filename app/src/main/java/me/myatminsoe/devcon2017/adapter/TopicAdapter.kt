package me.myatminsoe.devcon2017.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.myatminsoe.devcon2017.R
import me.myatminsoe.devcon2017.model.Time
import me.myatminsoe.devcon2017.model.Topic

/**
 * Created by myatminsoe on 17/11/17.
 */
class TopicAdapter(items: ArrayList<Any>) : RecyclerView.Adapter<TopicAdapter.VH>() {

    private var items = ArrayList<Any>()

    init {
        this.items = items
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        if (item is Time) {
            holder.tvTime.text = item.time
            return
        }
        if (getItemViewType(position) == TYPE_LUNCH) {
            return
        }
        if (item is Topic) {
            val (title, speaker, room) = item
            holder.tvTitle.text = title
            holder.tvSpeaker.text = speaker
            holder.tvRoom.text = room

            if (item.speaker.isEmpty()) {
                holder.tvSpeaker.visibility = View.GONE
            } else {
                holder.tvSpeaker.visibility = View.VISIBLE
            }
            if (item.type == "panel") {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue_100))
            } else {
                holder.itemView.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        if (viewType == TYPE_TIME) {
            return VH(LayoutInflater.from(parent?.context).inflate(R.layout.item_time, parent, false))
        } else if (viewType == TYPE_LUNCH) {
            return VH(LayoutInflater.from(parent?.context).inflate(R.layout.item_launch, parent, false))
        }
        return VH(LayoutInflater.from(parent?.context).inflate(R.layout.item_conf, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        if (item is Time) {
            return TYPE_TIME
        } else if (item is Topic && item.type == "lunch") {
            return TYPE_LUNCH
        }
        return TYPE_TOPIC
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvSpeaker = itemView.findViewById<TextView>(R.id.tvSpekaer)
        val tvRoom = itemView.findViewById<TextView>(R.id.tvRoom)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    }

    companion object {
        private val TYPE_TIME = 0
        private val TYPE_TOPIC = 1
        private val TYPE_LUNCH = 2
    }
}