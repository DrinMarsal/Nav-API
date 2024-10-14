package com.dicoding.myappp.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import android.widget.TextView
import android.content.Intent
import android.widget.Button
import android.view.View
import android.os.Bundle
import android.net.Uri
import com.bumptech.glide.Glide
import com.dicoding.myappp.R

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var scrollView: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar = findViewById(R.id.toolbar)
        scrollView = findViewById(R.id.scroll_view)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Ensure toolbar is always visible
        toolbar.visibility = View.VISIBLE

        val eventName = intent.getStringExtra("event.name")
        val eventImage = intent.getStringExtra("event.mediaCover")
        val eventImageLogo = intent.getStringExtra("event.imageLogo")
        val eventSummary = intent.getStringExtra("event.summary")
        val eventOwner = intent.getStringExtra("event.ownerName")
        val eventDescription = intent.getStringExtra("event.description")
        val eventBeginTime = intent.getStringExtra("event.beginTime")
        val eventQuota = intent.getIntExtra("event.quota", 0)
        val eventRegistrants = intent.getIntExtra("event.registrants", 0)
        val eventLink = intent.getStringExtra("event.link")

        eventName?.let {
            supportActionBar?.title = it
        }

        setupEventDetails(
            eventName,
            eventImage,
            eventImageLogo,
            eventSummary,
            eventOwner,
            eventDescription,
            eventBeginTime,
            eventQuota,
            eventRegistrants
        )
        setupLinkButton(eventLink)
    }

    private fun setupEventDetails(
        name: String?,
        mediaCover: String?,
        imageLogo: String?,
        summary: String?,
        ownerName: String?,
        description: String?,
        beginTime: String?,
        quota: Int?,
        registrants: Int?
    ) {
        val tvEventName = findViewById<TextView>(R.id.tv_event_name)
        val tvEventSummary = findViewById<TextView>(R.id.tv_event_summary)
        val tvDescription = findViewById<TextView>(R.id.tv_description)
        val tvBeginTime = findViewById<TextView>(R.id.tv_begin_time)
        val tvOwnerName = findViewById<TextView>(R.id.tv_owner_name)
        val tvEventQuota = findViewById<TextView>(R.id.tv_event_quota)

        tvEventName.text = name
        tvEventSummary.text = summary
        tvBeginTime.text = beginTime
        tvOwnerName.text = ownerName
        tvEventQuota.text = getString(R.string.remaining_quota).plus(quota?.minus(registrants ?: 0))
        tvDescription.text = HtmlCompat.fromHtml(
            description ?: "",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        mediaCover?.let {
            Glide.with(this)
                .load(it)
                .into(findViewById(R.id.iv_media_cover))
        }
        imageLogo?.let {
            Glide.with(this)
                .load(it)
                .into(findViewById(R.id.iv_image_logo))
        }
    }

    private fun setupLinkButton(link: String?) {
        val btnLink = findViewById<Button>(R.id.button)
        btnLink.setOnClickListener {
            link?.let {
                openEventLink(it)
            }
        }
    }

    private fun openEventLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
