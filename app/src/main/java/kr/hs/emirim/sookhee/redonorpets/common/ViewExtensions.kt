package kr.hs.emirim.sookhee.redonorpets.common

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kr.hs.emirim.sookhee.redonorpets.R

/**
 *  ViewExtensions.kt
 *
 *  Created by Minji Jeong on 2022/01/07
 *  Copyright © 2022 DonorPets2.0. All rights reserved.
 */

@SuppressLint("CheckResult")
fun ImageView.setImageWithUrl(url: String, radius: Int = 0) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 디스크 캐시 리소스
        .skipMemoryCache(true) // 메모리 캐시 사용안함.
        .priority(Priority.HIGH) // 처리순서

    if (radius != 0) options.transform(RoundedCorners(radius))

    Glide.with(context)
        .load(url)
        .apply(options)
        .error(R.drawable.border)
        .into(this)
}
