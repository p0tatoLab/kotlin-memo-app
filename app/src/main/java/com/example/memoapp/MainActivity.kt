package com.example.memoapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoapp.data.MemoRepository
import com.example.memoapp.databinding.ActivityMainBinding
import com.example.memoapp.ui.MemoAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: MemoRepository
    private lateinit var adapter: MemoAdapter

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

        // FABのクリックイベント
        binding.fabAdd.setOnClickListener {
            // TODO: 詳細画面への遷移（次のステップで実装）
            Snackbar.make(binding.root, "新規メモ作成（次のステップで実装）", Snackbar.LENGTH_SHORT).show()
        }

        // データを読み込んで表示
        loadMemos()
    }

    /**
     * RecyclerViewのセットアップ
     */
    private fun setupRecyclerView() {
        adapter = MemoAdapter(emptyList()) { memo ->
            // TODO: 詳細画面への遷移（次のステップで実装）
            Snackbar.make(binding.root, "メモID: ${memo.id} をクリック", Snackbar.LENGTH_SHORT).show()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    /**
     * メモを読み込んで表示
     */
    private fun loadMemos() {
        val memos = repository.getMemosSortedByDate()

        adapter.updateMemos(memos)
        // 空の状態の表示切り替え
        if (memos.isEmpty()) {
            binding.recyclerView.visibility = View.GONE
            binding.textEmptyView.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textEmptyView.visibility = View.GONE
        }
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
        // 画面に戻ってきたときにデータを再読み込み
        loadMemos()
    }
}