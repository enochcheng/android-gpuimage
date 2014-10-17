/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.cyberagent.android.gpuimage;

import android.opengl.GLES20;
import android.util.Log;

public class GPUImageTwoPassTextureSamplingFilter extends GPUImageTwoPassFilter {
    public GPUImageTwoPassTextureSamplingFilter(String firstVertexShader, String firstFragmentShader,
                                                String secondVertexShader, String secondFragmentShader) {
        super(firstVertexShader, firstFragmentShader,
                secondVertexShader, secondFragmentShader);
    }

    int texelWidthOffsetLocation;
    int texelHeightOffsetLocation;
    GPUImageFilter filter;
    float ratio;
    
    @Override
    public void onInit() {
        super.onInit();
        initTexelOffsets();
    }

    protected void initTexelOffsets() {
        ratio = getHorizontalTexelOffsetRatio();
        filter = mFilters.get(0);
        texelWidthOffsetLocation  = GLES20.glGetUniformLocation(filter.getProgram(), "texelWidthOffset");
        texelHeightOffsetLocation = GLES20.glGetUniformLocation(filter.getProgram(), "texelHeightOffset");
        filter.setFloat(texelWidthOffsetLocation, ratio / mOutputWidth);
        filter.setFloat(texelHeightOffsetLocation, 0);
        Log.v("GENERATE", " INIT vertical is " + ratio / mOutputWidth);

        ratio = getVerticalTexelOffsetRatio();
        filter = mFilters.get(1);
        texelWidthOffsetLocation  = GLES20.glGetUniformLocation(filter.getProgram(), "texelWidthOffset");
        texelHeightOffsetLocation = GLES20.glGetUniformLocation(filter.getProgram(), "texelHeightOffset");
        filter.setFloat(texelWidthOffsetLocation, 0);
        filter.setFloat(texelHeightOffsetLocation, ratio / mOutputHeight);
        Log.v("GENERATE", " INIT horizontal is " + ratio / mOutputHeight);
    }
    
    protected void updateTexelOffsets(float w, float h) {
        filter = mFilters.get(0);
        texelWidthOffsetLocation  = GLES20.glGetUniformLocation(filter.getProgram(), "texelWidthOffset");
        texelHeightOffsetLocation = GLES20.glGetUniformLocation(filter.getProgram(), "texelHeightOffset");
        filter.setFloat(texelWidthOffsetLocation, w / mOutputWidth);
        filter.setFloat(texelHeightOffsetLocation, 0);
        Log.v("GENERATE", " NEW vertical is " + w / mOutputWidth);

        filter = mFilters.get(1);
        texelWidthOffsetLocation  = GLES20.glGetUniformLocation(filter.getProgram(), "texelWidthOffset");
        texelHeightOffsetLocation = GLES20.glGetUniformLocation(filter.getProgram(), "texelHeightOffset");
        filter.setFloat(texelWidthOffsetLocation, 0);
        filter.setFloat(texelHeightOffsetLocation, h / mOutputHeight);
        Log.v("GENERATE", " NEW horizontal is " + h / mOutputHeight);
    }

    @Override
    public void onOutputSizeChanged(int width, int height) {
        super.onOutputSizeChanged(width, height);
        initTexelOffsets();
    }

    public float getVerticalTexelOffsetRatio() {
        return 1f;
    }

    public float getHorizontalTexelOffsetRatio() {
        return 1f;
    }

	public void setVerticalTexelSpacing(float f) {
		updateTexelOffsets(0.0f, f);
	}

	public void setHorizontalTexelSpacing(float f) {
		updateTexelOffsets(f, 0.0f);
	}
}
