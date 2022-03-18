package com.crystal.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

// 애셋은 AssetManager 클래스로 사용하며, AssetManager 인스턴스는 어떤 Context에서도 생성가능
class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
//            매개변수 : 음원 ID, 왼쪽 볼륨(0.0~1.0), 오른쪽 볼륨(0.0~1.0), 스트림 우선순위(0이면 최저 우선순위)
//            반복 재생여부(0=반복X, -1=무한반복, 다른숫자는 반복횟수), 재생률(1이면 녹음된 속도 그대로, 2는 두배빠르게)
            soundPool.play(it, 1.0f,1.0f, 1, 0, 1.0f )
        }
    }

//    음악재생이 끝나면 SoundPool을 클린업(리소스 해제)
    fun release() {
        soundPool.release()
    }

    //    list(String) 함수로 애셋에 있는 파일들의 내역을 찾는 함수 추가
    private fun loadSounds(): List<Sound> {

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Cound not load sound $filename")
            }
        }
        return sounds
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

}