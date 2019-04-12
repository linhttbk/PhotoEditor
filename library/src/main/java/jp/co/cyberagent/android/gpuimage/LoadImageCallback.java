package jp.co.cyberagent.android.gpuimage;

public interface LoadImageCallback {
    void onStartLoading();

    void onLoadingSuccess();

    void onLoadError();
}
