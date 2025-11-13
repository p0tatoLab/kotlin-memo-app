package com.example.memoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.databinding.ItemMemoBinding
import com.example.memoapp.model.Memo
import com.example.memoapp.utils.DateUtils

class MemoAdapter(
    private var memos: List<Memo>,
    private val onItemClick: (Memo) -> Unit
) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    private var lastPosition = -1

    class MemoViewHolder(
        private val binding: ItemMemoBinding,
        private val onItemClick: (Memo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memo: Memo) {
            binding.textTitle.text = memo.title
            binding.textContent.text = memo.getPreview()
            binding.textDate.text = DateUtils.getRelativeTimeString(memo.updatedAt)

            binding.root.setOnClickListener {
                onItemClick(memo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemoViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memos[position])

        // アニメーション（初回表示時のみ）
        val adapterPosition = holder.bindingAdapterPosition
        if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition > lastPosition) {
            val animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                android.R.anim.slide_in_left
            )
            holder.itemView.startAnimation(animation)
            lastPosition = adapterPosition
        }
    }

    override fun getItemCount(): Int = memos.size

    fun updateMemos(newMemos: List<Memo>) {
        memos = newMemos
        lastPosition = -1  // アニメーションをリセット
        notifyDataSetChanged()
    }
}