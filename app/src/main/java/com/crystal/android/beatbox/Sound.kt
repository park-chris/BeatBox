package com.crystal.android.beatbox


private const val WAV = ".wav"

class Sound(val assetPath: String, var soundId: Int? = null) {

//    String.split(String).last() : 경로 문자열의 맨 끝에 있는 파일 이름을 얻음
//    String.removeSuffix(String) : 확장자인 .wav를 제거
    val name = assetPath.split("/").last().removeSuffix(WAV)
}