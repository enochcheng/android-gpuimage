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

/**
 * SILO
 */
public class GPUImageChromAbbFilter extends GPUImageFilter {
    public static final String CHROMABB_FRAGMENT_SHADER = "" +

            " uniform sampler2D inputImageTexture;\n" +
            " varying highp vec2 textureCoordinate;\n" +

			" uniform highp float XoffsetValue;\n" +
            " uniform highp float YoffsetValue;\n" +
            
            " highp float rand_state = 0.5;\n" +

            "void main()\n" +
            "{\n" +
       
		     " highp vec4 textureColor;\n" +
		     " highp vec4 outputColor;\n" +
		     " highp vec2 offset = vec2(XoffsetValue,YoffsetValue);\n" +
     
			" textureColor = texture2D(inputImageTexture, textureCoordinate + offset);\n" +
			" outputColor.r = textureColor.r;\n" +
			     
			" textureColor = texture2D(inputImageTexture, textureCoordinate - offset);\n" +
			" outputColor.g = textureColor.g;\n" +
			     
			" textureColor = texture2D(inputImageTexture, textureCoordinate);\n" +
			" outputColor.b = textureColor.b;\n" +
			     
			" gl_FragColor = vec4(outputColor.rgb, textureColor.a);\n" +
            
            "}";

    private int XoffsetUniform;
    private int YoffsetUniform;

    public GPUImageChromAbbFilter() {
    		super(NO_FILTER_VERTEX_SHADER, CHROMABB_FRAGMENT_SHADER);
    }

    @Override
    public void onInit() {
        super.onInit();
        XoffsetUniform = GLES20.glGetUniformLocation(getProgram(), "XoffsetValue");
        YoffsetUniform = GLES20.glGetUniformLocation(getProgram(), "YoffsetValue");

        setXoffset(0.05f);
        setYoffset(0.05f);
    }


    public void setXoffset(final float offset) {
        setFloat(XoffsetUniform, offset);
    }
    
    public void setYoffset(final float offset) {
        setFloat(YoffsetUniform, offset);
    }
}
