# ためしたこと

サーバーサイドは以下の３つ

- coroutine の`delay`
- 単に`Thread.sleep`でブロッキングする
- `Thread.sleep`を`withContext(Dispatchers.IO)`で待つ

## 結果

### coroutine の`delay`

全部並列に捌いてくれる

### 単に`Thread.sleep`でブロッキングする

サーバーの並列リクエスト数の限界に達し、リクエスト実行前に待たされる

### `Thread.sleep`を`withContext(Dispatchers.IO)`で待つ

特に待たされない。
