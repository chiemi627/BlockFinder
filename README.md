# BlockFinder
* Bmp画像ファイルに含まれている黒の長方形を見つけてその位置と大きさをCSV形式で出力します。

## コンパイル
 % javac *.java
 
## 実行
 % java BlockFinder <bmpファイル> <出力ファイル>
 
## 出力内容
 1行目と2行目に画像の幅と高さが出力されます。

 4行目以降は，1行につき1ブロックの情報が出力されます。
 
 位置(x), 位置(y), 幅, 高さ, 向き
 
 向き情報：横に長いときは sideways, 縦に長いときはlongways です。
 
# BlockImageBuilder
* CSVファイルを直接変更して調整した後，その結果を確認することができます。

## 実行
 % java BlockImageBuilder <ブロック(csv)> <ブロックグループ(csv)> <出力ファイル(bmp)>
