package com.crystal.android.beatbox

import android.content.res.AssetManager
import android.util.Log
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

// 애셋은 AssetManager 클래스로 사용하며, AssetManager 인스턴스는 어떤 Context에서도 생성가능
class BeatBox(private val assets: AssetManager) {

//    list(String) 함수로 애셋에 있는 파일들의 내역을 찾는 함수 추가
    fun loadSounds(): List<String> {
        try {
            val soundNames = assets.list(SOUNDS_FOLDER)!!
            Log.d(TAG, "Found ${soundNames.size} sounds")
            return soundNames.asList()
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
    }

}