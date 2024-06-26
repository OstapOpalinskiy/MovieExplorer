package com.opalynskyi.movies

import android.content.Intent
import androidx.fragment.app.Fragment
import com.opalynskyi.movieslist.R

fun Fragment.share(text: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
    requireActivity().startActivity(
        Intent.createChooser(
            sharingIntent,
            getString(R.string.movies_list_share_chooser_title),
        ),
    )
}
