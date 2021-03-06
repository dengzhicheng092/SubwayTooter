package jp.juggler.util

// same as x?.let{ dst.add(it) }
fun <T> T.addTo(dst: ArrayList<T>) = dst.add(this)

fun <E:List<*>> E?.notEmpty() :E? =
	if(this?.isNotEmpty()== true) this else null
