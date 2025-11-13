package com.example.memoapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoapp.data.MemoRepository
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.model.Memo
import com.example.memoapp.ui.DetailActivity
import com.example.memoapp.ui.MemoAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: MemoRepository
    private lateinit var adapter: MemoAdapter

    private var currentMemos: List<Memo> = emptyList()
    private var isSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBindingの設定
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbarの設定
        setSupportActionBar(binding.toolbar)

        // Repositoryの初期化
        repository = MemoRepository(this)

        // テストデータの追加（初回のみ）
        if (repository.getMemosCount() == 0) {
            addTestData()
        }

        // RecyclerViewの設定
        setupRecyclerView()

        // FABのクリックイベント（新規メモ作成）
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_IS_EDIT_MODE, false)
            startActivity(intent)
        }

        // データを読み込んで表示
        loadMemos()
    }

    /**
     * メニューを作成
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // SearchViewの設定
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.apply {
            queryHint = "メモを検索..."

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    searchMemos(newText ?: "")
                    return true
                }
            })
        }

        // SearchViewの開閉監視
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                isSearching = true
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                isSearching = false
                loadMemos()
                return true
            }
        })

        return true
    }

    /**
     * メニューアイテムが選択されたとき
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showSortDialog()
                true
            }
            R.id.action_delete_all -> {
                showDeleteAllConfirmDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * RecyclerViewのセットアップ
     */
    private fun setupRecyclerView() {
        adapter = MemoAdapter(emptyList()) { memo ->
            // メモをタップしたら詳細画面へ遷移
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MEMO_ID, memo.id)
            intent.putExtra(DetailActivity.EXTRA_IS_EDIT_MODE, true)
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            setHasFixedSize(true)
        }
    }

    /**
     * メモを読み込んで表示
     */
    private fun loadMemos() {
        currentMemos = repository.getMemosSortedByDate()
        displayMemos(currentMemos)
    }

    /**
     * メモを検索
     */
    private fun searchMemos(keyword: String) {
        if (keyword.isEmpty()) {
            displayMemos(currentMemos)
        } else {
            val filteredMemos = repository.searchMemos(keyword)
            displayMemos(filteredMemos)
        }
    }

    /**
     * メモを表示
     */
    private fun displayMemos(memos: List<Memo>) {
        adapter.updateMemos(memos)

        // 空の状態の表示切り替え
        if (memos.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.textEmptyView.visibility = View.VISIBLE
            binding.textEmptyView.text = if (isSearching) {
                "検索結果がありません"
            } else {
                getString(R.string.empty_list)
            }
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textEmptyView.visibility = View.GONE
        }
    }

    /**
     * 並び替えダイアログを表示
     */
    private fun showSortDialog() {
        val options = arrayOf(
            "更新日（新しい順）",
            "更新日（古い順）",
            "タイトル順"
        )

        AlertDialog.Builder(this)
            .setTitle("並び替え")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        currentMemos = repository.getMemosSortedByDate()
                        displayMemos(currentMemos)
                    }
                    1 -> {
                        currentMemos = repository.getAllMemos().sortedBy { it.updatedAt }
                        displayMemos(currentMemos)
                    }
                    2 -> {
                        currentMemos = repository.getAllMemos().sortedBy { it.title }
                        displayMemos(currentMemos)
                    }
                }
            }
            .show()
    }

    /**
     * すべて削除の確認ダイアログ
     */
    private fun showDeleteAllConfirmDialog() {
        if (repository.getMemosCount() == 0) {
            Snackbar.make(binding.root, "削除するメモがありません", Snackbar.LENGTH_SHORT).show()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("確認")
            .setMessage("すべてのメモを削除しますか？この操作は取り消せません。")
            .setPositiveButton("削除") { _, _ ->
                repository.deleteAllMemos()
                loadMemos()
                Snackbar.make(binding.root, "すべてのメモを削除しました", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    /**
     * テストデータの追加
     */
    private fun addTestData() {
        repository.addMemo(
            "買い物リスト",
            "牛乳\nパン\n卵\nバナナ\nヨーグルト"
        )

        repository.addMemo(
            "会議メモ",
            "明日の会議の議題：\n1. プロジェクト進捗確認\n2. 予算の見直し\n3. 次週のスケジュール"
        )

        repository.addMemo(
            "勉強メモ",
            "Kotlinの基礎\n- データクラス\n- コレクション操作\n- RecyclerView\n- ViewBinding"
        )

        repository.addMemo(
            "TODO",
            "今日やること：\n✓ メールチェック\n✓ 資料作成\n□ ミーティング準備"
        )

        repository.addMemo(
            "アイデア",
            "新しいアプリのアイデア：\n- タスク管理アプリ\n- 家計簿アプリ\n- 読書記録アプリ"
        )
    }

    override fun onResume() {
        super.onResume()
        // 画面に戻ってきたときにデータを再読み込み（検索中でなければ）
        if (!isSearching) {
            loadMemos()
        }
    }
}