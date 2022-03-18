package com.crystal.android.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystal.android.beatbox.databinding.ActivityMainBinding
import com.crystal.android.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = BeatBox(assets)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
//            RecyclerView 한 행에 세 개의 격자를 가지는 것것
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beatBox.release()
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        뷰모델 인스턴스를 생성하고 바인딩 클래스의 viewModel 속성을 초기화
        init {
            binding.viewModel = SoundViewModel(beatBox)
        }

//        viewModel 속성을 변경
        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
//                보통은 호출할 필요없지만 RecyclerView에 포함된 바인딩 데이터를 변경해야하고 RecyclerView에
//                포함된 레이아웃을 즉각 변경하도록 호출
                executePendingBindings()
            }
        }

    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
//            뷰모델의 각 Sound 인스턴스를 SoundHolder 인스턴스와 연결
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
    }


}