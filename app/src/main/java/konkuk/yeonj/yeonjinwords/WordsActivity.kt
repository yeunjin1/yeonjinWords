package konkuk.yeonj.yeonjinwords

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.words_layout.*
import java.util.*
import kotlin.collections.ArrayList

class WordsActivity :AppCompatActivity() {
    var ttsReady = false
    lateinit var tts: TextToSpeech
    lateinit var adapter:WordsAdapter
    var num = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.words_layout)
        init()
    }

    fun init(){
        val intent = intent
        num = intent.getIntExtra("int", -1)
        wordsView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        if(num == 1){
            adapter = WordsAdapter(data, 0)
        }
        else{
            adapter = WordsAdapter(likeItems, 0)
        }
        tts = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US //영어 발음 지역 지정
        })
        adapter.itemClickListener = object : WordsAdapter.OnItemClickListener{
            override fun OnItemClick(holder: WordsAdapter.ViewHolder, view: View, data: MyWord, position: Int) {
                //TODO ~~~
                Log.d("log", "datachange")
                val str = data.eng
                if(ttsReady)
                    tts.speak(str, TextToSpeech.QUEUE_ADD, null, null)
                when(position){
                    1->{
                        Toast.makeText(applicationContext, data.kor.toString(), Toast.LENGTH_SHORT).show()
                    }
                    2->{
                        Toast.makeText(applicationContext, data.eng.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

        wordsView.adapter = adapter
        additemSpinner()
        spinner.onItemSelectedListener = SpinnerSelectedListener()
        //검색창
        val textAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, engData)
        searchText.setAdapter(textAdapter)
        //버튼
        button.setOnClickListener {
            if(engData.indexOf(searchText.text.toString())>=0)
                wordsView.scrollToPosition(engData.indexOf(searchText.text.toString()))
            else
                Toast.makeText(applicationContext, "검색한 단어가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    fun additemSpinner(){
        val adapter= ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ArrayList<String>())
        adapter.add("영어+한글")
        adapter.add("영어")
        adapter.add("한글")
        spinner.adapter = adapter
    }

    inner class SpinnerSelectedListener: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            if(num == 1){
                adapter = WordsAdapter(data, position)
            }
            else{
                adapter = WordsAdapter(likeItems, position)
            }
            adapter.itemClickListener = object : WordsAdapter.OnItemClickListener{
                override fun OnItemClick(holder: WordsAdapter.ViewHolder, view: View, data: MyWord, position: Int) {
                    //TODO ~~~
                    val str = data.eng
                    if(ttsReady)
                        tts.speak(str, TextToSpeech.QUEUE_ADD, null, null)
                    Log.v("str", position.toString())
                    when(position){
                        1->{
                            Toast.makeText(applicationContext, data.kor.toString(), Toast.LENGTH_SHORT).show()
                        }
                        2->{
                            Toast.makeText(applicationContext, data.eng.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            wordsView.adapter = adapter
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        }
    }
}