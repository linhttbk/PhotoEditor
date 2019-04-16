package com.linhtt.photoeditor.custom;

import android.opengl.GLES20;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class GPUImageColorFilter extends GPUImageFilter {
    public static final String BRIGHTNESS_FRAGMENT_SHADER = "" +
            "varying highp vec2 textureCoordinate;\n" +
            " \n" +
            " uniform sampler2D inputImageTexture;\n" +
            "uniform mediump mat4 colorMatrix;\n" +
            "uniform mediump vec4  colorOffset;\n" +
            " \n" +
            " void main()\n" +
            " {\n" +
            "     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
            "     \n" +
            "     gl_FragColor =  textureColor * colorMatrix + colorOffset;\n" +
            " }";


    private int colorMatrix;
    private int colorOffset;


    public GPUImageColorFilter() {
        this(new float[]{0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 0,
                0, 1, 0, 1}, new float[]{0, 0, 0, 0});
    }

    public GPUImageColorFilter(final float[] colorMatrix, final float[] colorOffset) {
        super(NO_FILTER_VERTEX_SHADER, BRIGHTNESS_FRAGMENT_SHADER);
    }


    @Override
    public void onInit() {
        super.onInit();
        colorMatrix = GLES20.glGetUniformLocation(getProgram(), "colorMatrix");
        colorOffset = GLES20.glGetUniformLocation(getProgram(), "colorOffset");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        changeMatrix(new float[]{1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1}, new float[]{0.1f, 0, 0, 0});

    }


    public void changeMatrix(final float[] matrix, final float[] colorOffset) {
        setUniformMatrix4f(colorMatrix, matrix);
        setFloatVec4(this.colorOffset, colorOffset);
    }
}
