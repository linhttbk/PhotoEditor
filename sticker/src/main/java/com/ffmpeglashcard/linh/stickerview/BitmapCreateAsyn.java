package com.ffmpeglashcard.linh.stickerview;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Linh on 6/6/2018.
 */

public class BitmapCreateAsyn extends AsyncTask<Void, Void, Bitmap> {
    private StickerView view;

    public BitmapCreateAsyn(StickerView view) {
        this.view = view;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        return view.createBitmap();
    }
}
