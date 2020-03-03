package konkuk.yeonj.yeonjinwords

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.quiz_layout.*
import kotlin.random.Random

class QuizActivity: AppCompatActivity() {

    lateinit var engList:ArrayList<String>
    lateinit var korList:ArrayList<String>
    lateinit var kor:String
    lateinit var eng:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_layout)
        init()
    }

    fun init(){

        engList = ArrayList()
        korList = ArrayList()
        readList()
        makeQuiz()
        listView.setOnItemClickListener { parent, view, position, it ->
            val str = parent.getItemAtPosition(position).toString()
            if(str==kor){
                Toast.makeText(this, "정답", Toast.LENGTH_SHORT).show()
                makeQuiz()
            }
            else{
                Toast.makeText(this, "오답", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun readList(){
        for(i in 0..likeItems.size-1){
            engList.add(likeItems[i].eng)
            korList.add(likeItems[i].kor)
        }
    }

    fun makeQuiz(){
        val quiz = ArrayList<String>(5) //한글 보기
        val index =  Random.nextInt(engList.size) //영어문제 인덱스
        val qindex = ArrayList<Int>()
        eng = engList[index] //영어 문제
        kor = korList[index]
        qindex.add(index)
        quiz.add(korList[index])
        while(quiz.size != 5){
            var temp = Random.nextInt(korList.size)
            if(qindex.indexOf(temp) == -1){
                quiz.add(korList[temp])
                qindex.add(temp)
            }
        }
        quiz.shuffle()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, quiz)
        listView.adapter = adapter
        textView.text = eng
    }
}