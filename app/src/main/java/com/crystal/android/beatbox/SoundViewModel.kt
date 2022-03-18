package com.crystal.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox): BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    //    사용할 Sound 객체 참조를 갖는 sound 속성을 추가한다.
    var sound: Sound? = null
        set(sound) {
            field = sound
//            데이터 객체의 모든 바인딩 속성값이 변경되었음을 바인딩 클래스에 알린다.
            notifyChange()
        }

//    각 버튼에 보여줄 제목을 갖는 title 속성도 추가한다.
    @get:Bindable
    val title: String?
        get() = sound?.name

}