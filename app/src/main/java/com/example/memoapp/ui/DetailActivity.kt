package com.example.memoapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memoapp.R
import com.example.memoapp.data.MemoRepository
import com.example.memoapp.databinding.ActivityDetailBinding
import com.example.memoapp.utils.DateUtils

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var repository: MemoRepository

    private var memoId: Long = -1
    private var isEditMode = false

    companion object {
        const val EXTRA_MEMO_ID = "extra_memo_id"
        const val EXTRA_IS_EDIT_MODE = "extra_is_edit_mode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Repository
        repository = MemoRepository(this)

        // Intentからデータを取得
        memoId = intent.getLongExtra(EXTRA_MEMO_ID, -1)
        isEditMode = intent.getBooleanExtra(EXTRA_IS_EDIT_MODE, false)

        // データを読み込み
        if (isEditMode && memoId != -1L) {
            loadMemo()
            supportActionBar?.title = "メモを編集"
        } else {
            supportActionBar?.title = "新規メモ"
        }

        // 保存ボタン
        binding.fabSave.setOnClickListener {
            saveMemo()
        }
    }

    /**
     * メニューを作成
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 編集モードの場合のみ削除ボタンを表示
        if (isEditMode) {
            menuInflater.inflate(R.menu.menu_detail, menu)
        }
        return true
    }

    /**
     * メニューアイテムが選択されたとき
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // 戻るボタン
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_delete -> {
                // 削除ボタン
                showDeleteConfirmDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * メモを読み込み
     */
    private fun loadMemo() {
        val memo = repository.getMemoById(memoId)

        if (memo != null) {
            binding.editTitle.setText(memo.title)
            binding.editContent.setText(memo.content)
            binding.textDate.text = "作成日: ${DateUtils.formatDateTime(memo.createdAt)}\n" +
                    "更新日: ${DateUtils.formatDateTime(memo.updatedAt)}"
        } else {
            Toast.makeText(this, "メモが見つかりません", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * メモを保存
     */
    private fun saveMemo() {
        val title = binding.editTitle.text.toString().trim()
        val content = binding.editContent.text.toString().trim()

        // バリデーション
        if (title.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_empty_title), Toast.LENGTH_SHORT).show()
            return
        }

        // 保存
        if (isEditMode && memoId != -1L) {
            // 更新
            val success = repository.updateMemo(memoId, title, content)
            if (success) {
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "保存に失敗しました", Toast.LENGTH_SHORT).show()
            }
        } else {
            // 新規作成
            repository.addMemo(title, content)
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     * 削除確認ダイアログを表示
     */
    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(this)
            .setTitle("確認")
            .setMessage(getString(R.string.confirm_delete))
            .setPositiveButton("削除") { _, _ ->
                deleteMemo()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    /**
     * メモを削除
     */
    private fun deleteMemo() {
        val success = repository.deleteMemo(memoId)
        if (success) {
            Toast.makeText(this, getString(R.string.deleted), Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "削除に失敗しました", Toast.LENGTH_SHORT).show()
        }
    }
}