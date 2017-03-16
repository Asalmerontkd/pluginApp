/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mariachi.allianzvision.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.google.android.gms.vision.face.Face;

import io.mariachi.allianzvision.ui.cvision.GraphicOverlay;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float BOX_STROKE_WIDTH = 5.0f;

    float leftEye, rightEye;

    private volatile Face mFace;
    private int mFaceId;

    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);
    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        leftEye = face.getIsLeftEyeOpenProbability();
        rightEye = face.getIsRightEyeOpenProbability();

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        Paint myPaint = new Paint();
        myPaint.setColor(Color.WHITE);
        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(BOX_STROKE_WIDTH);
        canvas.drawCircle(x, y, face.getHeight() / 2, myPaint);


        canvas.drawCircle(x, y, 2, myPaint);
    }

    public boolean blink()
    {
        if ((leftEye < 0.20 && leftEye > -1) && (rightEye < 0.20 && rightEye > -1) && (leftEye!=0 && rightEye!=0))
        {
            Log.w("Blink method", "Return true");
            Log.w("Blink Left", ""+leftEye);
            Log.w("Blink Right", ""+rightEye);
            return true;
        }
        return false;
    }
}
