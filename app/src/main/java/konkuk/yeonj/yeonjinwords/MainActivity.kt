package konkuk.yeonj.yeonjinwords

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


lateinit var likeItems:ArrayList<MyWord>
lateinit var data:ArrayList<MyWord>
lateinit var engData:ArrayList<String>

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun readFile(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val eng = scan.nextLine()
            val kor = scan.nextLine()
            data.add(MyWord(eng, kor, false))
            engData.add(eng)
        }
    }

    fun init(){
        data = ArrayList()
        engData = ArrayList()
        readFile()
        likeItems = ArrayList()
        wordsBtn.setOnClickListener{
            val intent = Intent(
                applicationContext, // 현재 화면의 제어권자
                WordsActivity::class.java
            ) // 다음 넘어갈 클래스 지정
            intent.putExtra("int", 1)
            startActivity(intent)
        }
        myWordsBtn.setOnClickListener{
            val intent = Intent(
                applicationContext, // 현재 화면의 제어권자
                WordsActivity::class.java
            ) // 다음 넘어갈 클래스 지정
            intent.putExtra("int", 2)
            startActivity(intent)
        }
        quizBtn.setOnClickListener{
            if(likeItems.size < 5){
                Toast.makeText(applicationContext, "단어장에서 단어를 5개이상 선택해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(
                    applicationContext, // 현재 화면의 제어권자
                    QuizActivity::class.java
                ) // 다음 넘어갈 클래스 지정
                startActivity(intent)
            }

        }

    }
}
