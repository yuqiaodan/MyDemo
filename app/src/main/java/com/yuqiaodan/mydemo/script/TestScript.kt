package com.yuqiaodan.mydemo.script

import java.text.DecimalFormat

/**
 * Created by qiaodan on 2022/2/11
 * Description:
 */


fun main(args: Array<String>) {

    val list = listOf(Allo("1", 2), Allo("1", 3), Allo("1", 10), Allo("1", 9), Allo("1", 1))


    val list1=  list.sortedBy {
        it.id
    }

    val list2=list.sortedByDescending {
        it.id
    }

    print(
        " ${list1.toString()}"
    )

    print(
        " ${list2.toString()}"
    )
    /*val str1 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw=="
    print(String(Base64.decode(str1, Base64.NO_WRAP)) + "\n")
    val str2 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw"
    print(String(Base64.decode(str2, Base64.NO_WRAP)) + "\n")
*/
    //print(json.getJSONObject("cloud_config"))


    /*  val num1 = 11.2 as Double
      val num2=101.34
      print("num1*num2=${num1 * num2}\n")
      val result1 = DoubleCalculateUtils.mul(num1, num2)
      print(" DoubleCalculateUtils.mul num*100.0=${result1}\n")


      print(" 四舍五入 保留1 =${DoubleCalculateUtils.round(599.1999991,1)}\n")
      print(" 四舍五入 保留0 =${DoubleCalculateUtils.round(599.1999991,0)}\n")
  */

}

class Allo(
    val name: String,
    val id: Int
)


const val KB = 1024L
const val MB = 1048576L
const val GB = 1073741824L

fun formatSize(size: Long?): String {
    val df = DecimalFormat("0.0")
    val fileSizeString: String
    val wrongSize = "0B"
    if (size == null || size == 0L) {
        return wrongSize
    }
    fileSizeString = when {
        size < KB -> df.format(size.toDouble()) + "B"
        size < MB -> df.format(size.toDouble() / KB) + "KB"
        size < GB -> df.format(size.toDouble() / MB) + "MB"
        else -> df.format(size.toDouble() / GB) + "GB"
    }
    return fileSizeString
}



