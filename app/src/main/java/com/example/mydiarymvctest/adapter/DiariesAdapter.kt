package com.example.mydiarymvctest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mydiarymvctest.R
import com.example.mydiarymvctest.model.Diary

/**
 * @author lewisli
 * @date 5/13
 * 日记的adapter
 */
class DiariesAdapter(var diaries: MutableList<Diary>): RecyclerView.Adapter<DiariesAdapter.DiaryViewHolder>() {

    private var listener: OnLongClickListener<Diary>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_diary_item, parent, false)
        val viewHolder = DiaryViewHolder(view)
        viewHolder.initView()
        return viewHolder
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.fill(diaries[position])
        holder.setLongClickListener {
            if (listener == null) {
                Log.e("MyDiaryTest", "listener is null")
                false
            } else {
                listener!!.onLongClick(it, diaries[position])
            }
        }
    }

    override fun getItemCount() = diaries.size

    fun update(dataList: List<Diary>) {
        diaries.clear()
        diaries.addAll(dataList)
        notifyDataSetChanged()
    }

    fun setLongClickListener(longClickListener: OnLongClickListener<Diary>) {
        this.listener = longClickListener
    }

    inner class DiaryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private lateinit var title: TextView
        private lateinit var description: TextView
        private var onLongClickListener: View.OnLongClickListener? = null

        fun setLongClickListener(listener: View.OnLongClickListener) {
            onLongClickListener = listener
        }

        fun initView() {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            itemView.setOnLongClickListener {
                if (onLongClickListener == null) {
                    false
                } else {
                    onLongClickListener!!.onLongClick(it)
                }
            }
        }

        fun fill(data: Diary) {
            title.text = data.title
            description.text = data.description
        }
    }

    interface OnLongClickListener<T> {
        fun onLongClick(v: View, data: T): Boolean
    }

}