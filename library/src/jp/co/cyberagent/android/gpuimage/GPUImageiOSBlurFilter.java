package jp.co.cyberagent.android.gpuimage;

import java.util.Arrays;
import java.util.List;

import android.graphics.Point;

public class GPUImageiOSBlurFilter extends GPUImageFilterGroup {

    public GPUImageiOSBlurFilter() {
        super(createFilters());
        saturationFilter = (GPUImageSaturationFilter) mFilters.get(0);
        blurFilter = (GPUImageGaussianBlurFilter) mFilters.get(1);
        luminanceRangeFilter = (GPUImageLuminanceRangeFilter) mFilters.get(2);
    }
    
    GPUImageSaturationFilter saturationFilter;
    GPUImageGaussianBlurFilter blurFilter;
    GPUImageLuminanceRangeFilter luminanceRangeFilter;
    private float mDownSampling = 4.0f;

    private  static List<GPUImageFilter> createFilters() {
        return Arrays.asList(new GPUImageFilter[] {
                new GPUImageSaturationFilter(0.8f),
                new GPUImageGaussianBlurFilter(12.0f, true),
                new GPUImageLuminanceRangeFilter(0.6f)
        });
    }

    public void setBlurRadiusInPixels(final float newValue)
    {
        blurFilter.setBlurRadiusInPixels(newValue);
    }

    public float blurRadiusInPixels()
    {
        return blurFilter.getBlurRadiusInPixels();
    }

    public void setSaturation(final float newValue)
    {
        saturationFilter.setSaturation(newValue);
    }

    public float saturation()
    {
        return saturationFilter.getSaturation();
    }

 
    public void setRangeReductionFactor(final float newValue)
    {
        luminanceRangeFilter.setRangeReductionFactor(newValue);
    }

    public float rangeReductionFactor()
    {
        return luminanceRangeFilter.getRangeReductionFactor();
    }
    
    public void setDownSampling(final float newValue)
    {
        mDownSampling = newValue;
    }

    public float getDownSampling()
    {
        return mDownSampling;
    }
    
    
    @Override
    public void setInputSize(int width, int height, Point temp) {
        if (mDownSampling > 1.0)
        {
            super.setInputSize((int) (width/mDownSampling), (int) (height/mDownSampling), temp);
        }
        else {
            super.setInputSize(width, height, temp);
        }
    }
}
