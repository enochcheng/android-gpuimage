package jp.co.cyberagent.android.gpuimage;

import au.notzed.jjmpeg.AVFrame;
import au.notzed.jjmpeg.exception.AVEncodingError;
import au.notzed.jjmpeg.exception.AVIOException;
import au.notzed.jjmpeg.exception.AVInvalidCodecException;
import au.notzed.jjmpeg.exception.AVInvalidFormatException;
import au.notzed.jjmpeg.exception.AVInvalidStreamException;
import au.notzed.jjmpeg.io.*;
import au.notzed.jjmpeg.io.JJMediaWriter.JJWriterVideo;

public class GPUImageMovieWriter {
	
	private volatile JJMediaWriter recorder;
	private volatile JJWriterVideo vstream;
	
	private boolean isPreviewOn = false;  	
	  long startTime = 0;
	   boolean recording = false;
	    
	    private int sampleAudioRateInHz = 44100;
	    private int imageWidth = 320;
	    private int imageHeight = 240;
	    private int frameRate = 30;
	    
	    private String ffmpeg_link = "/mnt/sdcard/Generate/stream.avi";
	    
	    
	public GPUImageMovieWriter () {
		
		initRecorder();
	}
	
	public void startRecording(){
        startTime = System.currentTimeMillis();
		try {
			recorder.open();
		} catch (AVInvalidFormatException e) {
			e.printStackTrace();
		} catch (AVInvalidCodecException e) {
			e.printStackTrace();
		} catch (AVIOException e) {
			e.printStackTrace();
		}
         recording = true;
	}
	
	public void finishRecording(){

        if (recorder != null && recording) {
            recording = false;
            recorder.close();
            recorder = null;
        }	
	}
	
	public void writeFrame(byte[] data) {
		AVFrame image = vstream.loadImage(data);
		try {
			vstream.addFrame(image);
		} catch (AVEncodingError e) {
			e.printStackTrace();
		} catch (AVIOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancelRecording(){
		
	}
	
	public void setOutputFilePath (String s){
		ffmpeg_link = s;
	}
	
	//---------------------------------------
    // initialize ffmpeg_recorder
    //---------------------------------------
    private void initRecorder() {
    		try {
				recorder = new JJMediaWriter(ffmpeg_link);
			} catch (AVInvalidFormatException e) {
				e.printStackTrace();
			}
    		try {
				vstream = recorder.addVideoStream(imageWidth, imageHeight, 30, 400000);
			} catch (AVInvalidStreamException e) {
				e.printStackTrace();
			}
    }
}
