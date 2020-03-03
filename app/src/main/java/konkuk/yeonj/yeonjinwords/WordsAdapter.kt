package konkuk.yeonj.yeonjinwords

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import java.util.*

class WordsAdapter(val items:ArrayList<MyWord>, val pos:Int) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    //클릭리스너 인터페이스 만들기
    interface OnItemClickListener{
        fun OnItemClick(holder:ViewHolder, view:View, data: MyWord, position: Int )
    }
    var itemClickListener : OnItemClickListener? = null

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){ //데이터 저장구조
        var engText:TextView
        var korText:TextView
        var checked:ToggleButton
        init { //row레이아웃의 data와
            engText = itemView.findViewById(R.id.engText)
            korText = itemView.findViewById(R.id.korText)
            checked = itemView.findViewById(R.id.myBtn)
            itemView.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.OnItemClick(this, it, items[position], position)
            }
            checked.setOnClickListener{
                if(items.get(adapterPosition).checked){
                    items.get(adapterPosition).checked = false
                    likeItems.remove(items.get(adapterPosition))
                    notifyDataSetChanged()
                }
                else{ //체크안되있으면 체크하기
                    items.get(adapterPosition).checked = true
                    likeItems.add(items.get(adapterPosition))
                }
            }
            when(pos){
                0->{
                    engText.visibility = View.VISIBLE
                    korText.visibility = View.VISIBLE
                }
                1->{
                    engText.visibility = View.VISIBLE
                    korText.visibility = View.INVISIBLE
                }
                2->{
                    engText.visibility = View.INVISIBLE
                    korText.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): WordsAdapter.ViewHolder { //ViewHolder생성
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val v = LayoutInflater.from(p0.context).inflate(R.layout.row, p0, false) // 내가 만든 row.xml 레이아웃 inflate
        return  ViewHolder(v) //row 리턴
    }

    override fun getItemCount(): Int {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) { //viewHolder의 내용 초기화
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        p0.engText.text = items.get(p1).eng.toString()
        p0.korText.text = items.get(p1).kor.toString()
        p0.checked.isChecked = items.get(p1).checked
    }
}

