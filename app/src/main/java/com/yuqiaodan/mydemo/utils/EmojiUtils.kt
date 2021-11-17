package com.yuqiaodan.mydemo.utils

import android.util.Log

/**
 * Created by qiaodan on 2021/11/3
 * Description:处理字符串中携带的emoji表情
 */
class EmojiUtils {
    val TAG="EmojiUtils"

    fun emojiTest() {


        val str1 = "\uD83D\uDC8B  \uD83D\uDE04huang太阳\uD83D\uDE42"
        val str2 =
            "C0cTCAERGxkBLQ8BQU5SUxZXUVRBFlFLVANbRkFXRURJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIDEwoWBlZcRxYDEwoQUl9QVAAHEBVdR1QDUERAUUUCAVRMRQEQVldbFRRdS1NQU0JSSVAPCAYdUl9QXlNWQEVRQlJQWkBHXUtTR09WHAoRBxEKGx5HSB1HAhAUFxcVFkFOUua4s+WMpeedp+axneW2veW5tuiSreWwlOWPjVVXUeedtemAo+mchei+o+WSoOacgOWTieiIjuadn+W4meWFjeWai0dPVhEKGwgEDhFSX1BESUEXGRELRF9B5rGM5bau5bmnUEpHBx0DEQAPBhdWSkfokq/lsJflj5pBWFIJExIMQU5SV0dIUVtERVdKRElBGB8LUFxHUkVCS0NTXFdFRUdeRAsWGRIAAERfQVZcRwIJDA0VHQBQXEflkqXmnJHlk5roiJ/mnZ3luJrlhZrlmo5WXEcCFAoVHR4GF0RfQea4ouWMp+edpFBKRxEbEQFQXEdQRkLnnaTpgKFESUEHBBcXAxFBTlJWQFTnnaTpgLBWDUlQCwQAVkpHM1JfUkZKVkBcUlZOMVdIVyFBWFIIHQIAD1ZKRwIEBA5EQEdeRAoCHRRHSEQhUzdAUTRVJFs1SFdGUldWTElQQSRcUzVIV0MjVVsyRwBEVQNUEkYDEFVQVEZIVxNWVFJCFFdCUl0BRkEBEFBWQVhSCgE5EwYGUl9QXktSWkBHXkQVCBNSX1AFCg5aEQsWFAoKEF4JGwENF1oTCgVESUEGHwhQXEcMBAAKUEpHERsdOgQDF0FOUlBcVEtSVlxHAAkKF1ZKRxQHCRARUklQFQwOVkpHBhQQBlZcRwEVDAdWSkfwo7OtRUMHGBATCAJBWFITFxQ6ABsUAFBcR1JEQFRHRElBAhUXLQgEDhFSX1BXS1NaQVBQGw=="


        val str3 = "k, 简称 संस्कृतम्한국어;，藏文（བོད་ཡིག）日本语（日本语/にほんご Nihongo ）1344wdfwdferfg34,;/./[;[1.!@#$%^&*()_+~!!``1【；】；。、。'；【psaṃskṛtā vā"

        var str4 = "😄😄👦🏻你们好 练习时长两年半🐔  🏀 e2333fkf af称 संस्कृतम्한국어;，藏文（བོད་ཡིག）日ongo "

        Log.d(TAG, "hasEmoji str1-->${hasEmoji(str1)}     Emoji")
        Log.d(TAG, "hasEmoji str2-->${hasEmoji(str2)}")

        Log.d(TAG, "hasEmoji str3-->${hasEmoji(str3)}")

        Log.d(TAG, "removeEmoji 原值-->$str4 \n新值--> ${removeEmoji(str4)}")


        var str5 = "😄😄👦🏻你们好 练习时长两年半🐔  🏀 "
        var str6 = "EmojiEmojiEmoji你们好 练习时长两年半Emoji  Emoji "

        Log.d(TAG, "replaceSsidEmoji \n原值-->$str4 \n新值--> ${replaceSsidEmoji(str4, "Emoji")}")

        Log.d(TAG, "replaceSsidEmoji \n原值-->$str5 \n新值--> ${replaceSsidEmoji(str5, "Emoji")}")


    }


    fun replaceSsidEmoji(value: String, new: String): String {
        var str = ""
        var isAddNew = false
        for (char in value) {
            if (!charIsEmoji(char)) {
                str += char
                isAddNew = false
            } else {
                if (!isAddNew) {
                    isAddNew = true
                    str += new
                }
            }
        }
        return str
    }


    fun replaceEmoji(value: String, new: String): String {
        var str = ""
        for (char in value) {
            if (!charIsEmoji(char)) {
                str += char
            } else {
                str += new
            }
        }
        return str
    }

    fun removeEmoji(str: String): String {
        var newStr = ""
        for (char in str) {
            if (!charIsEmoji(char)) {
                newStr += char
            }
        }
        return newStr
    }


    fun hasEmoji(str: String): Boolean {
        for (char in str) {
            if (charIsEmoji(char)) {
                return true
                break
            }
        }
        return false
    }

    fun charIsEmoji(codePoint: Char): Boolean {
        val code = codePoint.code
        val list = listOf(0x0, 0x9, 0xA)

        if (code in list || code in 0x20..0xD7FF ||
            code in 0xE000..0xFFFD ||
            code in 0x10000..0x10FFFF
        ) {
            return false
        }
        return true
    }





}