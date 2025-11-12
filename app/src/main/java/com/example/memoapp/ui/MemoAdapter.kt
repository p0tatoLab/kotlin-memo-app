package com.example.memoapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.databinding.ItemMemoBinding
import com.example.memoapp.model.Memo
import com.example.memoapp.utils.DateUtils

class MemoAdapter(
    private var memos: List<Memo>,
    private val onItemClick: (Memo) -> Unit
) : RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

    /**
     * ViewHolder（各アイテムのビューを保持）
     */
    class MemoViewHolder(
        private val binding: ItemMemoBinding,
        private val onItemClick: (Memo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memo: Memo) {
            binding.textTitle.text = memo.title
            binding.textContent.text = memo.getPreview()
            binding.textDate.text = DateUtils.getRelativeTimeString(memo.updatedAt)

            // クリックイベント
            binding.root.setOnClickListener {
                onItemClick(memo)
            }
        }
    }

    /**
     * ViewHolderを作成
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val binding = ItemMemoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemoViewHolder(binding, onItemClick)
    }

    /**
     * ViewHolderにデータをバインド
     */
    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memos[position])
    }

    /**
     * アイテム数を返す
     */
    override fun getItemCount(): Int = memos.size

    /**
     * データを更新
     */
    fun updateMemos(newMemos: List<Memo>) {
        memos = newMemos
        notifyDataSetChanged()
    }
}