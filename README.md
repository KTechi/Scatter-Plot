# Scatter-Plot

### 3次元の散布図として使用します。
**`Scatter-Plot/sampleData/`** 内の **`testData1.txt`** をプロットした結果。  
![sampleImage-1](/info/testData1.png)

同じディレクトリ内の **`testData2.txt`** をプロットした結果。  
![sampleImage-2](/info/testData2.png)

---

### 操作方法
**マウス** もしくは **キーボード** で散布図を回転させることができます。
**スペース** で下のアニメーションが使用できます。
![sampleGifLQ](/info/sampleGifLQ.gif)

---

### To load data
**頂点** を定義するためには、"vertex"のみ、もしくわ"vertex"とスペースを記述してください。
次の行に、xyz座標用の３整数もしくは実数を記述します。色を指定するには、続けてrgb用３つの実数[0,1.0]を記述します。  
デフォルト色は白色です。  
![sampleInfo1](/info/sampleInfo1.png)

**辺** を定義するためには、"edge"のみ、もしくわ"edge"とスペースを記述してください。
色を指定することはできません。頂点番号（0始まり）を２つ入力して頂点を定義します。  
色は白色です。
**面** も同様。ただし先頭での宣言は"face"で、頂点番号は３以上。
![sampleInfo2](/info/sampleInfo2.png)
