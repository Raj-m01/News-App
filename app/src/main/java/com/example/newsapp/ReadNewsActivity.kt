package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.architecture.NewsViewModel
import java.util.*


class ReadNewsActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var newsWebView: WebView
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsData: ArrayList<NewsModel>
    private lateinit var tts: TextToSpeech

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_news)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        newsWebView = findViewById(R.id.news_webview)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        //loading data into list
        newsData = ArrayList(1)
        val newsUrl = intent.getStringExtra(MainActivity.NEWS_URL)
        val newsContent =
            intent.getStringExtra(MainActivity.NEWS_CONTENT) + ". get paid version to hear full news. "
        newsData.add(
            NewsModel(
                intent.getStringExtra(MainActivity.NEWS_TITLE)!!,
                intent.getStringExtra(MainActivity.NEWS_IMAGE_URL),
                intent.getStringExtra(MainActivity.NEWS_DESCRIPTION),
                newsUrl,
                intent.getStringExtra(MainActivity.NEWS_SOURCE),
                intent.getStringExtra(MainActivity.NEWS_PUBLICATION_TIME),
                newsContent
            )
        )

        // Webview
        newsWebView.settings.domStorageEnabled = true
        newsWebView.settings.loadsImagesAutomatically = true
        newsWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        newsWebView.settings.javaScriptEnabled = true
        newsWebView.webViewClient = WebViewClient()


        newsWebView.webChromeClient = WebChromeClient()

        if (newsUrl != null) {
            newsWebView.loadUrl(newsUrl)
        }

        //text to speech
        tts = TextToSpeech(this, this)

    }


    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "TTS Not Supported for this news", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun playNews() {
        tts.speak(newsData[0].content, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // Adding voices
    private val voice1: Voice = Voice(
        "en-US-SMTf00",
        Locale("en", "USA"),
        300,
        300,
        false,
        setOf("NA", "f00", "202009152", "female", null)
    )
    private val voice2: Voice = Voice(
        "en-IN-SMTf00",
        Locale("en", "IND"),
        300,
        300,
        false,
        setOf("NA", "f00", "202007071", "female", null)
    )
    private val addedVoices: Set<Voice> = setOf(voice1, voice2)

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item_readnewsactivity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.share_news -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this news : " + newsData[0].url)
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share with :"))
                return true
            }

            R.id.save_news -> {
                this.let { viewModel.insertNews(this@ReadNewsActivity, newsData[0]) }
                Toast.makeText(this, "News saved!", Toast.LENGTH_SHORT)
                    .show()
            }

            R.id.browse_news -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsData[0].url))
                startActivity(intent)
            }

            // Menu items for vocal news
            R.id.play_news -> {
                playNews()
            }

            R.id.stop_news -> {
                tts.stop()
            }

            R.id.speed_075x -> {
                tts.stop()
                tts.setSpeechRate(0.75F)
                playNews()
            }

            R.id.speed_1x -> {
                tts.stop()
                tts.setSpeechRate(1F)
                playNews()
            }

            R.id.speed_2x -> {
                tts.stop()
                tts.setSpeechRate(2F)
                playNews()
            }

            R.id.voice1 -> {

                tts.stop()
                tts.voice = addedVoices.elementAt(0)
                playNews()

            }

            R.id.voice2 -> {
                tts.stop()
                tts.voice = addedVoices.elementAt(1)
                playNews()
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}