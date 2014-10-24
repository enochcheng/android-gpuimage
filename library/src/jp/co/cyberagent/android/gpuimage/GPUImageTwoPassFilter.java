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

import java.util.Arrays;

public class GPUImageTwoPassFilter extends GPUImageFilterGroup {
    public GPUImageTwoPassFilter(String firstVertexShader, String firstFragmentShader,
                                 String secondVertexShader, String secondFragmentShader) {
        super(null);
        if (firstVertexShader != null && firstFragmentShader != null) {
            addFilter(new GPUImageFilter(firstVertexShader, firstFragmentShader));
        }
        if (secondVertexShader != null && secondFragmentShader != null) {
            addFilter(new GPUImageFilter(secondVertexShader, secondFragmentShader));
        }
    }
    
    protected void setShaders(String firstVertexShader, String firstFragmentShader,
            String secondVertexShader, String secondFragmentShader) {
        setFilters(Arrays.asList(new GPUImageFilter[]{new GPUImageFilter(firstVertexShader, firstFragmentShader), new GPUImageFilter(secondVertexShader, secondFragmentShader)}));
    }
}
