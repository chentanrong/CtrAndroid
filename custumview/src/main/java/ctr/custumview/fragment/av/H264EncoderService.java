package ctr.custumview.fragment.av;

import android.graphics.ImageFormat;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

import static android.media.MediaFormat.MIMETYPE_VIDEO_AVC;

/**
 * 将相机预览写入文件
 *
 * @User chentanrong
 * @Date 2020/4/15
 * @Desc
 **/
public class H264EncoderService {
    private final static int TIMEOUT_USEC = 12000;

    private int width, height, framerate;
    private File desFile;
    private MediaCodec mediaCodec;

    public ArrayBlockingQueue<byte[]> yuv420Queue = new ArrayBlockingQueue<>(10);
    private BufferedOutputStream outputStream;

    public H264EncoderService(int width, int height, int framerate, File file,int imageFormat) {
        if ((width & 1) == 1) {
            width--;
        }
        if ((height & 1) == 1) {
            height--;
        }
        this.width = width;
        this.height = height;
        this.framerate = framerate;
        this.desFile = file;

        MediaFormat videoFormat = MediaFormat.createVideoFormat(MIMETYPE_VIDEO_AVC, width, height);

        videoFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height * 5);
        videoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
        videoFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
        switch(imageFormat ){
            case ImageFormat.NV21:
                videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
                break;
            case ImageFormat.YV12:
                videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
                break;
            default:
                break;
        }

        videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);

        try {
            mediaCodec = MediaCodec.createDecoderByType(MIMETYPE_VIDEO_AVC);
            mediaCodec.reset();
            mediaCodec.configure(videoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
            createfile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createfile(File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRuning = false;

    public void putData(byte[] buffer) {
        if (yuv420Queue.size() >= 10) {
            yuv420Queue.poll();
        }
        yuv420Queue.add(buffer);
    }

    public byte[] configbyte;


    public void startEncoder() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                isRuning = true;
                byte[] input = null;
                while (isRuning) {
                    if (yuv420Queue.size() > 0) {
                        byte[] yuv420sp = new byte[width * height * 3 / 2];
                        NV21ToNV12(input, yuv420sp, width, height);
                    }
                    if (input != null) {
                        try {
                            int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
                            if (inputBufferIndex >= 0) {
                                ByteBuffer inputBuffer = mediaCodec.getInputBuffer(inputBufferIndex);
                                inputBuffer.clear();
                                inputBuffer.put(input);
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, System.currentTimeMillis(), 0);
                            }
                            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
                            while (outputBufferIndex >= 0) {
                                ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(outputBufferIndex);
                                byte[] outData = new byte[bufferInfo.size];
                                outputBuffer.get(outData);
                                if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_CODEC_CONFIG) {
                                    configbyte = new byte[bufferInfo.size];
                                    configbyte = outData;
                                } else if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_SYNC_FRAME) {
                                    byte[] keyframe = new byte[bufferInfo.size + configbyte.length];
                                    System.arraycopy(configbyte, 0, keyframe, 0, configbyte.length);
                                    System.arraycopy(outData, 0, keyframe, configbyte.length, outData.length);
                                    outputStream.write(keyframe, 0, keyframe.length);
                                } else {
                                    outputStream.write(outData, 0, outData.length);
                                }
                                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                // 停止编解码器并释放资源
                try {
                    mediaCodec.stop();
                    mediaCodec.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 关闭数据流
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * 停止编码数据
     */
    public void stopEncoder() {
        isRuning = false;
    }


    private void NV21ToNV12(byte[] nv21, byte[] nv12, int width, int height) {
        if (nv21 == null || nv12 == null) return;
        int framesize = width * height;
        int i = 0, j = 0;
        System.arraycopy(nv21, 0, nv12, 0, framesize);
        for (i = 0; i < framesize; i++) {
            nv12[i] = nv21[i];
        }
        for (j = 0; j < framesize / 2; j += 2) {
            nv12[framesize + j - 1] = nv21[j + framesize];
        }
        for (j = 0; j < framesize / 2; j += 2) {
            nv12[framesize + j] = nv21[j + framesize - 1];
        }
    }


}
