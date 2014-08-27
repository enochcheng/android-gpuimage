package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;

public class GPUImageLuminanceRangeFilter extends GPUImageFilter {

    public static final String LUMINANCE_FRAGMENT_SHADER = ""
            + " varying highp vec2 textureCoordinate;\n"
            + " \n"
            + " uniform sampler2D inputImageTexture;\n"
            + " uniform lowp float rangeReduction;\n"
            + " \n"
            + " const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n"
            + " \n"
            + " void main()\n"
            + " {\n"
            + "    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n"
            + "    lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n"
            + "    lowp float luminanceRatio = ((0.5 - luminance) * rangeReduction);\n"
            + "    \n"
            + "    gl_FragColor = vec4((textureColor.rgb) + (luminanceRatio), textureColor.w);\n"
            + "     \n" + " }";

    private int mRangeReductionFactoLocation;
    private float mRangeReductionFactor;

    public GPUImageLuminanceRangeFilter() {
        this(0.6f);
    }

    public GPUImageLuminanceRangeFilter(final float factor) {
        super(NO_FILTER_VERTEX_SHADER, LUMINANCE_FRAGMENT_SHADER);
        mRangeReductionFactor = factor;
    }

    @Override
    public void onInit() {
        super.onInit();
        mRangeReductionFactoLocation = GLES20.glGetUniformLocation(
                getProgram(), "rangeReduction");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setRangeReductionFactor(mRangeReductionFactor);
    }

    public void setRangeReductionFactor(final float factor) {
        mRangeReductionFactor = factor;
        setFloat(mRangeReductionFactoLocation, mRangeReductionFactor);
    }
    
    public float getRangeReductionFactor() {
       return mRangeReductionFactor;
    }
}
