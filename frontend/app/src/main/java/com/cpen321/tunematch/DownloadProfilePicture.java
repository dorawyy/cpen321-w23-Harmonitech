package com.cpen321.tunematch;

import android.widget.ImageView;

public class DownloadProfilePicture extends DownloadImage {
    // ChatGPT Usage: No
    public DownloadProfilePicture(ImageView image, String url) {
        super(image, url, R.drawable.default_profile_image);
    }
}
