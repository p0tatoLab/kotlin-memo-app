package com.example.memoapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.memoapp.model.Memo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class MemoRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()
    private var nextId: Long = 1L

    companion object {
        private const val PREFS_NAME = "memo_prefs"
        private const val KEY_MEMOS = "memos"
        private const val KEY_NEXT_ID = "next_id"
    }

    init {
        // 次のIDを読み込み
        nextId = sharedPreferences.getLong(KEY_NEXT_ID, 1L)
    }

    /**
     * すべてのメモを取得
     */
    fun getAllMemos(): List<Memo> {
        val json = sharedPreferences.getString(KEY_MEMOS, null) ?: return emptyList()
        val type = object : TypeToken<List<Memo>>() {}.type
        return gson.fromJson(json, type)
    }

    /**
     * IDでメモを取得
     */
    fun getMemoById(id: Long): Memo? {
        return getAllMemos().find { it.id == id }
    }

    /**
     * メモを追加
     */
    fun addMemo(title: String, content: String): Memo {
        val memos = getAllMemos().toMutableList()
        val newMemo = Memo(
            id = nextId++,
            title = title,
            content = content
        )
        memos.add(newMemo)
        saveMemos(memos)
        saveNextId()
        return newMemo
    }

    /**
     * メモを更新
     */
    fun updateMemo(id: Long, title: String, content: String): Boolean {
        val memos = getAllMemos().toMutableList()
        val index = memos.indexOfFirst { it.id == id }

        if (index == -1) return false

        memos[index] = memos[index].copy(
            title = title,
            content = content,
            updatedAt = Date()
        )

        saveMemos(memos)
        return true
    }

    /**
     * メモを削除
     */
    fun deleteMemo(id: Long): Boolean {
        val memos = getAllMemos().toMutableList()
        val removed = memos.removeIf { it.id == id }

        if (removed) {
            saveMemos(memos)
        }

        return removed
    }

    /**
     * キーワードでメモを検索
     */
    fun searchMemos(keyword: String): List<Memo> {
        return getAllMemos().filter { memo ->
            memo.title.contains(keyword, ignoreCase = true) ||
                    memo.content.contains(keyword, ignoreCase = true)
        }
    }

    /**
     * メモを日付順にソート（新しい順）
     */
    fun getMemosSortedByDate(): List<Memo> {
        return getAllMemos().sortedByDescending { it.updatedAt }
    }

    /**
     * メモの総数を取得
     */
    fun getMemosCount(): Int {
        return getAllMemos().size
    }

    /**
     * すべてのメモを削除
     */
    fun deleteAllMemos() {
        sharedPreferences.edit().clear().apply()
        nextId = 1L
    }

    /**
     * メモをSharedPreferencesに保存
     */
    private fun saveMemos(memos: List<Memo>) {
        val json = gson.toJson(memos)
        sharedPreferences.edit().putString(KEY_MEMOS, json).apply()
    }

    /**
     * 次のIDを保存
     */
    private fun saveNextId() {
        sharedPreferences.edit().putLong(KEY_NEXT_ID, nextId).apply()
    }
}