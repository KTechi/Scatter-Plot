# Scatter-Plot

#### [en] You can use this program as a 3D scatter plot.
#### [jp] 3次元の散布図として利用できます。

---

[en] Do not expect my English.  
[jp] 英語に期待しないでください。  

---

[en] This is a graph, using **`testData1.txt`** in the **`sampleData`**.  
[jp] **`sampleData`** 内の **`testData1.txt`** を用いたグラフ。  
![sampleImage-1](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/testData1.png)

[en] This is a graph, using **`testData2.txt`** in the same directory.  
[jp] 同じディレクトリ内の **`testData2.txt`** を用いたグラフ。  
![sampleImage-2](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/testData2.png)

---

### What makes it "3D"
[en] You can also spin the graph by using your **mouse** or **keyboard**.
Press **space** to use the animation below.  
[jp] **マウス** か **キーボード** でグラフを回転させることができます。
**スペース** で下のアニメーションが使用できます。
<!--![sampleGifHQ](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/sampleGifHQ.gif)-->
![sampleGifLQ](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/sampleGifLQ.gif)

---

### How to load data
[en] To define **Vertex**, type only "vertex" or "vertex" and space.
Move to the next line and put three `integer` or `double` for x, y and z.
If you want to define color, add three `double` for r, g and b.  
Default color is white.  
[jp] **頂点** を定義するためには、"vertex"のみ若しくわ"vertex"とスペースと入力してください。
次の行に、xyz座標用の３整数もしくは実数を入力します。色を設定するためには、続けてrgb用３つの実数を入力します。  
デフォルト色は白色です。  
![sampleInfo1](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/sampleInfo1.png)

[en] To define **Edge**, type only "edge" or "edge" and space.
You cannot define color. Type two vertex number to define an edge.  
Default color is white.  
[jp] **辺** を定義するためには、"edge"のみ若しくわ"edge"とスペースと入力してください。
色を指定することはできません。頂点番号を２つ入力して頂点を定義します。  
デフォルト色は白色です。  
![sampleInfo2](https://raw.githubusercontent.com/KTeruuchi/Scatter-Plot/master/info/sampleInfo2.png)
