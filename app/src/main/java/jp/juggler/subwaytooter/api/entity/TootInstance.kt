package jp.juggler.subwaytooter.api.entity

import org.json.JSONObject

import jp.juggler.subwaytooter.util.VersionString
import jp.juggler.subwaytooter.util.parseLong
import jp.juggler.subwaytooter.util.parseString

class TootInstance(src : JSONObject) {
	
	// いつ取得したか(内部利用)
	var time_parse : Long = System.currentTimeMillis()
	
	//	URI of the current instance
	val uri : String?
	
	//	The instance's title
	val title : String?
	
	//	A description for the instance
	val description : String?
	
	//	An email address which can be used to contact the instance administrator
	val email : String?
	
	val version : String?
	
	// バージョンの内部表現
	private val decoded_version : VersionString
	
	// インスタンスのサムネイル。推奨サイズ1200x630px。マストドン1.6.1以降。
	val thumbnail : String?
	
	// ユーザ数等の数字。マストドン1.6以降。
	val stats : Stats?
	
	// XXX: urls をパースしてない。使ってないから…
	
	init {
		this.uri = src.parseString("uri")
		this.title = src.parseString("title")
		this.description = src.parseString("description")
		this.email = src.parseString("email")
		this.version = src.parseString("version")
		this.decoded_version = VersionString(version)
		this.stats = parseItem(::Stats, src.optJSONObject("stats"))
		this.thumbnail = src.parseString("thumbnail")
	}
	
	class Stats(src : JSONObject) {
		val user_count : Long
		val status_count : Long
		val domain_count : Long
		
		init {
			this.user_count = src.parseLong("user_count") ?: - 1L
			this.status_count = src.parseLong("status_count") ?: - 1L
			this.domain_count = src.parseLong("domain_count") ?: - 1L
		}
	}
	
	fun isEnoughVersion(check : VersionString) : Boolean {
		
		if(decoded_version.isEmpty || check.isEmpty) return false
		val i = VersionString.compare(decoded_version, check)
		return i >= 0
	}
	
}